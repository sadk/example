package org.lsqt.chk.mq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.lsqt.chk.model.CrimeDetail;
import org.lsqt.chk.model.UserCrime;
import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.OnStarted;
import org.lsqt.components.db.Db;
import org.lsqt.components.util.collection.ArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;


@Component
public class UserCrimeConsumer {
	private static final Logger log = LoggerFactory.getLogger(UserCrimeConsumer.class);
	
	@Inject private Db db;
	
 
	
	@SuppressWarnings("unchecked")
	@OnStarted(order = 1 , text="容器启动后：RabitMQ消费端启动，消费掉风控平台推送过来的数据")
	public void consume() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		
		factory.setVirtualHost(UserCrimeMQConfig.getVirtualHost());
		factory.setHost(UserCrimeMQConfig.getIP());
		factory.setPort(Integer.valueOf(UserCrimeMQConfig.getPort()));
		factory.setUsername(UserCrimeMQConfig.getUsername());
		factory.setPassword(UserCrimeMQConfig.getPassword());
			
		 
		Connection con = factory.newConnection();
		Channel chan = con.createChannel();
		//chan.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
		
		/*
		if (!isDbConfig) {
			QUEUE_NAME = chan.queueDeclare().getQueue();
		}
		*/
		
		chan.queueBind(UserCrimeMQConfig.getQueueName(), UserCrimeMQConfig.getExchangeName(),UserCrimeMQConfig.getRoutingKey());
		chan.basicQos(1); //同一时刻服务器只会发一条消息给消费者
		
		log.info("waiting message ...");
		
		Consumer c = new DefaultConsumer(chan){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				log.info("风控平台发送过来的消息, {} ",message); 
				
				db.executePlan(() -> {
					Map<String,Object> map = JSON.parseObject(message, Map.class);
					if (map!=null 
							&& (map.containsKey("ret_code") && map.get("ret_code")!=null)
							&& (map.containsKey("business_id") && map.get("business_id")!=null)) {
						
						String retCode = map.get("ret_code").toString();
						Long businessId = Long.valueOf(map.get("business_id").toString());
						
						UserCrime model = db.getById(UserCrime.class, businessId);
						if (model == null) {
							log.error("风控平台发送过来的消息业务处理：没有找到id为{}的对象",businessId);
							return ;
						}
						
						if (ResultCrime.RET_CODE_OK.equals(retCode)) {
							if (map.get("data")!=null) {
								String dataString = map.get("data").toString();
								Map<String,Object> data = JSON.parseObject(dataString, Map.class);
								if (data != null) {
									String resCode = data.get("resCode") == null ? null : data.get("resCode").toString();
									String resMsg = data.get("resMsg") == null ? null : data.get("resMsg").toString();
									String statusMsg = data.get("statusMsg") == null ? null : data.get("statusMsg").toString();
									
									model.setResCode(resCode);
									model.setResMsg(resMsg+"|"+statusMsg);
									db.update(model, "resCode","resMsg");
									
									if (data.get("case") != null) {
										List<Map<String,Object>> caseList = (List<Map<String,Object>>)data.get("case");
										if (ArrayUtil.isNotBlank(caseList)) {
											List<CrimeDetail> modelList = new ArrayList<>();
											for (Map<String,Object> row : caseList) {
												CrimeDetail detail = new CrimeDetail();
												detail.setUcId(businessId);
												detail.setCaseLevel(row.get("caseLevel") == null ? null :row.get("caseLevel").toString());
												detail.setCasePeriod(row.get("casePeriod") == null ? null :row.get("casePeriod").toString());
												detail.setCaseSource(row.get("caseSource") == null ? null :row.get("caseSource").toString());
												detail.setCrimeType(row.get("crimeType") == null ? null :row.get("crimeType").toString());
												detail.setCaseType(row.get("caseType") == null ? null :row.get("caseType").toString());
												detail.setCount(row.get("count") == null ? null :row.get("count").toString());
												modelList.add(detail);
											}
											
											if (ArrayUtil.isNotBlank(caseList)) {
												db.executeUpdate("delete from chk_crime_detail where uc_id=?", businessId);
												db.batchSave(modelList);
											}
										}
										
									}
								}
							}
						} else {
							model.setResCode(retCode);
							if (map.get("msg") != null) {
								model.setResMsg(map.get("msg").toString());
							}
							db.update(model, "resCode", "resMsg");
						}
						
 					}
					
				});
			}
		};
		
		chan.basicConsume(UserCrimeMQConfig.getQueueName(), true, c);  //第二个参数代表自动确认，不是客户端ack信号确认
		 
	}
	
	
}


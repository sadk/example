package org.lsqt.components.plugin.cache;

import java.io.IOException;

import org.lsqt.components.cache.Cache;
import org.lsqt.components.cache.CacheSynchronizer;
import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.annotation.OnStarted;
import org.lsqt.components.db.Db;
import org.lsqt.components.util.ExceptionUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * 缓存同步器
 * 注：在分布式集群时才有效果
 * 
 * @author mm
 *
 */
@Component
public class RabitMQEhcacheSynchronizer implements CacheSynchronizer<String, Object> {
	private static final Logger log = LoggerFactory.getLogger(RabitMQEhcacheSynchronizer.class);

	private static final String EXCHANGE_NAME = CacheSynchronizer.class.getName().concat(".exchange");
	private static final String ROUTING_KEY = CacheSynchronizer.class.getName().concat(".routingKey");

	private Cache<String,Object> ehcachePlugin = EhcachePlugin.getInstance();
	
	@OnStarted
	public void consumerListen() throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");

		Connection con = factory.newConnection();
		Channel chan = con.createChannel();
		chan.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT); // 广播模式!!!

		String queueName = chan.queueDeclare().getQueue();
		chan.queueBind(queueName, EXCHANGE_NAME, ROUTING_KEY);

		log.info("web cache synchronizing , consumer waiting message ...");

		Consumer c = new DefaultConsumer(chan) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				log.info("缓存广播同步刷新请求--> accept: {}", message);
				
				if(StringUtil.isBlank(message)) {
					return ;
				}
				
				try {
					Action act = JSON.parseObject(message, Action.class);
					if ("remove".equals(act.name)) {
						ehcachePlugin.remove(act.argsNameSpace, act.argsKey, false);
					} else if ("clear".equals(act.name)) {
						ehcachePlugin.clear(act.argsNameSpace, false);
					} else if ("put".equals(act.name)) {
						ehcachePlugin.put(act.argsNameSpace, act.argsKey, act.argsValue, false);
					}
				} catch (Exception ex) {
					log.error("队列监听器，更新缓存失败: {}", ExceptionUtil.getStackTrace(ex));
				}
			}
		};

		chan.basicConsume(queueName, true, c);
	}

	public Object remove(String nameSpace, String key) throws Exception {
		Action act = new Action();
		act.name = "remove";
		act.argsNameSpace = nameSpace;
		act.argsKey = key;

		sendMessage(JSON.toJSONString(act, true)); // 发送消息到中间件，通知其它订阅端更新本地
		return null;
	}

	public void clear(String nameSpace) throws Exception {
		Action act = new Action();
		act.name = "clear";
		act.argsNameSpace = nameSpace;
		sendMessage(JSON.toJSONString(act, true));
	}

	public Object put(String nameSpace, String key, Object value) throws Exception {
		Action act = new Action();
		act.name = "put";
		act.argsNameSpace = nameSpace;
		act.argsKey = key;
		act.argsValue = value;

		return null;
	}

	private void sendMessage(String message) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		/*
		 * factory.setPort(222); factory.setUsername("");
		 * factory.setPassword("");
		 */
		Connection con = factory.newConnection();
		Channel chan = con.createChannel();
		chan.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
		chan.basicPublish(EXCHANGE_NAME, message, null, message.getBytes());
		System.out.println("send :" + message);

		chan.close();
		con.close();
	}

	/**
	 * 缓存操作封装
	 * 
	 * @author mm
	 *
	 */
	public static class Action {
		public String name;// 缓存增删改方法名

		public String argsNameSpace;// 缓存增删改方法入参值
		public String argsKey;
		public Object argsValue;
	}

	public static class DbMQConfig {
		public static boolean isEnable;
		
		public static String host;
		public static String port;
		public static String userName;
		public static String password;
		public static String virtualHost;
		
		public static void init(Db db) {
			
		}
		
	}
	
	public static void main(String[] args) {
		String[] arr = new String[] { "111", "222da", "3333fdsaa" };
		String json = JSON.toJSONString(arr, true);
		System.out.println(json);
		 
	}
}

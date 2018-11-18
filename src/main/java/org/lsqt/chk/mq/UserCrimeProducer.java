package org.lsqt.chk.mq;

import org.lsqt.components.context.annotation.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Component
public class UserCrimeProducer {
	private static final Logger log = LoggerFactory.getLogger(UserCrimeProducer.class);
	
	/**
	 * 生产消息
	 * @param message 
	 * @throws Exception
	 */
	public static void produce(String message) throws Exception {
		log.info("发送给风控数据接入平台的消息: {}",message);
		
		UserCrimeMQConfig.printConfig();
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setVirtualHost(UserCrimeMQConfig.getVirtualHost());
		factory.setHost(UserCrimeMQConfig.getIP());
		factory.setPort(Integer.valueOf(UserCrimeMQConfig.getPort()));
		factory.setUsername(UserCrimeMQConfig.getUsername());
		factory.setPassword(UserCrimeMQConfig.getPassword());

			
		try (Connection con = factory.newConnection(); Channel chan = con.createChannel()) {
			// chan.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

			chan.basicPublish(UserCrimeMQConfig.getExchangeName(), UserCrimeMQConfig.getRoutingKey(), null, message.getBytes());

			log.info("send routing_key({}): ", message);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}


package org.lsqt.chk.mq;

import java.util.List;

import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.OnStarted;
import org.lsqt.components.db.Db;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.sys.model.Machine;
import org.lsqt.sys.model.MachineQuery;
import org.lsqt.sys.model.Property;
import org.lsqt.sys.model.PropertyQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MQ的配置类，从DB里加载信息
 * @author mm
 *
 */
@Component
public class UserCrimeMQConfig {
	private static final Logger log = LoggerFactory.getLogger(UserCrimeMQConfig.class);
	
	@Inject private Db db;
	private static boolean IS_ENABLE;
	
	private static String VIRTUAL_HOST;
	private static String IP;
	private static String PORT;
	private static String USERNAME;
	private static String PASSWORD;
	
	private static String EXCHANGE_NAME;
	private static String ROUTING_KEY; //发送的路由key
	private static String ROUTING_KEY_CONSUMER; //消费端接收的路由key
	
	private static String QUEUE_NAME;
	
	/**
	 * 是否启用MQ
	 * @return
	 */
	public static boolean isEnable() {
		return IS_ENABLE;
	}
	public static String getVirtualHost() {
		return VIRTUAL_HOST;
	}
	public static String getIP() {
		return IP;
	}
	public static String getPort() {
		return PORT;
	}
	public static String getUsername(){
		return USERNAME;
	}
	public static String getPassword() {
		return PASSWORD;
	}
	public static String getExchangeName() {
		return EXCHANGE_NAME;
	}
	public static String getRoutingKey() {
		return  ROUTING_KEY;
	}
	public static String getQueueName() {
		return QUEUE_NAME;
	}
	
	public static String getRoutingKeyConsumer() {
		return ROUTING_KEY_CONSUMER;
	}
	@OnStarted
	public synchronized void initConfig()  {
		log.info("Loading rabitmq config info from db, it's configuration for FongKong Platform!!!");
		
		db.executePlan(false, () -> {
			MachineQuery query = new MachineQuery();
			query.setCode("RabitMQ_FengKong");
			Machine machine = db.queryForObject("queryForPage", Machine.class, query);

			String errorMsg = "请录入rabitMQ机器配置,编码为:" + query.getCode();
			if (machine == null) {
				//throw new NullPointerException(errorMsg);
				printConfig();
				return ;
			}

			if (machine.getStatus() != null && Dictionary.ENABLE_启用 == machine.getStatus()) {
				IS_ENABLE = true;
			}
			
			PropertyQuery propQuery = new PropertyQuery();
			propQuery.setParentCode(machine.getCode());
			List<Property> list = db.queryForList("queryForPage", Property.class, propQuery);

			if (ArrayUtil.isBlank(list)) {
				throw new NullPointerException(errorMsg);
			}

			for (Property p : list) {
				if ("exchange_name".equals(p.getName())) {
					EXCHANGE_NAME = p.getValue();
				}
				if ("routing_key".equals(p.getName())) {
					ROUTING_KEY = p.getValue();
				}
				if ("routing_key_consumer".equals(p.getName())) {
					ROUTING_KEY_CONSUMER = p.getValue();
				}
				if ("queue_name".equals(p.getName())) {
					QUEUE_NAME = p.getValue();
				}
				
				if ("virtualHost".equals(p.getName())) {
					VIRTUAL_HOST = p.getValue();
				}
				if ("ip".equals(p.getName())) {
					IP = p.getValue();
				}
				if ("port".equals(p.getName())) {
					PORT = p.getValue();
				}
				if ("username".equals(p.getName())) {
					USERNAME = p.getValue();
				}
				if ("password".equals(p.getName())) {
					PASSWORD = p.getValue();
				}
				 
			}
		});
		
		printConfig();
	}
	
	
	
	public static void printConfig () {
		log.info("#################### RabitMQ的配置 ####################");
		log.info("#");
		log.info("# MQ是否启用: {}",IS_ENABLE);
		log.info("# virtualHost: {}",VIRTUAL_HOST);
		log.info("# ip: {}",IP);
		log.info("# port: {}",PORT);
		log.info("# username: {}", USERNAME);
		log.info("# password: {}",PASSWORD);
		log.info("# exchange_name: {}",EXCHANGE_NAME);
		log.info("# routing_key_produce: {}",ROUTING_KEY);
		log.info("# routing_key_consumer: {}",ROUTING_KEY_CONSUMER);
		log.info("#");
		log.info("#################### RabitMQ的配置 ####################");
	}
	
}


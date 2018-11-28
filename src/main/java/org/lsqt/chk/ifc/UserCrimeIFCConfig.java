package org.lsqt.chk.ifc;

import java.io.File;
import java.util.List;

import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.OnStarted;
import org.lsqt.components.db.Db;
import org.lsqt.components.mvc.ConfigInitialization;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.sys.model.Machine;
import org.lsqt.sys.model.MachineQuery;
import org.lsqt.sys.model.Property;
import org.lsqt.sys.model.PropertyQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class UserCrimeIFCConfig {
	private static final Logger log = LoggerFactory.getLogger(UserCrimeIFCConfig.class);
	
	@Inject private Db db;
	
	private static String APP_KEY;
	private static String API_URL_BASE;
	private static String CER_PATH;
	
	public static String getAppKey() {
		return APP_KEY;
	}
	
	public static String getApiUrlBase() {
		return API_URL_BASE;
	}
	
	public static String getCerPath() {
		return CER_PATH;
	}
	
	@OnStarted
	public synchronized void initConfig() throws Exception  {
		db.executePlan(false, () -> {
			MachineQuery query = new MachineQuery();
			query.setCode("IFC_HTTP_HOST");
			Machine machine = db.queryForObject("queryForPage", Machine.class, query);

			String errorMsg = "请录入rabitMQ机器配置,编码为:" + query.getCode();
			if (machine == null) {
				log.error(errorMsg);
				return ;
				//throw new NullPointerException(errorMsg);
			}

			PropertyQuery propQuery = new PropertyQuery();
			propQuery.setParentCode(machine.getCode());
			List<Property> list = db.queryForList("queryForPage", Property.class, propQuery);

			if (ArrayUtil.isBlank(list)) {
				//throw new NullPointerException(errorMsg);
				printConfig();
				return ;
			}

			for (Property p : list) {
				if ("appKey".equals(p.getName())) {
					APP_KEY = p.getValue();
				}
				if ("apiUrlBase".equals(p.getName())) {
					API_URL_BASE = p.getValue();
				}
				if ("cerPath".equals(p.getName())) {
					CER_PATH = new File(ConfigInitialization.getServletContextRealPath()+p.getValue()).getAbsolutePath();
				}
			}
		});
		
		printConfig();
	}
	
	public static void printConfig () {
		log.info("#################### 风控平台IFC的配置: 用于检验身份证和姓名是否匹配  ####################");
		log.info("#");
		log.info("# appKey: {}",APP_KEY);
		log.info("# apiUrlBase: {}",API_URL_BASE);
		log.info("# cerPath: {}",CER_PATH);
		log.info("#");
		log.info("#################### 风控平台IFC的配置: 用于检验身份证和姓名是否匹配  ####################");
	}
}


package org.lsqt.code.springtest;

public class SimpleService {
	public void startup() {
		System.out.println("startup ... ");
	}
	public SimpleService (Config config) {
		this.config = config;
	}
	
 	public SimpleService(Object ... objects){
 		
 	}
	
	private Config config ;
	
	public Config getConfig() {
		return config;
	}
	public void setConfig(Config config) {
		this.config = config;
	}

}

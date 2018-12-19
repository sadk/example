package org.lsqt.components.mvc;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mm
 *
 */
public class BannerConfigInit implements Order,Initialization{
	private static final Logger log = LoggerFactory.getLogger(BannerConfigInit.class);
	
	private int order = -1;
	
	private static String banner = "" +
" _____                          _       ______                                           _"    + "\n" +
" |  ___|                        | |      |  ___|                                         | |   " + "\n" +
" | |____  ____ _ _ __ ___  _ __ | | ___  | |_ _ __ __ _ _ __ ___   _____      _____  _ __| | __" + "\n" +
" |  __\\ \\/ / _` | '_ ` _ \\| '_ \\| |/ _ \\ |  _| '__/ _` | '_ ` _ \\ / _ \\ \\ /\\ / / _ \\| '__| |/ /" + "\n" +
" | |___>  < (_| | | | | | | |_) | |  __/ | | | | | (_| | | | | | |  __/\\ V  V / (_) | |  |   < " + "\n" +
" \\____/_/\\_\\__,_|_| |_| |_| .__/|_|\\___| \\_| |_|  \\__,_|_| |_| |_|\\___| \\_/\\_/ \\___/|_|  |_|\\_\\" + "\n" +
"                          | |                                                                  " + "\n" +
"                          |_|                                                                  " + "\n";

	private static final String buddha =  
	"            ////////////////////////////////////////////////////////////////////" 		+ "\n" +
	"            //                          _ooOoo_                               //" 		+ "\n" +
	"            //                         o8888888o                              //" 		+ "\n" +
	"            //                         88\" . \"88                              //" 	+ "\n" +
	"            //                         (| ^_^ |)                              //" 		+ "\n" +
	"            //                         O\\  =  /O                              //" 	+ "\n" +
	"            //                      ____/`---'\\____                           //" 	+ "\n" +
	"            //                    .'  \\|     |//  `.                          //" 	+ "\n" +
	"            //                   /  \\\\|||  :  |||//  \\                        //" 	+ "\n" +
	"            //                  /  _||||| -:- |||||-  \\                       //" 	+ "\n" +
	"            //                  |   | \\\\\\  -  /// |   |                       //" 	+ "\n" +
	"            //                  | \\_|  ''\\---/''  |   |                       //" 	+ "\n" +
	"            //                  \\  .-\\__  `-`  ___/-. /                       //" 	+ "\n" +
	"            //                ___`. .'  /--.--\\  `. . ___                     //" 	+ "\n" +
	"            //              .\"\" '<  `.___\\_<|>_/___.'  >'\"\".                  //" + "\n" +
	"            //            | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |                 //" 	+ "\n" +
	"            //            \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /                 //" 	+ "\n" +
	"            //      ========`-.____`-.___\\_____/___.-`____.-'========         //" 	+ "\n" +
	"            //                           `=---='                              //" 		+ "\n" +
	"            //      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //" 		+ "\n" +
	"            //                  佛祖保佑       永不宕机     永无BUG                           //" 		+ "\n" +
	"            ////////////////////////////////////////////////////////////////////" 		+ "\n" ;

     

	public int getOrder() {
		return order;
	}


	public void setOrder(int order) {
		this.order = order;
	}

	public void init() throws InterruptedException {
		System.out.println(banner + buddha);
		//Thread.sleep(1500L);
	}

}


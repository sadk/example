package org.lsqt.components.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * 
 * @author Sky
 *
 */
public class FileWriter implements Log{
	
	//private static final int MAXED_TO_PUSH=10000;
	
	private String className;
	
	private static final String LOG_WRITER_FILE_PATH = "log.writer.file.path";
	
	private static final String LOG_WRITER_FILE_ROLL="log.writer.file.roll";
	private static final String [] LOG_WRITER_FILE_ROLL_WAY={"hours","day","week","month","quarter","year","size"}; //日志文件的滚动方式
	
	private static final String  LOG_WRITER_FILE_ROLL_SIZE="log.writer.file.roll.size";
	
	private static final String LOG_WRITER_FILE_LEVEL = "log.writer.file.level";
	
	private static final String LEVEL_DEBUG="debug";
	private static final String LEVEL_WARN="warn";
	private static final String LEVEL_INFO="info";
	private static final String LEVEL_ERROR="error";
	
	
	@SuppressWarnings("unused")
	private FileWriter(){}
	
	@SuppressWarnings("rawtypes")
	public FileWriter(Class clazz){
		this.className=clazz.getName();
	}
	
	private static boolean isNeedPushByTime(){ //按日期策略推送内容
		String ways=Configuration.getConfigMap().get(LOG_WRITER_FILE_ROLL);
		if(ways==null || "".equals(ways)) throw new RuntimeException("没有找到文件滚动设置##hours,day,week,month,quarter,year /n log.writer.file.roll=day");
		
		for(String ele : LOG_WRITER_FILE_ROLL_WAY){
			for(String e: ways.split(",")){
				if(ele.equals(e)){
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean isNeedPushBySize(){ //按文件大小策略推送内容
		String size=Configuration.getConfigMap().get(LOG_WRITER_FILE_ROLL_SIZE);
		 if(size!=null &&  !"".equals(size)){
			 return true;
		 }
		return false;
	}
	
	
	public static void writeFile(String content){
		//获取文件名
		String baseName=Configuration.getConfigMap().get(LOG_WRITER_FILE_PATH);
		String endPrix="";
		
		int index=baseName.lastIndexOf(".");
		if(index!=-1){
			endPrix=baseName.substring(index,baseName.length());
			baseName=baseName.substring(0,index);
		}
		
		//1.按日期推送文件内容
		if(isNeedPushByTime()){
				String pattern="yyyyMMddHH";
				if("day".equalsIgnoreCase(Configuration.getConfigMap().get(LOG_WRITER_FILE_ROLL))){
					pattern="yyyMMdd";
				}
				
				SimpleDateFormat df=new SimpleDateFormat(pattern);
				String lastModifyFileName=baseName.concat("_").concat(df.format(new Date())).concat(endPrix);
				
				//获取类路径
				String dir=storageResolvePath();
				File file =new File(dir+File.separator+lastModifyFileName);
				
				if(file.exists()==false){
					try {
						file.createNewFile();
						storageCreate(file,content);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{ //追加数据
					storageAppend(file,content);
				}
			return ;
		}
		
		//2.按文件大小策略推送文件内容
		if(isNeedPushBySize()){
			
		}
		
	}
	
	/**
	 * 获取日志的存储目录
	 * <pre>
	 *1.如果整个应用是web应用，并且是war包,取web根目录的同级目录
	 *2.如果整个应用是web应用，是已解开的包,取web根目录的同级目当
	 *3.如果整个应用是application应用，取classes所在的根目录同级目录
	 *
	 *其它：
	 *4.开发模式下，取应用的maven目录根目录
	 *5.junit模试下，取应用的maven目录根目录
	 *  </pre>
	 * @return
	 */
	private static String storageResolvePath(){
		String dir=FileWriter.class.getClassLoader().getResource("").getPath();
		
		//4.开发模试下，日志存放在工程目录下，可在eclipse导航窗口直接查看
		final String MAVEN_TARGET="/target";
		final String MAVEN_DEV_CLASSES="/classes/";
		
		String endFix=MAVEN_TARGET.concat(MAVEN_DEV_CLASSES);
		if (dir.contains(MAVEN_TARGET) 
				&& dir.contains(MAVEN_DEV_CLASSES)
				&& dir.contains(MAVEN_TARGET.concat(MAVEN_DEV_CLASSES))) {
			dir=dir.substring(0,dir.lastIndexOf(endFix));
			if(dir!=null && !"".equals(dir))return dir;
		}
		
		//5.junit模试
		final String MAVEN_TEST_CLASSES="/test-classes/";
		endFix=MAVEN_TARGET.concat(MAVEN_TEST_CLASSES);
		if(dir.contains(MAVEN_TARGET) 
				&& dir.contains(MAVEN_TEST_CLASSES) 
				&& dir.contains(endFix)){
			dir=dir.substring(0,dir.lastIndexOf(endFix));
			if(dir!=null && !"".equals(dir))return dir;
		}
		
		
		return dir;
	}

	
	private static void storageAppend(File file,String content) {
		java.io.FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new java.io.FileWriter(file, true);
			bw = new BufferedWriter(fw);

			// 开始输出写入文件
			//bw.newLine();
			fw.write(content);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (bw != null)
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						fw.close();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				}
		}
	}

	
	private static void storageCreate(File file,String content){
		FileOutputStream fos = null;      
		try {
			fos = new FileOutputStream(file,true);
		    FileChannel fc = fos.getChannel();

		    ByteBuffer buffer = ByteBuffer.allocate(1024*2);
		    
		    buffer.put(content.getBytes("utf-8"));
		   
		    buffer.flip();
		    fc.write(buffer);
		    
		    fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//判断哪些日志级别启用
		private static boolean assertEnabled(String level){
			Map<String,String> configMap= Configuration.getConfigMap();
			 String value=configMap.get(LOG_WRITER_FILE_LEVEL);
			 if(value!=null){
				 for(String e:value.split(",")){
					 if(level.equalsIgnoreCase(e)){
						 return true;
					 }
				 }
			 }
			 return false;
		}
		
		public boolean isDebugEnabled() {
			return assertEnabled(LEVEL_DEBUG);
		}

		public boolean isInfoEnabled() {
			return assertEnabled(LEVEL_INFO);
		}

		public boolean isWarnEnabled() {
			return assertEnabled(LEVEL_WARN);
		}

		public boolean isErrorEnabled() {
			return assertEnabled(LEVEL_ERROR);
		}
	
	public void error(String msg) {
		if(!isErrorEnabled())return ;
		writeFile(msg);
	}

	private static final String FORMAT_MSG_PREFIX=" ---- " ;
	private static final String FORMAT_PARAM_PREFIX=" 参数: [";
	private static final String FORMAT_PARAM_END="]";
	
	private static final String FORMAT_PARAM_TYPE_PREFIX=" 参数类型: [";
	private static final String FORMAT_PARAM_TYPE_END="]";
	
	private static final String FORMAT_ERROR_PREFIX="  错误: \"";
	private static final String FORMAT_ERROR_END="\"";
	
	private  String print(String level,String msg, Throwable e, Object... params){
		Date dt=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S ");
		
		StringBuilder sb=new StringBuilder();
		
		sb.append(FORMAT_MSG_PREFIX);
		
		sb.append("[".concat(level.toUpperCase()).concat("] "));
		if(LEVEL_INFO.equalsIgnoreCase(level)){
			sb.append(" ");
		}
		sb.append(df.format(dt));
		
		/**/
		sb.append(className.concat("  ").concat(msg).concat(" "));
		
		if(params!=null && params.length>0){
			sb.append(FORMAT_PARAM_PREFIX);		//参数值
			for(int i=0;i<params.length;i++){  
				if(params[i]==null){
					sb.append("null");
				}else{
					sb.append(params[i]);
				}
				
				if(i!=params.length-1){
					sb.append(",");
				}
			}
			sb.append(FORMAT_PARAM_END);
			
			
			sb.append(FORMAT_PARAM_TYPE_PREFIX);		//参数类型
			for(int i=0;i<params.length;i++){  
				if(params[i]==null){
					sb.append("null");
				}else{
					sb.append(params[i].getClass().getSimpleName());
				}
				
				if(i!=params.length-1){
					sb.append(",");
				}
			}
			sb.append(FORMAT_PARAM_TYPE_END);
		}
		
		if(e!=null){
			sb.append(FORMAT_ERROR_PREFIX).append(e.getMessage()).append(FORMAT_ERROR_END);
			StackTraceElement [] ts= e.getStackTrace();
			if(ts!=null && ts.length>0){
				int line=ts[0].getLineNumber();
				sb.append(" ("+line+")");
			}
		}
		
		if(e!=null)e.printStackTrace();
		String content=sb.toString();
		writeFile(content.concat("\n"));
		return content;
	}
	
	public void error(String msg, Object... params) {
		if(!isErrorEnabled())return ;
		print(LEVEL_ERROR,msg,null,params);
	}

	public void error(String msg, Throwable e) {
		if(!isErrorEnabled())return ;
		print(LEVEL_ERROR,msg,e);
	}

	public void error(String msg, Throwable e, Object... params) {
		if(!isErrorEnabled())return ;
		print(LEVEL_ERROR,msg,e,params);
	}

	public void info(String msg) {
		if(!isInfoEnabled())return ;
		print(LEVEL_INFO,msg,null);
	}

	public void info(String msg, Object... params) {
		if(!isInfoEnabled())return ;
		print(LEVEL_INFO,msg,null,params);
	}

	public void debug(String msg) {
		if(!isDebugEnabled())return ;
		print(LEVEL_DEBUG,msg,null);
		
	}

	public void debug(String msg, Object... params) {
		if(!isDebugEnabled())return ;
		print(LEVEL_DEBUG,msg,null,params);
	}

	public void debug(String msg, Throwable e) {
		if(!isDebugEnabled())return ;
		print(LEVEL_DEBUG,msg,e);
	}

	public void debug(String msg, Throwable e, Object... params) {
		if(!isDebugEnabled())return ;
		print(LEVEL_DEBUG,msg,e,params);
	}

	public void warn(String msg) {
		if(!isWarnEnabled())return ;
		print(LEVEL_WARN,msg,null);
	}

	public void warn(String msg, Object... params) {
		if(!isWarnEnabled())return ;
		print(LEVEL_WARN,msg,null,params);
	}

	public void warn(String msg, Throwable e) {
		if(!isWarnEnabled())return ;
		print(LEVEL_WARN,msg,e);
	}

	public void warn(String msg, Throwable e, Object... params) {
		if(!isWarnEnabled())return ;
		print(LEVEL_WARN,msg,e,params);
	}

	public static void main(String ... args){
		Log log=LogFactory.getLog(FileWriter.class);
		log.debug("aaaaaaaa");
		log.debug("aaaaaaaa","hahafdasf",5224);
		log.debug("wrwereasdfas",new RuntimeException("这是一个测试，哈哈！！！！"));
		
		/**/
		log.info("aaaaaaaa");
		log.info("aaaaaaaa","hahafdasf",5224);
		log.info("wrwereasdfas","这是一个测试要",23445,2425,43424,new Object());
		
		log.error("xxxxxxx", new RuntimeException("yyyyy"), "11111");
	}
}

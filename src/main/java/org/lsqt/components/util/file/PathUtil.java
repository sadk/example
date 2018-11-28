package org.lsqt.components.util.file;


public class PathUtil {
	/**
	 * 获取工程的布署目录
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
	public static String getAppRootDir(){
		String dir=PathUtil.class.getClassLoader().getResource("").getPath();
		
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
	
	public static void main(String args[]) {
		System.out.println(PathUtil.class.getClassLoader().getResource("").getPath());
		System.out.println(new java.io.File(getAppRootDir()));
	}
}

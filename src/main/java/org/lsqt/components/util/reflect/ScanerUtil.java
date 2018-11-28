package org.lsqt.components.util.reflect;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.lsqt.components.util.file.FileUtil;
import org.lsqt.components.util.lang.StringUtil;

/**
 * 类路径扫描工具类
 * @author Sky
 *
 */
public class ScanerUtil {
	
	private static List<File> CLASS_PATH_ARRAY = getClassPathFiles();

	private static List<File> getEnvPathFiles(String ... env) {
		List<File> rs = new ArrayList<File>();
		
		String delim = ":";
		if (System.getProperty("os.name").indexOf("Windows") != -1){
			delim = ";";
		}
		
		for(String e: env){
			String[] pathes = System.getProperty(e).split(delim);
			for (String path : pathes){
				rs.add(new File(path));
			}
		}
			
		return rs;
	}

	/**
	 * 获取应用的宿主环境下class-path类路径
	 * @return
	 */
	public static List<File> getAppClassPathFiles(){
		return getEnvPathFiles("java.class.path");
	}
	
	/**
	 * 获取web环境的classes目录下的所有class文件
	 * @return
	 */
	public static List<String> getWebClassesFiles(){
		List<String> rs = new ArrayList<String>();
		
		List<File> list = getAppClassPathFiles();
		
		//System.out.println(" ==== > 获取web环境的classes目录下的所有class文件:"+list);
		//System.out.println(" ==== > 获取当前类所在的路径："+ScanerUtil.class.getResource("/").getPath());
		
		list.add(new File(ScanerUtil.class.getResource("/").getPath()));
		
		if(list==null || list.size()==0)return null;
		
		list = skipMavenHome(list);
		
		List<String> fileInRoot = null;
		final String suffix=File.separator.concat("classes");
		for(File e: list){
			if(e.getAbsolutePath().endsWith(suffix)){
				fileInRoot = FileUtil.getDeepFiles(e);
				break;
			}
		}
		if(fileInRoot==null) return rs;
		
		final String clz = ".class";
		for(String e: fileInRoot){
			if(e.endsWith(clz)){
				rs.add(e);
			}
		}
		
		return rs;
	}

	private static List<File> skipMavenHome(List<File> list) {
		List<File> temps = new ArrayList<File>();
		for (File e: list) {
			if(!e.getAbsolutePath().contains("\\.m2\\")) {
				temps.add(e);
			}
		}
		list = temps;
		return list;
	}
	
	/**
	 * 获取JDK的类路径
	 * @return
	 */
	public static List<File> getJdkClassPathFiles(){
		List<File> rs = new ArrayList<File>();
		
		rs.addAll(getEnvPathFiles("java.ext.dirs"));
		rs.addAll(getEnvPathFiles("sun.boot.class.path"));
		
		rs = skipMavenHome(rs);
		return rs;
	}
	
	/**
	 * 获取应用的宿主环境下class-path类路径和jdk的类路径
	 * @return
	 */
	public static List<File> getClassPathFiles(){
		if(CLASS_PATH_ARRAY!=null && !CLASS_PATH_ARRAY.isEmpty())return CLASS_PATH_ARRAY;
		
		List<File> rs = new ArrayList<File>();
		
		rs.addAll(getAppClassPathFiles());
		
		//暂时去掉扫描JDK的类路径，提升扫描性能
		//rs.addAll(getJdkClassPath());
		
		rs = skipMavenHome(rs);
		return rs;
	}
	
	/**
	 * 偿试检测是否是classes文件夹路径
	 * @param path
	 * @return
	 */
	private static boolean isCanClassesDir(File path){
		if(path==null)return false;
		
		if(!path.exists()) return false;
		
		if(path.isFile()) return false;
		
		final String classes = File.separator.concat("classes");
		
		if(path.getAbsolutePath().indexOf(classes)!=-1)return true;
		
		return false;
	}
	
	/**
	 * 偿试检测是否是jar包
	 * @param path
	 * @return
	 */
	private static boolean isCanJar(File path){
		if(path == null) return false;
		if(!path.exists()) return false;
		if(!path.isFile()) return false;
		if(path.getAbsolutePath().endsWith(".jar")) return true;
		
		return false;
		
	}
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private static List<String> getOpenSourcePackage(){
		final String [] osp = new String []
		{
			"apple",
			"com.sun","com.mysql","com.oracle","com.apple","com.alibaba","com.google","com.adobe","com.microsoft","com.caucho.hessian","com.ibm","com.ckeditor",
			"freemarker","java","javax",
			"org.apache","org.springframework","org.omg","org.jcp","org.gjt","org.ietf","org.jcp","org.eclipse","org.hibernate",
			"org.xml","org.w3c","org.aopalliance","org.relaxng",
			"sun","sunw"
		};
		return Arrays.asList(osp);
	}
	
	private static boolean isCanOpenSourcePackage(String fullPackageName){
		List<String> list = getOpenSourcePackage();
		for(String e: list){
			if(fullPackageName.startsWith(e)) return true;
		}
		return false;
	}
	
	/**
	 * 解析class-path下的文件，转成类名信息
	 * @param fullPath
	 * @return
	 */
	private static List<String> resolveClassNameFromFile(String ... classFileFullPath){
		List<String> rs = new ArrayList<String>();
		
		if(classFileFullPath == null || classFileFullPath.length == 0) return rs;
		
		final String classes = "classes";
		final String innerFlag = "$";
		final String suffix = ".class";
		
		for(String path : classFileFullPath){
			if(StringUtil.isBlank(path)) continue ;
			if(!path.endsWith(suffix)) continue;
			if(path.indexOf(classes) == -1) continue;
			
			String [] temp = path.split(classes);
			if(temp==null || temp.length<2) continue;
			
			String pkg = temp[1];
			if(pkg.indexOf(innerFlag)!=-1){
				pkg = pkg.substring(0,pkg.indexOf(innerFlag));
			}
			
			if(pkg.startsWith(File.separator)){
				pkg = pkg.substring(1,pkg.length()).replace(File.separator,".");
			}
			
			if(pkg.endsWith(suffix)){
				pkg = pkg.substring(0,pkg.length()-suffix.length());
			}
			
			if(!isCanOpenSourcePackage(pkg)){
				rs.add(pkg);
			}
			
		}
		
		return rs;
	}
	
	/**
	 * 解析jar包里的文件，转成类名信息
	 * @param jarFullPath
	 * @return
	 * @throws Exception 
	 */
	private static List<String> resolveClassNameFromJar(String ... jarFullPath) throws Exception{
		List<String> rs = new ArrayList<String>();
		
		if(jarFullPath == null || jarFullPath.length==0) return rs;
		
		final String suffix = ".class";
		final String innerFlag = "$";
		
		for(String path : jarFullPath){
			if(StringUtil.isBlank(path)) continue;
			
			FileInputStream fis = new FileInputStream(path);
			JarInputStream jis = new JarInputStream(fis, false);
			JarEntry e = null;
			try{
				while ((e = jis.getNextJarEntry()) != null) {
					try{
						if(!e.getName().endsWith(suffix)) continue;
						
						String pkg = e.getName();
						if(pkg.indexOf(innerFlag)!=-1){
							pkg = pkg.substring(0,pkg.indexOf(innerFlag));
						}
						
						pkg = pkg.replace(File.separator,".");
						
						if(pkg.endsWith(suffix)){
							pkg = pkg.substring(0,pkg.length()-suffix.length());
						}

						if(!isCanOpenSourcePackage(pkg)){
							rs.add(pkg);
						}
						
					}finally{
						jis.closeEntry();
					}
				}
			}finally{
				try{
					jis.close();
				}finally{
					fis.close();
					jis = null ;
					fis = null ;
				}
			}
		}
		return rs;
	}
	
	
	
	/**
	 * 扫描系统路径
	 * @param pkgName
	 * @return
	 * @throws Exception
	 */
	public static List<String> scan(String ... basePackage) throws Exception {
		Set<String> rs = new HashSet<String>();
		
		//扫描系统
		{
			/*
			List<File> files = getClassPathFiles();
			if(files == null || files.size() == 0)return rs;
			
			for(File e: files){
				if(isCanClassesDir(e)){
					rs.addAll(resolveClassNameFromFile(e.getAbsolutePath()));
				}
				if(isCanJar(e)){
					rs.addAll(resolveClassNameFromJar(e.getAbsolutePath()));
				}
			}
			*/
			List<String> fs = getWebClassesFiles();
			if(rs!=null && fs.size()>0){
				rs.addAll(resolveClassNameFromFile(fs.toArray(new String[fs.size()])));
			}
		}
		
		//去掉开源公用包
		List<String> rst = new ArrayList<String>();
		for(String e: rs) {
			/*
			if(e.startsWith( )) {
				
			}*/
		}
		
		boolean isScanAll = false;
		if (basePackage == null || basePackage.length == 0) isScanAll = true;
		
		if (isScanAll) {
			return new ArrayList<>(rs);
		} else {
			List<String> r = new ArrayList<String>();
			for (String e : rs) {
				for (String p : basePackage) {
					if (e.startsWith(p)) {
						r.add(e);
						break;
					}
				}
			}
			return r;
		}
	}
	
	public static void main(String args[]) throws Exception{
		/*
		List<File> list = getAppClassPath();
		for(File e: list){
			System.out.println(e.getAbsolutePath());
		}
		
		List<String> t = getWebClassesFiles();
		for(String e: t){
			//System.out.println(e);
		}*/
		
		
		long start = System.currentTimeMillis();
		List<String> temp = scan();
		long end = System.currentTimeMillis();
		
		System.out.println(end-start);

		
		for(String e: temp){
			System.out.println(e);
		}
	}
}

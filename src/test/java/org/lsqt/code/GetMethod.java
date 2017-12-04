package org.lsqt.code;

import java.lang.reflect.Method;

public class GetMethod {
	   public static void main(String[] args) {
	       getMethodInfo("ttt4.Student");
	   }

	   /**
	    * 传入全类名获得对应类中所有方法名和参数名
	    */
	   @SuppressWarnings("rawtypes")
	   private static void getMethodInfo(String pkgName) {
	       try {
	           Class clazz = Class.forName(pkgName);
	           Method[] methods = clazz.getMethods();
	           for (Method method : methods) {
	               String methodName = method.getName();
	               System.out.println("方法名称:" + methodName);
	               Class<?>[] parameterTypes = method.getParameterTypes();
	               for (Class<?> clas : parameterTypes) {
	                   String parameterName = clas.getName();
	                   System.out.println("参数名称:" + parameterName);
	               }
	               System.out.println("*****************************");
	           }
	       } catch (ClassNotFoundException e) {
	           e.printStackTrace();
	       }
	   }
	}
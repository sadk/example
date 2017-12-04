package org.lsqt.code;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Arrays;
import java.util.List;

public class Student {
	    
	public void getXXX(String uName,Integer age,Double salary,Long sge,Date birthday){
		
	}
	
	protected String getZZZ(Date birthday){
		return "";
	}
	
	public static void main(String args[]) throws NoSuchMethodException, SecurityException{
		for(Method m: Student.class.getMethods()){
			List<String> params=ParameterNameUtil.getParameterNames(m);
			System.out.println(params);
			System.out.println(Arrays.asList(m.getParameterTypes()));
		}
		
	}
}

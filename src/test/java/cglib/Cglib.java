package cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

public class Cglib {
	public static void main(String[] args) {
		MyMethodInterceptor interceptor = new MyMethodInterceptor();
        
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(User.class);
        enhancer.setCallback(interceptor);
       // enhancer.setAttemptLoad(true);
      //  enhancer.setInterceptDuringConstruction(false);
        enhancer.setCallback(new MyMethodInterceptor());
        //enhancer.setCallbackFilter(new MyFilter());
        
        User user = (User)enhancer.create();
       // 
       System.out.println(user.select());
       
       //System.out.println(user.getClass().getSuperclass());
      // user.toString();
      System.out.println(user); // 为啥会报错错呢..
	}
	
	public static class MyFilter implements CallbackFilter {

		@Override
		public int accept(Method method) {
			if (method.toString().indexOf("toString") != -1) {
				System.out.println(":::::::" + method);
				return 1;
			}
			return 0;
		}
		
	}
	
	public static class Human {
		 public String select() {
		    	System.out.println("方法体内，李四");
		        return "李四";
		    }
	}
	
	public static class User extends Human{
		
		public User() {
			System.out.println("构造函数...");
		}
		
	    
	    public String select() {
	    	System.out.println("方法体内，张三");
	        return "张三";
	    }
	    
	}
	
	public static class MyMethodInterceptor implements MethodInterceptor {

		@Override
		public Object intercept(Object object, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
			System.out.println("调用前, " + method);
			Object rs = proxy.invokeSuper(object, objects);
			System.out.println("调用后, " + method);
			return rs;
		}
	    
	}
}


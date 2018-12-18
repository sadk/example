
public class Test2 {
	
		 public static void main(String[] args) {
		        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		            public void run() {
		                System.out.println("auto clean temporary file");
		            }
		        }));
		        
		        try {
		            Thread.sleep(20000);
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
		 
		    }
}


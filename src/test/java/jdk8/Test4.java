package jdk8;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
public class Test4 {
	public static void main(String[] args) throws Exception {
		String a = "xxx";
		TestX1.test(a);

		System.out.println(a);

		
		Human h = new Human("李四");
		TestX1.test(h);
		//System.out.println(h.getName());
		
		
		Clock clock = Clock.systemUTC();
		System.out.println(Clock.systemUTC().millis() +"  " + System.currentTimeMillis()+"   "+clock);
		
		/*
		Clock c2 = Clock.systemDefaultZone();
		System.out.println(c2);
		
		System.out.println(Clock.system(ZoneId.of("Asia/Shanghai")).millis());
		
		Clock c3 = Clock.fixed(Instant.now(), ZoneId.of("Asia/Shanghai"));
		System.out.println("==>"+c3.millis());
		Thread.sleep(1000);
		System.out.println("==>"+c3.millis());*/
		
		Clock c4 = Clock.offset(clock, Duration.ofSeconds(2L));
		System.out.println("====>"+clock.millis());
		System.out.println("====>"+c4.millis());
		
		Instant inst=Instant.now();
		System.out.println(inst.toEpochMilli());
		
		List<String> names = new ArrayList<>();
		names.add("张三");
		names.add("李四");
		names.add("王五");
		filter(names, (b) -> b.startsWith("张"));
	}
	
	
	public static void filter(List<String> names, Predicate<String> condition) {
		names.stream().filter((name) -> (condition.test(name))).forEach((name) -> {
			System.out.println(name + " ");
		});
	}
}

class TestX1 {
	 public static void test(String a) {
		 a = "134324";
	 }
	 
	 public static void test(Human a) {
		 a.setName("格三");
	 }
}

class Human {
	private String name;

	public Human(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

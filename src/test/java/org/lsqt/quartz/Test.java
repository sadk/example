package org.lsqt.quartz;


import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;

import java.text.SimpleDateFormat;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import static org.quartz.SimpleScheduleBuilder.*;

public class Test {
	public static void main(String[] args) throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();
		
		 // define the job and tie it to our MyJob class
		  JobDetail job = newJob(MyJob.class)
		      .withIdentity("job1", "group1")
		      .build();

		  // Trigger the job to run now, and then repeat every 40 seconds
		  Trigger trigger = newTrigger()
		      .withIdentity("trigger1", "group1")
		      .startNow()
		      .withSchedule(simpleSchedule()
		              .withIntervalInSeconds(10)
		              .repeatForever())
		      .build();

		  // Tell quartz to schedule the job using our trigger
		  scheduler.scheduleJob(job, trigger);
		  
		 // scheduler.shutdown();
	}
	
	
	public static class MyJob implements org.quartz.Job {

	    public MyJob() {
	    	
	    }

	    public void execute(JobExecutionContext context) throws JobExecutionException {
	    	System.out.println("jobRunTime:"+context.getJobRunTime());
	    	System.out.println("RefireCount:"+context.getRefireCount());
	    	System.out.println("FireInstanceId:"+context.getFireInstanceId());
	    	if(context.getNextFireTime()!=null) {
	    		System.out.println("NextFireTime:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(context.getNextFireTime()));
	    	}
	    	if(context.getPreviousFireTime()!=null) {
	    		System.out.println("PreviousFireTime:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(context.getPreviousFireTime()));
	    	}
	        System.out.println("Hello World!  MyJob is executing.");
	        System.out.println("================================================");
	    }
	}
}

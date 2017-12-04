package org.lsqt.components.mvc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.util.Assert;

public class ApplicationFilterTest {
	
	@Test
	public void isDoFilter() {
		 
		
		Pattern pattern = Pattern.compile(".*.js");
		Matcher matcher = pattern.matcher("/scripts/boot.js");
		boolean isMatch = matcher.matches();
		Assert.isTrue(isMatch);
		
		Pattern pattern2 = Pattern.compile(".*.html");
		Matcher matcher2 = pattern.matcher("index.html");
		boolean isMatch2 = matcher.matches();
		Assert.isTrue(isMatch2);
		
		Pattern pattern3 = Pattern.compile(".*/apps/.*"); 
		Matcher matcher3 = pattern.matcher("/apps/");
		Matcher matcher4 = pattern.matcher("console/apps/");
		Matcher matcher5 = pattern.matcher("console/apps");
		Matcher matcher6 = pattern.matcher("apps?id=23");
		Matcher matcher7 = pattern.matcher("apps/user?id=23");
		 
		/*
		Assert.isTrue(matcher3.matches());
		Assert.isTrue(matcher4.matches());
		Assert.isTrue(matcher5.matches());
		Assert.isTrue(matcher6.matches());
		Assert.isTrue(matcher7.matches());
		*/
	}
}

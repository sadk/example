package org.lsqt.resource;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class GetJarResource {
public static void main(String[] args) throws IOException {
	InputStream is = GetJarResource.class.getResourceAsStream("1.txt");
	System.out.println(IOUtils.toString(is));
	
	
	
}
}

package org.lsqt.components.util.file;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class IOUtil {
	
	public static String getFileContent(File file,String charset){
		FileInputStream fis = null;
		FileChannel channel = null;
		try {
			fis = new FileInputStream(file);
			channel = fis.getChannel();
			int size = (int) channel.size();

			ByteBuffer buffer = ByteBuffer.allocate(1024*1024*5); // 5M
			channel.read(buffer);

			@SuppressWarnings("unused")
			Buffer bf = buffer.flip();
			 
			
			byte[] bt = buffer.array();
			String content = (new String(bt,0,size,charset));
			
			buffer.clear();
			buffer = null;
			
			return content;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				channel.close();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return null;
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileContent(File file){
		return getFileContent(file,"utf-8");
	}
	
	public static void close(Closeable... objs) {
		if (objs != null) {
			for (Closeable o : objs) {
				try {
					if(o!=null) {
						o.close();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}

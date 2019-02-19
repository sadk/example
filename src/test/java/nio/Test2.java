package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Test2 {
	public static void main(String[] args) throws Exception {
		RandomAccessFile from = new RandomAccessFile("E:\\workspace\\example\\src\\test\\java\\dto2.json","rw");
		RandomAccessFile to = new RandomAccessFile("E:\\workspace\\example\\src\\test\\java\\dto3.json","rw");
		
		FileChannel fromChannel = from.getChannel();
		FileChannel toChannel = to.getChannel();
		long size = fromChannel.size();
		System.out.println(size);
		
		fromChannel.transferTo(0, size, toChannel);
		
		
	}
}


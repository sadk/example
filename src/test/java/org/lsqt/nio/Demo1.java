package org.lsqt.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Demo1 {
	public static void main(String[] args) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(new File("C:\\Users\\yuanmm\\Desktop\\周报模板.txt"), "rw");
		FileChannel channel = raf.getChannel();
		ByteBuffer buf = ByteBuffer.allocate(48);
		int byteRead = channel.read(buf);
		while (byteRead != -1) {
			System.out.println("Read " + byteRead);
			buf.flip();
			while (buf.hasRemaining()) {
				System.out.print((char) buf.get());
			}
			buf.clear();
			
			
			byteRead = channel.read(buf);
		}
		raf.close();
	}
}

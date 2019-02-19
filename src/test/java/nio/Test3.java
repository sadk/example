package nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;


public class Test3 {
	public static void main(String[] args) throws IOException {
		RandomAccessFile file = new RandomAccessFile("E:\\workspace\\example\\src\\test\\java\\dto2.json", "rw");
		FileChannel channel = file.getChannel();
		System.out.println(channel.isOpen());
		
		ByteBuffer bbf =ByteBuffer.allocateDirect(10);
		System.out.println(bbf.capacity());
		
		int flag = channel.read(bbf);
		while( flag !=-1 ) {
			bbf.flip();
			System.out.println(bbf.getChar());
			channel.read(bbf);
		}
		
		file.close();
		//
		//channel.
		
		 SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 6666));
		
	}
}


package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class Test4 {
	public static void main(String[] args) throws IOException {
		try (SocketChannel socketChannel = SocketChannel.open()) {
			String newData = "New String to write to file..." + System.currentTimeMillis();
			ByteBuffer bf = ByteBuffer.allocate(1024);
			bf.clear();
			bf.put(newData.getBytes("utf-8"));
			bf.flip();
			
			DatagramChannel channel = DatagramChannel.open();
			//channel.connect();
			channel.send(bf,new InetSocketAddress("localhost",80));
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
	}
}


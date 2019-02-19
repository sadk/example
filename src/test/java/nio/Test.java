package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args)  throws Exception{
		write();
	 
	}
	
	public static void read() throws Exception{
		FileInputStream fs = new FileInputStream(new File("E:\\workspace\\example\\src\\test\\java\\dto2.json"));
		FileChannel fc = fs.getChannel();
		
		ByteBuffer bb = ByteBuffer.allocate(512);
		
		int bytesRead = fc.read(bb);
		
		while (bytesRead != -1) {
 
			bb.flip();
			
			while (bb.hasRemaining()) {
				System.out.print((char)bb.get());
			}
			bb.compact();
			bytesRead = fc.read(bb);
		}
		fs.close();
	}

	public static void write() throws Exception{
		File demo = new File("E:\\workspace\\example\\src\\test\\java\\dto2.json");
		try (RandomAccessFile f = new RandomAccessFile(demo, "rw");
				FileChannel fc = f.getChannel()) {

			ByteBuffer bf = ByteBuffer.allocate(1024);
			//bf.clear();
			bf.put("我是中国人222 dfsa需要".getBytes("utf-8"));
			bf.flip();
			
			while (bf.hasRemaining()) {
				fc.write(bf);
			}
		}
		
		//fc.close();
		//f.close();
	}
}


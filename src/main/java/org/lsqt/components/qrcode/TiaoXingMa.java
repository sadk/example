package org.lsqt.components.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

/**
 * http://blog.csdn.net/yiluoak_47/article/details/22808915
 * @author yuanmm
 *
 */
public class TiaoXingMa {
		
		public static void encode(String contents, int width, int height, String imgPath) {  
	        int codeWidth = 3 + // start guard  
	                (7 * 6) + // left bars  
	                5 + // middle guard  
	                (7 * 6) + // right bars  
	                3; // end guard  
	        codeWidth = Math.max(codeWidth, width);  
	        try {  
	            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,  
	                    BarcodeFormat.EAN_13, codeWidth, height, null);  
	  
	            Path path = new File(imgPath).toPath();
	            MatrixToImageWriter.writeToPath(bitMatrix, "png",path);
	  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
		
		public static String decode(String imgPath) {  
	        BufferedImage image = null;  
	        Result result = null;  
	        try {  
	            image = ImageIO.read(new File(imgPath));  
	            if (image == null) {  
	                System.out.println("the decode image may be not exit.");  
	            }  
	            LuminanceSource source = new BufferedImageLuminanceSource(image);  
	            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));  
	  
	            result = new MultiFormatReader().decode(bitmap, null);  
	            return result.getText();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return null;  
	    }
		
	    /** 
	     * @param args 
	     */  
	    public static void main(String[] args) {
	    	/*
	        String imgPath = "d:/123.jpg";  
	        String contents = "6943620593115";  
	        int width = 105, height = 50;  
	        encode(contents, width, height, imgPath);
	    	*/
	    	String imgPath2 = "D:\\桌面\\txm.jpg";  
	        String decodeContent = decode(imgPath2);  
	        System.out.println("解码内容如下：");  
	        System.out.println(decodeContent);  
	    }

}

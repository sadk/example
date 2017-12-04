package org.lsqt.components.qrcode;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


/**
 * 文件操作工具类
 * 
 * @author CFuture.aw
 * 
 */
public class FileUtil {

	public static void appendFile(File f, String content) throws Exception {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(f, true));
			writer.write(content);
		} catch (Exception e) {
			throw e;
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void writeFile(File f, String content) throws Exception {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(f), "utf-8"));
			writer.write(content);
		} catch (Exception e) {
			throw e;
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String readFile(File f) {
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(f), "utf-8"));
			String line = null;
			while ((line = reader.readLine()) != null)
				sb.append(line);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}
	
	public static List<String> readLine(File f) {
		List<String> result = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf-8"));
			String line = null;
			while ((line = reader.readLine()) != null)
				result.add(line);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	public static String getExt(File f) {
		return f.getName().substring(f.getName().lastIndexOf(".") + 1);
	}

	public static boolean deleteFolder(File folder) {
		return deleteFolderContents(folder) && folder.delete();
	}

	/**
	 * Delete folder's children files
	 * 
	 * @param folder
	 * @return
	 */
	public static boolean deleteFolderContents(File folder) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					if (!file.delete()) {
						return false;
					}
				} else {
					if (!deleteFolder(file)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static BufferedImage getBufferedImage(String imagePath, boolean isRemote) throws Exception {
		return getBufferedImage(imagePath, isRemote, 0, 1*1000);
	}
	
	public static BufferedImage getBufferedImage(String imagePath) throws Exception {
		return getBufferedImage(imagePath, false, 0, 1*1000);
	}
	
	public static BufferedImage getBufferedImage(String imagePath, int retryTimes, long sleep) throws Exception {
		return getBufferedImage(imagePath, false, retryTimes, sleep);
	}
	/**
	 * 
	 * @param imagePath
	 *            给定的图片Path
	 * @param retryTimes
	 *            如果发生异常重试次数
	 * @param sleep
	 * @return
	 */
	public static BufferedImage getBufferedImage(String imagePath, boolean isRemote, int retryTimes, long sleep) throws Exception {
		if (imagePath == null || imagePath.trim().length() == 0)
			throw new Exception("image url can not be empty");

		int count = 0;
		while (true) {
			try {
				if (isRemote)
					return getRemote(imagePath);
				
				try {
					return getLocal(imagePath);
				} catch (Throwable e) {
					return getRemote(imagePath);
				}
			} catch (Throwable e) {
				if (count >= retryTimes) {
					throw new Exception(e);
				}
				Thread.sleep(sleep);
			}
			count++;
		}
	}

	private static BufferedImage getLocal(String imagePath) throws IOException {
		try {
			return toBufferedImage(Toolkit.getDefaultToolkit().getImage(imagePath));
		} catch (Throwable e){
			return ImageIO.read(new File(imagePath));
		}
	}

	private static BufferedImage getRemote(String imagePath)
			throws MalformedURLException, IOException {
		URL url = new URL(imagePath.replace(" ","%20"));
		try {
			return toBufferedImage(Toolkit.getDefaultToolkit().getImage(url));
		}catch (Throwable e1){
			return ImageIO.read(url);
		}
	}

	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		// This code ensures that all the pixels in the image are loaded
		image = new ImageIcon(image).getImage();

		// Determine if the image has transparent pixels; for this method's
		// implementation, see e661 Determining If an Image Has Transparent
		// Pixels
		// boolean hasAlpha = hasAlpha(image);

		// Create a buffered image with a format that's compatible with the
		// screen
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		try {
			// Determine the type of transparency of the new buffered image
			int transparency = Transparency.OPAQUE;
			/*
			 * if (hasAlpha) { transparency = Transparency.BITMASK; }
			 */

			// Create the buffered image
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			// The system does not have a screen
		}

		if (bimage == null) {
			// Create a buffered image using the default color model
			int type = BufferedImage.TYPE_INT_RGB;
			// int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
			/*
			 * if (hasAlpha) { type = BufferedImage.TYPE_INT_ARGB; }
			 */
			bimage = new BufferedImage(image.getWidth(null),
					image.getHeight(null), type);
		}

		// Copy image to buffered image
		Graphics g = bimage.createGraphics();

		// Paint the image onto the buffered image
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}

  

	/**
	 * 
	 * @param file
	 * @param isDelete
	 * @return
	 * @throws IOException
	 */
	public static boolean createFile(File file, boolean isDelete)
			throws IOException {
		boolean flag = true;
		if (file.exists()) {
			if (isDelete) {
				file.delete();
				file.createNewFile();
			} else {
				flag = false;
			}
		} else {
			file.createNewFile();
		}

		return flag;
	}

 
 
 
 
	/**
	 * 删除文件
	 * 
	 * @param file
	 * @return
	 */
	public static boolean deleteFile(File file) {
		boolean flag = false;
		if (file != null && file.exists()) {
			if (file.isDirectory()) {
				for (File f : file.listFiles()) {
					deleteFile(f);
				}
			}
			flag = file.delete();
		}

		return flag;
	}

	/**
	 * 检查文件名是否合法
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isValidFileName(String fileName) {
		if (fileName == null || fileName.length() > 255)
			return false;
		else {
			return fileName
					.matches("[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$");
		}
	}

	/**
	 * 复制文件
	 * 
	 * @param src
	 * @param dst
	 */
	public static void copy(File src, File dst) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(src);
			out = new FileOutputStream(dst);

			// Transfer bytes from in to out
			byte[] buf = new byte[1024];
			int len = -1;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return;
	}

}

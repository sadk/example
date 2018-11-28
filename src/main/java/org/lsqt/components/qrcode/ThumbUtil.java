package org.lsqt.components.qrcode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lsqt.components.util.lang.StringUtil;

import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * 缩略图工具类
 * 
 * @author weiwei
 * 
 */
public class ThumbUtil {
	public static String rebuild_path(String path,int width,int height){
		int i = path.lastIndexOf('.');
		if(StringUtil.isBlank(path) || i<0)
			return path;
		String ident = path.substring(0,i);
		String ext = path.substring(i);
		return ident+"-"+width+"x"+height+ext;
	}
	
	/**
	 * 裁剪
	 * <pre>
	 * 自动添加文件后缀 
	 *    _{长度}x{宽度}
	 *    
	 * 比如     xxx.jpg -> xxx_120x120.jpg
	 * </pre>
	 * @param src_path 原图地址，可以本地，可以远程
	 * @param dest 目标文件
	 * @param top 截图的左顶点距离原图左顶点的高度
	 * @param left 截图的做顶点距离原图做顶点的宽度
	 * @param width 截图宽度
	 * @param height 截图高度
	 * @throws Exception
	 */
    public static String crop(String src, String dest, int top, int left, int width, int height, boolean is_rename) throws Exception{
    	int x1 = left;
		int x2 = x1 + width;
		int y1 = top;
		int y2 = y1 + height;
		
		String file_name = dest;
		if (is_rename) {
			file_name = rebuild_path(file_name, width, height);
		}
		//自动创建文件夹
		File destFile = new File(file_name);
		if(!destFile.getParentFile().exists())
			destFile.getParentFile().mkdirs();
		
    	ThumbUtil.build(src, 0, 0, 0, x1, y1, x2, y2).toFile(destFile);
    	
    	return destFile.getAbsolutePath();
	}
    
    public static String crop(File src, File dest, int top, int left, int width, int height, boolean is_rename) throws Exception{
    	return crop(src.getAbsolutePath(), dest.getAbsolutePath(), top, left, width, height, is_rename);
    }
    
    public static String crop(File src, String dest, int top, int left, int width, int height, boolean is_rename) throws Exception{
    	return crop(src.getAbsolutePath(), dest, top, left, width, height, is_rename);
    }
    
    public static String crop(String src, File dest, int top, int left, int width, int height, boolean is_rename) throws Exception{
    	return crop(src, dest.getAbsolutePath(), top, left, width, height, is_rename);
    }

	/**
	 * 压缩图片
	 * <pre>
	 * 自动添加文件后缀 
	 *    _{长度}x{宽度}
	 *    
	 * 比如     xxx.jpg -> xxx_120x120.jpg
	 * </pre>
	 * @param src_path 原图地址，可以本地，可以远程
	 * @param dest 目标文件
	 * @param width 缩略图宽度
	 * @param height 缩略图高度
	 * @param is_crop 是否裁剪
	 * @throws Exception
	 */
	public static String resize(String src_path, String dest, int width, int height, boolean is_crop, boolean is_rename) throws Exception{
		
		final String srcImgPath = src_path;
		
		int sharpenTimes = 0;// 输出锐化次数
		float quality = 1f;// 输出质量
		String outputFormat = "jpg";// 输出格式
		int outputWidth = width;// 输出宽度
		int outputHeight = height;// 输出高度
		boolean isCrop = is_crop;//是否裁剪图片
		float contrast = 0f; // 对比度 0 表示不调整
		float brightness = 0f; // 亮度 0 表示不调整
		
		FileOutputStream fos = null;
		try {
			//开始压缩裁剪
			BufferedImage bi = ThumbUtil.generate(
					srcImgPath,
					sharpenTimes,
					quality, contrast, brightness, outputFormat, 10, // 远程图片下载失败重试次数
					1 * 1000, // 失败后休眠时间
					outputWidth, outputHeight,
					isCrop);
			String file_name = dest;
			if (is_rename) {
				file_name = rebuild_path(file_name, width, height);
			}
			
			//自动创建文件夹
			File destFile = new File(file_name);
			if(!destFile.getParentFile().exists())
				destFile.getParentFile().mkdirs();
			
			fos = new FileOutputStream(destFile);
			boolean isOK = ImageIO.write(bi, outputFormat, fos);
			bi.flush();
			bi = null;
			if (!isOK)
				throw new Exception("create image fail ");
			
			return destFile.getAbsolutePath();
		} catch (Throwable e) {
			throw new Exception(e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String resize(File src, File dest, int width, int height, boolean is_crop, boolean is_rename) throws Exception{
    	return resize(src.getAbsolutePath(), dest.getAbsolutePath(), width, height, is_crop, is_rename);
    }
	
	public static String resize(String src, File dest, int width, int height, boolean is_crop, boolean is_rename) throws Exception{
    	return resize(src, dest.getAbsolutePath(), width, height, is_crop, is_rename);
    }
	
	public static String resize(File src, String dest, int width, int height, boolean is_crop, boolean is_rename) throws Exception{
    	return resize(src.getAbsolutePath(), dest, width, height, is_crop, is_rename);
    }
	
	
	public static ByteArrayOutputStream generateThumb(String imagePath, String outputFormat, int failRetryTimes, long sleep, int outputWidth, int outputHeight) throws Exception {
		return generateThumb(imagePath, 0, 0.9f, 0, 0, outputFormat, failRetryTimes, sleep, outputWidth, outputHeight, false);
	}
	
	public static ByteArrayOutputStream generateThumb(String imagePath, String outputFormat, int failRetryTimes, long sleep, int outputWidth, int outputHeight, boolean isCrop) throws Exception {
		return generateThumb(imagePath, 0, 0.9f, 0, 0, outputFormat, failRetryTimes, sleep, outputWidth, outputHeight, isCrop);
	}
	
	public static ByteArrayOutputStream generateThumb(String imagePath,
			int sharpenTimes,
			float quality, float contrast, float brightness,
			String outputFormat, int failRetryTimes, long sleep,
			int outputWidth, int outputHeight) throws Exception {
		return generateThumb(imagePath, sharpenTimes, quality, contrast, brightness, outputFormat, failRetryTimes, sleep, outputWidth, outputHeight, false);
	}
	
	public static ByteArrayOutputStream generateThumb(String imagePath,
			int sharpenTimes,
			float quality, float contrast, float brightness,
			String outputFormat, int failRetryTimes, long sleep,
			int outputWidth, int outputHeight, boolean isCrop) throws Exception {
		BufferedImage img = generate(imagePath, sharpenTimes, quality, contrast, brightness, outputFormat, failRetryTimes, sleep, outputWidth, outputHeight, isCrop);
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(img, outputFormat, os);
		return os;
	}
	
	public static Builder<BufferedImage> build(
			String imagePath, 
			int sharpenTimes,
			float contrast, 
			float brightness, 
			int x1, int y1, int x2, int y2) throws Exception{
		if (imagePath == null || imagePath.trim().length() == 0)
			throw new Exception("ImageURL required");

		int	failRetryTimes = 1;


		BufferedImage bi = null;
		try {
			bi = FileUtil.getBufferedImage(imagePath, failRetryTimes, 1*1000);
		} catch (Exception e) {
			throw e;
		}

		if (bi == null)
			throw new Exception("can not get the image file from -> "
					+ imagePath);

		// 锐化
		if (sharpenTimes > 0)
			bi = sharpen(bi, sharpenTimes);

		// 对比度、亮度过滤
		/*
		if (contrast > 0 || brightness > 0) {
			ContrastFilter filter = new ContrastFilter();
			if (contrast > 0)
				filter.setContrast(contrast);
			if (brightness > 0)
				filter.setBrightness(brightness);
			bi = filter.filter(bi, null);
		}
*/
		// scale必须为 1 的时候图片才不被放大
		return Thumbnails
				.of(bi)
				.scale(1)
				.sourceRegion(x1, y1, x2-x1, y2-y1);
	}
	
	public static BufferedImage generate(String imagePath,
			int sharpenTimes,
			float quality, float contrast, float brightness,
			String outputFormat, int failRetryTimes, long sleep,
			int outputWidth, int outputHeight) throws Exception {
		return generate(imagePath, sharpenTimes, quality, contrast, brightness, outputFormat, failRetryTimes, sleep, outputWidth, outputHeight, false);
	}
	/**
	 * 注意！当宽度和高度都给定且isCrop=true的情况下会进行裁剪。裁剪规则是：先按照比例压缩，然后将多出的部分分两边裁剪。
	 * 
	 * @param imagePath
	 *            图片path，如果是以 http:// || https:// 开头则认为是远程图片
	 * @param sharpenTimes 锐化次数
	 * @param quality 质量 0f-1.0f
	 * @param contrast
	 *            对比度 默认1.2f
	 * @param brightness
	 *            亮度 默认 1.0f
	 * @param outputFormat
	 *            希望生成的缩略图格式
	 * @param failRetryTimes
	 *            图片获取失败尝试次数
	 * @param sleep
	 *            重试间隔时间 单位 毫秒
	 * @param outputWidth
	 *            希望生成的缩略图宽度
	 * @param outputHeight
	 *            希望生成的缩略图高度
	 * @param isCrop 
	 * 			是否要裁剪图片
	 */
	public static BufferedImage generate(String imagePath,
			int sharpenTimes,
			float quality, float contrast, float brightness,
			String outputFormat, int failRetryTimes, long sleep,
			int outputWidth, int outputHeight,
			boolean isCrop) throws Exception {
		
		if (imagePath == null || imagePath.trim().length() == 0)
			throw new Exception("ImageURL required");

		if (outputFormat == null || outputFormat.trim().length() == 0)
			outputFormat = imagePath.substring(imagePath.lastIndexOf(".") + 1,
					imagePath.length());

		if (outputFormat == null || outputFormat.trim().length() == 0)
			throw new Exception("can not get the image suffix -> " + imagePath);

		if (failRetryTimes <= 0)
			failRetryTimes = 1;

		final String W = "width";
		final String H = "height";

		BufferedImage bi = null;
		try {
			bi = FileUtil.getBufferedImage(imagePath, failRetryTimes, sleep);
		} catch (Exception e) {
			throw e;
		}

		if (bi == null) {
//			bi = ImageIO.read(imagePath);
			bi = Thumbnailator.createThumbnail(new File(imagePath), 1499, 1499);
		}
		
		if (bi == null)
			throw new Exception("can not get the image file from -> " + imagePath);

		//原图大小
		int sw = bi.getWidth();
		int sh = bi.getHeight();
		
		/*
		 * :如果给定了目标宽度和高度，且isCrop=false，即不进行裁剪的情况下，使用以下算法:
		 * 如果原图宽高比大于等于目标宽高比，则使用宽度优先压缩，高度按压缩比例进行缩放
		 * 如果原图宽高比小于目标宽高比，则使用高度优先压缩，宽度按压缩比例进行缩放
		 */
		if (outputWidth > 0 && outputHeight > 0 && !isCrop) {
			double sourceScale = Double.parseDouble(String.valueOf(sw))/Double.parseDouble(String.valueOf(sh));
			double outputScale = Double.parseDouble(String.valueOf(outputWidth))/Double.parseDouble(String.valueOf(outputHeight));
			if (sourceScale >= outputScale){
//				return generate(imagePath, sharpenTimes, quality, contrast, brightness, outputFormat, failRetryTimes, sleep, outputWidth, 0, false);
				outputHeight = 0;
			}else{
//				return generate(imagePath, sharpenTimes, quality, contrast, brightness, outputFormat, failRetryTimes, sleep, 0, outputHeight, false);
				outputWidth = 0;
			}
		}
		
		// 如果原图比目标长宽要少，用原图大小,这样就不会进行放大了
		if (sw < outputWidth)
			outputWidth = sw;
		if (sh < outputHeight)
			outputHeight = sh;

		// 原图宽高
		final Map<String, Integer> source = new HashMap<String, Integer>();
		source.put(W, sw);
		source.put(H, sh);

		// 如果给出的长宽不大于0的话，用原图大小
		if (outputWidth <= 0 && outputHeight <= 0) {
			outputWidth = sw;
			outputHeight = sh;
		}

		// 目标宽高
		final Map<String, Integer> output = new HashMap<String, Integer>();
		if (outputWidth > 0)
			output.put(W, outputWidth);

		if (outputHeight > 0)
			output.put(H, outputHeight);
		
		// 比较W与H，找出小的，记住小的那个
		String min = W;
		if (sh < sw)
			min = H;
		// 如果小值不存在，则小值取给过来的其中一个值
		if (!output.containsKey(min)) {
			if (output.containsKey(W))
				min = W;
			else
				min = H;
		}

		// 锐化
		if (sharpenTimes > 0)
			bi = sharpen(bi, sharpenTimes);

		// 对比度、亮度过滤
		/*if (contrast > 0 || brightness > 0) {
			ContrastFilter filter = new ContrastFilter();
			if (contrast > 0)
				filter.setContrast(contrast);
			if (brightness > 0)
				filter.setBrightness(brightness);
			bi = filter.filter(bi, null);
		}*/

		// 如果给了两个参数，且isCrop=true则剪裁
		if (output.containsKey(W) && output.containsKey(H) && isCrop) {
			
			// 裁剪的话因为要保留最大区域所以按照比例最小的那端进行裁剪
			double scale ;
			double wScale = Double.parseDouble(String.valueOf(source.get(W))) / Double.parseDouble(String.valueOf(output.get(W)));
			double hScale = Double.parseDouble(String.valueOf(source.get(H))) / Double.parseDouble(String.valueOf(output.get(H)));
			if (wScale < hScale)
				scale = wScale;
			else
				scale = hScale;
			
			int sW = new Double(source.get(W) / scale).intValue();
			int sH = new Double(source.get(H) / scale).intValue();
			
			BufferedImage _bi = Thumbnails.of(bi).size(sW, sH).outputFormat(outputFormat).asBufferedImage();

			// scale必须为 1 的时候图片才不被放大
			return Thumbnails
					.of(_bi)
					.scale(1)
					.sourceRegion(Positions.CENTER, output.get(W),
							output.get(H)).outputQuality(quality)
					.outputFormat(outputFormat).asBufferedImage();

		} 
		
		// 算出比例
		double scale = Double.parseDouble(String.valueOf(source.get(min))) / Double.parseDouble(String.valueOf(output.get(min)));
		int sW = new Double(sw / scale).intValue();
		int sH = new Double(sh / scale).intValue();
		
		// 压缩
		return Thumbnails.of(bi).size(sW, sH).outputQuality(quality).outputFormat(outputFormat).asBufferedImage();
	}

	//锐化
	public static BufferedImage sharpen(BufferedImage src) {
		return sharpen(src, 2);
	}
	
	public static BufferedImage sharpen(BufferedImage src, int times) {
        BufferedImage desc = null;
      //  SharpenFilter filter = new SharpenFilter();
     //   filter.setUseAlpha(true); 
      /*  desc = filter.filter(src, desc);
        for (int i = 0; i < times - 1; i++) {
            desc = filter.filter(desc, desc);
        }
        filter = null;
 */
        return null;
    }
	/**
	 * 使用绘制的方式去掉图像的alpha值
	 * @param $image
	 * @param $bgColor
	 * @return
	 */
//	public static BufferedImage removeAlpha(BufferedImage image, Color bgColor)
//	{
//	    int $w = image.getWidth();
//	    int $h = image.getHeight();
//	    BufferedImage __image = new BufferedImage($w, $h, BufferedImage.TYPE_INT_RGB);
//	    Graphics2D __graphic = __image.createGraphics();
//	    __graphic.setColor(bgColor);
//	    __graphic.fillRect(0,0,$w,$h);
//	    __graphic.drawRenderedImage(image, null);
//	    __graphic.dispose();
//	    return __image; 
//	}
/*
	public static void main(String[] args) throws Exception {
		// 原图，也可以是本地的d:/xx.jpg
//		String remoteImageUrl = "http://gd.image-gmkt.com/mi/830/443/414443830.jpg";
//		String remoteImageUrl = "http://www.shoplay.com/cache/bigpic/20121130/470/aaeed8a8dd_w470.jpg";
//		String remoteImageUrl = "http://www.malijuthemeshop.com/live_previews/mws-admin/example/scottwills_squirrel.jpg";
//		String remoteImageUrl = "http://static.sg.groupon-content.net/88/75/1357633937588.png";
//		String remoteImageUrl = "http://coupree.com/image/ke7VpHtYCBwg6rCF.png/301/174";
//		String remoteImageUrl = "http://www.featcher.sg/deal_images/wall street cafe/web3.jpg";
//		String remoteImageUrl = "http://www.moxdeals.sg/images/Ace%20Tours%20&%20Travels/Ace%20Tours%20Logo.jpg";
//		String remoteImageUrl = "http://bestbargain.com.sg/static/team/2012/1226/13564943221081.jpg";
//		String remoteImageUrl = "http://bestbargain.com.sg/static/team/2012/1226/13564943221081.jpg";
//		String remoteImageUrl = "d:/13588489445010.jpg";
//		String remoteImageUrl = "http://www.iconpng.com/png/bluegray-icons/file-png.png";
//		String remoteImageUrl = "http://shoppetreatz.com/wp-content/themes/DailyDeal/thumb.php?src=http://shoppetreatz.com/wp-content/uploads/2012/11/13541581271295634499.jpg&w=560&h=280&zc=1&q=80&bid=1";
//		String remoteImageUrl = "http://gd.image-gmkt.com/mi/207/546/419546207.jpg";
//		String remoteImageUrl = "http://test.shoplay.com/cache/bigpic/20121108/470/55c5b78e5c_w470.jpg";
//		String remoteImageUrl = "http://www.imm.sg/resources/imm/images/common/logo.png";
//		String remoteImageUrl = "http://www.marinasquare.com.sg/images/msq//tenants/arte.jpg";
		String remoteImageUrl = "http://www.moxdeals.sg/storage/32000/1682/a7caf8be7675fba0f932f0eeeb84e1d7.jpg";
		int sharpenTimes = 0;// 锐化次数
		float quality = 1f;// 质量
		String outputFormat = "jpg";// 输出格式
		String name = CommonUtil.getNowTime("yyyyMMddHHmmss");
		int outputWidth = 400;// 宽度
		int outputHeight = 306;// 高度
		boolean isCrop = false;//是否裁剪图片
		float contrast = 0f; // 对比度
		float brightness = 0f; // 亮度 0 表示不调整

		File file = new File("d:/" + name + "_w" + outputWidth + "h" + outputHeight + "_sharpen" + sharpenTimes + "_contrat" + contrast + "_quality"+quality + "." + outputFormat);
		
		BufferedImage image = ThumbUtil.generate(
				remoteImageUrl, 
				sharpenTimes,
				quality, contrast, brightness, outputFormat, 10, // 远程图片下载失败重试次数
				1 * 1000, // 失败后休眠时间
				outputWidth, outputHeight,
				isCrop);
		
		boolean isOK = ImageIO.write(image, outputFormat, new FileOutputStream(file));
		if (!isOK)
			throw new Exception("create image fail ");
		
		File _f = new File(file.getAbsolutePath());
		System.out.println("generate file -> " + _f.getAbsolutePath() + " " + _f.exists());
	}*/

	
	public static void main(String[] args) throws Exception{
		
		/*String pdf = "d:/tt.jpeg";
		BufferedImage bi = ImageIO.read(new File(pdf));
		System.out.println(bi);
		*/
		
		
		String path = "/Users/yuanke/Documents/qhee/workspace/qhee-mobile-server/20150312132002_Screenshots.png";//也可以本地图片路径
		int width = 95;
		int height = 95;
		int left = 118;
		int top = 98;
		boolean is_rename = true;		
		ThumbUtil.crop(path, new File("/Users/yuanke/Documents/qhee/workspace/qhee-mobile-server/d4.png"), top, left, width, height, is_rename);
		//ThumbUtil.resize(path, "/Users/yuanke/Documents/qhee/workspace/qhee-mobile-server/d2.jpg", 50, 50, false, false);
	}
	
}

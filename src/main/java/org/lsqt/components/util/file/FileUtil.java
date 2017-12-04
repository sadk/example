package org.lsqt.components.util.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	
	private static List<String> getDeepFiles(File file,List<String> resultFileName){
        File[] files = file.listFiles();
        if(files==null)return resultFileName;// 判断目录下是不是空的
        for (File f : files) {
            if(f.isDirectory()){// 判断是否文件夹
                resultFileName.add(f.getPath());
                getDeepFiles(f,resultFileName);// 调用自身,查找子目录
            }else
                resultFileName.add(f.getPath());
        }
        return resultFileName;
    }
	
	/**
	 * 获取指定目录下所有文件夹和文件
	 * @param root
	 * @return
	 */
	public static List<String> getDeepFiles(File root){
		List<String> list=new ArrayList<String>();
		return getDeepFiles(root,list);
	}

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}
}

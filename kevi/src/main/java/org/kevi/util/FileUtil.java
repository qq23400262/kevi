package org.kevi.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文件处理类
 * 
 * @author 422575
 * 
 */
public class FileUtil {
	/**
	 * 获取目录内文件名清单（不包含目录）
	 * 
	 * @return
	 */
	public static List<String> getFileNameList(String filePath) {
		List<String> fileNames = new ArrayList<String>();
		File file = new File(filePath);
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (!files[i].isDirectory()) {
				fileNames.add(files[i].getName());
			}
		}
		return fileNames;
	}

	/**
	 * 按行读取文件，把行存到list返回
	 * 
	 * @param filePath
	 * @return
	 */
	public static List<String> readFile(String filePath) {
		File file = new File(filePath);
		List<String> list = new ArrayList<String>();
		try {
			BufferedReader bw = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = bw.readLine()) != null) {
				list.add(line);
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static String readFileToStr(String filePath, String charsetName) {
		String result = "";
		int i = 10000;
		try {
			 BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(filePath),charsetName)); 
			String line = null;
			while ((line = br.readLine()) != null && i-- > 0) {
				result+=line+"\n";
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 读取文件修改时间
	 * 
	 * @param filePath
	 * @return
	 */
	public static Date getModifiedTime(String filePath) {
		File f = new File(filePath);
		long time = f.lastModified();
		Date date = new Date(time);
		return date;
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
//			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
//					bytesum += byteread; // 字节数 文件大小
//					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				fs.close();
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();

		}

	}
	
	/**
	 * 删除文件
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFile(String filePath) {
		File f = new File(filePath);
		if(f.delete()) {
			return true;
		} else {
			return false;
		}
	}
	/** 
     * 检查指定的文件路径，如果文件路径不存在，则创建新的路径， 文件路径从根目录开始创建。 
     *  
     * @param filePath 
     * @return 
     */  
    public static boolean createPath(String filePath) {  
        if (filePath == null || filePath.length() == 0)  
            return false;  
  
        // 路径中的\转换为/  
        filePath = filePath.replace('\\', '/');  
        // 处理文件路径  
        String[] paths = filePath.split("/");  
  
        // 处理文件名中没有的路径  
        StringBuilder sbpath = new StringBuilder();  
        for (int i = 0, n = paths.length; i < n; i++) {  
            sbpath.append(paths[i]);  
            // 检查文件路径如果没有则创建  
            File ftmp = new File(sbpath.toString());  
            if (!ftmp.exists()) {  
                ftmp.mkdir();  
            }  
  
            sbpath.append("/");  
        }  
  
        return true;  
    }
    
	public static boolean writeFile(String fileName, String content, boolean append) {
		if (fileName == null || fileName.length() == 0)  
            return false;  
        if (content == null)  
            return false;
     // 路径中的\转换为/  
        fileName = fileName.replace('\\', '/');  
        // 处理文件路径  
        createPath(fileName.substring(0, fileName.lastIndexOf('/')));  
        
		File f = new File(fileName);
		if(!f.exists()) {
			try {
				f.createNewFile();
				System.out.println("create file:"+fileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("create file faild:"+fileName + e.getMessage());
			}
		}
		if(!f.exists()) {
			System.out.println("file is not exists");
			return false;
		}
		FileOutputStream fs = null;
		OutputStreamWriter out = null;
		try {
			fs = new FileOutputStream(f,append);
			out = new OutputStreamWriter(fs,"utf-8");
			out.write(content);
			out.close();
			fs.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (fs != null) {
					fs.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return false;
		
	}
}

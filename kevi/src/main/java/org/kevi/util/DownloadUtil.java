package org.kevi.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import org.kevi.map.MapToolApp;

public class DownloadUtil {

	private static boolean isProxy = false;
	private static String proxyAddress = "";
	private static int proxyPort = 80;	
	private static MapToolApp mapToolApp;
	
	public static void setMapToolApp(MapToolApp mapToolApp0) {
		if(mapToolApp==null)
			mapToolApp = mapToolApp0;
	}
	
	public static void showInfo(String info) {
		if(mapToolApp != null) {
			mapToolApp.showInfo(info, true);
		}
	}

	/**
	 * 
	 * 功能描述：<br>
	 * 
	 * @param url       需要下载的地址
	 * @param savepath  保存文件路径，全路径包括扩展名
	 * @param isOveride 存在的文件是否覆盖   true覆盖   false不覆盖
	 * @throws Exception
	 * @return void
	 * 
	 * 修改记录:
	 */
	public static void download(String url, String savepath, boolean isOveride) throws Exception{
		File file = new File(savepath);
		if(!file.exists()){
			File parentDir = new File(file.getParent());
			if(!parentDir.exists()){
				parentDir.mkdirs();
			}
		}else{
			if(!isOveride){
				return;
			}
		}
		URL u = new URL(url);
		URLConnection connection = null;
		if(isProxy){
			connection = u.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyAddress, proxyPort)));
		}else{
			connection = u.openConnection();
		}
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:5.0.1) Gecko/20100101 Firefox/5.0.1");
		connection.setRequestProperty("Accept-Language", "zh-cn,en-US;q=0.5");
		
		try{
			connection.connect();
			InputStream is = connection.getInputStream();
			OutputStream os = new FileOutputStream(file);
			int len = -1;
			byte[] data = new byte[3 * 1024];
			while((len = is.read(data)) != -1){
				os.write(data, 0, len);
			}
			is.close();
			os.close();
		} catch (FileNotFoundException e) {
			showInfo("下载["+savepath+"]失败:"+e.getMessage()+" url=" + url);
			FileUtil.deleteFile(savepath);
			//e.printStackTrace();
		} catch (Exception e) {
			showInfo("下载["+savepath+"]失败:"+e.getMessage()+" url=" + url);
			FileUtil.deleteFile(savepath);
			//e.printStackTrace();
		}
	}
	
	public static void setProxy(boolean isProxy) {
		DownloadUtil.isProxy = isProxy;
	}
	public static void setProxyAddress(String proxyAddress) {
		DownloadUtil.proxyAddress = proxyAddress;
	}
	public static void setProxyPort(int proxyPort) {
		DownloadUtil.proxyPort = proxyPort;
	}
}
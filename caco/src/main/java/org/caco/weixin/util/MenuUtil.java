package org.caco.weixin.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

public class MenuUtil {
	/**
	 * 获得ACCESS_TOKEN @Title: getAccess_token @Description:
	 * 获得ACCESS_TOKEN @param @return 设定文件 @return String 返回类型 @throws
	 */
	private static String getAccess_token(String appID, String appSecret) {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appID + "&secret="
				+ appSecret;
		String accessToken = null;
		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();

			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();

			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");

			JSONObject demoJson = new JSONObject(message);
			accessToken = demoJson.getString("access_token");

			System.out.println(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accessToken;
	}

	/**
	 * 创建Menu @Title: createMenu @Description:
	 * 创建Menu @param @return @param @throws IOException 设定文件 @return int
	 * 返回类型 @throws
	 */
	public static String createMenu(String menuJson, String appID, String appSecret) {
		// 此处改为自己想要的结构体，替换即可
		String access_token = getAccess_token(appID, appSecret);
		String action = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + access_token;
		try {
			URL url = new URL(action);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();

			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			OutputStream os = http.getOutputStream();
			os.write(menuJson.getBytes("UTF-8"));// 传入参数
			os.flush();
			os.close();

			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			return "Result info:" + message;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "CreateMenu faild!";
	}

	/**
	 * 删除当前Menu @Title: deleteMenu @Description: 删除当前Menu @param @return
	 * 设定文件 @return String 返回类型 @throws
	 */
	public static String deleteMenu(String appID, String appSecret) {
		String access_token = getAccess_token(appID, appSecret);
		String action = "https://api.weixin.qq.com/cgi-bin/menu/delete? access_token=" + access_token;
		try {
			URL url = new URL(action);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();

			http.setRequestMethod("GET");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			OutputStream os = http.getOutputStream();
			os.flush();
			os.close();

			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			return "deleteMenu返回信息:" + message;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "deleteMenu 失败";
	}

	public static void main(String[] args) {
		//for test
		String menuJson = "{\"button\":[{\"name\":\"系统功能\",\"sub_button\":[{\"type\":\"scancode_waitmsg\",\"name\":\"扫码带提示\",\"key\":\"rselfmenu_0_0\",\"sub_button\":[]},{\"type\":\"scancode_push\",\"name\":\"扫码推事件\",\"key\":\"rselfmenu_0_1\",\"sub_button\":[]},{\"name\":\"发送位置\",\"type\":\"location_select\",\"key\":\"rselfmenu_2_0\"	}]},{\"name\":\"发图\",\"sub_button\":[{\"type\":\"pic_sysphoto\",\"name\":\"系统拍照发图\",\"key\":\"rselfmenu_1_0\",\"sub_button\":[]},{\"type\":\"pic_photo_or_album\",\"name\":\"拍照或者相册发图\",\"key\":\"rselfmenu_1_1\",\"sub_button\":[]},{\"type\":\"pic_weixin\",\"name\":\"微信相册发图\",\"key\":\"rselfmenu_1_2\",\"sub_button\":[]},{\"type\":\"view\",\"name\":\"Home\",\"url\":\"http://testyun.cn:8080/caco\"},{\"type\":\"click\",\"name\":\"赞一下我们\",\"key\":\"V1001_GOOD\"}]},{\"type\":\"click\",\"name\":\"今日要闻\",\"key\":\"V1001_TODAY_MUSIC\"}]}";
		String appID = "";
		String appSecret = "";
		System.out.println(createMenu(menuJson, appID, appSecret));
	}
}
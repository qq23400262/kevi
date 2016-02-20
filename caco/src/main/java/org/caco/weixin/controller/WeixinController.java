package org.caco.weixin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.caco.weixin.aes.AesException;
import org.caco.weixin.aes.WXBizMsgCrypt;
import org.caco.weixin.services.CoreService;
import org.caco.weixin.util.MenuUtil;
import org.caco.weixin.util.SHA1;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author caco
 */
@Controller
@RequestMapping("/weixin/")
public class WeixinController {
	/** 
     *  与开发模式接口配置信息中的Token保持一致 
     */  
    private static String token = "Caco20160219token";
    /** 
     * 微信生成的 ASEKey 
     */  
    private static String encodingAESKey ="JUow4htkvbdS0m2oIzsBRGuNjMj3ve5uU4FF9yZdDTT";  
    /** 
     * 应用的AppId 
     */  
    private static String appID="wx3205ac2c498336a4";
    private static String appSecret = "72586c39ab471e8ffe536e3162613c40";
    
	@RequestMapping(value = { "/signature" }, method = RequestMethod.GET)
	public void signatureFroGet(@RequestParam(value = "signature", required = true) String signature,
			@RequestParam(value = "timestamp", required = true) String timestamp,
			@RequestParam(value = "nonce", required = true) String nonce,
			@RequestParam(value = "echostr", required = true) String echostr, 
			HttpServletResponse response)
					throws IOException {
		//test utl: http://120.25.122.7/caco/weixin/signature?timestamp=&nonce=&echostr=&signature=
		System.out.println(signature + "," + token + "," + timestamp + "," + nonce + "," + echostr);
		String[] values = { token, timestamp, nonce };
		Arrays.sort(values); // 字典序排序
		String value = values[0] + values[1] + values[2];
		String sign = SHA1.encode(value);
		PrintWriter writer = response.getWriter();
		if (signature.equals(sign)) {// 验证成功返回ehcostr
			writer.print(echostr);
		} else {
			writer.print("error");
		}
		writer.flush();
		writer.close();
		
		/*以下是sample里的代码，但出错了，目前还没找出原因
		 * 
		// 解析出url上的参数值如下：
		String sEchoStr; //需要返回的明文
		PrintWriter writer = response.getWriter();
		try {
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAESKey, appId);
			
			sEchoStr = wxcpt.VerifyURL(signature, timestamp,nonce, echostr);
			// 验证URL成功，将sEchoStr返回
			writer.print(sEchoStr);
			System.out.println(echostr+"=="+sEchoStr);
		} catch (Exception e) {
			//验证URL失败，错误原因请查看异常
			e.printStackTrace();
			writer.print(e.getMessage());
		} finally {
			writer.flush();
			writer.close();
		}*/
	}
	
	@RequestMapping(value = { "/createMenu" }, method = RequestMethod.GET)
	public void createMenu(HttpServletResponse response, HttpServletRequest request)throws IOException  {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String menuJson = "{\"button\":[{\"name\":\"系统功能\",\"sub_button\":[{\"type\":\"scancode_waitmsg\",\"name\":\"扫码带提示\",\"key\":\"rselfmenu_0_0\",\"sub_button\":[]},{\"type\":\"scancode_push\",\"name\":\"扫码推事件\",\"key\":\"rselfmenu_0_1\",\"sub_button\":[]},{\"name\":\"发送位置\",\"type\":\"location_select\",\"key\":\"rselfmenu_2_0\"	}]},{\"name\":\"发图\",\"sub_button\":[{\"type\":\"pic_sysphoto\",\"name\":\"系统拍照发图\",\"key\":\"rselfmenu_1_0\",\"sub_button\":[]},{\"type\":\"pic_photo_or_album\",\"name\":\"拍照或者相册发图\",\"key\":\"rselfmenu_1_1\",\"sub_button\":[]},{\"type\":\"pic_weixin\",\"name\":\"微信相册发图\",\"key\":\"rselfmenu_1_2\",\"sub_button\":[]},{\"type\":\"view\",\"name\":\"Home\",\"url\":\"http://testyun.cn:8080/caco\"},{\"type\":\"click\",\"name\":\"赞一下我们\",\"key\":\"V1001_GOOD\"}]},{\"type\":\"click\",\"name\":\"今日要闻\",\"key\":\"V1001_TODAY_MUSIC\"}]}";
		String result = MenuUtil.createMenu(menuJson, appID, appSecret);
		// 响应消息
		PrintWriter out = response.getWriter();
		out.print(result);
		out.close();
	}
	
	@RequestMapping(value = { "/signature" }, method = RequestMethod.POST)
	public void signatureForPost(HttpServletResponse response, HttpServletRequest request)
					throws IOException {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 微信加密签名
		String msg_signature = request.getParameter("msg_signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		//从请求中读取整个post数据
		InputStream inputStream = request.getInputStream();
		String postData = IOUtils.toString(inputStream, "UTF-8");
		String msg = "";
		WXBizMsgCrypt wxcpt = null;
		try {
			wxcpt = new WXBizMsgCrypt(token, encodingAESKey, appID);
			//解密消息
			msg = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, postData);
		} catch (AesException e) {
			e.printStackTrace();
		}
		
		// 调用核心业务类接收消息、处理消息
		String respMessage = CoreService.processRequest(msg);
		String encryptMsg = "";
		try {
			//加密回复消息
			encryptMsg = wxcpt.EncryptMsg(respMessage, timestamp, nonce);
		} catch (AesException e) {
			e.printStackTrace();
		}
		
		// 响应消息
		PrintWriter out = response.getWriter();
		out.print(encryptMsg);
		out.close();
	}
	
	
	public static void main(String[] args) {
		
	}
	
}
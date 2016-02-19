package org.caco.weixin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.caco.weixin.services.CoreService;
import org.caco.weixin.util.SHA1;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

/**
 * @author caco
 */
@Controller
@RequestMapping("/weixin/")
public class WeixinController {
	@RequestMapping(value = { "/signature" }, method = RequestMethod.GET)
	public void signatureFroGet(@RequestParam(value = "signature", required = true) String signature,
			@RequestParam(value = "timestamp", required = true) String timestamp,
			@RequestParam(value = "nonce", required = true) String nonce,
			@RequestParam(value = "echostr", required = true) String echostr, HttpServletResponse response)
					throws IOException {
		//test utl: http://120.25.122.7/caco/weixin/signature?timestamp=&nonce=&echostr=&signature=
		String[] values = { token, timestamp, nonce };
		Arrays.sort(values); // 字典序排序
		String value = values[0] + values[1] + values[2];
		String sign = SHA1.encode(value);
		System.out.println(sign);
		PrintWriter writer = response.getWriter();
		if (signature.equals(sign)) {// 验证成功返回ehcostr
			writer.print(echostr);
		} else {
			writer.print("error");
		}
		writer.flush();
		writer.close();
		
		/*// 解析出url上的参数值如下：
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
    private static String appId="wx3205ac2c498336a4"; 
	@RequestMapping(value = { "/signature" }, method = RequestMethod.POST)
	public void signatureFroPost(HttpServletResponse response, HttpServletRequest request)
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
			wxcpt = new WXBizMsgCrypt(token, encodingAESKey, appId);
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
	public static String[] getSignatureFromXML(String xmltext)  {
		String[] result = new String[2];
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(xmltext);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);
			Element root = document.getDocumentElement();
			NodeList nodelist1 = root.getElementsByTagName("MsgSignature");
			NodeList nodelist2 = root.getElementsByTagName("Encrypt");
			
			//这两行有时候会报错，解决方案在项目的Java Build Path | Order and Export选项卡中，将JRE System Library选中，并Top置顶。然后再进行编译即可
			result[0] = nodelist1.item(0).getTextContent();
			result[1] = nodelist2.item(0).getTextContent();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void testEncryptDecryptMsg() {
		WXBizMsgCrypt wxcpt = null;
		String encryptMsg = "";
		try {
			wxcpt = new WXBizMsgCrypt(token, encodingAESKey, appId);
			//加密回复消息
			encryptMsg = wxcpt.EncryptMsg("test", "1455856212", "321197342");
		} catch (AesException e) {
			e.printStackTrace();
		}
		System.out.println(encryptMsg);
		System.out.println(getSignatureFromXML(encryptMsg));
		String msg = null;
		try {
//			wxcpt = new WXBizMsgCrypt(token, encodingAESKey, appId);
			//解密消息
			String[] result = getSignatureFromXML(encryptMsg);
			//msg = wxcpt.DecryptMsg("cd503936ae4aad0b001fdbf504c536e490a53d35","1455856212","321197342", "<xml><ToUserName><![CDATA[gh_67d5e9bf4fa7]]></ToUserName><Encrypt><![CDATA[QuHdotov266utLifdXMl1t0KAgly+3BuNxJ+RqWcXakQfRdchEyCivIYfofFhjN4BDCeBXg2P5B1vcZtDrPM2hIaE/9iWX4ifl4glbqVDVqwR8X/gSmJyXg9JRQhEakgwVv4ofS77nug5hUqIyJtIwB3GVluLHvDXWoTL3+dpgUeRXYd2eZgFwd4pLEGvDfuBuyaws2FG+Xaom7N0gitI5/Kuk8girmy9C7QgmIOlQ/0kYyllxQwpT5aWP6yydEfWEGuRFGBmYKkHWdGB7Yx3yWOnIDdVyoZKgE7rxVMeIuqvVmDXGBYW2b+zNAk2UN6iOxIqH45k1EH1+19Ksp5hk7PNU58BuEoRrdk+4mVEjILAajsPg3YRUTienRZl5yGyQXsBHP04/PmlJjrb83A5K3qg1wCgDQcfsdMmaLOydQ=]]></Encrypt></xml>");
			String xml = "<xml><ToUserName><![CDATA[gh_67d5e9bf4fa7]]></ToUserName><Encrypt><![CDATA["+result[1]+"]]></Encrypt></xml>";
			msg = wxcpt.DecryptMsg(result[0],"1455856212","321197342", xml);
		} catch (AesException e) {
			e.printStackTrace();
		}
		System.out.println("msg=" + msg);
	}
	public static void main(String[] args) {
		// 微信加密签名
		String msg_signature = "3080dad74cc9c21b5b4121fdc835a6e741f96c7e";
		// 时间戳
		String timestamp = "1455864051";
		// 随机数
		String nonce = "495595013";
		//从请求中读取整个post数据
		String postData = "<xml><ToUserName><![CDATA[gh_67d5e9bf4fa7]]></ToUserName><Encrypt><![CDATA[SM4YPwmXjIFUSFwgkdL9V9xoz42slTln9Dvs5/FrEsTKEPuE2ZmZYlIIYlY8+4jEMuwq94GTvLBrOND9Xx6j096WdqguX7GhIu6UIyebhqr4rJb7wmaaBXuGwC8F+V+VnekKS+iNQrwd8M57xqW8PCAo3Mc3GWEU98f2vJvVNMci3Cnv8YNmOt6IwKCfxCd4S7H7NzsEj9J3eD84Z4ZFQjztz4PmoPPYPfcDzN3XoTDrmKzna1IpHSj3M4cW8kbHQ5q4n9zbAa3PMY+6jPLQW+WnN+5oRhfZeYSyNs35pjSdF/dogLXymsyrlp6XZD1fhTKhyoggRY4wiIOtUJxEn3zKsOf7L0mDViM/KvD+4DCXsKWTJ1qiztVnt8wPHOnPMwbbPL22NX+475zvo4Y5dRWeZ4aptOsgicc6wedPOwJ033HYgQumgPHhvaBlgBH2PXiUmUdJxkmNTyyYVvEL6Q==]]></Encrypt></xml>";
		System.out.println(msg_signature+","+timestamp+","+nonce+","+postData);
		String msg = "";
		WXBizMsgCrypt wxcpt = null;
		try {
			wxcpt = new WXBizMsgCrypt(token, encodingAESKey, appId);
			//解密消息
			msg = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, postData);
		} catch (AesException e) {
			e.printStackTrace();
		}
		System.out.println("解密信息=" + msg);
		
		// 调用核心业务类接收消息、处理消息
		String respMessage = CoreService.processRequest(msg);
		System.out.println("respMessage=" + respMessage);
		String encryptMsg = "";
		try {
			//加密回复消息
			encryptMsg = wxcpt.EncryptMsg(respMessage, timestamp, nonce);
		} catch (AesException e) {
			e.printStackTrace();
		}
		
		System.out.println("加密信息："+encryptMsg);

	}
	
}
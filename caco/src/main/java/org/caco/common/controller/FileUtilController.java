package org.caco.common.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.caco.common.util.Result;
import org.caco.common.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author caco
 */
@Controller
@RequestMapping("/util/")
public class FileUtilController {
	@RequestMapping(value = "fileupload", method = RequestMethod.POST)  
	public void upload(HttpServletResponse response, HttpServletRequest request, @RequestParam MultipartFile[] files) {
		String fileNames="";
		String fileName="";
		
		String port = request.getLocalPort()==80?"":":"+request.getLocalPort();
		String url = "http://"+request.getServerName() + port + "/caco/upload/";
		try {
			// 文件个数至少1个以上
			if (files != null && files.length > 0) {
				// 循环获取file数组中得文件
				for (int i = 0; i < files.length; i++) {
					MultipartFile file = files[i];
					if(!file.isEmpty()) {
						// 文件保存路径
//						String filePath = request.getServletContext().getRealPath("/")
//								+ "upload/" + file.getOriginalFilename();
						fileName = UUID.randomUUID()+(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
						if("".equals(fileNames)) {
							fileNames = url + fileName;
						} else {
							fileNames += "," + url + fileName;
						}
						String filePath = request.getServletContext().getRealPath("/")
								+ "upload/" + fileName;
						// 转存文件
						file.transferTo(new File(filePath));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Result ret = new Result(Result.SUCCESS, "上传成功", fileNames);
		WebUtils.writeJson(response, ret);
	}
}
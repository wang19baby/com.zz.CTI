package com.zz.CTI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/test")
public class TestController extends BaseController {
	
	@RequestMapping(value = "/addContract", method = RequestMethod.POST)
	@ResponseBody
	public String addContract(@RequestParam("upfile") MultipartFile file, @RequestParam("fileName") String fileName) {
		String fileName1 = file.getOriginalFilename();
		System.out.println("文件名：" + fileName1 + " ; " + fileName);
		return "ZZ";
	}
}


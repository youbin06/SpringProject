package com.youbin.myresume;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = "project1.app")
	public String project1() {
		return "project1/index";
	}
	
	@RequestMapping(value ="project2.app")
	public String project2() {
		return "project2/index";
	}
	
}

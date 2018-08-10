package com.youbin.myresume;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youbin.myresume.project1.user.User2Mapper;

@Controller
public class Project2MenuController {
	
	@Autowired
	User2Mapper user2Mapper;

	@RequestMapping(value = "/index.project2")
	public String main() {
		return "project2/index";
	}
	
	@RequestMapping(value = "/instructor.project2")
	public String instructor() {
		return "project2/instructor";
	}
	
	@RequestMapping(value = "/lecture.project2")
	public String lecture() {
		return "project2/lecture";
	}
	
	@RequestMapping(value = "/loginForm.project2")
	public String loginForm() {
		return "project2/loginForm";
	}
	
	@RequestMapping(value = "/registerForm.project2")
	public String registerForm() {
		return "project2/registerForm";
	}
	
	
	
	@RequestMapping(value ="/logout.project2")
	public String logout() {
		return "project2/user/logoutAction";
	}
}

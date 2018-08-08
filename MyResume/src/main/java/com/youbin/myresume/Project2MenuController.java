package com.youbin.myresume;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Project2MenuController {

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
	
	@RequestMapping(value = "/lectureAssessment.project2")
	public String lectureAssessment() {
		return "project2/lectureAssessment";
	}
}

package com.youbin.myresume;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youbin.myresume.project1.user.User2DTO;
import com.youbin.myresume.project1.user.User2Mapper;

@Controller
public class Project2UserController {
	
	@Autowired
	User2Mapper user2Mapper;
	
	@RequestMapping(value ="/login.project2")
	public void login() {
		String userID = "";
		String userPassword = "";
		user2Mapper.login(userID, userPassword);
	}
	
	@RequestMapping(value ="/register.project2")
	public String register(@ModelAttribute User2DTO user, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userPassword1 = request.getParameter("userPassword1");
		String userPassword2 = request.getParameter("userPassword2");
		
		String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(user.getUserEmail());
		boolean isNormal = m.matches();
		if(!isNormal) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "이메일 형식을 잘못 입력하셨습니다.");
			return "project1/join";
		}
		
		if(!userPassword1.equals(userPassword2)) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "비밀번호가 서로 다릅니다.");
			return "project1/join";
		}
		user.setUserPassword(userPassword1);
		
		user.setUserEmailHash(SHA256.getSHA256(user.getUserEmail()));
		user.setUserEmailChecked("0");
		int result = user2Mapper.register(user);
		if(result == 1) {
			request.getSession().setAttribute("userID", user.getUserID());
			request.getSession().setAttribute("messageType", "성공 메시지");
			request.getSession().setAttribute("messageContent", "회원가입에 성공했습니다.");
			return "project2/index";
		}else {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "이미 존재하는 회원입니다.");
			return "project1/index";
		}
	}
	

}

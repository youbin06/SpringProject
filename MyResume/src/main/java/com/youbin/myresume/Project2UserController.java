package com.youbin.myresume;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		int result = user2Mapper.login(userID, userPassword);
		
		if(result == 1) {
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("messageType", "성공 메시지");
			request.getSession().setAttribute("messageContent", "로그인에 성공했습니다.");
			return "project2/index";	
		}else if(result == 0){
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "비밀번호를 다시 확인하세요.");
			return "project2/login";
		}else if(result == -1) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "아이디가 존재하지 않습니다.");
			return "project2/login";
		}
		
		return "project2/index";
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
			return "project2/registerForm";
		}
		
		if(!userPassword1.equals(userPassword2)) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "비밀번호가 서로 다릅니다.");
			return "project2/registerForm";
		}
		user.setUserPassword(userPassword1);
		
		user.setUserEmailHash(SHA256.getSHA256(user.getUserEmail()));
		user.setUserEmailChecked("0");
		int result = user2Mapper.register(user);
		if(result == 1) {
			request.getSession().setAttribute("userID", user.getUserID());
			this.emailChecked(user.getUserID(), request);
			return "project2/user/emailSendAction";
		}else {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "이미 존재하는 회원입니다.");
			return "project2/registerForm";
		}
	}
	
	public String emailChecked(String userID, HttpServletRequest request) throws Exception {
		
		boolean emailChecked = user2Mapper.getUserEmailChecked(userID);
		if(emailChecked == true) {
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("messageType", "성공 메시지");
			request.getSession().setAttribute("messageContent", "이미 인증 된 회원입니다.");
			return "project2/index";
		}
		String host = "http://localhost:8080/myresume/";
		String from = "qordbqls06@gmail.com";
		String to = user2Mapper.getUserEmail(userID);
		String subject = "강의평가를 위한 이메일 인증 메일입니다.";
		String content  = "다음 링크에 접속하여 이메일 인증을 진행하세요." + "<br><a href='" + host + 
				"emailCheckAction?code="+ new SHA256().getSHA256(to) + "'>이메일 인증하기</a>";
		
		Properties p = new Properties();
		p.put("mail.smtp.uesr", from);
		p.put("mail.smtp.host", "smtp.googlemail.com");
		p.put("mail.smtp.port", "456");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.debug", "true");
		p.put("mail.smtp.socketFactory.port", "465");
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.fallback", "false");
		
		try {
			Authenticator auth = new Gmail();
			Session ses = Session.getInstance(p, auth);
			ses.setDebug(true);
			MimeMessage msg = new MimeMessage(ses);
			msg.setSubject(subject);
			Address fromAddr = new InternetAddress(from);
			msg.setFrom(fromAddr);
			Address toAddr = new InternetAddress(to);
			msg.addRecipient(Message.RecipientType.TO, toAddr);
			msg.setContent(content, "text/html; charset=UTF-8");
			Transport.send(msg);
		}catch(Exception e){
			e.printStackTrace();
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "오류가 발생했습니다.");
			return "project2/registerForm";
		}
		return "project2/index";
	}
	
	@RequestMapping(value="/emailCheckAction")
	public String emailCheckAction(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String code = request.getParameter("code");
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		String userEmail = user2Mapper.getUserEmail(userID);
		boolean isRight = (new SHA256().getSHA256(userEmail).equals(code)) ? true : false;
		if(isRight == true) {
			user2Mapper.setUserEmailChecked(userID);
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("messageType", "성공 메시지");
			request.getSession().setAttribute("messageContent", "인증에 성공했습니다.");
			return "project2/index";
		}else {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "유효하지 않은 코드입니다.");
			return "project2/index";
		}
	}
	
	@RequestMapping(value ="/emailReSend")
	public String emailResend(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		boolean emailChecked = user2Mapper.getUserEmailChecked(userID);
		
		String host = "http://localhost:8080/myresume/";
		String from = "qordbqls06@gmail.com";
		String to = user2Mapper.getUserEmail(userID);
		String subject = "강의평가를 위한 이메일 인증 메일입니다.";
		String content  = "다음 링크에 접속하여 이메일 인증을 진행하세요." + "<a href='" + host + 
				"emailCheckAction?code="+ new SHA256().getSHA256(to) + "'>이메일 인증하기</a>";
		
		Properties p = new Properties();
		p.put("mail.smtp.uesr", from);
		p.put("mail.smtp.host", "smtp.googlemail.com");
		p.put("mail.smtp.port", "456");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.debug", "true");
		p.put("mail.smtp.socketFactory.port", "465");
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.fallback", "false");
		
		try {
			Authenticator auth = new Gmail();
			Session ses = Session.getInstance(p, auth);
			ses.setDebug(true);
			MimeMessage msg = new MimeMessage(ses);
			msg.setSubject(subject);
			Address fromAddr = new InternetAddress(from);
			msg.setFrom(fromAddr);
			Address toAddr = new InternetAddress(to);
			msg.addRecipient(Message.RecipientType.TO, toAddr);
			msg.setContent(content, "text/html; charset=UTF-8");
			Transport.send(msg);
		}catch(Exception e){
			e.printStackTrace();
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "오류가 발생했습니다.");
			return "project2/index";
		}
		return "project2/user/emailSendAction";
	}
}

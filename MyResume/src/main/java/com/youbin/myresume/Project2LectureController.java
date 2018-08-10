package com.youbin.myresume;

import java.util.ArrayList;
import java.util.Properties;

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

import com.youbin.myresume.project1.user.EvaluationDTO;
import com.youbin.myresume.project1.user.LectureMapper;
import com.youbin.myresume.project1.user.User2Mapper;

@Controller
public class Project2LectureController {
	
	@Autowired
	LectureMapper lectureMapper;
	@Autowired
	User2Mapper user2Mapper;
	
	@RequestMapping(value ="/evaluationRegisterAction.project2")
	public String evaluationRegister(@ModelAttribute EvaluationDTO evaluationDTO, HttpServletRequest request, HttpServletResponse response)throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		this.evaluationSearch(request, response);
		if(userID == null) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "현재 로그인이 되어 있지 않습니다.");
			return "project2/loginForm";
		}
		String lectureYear = request.getParameter("lectureYear");
		evaluationDTO.setLectureYear(Integer.parseInt(lectureYear));
		evaluationDTO.setUserID(userID);
		
		int result = lectureMapper.write(evaluationDTO);
		if(result == 1) {
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("messageType", "성공 메시지");
			request.getSession().setAttribute("messageContent", "강의 평가 등록에 성공했습니다.");
			return this.lectureAssessment(request, response);
		}else {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "강의 평가 등록에 실패했습니다.");
			return "project2/lectureAssessment";
		}
	}
	
	@RequestMapping(value ="/reportAction.project2")
	public String report(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		this.evaluationSearch(request, response);
		if(userID == null) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "현재 로그인이 되어 있지 않습니다.");
			return "project2/loginForm";
		}
		String reportTitle = request.getParameter("reportTitle");
		String reportContent = request.getParameter("reportContent");
		
		if(reportTitle.equals("") || reportTitle == null || reportContent.equals("") || reportContent == null) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "잘못된 경로 입니다.");
			return "project2/lectureAssessment";
		}
	
		String from = "qordbqls06@gmail.com";
		String to = "youbin06@naver.com";
		String subject = "강의평가 사이트에서 접수된 신고 메일입니다.";
		String content  = "신고자: " + userID + 
							"<br>제목: " + reportTitle +
							"<br>내용: " + reportContent;
		
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
		
		request.getSession().setAttribute("userID", userID);
		request.getSession().setAttribute("messageType", "성공 메시지");
		request.getSession().setAttribute("messageContent", "정상적으로 신고되었습니다.");
		return "project2/lectureAssessment";
	}
	
	@RequestMapping(value = "/lectureAssessment.project2")
	public String lectureAssessment(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		if(userID == null) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "현재 로그인이 되어 있지 않습니다.");
			return "project2/loginForm";
		}
		boolean emailChecked = user2Mapper.getUserEmailChecked(userID);
		if(emailChecked == false) {
			return "project2/user/emailSendConfirm";
		}
		this.evaluationSearch(request, response);
		return "project2/lectureAssessment";
	}
	
	public void evaluationSearch(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String lectureDivide = request.getParameter("lectureDivide");
		if(lectureDivide == null || lectureDivide.equals("")) {
			lectureDivide = "전체";
		}
		
		String search = request.getParameter("search");
		if(search == null || search.equals("")) {
			search = "";
		}
		String searchType = request.getParameter("searchType");
		if(searchType == null || searchType.equals("")) {
			searchType = "최신순";
		}
		String pageNumber = request.getParameter("pageNumber");
		if(pageNumber == null || pageNumber.trim().equals("") || pageNumber.equals("null")) {
			pageNumber = "0";
		}
		request.setAttribute("search", search);
		request.setAttribute("pageNumber", pageNumber);
		request.setAttribute("lectureDivide", lectureDivide);
		request.setAttribute("searchType", searchType);
		ArrayList<EvaluationDTO> evaluationList = lectureMapper.getEvaluationList(lectureDivide, searchType, search, Integer.parseInt(pageNumber));
		int nextPage = lectureMapper.nextPage(lectureDivide, searchType, searchType, Integer.parseInt(pageNumber));
		request.setAttribute("nextPage", nextPage);
		request.setAttribute("evaluationList", evaluationList);
	}
	
	@RequestMapping(value = "/evaluationSearch.project2")
	public String search(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String lectureDivide = request.getParameter("lectureDivide");
		String search = request.getParameter("search");
		String searchType = request.getParameter("searchType");
		String pageNumber = request.getParameter("pageNumber");
		if(pageNumber == null || pageNumber.trim().equals("") || pageNumber.equals("null")) {
			pageNumber = "0";
		}
		request.setAttribute("search", search);
		request.setAttribute("pageNumber", pageNumber);
		request.setAttribute("lectureDivide", lectureDivide);
		request.setAttribute("searchType", searchType);
		ArrayList<EvaluationDTO> evaluationList = lectureMapper.getEvaluationList(lectureDivide, searchType, search, Integer.parseInt(pageNumber));
		request.setAttribute("evaluationList", evaluationList);
		return "project2/searchAssessment";
	}
	
}

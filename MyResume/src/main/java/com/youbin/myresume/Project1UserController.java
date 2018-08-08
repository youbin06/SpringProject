package com.youbin.myresume;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.youbin.myresume.project1.user.UserDTO;
import com.youbin.myresume.project1.user.UserMapper;

@Controller
public class Project1UserController {
	
	@Autowired
	private UserMapper userMapper;
	
	@RequestMapping(value = "/index.do")
	public String index() {
		return "project1/index";
	}
	
	@RequestMapping(value = "/join.do", method=RequestMethod.GET)
	public String join() {
		return "project1/join";
	}
	
	@RequestMapping(value ="/userRegisterCheck.do")
	public void userRegisterCheck(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		
		if(userID == null || userID.equals("")) {
			response.getWriter().write("-1");
			return;
		}else {
			response.getWriter().write(userMapper.registerCheck(userID) + "");
		}
		
	}
	
	@RequestMapping(value = "/join.do", method=RequestMethod.POST)
	public String joinUser(@ModelAttribute UserDTO dto, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userPassword1 = request.getParameter("userPassword1");
		String userPassword2 = request.getParameter("userPassword2");
		
		String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(dto.getUserEmail());
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
		dto.setUserPassword(userPassword1);
		int result = userMapper.register(dto);
		
		if(result == 1) {
			request.getSession().setAttribute("userID", dto.getUserID());
			request.getSession().setAttribute("messageType", "성공 메시지");
			request.getSession().setAttribute("messageContent", "회원가입에 성공했습니다.");
			return "project1/index";
		}else {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "이미 존재하는 회원입니다.");
			return "project1/join";
		}

	}
	
	@RequestMapping(value = "/logoutAction.do")
	public String logout() {
		return "project1/logoutAction";
	}
	
	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String loginForm() {
		return "project1/login";
	}
	
	@RequestMapping(value ="/login.do", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		
		int result = userMapper.login(userID, userPassword);
		
		if(result == 1) {
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("messageType", "성공 메시지");
			request.getSession().setAttribute("messageContent", "로그인에 성공했습니다.");
			return "project1/index";	
		}else if(result == 2){
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "비밀번호를 다시 확인하세요.");
			return "project1/login";
		}else if(result == 0) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "아이디가 존재하지 않습니다.");
			return "project1/login";
		}
		
		return "project1/index";
	}
	
	@RequestMapping(value ="/update.do", method = RequestMethod.GET)
	public String upadateForm(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		
		UserDTO userDTO = userMapper.getUser(userID);
		request.setAttribute("userGender", userDTO.getUserGender());
		request.setAttribute("userDTO", userDTO);
		return "project1/update";
	}
	
	@RequestMapping(value ="/update.do", method = RequestMethod.POST)
	public String update(@ModelAttribute UserDTO dto, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		request.setAttribute("userGender", dto.getUserGender());
		
		String userPassword1 = request.getParameter("userPassword1");
		String userPassword2 = request.getParameter("userPassword2");
		
		HttpSession session = request.getSession();
		if(!dto.getUserID().equals((String)session.getAttribute("userID"))){
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "접근할 수 없습니다.");
			return "project1/index";
		}
		
		if(!userPassword1.equals(userPassword2)) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "비밀번호가 서로 다릅니다.");
			return "project1/update";
		}
		
		dto.setUserPassword(userPassword1);
		int result = userMapper.update(dto);
		
		if(result == 1) {
			request.getSession().setAttribute("userID", dto.getUserID());
			request.getSession().setAttribute("messageType", "성공 메시지");
			request.getSession().setAttribute("messageContent", "회원정보 수정에 성공했습니다.");
			return "project1/index";
		}else {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "데이터베이스 오류가 발생했습니다.");
			return "project1/update";
		}
	}
	
	@RequestMapping(value = "/profileUpdate.do", method=RequestMethod.GET)
	public String profileUpdateForm() {
		return "project1/profileUpdate";
	}
	
	@RequestMapping(value = "/userProfile.do")
	public String userProfile(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		MultipartRequest multi = null;
		int fileMaxSize = 10 * 1024 * 1024;
		String savePath = request.getRealPath("/resources/upload").replaceAll("\\\\", "/");
		try {
			multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
		}catch(Exception e) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "파일크기는 10MB를 넘을 수 없습니다.");
			return "project1/profileUpdate";
		}
		String userID = multi.getParameter("userID");
		HttpSession session = request.getSession();
		if(!userID.equals((String)session.getAttribute("userID"))){
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "접근할 수 없습니다.");
			return "project1/index";
		}
		
		String fileName = "";
		File file = multi.getFile("userProfile");
		if(file != null) {
			String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
			if(ext.equals("jpg") || ext.equals("png") || ext.equals("gif")) {
				String prev = userMapper.getUser(userID).getUserProfile();
				File prevFile = new File(savePath + "/" + prev);
				if(prevFile.exists()) {
					prevFile.delete();
				}
				fileName = file.getName();
			}else {
				String notImg = file.getName();
				File notImgFile = new File(savePath + "/" + notImg);
				notImgFile.delete();
				session.setAttribute("messageType", "오류 메시지");
				session.setAttribute("messageContent", "이미지 파일만 업로드 가능합니다.");
				return "project1/profileUpdate";
			}
		}else {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "파일을 선택하지 않았습니다.");
			return "project1/profileUpdate";
		}
		
		userMapper.profile(userID, fileName);
		
		session.setAttribute("messageType", "성공 메시지");
		session.setAttribute("messageContent", "성공적으로 프로필이 변경되었습니다.");
		return "project1/index";
		
	}
	
	@RequestMapping(value = "/find.do", method=RequestMethod.GET)
	public String findForm() {
		return "project1/find";
	}
	@RequestMapping(value ="/UserFind.do")
	public void userFind(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		if(userID == null || userID.equals("")) {
			response.getWriter().write("-1");
		}else if(userMapper.registerCheck(userID) == 0) {
			try {
				response.getWriter().write(find(userID));
			}catch(Exception e) {
				response.getWriter().write("-1");
				e.printStackTrace();
			}
		}else {
			response.getWriter().write("-1");
		}
	}
	
	private String find(String userID) throws Exception{
		StringBuffer result = new StringBuffer("");
		result.append("{\"userProfile\":\"" + userMapper.getProfile(userID) + "\"}");
		return result.toString();
	}
}

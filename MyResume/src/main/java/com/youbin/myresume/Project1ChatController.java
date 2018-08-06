package com.youbin.myresume;

import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youbin.myresume.project1.user.ChatDTO;
import com.youbin.myresume.project1.user.ChatMapper;
import com.youbin.myresume.project1.user.UserMapper;

@Controller
public class Project1ChatController {

	@Autowired 
	UserMapper userMapper;
	@Autowired 
	ChatMapper chatMapper;
	
	@RequestMapping(value = "/chat.do")
	public String chat(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		String toID = request.getParameter("toID");
		String fromProfile = userMapper.getProfile(userID);
		String toProfile = userMapper.getProfile(toID);
		
		request.setAttribute("toID", toID);
		request.setAttribute("fromProfile", fromProfile);
		request.setAttribute("toProfile", toProfile);
		return "project1/chat/chat";
	}
	
	@RequestMapping(value = "/chatSubmit")
	public void chatSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String fromID = request.getParameter("fromID");
		String toID = request.getParameter("toID");
		String chatContent = request.getParameter("chatContent");
		this.chat(request, response);
		
		if(fromID == null || fromID.equals("") || toID == null || toID.equals("") ||
				chatContent == null || chatContent.equals("")) {
			response.getWriter().write("0");
		}else if(fromID.equals(toID)) {
			response.getWriter().write("-1");
		}
		
		else {
			fromID = URLDecoder.decode(fromID, "UTF-8");
			toID = URLDecoder.decode(toID, "UTF-8");
			HttpSession session = request.getSession();
			if(!URLDecoder.decode(fromID, "UTF-8").equals((String)session.getAttribute("userID"))){
				response.getWriter().write("");
				return;
			}
			chatContent = URLDecoder.decode(chatContent, "UTF-8");
			response.getWriter().write(chatMapper.submit(fromID, toID, chatContent) + "");
		}
		
		
	}
	
	@RequestMapping(value = "/chatList")
	public void chatList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String fromID = request.getParameter("fromID");
		String toID = request.getParameter("toID");
		String listType = request.getParameter("listType");
		this.chat(request, response);
		
		if(fromID == null || fromID.equals("") || toID == null || toID.equals("") || listType == null || listType.equals("")) {
			response.getWriter().write("");
		} else if(listType.equals("ten")) response.getWriter().write(getTen(URLDecoder.decode(fromID, "UTF-8"), URLDecoder.decode(toID,"UTF-8")));
		else {
			try {
				HttpSession session = request.getSession();
				if(!URLDecoder.decode(fromID, "UTF-8").equals((String)session.getAttribute("userID"))){
					response.getWriter().write("");
					return;
				}
				response.getWriter().write(getID(URLDecoder.decode(fromID, "UTF-8"), URLDecoder.decode(toID,"UTF-8"), listType));
			}catch(Exception e) {
				response.getWriter().write("");	
			}
		}
	}
	
	public String getTen(String fromID, String toID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ArrayList<ChatDTO> chatList = (ArrayList<ChatDTO>)chatMapper.getChatListByRecent(fromID, toID, 100);
		if(chatList.size() == 0) return "";
		for(int i = 0; i < chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
			if(i != chatList.size() - 1) result.append(",");
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
		chatMapper.readChat(fromID, toID);
		return result.toString();
	}
	
	public String getID(String fromID, String toID, String chatID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ArrayList<ChatDTO> chatList = (ArrayList<ChatDTO>) chatMapper.getChatListByID(fromID, toID, chatID);
		if(chatList.size() == 0) return "";
		for(int i = 0; i < chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
			if(i != chatList.size() - 1) result.append(",");
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
		chatMapper.readChat(fromID, toID);
		return result.toString();
	}
	
	@RequestMapping(value ="/box.do")
	public String boxForm() {
		return "project1/chat/box";
	}
	
	@RequestMapping(value ="/chatBox")
	public void box(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");

		if (userID == null || userID.equals("")) {
			response.getWriter().write("");
		} else {
			try {
				HttpSession session = request.getSession();
				if(!URLDecoder.decode(userID, "UTF-8").equals((String)session.getAttribute("userID"))){
					response.getWriter().write("");
					return;
				}
				userID = URLDecoder.decode(userID, "UTF-8");
				response.getWriter().write(getBox(userID));
			} catch (Exception e) {
				response.getWriter().write("");
			}

		}
	}
	
	public String getBox(String userID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ArrayList<ChatDTO> chatList = chatMapper.getBox(userID);
		if(chatList.size() == 0) return "";
		for(int i = chatList.size() - 1; i >= 0; i--) {
			String unread = "";
			String userProfile = "";
			if(userID.equals(chatList.get(i).getToID())) {
				unread = chatMapper.getUnreadChat(chatList.get(i).getFromID(), userID) + "";
				if(unread.equals("0")) unread = "";
			}
			if(userID.equals(chatList.get(i).getToID())) {
				userProfile = userMapper.getProfile(chatList.get(i).getFromID());
			}else {
				userProfile = userMapper.getProfile(chatList.get(i).getToID());
			}
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"},");
			result.append("{\"value\": \"" + unread + "\"},");
			result.append("{\"value\": \"" + userProfile + "\"}]");

			if(i != 0) result.append(",");
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
		
		return result.toString();
	}
	
	@RequestMapping(value="/chatUnread")
	public void chatUnread(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		if(userID == null || userID.equals("")) {
			response.getWriter().write("0");
		}else {
			userID = URLDecoder.decode(userID, "UTF-8");
			HttpSession session = request.getSession();
			if(!URLDecoder.decode(userID, "UTF-8").equals((String)session.getAttribute("userID"))){
				response.getWriter().write("");
				return;
			}
			response.getWriter().write(chatMapper.getAllUnreadChat(userID)+ "");
		}
	}
}

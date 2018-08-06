package com.youbin.myresume;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youbin.myresume.project1.user.AnonymousDTO;
import com.youbin.myresume.project1.user.AnonymousMapper;

@Controller
public class Project1AnonymousChatController {
	
	@Autowired 
	AnonymousMapper anonymousMapper;
	
	@RequestMapping(value = "/anonymousChat.do")
	public String anonymousChatForm() {
		return "project1/anonymous/anonymousChat";
	}
	
	@RequestMapping(value = "/anonymousChatSubmit")
	public void anonymousChatSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String chatName = URLDecoder.decode(request.getParameter("chatName"), "UTF-8");
		String chatContent = URLDecoder.decode(request.getParameter("chatContent"), "UTF-8");
		
		if(chatName == null || chatName.equals("") || chatContent == null || chatContent.equals("")) {
			response.getWriter().write("0");
		}else {
			response.getWriter().write(anonymousMapper.submit(chatName, chatContent) + "");
		}
	}
	
	@RequestMapping(value = "/anonymousChatList")
	public void anonymousChatList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String listType = request.getParameter("listType");
		if(listType == null || listType.equals("")) response.getWriter().write("0");
		else if(listType.equals("today")) response.getWriter().write(getToday());
		else if(listType.equals("ten")) response.getWriter().write(getTen());
		else {
			try {
				Integer.parseInt(listType);
				response.getWriter().write(getID(listType));
			}catch(Exception e) {
				response.getWriter().write("");
			}
		}
	}
	
	public String getToday() {
		StringBuffer result = new StringBuffer("");
		ArrayList<AnonymousDTO> anonyList = (ArrayList<AnonymousDTO>) anonymousMapper.getChatList(new SimpleDateFormat("yyyy-MM-dd").format(new Date())); 
		result.append("{\"result\":[");
		for(int i = 0; i < anonyList.size(); i++) {
			result.append("[{\"value\": \"" + anonyList.get(i).getChatName() + "\"},");
			result.append("{\"value\": \"" + anonyList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + anonyList.get(i).getChatTime() + "\"}]");
			if(i != anonyList.size() - 1) result.append(",");
		}
		result.append("], \"last\":\"" + anonyList.get(anonyList.size() - 1).getChatID() + "\"}");
		return result.toString();
	}
	
	public String getTen() {
		StringBuffer result = new StringBuffer("");
		ArrayList<AnonymousDTO> anonyList = (ArrayList<AnonymousDTO>) anonymousMapper.getChatListByRecent(10); 
		result.append("{\"result\":[");
		for(int i = 0; i < anonyList.size(); i++) {
			result.append("[{\"value\": \"" + anonyList.get(i).getChatName() + "\"},");
			result.append("{\"value\": \"" + anonyList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + anonyList.get(i).getChatTime() + "\"}]");
			if(i != anonyList.size() - 1) result.append(",");
		}
		result.append("], \"last\":\"" + anonyList.get(anonyList.size() - 1).getChatID() + "\"}");
		return result.toString();
	}
	
	public String getID(String chatID) {
		StringBuffer result = new StringBuffer("");
		ArrayList<AnonymousDTO> anonyList = (ArrayList<AnonymousDTO>) anonymousMapper.getChatListByRecent(chatID); 
		result.append("{\"result\":[");
		for(int i = 0; i < anonyList.size(); i++) {
			result.append("[{\"value\": \"" + anonyList.get(i).getChatName() + "\"},");
			result.append("{\"value\": \"" + anonyList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + anonyList.get(i).getChatTime() + "\"}]");
			if(i != anonyList.size() - 1) result.append(",");
		}
		result.append("], \"last\":\"" + anonyList.get(anonyList.size() - 1).getChatID() + "\"}");
		return result.toString();
	}

}

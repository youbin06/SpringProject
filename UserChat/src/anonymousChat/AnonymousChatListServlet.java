package anonymousChat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/AnonymousChatListServlet")
public class AnonymousChatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		AnonymousDAO anonydao = new AnonymousDAO();
		ArrayList<AnonymousDTO> anonyList = anonydao.getChatList(new SimpleDateFormat("yyyy-MM-dd").format(new Date())); 
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
		AnonymousDAO anonydao = new AnonymousDAO();
		ArrayList<AnonymousDTO> anonyList = anonydao.getChatListByRecent(10); 
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
		AnonymousDAO anonydao = new AnonymousDAO();
		ArrayList<AnonymousDTO> anonyList = anonydao.getChatListByRecent(chatID); 
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

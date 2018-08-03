package chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ChatDAO {
	Connection con;
	PreparedStatement ps;
	ResultSet rs;

	static DataSource ds;
	
	static {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/UserChat");
		} catch (NamingException e) {
			System.out.println("lookup : " + e.getMessage());
		}
	}
	
	public ArrayList<ChatDTO> getChatListByID(String fromID, String toID, String chatID){
		ArrayList<ChatDTO> chatList = null;
		String SQL = "SELECT * FROM chat WHERE ((fromID = ? AND toID = ?) OR (fromID = ? AND toID = ?)) AND chatID > ? ORDER BY chatTime";
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, fromID);
			ps.setString(2, toID);
			ps.setString(3, toID);
			ps.setString(4, fromID);
			ps.setInt(5, Integer.parseInt(chatID));
			rs = ps.executeQuery();
			chatList = new ArrayList<ChatDTO>();
			while(rs.next()) {
				ChatDTO chat = new ChatDTO();
				chat.setChatID(rs.getInt("chatID"));
				chat.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setToID(rs.getString("toID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				if(chatTime > 12) {
					timeType = "오후";
					chatTime -= 12;
				}else if(chatTime == 12) {
					timeType ="오후";
				}
				chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
				chatList.add(chat);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) rs.close();
				if (con != null) con.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return chatList;	
	}
	
	public ArrayList<ChatDTO> getChatListByRecent(String fromID, String toID, int number){
		ArrayList<ChatDTO> chatList = null;
		String SQL = "SELECT * FROM chat WHERE ((fromID = ? AND toID = ?) OR (fromID = ? AND toID = ?)) "
				+ "AND chatID > (SELECT MAX(chatID) - ? FROM CHAT WHERE (fromID = ? AND toID =?) "
				+ "OR (fromID =? AND toID = ?)) ORDER BY chatTime";
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, fromID);
			ps.setString(2, toID);
			ps.setString(3, toID);
			ps.setString(4, fromID);
			ps.setInt(5, number);
			ps.setString(6, fromID);
			ps.setString(7, toID);
			ps.setString(8, toID);
			ps.setString(9, fromID);
			rs = ps.executeQuery();
			chatList = new ArrayList<ChatDTO>();
			while(rs.next()) {
				ChatDTO chat = new ChatDTO();
				chat.setChatID(rs.getInt("chatID"));
				chat.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setToID(rs.getString("toID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				if(chatTime > 12) {
					timeType = "오후";
					chatTime -= 12;
				}else if(chatTime == 12) {
					timeType ="오후";
				}
				chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
				chatList.add(chat);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) rs.close();
				if (con != null) con.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return chatList;	
	}
	
	public ArrayList<ChatDTO> getBox(String userID){
		ArrayList<ChatDTO> chatList = null;
		String SQL = "SELECT * FROM CHAT WHERE chatID IN (SELECT MAX(chatID) FROM CHAT WHERE toID = ? OR fromID = ? GROUP BY fromID, toID)";
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, userID);
			ps.setString(2, userID);
			rs = ps.executeQuery();
			chatList = new ArrayList<ChatDTO>();
			while(rs.next()) {
				ChatDTO chat = new ChatDTO();
				chat.setChatID(rs.getInt("chatID"));
				chat.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setToID(rs.getString("toID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				if(chatTime > 12) {
					timeType = "오후";
					chatTime -= 12;
				}else if(chatTime == 12) {
					timeType ="오후";
				}
				chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
				chatList.add(chat);
			}
			
			for(int i = 0; i < chatList.size(); i++) {
				ChatDTO x = chatList.get(i);
				for(int j = 0; j < chatList.size(); j++) {
					ChatDTO y = chatList.get(j);
					if(x.getFromID().equals(y.getToID()) && x.getToID().equals(y.getFromID())) {
						if(x.getChatID() < y.getChatID()) {
							chatList.remove(x);
							i--;
							break;
						} else {
							chatList.remove(y);
							j--;
						}
					}
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) rs.close();
				if (con != null) con.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return chatList;	
	}
	
	public int submit(String fromID, String toID, String chatContent){
		
		String SQL = "INSERT INTO CHAT VALUES (chatID_seq.nextval, ?, ?, ?, sysdate, 0)";
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, fromID);
			ps.setString(2, toID);
			ps.setString(3, chatContent);

			return ps.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (ps != null) rs.close();
				if (con != null) con.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;	
	}
	
	public int readChat(String fromID, String toID) {
		
		String SQL = "UPDATE CHAT SET chatRead = 1 WHERE (fromID = ? AND toID = ?)";
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, toID);
			ps.setString(2, fromID);
			return ps.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (ps != null) rs.close();
				if (con != null) con.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	public int getAllUnreadChat(String userID) {
		
		String SQL = "SELECT COUNT(chatID) FROM chat WHERE toID = ? AND chatRead = 0";
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, userID);
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt("COUNT(chatID)");
			}
			return 0;
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (ps != null) rs.close();
				if (con != null) con.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	
public int getUnreadChat(String fromID, String toID) {
		
		String SQL = "SELECT COUNT(chatID) FROM chat WHERE fromID = ? AND toID = ? AND chatRead = 0";
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, fromID);
			ps.setString(2, toID);
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt("COUNT(chatID)");
			}
			return 0;
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (ps != null) rs.close();
				if (con != null) con.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
}

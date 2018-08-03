package anonymousChat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AnonymousDAO {
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
	
	public ArrayList<AnonymousDTO> getChatList(String nowTime){
		ArrayList<AnonymousDTO> chatList = null;
		String SQL = "SELECT * FROM anonymous_chat WHERE chatTime > ? ORDER BY chatTime";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, nowTime);
			rs = ps.executeQuery();
			chatList = new ArrayList<AnonymousDTO>();
			while(rs.next()) {
				AnonymousDTO dto = new AnonymousDTO();
				dto.setChatID(rs.getInt("chatID"));
				dto.setChatName(rs.getString("chatName"));
				dto.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				if(chatTime > 12) {
					timeType = "오후";
					chatTime -= 12;
				}else if(chatTime == 12) {
					timeType ="오후";
				}
				dto.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
				chatList.add(dto);
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
	
	public ArrayList<AnonymousDTO> getChatListByRecent(int number){
		ArrayList<AnonymousDTO> chatList = null;
		String SQL = "SELECT * FROM anonymous_chat WHERE chatID > (SELECT MAX(chatID) - ? FROM anonymous_chat) ORDER BY chatTime";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setInt(1, number);
			rs = ps.executeQuery();
			chatList = new ArrayList<AnonymousDTO>();
			while(rs.next()) {
				AnonymousDTO dto = new AnonymousDTO();
				dto.setChatID(rs.getInt("chatID"));
				dto.setChatName(rs.getString("chatName"));
				dto.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				if(chatTime > 12) {
					timeType = "오후";
					chatTime -= 12;
				}else if(chatTime == 12) {
					timeType ="오후";
				}
				dto.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
				chatList.add(dto);
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
	
	public ArrayList<AnonymousDTO> getChatListByRecent(String chatID){
		ArrayList<AnonymousDTO> chatList = null;
		String SQL = "SELECT * FROM anonymous_chat WHERE chatID > ? ORDER BY chatTime";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setInt(1, Integer.parseInt(chatID));
			rs = ps.executeQuery();
			chatList = new ArrayList<AnonymousDTO>();
			while(rs.next()) {
				AnonymousDTO dto = new AnonymousDTO();
				dto.setChatID(rs.getInt("chatID"));
				dto.setChatName(rs.getString("chatName"));
				dto.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				if(chatTime > 12) {
					timeType = "오후";
					chatTime -= 12;
				}else if(chatTime == 12) {
					timeType ="오후";
				}
				dto.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
				chatList.add(dto);
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
	
	public int submit(String chatName, String chatContent) {
	
		String SQL = "INSERT INTO anonymous_chat VALUES(?, ?, sysdate, anonymous_seq.nextval)";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, chatName);
			ps.setString(2, chatContent);
			
			return ps.executeUpdate();
		
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
		return -1;
	}
}

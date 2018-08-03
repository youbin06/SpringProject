package user;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;

public class UserDAO {
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

	public int login(String userID, String userPassword) {
		String SQL = "SELECT * FROM userInfo WHERE userID = ?";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, userID);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("userPassword").equals(userPassword)) {
					return 1; // login success
				}
				return 2; // password not correct
			} else {
				return 0; // ID not exist
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {

			}
		}
		return -1; // Database error;
	}
	
	public int registerCheck(String userID) {
		String SQL = "SELECT * FROM userInfo WHERE userID = ?";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, userID);
			rs = ps.executeQuery();
			if (rs.next() || userID.equals("")) {
				return 0; // ID exist
			}else {
				return 1; // OK
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {

			}
		}
		return -1; // Database error;
	}
	
	public int register(String userID, String userPassword, String userName, String userAge, String userGender, String userEmail, String userProfile) {
		String SQL = "INSERT INTO userInfo VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			con = ds.getConnection();  
			ps = con.prepareStatement(SQL);
			ps.setString(1, userID);
			ps.setString(2, userPassword);
			ps.setString(3, userName);
			ps.setInt(4, Integer.parseInt(userAge));
			ps.setString(5, userGender);
			ps.setString(6, userEmail);
			ps.setString(7, userProfile);
			
			return ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {

			}
		}

		return -1; // Database error;
	}
	
	public UserDTO getUser(String userID) {
		UserDTO user = new UserDTO();
		String SQL = "SELECT * FROM userInfo WHERE userID = ?";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, userID);
			rs = ps.executeQuery();
			if (rs.next()) {
				user.setUserID(userID);
				user.setUserPassword(rs.getString("userPassword"));
				user.setUserName(rs.getString("userName"));
				user.setUserAge(rs.getInt("userAge"));
				user.setUserGender(rs.getString("userGender"));
				user.setUserEmail(rs.getString("userEmail"));
				user.setUserProfile(rs.getString("userProfile"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return user;
	}
	
	public int update(String userID, String userPassword, String userName, String userAge, String userGender, String userEmail) {
		String SQL = "UPDATE userInfo SET userPassword = ?, userName = ?, userAge = ?, userGender = ?, userEmail = ? WHERE userID = ?";
		try {
			con = ds.getConnection();  
			ps = con.prepareStatement(SQL);
			ps.setString(1, userPassword);
			ps.setString(2, userName);
			ps.setInt(3, Integer.parseInt(userAge));
			ps.setString(4, userGender);
			ps.setString(5, userEmail);
			ps.setString(6, userID);
			
			return ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {

			}
		}

		return -1; // Database error;
	}
	
	public int profile(String userID, String userProfile) {
		String SQL = "UPDATE userInfo SET userProfile = ? WHERE userID = ?";
		try {
			con = ds.getConnection();  
			ps = con.prepareStatement(SQL);
			ps.setString(1, userProfile);
			ps.setString(2, userID);
			
			return ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {

			}
		}

		return -1; // Database error;
	}
	
	public String getProfile(String userID) {
		String SQL = "SELECT userProfile FROM userInfo WHERE userID = ?";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, userID);
			rs = ps.executeQuery();
			if (rs.next()) {
				if(rs.getString("userProfile").equals("")) {
					return "http://192.168.51.87:8080/UserChat/images/icon.png";
				}
				return "http://192.168.51.87:8080/UserChat/upload/" + rs.getString("userProfile");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {

			}
		}
		return "http://192.168.51.87:8080/UserChat/images/icon.png";
	}
}

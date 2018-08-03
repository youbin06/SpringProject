package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class BoardDAO {
	
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
	
	public int write(String userID, String boardTitle, String boardContent, String boardFile, String boardRealFile) {
		String SQL = "INSERT INTO BOARD VALUES(?, NVL((SELECT MAX(boardID) + 1 FROM BOARD), 1), "
				+ "?, ?, sysdate, 0, ?, ?, NVL((SELECT MAX(boardGroup) + 1 FROM BOARD), 0), 0, 0, 1)";
		try {
			con = ds.getConnection();  
			ps = con.prepareStatement(SQL);
			ps.setString(1, userID);
			ps.setString(2, boardTitle);
			ps.setString(3, boardContent);
			ps.setString(4, boardFile);
			ps.setString(5, boardRealFile);
			
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
	
	public BoardDTO getBoard(String boardID) {
		BoardDTO board = new BoardDTO();
		String SQL = "SELECT * FROM BOARD WHERE boardID = ?";
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, boardID);
			rs = ps.executeQuery();
			if (rs.next()) {
				board.setUserID(rs.getString("userID"));
				board.setBoardID(rs.getInt("boardID"));
				board.setBoardTitle(rs.getString("boardTitle").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				board.setBoardDate(rs.getString("boardDate").substring(0, 11));
				board.setBoardContent(rs.getString("boardContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				board.setBoardHit(rs.getInt("boardHit"));
				board.setBoardFile(rs.getString("boardFile"));
				board.setBoardRealFile(rs.getString("boardRealFile"));
				board.setBoardGroup(rs.getInt("boardGroup"));
				board.setBoardSequence(rs.getInt("boardSequence"));
				board.setBoardLevel(rs.getInt("boardLevel"));
				board.setBoardAvailable(rs.getInt("boardAvailable"));
				this.hit(boardID);
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
		return board;
	}
	
	public ArrayList<BoardDTO> getList(String pageNumber) {
		ArrayList<BoardDTO> boardList = null;
		String SQL = "SELECT * FROM BOARD WHERE boardGroup > (SELECT MAX(boardGroup) FROM board) - ? "
				+ "AND boardGroup <= (SELECT MAX(boardGroup) FROM board) - ? ORDER BY boardGroup DESC, boardSequence ASC";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setInt(1, Integer.parseInt(pageNumber) * 10);
			ps.setInt(2, (Integer.parseInt(pageNumber) - 1) * 10);
			rs = ps.executeQuery();
			boardList = new ArrayList<BoardDTO>();
			while (rs.next()) {
				BoardDTO board = new BoardDTO();
				board.setUserID(rs.getString("userID"));
				board.setBoardID(rs.getInt("boardID"));
				board.setBoardTitle(rs.getString("boardTitle").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				board.setBoardDate(rs.getString("boardDate").substring(0, 11));
				board.setBoardContent(rs.getString("boardContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				board.setBoardHit(rs.getInt("boardHit"));
				board.setBoardFile(rs.getString("boardFile"));
				board.setBoardRealFile(rs.getString("boardRealFile"));
				board.setBoardGroup(rs.getInt("boardGroup"));
				board.setBoardSequence(rs.getInt("boardSequence"));
				board.setBoardLevel(rs.getInt("boardLevel"));
				board.setBoardAvailable(rs.getInt("boardAvailable"));
				boardList.add(board);
			
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
		return boardList;
	}
	
	private int hit(String boardID) {
		String SQL = "UPDATE BOARD SET boardHIT = boardHIT + 1 WHERE boardID = ?";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, boardID);
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
		return -1;
	}
	
	public String getFile(String boardID) {
	
		String SQL = "SELECT boardFile FROM BOARD WHERE boardID = ?";
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, boardID);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("boardFile");
			}
			return "";
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
		return "";
	}
	

	public String getRealFile(String boardID) {
		
		String SQL = "SELECT boardRealFile FROM BOARD WHERE boardID = ?";
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setString(1, boardID);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("boardRealFile");
			}
			return "";
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
		return "";
	}
	
	public int update(String boardID, String boardTitle, String boardContent, String boardFile, String boardRealFile) {
		String SQL = "UPDATE BOARD SET boardTitle = ?, boardContent = ?, boardFile = ?, boardRealFile = ? WHERE boardID = ?";
		try {
			con = ds.getConnection();  
			ps = con.prepareStatement(SQL);
			ps.setString(1, boardTitle);
			ps.setString(2, boardContent);
			ps.setString(3, boardFile);
			ps.setString(4, boardRealFile);
			ps.setInt(5, Integer.parseInt(boardID));
			
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
	
	public int delete(String boardID) {
		String SQL = "UPDATE board SET boardAvailable = 0 WHERE boardID = ?";
		try {
			con = ds.getConnection();  
			ps = con.prepareStatement(SQL);
			ps.setInt(1, Integer.parseInt(boardID));
			
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
	
	public int reply(String userID, String boardTitle, String boardContent, String boardFile, String boardRealFile, BoardDTO parent) {
		String SQL = "INSERT INTO BOARD VALUES(?, NVL((SELECT MAX(boardID) + 1 FROM BOARD), 1), "
				+ "?, ?, sysdate, 0, ?, ?, ?, ?, ?, 1)";
		try {
			con = ds.getConnection();  
			ps = con.prepareStatement(SQL);
			ps.setString(1, userID);
			ps.setString(2, boardTitle);
			ps.setString(3, boardContent);
			ps.setString(4, boardFile);
			ps.setString(5, boardRealFile);
			ps.setInt(6, parent.getBoardGroup());
			ps.setInt(7, parent.getBoardSequence() + 1);
			ps.setInt(8, parent.getBoardLevel() + 1);
			
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
	
	public int replyUpdate(BoardDTO parent) {
		String SQL = "UPDATE BOARD SET boardSequence = boardSequence + 1 WHERE boardGroup = ? AND boardSequence > ?";
		try {
			con = ds.getConnection();  
			ps = con.prepareStatement(SQL);
			ps.setInt(1, parent.getBoardGroup());
			ps.setInt(2, parent.getBoardSequence());

			
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
	
	public boolean nextPage(String pageNumber) {
		
		String SQL = "SELECT * FROM BOARD WHERE boardGroup >= ?";
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setInt(1, Integer.parseInt(pageNumber) * 10);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
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
		return false;
	}
	
	public int targetPage(String pageNumber) {
		
		String SQL = "SELECT COUNT(boardGroup) FROM BOARD WHERE boardGroup > ?";
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(SQL);
			ps.setInt(1, (Integer.parseInt(pageNumber)-1) * 10);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) / 10;
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
		return 0;
	}


}

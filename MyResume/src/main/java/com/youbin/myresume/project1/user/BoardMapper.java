package com.youbin.myresume.project1.user;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardMapper {

	@Autowired
	private SqlSession sqlSession;

	public List<BoardDTO> getList(int pageNumber) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("pageNumber1", pageNumber * 10);
		map.put("pageNumber2", (pageNumber -1) * 10);
		List<BoardDTO> list = sqlSession.selectList("getList", map);
		
		return list;
	}
	
	public int targetPage(int pageNumber) {
		int result = 0;
		int pageNum = (pageNumber - 1) * 10;
		result = (sqlSession.selectOne("targetPage", pageNum));
		return result / 10;
	}
	
	public void write(String userID, String boardTitle, String boardContent, String boardFile, String boardRealFile) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userID", userID);
		map.put("boardTitle", boardTitle);
		map.put("boardContent", boardContent);
		map.put("boardFile", boardFile);
		map.put("boardRealFile", boardRealFile);
		sqlSession.insert("write",map);
	}
	
	public BoardDTO getBoard(String boardID) {
		BoardDTO dto = sqlSession.selectOne("getBoard", boardID);
		return dto;
	}
	
	public void hit(String boardID) {
		sqlSession.update("hit", boardID);
	}
	
	public String getRealFile(String boardID) {
		BoardDTO dto = null;
		dto = sqlSession.selectOne("getRealFile", boardID);
		if(dto != null) {
			return dto.getBoardRealFile();
		}else {
			return "";
		}
	}
	
	public int delete(String boardID) {
		return sqlSession.update("delete", boardID);
	}
	
	public String getFile(String boardID) {
		BoardDTO dto = null;
		dto = sqlSession.selectOne("getFile", boardID);
		if(dto != null) {
			return dto.getBoardFile();
		}else {
			return "";
		}
	}
	
	public void update(String boardID, String boardTitle, String boardContent, String boardFile, String boardRealFile) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("boardTitle", boardTitle);
		map.put("boardContent", boardContent);
		map.put("boardFile", boardFile);
		map.put("boardRealFile", boardRealFile);
		map.put("boardID", boardID);
		
		sqlSession.update("updateBoard", map);
	}
	
	public void replyUpdate(BoardDTO parent) {
		sqlSession.update("replyUpdate", parent);
	}
	
	public void reply(String userID, String boardTitle, String boardContent, String boardFile, String boardRealFile, BoardDTO parent ) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userID", userID);
		map.put("boardTitle", boardTitle);
		map.put("boardContent", boardContent);
		map.put("boardFile", boardFile);
		map.put("boardRealFile", boardRealFile);
		map.put("boardGroup", parent.getBoardGroup()+"");
		int boardSequence = parent.getBoardSequence() + 1;
		map.put("boardSequence", boardSequence+"");
		int boardLevel = parent.getBoardLevel() + 1;
		map.put("boardLevel", boardLevel+"");
		sqlSession.insert("reply", map);
	}

}

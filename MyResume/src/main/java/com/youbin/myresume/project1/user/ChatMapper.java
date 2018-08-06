package com.youbin.myresume.project1.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatMapper {
	
	@Autowired 
	private SqlSession sqlSession;
	
	public int submit(String fromID, String toID, String chatContent) {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("fromID", fromID);
		map.put("toID", toID);
		map.put("chatContent", chatContent);
		return sqlSession.insert("submit", map);
	}
	
	public List<ChatDTO> getChatListByRecent(String fromID, String toID, int number){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("fromID", fromID);
		map.put("toID", toID);
		map.put("number", number+"");
		return sqlSession.selectList("getChatListByRecent", map);
	}
	
	public List<ChatDTO> getChatListByID(String fromID, String toID, String chatID){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("fromID", fromID);
		map.put("toID", toID);
		map.put("chatID", chatID);
		return sqlSession.selectList("getChatListByID", map);
	}
	
	public void readChat(String fromID, String toID) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("fromID", fromID);
		map.put("toID", toID);
		sqlSession.update("readChat", map);
	}
	
	public ArrayList<ChatDTO> getBox(String userID){
		ArrayList<ChatDTO> chatList = new ArrayList<ChatDTO>();
		List<ChatDTO> list = sqlSession.selectList("getBox", userID);
		chatList = (ArrayList<ChatDTO>) list;
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
		
		return chatList;

	}
	
	public int getUnreadChat(String fromID, String toID) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("fromID", fromID);
		map.put("toID", toID);
		return sqlSession.selectOne("getUnreadChat", map);
	}
	
	public int getAllUnreadChat(String userID) {
		return sqlSession.selectOne("getAllUnreadChat", userID);
	}

}

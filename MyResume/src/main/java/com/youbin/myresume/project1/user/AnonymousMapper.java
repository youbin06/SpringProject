package com.youbin.myresume.project1.user;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnonymousMapper {
	
	@Autowired 
	private SqlSession sqlSession;
	
	public int submit(String chatName, String chatContent) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("chatName", chatName);
		map.put("chatContent", chatContent);
		return sqlSession.insert("anonymousSubmit", map);
	}
	
	public List<AnonymousDTO> getChatList(String nowTime){
		return sqlSession.selectList("getAnonymousChatList", nowTime);
	}
	
	public List<AnonymousDTO> getChatListByRecent(int number){
		return sqlSession.selectList("getAnonymousChatListByRecent", number);
	}
	
	public List<AnonymousDTO> getChatListByRecent(String chatID){
		return sqlSession.selectList("getAnonymousChatListBychatID", chatID);
	}

}

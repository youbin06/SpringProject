package com.youbin.myresume.project1.user;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int registerCheck(String userID) {
		UserDTO dto = null;
		dto = sqlSession.selectOne("registerCheck",userID);
		if(dto == null) {
			return 1; // OK
		}else {
			return 0; // ID exist
		}
	}
	
	public int register(UserDTO dto) {
		int result = this.registerCheck(dto.getUserID());
		if(result ==  0) {
			return 0; // ID exist
		}else {
			return sqlSession.insert("register",dto);
		}

	}
	
	public int login(String userID, String userPassword) {
	
		int result = this.registerCheck(userID);
		if(result == 0) {
			UserDTO dto = sqlSession.selectOne("registerCheck",userID);
			if(dto.getUserPassword().equals(userPassword)) {
				return 1; //login success
			}else {
				return 2; //password not correct;
			}
		}else {
			return 0; //ID not exist;
		}
	
	}
	
	public UserDTO getUser(String userID) {
		return sqlSession.selectOne("registerCheck",userID);
	}
	
	public int update(UserDTO dto) {
		return sqlSession.update("update", dto);
	}
	
	public void profile(String userID, String fileName) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userID", userID);
		map.put("fileName", fileName);
		sqlSession.update("profile", map);
	}
	
	public String getProfile(String userID) {
		UserDTO dto = null;
		dto = sqlSession.selectOne("getProfile", userID);
		if(dto == null) {
			return "http://localhost:8080/myresume/resources/images/icon.png";
		}else {
			if(dto.getUserProfile().equals("")) {
				return "http://localhost:8080/myresume/resources/images/icon.png";
			}else {
				return "http://localhost:8080/myresume/resources/upload/" + dto.getUserProfile();
			}
		}
	}

}













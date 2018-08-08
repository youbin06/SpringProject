package com.youbin.myresume.project1.user;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class User2Mapper {
	@Autowired
	private SqlSession sqlSession;
	
	public int login(String userID, String userPassword) {
		String checkPassword = "";
		checkPassword = sqlSession.selectOne("passwordCheck", userID);
		
		if(!checkPassword.equals("")) {
			if(checkPassword.equals(userPassword)) {
				return 1; //login success
			}else {
				return 0; //password not correct
			}
		}
		return -1; //ID not exist
	}
	
	public int register(User2DTO user) {
		return sqlSession.insert("registerUser2", user);
	}
	
	public boolean getUserEmailChecked(String userID) {
		String userEmailChecked = "";
		userEmailChecked = sqlSession.selectOne("getUserEmailChecked", userID);
		if(!userEmailChecked.equals("")) {
			if(userEmailChecked.equals("1")) {
				return true;
			}else {
				return false;
			}
		}
		return false;
	}
	
	public boolean setUserEmailChecked(String userID) {
		sqlSession.update("setUserEmailChecked", userID);
		return true;
	}
	
	public String getUserEmail(String userID) {
		String userEmail = "";
		userEmail = sqlSession.selectOne("getUserEmail", userID);
		if(!userEmail.equals("")) {
			return userEmail;
		}
		return null;
	}
}

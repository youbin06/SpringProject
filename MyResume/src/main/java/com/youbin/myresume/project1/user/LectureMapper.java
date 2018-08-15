package com.youbin.myresume.project1.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LectureMapper {
	@Autowired
	private SqlSession sqlSession;
	
	public int write(EvaluationDTO evaluationDTO) {
		return sqlSession.insert("writeLecture", evaluationDTO);
	}
	
	public ArrayList<EvaluationDTO> getEvaluationList(String lectureDivide, String searchType, String search, int pageNumber){
		ArrayList<EvaluationDTO> evaluationList = null;
		if(lectureDivide.equals("전체")) {
			lectureDivide = "";
		}
		List<EvaluationDTO> list = null;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("lectureDivide", "%"+lectureDivide+"%");
		map.put("search", "%"+search+"%");
		int pageNumber1 = (pageNumber * 5 + 1);
		int pageNumber2 = pageNumber1 + 4;
		map.put("pageNumber1", pageNumber1+"");
		map.put("pageNumber2", pageNumber2+"");

		if(searchType.equals("최신순")) {
			list = sqlSession.selectList("evaluationListByRecent", map);
			evaluationList = (ArrayList<EvaluationDTO>) list;
			return evaluationList;
		}else {
			list = sqlSession.selectList("evaluationListByLike", map);
			evaluationList = (ArrayList<EvaluationDTO>) list;
			return evaluationList;
		}
	}
	
	public int nextPage(String lectureDivide, String searchType, String search, int pageNumber) {
		
		if(lectureDivide.equals("전체")) {
			lectureDivide = "";
		}
		List<EvaluationDTO> list = null;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("lectureDivide", "%"+lectureDivide+"%");
		map.put("search", "%"+search+"%");
		int pageNumber1 = (pageNumber + 1) * 5;
		map.put("pageNumber1", pageNumber1+"");
		
		if(searchType.equals("최신순")) {
			list = sqlSession.selectList("nextPageByRecent", map);
			if(list.isEmpty()) return 0;
		}else {
			list = sqlSession.selectList("nextPageByLike", map);
			if(list.isEmpty()) return 0;
		}
		
		return 1;
	}
	
	public int likeCount(String evaluationID) {
		return sqlSession.update("likeCount", evaluationID);
	}
	
	public int deleteAssessment(String evaluationID) {
		return sqlSession.delete("deleteAssessment", evaluationID);
	}
	
	public String getUserID(String evaluationID) {
		
		return sqlSession.selectOne("getUserID", evaluationID);
	}
	
	public int like(String userID, String evaluationID, String userIP) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userID", userID);
		map.put("evaluationID", evaluationID);
		map.put("userIP", userIP);
		List<String> list = null;
		list = sqlSession.selectList("duplicationLike", evaluationID);
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).equals(userID)) {
				return 0;
			}
		}
		
		return sqlSession.insert("like", map);
		

	}
}

package com.youbin.myresume;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.youbin.myresume.project1.user.BoardDTO;
import com.youbin.myresume.project1.user.BoardMapper;
import com.youbin.myresume.project1.user.UserMapper;

@Controller
public class Project1BoardController {

	@Autowired
	BoardMapper boardMapper;
	UserMapper userMapper;
	
	@RequestMapping(value = "/boardView.do")
	public String boardView(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String pageNumber = request.getParameter("pageNumber");
		if(pageNumber == null || pageNumber.trim().equals("") || pageNumber.equals("null")) {
			pageNumber = "1";
		}
		
		List<BoardDTO> boardList = boardMapper.getList(Integer.parseInt(pageNumber));
		request.setAttribute("boardList", boardList);
	
		int targetPage = boardMapper.targetPage(Integer.parseInt(pageNumber));
		request.setAttribute("targetPage", targetPage);
		return "project1/board/boardView";
	
	}
	
	@RequestMapping(value = "/boardWrite.do", method=RequestMethod.GET)
	public String boardWriteForm()  {
		return "project1/board/boardWrite";
	}
	
	@RequestMapping(value = "/boardWrite.do", method=RequestMethod.POST)
	public String boardWrite(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		MultipartRequest multi = null;
		int fileMaxSize = 10 * 1024 * 1024;
		String savePath = request.getRealPath("/resources/upload").replaceAll("\\\\", "/");
		try {
			multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
		}catch(Exception e) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "파일크기는 10MB를 넘을 수 없습니다.");
			return "project1/board/boardWrite";
		}
		String userID = multi.getParameter("userID");
		HttpSession session = request.getSession();
		if(!userID.equals((String)session.getAttribute("userID"))){
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "접근할 수 없습니다.");
			return "project1/index";
		}
		String boardTitle = multi.getParameter("boardTitle");
		String boardContent = multi.getParameter("boardContent");
		
		if(boardTitle == null || boardTitle.equals("") || boardContent == null || boardContent.equals("")) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "내용을 모두 채워주세요.");
			return "project1/board/boardWrite";
		}
		String boardFile = "";
		String boardRealFile = "";
		File file = multi.getFile("boardFile");
		if(file != null) {
			boardFile = multi.getOriginalFileName("boardFile");
			boardRealFile = file.getName();
		}
		
		boardMapper.write(userID, boardTitle, boardContent, boardFile, boardRealFile);
		session.setAttribute("messageType", "성공 메시지");
		session.setAttribute("messageContent", "성공적으로 게시물이 작성되었습니다.");
		this.boardView(request, response);
		return "project1/board/boardView";
	}
	@RequestMapping(value = "/boardShow.do")
	public String boardShow(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		request.setAttribute("userID", userID);
		
		String boardID = request.getParameter("boardID");
		BoardDTO boardDTO = boardMapper.getBoard(boardID);
		
		if(boardDTO.getBoardAvailable() == 0) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "삭제된 게시물 입니다.");
			this.boardView(request, response);
			return "project1/board/boardView";
		}
		boardMapper.hit(boardID);
		request.setAttribute("boardDTO", boardDTO);
		
		return "project1/board/boardShow";
	}
	
	@RequestMapping(value = "/boardDelete.do")
	public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String boardID = request.getParameter("boardID");
		
		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("userID");
		
		if(boardID == null || boardID.equals("")) {
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("messageType", "성공 메시지");
			request.getSession().setAttribute("messageContent", "접근할 수 없습니다.");
			return "project1/index"; 	
		}
		BoardDTO board = boardMapper.getBoard(boardID);
		if(!userID.equals(board.getUserID())) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "접근할 수 없습니다.");
			return "project1/index"; 
		}
		
		String savePath = request.getRealPath("/resources/upload").replaceAll("\\\\", "/");
		String prev = boardMapper.getRealFile(boardID);
		
		int result = boardMapper.delete(boardID);
		
		if(result == -1) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "접근할 수 없습니다.");
			return "project1/index";
		}else {
			File prevFile = new File(savePath + "/" + prev);
			if(prevFile.exists()) {
				prevFile.delete();
			}
			session.setAttribute("messageType", "성공 메시지");
			session.setAttribute("messageContent", "삭제에 성공했습니다.");
			this.boardView(request, response);
			return "project1/board/boardView";
		}
	}
	
	@RequestMapping(value ="/boardUpdate.do", method=RequestMethod.GET)
	public String updateForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String boardID = request.getParameter("boardID");
		
		BoardDTO boardDTO = boardMapper.getBoard(boardID);
		request.setAttribute("boardDTO", boardDTO);
		return "project1/board/boardUpdate";
	}
	
	@RequestMapping(value = "boardUpdate.do", method=RequestMethod.POST)
	public String update(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		MultipartRequest multi = null;
		int fileMaxSize = 10 * 1024 * 1024;
		String savePath = request.getRealPath("/resources/upload").replaceAll("\\\\", "/");
		try {
			multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
		}catch(Exception e) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "파일크기는 10MB를 넘을 수 없습니다.");
			return "project1/board/boardWrite";
		}
		
		String userID = multi.getParameter("userID");
		HttpSession session = request.getSession();
		if(!userID.equals((String)session.getAttribute("userID"))){
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "접근할 수 없습니다.");
			return "project1/index";
		}
		
		String boardID = multi.getParameter("boardID");
		if(boardID == null || boardID.equals("")) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "접근할 수 없습니다.");
			return "project1/index";
		}
		
		BoardDTO board = boardMapper.getBoard(boardID);
		if(!userID.equals(board.getUserID())) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "접근할 수 없습니다.");
			return "project1/index";
		}
		
		String boardTitle = multi.getParameter("boardTitle");
		String boardContent = multi.getParameter("boardContent");
		
		if(boardTitle == null || boardTitle.equals("") || boardContent == null || boardContent.equals("")) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "내용을 모두 채워주세요.");
			return "project1/board/boardWrite";
		}
		
		String boardFile = "";
		String boardRealFile = "";
		File file = multi.getFile("boardFile");
		if(file != null) {
			boardFile = multi.getOriginalFileName("boardFile");
			boardRealFile = file.getName();
			String prev = boardMapper.getRealFile(boardID);
			File prevFile = new File(savePath + "/" + prev);
			if(prevFile.exists()) {
				prevFile.delete();
			}
		}else {
			boardFile = boardMapper.getFile(boardID);
			boardRealFile = boardMapper.getRealFile(boardID);
		}
		
		boardMapper.update(boardID, boardTitle, boardContent, boardFile, boardRealFile);
		
		session.setAttribute("messageType", "성공 메시지");
		session.setAttribute("messageContent", "성공적으로 게시물이 수정되었습니다.");
		this.boardView(request, response);
		return "project1/board/boardView";
	}
}

<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html>
<html>
	<%
		String userID = null;
		if(session.getAttribute("userID") != null) {
			userID = (String)session.getAttribute("userID");
		}
		
		if(userID == null){
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "현재 로그인이 되어 있지 않습니다.");
			response.sendRedirect("loginForm.project2");
			return;
		}
		
		int pageNumber = 0;
		if(request.getParameter("pageNumber") != null){
			try{
				pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			}catch(Exception e){
				session.setAttribute("messageType", "오류 메시지");
				session.setAttribute("messageContent", "페이지 번호가 잘못되었습니다.");
				response.sendRedirect("index.project2");
				return;
			}
		}

		String lectureDivide = "전체";
		if(request.getParameter("lectureDivide") != null){
			lectureDivide = request.getParameter("lectureDivide"); 
		}
		
		String search = "";
		if(request.getParameter("search") != null){
			search = request.getParameter("search"); 
		}
		
		String searchType ="최신순";
		if(request.getParameter("searchType") != null){
			searchType = request.getParameter("searchType"); 
		}
		
		String nextPage = request.getAttribute("nextPage").toString();
		if(nextPage == null || nextPage.equals("")){
			nextPage = "0";
		}
				
	%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=deive-width, initial-scale=1">
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.css"/>">
	<link rel="stylesheet" href="<c:url value="/resources/css/custom.css"/>">
	<title>Spring Final Project #2</title>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="<c:url value="/resources/js/bootstrap.js"/>"></script>
	<script src="https://unpkg.com/popper.js@1.12.9/dist/umd/popper.min.js"></script>
	<title>Spring Final Project #2</title>
	<script type="text/javascript">
		function checkEvaluation(){
			if(evaluation_f.lectureName.value == "" || evaluation_f.professorName.value == "" ||
					evaluation_f.evaluationTitle.value == "" || evaluation_f.evaluationContent.value == ""){
				$('#checkMessage').html('모든 내용을 입력하세요.');
				$('#checkType').attr('class', 'modal-content panel-warning');
				$('#checkModal').modal("show");
				return
			}
			document.evaluation_f.submit()
		}
		
		function checkReport(){
			if(report_f.reportTitle.value == "" || report_f.reportContent.value == ""){
				$('#checkMessage').html('모든 내용을 입력하세요.');
				$('#checkType').attr('class', 'modal-content panel-warning');
				$('#checkModal').modal("show");
				return
			}
			document.report_f.submit()
		}
	</script>
</head>
<body>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" 
					data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only"></span>
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.project2">Spring Final Project #2</a>
			</div>
			<div class="collaspe navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="index.project2">소개<span class="sr-only"></span></a></li>
					<li><a href="instructor.project2">강사진</a></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
							aria-haspopup="true" aria-expanded="false">강의<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="lecture.project2?lectureName=HTML">HTML</a></li>
							<li><a href="lecture.project2?lectureName=CSS">CSS</a></li>
							<li><a href="lecture.project2?lectureName=JavaScript">JavaScript</a></li>
						</ul>
					</li>
					<li class="active"><a href="lectureAssessment.project2">강의평가</a></li>
					<li><a href="#">고객센터</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
							aria-haspopup="true" aria-expanded="false">접속하기<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="logout.project2">로그아웃</a></li>
						</ul>
					</li>
				</ul>
			</div>			
		</div>
	</nav> 
	<section class="container">
		<form method="get" action="./evaluationSearch.project2" class="form-inline mt-3">
			<select name="lectureDivide" class="form-control mx-1 mt-2">
				<option value="전체">전체</option>
				<option value="HTML" <%if(lectureDivide.equals("HTML")) out.println("selected"); %>>HTML</option>
				<option value="CSS" <%if(lectureDivide.equals("CSS")) out.println("selected"); %>>CSS</option>
				<option value="JavaScript" <%if(lectureDivide.equals("JavaScript")) out.println("selected"); %>>JavaScript</option>
			</select>
			<select name="searchType" class="form-control mx-1 mt-2">
				<option value="최신순">최신순</option>
				<option value="추천순" <%if(searchType.equals("추천순")) out.println("selected"); %>>추천순</option>
			</select>
			<input type="text" name="search" class="form-control mx-1 mt-2" placeholder="내용을 입력하세요">
			<button type="submit" class="btn btn-primary mx-1 mt-2">검색</button>
			<a class="btn btn-primary mx-1 mt-2" data-toggle="modal" href="#registerModal">등록하기</a>
			<a class="btn btn-danger mx-1 mt-2" data-toggle="modal" href="#reportModal">신고</a>
		</form>
		<c:if test="${evaluationList != null}">
			<c:forEach var="evaluationList" items="${evaluationList }">
				<div class="card bg-light mt-3">
					<div class="card-header bg-light">
						<div class="row">
							<div class="col-sm-8 text-left">
								${evaluationList.lectureName }&nbsp;
								<small>${evaluationList.professorName }</small>
							</div>
							<div class="col-sm-4 text-right">
								종합<span style="color: red;">&nbsp;${evaluationList.totalScore }</span></div>
						</div>
					</div>
					<div class="card-body">
						<h5 class="card-title">${evaluationList.evaluationTitle }&nbsp;
							<small>${evaluationList.semesterDivide }</small>
						</h5>
						<p class="card-text">${evaluationList.evaluationContent }</p>
						<div class="row">
							<div class="col-sm-8 text-left">
								성적<span style="color: red;">${evaluationList.creditScore }</span>
								난이도<span style="color: red;">${evaluationList.comfortableScore }</span>
								강의<span style="color: red;">${evaluationList.lectureScore }</span>
								<span style="color: green;">(추천: ${evaluationList.likecount })</span>
							</div>
							<div class="col-sm-4 text-right">
								<a onclick="return confirm('추천하시겠습니까?')" href="./likeAssessment?evaluationID=${evaluationList.evaluationID }">추천</a>
								<a onclick="return confirm('삭제하시겠습니까?')" href="./deleteAssessment?evaluationID=${evaluationList.evaluationID }">삭제</a>
							</div>
						</div>
					</div>
				</div>
				<br>
			</c:forEach>
		</c:if>
		<%
		if(pageNumber > 0){
		%>
			<a href="./lectureAssessment.project2?lectureDivide=<%=URLEncoder.encode(lectureDivide,"UTF-8")%>
			&searchType=<%=URLEncoder.encode(searchType,"UTF-8")%>&search=<%=URLEncoder.encode(search,"UTF-8")%>
			&pageNumber=<%=pageNumber - 1%>" class="btn btn-success">이전</a>
		<%		
		} if(nextPage.equals("1")){
		%>
			<a href="./lectureAssessment.project2?lectureDivide=<%=URLEncoder.encode(lectureDivide,"UTF-8")%>
			&searchType=<%=URLEncoder.encode(searchType,"UTF-8")%>&search=<%=URLEncoder.encode(search,"UTF-8")%>
			&pageNumber=<%=pageNumber + 1%>" class="btn btn-success pull-right">다음</a>
		<%	
		}
		%>
	</section>
	<br>
	<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="modal">평가 등록</h4>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form name="evaluation_f" action="./evaluationRegisterAction.project2" method="post">
						<div class="form-row">
							<div class="form-group col-sm-6">
								<label>강의명</label>
								<input type="text" name="lectureName" class="form-control" maxlength="20">
							</div>
							<div class="form-group col-sm-6">
								<label>강사명</label>
								<input type="text" name="professorName" class="form-control" maxlength="20">
							</div>
						</div>
						<div class="form-row">
							<div class="form-group col-sm-4">
								<label>수강연도</label>
								<select name="lectureYear" class="form-control">
									<option value="2017">2017</option>
									<option value="2018" selected>2018</option>
								</select>
							</div>
							<div class="form-group col-sm-4">
								<label>수강학기</label>
								<select name="semesterDivide" class="form-control">
									<option value="1학기">1학기</option>
									<option value="여름학기" selected>여름학기</option>
									<option value="2학기">1학기</option>
									<option value="겨울학기">겨울학기</option>
								</select>
							</div>
							<div class="form-group col-sm-4">
								<label>강의구분</label>
								<select name="lectureDivide" class="form-control">
									<option value="HTML" selected>HTML</option>
									<option value="CSS">CSS</option>
									<option value="JavaScript">JavaScript</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label>제목</label>
							<input type="text" name="evaluationTitle" class="form-control" maxlength="30">						
						</div>
						<div class="form-group">
							<label>내용</label>
							<textarea name="evaluationContent" class="form-control" maxlength="2048" style="height:180px;"></textarea>						
						</div>
						<div class="form-row">
							<div class="form-group col-sm-3">
								<label>종합</label>
								<select name="totalScore" class="form-control">
									<option value="A" selected>A</option>
									<option value="B" >B</option>
									<option value="C" >C</option>
									<option value="D" >D</option>
									<option value="F" >F</option>
								</select>
							</div>
							<div class="form-group col-sm-3">
								<label>성적</label>
								<select name="creditScore" class="form-control">
									<option value="A" selected>A</option>
									<option value="B" >B</option>
									<option value="C" >C</option>
									<option value="D" >D</option>
									<option value="F" >F</option>
								</select>
							</div>
							<div class="form-group col-sm-3">
								<label>난이도</label>
								<select name="comfortableScore" class="form-control">
									<option value="A" selected>A</option>
									<option value="B" >B</option>
									<option value="C" >C</option>
									<option value="D" >D</option>
									<option value="F" >F</option>
								</select>
							</div>
							<div class="form-group col-sm-3">
								<label>강의</label>
								<select name="lectureScore" class="form-control">
									<option value="A" selected>A</option>
									<option value="B" >B</option>
									<option value="C" >C</option>
									<option value="D" >D</option>
									<option value="F" >F</option>
								</select>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
							<button type="button" class="btn btn-primary" onclick="checkEvaluation();">등록하기</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="reportModal" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="modal">신고하기</h4>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form name="report_f" action="./reportAction.project2" method="post">
						<div class="form-group">
							<label>신고 제목</label>
							<input type="text" name="reportTitle" class="form-control" maxlength="30">						
						</div>
						<div class="form-group">
							<label>신고 내용</label>
							<textarea name="reportContent" class="form-control" maxlength="2048" style="height:180px;"></textarea>						
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
							<button type="button" class="btn btn-danger" onclick="checkReport()">신고하기</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<%
		String messageContent = null;
		if (session.getAttribute("messageContent") != null){
			messageContent = (String) session.getAttribute("messageContent");	
		}
		String messageType = null;
		if (session.getAttribute("messageType") != null){
			messageType = (String) session.getAttribute("messageType");	
		}
		if(messageType != null){
	%>
	<div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div class="modal-content <% if(messageType.equals("오류 메시지")) out.println("panel-warning"); else out.println("panel-success"); %>">
					<div class="modal-header panel-heading">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times</span>
							<span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">
							<%=messageType %>
						</h4>
					</div>
					<div class="modal-body">
						<%= messageContent %>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		$('#messageModal').modal("show");
	</script>
	<% 		
			session.removeAttribute("messageContent");
			session.removeAttribute("messageType");
		}
	%>
	<div class="modal fade" id="checkModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div id="checkType" class="modal-content panel-info" >
					<div class="modal-header panel-heading">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times</span>
							<span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">
							확인 메시지
						</h4>
					</div>
					<div id="checkMessage" class="modal-body">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
					</div>
				</div>
			</div>
		</div>
	</div>


</body>
</html>
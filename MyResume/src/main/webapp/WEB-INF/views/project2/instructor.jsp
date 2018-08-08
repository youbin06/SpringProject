<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%
		String userID = null;
		if(session.getAttribute("userID") != null) {
			userID = (String)session.getAttribute("userID");
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
					<li class="active"><a href="instructor.project2">강사진</a></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
							aria-haspopup="true" aria-expanded="false">강의<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="lecture.project2?lectureName=HTML">HTML</a></li>
							<li><a href="lecture.project2?lectureName=CSS">CSS</a></li>
							<li><a href="lecture.project2?lectureName=JavaScript">JavaScript</a></li>
						</ul>
					</li>
					<li><a href="lectureAssessment.project2">강의평가</a></li>
					<li><a href="#">고객센터</a></li>
				</ul>
				<%
					if(userID == null){
				%>
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
							aria-haspopup="true" aria-expanded="false">접속하기<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="loginForm.project2">로그인</a></li>
							<li><a href="registerForm.project2">회원가입</a></li>
						</ul>
					</li>
				</ul>
				<%
					} else {
				%>
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
							aria-haspopup="true" aria-expanded="false">접속하기<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="logout.project2">로그아웃</a></li>
						</ul>
					</li>
				</ul>
				<%
					}
				%>
			</div>			
		</div>
	</nav>
<div class="container">
	<div class="row">
		<div class="col-xs-12">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">
						<span class="glyphicon glyphicon-tags"></span>
						&nbsp;&nbsp;라이언 강사
					</h3>
				</div>
				<div class="panel panel-body">
					<div class="media">
						<div class="media-left">
						<a href="#">
							<img class="media-object" style="width: 180px; height: 150px;" src="<c:url value="/resources/images/ryon.jpg"/>" alt="개발자 이미지">
						</a>
						</div>
						<div class="media-body">
							<h4 class="media-heading">라이언</h4><br>
								<p>HTML 강의를 맡은 웹개발 강의 서비스 대표 강사입니다.</p>
								<p>무뚝뚝한 표정과는 다르게 배려심이 많고 따뜻한 리더십을 가지고 있습니다.</p>
						</div>
					</div>
				</div>
				<table class="table">
					<thead>
						<tr>
							<th>강사명</th>
							<th>강의 번호</th>
							<th>강의 제목</th>
							<th>강의 날짜</th>
						</tr>
					</thead>
					<tbody>
						<tr style="text-align:center;">
							<td>라이언</td>
							<td>1</td>
							<td><a href="lecture.project2?lectureName=HTML">HTML 기초 프로그래밍 강좌</a></td>
							<td>2018-08-07</td>
						</tr>
						<tr style="text-align:center;">
							<td>라이언</td>
							<td>2</td>
							<td><a href="lecture.project2?lectureName=HTML">HTML 기초 프로그래밍 강좌</a></td>
							<td>2018-08-07</td>
						</tr>
						<tr style="text-align:center;">
							<td>라이언</td>
							<td>3</td>
							<td><a href="lecture.project2?lectureName=HTML">HTML 기초 프로그래밍 강좌</a></td>
							<td>2018-08-07</td>
						</tr>
					</tbody>
				</table>
				<div class="panel-footer">
					<blockquote>&nbsp;&nbsp;웹개발의 기초가 되는 HTML! 열심히 강의하겠습니다.</blockquote>
				</div>
			</div>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">
						<span class="glyphicon glyphicon-tags"></span>
						&nbsp;&nbsp;무지 강사
					</h3>
				</div>
				<div class="panel panel-body">
					<div class="media">
						<div class="media-left">
						<a href="#">
							<img class="media-object" style="width: 180px; height: 150px;" src="<c:url value="/resources/images/muzi.jpg"/>" alt="개발자 이미지">
						</a>
						</div>
						<div class="media-body">
							<h4 class="media-heading">무지</h4><br>
								<p>CSS 강의를 맡은 웹개발 강의 서비스 대표 강사입니다.</p>
								<p>호기심 많고 장난꾸러기이지만, 토끼옷을 벗으면 부끄러움을 많이 탑니다.</p>
						</div>
					</div>
				</div>
				<table class="table">
					<thead>
						<tr>
							<th>강사명</th>
							<th>강의 번호</th>
							<th>강의 제목</th>
							<th>강의 날짜</th>
						</tr>
					</thead>
					<tbody>
						<tr style="text-align:center;">
							<td>무지</td>
							<td>1</td>
							<td><a href="lecture.project2?lectureName=CSS">CSS 기초 프로그래밍 강좌</a></td>
							<td>2018-08-07</td>
						</tr>
						<tr style="text-align:center;">
							<td>무지</td>
							<td>2</td>
							<td><a href="lecture.project2?lectureName=CSS">CSS 기초 프로그래밍 강좌</a></td>
							<td>2018-08-07</td>
						</tr>
						<tr style="text-align:center;">
							<td>무지</td>
							<td>3</td>
							<td><a href="lecture.project2?lectureName=CSS">CSS 기초 프로그래밍 강좌</a></td>
							<td>2018-08-07</td>
						</tr>
					</tbody>
				</table>
				<div class="panel-footer">
					<blockquote>&nbsp;&nbsp;웹페이지를 보다 이쁘게 꾸며주는 CSS! 열심히 강의하겠습니다.</blockquote>
				</div>
			</div>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">
						<span class="glyphicon glyphicon-tags"></span>
						&nbsp;&nbsp;어피치 강사
					</h3>
				</div>
				<div class="panel panel-body">
					<div class="media">
						<div class="media-left">
						<a href="#">
							<img class="media-object" style="width: 180px; height: 150px;" src="<c:url value="/resources/images/apitch.png"/>" alt="개발자 이미지">
						</a>
						</div>
						<div class="media-body">
							<h4 class="media-heading">어피치</h4><br>
								<p>JavaScript 강의를 맡은 웹개발 강의 서비스 대표 강사입니다.</p>
								<p>섹시한 뒤태와 아름다운 분홍빛을 무기로 친구들을 유혹해 보지만, 본능적인 장난기 때문인지 친구들은 항상 어피치를 경계합니다. </p>
						</div>
					</div>
				</div>
				<table class="table">
					<thead>
						<tr>
							<th>강사명</th>
							<th>강의 번호</th>
							<th>강의 제목</th>
							<th>강의 날짜</th>
						</tr>
					</thead>
					<tbody>
						<tr style="text-align:center;">
							<td>어피치</td>
							<td>1</td>
							<td><a href="lecture.project2?lectureName=JavaScript">JavaScript 기초 프로그래밍 강좌</a></td>
							<td>2018-08-07</td>
						</tr>
						<tr style="text-align:center;">
							<td>무지</td>
							<td>2</td>
							<td><a href="lecture.project2?lectureName=JavaScript">JavaScript 기초 프로그래밍 강좌</a></td>
							<td>2018-08-07</td>
						</tr>
						<tr style="text-align:center;">
							<td>무지</td>
							<td>3</td>
							<td><a href="lecture.project2?lectureName=JavaScript">JavaScript 기초 프로그래밍 강좌</a></td>
							<td>2018-08-07</td>
						</tr>
					</tbody>
				</table>
				<div class="panel-footer">
					<blockquote>&nbsp;&nbsp;웹개발의 대세 JavaScript! 열심히 강의하겠습니다.</blockquote>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="footer.jsp"%>
</body>
</html>
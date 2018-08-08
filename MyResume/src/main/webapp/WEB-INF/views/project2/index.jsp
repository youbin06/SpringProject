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
					<li class="active"><a href="index.project2">소개<span class="sr-only"></span></a></li>
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
					<li><a href="lectureAssessment.project2">강의평가</a></li>
				</ul>
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
			</div>			
		</div>
	</nav>
	<div class="container">
		<div class="jumbotron" style="background-image: url(<c:url value="/resources/images/webcodingjumbo.jpg"/>); background-size: cover; text-shadow: black 0.2em 0.2em 0.2em; color: white;">
			<h1 class="text-center">웹개발 강의서비스를 소개합니다.</h1>
			<p class="text-center">웹개발 강의서비스는 IT 교육 사이트 입니다. 웹개발 관련 무료 강의가 존재합니다.
			<p class="text-center"><a class="btn btn-primary btn-lg" href="lecture.project2" role="button">강의 들으러 가기</a>
		</div>
		<div class="row">
			<div class="col-md-4">
				<h4>웹개발 강의서비스 특징</h4>
				<p>웹개발 강의서비스는 다른 사이트와 차원이 다른 깔끔한 구성을 보여줍니다. 모든 페이지가 사용자가 접근하고 읽기 쉽도록 정교하게 구성되었습니다.</p>
				<p><a class="btn btn-default" data-target="#modal" data-toggle="modal">자세히 알아보기</a>
			</div>
			<div class="col-md-4">
				<h4>폭넓은 강사진</h4>
				<p>웹개발 강의서비스는 정말로 뛰어난 개발자만을 강사진으로 뽑습니다. 각각의 강사진은 현직 개발자로서 프로그래밍을 처음 접하는 학생에게 친절하게 알려줍니다.</p>
				<p><a class="btn btn-default" href="instructor.project2">자세히 알아보기</a>
			</div>
			<div class="col-md-4">
				<h4>핵심적인 강의</h4>
				<p>강의는 실제 개발에 있어서 반드시 필요한 핵심적인 내용을 중심으로 다룹니다. 강의를 듣는 시간 내내 요약된 정보를 온전히 받아들일 수 있습니다.</p>
				<p><a class="btn btn-default" href="lecture.project2">자세히 알아보기</a>
			</div>
		</div>
		<hr>
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title"><span class="glyphicon glyphicon-pencil"></span>
				&nbsp;&nbsp;최신 강의 목록</h3>
			</div>
			<div class="panel-body">
				<div class="media">
					<div class="media-left">
						<a href="lecture.project2?lectureName=HTML"><img class="media-object" src="<c:url value="/resources/images/html.png"/>" alt="HTML 강의 이미지" style="width:96px; height:96px;"></a>
					</div>
					<div class="media-body">
						<h4 class="media-heading"><a href="lecture.project2?lectureName=HTML">HTML 기초 프로그래밍 강의</a>&nbsp;<span class="badge">New</span></h4>
						HTML 강의는 기초 프로그래밍 강의입니다. 처음 웹개발을 접하는 입문자가 듣기에 적합한 강의입니다.<br>
						강의는 무료이며, HTML 기초 프로그래밍 강좌는 총 35강으로 구성됩니다.
					</div>
				</div>
				<hr>
				<div class="media">
					<div class="media-left">
						<a href="lecture.project2?lectureName=CSS"><img class="media-object" src="<c:url value="/resources/images/css.png"/>" alt="HTML 강의 이미지" style="width:96px; height:96px;"></a>
					</div>
					<div class="media-body">
						<h4 class="media-heading"><a href="lecture.project2?lectureName=CSS">CSS 기초 프로그래밍 강의</a>&nbsp;<span class="badge">New</span></h4>
						CSS 강의는 기초 프로그래밍 강의입니다. HTML 기초 프로그래밍 강의를 수강하신 분이 듣기에 적합한 강의입니다.<br>
						강의는 무료이며, CSS 기초 프로그래밍 강좌는 총 16강으로 구성됩니다.
					</div>
				</div>
				<hr>
				<div class="media">
					<div class="media-left">
						<a href="lecture.project2?lectureName=JavaScript"><img class="media-object" src="<c:url value="/resources/images/javascript.png"/>" alt="HTML 강의 이미지" style="width:96px; height:96px;"></a>
					</div>
					<div class="media-body">
						<h4 class="media-heading"><a href="lecture.project2?lectureName=JavaScript">HTML 기초 프로그래밍 강의</a>&nbsp;<span class="badge">New</span></h4>
						JavaScript 강의는 기초 프로그래밍 강의입니다. HTML 기초 프로그래밍 강의를 수강하신 분이 듣기에 적합한 강의입니다.<br>
						강의는 무료이며, JavaScript 기초 프로그래밍 강좌는 총 38강으로 구성됩니다.
					</div>
				</div>
			</div>
		</div>
	</div>
<%@ include file="footer.jsp"%>
	<div class="row">
		<div class="modal" id="modal" tabindex="-1">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						웹개발 강의서비스 특징
						<button class="close" data-dismiss="modal">&times;</button>
					</div>
					<div class="modal-body" style="text-align: center;">
						저희 서비스의 특징은 바로 강의를 들을 수 있다는 점입니다.<br>
						특히 다양한 무료 강의가 Youtube와 연동되어 제공됩니다.<br><br>
						<img src="<c:url value="/resources/images/youtube.JPG"/>" id="imagepreview" style="width: 356px; height: 156px;">
					</div>
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
	<!--Start of Tawk.to Script-->
		<script type="text/javascript">
			var Tawk_API=Tawk_API||{}, Tawk_LoadStart=new Date();
			(function(){
			var s1=document.createElement("script"),s0=document.getElementsByTagName("script")[0];
			s1.async=true;
			s1.src='https://embed.tawk.to/5b6922f0e21878736ba2b004/default';
			s1.charset='UTF-8';
			s1.setAttribute('crossorigin','*');
			s0.parentNode.insertBefore(s1,s0);
			})();
		</script>
<!--End of Tawk.to Script-->
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
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
					<li><a href="index.project2">소개<span class="sr-only"></span></a></li>
					<li><a href="instructor.project2">강사진</a></li>
					<li class="dropdown active">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
							aria-haspopup="true" aria-expanded="false">강의<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li class="active"><a href="lecture.project2?lectureName=HTML">HTML</a></li>
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
		<div class="row">
			<ul class="list-group">
				<a href="#" class="list-group-item active">1강. 수업소개</a>
				<a href="#" class="list-group-item">2강. 프로젝트의 동기</a>
				<a href="#" class="list-group-item">3강. 기획</a>
			</ul>
		</div>
		<div class="row">
			<div class="embed-responsive embed-responsive-16by9">
				<iframe class="embed-responsive-item" src="https://www.youtube.com/embed/tZooW6PritE"></iframe>
			</div>
			<hr>
			<div class="row">
				<p>
					첫 번째 강의는 HTML 강의에 대해 소개하는 강의입니다.
				</p>
				<pre class="pre-scrollable">
&lt;!DOCTYPE html&gt;
&lt;html&gt;
  &lt;head&gt;
  &lt;/head&gt;
&lt;body&gt;
&lt;/body&gt;
&lt;/html&gt;
				</pre>
			</div>
		</div>
		<hr>
		<div id="disqus_thread"></div>
			<script>
				(function() { // DON'T EDIT BELOW THIS LINE
				var d = document, s = d.createElement('script');
				s.src = 'https://myresume-1.disqus.com/embed.js';
				s.setAttribute('data-timestamp', +new Date());
				(d.head || d.body).appendChild(s);
				})();
			</script>
			<noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>
	</div>
<%@ include file="footer.jsp"%>

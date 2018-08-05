<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			response.sendRedirect("index.do");
			return;
		}
		String userGender = (String)request.getAttribute("userGender");
	%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=deive-width, initial-scale=1">
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.css"/>">
	<link rel="stylesheet" href="<c:url value="/resources/css/custom.css"/>">
	<title>Spring Final Project #1</title>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="<c:url value="/resources/js/bootstrap.js"/>"></script>
	<script type="text/javascript">
	
		function check(){
			if (f.userPassword1.value == "" || f.userPassword2.value == "" ||
					f.userName.value == "" || f.userAge.value == "" || f.userEmail.value == ""){
				$('#checkMessage').html('모든 내용을 입력하세요.');
				$('#checkType').attr('class', 'modal-content panel-warning');
				$('#checkModal').modal("show");
				return
			}
		
			document.f.submit()
		}
		
		function getUnread(){
			$.ajax({
				type: "POST",
				url: "./chatUnread",
				data: {
					userID: encodeURIComponent('<%=userID %>'),
				},
				success: function(result){
					if(result >= 1){
						showUnread(result);
					}else{
						showUnread('');
					}
				}
			});
		}
		function getInfiniteUnread(){
			setInterval(function(){
				getUnread();
				
			}, 4000);
		}
		function showUnread(result){
			$('#unread').html(result);
		}
		function passwordCheckFunction(){
			var uesrPassword1 = $('#userPassword1').val();
			var uesrPassword2 = $('#userPassword2').val();
			
			if(uesrPassword1 != uesrPassword2){
				$('#passwordCheckMessage').html('비밀번호가 서로 일치하지 않습니다.');
			}else{
				$('#passwordCheckMessage').html('');
			}
		}
		
		
	</script>
</head>
<body>
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="index.do">Spring Final Project #1</a>
		</div>
		<div class="collaspe navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="index.do">메인</a></li>
				<li><a href="find.do">친구찾기</a></li>
				<li><a href="box.do">메시지함<span id="unread" class="label label-info"></span></a></li>
				<li><a href="boardView.do">자유게시판</a></li>
				<li><a href="anonymousChat.do">익명채팅방</a></li>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="buton" aria-haspopup="true"
						aria-expanded="false">회원관리<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li class="active"><a href="update.do">회원정보수정</a></li>
						<li><a href="profileUpdate.do">프로필 수정</a></li>
						<li><a href="logoutAction.do">로그아웃</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</nav>
	<div class="container">
		<form name = "f" method="post" action="./update.do">
			<table class="table table-bordered table-hover"
				style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="2"><h4>회원정보수정 양식</h4></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width: 110px;"><h5>아이디</h5></td>
						<td><h5><%= userID %></h5>
						<input type="hidden" name="userID" value="<%=userID %>"></td>
						
					</tr>
					<tr>
						<td style="width: 110px;"><h5>비밀번호</h5></td>
						<td colspan="2" id="input"><input onkeyup="passwordCheckFunction();" 
							class="form-control" type="password" id="userPassword1" 
							name="userPassword1" maxlength="20" placeholder="비밀번호를 입력하세요."></td>
					</tr>
					<tr>
						<td style="width: 110px;"><h5>비밀번호 확인</h5></td>
						<td colspan="2" id="input"><input onkeyup="passwordCheckFunction();"
							class="form-control" type="password" id="userPassword2"
							name="userPassword2" maxlength="20" placeholder="비밀번호 확인을 입력하세요."></td>
					</tr>
					<tr>
						<td style="width: 110px;"><h5>이름</h5></td>
						<td colspan="2" id="input"><input class="form-control" type="text"
							id="userName" name="userName" maxlength="20"
							placeholder="이름을 입력하세요." value="${userDTO.userName}"></td>
					</tr>
					<tr>
						<td style="width: 110px;"><h5>나이</h5></td>
						<td colspan="2" id="input"><input class="form-control" type="number"
							id="userAge" name="userAge" maxlength="20"
							placeholder="나이를 입력하세요." value="${userDTO.userAge}"></td>
					</tr>
					<tr>
						<td style="width: 110px;"><h5>성별</h5></td>
						<td colspan="2">
							<div class="form-group" style="text-align: center;">
								<div class="btn-group" data-toggle="buttons">
									<label class="btn btn-primary <%if(userGender.equals("남자")) out.print("active"); %>"> <input
										type="radio" name="userGender" autocomplete="off" value="남자"
										<%if(userGender.equals("남자")) out.print("checked"); %>>남자
									</label> 
									<label class="btn btn-primary <%if(userGender.equals("여자")) out.print("active"); %>"> <input type="radio"
										name="userGender" autocomplete="off" value="여자" <%if(userGender.equals("여자")) out.print("checked"); %>>여자
									</label>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td style="width: 110px;"><h5>이메일</h5></td>
						<td colspan="2" id="input"><input class="form-control" type="email"
							id="userEmail" name="userEmail" maxlength="20"
							placeholder="이메일를 입력하세요." value="${userDTO.userEmail}"></td>
					</tr>
					<tr>
						<td style="text-align: left;" colspan="3">
							<h5 style="color: red;" id="passwordCheckMessage"></h5>
							<button class="btn btn-primary pull-right" onclick="check();" type="button">수정</button>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
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
	
	<%
		if(userID != null){
	%>
		<script type="text/javascript">
			$(document).ready(function(){
				getUnread();
				getInfiniteUnread();
			});
		</script>
	<%		
		}
	
	%>
</body>
</html>

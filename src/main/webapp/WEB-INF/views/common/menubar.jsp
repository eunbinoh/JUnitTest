<%@ page language="java" contentType="text/html; charset=UTF-8"
	    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="resources/js/jquery-3.6.0.min.js"></script>
<style>
	.nav {margin-left:auto; margin-right:auto; text-align: center;}
	.menu {
		display:inline-block; background:#e4fc41; text-align:center;
		line-height:50px; width:150px; height:50px; border-radius:20px;
		margin-left: 5%; margin-right: 5%;
	}
	.menu:hover {background:#b0c903; font-weight: bolder; cursor:pointer;}
	a:link {color: black; text-decoration: none;}
	a:visited {color: black; text-decoration: none;}
</style>
</head>
<body>
	
	<!-- 메뉴바는 어느 페이지든 포함하고 있을 테니 여기서 contextPath 변수 값 만들기 -->
	<c:set var="contextPath" value="${ pageContext.servletContext.contextPath }" scope="application"/>
	
	<h1 align="center">Finally, SPRING</h1>
	<br>
	
	<div class="loginArea" align="right">
		<c:if test="${ empty sessionScope.loginUser }">
			<form action="login.me" method="post">
				<table id="loginTable" style="text-align:center;">
					<tr>
						<td>아이디</td>
						<td><input type="text" name="id"></td>
						<td rowspan="2">
							<button id="loginBtn">로그인</button>
						</td>
					</tr>
					<tr>
						<td>비밀번호</td>
						<td><input type="password" name="pwd"></td>
					</tr>
					<tr>
						<td colspan="3">
							<button type="button" onclick="location.href='enrollView.me'">회원가입</button>
							<button type="button">아이디/비밀번호 찾기</button>
						</td>
					</tr>
				</table>
			</form>
		</c:if>
		<c:if test="${ !empty sessionScope.loginUser }">
			<h3 align="right">
				<c:out value="${ loginUser.name }님 환영합니다."/> <!-- c:out 활용 -->
				<c:url var="myinfo" value="myinfo.me"/>
				<c:url var="logout" value="logout.me"/>
				<button onclick="location.href='${myinfo}'">정보보기</button>
				<button onclick="location.href='${logout}'">로그아웃</button>
			</h3>
		</c:if>
	</div>
	
	<!-- 위처럼 c:url을 통해서 지정할 수도 있지만 지금까지 해왔던 것처럼 바로 href에 url을 넣어도 상관없음 -->
	<div class="menubar">
		<div class="nav">
			<div class="menu"><a href="home.do">HOME</a></div>
			<div class="menu"><a href="blist.bo">게시판</a></div>
		</div>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${contextPath}/resources/css/member-style.css" type="text/css">
<style type="text/css">
	#myInfoTable td{text-align: center;}
</style>
</head>
<body>

	<c:import url="../common/menubar.jsp"/>
	
	<h1 align="center">'${ loginUser.name }'님의 정보 보기</h1>
	
	<div class="centerText">
		<table id="myInfoTable">
			<tr>
				<th>아이디</th>
				<td>${ loginUser.id }</td>
			</tr>
			<tr>
				<th>이름</th>
				<td>${ loginUser.name }</td>
			</tr>
			<tr>
				<th>닉네임</th>
				<td>${ loginUser.nickName }</td>
			</tr>
			<tr>
				<th>성별</th>
				<c:if test="${ loginUser.gender eq 'M'}">
					<td>남성</td>
				</c:if>
				<c:if test="${ loginUser.gender eq 'F'}">
					<td>여성</td>
				</c:if>
			</tr>
			<tr>
				<th>나이</th>
				<td>${ loginUser.age }</td>				
			</tr>
			<tr>
				<th>이메일</th>
				<td>${ loginUser.email }</td>
			</tr>
			<tr>
				<th>전화번호</th>
				<td>${ loginUser.phone }</td>
			</tr>
			<tr>
				<th>주소</th>
				<td>
					<c:forTokens var="addr" items="${ loginUser.address }" delims="/" varStatus="status">
						<c:if test="${ status.index eq 0 && addr >= '0' && addr <= '99999' }">
							(${ addr })
						</c:if>
						<c:if test="${ status.index eq 0 && !(addr >= '0' && addr <= '99999') }">
							${ addr }
						</c:if>
						<c:if test="${ status.index eq 1 }">
							${ addr }
						</c:if>
						<c:if test="${ status.index eq 2 }">
							${ addr }
						</c:if>
					</c:forTokens>
				</td>
			</tr>
			<!-- jQuery와 Postcodify를 로딩한다. -->
			<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
			<script>
				// 검색 단추를 누르면 팝업 레이어가 열리도록 설정한다.
				$(function(){
					$("#postcodify_search_button").postcodifyPopUp();
				});
			</script>
			
			<tr>
				<td colspan="2" align="center">					
					<button type="button" onclick="location.href='mpwdUpdateView.me'">비밀번호수정</button>
					<button type="button" onclick="location.href='mupdateView.me'">정보수정</button>
					<c:url var="mdelete" value="mdelete.me">
						<c:param name="id" value="${ loginUser.id }"/><!-- 넘어갈 값이 있으니 param을 넣게 c:url을 쓰자 -->
					</c:url>
					<button type="button" onclick="location.href='${ mdelete }'">회원탈퇴</button>
					<button type="button" onclick="location.href='home.do'">시작 페이지로 이동</button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#boardDetailTable{width: 800px; margin: auto; border-collapse: collapse; border-left: hidden; border-right: hidden;}
	#boardDetailTable tr td{padding: 5px;}
	.replyTable{margin:auto; width:500px;}
</style>
</head>
<body>
	<c:import url="../common/menubar.jsp"/>
	
	<h1 align="center">${ b.boardId }번 글 상세보기</h1>
	
	<table border="1" id="boardDetailTable">
		<tr>
			<th>번호</th>
			<td>${ b.boardId }</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${ b.boardTitle }</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${ b.nickName }</td>
		</tr>
		<tr>
			<th>작성날짜</th>
			<td>${ b.boardCreateDate }</td>
		</tr>
		<tr>
			<th>내용</th>
			<%-- <td>${ board.boardContent }</td> --%>
			<!-- 
				이렇게만 두면 엔터가 먹지 않음. 
				DB에는 엔터가 \r\n으로 들어가서 이를 치환해주는 작업 필요
			-->
			
			<% pageContext.setAttribute("newLineChar", "\r\n"); %> <!-- \r\n 말고 그냥 \n도, \r도 가능하다 -->
			<td>${ fn:replace(b.boardContent, newLineChar, "<br>") }</td>
		</tr>
		
		<c:if test="${ !empty b.originalFileName }">
			<tr>
				<th>첨부파일</th>
				<td>
					<a href="${ contextPath }/resources/buploadFiles/${ b.renameFileName }" download="${ b.originalFileName }">${ b.originalFileName }</a>
					<!-- a태그 안에서 다운로드 받을 것이 있을 때 쓰는 속성 download, 얘는 download="fileName" 이라고 해서 fileName을 지정해줄 수 있다. -->
				</td>
			</tr>
		</c:if>
		
		<c:url var="bupView" value="bupView.bo">
			<c:param name="boardId" value="${ b.boardId }"/>
			<c:param name="page" value="${ page }"/>
		</c:url>
		<c:url var="bdelete" value="bdelete.bo">
			<c:param name="boardId" value="${ b.boardId }"/>
		</c:url>
		<c:url var="blist" value="blist.bo">
			<c:param name="page" value="${ page }"/>
		</c:url>
		
		<c:if test="${ loginUser.id eq b.boardWriter }">
		<tr>
			<td colspan="2" align="center">
				<button onclick="location.href='${ bupView }'">수정하기</button>
				<button onclick="location.href='${ bdelete }'">삭제하기</button>
			</td>
		</tr>
		</c:if>
		
	</table>
	
	<p align="center">
		<button onclick="location.href='home.do'">시작 페이지로 이동</button>
		<button onclick="location.href='${ blist }'">목록 보기로 이동</button>
	</p>
	
	<br><br>
	
	<table class="replyTable">
		<tr>
			<td><textarea rows="3" cols="55" id="replyContent"></textarea></td>
			<td><button id="rSubmit">등록하기</button></td>
		</tr>
	</table>
	<table class="replyTable" id="rtb">
		<thead>
			<tr>
				<td colspan="2"><b id="replyCount"></b></td>
			</tr>
		</thead>
		<tbody>
		
		</tbody>
	</table>
	
	
	<script>
		$('#rSubmit').on('click',function(){
			var replyContent = $('#replyContent').val();
			var refBoardId = ${b.boardId};
			
			$.ajax({
				url:'addReply.bo',
				data:{replyContent:replyContent, refBoardId:refBoardId },
				success: function(data){
					console.log(data);
					if(data=='success'){
						$('#replyContent').val('');
						getReplyList(); //댓글 리스트를 불러오는 함수
					}
				}
			});
		});
	
	
		function getReplyList(){
			var boardId=${b.boardId};
			
			$.ajax({
				url: 'rList.bo',
				data: {boardId:boardId},
				success: function(data){
					console.log(data);
					$tableBody = $('#rtb tbody'); //$jQuery 변수
					$tableBody.html(''); //중복출력되지 않도록 비워주기
					
					$('#replyCount').text('댓글('+ data.length+ ')');
					
					var $tr;
					var $writer;
					var $content;
					var $createDate;
					
					if(data.length>0){
						for( var i in data){
							$tr=$('<tr>');
							$writer = $('<td width="100">').text(data[i].replyWriter);
							$content = $('<td>').text(data[i].replyContent);
							$createDate = $('<td width="100">').text(data[i].replyCreateDate);
							
							$tr.append($writer);
							$tr.append($content);
							$tr.append($createDate);
							$tableBody.append($tr);
						}
					}else{
						$tr=$('<tr>');
						$content = $('<td colspan="3">').text('등록된 댓글이 없습니다');
						
						$tr.append($content);
						$tableBody.append($tr);
						
					}
				},
				error: function(data){
					console.log('error');
				}
			});
		}
		
		$(function(){
			getReplyList();
			
			setInterval(function(){
				getReplyList();	},5000); //실시간 등록되는 댓글 5초마다 업데이트
		
		});
		
	</script>
	
	
	
	
	
	
	
	
	
	
	
	
	
</body>
</html>
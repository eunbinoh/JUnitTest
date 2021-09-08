<%@ page language="java" contentType="text/html; charset=UTF-8"
	    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<style>#tb{margin:auto; width:700px;}</style>
</head>
<body>

	<c:import url="common/menubar.jsp"></c:import>
	
	<script>
		$(function(){
			var msg = '${msg}';
			if(msg!=''){
				alert(msg);
			}
		});
	
	</script>

	<h1 align="center">게시글 TOP 5 목록</h1>
	<table id="tb" boarder="1">
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>날짜</th>
				<th>조회수</th>
				<th>첨부파일</th>
			</tr>
		</thead>
		<tbody>
			
		</tbody>
	</table>
	
	<script>
		$(function(){
			topList();	
			setInterval(function(){
				topList();
			},3000); //3초마다 실검 갱신
		});
		
		function topList(){
			$.ajax({
				url:'topList.bo',
				success: function(data){
					console.log(data);
					
					$tBody = $('#tb tbody'); 
					$tBody.html(''); 
					
					var $tr;
					var $id;
					var $title;
					var $writer;
					var $createDate;
					var $count;
					var $file;
					
					if(data.length>0){
						for( var i in data){
							$tr=$('<tr>');
 							$id=$('<th>').text(data[i].boardId); 
							$title=$('<th>').text(data[i].boardTitle)
							$writer = $('<th>').text(data[i].boardWriter);
							$createDate = $('<th>').text(data[i].boardCreateDate);
							$count = $('<th>').text(data[i].boardCount);
							$file = $('<th>').text(data[i].originalFileName);
							if(data[i].originalFileName != null){
								$file = $('<th>').text('O')
							}
							
							$tr.append($id);
							$tr.append($title);
							$tr.append($writer);
							$tr.append($createDate);
							$tr.append($count);
							$tr.append($file);
							$tBody.append($tr);
						}
					}
					
				},
				error: function(data){
					console.log(data);
				}
			});
		}
		
		
		
	</script>
</body>
</html>

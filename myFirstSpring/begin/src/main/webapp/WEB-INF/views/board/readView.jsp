<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
	 	<title>게시판</title>
	 	<link href = "/resources/css/readView.css" rel = "stylesheet">
	</head>	
	
	<body>
	
		<div id="root">
			<header>
				<h1><a href="/board/list"> 게시판</a></h1>
				<div>
					<%@include file="nav.jsp" %>
				</div>
			</header>
			 
			 
					
			<section id="container">
				<form id = "readForm" name="readForm" role="form" method="post">
				  <input type="hidden" id="bno" name="bno" value="${read.bno}" />
				  <input type="hidden" id="parentno" name="parentno" value="${read.parentno}" />
				  <input type="hidden" id="page" name="page" value="${scri.page}"> 
				  <input type="hidden" id="perPageNum" name="perPageNum" value="${scri.perPageNum}"> 
				  <input type="hidden" id="searchType" name="searchType" value="${scri.searchType}"> 
				  <input type="hidden" id="keyword" name="keyword" value="${scri.keyword}"> 
				  <input type="hidden" id="FILE_NO" name="FILE_NO" value=""> 
				</form>
				
				<h1 id = "title">${read.title}</h1>
				<span id = "writer">${read.writer}(<fmt:formatDate value="${read.regdate}" pattern="yyyy-MM-dd" />)</span>
				<table>
					<tbody>
						<tr>
							<td id = "td_content" colspan="2">
								<p id = "p_content">${read.content}</p>
							</td>
						</tr>
						<tr>
							<td colspan = "2">
								<c:forEach var="file" items="${file}">
									<a href="#" onclick="fn_fileDown('${file.FILE_NO}'); return false;">${file.ORG_FILE_NAME}</a>(${file.FILE_SIZE}kb)<br>
								</c:forEach>
							</td>
						</tr>
					</tbody>			
				</table>

				<div id = "div_button">
					<button type="submit" class="update_btn">수정</button>
					<button type="submit" class="delete_btn">삭제</button>
					<button type="submit" class="list_btn">목록</button>	
					<button type="submit" class="comment_btn">답글</button>	
				</div>
			
			<div id = "div_comment">
                <span><strong>Comments</strong></span> <span id="rCounter"></span>
            </div>
			
			<form id = "replyForm" name="replyForm" method="post">
			  <input type="hidden" id="bno" name="bno" value="${read.bno}" />
			  <input type="hidden" id="page" name="page" value="${scri.page}"> 
			  <input type="hidden" id="perPageNum" name="perPageNum" value="${scri.perPageNum}"> 
			  <input type="hidden" id="searchType" name="searchType" value="${scri.searchType}"> 
			  <input type="hidden" id="keyword" name="keyword" value="${scri.keyword}"> 
			
			  <div id = "div_reply_input">
			  <table>
			  	<tbody>
			  		<tr>
				    	<td id = "td1"><label for="writer">댓글 작성자</label></td>
				    	<td><input type="text" id="reply_writer" name="writer"  size= 20  style = "padding : 3px;"/></td>
				    </tr>
				    <tr>
				    	<td id = "td1"><label for="content">댓글 내용</label></td>
				    	<td><textarea id="reply_content" name="content"  rows =  "5" cols = "122"  style="resize: none;"/></textarea></td>
				    </tr>
			    </tbody>
			   </table>
			  </div>
			  <div id ="replyWriteBtn">
			 	 <button type="button"  onClick="reply_write()">작성</button>
			  </div>
			</form>
			<div class="replyContainer">
				    <form id="replyListForm" name="commentListForm" method="post">
				        <div id="replyList"></div>
				    </form>
				</div>
		</section>
		</div>
		
		<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
		<script src = "/resources/js/readView.js"></script>
		<script>			// 목록
			$(".list_btn").on("click", function(){
				location.href = "/board/list?page=${scri.page}"
				+"&perPageNum=${scri.perPageNum}"
				+"&searchType=${scri.searchType}&keyword=${scri.keyword}";})
		</script>
	</body>
</html>
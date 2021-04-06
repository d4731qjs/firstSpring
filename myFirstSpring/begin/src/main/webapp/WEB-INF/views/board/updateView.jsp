<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
	 	<title>게시판-답글 달기</title>
	 	<link href = "/resources/css/updateView.css" rel = "stylesheet">
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
				<form name="updateForm" role="form" method="post" action="/board/update" enctype="multipart/form-data">
					<input type="hidden" name="bno" value="${update.bno}" readonly="readonly"/>
					<input type="hidden" id="page" name="page" value="${scri.page}"> 
					<input type="hidden" id="perPageNum" name="perPageNum" value="${scri.perPageNum}"> 
					<input type="hidden" id="searchType" name="searchType" value="${scri.searchType}"> 
					<input type="hidden" id="keyword" name="keyword" value="${scri.keyword}"> 
					<input type="hidden" id="fileNoDel" name="fileNoDel[]" value=""> 
					<input type="hidden" id="fileNameDel" name="fileNameDel[]" value=""> 
					<table>
						<tbody>
							<tr>
								<td id = "td1">
									<label for="title">제목</label>
								</td>
								<td  id = "td2">
									<input type="text" id="title" name="title" value="${update.title}"
									class = "chk" title = "제목을 입력하세요." size= 90  style = "padding : 3px;"/>
								</td>
							</tr>	
							<tr>
								<td id = "td1">
									<label for="content">내용</label>
								</td>
								<td  id = "td2">
									<textarea id="content" name="content" 
									class = "chk" title = "내용을 입력하세요." style="resize: none;" rows =  "25" cols = "126" 
									 ><c:out value="${update.content}" /></textarea>
								</td>
							</tr>
							<tr>
								<td id = "td1">
									<label for="writer">작성자</label>
								</td>
								<td id = "td2">
									<input type="text" id="writer" name="writer" value="${update.writer}" readonly="readonly"/>
								</td>
							</tr>
							<tr>
								<td id = "td1">
									<label for="regdate">작성날짜</label>
								</td>
								<td  id = "td2">
									<fmt:formatDate value="${update.regdate}" pattern="yyyy-MM-dd"/>					
								</td>
							</tr>	
							<tr>
								<td id="fileIndex" colspan="2">
									<c:forEach var="file" items="${file}" varStatus="var">
									<div id = "${file.FILE_NO }">
										<input type="hidden" id="FILE_NO" name="FILE_NO_${var.index}" value="${file.FILE_NO }">
										<input type="hidden" id="FILE_NAME" name="FILE_NAME" value="FILE_NO_${var.index}">
										<a href="#" id="fileName" onclick="return false;">${file.ORG_FILE_NAME}</a>(${file.FILE_SIZE}kb)
										<button id="fileDel" onclick="fn_del('${file.FILE_NO}','FILE_NO_${var.index}');" type="button">삭제</button><br>
									</div>
									</c:forEach>
								</td>
							</tr>	
						</tbody>			
					</table>
					<div>
						<button type="submit" class="update_btn">저장</button>
						<button type="submit" class="cancel_btn">취소</button>
						<button type="button" class="fileAdd_btn">파일추가</button>
					</div>
				</form>
			</section>
			
		   <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		   <script type="text/javascript">
				$(document).ready(function(){
					var formObj = $("form[name='updateForm']");
					
					$(".cancel_btn").on("click", function(){
						event.preventDefault();
						location.href = "/board/readView?bno=${update.bno}"
							   + "&page=${scri.page}"
							   + "&perPageNum=${scri.perPageNum}"
							   + "&searchType=${scri.searchType}"
							   + "&keyword=${scri.keyword}";
					})
					
					$(".update_btn").on("click", function(){
						if(fn_valiChk()){
							return false;
						}
						formObj.attr("action", "/board/update");
						formObj.attr("method", "post");
						alert("수정되었습니다.");
						formObj.submit();
					})
					
					fn_addFile();
				});
					
				function fn_valiChk(){
					var updateForm = $("form[name='updateForm'] .chk").length;
					for(var i = 0; i<updateForm; i++){
						if($(".chk").eq(i).val() == "" || $(".chk").eq(i).val() == null){
							alert($(".chk").eq(i).attr("title"));
							return true;
						}
					}
				}
				
				function fn_addFile(){
					
					var fileIndex = 1;
					$(".fileAdd_btn").on("click", function(){
						$("#fileIndex").append("<div id = 'div_file'><input type='file' name='file_"+(fileIndex++)+"'>"+"</button>"+"<button type='button' id='fileDelBtn'>"+"삭제"+"</button></div>");
					});
					$(document).on("click","#fileDelBtn", function(){
						$(this).parent().remove();
						
					});
				};
				
		 		var fileNoArry = new Array();
		 		var fileNameArry = new Array();
		 		
		 		function fn_del(value, name){
		 			
		 			fileNoArry.push(value);
		 			fileNameArry.push(name);
		 			
		 			$("#fileNoDel").attr("value", fileNoArry);
		 			$("#fileNameDel").attr("value", fileNameArry);
		 			$("#" + value + "").remove();
		 		}
		</script>
		</div>
	</body>
</html>
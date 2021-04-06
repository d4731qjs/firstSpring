<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
	 	<title>게시판</title>
	 	<link href = "/resources/css/list.css" rel = "stylesheet">
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
			<form role="form" method="get" >
				<table>
					<tr>
						<th id = "td_bno">번호</th>
						<th id = "td_title">제목</th>
						<th id = "td_writer">작성자</th>
						<th id = "td_regdate">등록일</th>
					</tr>

					<c:forEach items="${list}" var="list">
						<tr id = "tr_list">
							<td id = "td_bno">
								<c:set var="groupno" value="${list.groupno}" />
								<c:set var="depth" value="${list.depth}" />
								<c:if test="${groupno eq 0}">
								  <c:out value="${list.bno}" />
								</c:if>
							</td>
							<td id = "td_title" style = "text-indent: calc( ${list.depth}*0.5em )"><a href="/board/readView?bno=${list.bno}
							&page=${scri.page}
							&perPageNum=${scri.perPageNum}
							&searchType=${scri.searchType}
							&keyword=${scri.keyword}">
								<c:choose>
									<c:when test="${depth eq 0}"> 
										<c:out value="${list.title}" />
									</c:when>
									<c:when test="${depth > 0}"> 
										<c:out value="└ ${list.title}" />
									</c:when>
								</c:choose>
							</a></td>
							<td id = "td_writer"><c:out value="${list.writer}" /></td>
							<td id = "td_regdate"><fmt:formatDate value="${list.regdate}" pattern="yyyy-MM-dd" /></td>
						</tr>
					</c:forEach>

				</table>
				
				<div class="search">
					<select name="searchType" style = "padding : 3px;">
						<option value="n"
							<c:out value="${scri.searchType == null ? 'selected' : ''}"/>>-----</option>
						<option value="t"
							<c:out value="${scri.searchType eq 't' ? 'selected' : ''}"/>>제목</option>
						<option value="c"
							<c:out value="${scri.searchType eq 'c' ? 'selected' : ''}"/>>내용</option>
						<option value="w"
							<c:out value="${scri.searchType eq 'w' ? 'selected' : ''}"/>>작성자</option>
						<option value="tc"
							<c:out value="${scri.searchType eq 'tc' ? 'selected' : ''}"/>>제목+내용</option>
					</select> 
					
					<input type="text" name="keyword" id="keywordInput" value="${scri.keyword}" size= 25  style = "padding : 3px;" />

					<button id="searchBtn" type="button">검색</button>
					
				</div>
				
				<div id = "div_list">
					<ul>
						<c:if test="${pageMaker.prev}">
							<li><a
								href="list${pageMaker.makeSearch(pageMaker.startPage - 1)}">이전</a></li>
						</c:if>

						<c:forEach begin="${pageMaker.startPage}"
							end="${pageMaker.endPage}" var="idx">
							<li><a href="list${pageMaker.makeSearch(idx)}">${idx}</a></li>
						</c:forEach>

						<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
							<li><a
								href="list${pageMaker.makeSearch(pageMaker.endPage + 1)}">다음</a></li>
						</c:if>
					</ul>
				</div>
			</form>
		</section>
			
			<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
			<script>
						$(function() {
							$('#searchBtn').click(function() {
									self.location = "list"
									+ '${pageMaker.makeQuery(1)}'
									+ "&searchType=" + $("select option:selected").val()
									+ "&keyword=" + encodeURIComponent($('#keywordInput').val());
								});
						});
			</script>
					
		</div>
	</body>
</html>
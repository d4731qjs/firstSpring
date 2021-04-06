<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html> 
<html>
	<head>
	 	<title>게시판</title>
	 	<link href = "/resources/css/writeView.css" rel = "stylesheet">
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
					<input type="hidden" id="parentno" name="parentno" value="${comment.parentno}" />
				</form>
					<table>
						<tbody>
							<tr>
								<td id = "td1">
									<label for="title">제목</label>
								</td>
								<td id = "td2">
									<input type="text" id="title" name="title" class = "chk" title = "제목을 입력하세요." size=90  style = "padding : 3px;" 
									value = "RE : ${comment.title}" disabled/>
								</td>
							</tr>	
							<tr>
								<td id = "td1">
									<label for="content">내용</label>
								</td>
								<td id = "td2">	
									<textarea id="content" name="content"
									class = "chk" title = "내용을 입력하세요."  rows =  "25" cols = "126"  style = "resize : none" ></textarea>
								</td>
							</tr>
							<tr>
								<td id = "td1">
									<label for="writer">작성자</label>
								</td>
								<td id = "td2">
									<input type="text" id="writer" name="writer"  class = "chk" title = "작정자를 입력하세요." />
								</td>
							</tr>
							<tr>
								<td colspan = "2">
									<form id = "writeForm" name="writeForm" method="post" enctype="multipart/form-data"  action = "/board/file">
										<div id = "fileIndex">
											<input id = "hidden_bno" type = "hidden" name = "bno">
										</div>
									</form>
								</td>
							</tr>
							<tr>
								<td colspan = "2">						
									<button type = "button" id = "write_btn">작성</button>
									<button class="fileAdd_btn" type="button">파일추가</button>	
								</td>
							</tr>			
						</tbody>			
					</table>
				
			</section>
		</div>
		
		<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
		<script>
                //ID가 write_btn을 클릭할떄
            $(document).ready(function(){
                $("#write_btn").click(function(){
					
                    //JSON 객체에 값을 담아줌
                    var json = {
                        title : $("#title").val(),
                        content : $("#content").val(),
                        writer : $("#writer").val(),
                        parentno : $("#parentno").val(),
                    };
                    
                    //변수명이 JSON에 담아둔 값만큼 포이치문을 돌림
                    for(var str in json){
                        //JSON에 STR의 길이가 0일 경우
                        if(json[str].length == 0){
                            //해당하는 ID에 placeholder를 찾아 경고창을 띄움
                            alert($("#" + str).attr("name") + "를 입력해주세요.");
                            //해당하는 ID에 포커스를 올림
                            $("#" + str).focus();
                            //리턴
                            return;
                        }
                    }
                    //비동기 요청
                     $.ajax({
                        type : 'post', //POST로
                        url : '/board/commentWriteView', //URL 지정
                        dataType : 'json',
                        contentType : 'application/json; charset=UTF-8',
                        data : JSON.stringify(json),//전달값은 JSON
                        
                        success : function(result) { //성공시

                            alert("정상적으로 등록이 되었습니다.");
                            $("#hidden_bno").val(Number(result));
                            $("#writeForm").submit();
                        },
                            
                        error : function(error) {
                            alert(JSON.stringify(error));
                        }
                    });
                });
                
                fn_addFile();
            });
                
            function fn_addFile(){
    			var fileIndex = 1;
    			$(".fileAdd_btn").on("click", function(){
    				$("#fileIndex").append("<div id = 'div_file'><input type='file' name='file_"+(fileIndex++)+"'>"+"</button>"+"<button type='button'  id='fileDelBtn'>"+"삭제"+"</button></div>");
    			});
    			$(document).on("click","#fileDelBtn", function(){
    				$(this).parent().remove();
    				
    			});
    		};
        </script>
        
	</body>
</html>
$(document).ready(function(){
	var formObj = $("form[name='readForm']");
	
	// 수정 
	$(".update_btn").on("click", function(){
		formObj.attr("action", "/board/updateView");
		formObj.attr("method", "get");
		formObj.submit();				
	})
	
	// 답글 
	$(".comment_btn").on("click", function(){
		formObj.attr("action", "/board/commentWriteView");
		formObj.attr("method", "get");
		formObj.submit();				
	})
	
	// 삭제
	$(".delete_btn").on("click", function(){
		
		var deleteYN = confirm("삭제하시겠습니까?");
		if(deleteYN == true){
			
			var json = {
                    bno : $("#bno").val(),  
					parentno : $("#parentno").val()
           	};
			
			$.ajax({
                type : 'post', //POST로
                url : '/board/delete', //URL 지정
                dataType : 'json',
                contentType : 'application/json; charset=UTF-8',
                data : JSON.stringify(json),//전달값은 JSON
                
                success : function(result) { //성공시
                    //받아온 데이터를 스위치문사용
                    //0은 성공, 나머지는 db오류
                    switch (Number(result)) {
                    case 0:
                        alert("정상적으로 삭제 되었습니다.");
                        window.location.href = '/board/list';
                        break;

                    default:
                        alert("알수없는 오류가 발생했습니다. [ErrorCode : " + Number(result) + "]");
                        break;
                    }
                },
                error : function(error) {
                    alert(JSON.stringify(error));
                     
                }
            });
		}
	})
	
	//댓글 수정	
	$(document).on('click','#reply_change_btn',function(){
		$(this).css('display','none');
		$(this).next().css('display','none');		
		$(this).next().next().css('display','inline-block');
		$(this).parent().parent().children('#reply_content').children('#reply_content_span').css('display','none');
		$(this).parent().parent().children('#reply_content').children('#hidden_content').css('display','inline-block');
	});
	
	//댓글 수정입력	
	$(document).on('click','#reply_update_btn',function(){
				
		var deleteYN = confirm("댓글을 수정하시겠습니까?");
		if(deleteYN == true){
			var rno = $(this).parent().parent().children('#rno').val();
			
			var json = {
	                rno : rno,
					content : $(this).parent().parent().children('#reply_content').children('#hidden_content').val()
	       	};
			
			$.ajax({
	            type : 'post', //POST로
	            url : '/board/replyUpdate', //URL 지정
	            dataType : 'json',
	            contentType : 'application/json; charset=UTF-8',
	            data : JSON.stringify(json),//전달값은 JSON
	            
	            success : function(result) { 
	
	                switch (Number(result)) {
	                case 0:
	                    alert("정상적으로 수정 되었습니다.");
						getReplyList(); 
	                    break;
	
	                default:
	                    alert("알수없는 오류가 발생했습니다. [ErrorCode : " + Number(result) + "]");
	                    break;
	                }
	            },
	            error : function(error) {
	                alert(JSON.stringify(error));
	            }
	        });
		}
	});
});
		
$(function(){
	checkComment($("#bno").val(),  $("#parentno").val());
	getReplyList();	
});

function checkComment(bno, parentno){
	
	if(bno != parentno){
		$(".comment_btn").css('display','none');
	}
};

 
//댓글 불러오기(Ajax)

function getReplyList(){
    
    $.ajax({
        type:'GET',
        url : '/board/replyLoad',
        dataType : "json",
        data:$("#readForm").serialize(),
        contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
        success : function(data){
            
            var html = "";
            var rCounter = data.length;
            
            
            if(data.length > 0){
                
                for(i=0; i<data.length; i++){
                	html += "<div id = 'reply_div'>";
					html += "<input type = 'hidden'  id='rno' name='rno' value="+data[i].rno+" />"
                    html += "<div id = 'reply_writer'><h6><strong>"+data[i].writer+"</strong></h6></div>";
                    html += "<div id = 'reply_content'><span id = 'reply_content_span'>" + data[i].content + "</span>";
                    html += "<textarea  id='hidden_content' name='hidden_content'  style = 'display : none'>"+data[i].content+"</textarea></div>"
                    html += "<div><button type = 'button' id = 'reply_change_btn' onClick = 'reply_change()'>수정</button> ";
					html += "<button type = 'button' id = 'reply_delete_btn' onClick = 'reply_delete("+data[i].rno+")'>삭제</button>";
					html += "<button type = 'button' id = 'reply_update_btn' style = 'display:none'>입력</button></div>";
                    html += "</div>";
                }
                
                
            } else {
                
                html += "<div>";
                html += "<div><table class='table'><h6><strong>등록된 댓글이 없습니다.</strong></h6>";
                html += "</table></div>";
                html += "</div>";
                
            }
            
            $("#rCounter").html(rCounter);
            $("#replyList").html(html);
            
        },
        error:function(error){
        	 alert(JSON.stringify(error));
       }
        
    });
};


 //댓글 등록하기(Ajax)
 
function reply_write(){
    
	
    $.ajax({
        type:'POST',
        url : '/board/replyWrite',
        data:$("#replyForm").serialize(),
        success : function(data){
        	 switch (Number(data)) {
             case 0:
            	 getReplyList();
                 $("#reply_writer").val("");
                 $("#reply_content").val("");
                 break;
             default:
                 alert("알수없는 오류가 발생했습니다. [ErrorCode : " + Number(result) + "]");
                 break;
             }  
         },
         error : function(error) {
             alert(JSON.stringify(error));
         }	              
    });
};

// 댓글 삭제

function reply_delete(rno){
	
	var deleteYN = confirm("댓글을 삭제하시겠습니까?");
	if(deleteYN == true){
		
		var json = {
                rno : rno
       	};
		
		$.ajax({
            type : 'post', //POST로
            url : '/board/replyDelete', //URL 지정
            dataType : 'json',
            contentType : 'application/json; charset=UTF-8',
            data : JSON.stringify(json),//전달값은 JSON
            
            success : function(result) { 

                switch (Number(result)) {
                case 0:
                    alert("정상적으로 삭제 되었습니다.");
					getReplyList(); 
                    break;

                default:
                    alert("알수없는 오류가 발생했습니다. [ErrorCode : " + Number(result) + "]");
                    break;
                }
            },
            error : function(error) {
                alert(JSON.stringify(error));
            }
        });
	}
};

function fn_fileDown(fileNo){
	
	var formObj = $("form[name='readForm']");
	$("#FILE_NO").attr("value", fileNo);
	formObj.attr("action", "/board/fileDown");
	formObj.submit();
	
}
		

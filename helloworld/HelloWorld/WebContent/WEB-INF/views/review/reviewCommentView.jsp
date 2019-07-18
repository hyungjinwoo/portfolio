<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="review.model.vo.*, java.util.*" %>
<%
	Review rv = (Review)request.getAttribute("rv");
	List<ReviewComment> commentList = (List<ReviewComment>)request.getAttribute("commentList");
	
	String LoggedId = (String)request.getAttribute("LoggedId");
	System.out.println("댓글보기창에서 로그드아이디:"+LoggedId);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/reviewComment.css" />

<style>
button.btn-reply, button.btn-delete {
    width:100px;
    background-color: #f8585b;
    border: none;
    color:#fff;
    padding: 15px 0;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 15px;
    margin: 4px;
    cursor: pointer;
    border-radius:10px;
}

button.btn-insert2{
	width:50px;
    background-color: #f8585b;
    border: none;
    color:#fff;
    padding: 15px 0;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 15px;
    margin: 4px;
    cursor: pointer;
    border-radius:10px;
    
    position:relative;
    top:-20px;
}
</style>

<script>
//대댓글입력
$(function(){

	$(".btn-reply").click(function(){	
		console.log("확인");
		var tr = $("<tr></tr>");
		var html = '<td style="display:none; text-align:left; colspan=2">';
		html += '<form action="<%=request.getContextPath()%>/review/reviewCommentInsert" method="post">';
		html += '<textarea name="reviewCommentContent" id="" cols="60" rows="3" style="margin-top:20px"></textarea>';
		html += '<input type="hidden" name="reviewNo" value="<%=rv.getReviewNo()%>" />';
		html += '<input type="hidden" name="reviewCommentWriter" value="<%=LoggedId%>" />';
		html += '<button type="submit" class="btn-insert2">등록</button>';
		html += '<input type="hidden" name="reviewRef" value="<%=rv.getReviewNo()%>" />';
		html += '<input type="hidden" name="reviewCommentLevel" value="2" />';
		html += '<input type="hidden" name="reviewCommentRef" value="'+$(this).val()+'" />';
		html += '</form>';
		html += '</td>';
		
		tr.html(html);
		tr.insertAfter($(this).parent().parent()).children("td").slideDown(800);
		
		//답글버튼 연속적으로 누르지 않도록 하기
		$(this).off('click');
		
		//새로생성한 요소에 대해 submit이벤트 핸들러 작성
		tr.find("form").submit(function(){
			//댓글textarea 유효성검사
			var content = $(this).children("textarea").val().trim();
			if(content.length == 0){
				e.preventDefault();
			}
		});
	});
});
</script>

<title>댓글 보기</title>
</head>
<body>
	<!-- 댓글목록 테이블 -->
		<table id="tbl-comment">
		
			<%if(!commentList.isEmpty()) {
				for(ReviewComment rc : commentList){
					if(rc.getReviewCommentLevel() == 1){
			%>
					<!-- 댓글인경우 -->
					<tr class="level1">
						<td>
							<sub class="comment-writer"><%=rc.getReviewCommentWriter() %></sub>
							<sub class="comment-date"><%=rc.getReviewCommentDate() %></sub>
							<br />
							<input type="text" value="<%=rc.getReviewCommentContent() %>" style="border:none" />
						</td>
						
						<td style="width:32%">
							<%if(!LoggedId.equals("")){ %>
							<button class="btn-reply" value="<%=rc.getReviewCommentNo()%>">답글</button>
							<%} %>
							<%if(rc.getReviewCommentWriter().equals(LoggedId)){ %>
							<button class="btn-delete" onclick="location.href='<%=request.getContextPath()%>/review/deleteReply?reviewCommentNo=<%=rc.getReviewCommentNo()%>'">삭제</button>
							<%} %>
						</td>
					</tr>
			<% 
					}
					else{
			%>
					<!-- 대댓글인경우 -->
					<tr class="level2">
						<td>
							<sub class="comment-writer"><%=rc.getReviewCommentWriter() %></sub>
							<sub class="comment-date"><%=rc.getReviewCommentDate() %></sub>
							<br />
							<%=rc.getReviewCommentContent() %>
						</td>
						
						<td style="width:32%">
							<%if(rc.getReviewCommentWriter().equals(LoggedId)){ %>
							<button class="btn-delete" onclick="location.href='<%=request.getContextPath()%>/review/deleteReply?reviewCommentNo=<%=rc.getReviewCommentNo()%>'">삭제</button>
							<%} %>
						</td>
					</tr>
			<% 
					}//end of if(getBoardCommentLevel==1)
				}//end of for
			}//end of if
			%>			
		</table>

</body>
</html>
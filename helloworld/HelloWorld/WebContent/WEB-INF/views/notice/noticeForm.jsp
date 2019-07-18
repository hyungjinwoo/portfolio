<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/notice.css" />

<script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<title>공지사항 글쓰기</title>

<script>
$(function(){
	$("#sel").on("change", function(){
		var result = $("#sel option:selected").val();
		if(result == 1){
			$.ajax({
				url:"<%=request.getContextPath()%>/notice/noticeCheck",
				data:"get",
				dataType:"json",
				success:function(data){
					console.log(data);
					
					var html = "<th><div>첨부파일</div></th>";
					html += "<td><input type='file' name='upFile'/></td>";
					
					$("#filediv").html(html);
				}
				
			});
		} else {
			$("#filediv").hide();
		}{}
	});
});
</script>
	<section class="notice-container">
		<h1>notice write</h1>
		<form action="<%=request.getContextPath()%>/notice/noticeFormEnd" method="post" enctype="multipart/form-data">
			<table id=noticeForm-table>
				<tr>
					<th><div>공지종류</div></th>
					<td>
						<select name="sel" id="sel">
							<option value="0">공지사항</option>
							<option value="1" id="test">이벤트</option>
						</select>
					</td>
				</tr>
				<tr id=filediv></tr>
				<tr>
					<th><div>제목</div></th>
					<td>		
						<input type="text" id="noticeTitle" name="noticeTitle"/>	
					</td>
				</tr>
				<tr>
					<th><div>내용</div></th>
					<td>
						<textarea name="noticeContent" id="noticeContent"></textarea>				
					</td>
				</tr>
				<tr>
					<th colspan="2">
						<input type="submit" value="등록" />				
						<input type="button" value="목록보기" onclick="location.href='<%=request.getContextPath()%>/notice/noticeList'"/>								
					</th>
				</tr>
			</table>
		</form>
	</section>
	
	
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
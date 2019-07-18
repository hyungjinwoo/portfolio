<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ page import="quest.model.vo.*" %>
<%
	Question q = (Question)request.getAttribute("q");
%>

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/question.css" />

<script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<title>문의사항 글쓰기</title>

<section class="question-container">
	
	<h1>question write</h1>
		
		<form  action="<%=request.getContextPath()%>/quest/questUpdateEnd" method="post" enctype="multipart/form-data">
			<table id="questionForm-table">
				<tr>
					<th><div>제목</div></th>
					<td>
						<input type="hidden" name="questWriter" value="<%=q.getQuestionWriter() %>"/>
						<input type="text" id="questTitle" name="questTitle" value="<%=q.getQuestionTitle() %>" />
					</td>
				</tr>
				<tr>
					<th><div>파일첨부</div></th>
					<td id="questionForm-upfile">
						<%if(q.getQuestionRenamedFileName()!=null){ %>
							<img src="<%=request.getContextPath() %>/upload/question/<%=q.getQuestionRenamedFileName() %>" alt="" />
							<input type="hidden" name="oldRenamedFile" value="<%=q.getQuestionRenamedFileName() %>" />
							<input type="hidden" name="oldOriginalFile" value="<%=q.getQuestionOriginalFileName() %>" />
						<%} %>
						<input type="file" name="upFile"/>	
						
						<label for="del">첨부파일 삭제</label>
						<input type="checkbox" name="delFile" id="del" />	
					</td>
				</tr>
				<tr>
					<th><div>내용</div></th>
					<td>
						<textarea name="questContent" id="questContent"><%=q.getQuestionCotent() %></textarea>				
					</td>
				</tr>
				<tr>
					<th><div>공개여부</div></th>
					<td>
						<select name="sel" id="sel">
							<option value="1">공개</option>
							<option value="0">비공개</option>
						</select>				
					</td>
				</tr>
				<tr>
					<th colspan="2">
						<input type="submit" value="수정" />				
						<input type="button" value="목록보기" onclick="location.href='<%=request.getContextPath()%>/quest/questList'"/>				
					</th>
				</tr>
			</table>
		</form>	
</section>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="event.model.vo.*" %>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<%
	Event e = (Event)request.getAttribute("e");
	EventPhoto ep = (EventPhoto)request.getAttribute("ep");
%>

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/event.css" />

<script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<title>공지사항</title>


<section class="event-container">
	<h1>event write</h1>
	<form action="<%=request.getContextPath()%>/event/eventUpdateEnd" method="post" enctype="multipart/form-data">
	
	
	
	
		<table id=eventForm-table>
			<tr>
				<th><div>제목</div></th>
				<td>
					<input type="hidden" name="eventNo" value="<%=e.getEventNo() %>" />
					<input type="text" name="eventTitle" value="<%=e.getEventTitle() %>" />				
				</td>
			</tr>
			<tr>
				<th><div>첨부파일</div></th>
				<td>
					<input type="file" name="upFile" id="" />
					<span id="cover"><%=ep.getOriginalFileName() %></span>
					<%if(ep.getOriginalFileName()!=null){ %>
						<br />
						<input type="checkbox" name="delFile" id="delFile" />
						<label for="delFile">첨부파일 삭제</label>
					<%} %>
					<input type="hidden" name="original" value="<%=ep.getOriginalFileName() %>" />
					<input type="hidden" name="renamed" value="<%=ep.getRenamedFileName() %>" />				
				</td>
				
			</tr>
			<tr>
				<th><div>내용</div></th>
				<td>
					<textarea name="eventContent" id="eventContent"><%=e.getEventContent() %></textarea>				
				</td>
			</tr>
			<tr>
				<th colspan="2">
					<input type="submit" value="수정" />				
					<input type="button" value="목록보기" onclick="location.href='<%=request.getContextPath()%>/event/eventList'"/>				
									
				</th>
			</tr>
		</table>
	</form>
</section>
	
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
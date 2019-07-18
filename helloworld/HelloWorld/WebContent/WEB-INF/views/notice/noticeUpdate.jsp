<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="notice.model.vo.*" %>

<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%
	Notice n = (Notice)request.getAttribute("n");
%>

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/notice.css" />

<script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<title>공지사항</title>

<section class="notice-container">
	<h1>notice write</h1>
	<form action="<%=request.getContextPath()%>/notice/noticeUpdateEnd" method="post">
		<table id=noticeForm-table>
			<tr>
				<th><div>제목</div></th>
				<td>
					<input type="hidden" name="noticeNo" value="<%=n.getNoticeNo() %>" />
					<input type="text" name="noticeTitle" id="noticeTitle" value="<%=n.getNoticeTitle() %>" />				
				</td>
			</tr>
			<tr>
				<th><div>내용</div></th>
				<td>
					<textarea id="noticeContent" name="noticeContent"><%=n.getNoticeContent() %></textarea>				
				</td>
			</tr>
			<tr>
				<th colspan="2">
					<input type="submit" value="수정" />				
					<input type="button" value="목록보기" onclick="location.href='<%=request.getContextPath()%>/notice/noticeList'"/>				
				</th>
			</tr>
		</table>
	</form>
</section>
	
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
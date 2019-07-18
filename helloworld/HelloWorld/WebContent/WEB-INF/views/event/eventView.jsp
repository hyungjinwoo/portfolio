<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<%@ page import="event.model.vo.*" %>

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/event.css" />

<script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<%
	Event e = (Event)request.getAttribute("e");
	EventPhoto ep = (EventPhoto)request.getAttribute("ep");
	Member memberAdmin = (Member)request.getAttribute("memberAdmin");
			
%>

<title>공지사항</title>

<script>
function deleteEvent(){
	if(!confirm("이 게시글을 정말 삭제하시겠습니까?")){
		return;
	}
	$("#eventDelFrm").submit();
}
</script>

<section class="event-container">

	<h1>event</h1>
	
	<nav>
		<div id="event-index">
			<a href="<%=request.getContextPath()%>/notice/noticeList">공지사항</a>&nbsp;&nbsp;
			<a href="<%=request.getContextPath()%>/event/eventList" id="event-select">이벤트</a>&nbsp;&nbsp;
			<a href="<%=request.getContextPath()%>/quest/questList">문의사항</a>&nbsp;&nbsp;
		</div>
	</nav>
	
	<table id="eventView-table">
		<tr>
			<td colspan="4" id="eventView-title">
				<%=e.getEventTitle()%>
			</td>
		</tr>
		<tr>
			<td colspan="4" id=eventView-writer>
				<img id="profile-viewer_"
					 src="<%=request.getContextPath()%>/upload/member/profile/<%=memberAdmin.getRenamedImgName()%>" />
				<span>
					관리자(admin)
				</span>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<div id="eventView-content">
					<img id="eventView-img" width="600px" height="600px" src="<%=request.getContextPath() %>/upload/notice/<%=ep.getRenamedFileName() %>" />
					<br />
					<%=e.getEventContent() %>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<button onclick="location.href='<%=request.getContextPath()%>/event/eventUpdate?eventNo=<%=e.getEventNo()%>'">수정하기</button>
				<button onclick="deleteEvent();">삭제하기</button>
				<button onclick="location.href='<%=request.getContextPath()%>/event/eventList'">목록보기</button>
			</td>
		</tr>
	
	</table>
</section>
	
	
	<form action="<%=request.getContextPath()%>/event/eventDelete" method="post" id="eventDelFrm">
	<input type="hidden" name="eventNo" value="<%=e.getEventNo()%>" />
</form>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
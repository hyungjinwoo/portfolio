<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<%@ page import="notice.model.vo.*" %>

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/notice.css" />

<script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<%
	Notice n = (Notice)request.getAttribute("n");
	Member m = (Member)request.getAttribute("member");
	Member memberAdmin = (Member)request.getAttribute("memberAdmin");
%>

<title>공지사항</title>

<section class="notice-container">

	<h1>notice</h1>
	
	<nav>
		<div id="notice-index">
			<a href="<%=request.getContextPath()%>/notice/noticeList" id="notice-select">공지사항</a>&nbsp;&nbsp;
			<a href="<%=request.getContextPath()%>/event/eventList">이벤트</a>&nbsp;&nbsp;
			<a href="<%=request.getContextPath()%>/quest/questList">문의사항</a>&nbsp;&nbsp;
		</div>
	</nav>
	
	<table id="noticeView-table">
		<tr>
			<td colspan="3" id="noticeView-title">
				<%=n.getNoticeTitle() %>
			</td>
			<td id="noticeView-date"><%=n.getNoticeDate() %></td>
		</tr>
		<tr>
			<td colspan="4" id=noticeView-writer>
				<img id="profile-viewer_"
					 src="<%=request.getContextPath()%>/upload/member/profile/<%=memberAdmin.getRenamedImgName()%>" />
				<span>
					관리자(admin)
				</span>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<div id="noticeView-content">
					<%=n.getNoticeContent() %>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<button onclick="location.href='<%=request.getContextPath()%>/notice/noticeUpdate?noticeNo=<%=n.getNoticeNo()%>'">수정하기</button>			
				<button onclick="location.href='<%=request.getContextPath()%>/notice/noticeDelete?noticeNo=<%=n.getNoticeNo()%>'">삭제하기</button>
				<button onclick="location.href='<%=request.getContextPath()%>/notice/noticeList'">목록보기</button>
			</td>
		</tr>
	
	</table>
</section>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
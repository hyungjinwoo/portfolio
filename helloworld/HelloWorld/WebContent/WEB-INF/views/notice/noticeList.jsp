<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<%@ page import="java.util.*, notice.model.vo.*" %>

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/notice.css" />

<script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<%
	int cPage = (int) request.getAttribute("cPage");
	String pageBar = (String) request.getAttribute("pageBar");
	List<Notice> list = (List<Notice>)request.getAttribute("list");
%>


<title>공지사항</title>

<script>
$(function(){
	$("#notice-write-btn").click(function(){
		location.href = "<%=request.getContextPath()%>/notice/noticeForm";
	});
});
</script>

<section class="notice-container">

	<h1>notice</h1>

	<nav>
		<div id="notice-index">
		<a href="<%=request.getContextPath()%>/notice/noticeList" id="notice-select">공지사항</a>&nbsp;&nbsp;
		<a href="<%=request.getContextPath()%>/event/eventList">이벤트</a>&nbsp;&nbsp;
		<a href="<%=request.getContextPath()%>/quest/questList">문의사항</a>&nbsp;&nbsp;
		</div>
	</nav>
	
	<%if(memberLoggedIn!=null && "admin".equals(memberLoggedIn.getMemberId())){ %>
		<button type="button" id="notice-write-btn">글쓰기</button>
	<%} %>

	<table id="noticeList-table">
	  <thead>
	    <tr>
	      <th>글번호</th>
	      <th>글제목</th>
	      <th>작성자</th>
	      <th>작성일</th>
	    </tr>
	  </thead>
	  <tbody>
	    <tr>
	    <%if(list.isEmpty()){%>
			<td colspan="4" align="center">게시글이 없습니다.</td>
	    <%} 
	      else {
        	for(Notice n : list){%>
		     	<tr>
		     		<td><%=n.getNoticeNo() %></td>
		     		<td>
		     			<a href="<%=request.getContextPath()%>/notice/noticeView?noticeNo=<%=n.getNoticeNo()%>">
		           			<%=n.getNoticeTitle() %>
		           		</a>
		           	</td>
		      		<td>관리자</td>
		      		<td><%=n.getNoticeDate() %></td>
		    	</tr>
    		<%} // end of for 
	      } // end of if %> 
	  </tbody>
	</table>
	
	<div class="pageBar">
		<%=pageBar %>
	</div>

</section>

	
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
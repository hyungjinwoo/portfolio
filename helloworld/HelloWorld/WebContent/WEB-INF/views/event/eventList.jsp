<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ page import="java.util.*, event.model.vo.*" %>

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/event.css" />

<%
	int cPage = (int)request.getAttribute("cPage");
	String pageBar = (String)request.getAttribute("pageBar");
	List<Event> list = (List<Event>)request.getAttribute("list");
%>

<script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<title>이벤트</title>

<section class="event-container">

	<h1>event</h1>

	<nav>
		<div id="event-index">
		<a href="<%=request.getContextPath()%>/notice/noticeList">공지사항</a>&nbsp;&nbsp;
		<a href="<%=request.getContextPath()%>/event/eventList" id="event-select">이벤트</a>&nbsp;&nbsp;
		<a href="<%=request.getContextPath()%>/quest/questList">문의사항</a>&nbsp;&nbsp;
		</div>
	</nav>
		
	
	<table id="event-table">
	  <thead>
	    <tr>
	      <th>글번호</th>
	      <th>글제목</th>
	      <th>작성자</th>
	      <!-- <th>작성일</th> -->
	    </tr>
	  </thead>
	  <tbody>
	    <tr>
	    <%if(list.isEmpty()){%>
			<td colspan="4" align="center">게시글이 없습니다.</td>
	    <%} 
	      else {
        	for(Event e : list){%>
		     	<tr>
		     		<td><%=e.getEventNo() %></td>
		     		<td>
		     			<a href="<%=request.getContextPath()%>/event/eventView?eventNo=<%=e.getEventNo()%>">
		           			<%=e.getEventTitle() %>
		           		</a>
		           	</td>
		      		<td>관리자</td>
		      		<!-- <td>왜없는지</td> -->
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
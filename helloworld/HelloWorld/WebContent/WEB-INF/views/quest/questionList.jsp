<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<%@ page import="java.util.*, quest.model.vo.*" %>

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/question.css" />

<script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<%
	int cPage = (int)request.getAttribute("cPage");
	String pageBar = (String)request.getAttribute("pageBar");
	
	List<Question>list = (List<Question>)request.getAttribute("list");
%>

<script>
$(function(){
	$("#question-write-btn").click(function(){
		location.href = "<%=request.getContextPath()%>/quest/questionForm";
	});
});
</script>

<script>
function arm(){
	alert("비공개 문의사항입니다");
}
</script>

<title>문의사항</title>

<section class="question-container">
	<h1>Q&A</h1>
	
	<nav>
		<div id="question-index">
		<a href="<%=request.getContextPath()%>/notice/noticeList">공지사항</a>&nbsp;&nbsp;
		<a href="<%=request.getContextPath()%>/event/eventList">이벤트</a>&nbsp;&nbsp;
		<a href="<%=request.getContextPath()%>/quest/questList" id="question-select">문의사항</a>&nbsp;&nbsp;
		</div>
	</nav>
	
	<button type="button" id="question-write-btn">글쓰기</button>
	
	<table id="question-table">
		  <thead>
		    <tr>
		      <th>글번호</th>
		      <th>글제목</th>
		      <th>작성자</th>
		      <th>공개여부</th>
		      <th>작성일</th>
		      <th>해결여부</th>
		      <%if("admin".equals(memberLoggedIn.getMemberId())){%>
		      <th>해결</th>
		      <%}%>
		    </tr>
		  </thead>
		  
		  <tbody>
		    <tr>
		    <%if(list.isEmpty()){%>
				<td colspan="7" align="center">게시글이 없습니다.</td>
		    <%} 
		      else {
	        	for(Question q : list){%>
			     	<tr>
			     		<td><%=q.getQuestionNo() %></td>
			     		 <%if(q.getQuestionLevel() != 1 && 
			     		 	!"admin".equals(memberLoggedIn.getMemberId()) &&
			     		 	!q.getQuestionWriter().equals(memberLoggedIn.getMemberId())){ %>
				     		<td>
				     			<a href="" onclick="arm();"><%=q.getQuestionTitle() %></a>
				           	</td>
			           	<%}else{ %>
			      			<td>
			      				<a href="<%=request.getContextPath()%>/quest/questView?questNo=<%=q.getQuestionNo()%>">
	           					<%=q.getQuestionTitle() %></a>
	           				</td>
	           			<%} %>
	           			<td><%=q.getQuestionWriter()%></td>
	           			<td id="visible"><%=q.getQuestionLevel()!=1?"비공개":"공개" %></td>
	     				<td><%=q.getQuestionDate() %></td>
	           			<td><%=q.getAnswerLevel()!=1?"해결완료":"해결중" %></td>
	           			<%if("admin".equals(memberLoggedIn.getMemberId())){%>
		           			<%if(q.getAnswerLevel()==1){ %>
		           				<td>
		           					<input type="checkbox" 
		           						   onclick="location.href='<%=request.getContextPath()%>/quest/questionClear?questNo=<%=q.getQuestionNo()%>' "/>
		           				</td>
		           			<%} %>
		           		<%} %>
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
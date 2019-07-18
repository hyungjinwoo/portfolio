<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<%@ page import="quest.model.vo.*, java.util.*"%>

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/question.css" />

<script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<%
	Question q = (Question)request.getAttribute("q");
	List<QuestionComment> list = (List<QuestionComment>)request.getAttribute("list");
	Member m = (Member)request.getAttribute("m");
%>


<script>
function deleteBtn(){
	if(!confirm("이 게시글을 정말 삭제하시겠습니까?")){
		return;
	}
	
	location.href='<%=request.getContextPath()%>/quest/questDelete?questNo=<%=q.getQuestionNo()%>';
}
</script>

<title>Q&A</title>

<section class="question-container">

	<h1>Q&A</h1>
	
	<nav>
		<div id="question-index">
			<a href="<%=request.getContextPath()%>/notice/noticeList">공지사항</a>&nbsp;&nbsp;
			<a href="<%=request.getContextPath()%>/event/eventList">이벤트</a>&nbsp;&nbsp;
			<a href="<%=request.getContextPath()%>/quest/questList" id="question-select">문의사항</a>&nbsp;&nbsp;
		</div>
	</nav>
	
	<table id="questView-table">
		<tr>
			<td colspan="3" id="questView-title">
				<%=q.getQuestionTitle() %>
			</td>
			<td id="questView-date"><%=q.getQuestionDate() %></td>
		</tr>
		<tr>
			<td colspan="4" id=questView-writer>
				<img id="profile-viewer_"
					 src="<%=request.getContextPath()%>/upload/member/profile/<%=m.getRenamedImgName()%>" />
				<span>
					<%=q.getQuestionWriter() %>
				</span>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<div id="questView-content">
				<%if(q.getQuestionRenamedFileName()!=null){ %>
					<img src="<%=request.getContextPath() %>/upload/question/<%=q.getQuestionRenamedFileName() %>" />
				<%} %>
				<br />
					<%=q.getQuestionCotent() %>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<div id="questView-comment">
					<%if(!list.isEmpty()){ 
					   for(QuestionComment qc : list){%>
					    <p>관리자(admin)</p>
						<span id="comment-content"><%=qc.getQuestionCommentContent() %></span>
						<span id="comment-date"><%=qc.getQuestionCommentDate() %></span>
							<%if(memberLoggedIn!=null && "admin".equals(memberLoggedIn.getMemberId())){ %>
								<button onclick="location.href='<%=request.getContextPath()%>/quest/deleteQuestionComment?questionCommentNo=<%=qc.getQuestionCommentNo()%>&questionNo=<%=q.getQuestionNo()%>'">삭제</button>
							<%} %>
						<%} %>
					<%} %>
				</div>
			</td>
		</tr>
		
		<%if(memberLoggedIn!=null && "admin".equals(memberLoggedIn.getMemberId())){ %>
			<tr>
				<td colspan="4">
					<form action="<%=request.getContextPath()%>/quest/questionCommentInsert">
						<textarea name="QuestionCommentContent" cols="85" rows="2"></textarea>
						<input type="hidden" name="questionRef" value="<%=q.getQuestionNo() %>" />
						<input id="comment-button" type="submit" value="등록" />
					</form>
				</td>
			</tr>
		
		<%} %>
		
		<tr>
			<td colspan="4">
			<%if(memberLoggedIn!=null && "admin".equals(memberLoggedIn.getMemberId())
					||q.getQuestionWriter().equals(memberLoggedIn.getMemberId())){ %>
				<button onclick="location.href='<%=request.getContextPath()%>/quest/questUpdate?questNo=<%=q.getQuestionNo()%>'">수정하기</button>
				<button onclick="deleteBtn();">삭제하기</button>				
			<%} %>
				<button onclick="location.href='<%=request.getContextPath()%>/quest/questList'">목록보기</button>					
			</td>
		</tr>
	
	</table>
</section>
	
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
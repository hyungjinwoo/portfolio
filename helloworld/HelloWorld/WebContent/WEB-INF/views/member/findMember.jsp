<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/findMember.css" />

<script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>

<%
	String findIdMsg = (String)request.getAttribute("findIdMsg");
	String findPasswordMsg = (String)request.getAttribute("findPasswordMsg");
	
	String memberName = (String)request.getAttribute("memberName");
	String tel = (String)request.getAttribute("tel");
	String memberId = (String)request.getAttribute("memberId");
	String question = (String)request.getAttribute("question");
	String answer = (String)request.getAttribute("answer");
%>
</head>
<body>

<script>
function resetAll(){
	$("input[type=text]").val("");
}

$(function(){
	$("#updatePasswordAfterFind").click(function(){
		window.opener.top.location.href="<%=request.getContextPath()%>/member/updatePasswordAfterFind?memberId=<%=memberId%>";
        window.close();
	});
});


</script>

<div id="find-container">
	<div id="findId">
	<h2>아이디 찾기</h2>
		<form action="<%=request.getContextPath()%>/member/findMemberId"
			  id="findIdFrm"
			  name="findIdFrm"
			  method="post">
			<label for="memberName">회원이름</label>
			<input type="text" 
				   id="memberName" 
				   name="memberName" 
				   value="<%=memberName!=null?memberName:""%>"
				   required/>
			<br />
			<label for="tel">전화번호</label>
			<input type="text" 
				   id="tel" 
				   name="tel" 
				   value="<%=tel!=null?tel:""%>"
				   required/>
			<br />
			<input type="button" value="초기화" onclick="resetAll();"/>
			<input type="submit" value="아이디 찾기" />
		</form>
		<br />
		<span id="findIdSpan"><%=findIdMsg!=null?findIdMsg:""%></span>
		
	</div>
	
	<br />
	<hr />
	
	<div id="findPassword">
	<br />
	<h2>비밀번호 찾기</h2>
		<form action="<%=request.getContextPath()%>/member/findMemberPassword"
			  id="findPasswordFrm"
			  name="findPasswordFrm"
			  method="post">
			<label for="memberId" >회원 아이디</label>
			<input type="text" 
				   id="memberId" 
				   name="memberId" 
				   value="<%=memberId!=null?memberId:""%>"
				   required/>
			<br />
			<label for="question">찾기 질문</label>
			&nbsp;
			<select name="question" 
					    id="question"
					    required>
					<option value="">비밀번호 찾기 질문</option>
					<option value="나의 고향은?" <%=question!=null&&question.equals("나의 고향은?")?"selected":""%>>나의 고향은?</option>
		            <option value="보물 1호는?" <%=question!=null&&question.equals("보물 1호는?")?"selected":""%>>보물 1호는?</option>
		            <option value="어머니의 성함은?" <%=question!=null&&question.equals("어머니의 성함은?")?"selected":""%>>어머니의 성함은?</option>
		            <option value="나의 가장 친한 친구는?" <%=question!=null&&question.equals("나의 가장 친한 친구는?")?"selected":""%>>나의 가장 친한 친구는?</option>
			</select> 
			<br />
			<label for="answer">질문 정답</label>
			<input type="text" 
				   id="answer" 
				   name="answer" 
				   value="<%=answer!=null?answer:""%>"
				   required/>
			<br />
			<input type="button" value="초기화" onclick="resetAll();"/>
			<input type="submit" value="비밀번호 찾기" />
		</form>
		<br />
		<span id="findPasswordSpan"><%=findPasswordMsg!=null?findPasswordMsg:""%></span>
	</div>
</div>



</body>
</html>
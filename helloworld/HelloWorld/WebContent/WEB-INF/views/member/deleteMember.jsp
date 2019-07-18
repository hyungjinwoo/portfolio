<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/updatePassword.css" />
<%
	String memberId = (String)request.getAttribute("memberId");
%>
<title>탈퇴하기</title>

<div id="updatePassword-container">
<br /><br /><br />
    <h1>탈퇴하기</h1>
    <form action="<%=request.getContextPath()%>/member/deleteMemberEnd"
          id="updatePasswordFrm"
          name="updatePasswordFrm"
          method="post">
        
        <input type="hidden" name="memberId_" value="<%=memberId%>"/>
        
        <label for="passwordNew">비밀번호</label>
        <input type="password"
               id="passwordNew" name="passwordNew"
               required/>
        <br />
        <label for="passwordCheck">비밀번호 확인</label>
        <input type="password"
               id="passwordCheck"
               required/>
        <span id="passwordCheck-span"></span>
        <br />
		<img src="<%=request.getContextPath()%>/images/cancel.png"
             onclick="location.href='<%=request.getContextPath()%>'"/>
        <img src="<%=request.getContextPath()%>/images/ok.png"
             onclick="submitFrm();"
             />
    </form>
</div>

<script>
function submitFrm(){
	$("#updatePasswordFrm").submit();
}


//유효성 검사용 알림창 띄우는 함수
function regExpTest(regExp, id, msg){
   if(regExp.test(document.querySelector("#"+id).value)){
       return true;
   }
   alert(msg);
   return false;
}


//비밀번호 확인 틀리면 바로 빨간 밑줄
$("#passwordCheck").keyup(function(){
   var pwd = document.getElementById("passwordNew");
   var pwdcheck = document.getElementById("passwordCheck");

   if(pwd.value == pwdcheck.value){
       $("#passwordCheck-span").text("O").css("color", "black");
       return true;
   }
   else{
       $("#passwordCheck-span").text("X").css("color", "red").css("font-weight", "bold");
   }
});
$("#passwordNew").keyup(function(){
   var pwd = document.getElementById("passwordNew");
   var pwdcheck = document.getElementById("passwordCheck");

   if(pwd.value == pwdcheck.value){
       $("#passwordCheck-span").text("O").css("color", "black");
       return true;
   }
   else{
       $("#passwordCheck-span").text("X").css("color", "red").css("font-weight", "bold");
   }
});

</script>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
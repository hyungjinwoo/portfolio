<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<%
    String memberId = (String)request.getAttribute("memberId");
%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/updatePassword.css" />

<title>비밀번호 변경</title>

<div id="updatePassword-container">
<br /><br /><br />
    <h1>비밀번호 변경</h1>
    <form action="<%=request.getContextPath()%>/member/updatePasswordEnd"
          id="updatePasswordFrm"
          name="updatePasswordFrm"
          method="post"
          onsubmit="return submitInvalid();">
        <input type="hidden" name="memberId_" value="<%=memberId%>"/>
        
        <label for="passwordOld">현재 비밀번호</label>
        <input type="password"
               id="passwordOld" name="passwordOld"
               required/>
        <br />
        <label for="passwordNew">새 비밀번호</label>
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
             onclick="location.href='<%=request.getContextPath()%>/member/memberMyPage?memberId=<%=memberId%>'"
             />
        <img src="<%=request.getContextPath()%>/images/ok.png"
             onclick="submitFrm();"
             />
    </form>
</div>

<script>
function submitInvalid(){
    //비밀번호 검사
   //숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호 정규식
   var regExp = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g;
   if(!regExpTest(regExp, "passwordNew", "비밀번호는 8~15자리 숫자/문자/특수문자를 포함해야 합니다.")){
       return false;
   }
}

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

//비밀번호 유효성 검사 충족 못 하면 바로 빨간 밑줄
//숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호 정규식
$("#passwordNew").keyup(function(){
    var regExp = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g;
    if(!regExp.test(document.getElementById("passwordNew").value)){
        $("#passwordNew").css("border-bottom", "2px dashed red");
    }
    else{
        $("#passwordNew").css("border-bottom", "2px solid black");
    }
});

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
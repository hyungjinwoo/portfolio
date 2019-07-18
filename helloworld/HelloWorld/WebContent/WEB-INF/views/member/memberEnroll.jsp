<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<title>회원가입</title>

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/memberEnroll.css" />

<script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>


<section id="enroll-container">
<h1>Join Us,</h1>

<form action="<%=request.getContextPath()%>/member/memberEnrollEnd"
	  name="memberEnrollFrm"
	  method="post"
	  enctype="multipart/form-data"
	  onsubmit="return submitInvalid();">
	<div id="enrollGuideDiv">
		<p id="enrollGuideP"></p>
	</div>
<table id="memberEnrollTable">
	<tr>
		<td rowspan="10">
			<span id="deleteProfile">X</span>
			<div id="profile-div">
				<img id="profile-viewer"
					 src="<%=request.getContextPath()%>/images/profilebasic.png" 
					 width="200px" height="200px"
					 />
			</div>
			<input type="file" 
				   name="profile" 
				   id="profile"
				   style="display:none"
				   onchange="loadProfile(this);"/>
		</td>
		<th>아이디</th>
		<td>
			<input type="text" 
				   name="memberId_"
				   id="memberId_" 
				   required/>
				<!-- <span class='triangle-isosceles0'></span> -->
			<span id="checkIdDuplicate"></span>
			<input type="hidden" id="isValid" value="0"/>
		</td>
	</tr>
	<tr>
		<th>이름</th>
		<td>
			<input type="text"
				   name="memberName"
				   id="memberName"
				   required/>
			
		</td>
	</tr>
	<tr>
		<th>비밀번호</th>
		<td><input type="password" 
				   name="password_"
				   id="password_" 
				   required/>
			<!-- <span class='triangle-isosceles'></span> -->
		</td>
	</tr>
	<tr>
		<th>비밀번호 확인</th>
		<td><input type="password" 
				   id="passwordCheck"
				   required/>
			<span id="passwordCheck-span"></span>
		</td>
	</tr>
	<tr>
		<th>질문</th>
		<td>
			<select name="question" 
				    id="question"
				    required>
				<option value="">비밀번호 찾기 질문</option>
				<option value="나의 고향은?">나의 고향은?</option>
	            <option value="보물 1호는?">보물 1호는?</option>
	            <option value="어머니의 성함은?">어머니의 성함은?</option>
	            <option value="나의 가장 친한 친구는?">나의 가장 친한 친구는?</option>
			</select>
		</td>
	</tr>
	<tr>
		<th>답</th>
		<td>
			<input type="text"
				   id="answer"
				   name="answer"
				   required/>
		</td>
	</tr>
	<tr>
		<th>성별</th>	
		<td>
			<input type="radio" 
				   name="gender"
				   id="genderF" 
				   value="F"
				   checked/>
			<label for="genderF">여</label>
			<input type="radio" 
				   name="gender"
				   id="genderM" 
				   value="M"/>
			<label for="genderF">남</label>
		</td>
	</tr>
	<tr>
		<th>생년월일</th>
		<td>
			<input type="date"
				   name="birth" 
				   id="birth"
				   required/>
		</td>
	</tr>
	<tr>
		<th>전화번호</th>
		<td>
			<input type="tel"
				   name="tel"
				   id="tel"
				   required/>
			<!-- <span class='triangle-isosceles1'></span> -->
		</td>
	</tr>
	<tr>
		<th>관심사</th>
		<td>
			 <input type="checkbox" 
				   name="interest" 
				   id="interest0"
				   class="interest-checkbox"
				   value="맛집"/>
			<label for="interest0"></label>
			<span class="interest-span">맛집</span>
			<input type="checkbox" 
				   name="interest" 
				   id="interest1"
				   class="interest-checkbox"
				   value="관광"/>
			<label for="interest1"></label>
			<span class="interest-span">관광</span>
			<input type="checkbox" 
				   name="interest" 
				   id="interest2"
				   class="interest-checkbox"
				   value="휴양"/>
			<label for="interest2"></label>
			<span class="interest-span">휴양</span>
			<input type="checkbox" 
				   name="interest" 
				   id="interest3"
				   class="interest-checkbox"
				   value="레저"/>
			<label for="interest3"></label>
			<span class="interest-span">레저</span>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<div id="inroll-submit">
				<input type="reset" value="초기화" />		 
				<input type="submit" value="가입하기" />
			</div>
		</td>
	</tr>
	
</table>

</form>

</section>

<script>
$(function(){
	$("#profile-viewer").click(function(){
		$("#profile").click();
	});
	
	$("#deleteProfile").click(function(){
		$("#profile-viewer").attr("src", "<%=request.getContextPath()%>/upload/member/profile/nonProfile.png");
		$("#profile").val("");
	});
});

function loadProfile(f){
	console.log(f.files);
	
	if(f.files && f.files[0]){
		var reader = new FileReader();
		reader.readAsDataURL(f.files[0]);
		reader.onload = function(){
			$("#profile-viewer").attr("src", reader.result);
		}
	}
}

function submitInvalid(){
	var file = $("#profile").val();
	if(file==""){
		$("#profile").remove();
	}
	
	//유효성 검사 시작
	//1.아이디
	var regExp1 = /^[a-z\d]{4,12}$/;
    if(!regExpTest(regExp1, "memberId_", "사용자 아이디가 부적합합니다.")){
         return false;
    }
    
    //2.아이디 중복 검사
	var isValid = $("#isValid").val();
	if(isValid == "0"){
		alert("아이디 중복 검사를 해 주세요.");
		return false;
	}
	
	//3.비밀번호 검사
    //숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호 정규식 
    var regExp2 = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g;
    //전체길이검사 /(?=^.{8,15}$)/
    //숫자하나 반드시 포함 /(?=.*\d)/ 또는  (?=\d)
    //영문자 반드시 포함 /(?=.*[a-zA-Z])/
    //특수문자 반드시 포함  /(?=.*[^a-zA-Z0-9])/
    //앞뒤에 ^.* .*$를 반드시 작성해야 한다.
    if(!regExpTest(regExp2, "password_", "비밀번호는 8~15자리 숫자/문자/특수문자를 포함해야 합니다.")){
        return false;
    }
	
  	//4.이름 검사 : 한글2글자 이상만 허용. 
    var regExp3 = /^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]{2,}$/;
    if(!regExpTest(regExp3, "memberName", "이름을 정확히 입력해 주세요.")){
        return false;
    }
    
  	//비밀번호일치여부 검사
    var pwd = document.getElementById("password_");
    var pwdcheck = document.getElementById("passwordCheck");
    if(pwd.value != pwdcheck.value){
    	alert("비밀번호가 일치하지 않습니다.");
    	pwd.select();
    	return false;
    }
    
    //전화번호 검사
    var regExp4 = /^01[0-9]{9}$/;
    if(!regExpTest(regExp4, "tel", "전화번호를 정확히 입력해 주세요.")){
    	return false;
    }
    
    return true;
    
}

//유효성 검사용 알림창 띄우는 함수
function regExpTest(regExp, id, msg){
    if(regExp.test(document.querySelector("#"+id).value)){
        return true;
    }
    alert(msg);
    return false;
}

//아이디 유효성 검사 충족 못 하면 바로 빨간 밑줄
//아이디 유효성 검사 통과하면 중복 검사 버튼 생성
$("span.triangle-isosceles0").hide();
$("div#enrollGuideDiv").hide();
$("#memberId_").focus(function(){
	$("span.triangle-isosceles0").show().html("아이디는 영문 4~12자로 입력해주세요. ");
	$("div#enrollGuideDiv").show();
	$("p#enrollGuideP").html("아이디는 4~12  글자 사이의 영문으로 입력해 주세요");
});
$("#memberId_").keyup(function(){
	/* $("span.triangle-isosceles0").show().html("아이디는 영문 4~12자로 입력해주세요. "); */
	$("#isValid").val("0");
    var regExp = /^[a-z\d]{4,12}$/;
    if(!regExp.test(document.querySelector("#memberId_").value)){
        $("#memberId_").css("border-bottom", "1px dashed red");
        $("#checkIdDuplicate").html("");
        $("span.triangle-isosceles0").show().html("아이디는 영문 4~12자로 입력해주세요.");
    }
    else{
        $("#memberId_").css("border-bottom", "1px solid gray");
        $("#checkIdDuplicate").html("<input onclick='checkIdDuplicate();' id='checkIdDuplicate-button' type='button' value='중복 검사' />");
        $("span.triangle-isosceles0").hide();
    }
});

//이름 유효성 검사 충족 못 하면 바로 빨간 밑줄
$("#memberName").keyup(function(){
	var regExp = /^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]{2,}$/;
	if(!regExp.test(document.querySelector("#memberName").value)){
		$("#memberName").css("border-bottom", "1px dashed red");
	}
	else{		
		$("#memberName").css("border-bottom", "1px solid gray");
	}
});

//비밀번호 유효성 검사 충족 못 하면 바로 빨간 밑줄
//숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호 정규식 
$("span.triangle-isosceles").hide();
$("div#enrollGuideDiv").hide();
$("#password_").keyup(function(){
		$("span.triangle-isosceles").show().html("비밀번호는 숫자/문자/특수문자 포함 형태의 8~15자리로 입력해주세요.");
		$("div#enrollGuideDiv").show();
		$("p#enrollGuideP").html("비밀번호는 숫자, 문자,<br/>특수문자를 포함하여<br/>8~15자로 입력해 주세요");
	var regExp = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g;
	if(!regExp.test(document.querySelector("#password_").value)){
	    $("#password_").css("border-bottom", "1px dashed red");
	    $("span.triangle-isosceles").show().html("비밀번호는 숫자/문자/특수문자 포함 형태의 8~15자리로 입력해주세요.");
	}
	else{
	    $("#password_").css("border-bottom", "1px solid gray");
	    $("span.triangle-isosceles").hide();
	}
});

//비밀번호 확인 틀리면 바로 빨간 밑줄
$("#passwordCheck").blur(function(){
	
    var pwd = document.getElementById("password_");
    var pwdcheck = document.getElementById("passwordCheck");

    if(pwd.value == pwdcheck.value){
        $("#passwordCheck-span").text("O").css("color", "black");
        return true;
    }
    else{
        $("#passwordCheck-span").text("X").css("color", "red").css("font-weight", "bold")
        .append("비밀번호를 확인해주세요").css({'font-size':'10px','position':'inline-block','animation':'shake 0.5s'});
    }
});
$("#password_").keyup(function(){
    var pwd = document.getElementById("password_");
    var pwdcheck = document.getElementById("passwordCheck");

    if(pwd.value == pwdcheck.value){
        $("#passwordCheck-span").text("O").css("color", "black");
        return true;
    }
    else{
        $("#passwordCheck-span").text("X").css("color", "red").css("font-weight", "bold");
    }
});

//전화번호 11자리 아니면 바로 빨간 밑줄
$("span.triangle-isosceles1").hide();
$("div#enrollGuideDiv").hide();
$("#tel").focus(function(){
	$("span.triangle-isosceles1").show().html("-없이 11자리 숫자로 입력해주세요. ");
	$("div#enrollGuideDiv").show();
	$("p#enrollGuideP").html("전화번호는 -없이<br/>11자리 숫자로 입력해 주세요");
});

$("#tel").keyup(function(){
    var regExp = /^01[0-9]{9}$/;
    if(!regExp.test(document.querySelector("#tel").value)){
        $("#tel").css("border-bottom", "1px dashed red");
        $("span.triangle-isosceles1").show().html("-없이 11자리 숫자로 입력해주세요. ");
    }
    else{
        $("#tel").css("border-bottom", "1px solid gray");
    }
});

function checkIdDuplicate(){
	var param = {
		memberId : $("#memberId_").val()		
	}
	
	$.ajax({
		url: "<%=request.getContextPath()%>/member/checkIdDuplicate",
		data: param,
		success: function(data){
			if(data==1){
				//아이디 사용 가능
				$("#isValid").val("1");
				$("#checkIdDuplicate").text("사용 가능");
			}
			else{
				//아이디 이미 존재
				$("#isValid").val("0");
				$("#checkIdDuplicate").text("존재하는 아이디입니다");
			}
		},
		error: function(jqxhr, textStatus, errorThrown){
			console.log("ajax 처리 실패ㅠ~!~!");
			console.log(jqxhr);
			console.log(textStatus);
			console.log(errorThrown);
		}
	});
	
}


</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
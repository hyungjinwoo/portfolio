<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%
	Member m = (Member)request.getAttribute("member");
	String interestStr = m.getInterest();
	System.out.println("겟인터레스트ㅡㅡ트틑---"+m.getInterest());
	//null 처리
	interestStr = interestStr==null?"":interestStr;
%>
<title>마이 페이지</title>
<script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/memberMyPage.css" />

<section id="update-container">
<form action="<%=request.getContextPath()%>/member/memberUpdate"
	  name="memberUpdateFrm"
	  method="post"
	  enctype="multipart/form-data"
	  onsubmit="return submitInvalid();">
	  
			<input type="hidden" 
				   name="memberId_"
				   id="memberId_"
				   value="<%=m.getMemberId()%>"/>
	  
	  
<table id="memberUpdateTable">
	
	<tr>
		<td rowspan="7">
			<span id="deleteProfile">X</span>
			<input type="hidden" name="noneProfileCheck" id="noneProfileCheck" value="1"/>
			<div id="profile-div">
				<img id="profile-viewer"
					 src="<%=request.getContextPath()%>/upload/member/profile/<%=m.getRenamedImgName()%>" 
					 width="200px" height="200px"
					 />
			</div>
			<input type="file" 
				   name="profile" 
				   id="profile"
				   style="display:none"
				   onchange="loadProfile(this);"/>
				   
			<!-- 프로필 사진 변경 안 하는 경우 -->
			<input type="hidden" name="originalImgNameOld" value="<%=m.getOriginalImgName()%>"/>
			<input type="hidden" name="renamedImgNameOld" value="<%=m.getRenamedImgName()%>"/>
			</td>
		<th>이름</th>
		<td>
			<input type="text"
				   name="memberName"
				   id="memberName"
				   value="<%=m.getMemberName() %>"
				   readonly/>
		</td>
		
		
	</tr>
	
	<tr>
		<th>질문</th>
		<td>
			<select name="question" 
				    id="question"
				    required>
				<option value="">비밀번호 찾기 질문</option>
				<option value="나의 고향은?" <%="나의 고향은?".equals(m.getQuestion())?"selected":""%>>나의 고향은?</option>
	            <option value="보물 1호는?" <%="보물 1호는?".equals(m.getQuestion())?"selected":""%>>보물 1호는?</option>
	            <option value="어머니의 성함은?" <%="어머니의 성함은?".equals(m.getQuestion())?"selected":""%>>어머니의 성함은?</option>
	            <option value="나의 가장 친한 친구는?" <%="나의 가장 친한 친구는?".equals(m.getQuestion())?"selected":""%>>나의 가장 친한 친구는?</option>
			</select>
		</td>
	</tr>
	<tr>
		<th>답</th>
		<td>
			<input type="text"
				   id="answer"
				   name="answer"
				   value="<%=m.getAnswer()%>"
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
				   <%="F".equals(m.getGender())?"checked":""%>
				   disabled/>
			<label for="genderF">여</label>
			<input type="radio" 
				   name="gender"
				   id="genderM" 
				   value="M"
				   <%="M".equals(m.getGender())?"checked":""%>
				   disabled/>
			<label for="genderF">남</label>
		</td>
	</tr>
	<tr>
		<th>생년월일</th>
		<td>
			<input type="date"
				   name="birth" 
				   id="birth"
				   value="<%=m.getBirth()%>"
				   readonly/>
		</td>
	</tr>
	<tr>
		<th>전화번호</th>
		<td>
			<input type="tel"
				   name="tel"
				   id="tel"
				   value="0<%=m.getTel()%>"
				   required/>
		</td>
	</tr>
	<tr>
		<th>관심사</th>
		<td>
			<input type="checkbox" 
				   name="interest" 
				   id="interest0"
				   class="interest-checkbox"
				   value="맛집"
				   <%=interestStr.contains("맛집")?"checked":""%>/>
			<label for="interest0"></label>
			<span class="interest-span">맛집</span>
			<input type="checkbox" 
				   name="interest" 
				   id="interest1"
				   class="interest-checkbox"
				   value="관광"
				   <%=interestStr.contains("관광")?"checked":""%>/>
			<label for="interest1"></label>
			<span class="interest-span">관광</span>
			<input type="checkbox" 
				   name="interest" 
				   id="interest2"
				   class="interest-checkbox"
				   value="휴양"
				   <%=interestStr.contains("휴양")?"checked":""%>/>
			<label for="interest2"></label>
			<span class="interest-span">휴양</span>
			<input type="checkbox" 
				   name="interest" 
				   id="interest3"
				   class="interest-checkbox"
				   value="레저"
				   <%=interestStr.contains("레저")?"checked":""%>/>
			<label for="interest3"></label>
			<span class="interest-span">레저</span>
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<div id="enroll-submit">
				<input type="reset" value="초기화" />		 
				<input type="button" value="비밀번호 변경" onclick="location.href='<%=request.getContextPath()%>/member/updatePassword?memberId=<%=m.getMemberId()%>'" />
				<input type="submit" value="수정 완료" />				
				<input type="button" value="탈퇴" onclick="location.href='<%=request.getContextPath()%>/member/deleteMember?memberId=<%=m.getMemberId()%>'" />
				
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
		$("#noneProfileCheck").val("0");
	});
	
});

function loadProfile(f){
	console.log(f.files);
	
	if(f.files && f.files[0]){
		var reader = new FileReader();
		reader.readAsDataURL(f.files[0]);
		reader.onload = function(){
			$("#profile-viewer").attr("src", reader.result);
			$("#noneProfileCheck").val("1");
		}
	}
}

function submitInvalid(){
	/* var file = $("#profile").val();
	if(file==""){
		$("#profile").remove();
	} */
	
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

//전화번호 11자리 아니면 바로 빨간 밑줄
$("#tel").keyup(function(){
    var regExp = /^01[0-9]{9}$/;
    if(!regExp.test(document.querySelector("#tel").value)){
        $("#tel").css("border-bottom", "2px dashed red");
    }
    else{
        $("#tel").css("border-bottom", "2px solid");
    }
});



</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
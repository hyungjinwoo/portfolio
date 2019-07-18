<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.*" %>
<%
	Member memberLoggedIn = (Member)session.getAttribute("memberLoggedIn");
	String visit = (String)request.getAttribute("visit");	

	//쿠키 관련 처리
	Cookie[] cookies = request.getCookies();
	boolean saveIdFlag = false;
	String memberIdSaved = "";
	
	if(cookies != null){
		for(Cookie c: cookies){
			String key = c.getName();
			String value = c.getValue();
			if("saveId".equals(key)){
				saveIdFlag = true;
				memberIdSaved = value;
			}
		}
	}
	if(memberLoggedIn != null){
	System.out.println(memberLoggedIn.getAnswer().replaceAll(" ", "").replaceAll("\\p{Z}", ""));
	}
	System.out.println("멤버로그드인=="+memberLoggedIn);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/common.css" />
<script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
$(function(){
	<%
	if(visit!=null && "first".equals(visit)){
	%>
		$("#menubar").css("right","0px");
		$("#menubar").css("display","inline");
		
	<%
		visit = "none";
	}
	%>	
	
	/* 메뉴바 클릭 이벤트 */
	$("#menu_img").click(function(){
		$("#menubar").animate({'right':'0px'},300,'linear');
		$("#menubar").css("display","inline");
	});
	
	$("#menubar-close").click(function(){
		$("#menubar").animate({'right':'-290px'},300, function(){
			$("#menubar").css("display","none");
		});
	});
	
	$("#chat-close").click(function(){
		$("#chatList-container").css({'display':'none'});
	});
	
	<%if(memberLoggedIn==null){%>
	$("#chat_img").click(function(){
		alert("로그인을 해주세요.");
		return;
	});
	<%}%>
	
	$("#chatList-container").draggable();
	
});


/* 로그인이 안된상태라면 웹소켓 접속 X */
<% if(memberLoggedIn!=null){ %>

var host = location.host;//접속하고 있는 서버의 ip주소
var ws = new WebSocket("ws://"+host+"<%=request.getContextPath()%>/chatWebSocket");

function chatFunction(){
	
	<% if("admin".equals(memberLoggedIn.getMemberId())){%>
		/* 관리자는 채팅메뉴 클릭시 현재 접속 인원수대로 채팅 방이 만들어진다 */
		$.ajax({
			url: "<%=request.getContextPath()%>/chat/clientList.chat",
			dataType: "json",
			success: function(data){
				for(var i=0; i<data.length; i++){
					
					if(data[i]=="admin" || $("[name="+data[i]+"]").html()) continue;
					
					var html = '<button id="chatroom-show-btn" onclick="viewChat(this);">'+data[i]+'</button>';
					$("#memberIdList-container").append(html);
					
					<%-- name값으로 채팅방 구분 --%>
					html = '<div id="chat-container" name="'+data[i]+'">'
					html += '<div id="chatLog-container">';
					html += '<div id="chatLog"></div>';	
					html += '<div id="current-chat"></div></div>';
					
					/* 사용자 input태그 */
					html += '<div id="input">';
					html += '<input type="text" id="chat-msg" onkeypress="enterEvent(event, this);">';
					html += '<button id="btn-send" onclick="sendAnswer(this);">전송</button></div></div>';
					
					$("#chatList-container").append(html);
					$("#chat-container").siblings("#chat-container").hide();
				}//end of for
			}//end of success
		})//end of ajax
	<%}else{%>
		/* 채팅방을 열때마다 로그 최신화 */
		$("#chatLog #msg").remove();
		$.ajax({
			url: "<%=request.getContextPath()%>/chat/chatLogList.chat",
			dataType: "json",
			data: "userId=<%=memberLoggedIn.getMemberId()%>",//파라미터직렬화
			success: function(data){
				for(var i in data){
					var chatLog = data[i];
					var html = "";
					
					if(chatLog.sender=="admin")	html = '<p id="msg" class="reciver">'+chatLog.content+'</p>';
					else html = '<p id="msg" class="sender">'+chatLog.content+'</p>';
					//chatLog에는 sender/reciver/msg가 들어있다
					//msg-container안의 div를 채팅로그를 보여주는 div와 현재 이루어지는 채팅을 보여주는 div 2가지로 나눔
					$("#chatLog").append(html);
					//스크롤처리 : 스크롤을 가장 하단으로 내린다.
					$("#chatLog-container").scrollTop($("#chatLog-container").prop("scrollHeight"));
				}//end of for
			}//end of success
		});// end of ajax
	<%}%>
		$("#chatList-container").show();
};//end of chatFunction

ws.onopen = function(e){
	
}

ws.onmessage = function(e){
	var o = JSON.parse(e.data);
	var sender = o.sender;
	var msg = o.msg;
	var to = o.to;
	console.log(o);
	var html = '<div class="msg-view sender"><span>'+sender+'</span>'+msg+'</div>';
	//관리자접속중 보낸이가 관리자면 자신, 관리자x 보낸이가 
	if(sender=="<%=memberLoggedIn.getMemberId()%>"){
		html = '<p id="msg" class="sender">'+msg+'</p>';
	}
	else {
		html = '<p id="msg" class="reciver">'+msg+'</p>';
	}
	$("[name="+(sender=='admin'?to:sender)+"] #chatLog-container #current-chat").append(html);
	
	//스크롤처리 : 스크롤을 가장 하단으로 내린다.
	$("[name="+(sender=='admin'?to:sender)+"] #chatLog-container").scrollTop($("[name="+(sender=='admin'?to:sender)+"] #chatLog-container").prop("scrollHeight"));
};

ws.onerror = function(e){
	
};

ws.onclose = function(e){
	
};
//회원이름버튼 클릭하면 해당 회원의 채팅창 띄우기(채팅로그도 같이 가져온다)
function viewChat(e){
	var id = $(e).html();
	//모든 회원의 채팅창을 숨김
	$("#chat-container").hide();
	//모든 회원의 채팅창을 숨김
	$("[name="+id+"]").show();

	//채팅창이 보여지면서 채팅창에 채팅로그를 출력
	$.ajax({
		url: "<%=request.getContextPath()%>/chat/chatLogList.chat",
		dataType: "json",
		data: "userId="+id,//파라미터직렬화
		success: function(data){
			for(var i in data){
				var chatLog = data[i];
				var html = "";
				if(chatLog.sender=="admin")	{
					html = '<p id="msg" class="sender">'+chatLog.content+'</p>';
				} else {
					html = '<p id="msg" class="reciver">'+chatLog.content+'</p>';
				}

				//chatLog에는 sender/reciver/msg가 들어있다
				//msg-container안의 div는 채팅로그를 보여주는 div와 현재 이루어지는 채팅을 보여주는 div 2가지로 나눔
				$("[name="+id+"] #chatLog-container #chatLog").append(html);
				//스크롤처리 : 스크롤을 가장 하단으로 내린다.
				$("[name="+id+"] #chatLog-container").scrollTop($("[name="+id+"] #chatLog-container").prop("scrollHeight"));
			}
		}
	});
};

<% if("admin".equals(memberLoggedIn.getMemberId())){%>
//관리자 답변 버튼
function sendAnswer(e){
	if($(e).siblings().val().trim().length==0) return;
	
	/* 전송메세지를 js객체로 생성 */
	var msg = {
			type: "message",
			msg: $(e).siblings().val(),
			sender: "admin",
			to: $(e).parent().parent().attr("name")
			};
	
	var jsonStr = JSON.stringify(msg);
	/* 웹소켓을 통해 메세지 전송 */
	ws.send(jsonStr);
	/* 스크롤처리 : 스크롤을 가장 하단으로 내린다. */
	$("#chatLog-container").scrollTop($("#chatLog-container").prop("scrollHeight"));
	/* #msg 초기화 */
	$(e).siblings().val("").focus();
}
<%}else{%>
//회원 문의버튼
function sendQuestion(e){
	if($(e).siblings().val().trim().length==0) return;
	
	/* 전송메세지를 js객체로 생성 */
	var o = {
		type: "message",
		msg: $(e).siblings().val(),
		sender: "<%=memberLoggedIn.getMemberId()%>",
		to: "admin"
	}
	var jsonStr = JSON.stringify(o);
	/* 웹소켓을 통해 메세지 전송 */
	ws.send(jsonStr);
	//스크롤처리 : 스크롤을 가장 하단으로 내린다.
	$("#chatLog-container").scrollTop($("#chatLog-container").prop("scrollHeight"));
	/* #msg 초기화 */
	$(e).siblings().val("").focus();
	
};
<%}%>

<%}%><%--로그인채크 종료 --%>


function findMember(){
	//아이디, 비밀번호 찾기 팝업 생성
	var url = "<%=request.getContextPath()%>/member/findMember";
	var title = "findMember";
	var spec = "width=500px, height=600px, left=500px, top=100px";
		
	var popup = open(url, title, spec); 
}

function enterEvent(event, el){
	<%if(memberLoggedIn != null){%>
		if(event.key=="Enter") {
			<%if("admin".equals(memberLoggedIn.getMemberId())){%>
			sendAnswer($(el).siblings());
			<%}else{%>
			sendQuestion($(el).siblings());
			<%}%>
		}
	<%}%>
}

</script>
</head>
<body>
<div id="container">
	<!-- 헤더 -->
	<header>
		<div id="header-container" 
			 class="header">
			<a href="<%=request.getContextPath()%>">
			<img id="logo_img" src="<%=request.getContextPath()%>/images/logo.png"/>
			<span>HelloWorld</span>
			</a>
			<%-- <nav>
				<div id="index">
				<a href="<%=request.getContextPath()%>">인덱스를</a>&nbsp;&nbsp;
				<a href="<%=request.getContextPath()%>">이렇게</a>&nbsp;&nbsp;
				<a href="<%=request.getContextPath()%>">넣어야</a>&nbsp;&nbsp;
				<a href="<%=request.getContextPath()%>">홈페이지</a>&nbsp;&nbsp;
				<a href="<%=request.getContextPath()%>">같을텐데</a>
				</div>
			</nav> --%>
			
			<img id="chat_img" src="<%=request.getContextPath()%>/images/chat.png" onclick="chatFunction();"/>
			<img id="menu_img" src="<%=request.getContextPath()%>/images/menu.png"/>		
		</div>
	</header>
	
	<!-- 채팅화면 -->
	<div id="chatList-container">
	<%if(memberLoggedIn!=null){ %>
		<% if("admin".equals(memberLoggedIn.getMemberId())){%>
			<div id="memberIdList-container"></div>
			<span id="chat-title">문의 답변하기</span>
		<%}else{%>
			<span id="chat-title">1:1 문의</span>		
		<%} %>
		<span id="chat-close">X</span>
		<div id="chat-container" name="<%=memberLoggedIn.getMemberId()%>">
			<div id="chatLog-container">
				<div id="chatLog"></div>
				<div id="current-chat"></div>
			</div>
			<div id="input">
				<input type="text" id="chat-msg" onkeypress="enterEvent(event, this);">
				<button id="btn-send" onclick="sendQuestion(this);">전송</button>
			</div>
		</div>
	<%} %>
	</div>
	
	<!-- 메뉴바 -->
	<div id="menubar">
	<div>
		<span id="menubar-close">X</span>	
	</div>
	
		<!-- 로그인 메뉴/폼 -->
		<div class="login-container">
		<%-- 1.로그인하지 않은 경우 --%>
		<%if(memberLoggedIn == null){ %>
			<form action="<%=request.getContextPath() %>/member/login" 
				  id="loginfrm"
				  method="post"
				  onsubmit="loginSubmit();"
				  >
				<span class="text">로그인이 필요합니다.</span>
				<br /><br />
				<table>
					<tr>
						<td>
							<img src="<%=request.getContextPath()%>/images/userid.png"/>
						</td>
						<td>
							<input type="text" 
								   name="memberId" 
								   id="memberId" 
								   placeholder="ID"
								   value="<%=saveIdFlag?memberIdSaved:"" %>"/>
						</td>
						<td rowspan="2">
							<input id="login_btn" 
								   type="submit" 
								   value="로그인"/>
						</td>
						
					</tr>
					
					<tr>
						<td><img src="<%=request.getContextPath()%>/images/key.png"/></td>
						<td>
							<input type="password" 
								   name="password" 
								   id="password"
								   placeholder="PASSWORD" />
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<input type="checkbox" 
								   name="saveId" 
								   id="saveId"
								   <%=saveIdFlag?"checked":""%>/>
							<label for="saveId"></label>
							<span id="saveId-span">&nbsp;아이디 저장</span>
						</td>
					</tr>
					
				</table>
			<br />
			&nbsp;
			<input type="button" value="아이디/비밀번호찾기"
				   class="membermenu"
				   onclick="findMember();"/>
			<input type="button" value="회원가입"
				   class="membermenu"
				   onclick="location.href='<%=request.getContextPath()%>/member/memberEnroll'"/>
			</form>
		<%
		}
		else{
		%>
		<!-- 로그인 한 경우 -->
			<table id='logged-in'>
				<tr>
					<td>
						<div id="profile-div">
						<img id="profile-viewer_"
					 		 src="<%=request.getContextPath()%>/upload/member/profile/<%=memberLoggedIn.getRenamedImgName()%>" 
					 		 width="120px" height="120px"/>
						</div>
					</td>
					<td>
						<%=memberLoggedIn.getMemberName()%>님!<br>반갑습니다
						<%if("admin".equals(memberLoggedIn.getMemberId())) {%>
						<input type="button"
							   id="adminPageBtn"
							   value="▶ Admin Page"
							   onclick="location.href='<%=request.getContextPath()%>/admin'"/>
						<%} %>
					</td>
				</tr>
				<tr>
					<td>
						<input type='button' 
							   value='My Page'
							   onclick="location.href='<%=request.getContextPath()%>/member/memberMyPage?memberId=<%=memberLoggedIn.getMemberId()%>'"/>
					</td>
					<td>
						<input type='button'
							   value='LogOut'
							   onclick="location.href='<%=request.getContextPath()%>/member/Logout'"/>
						
					</td>
					
				
				
				
		
			</table>
		
		<%
		}
		%>
			
		<input type="hidden" name="loginCheck" id="loginCheck" value="0"/>
		</div>
		<!-- 로그인 메뉴/폼 끝 -->	
		
		<script>
		function loginSubmit(){
			if($("#memberId").val().trim().length == 0){
				alert("아이디를 입력하세요.");
				$("#memberId").focus();
				return;
			}
			
			if($("#password").val().trim().length == 0){
				alert("비밀번호를 입력하세요.");
				$("#password").focus();
				return;
			}
		} 
		</script>
		
		
		
		
		<!-- 메뉴목록 -->
		<div id="menu">
			<ul id="menu-list1">
			<%if(memberLoggedIn != null) {%>
				<li><a href="<%=request.getContextPath()%>/plan/planList">일정보기</a></li>
			</ul>
			 <ul id="menu-list2">
				<li><a href="<%=request.getContextPath()%>/plan/planWriteFrm?userId=<%=memberLoggedIn.getMemberId()%>">일정작성</a></li>
			</ul>
			<ul id="menu-list3">
				<li><a href="<%=request.getContextPath()%>/review/MyreviewList?LoggedId=<%=memberLoggedIn.getMemberId()%>">내글보기</a></li>
			</ul>
			<ul id="menu-list4">
				<li><a href="<%=request.getContextPath()%>/review/reviewForm">리뷰작성</a></li>
			</ul>
			<ul id="menu-list5">
				<li><a href="<%=request.getContextPath()%>/quest/questList">문의사항</a></li>
			</ul>
			<%} %>
			<ul id="menu-list6">
				<li><a href="<%=request.getContextPath()%>/notice/noticeList">공지사항</a></li>
			</ul>
		</div>
		
	</div>
	<!-- 메뉴바 끝 -->
	
	<section id="content">
		
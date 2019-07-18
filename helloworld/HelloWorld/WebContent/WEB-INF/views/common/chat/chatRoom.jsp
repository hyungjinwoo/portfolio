<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>WebSocket ChatRoom</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<style>
#chat-container{
	width: 600px;
	margin: 0 auto;
	padding: 10px;
}
#msg-container{
	height: 200px;
	overflow-y: scroll;
}
#btn-clientList{
	margin: 10px 150px;
}
</style>
<script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script>
var host = location.host;//접속하고 있는 서버의 ip주소
var ws = new WebSocket("ws://"+host+"<%=request.getContextPath()%>/chat/helloWebSocket");

//웹소켓 연결 성공시 호출
ws.onopen = function(e){
	console.log("open");
}
//서버로부터 메세지를 받았을 경우 호출
ws.onmessage = function(e){
	console.log("message: "+e.data);
	
	var o = JSON.parse(e.data);
	var sender = o.sender;
	var msg = o.msg.split("|");
	
	if(msg[1]!='admin' && msg[1]!='<%=request.getParameter("userId")%>' && sender!='<%=request.getParameter("userId")%>') return;
	
	var html = '<li class="list-group-item">';
	html += '<span class="badge badge-pill badge-info">'+sender+'</span>'+msg[0]+'</li>';
	
	$("#msg-container ul").append(html);
	
	//스크롤처리 : 스크롤을 가장 하단으로 내린다.
	$("#msg-container").scrollTop($("#msg-container").prop("scrollHeight"));
}
//서버에서 에러 발생시 호출
ws.onerror = function(e){
	console.log("error");
}
//서버 연결이 종료된 경우 호출
ws.onclose = function(e){
	console.log("close");
}
$(function(){
	$("#btn-send").click(function(){
		if($("#msg").val().trim().length==0) return;
		//전송메세지를 js객체로 생성
		var o = {
				type: "message",
				msg: $("#msg").val(),
				sender: '<%=request.getParameter("userId")%>',
				time: Date.now()//utc타임을 리턴
		}
		var jsonStr = JSON.stringify(o);
		//웹소켓을 통해 메세지 전송
		ws.send(jsonStr);
		
		//#msg 초기화
		$("#msg").val('').focus();
		
	});
	//접속자 확인
	$("#btn-clientList").click(function(){
		$.ajax({
			url: "<%=request.getContextPath()%>/chat/clientList.chat",
			dataType: "json",
			success: function(data){
				console.log(data);
				alert(data+"("+data.length+")");
			}
		})
	});
});
</script>
</head>
<body>
	<div class="jumbotron jumbotron-fluid">
	  <div class="container">
	    <h1 class="display-4">Hello WebSocket</h1>
	    <p class="lead">회원채팅입니다.</p>
	  </div>
	</div>
	<button type="button" class="btn btn-outline-success" 
			id="btn-clientList">현재 접속자 확인</button>
	
	<section id="chat-container">
		<div id="msg-container">
			<ul class="list-group list-group-flush">
			</ul>
		</div>
		<!-- 사용자 input태그 -->
		<div class="input-group mb-3">
		  <input type="text" class="form-control" id="msg">
		  <div class="input-group-append">
		    <button class="btn btn-outline-secondary" type="button" id="btn-send">Send</button>
		  </div>
		</div>
	</section>
</body>
</html>
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
	var msg = new Array();
	msg = o.msg.split("|");
	
	//현재 관리자 채팅창에 보낸 회원의 채팅창이 있는지 여부를 확인하고 없다면 새로 생성
	//해당 회원으로 보내기 태그 및 이벤트까지 작성

	if(sender!="admin" && !$("#"+sender+"-chatroom").val()){
		var html = '<button class="btn btn-outline-secondary" type="button" id="'+sender+'-chatroom" value="'+sender+'">'+sender+'</button>\n';
		$("#btn-list").append(html);
		
		html = '<div id="msg-container" name="'+sender+'-msg">\n';
		html += '<ul class="list-group list-group-flush"></ul></div>\n';
		/* 값채크를 위한 히든 */
		html += '<input type="hidden" id="'+sender+'" value="'+sender+'">\n';
		/* 사용자 input태그 */
		html += '<div class="input-group mb-3">\n';
		html += '<input type="text" class="form-control" id="'+sender+'-msg">\n';
		html += '<div class="input-group-append">\n';
		html += '<button class="btn btn-outline-secondary" type="button" id="'+sender+'-btn-send">Send</button></div></div>\n';
		/* 메세지 전송 이벤트 함수 추가 */
		html += '<script>$(function(){\n';
		html += '$("#'+sender+'-btn-send").click(function(){\n';
		html += 'if($("#'+sender+'-msg").val().trim().length==0) return;\n';
		/* 전송메세지를 js객체로 생성 */
		html += 'var o = {type: "message",msg: $("#'+sender+'-msg").val()+"|'+sender+'",sender: "<%=request.getParameter("userId")%>",time: Date.now()}\n';
		html += 'var jsonStr = JSON.stringify(o);\n';
		/* 웹소켓을 통해 메세지 전송 */
		html += 'ws.send(jsonStr);\n';
		/* #msg 초기화 */
		html += '$("#'+sender+'-msg").val("").focus();});});';
		
		$("#chat-container").append(html);
		
	}
	
	
	
	if(o.type == 'adieu') return;
	
	html = '<li class="list-group-item">';
	html += '<span class="badge badge-pill badge-info">'+sender+'</span>'+msg[0]+'</li>';
	
	$("[name="+(sender=='admin'?msg[1]:sender)+"-msg] ul").append(html);
	
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
});
</script>
</head>
<body>
	<div class="jumbotron jumbotron-fluid">
	  <div class="container">
	    <h1 class="display-4">Hello WebSocket</h1>
	    <p class="lead">관리자 채팅입니다.</p>
	  </div>
	</div>
	<div id="btn-list">
		<button type="button" class="btn btn-outline-success" 
				id="btn-clientList">현재 접속자 확인</button>
	</div>
	<section id="chat-container">
		
	</section>
</body>
</html>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@page import="notice.model.vo.Notice"%>
<%@page import="quest.model.vo.Question"%>
<%@page import="java.util.List"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    int todayQnaCnt=(Integer)request.getAttribute("todayQnaCnt");
    int totalQnaCnt=(Integer)request.getAttribute("totalQnaCnt");
    int ingQnaCnt=(Integer)request.getAttribute("ingQnaCnt");
    int todayReviewCnt=(Integer)request.getAttribute("todayReviewCnt");
    int totalReviewCnt=(Integer)request.getAttribute("totalReviewCnt");
    int totalMemberCnt=(Integer)request.getAttribute("totalMemberCnt");
    int currentUserCnt=(Integer)request.getAttribute("currentUserCnt");
    int totalNoticeCnt=(Integer)request.getAttribute("totalNoticeCnt");
    List<Question> qnaList=(List<Question>)request.getAttribute("qnaList");
    List<Notice> noticeList=(List<Notice>)request.getAttribute("noticeList");
    JSONArray chartList=(JSONArray)request.getAttribute("chartList");
    JSONArray a=(JSONArray)chartList.get(chartList.size()-1);
    
    %> 


<title>관리자 페이지</title>
<script
  src="https://code.jquery.com/jquery-3.4.0.min.js"
  integrity="sha256-BJeo0qm959uMBGb65z40ejJYGSgR7REI4+CW1fNKwOg="
  crossorigin="anonymous"></script>
 
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/admin/adminMain.css">

<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">


<!-- bootstrap -->
<!-- 
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
 -->
 <!-- chart.jsp에서 사용하는 js -->
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
 
 <!-- map.jsp에서 사용하는 js -->
<script src="https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/markerclusterer.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 
<script type="text/javascript">
  google.charts.load('current', {'packages':['corechart']});
  google.charts.setOnLoadCallback(drawVisualization);

  function drawVisualization() {
    // Some raw data (not necessarily accurate)
    var data = google.visualization.arrayToDataTable(<%=chartList%>);

    var options = {
      title : '최근 일주일 간 접속자수',
      vAxis: {title: '접속자수'},
      hAxis: {title: 'day'},
      seriesType: 'bars',
      series: {2: {type: 'line'}}
    };

    var chart = new google.visualization.ComboChart(document.getElementById('connChart'));
    chart.draw(data, options);
  }
</script>

 
 <script>
$(function(){
	$("#adminHome").click(function(e){
		location.href = "<%=request.getContextPath()%>/admin";
})

$("#adminChart").click(function(e){
	$.ajax({
		url:"<%=request.getContextPath()%>/admin/chart",
	dataType:"html",
		success:function(data){
			$("#adminBody").html(data);
		},
	error: function(jqxhr,textStatus, errorThrown){
		console.log("ajax처리 실패!");
		console.log(jqxhr);
		console.log(textStatus);
		console.log(errorThrown);
	}
	});
})


$("#adminMember").click(function(e){
	$.ajax({
		url:"<%=request.getContextPath()%>/admin/member",
	dataType:"html",
		success:function(data){
			$("#adminBody").html(data);
		},
	error: function(jqxhr,textStatus, errorThrown){
		console.log("ajax처리 실패!");
		console.log(jqxhr);
		console.log(textStatus);
		console.log(errorThrown);
	}
	});
})

$("#adminMap").click(function(e){
	$.ajax({
		url:"<%=request.getContextPath()%>/admin/map",
		dataType:"html",
			success:function(data){
				$("#adminBody").html(data);
			},
		error: function(jqxhr,textStatus, errorThrown){
			console.log("ajax처리 실패!");
			console.log(jqxhr);
			console.log(textStatus);
			console.log(errorThrown);
		}
		});
	})
	
});
 </script>


<div id="admin-container">
	<div id="nav">
		<div id="b5" class="btn item"><i id="adminHome" class="fas fa-home"></i>Home</div>
        <div id="b4" class="btn item"><i id="adminChart" class="fas fa-chart-line"></i>분석자료</div>
        <div id="b2" class="btn item"><i id="adminMember" class="far fa-address-book"></i>회원관리</div>
        <div id="b1" class="btn item"><i id="adminMap" class="fas fa-map-marked-alt"></i>리뷰관리</div>
	</div>
	
	<div id="adminBody">
		<div id="initialPage">
			<div id="ipCon1">
				<!-- <div class="ipCon1-item" id="ipHead">Admin Page</div>
				 -->
				<div class="ipCon1-item" id="todayChart">
				
					<div id="todayChart-item1">
						<div id="tc-item1-c1">
							<p>[ TODAY ]</p>
						</div>
						<div id="tc-item1-c2">
							<div id="tc-item1-c2-item1"> 
								<img class="todayIcon" src="<%=request.getContextPath()%>/images/admin-today.png"/>
    							<br /><span class="users">방문자</span>
    							<p class="cnt"><%=a.get(1)%></p>
    						</div>
							<div id="tc-item1-c2-item2">
								<img class="todayIcon" src="<%=request.getContextPath()%>/images/admin-question.png"/>
								<br /><span class="comments">문의사항</span>
								<p class="cnt"><%=todayQnaCnt%></p>
							</div>
							<div id="tc-item1-c2-item3">
								<img class="todayIcon" src="<%=request.getContextPath()%>/images/admin-review.png"/>
								<br /><span class="window-restore">리뷰 수</span>
								<p class="cnt"><%=todayReviewCnt%></p>
							</div>
						</div>
					</div>
					
					<div id="todayChart-item2">
						<div id="tc-item2-c1">
							<p>현재 접속자 수 : <%=currentUserCnt %></p>
						</div>
						<div id="tc-item2-c2">
							<ul>
								<li>▶ 총 회원 수 : <%=totalMemberCnt%></li>
								<li>▶ 총 리뷰 수 : <%=totalReviewCnt%></li>
								<li>▶ 문의사항 : <%=totalQnaCnt%></li>
								<li>▶ 미답변 문의 : <%=ingQnaCnt%></li>
								<li>▶ 공지사항 : <%=totalNoticeCnt%></li>
							</ul>
						</div>
					</div>
				</div>
				
				<div class="ipCon1-item" id="connChart">
					
				</div>
			</div>
			
			<div id="ipCon2">
			
				<div class="ipCon2-item" id="latelyQna">
					<div id="latelyQna-head">
						<p>최신 문의사항</p>
					</div>
					<div id="latelyQna-body">
						<ul>
						<%
						for(Question q:qnaList){
						%>
							<li><a href="<%=request.getContextPath()%>/quest/questView?questNo=<%=q.getQuestionNo()%>"><%=q.getQuestionTitle()%></a></li>
						<%								
						}
						%>
						</ul>
						<p><i class="fas fa-plus-circle"></i></p>
					</div>
				</div>
				
				<div class="ipCon2-item" id="latelyNotice">
					<div id="latelyNotice-head">
						<p>공지사항</p>
					</div>
					<div id="latelyNotice-body">
						<ul>
						<%
						for(Notice n:noticeList){
						%>
							<li><a href="<%=request.getContextPath()%>/notice/noticeView?noticeNo=<%=n.getNoticeNo()%>"><%=n.getNoticeTitle()%></a></li>
						<%								
						}
						%>
						</ul>
						<p><i class="fas fa-plus-circle"></i></p>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
    
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
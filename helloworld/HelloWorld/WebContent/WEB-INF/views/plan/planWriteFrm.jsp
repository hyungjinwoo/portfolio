<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, plan.model.vo.*" %>
<%
	List<WishPlace> wishPlace = (List<WishPlace>)request.getAttribute("wishPlace");
%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<script src="<%=request.getContextPath()%>/jquery-ui-1.12.1/jquery-ui.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.ui.touch-punch.min.js"></script>
<script src="<%=request.getContextPath()%>/js/moment.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.rangecalendar.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.sortable.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.easing.1.3.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/rangecalendar.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/rangecalendarstyle.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/plan.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
	  integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" 
		integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>


<script>
$(function(){
	//캘린더 생성
	$("#calender-container").rangeCalendar({
		minRangeWidth: 2,
		maxRangeWidth: 60,
		start : "+1",
		startDate: moment(),
		theme:"full-green-theme",
		changeRangeCallback: rangeChanged
	});
	
	//일정값 변경시 발생하는 이벤트 핸들링
	function rangeChanged(target,range){
		$("#startDay").val(range.start);
		$("#endDay").val(range.end);
		if($("#planWriteList #drag-ul").length < range.width){
			for(var i = 0; i<range.width; i++){
				if($("#planWriteList #drag-ul").length == range.width) return;
				var html = "";
				html = "<ul id='drag-ul' class='list-group list-group-sortable-connected'><p id='day' draggable='false'>"+($("#planWriteList #drag-ul").length+1)+"일차</p></ul>";
				$("#planWriteList").append(html);
			}
		}else if($("#planWriteList #drag-ul").length > range.width){
			while(true){
				if($("#planWriteList #drag-ul").length == range.width) return;
				$("#planWriteList #drag-ul").last().remove("#drag-ul");
			}	
		}
	}
	
	//드래그 앤 드롭 핸들링
	$('.list-group-sortable-connected').sortable({
        placeholderClass: 'list-group-item',
        connectWith: '.connected',
        items: ':not(#day)'
    });
	
	//아코디언 핸들링
	$('.placeView-arcordian').click(function(){
        $(this).siblings("[id$=Body]").slideToggle();
	});
	
	$("#searchHead").one("click",function(e){
		$.ajax({
			url:"<%=request.getContextPath()%>/plan/googleMap",
			dataType:"html",
			success:function(data){
				$("#search-container").html(data);
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

function memoView(e){
	$(e).siblings("#placeMemo").slideToggle();
}

function delPlace(e) {
	$(e).parent().remove();
}

//일정 리스트 전송 함수
function planListInput(){
	//일정 타이틀 입력확인
	var check = $("#planTitle").val().trim();
	if(check == "") {
		alert("일정 제목을 입력해 주세요");
		return;
	}
	
	var planUl = $("#planFrm-container #drag-ul").length;
	console.log($("#planFrm-container #drag-ul").eq(i).find("li").eq(0).find("#placeName").val());
	
	var planDayArr = new Array();
	for(var i=0; i<planUl; i++){
		var planLi = $("#planFrm-container #drag-ul").eq(i).find("li").length;
		var placeArr = new Array();
		for(var j=0; j<planLi; j++){
			placeArr[j] = {
					placeName : $("#planFrm-container #drag-ul").eq(i).find("li").eq(j).find("#placeName").val(),
					placeLat : $("#planFrm-container #drag-ul").eq(i).find("li").eq(j).find("#placeLat").val(),
					placeLng : $("#planFrm-container #drag-ul").eq(i).find("li").eq(j).find("#placeLng").val(),
					placeMemo : $("#planFrm-container #drag-ul").eq(i).find("li").eq(j).find("#placeMemo").val()		
			};
/* 			console.log(placeArr[j]); */
		}
		planDayArr[i] = placeArr;
	}
	console.log(planDayArr);
	var plan = {
			planTitle : $("#planTitle").val(),
			memberId : "<%=memberLoggedIn.getMemberId()%>",
			startDay : $("#startDay").val(),
			endDay : $("#endDay").val(),
			planContent : planDayArr
	}
	
	console.log(plan);
	
	//json 파싱
	var planJson = JSON.stringify(plan);
	
	$.ajax({
		url:"<%=request.getContextPath()%>/plan/planWriteEnd",
		data: {planJson:planJson},
		success: function(){
			alert("일정 작성 완료!");
			location.href='<%=request.getContextPath()%>/plan/planList?memberId=<%=memberLoggedIn.getMemberId()%>'
		}
	});
	
};




</script>
<section id="planView-container">
<input type="text" id="planTitle" placeholder="일정 제목을 입력해 주세요." required/>
<!-- bootstrap을위해 하나의 컨테이너로 묶는다 -->
<div id="wishList-search-container">
	<!-- 위시리스트 컨테이너 -->
	<div  id="wishHead" class="placeView-arcordian">
		<span id="wishPlaceTitle">찜 리스트</span>
	</div>
	<div id="wishBody" class="slide-body">
		<div id="wishPlaceList-container" >
		<%if(wishPlace.size() == 0){ %>
			<span id="noWishPlaceList" draggable="false">추가하신 장소가 없습니다.</span>
			
		<%}else{ %>
			<ul id="drag-uls" class="list-group list-group-sortable-connected">
				<%for(WishPlace wp : wishPlace){ %>
				<li class="list-group-item list-group-item-info" draggable="true">
					<img src='<%=request.getContextPath()%>/images/plan-memo.png' id='inputPlaceMemo' onclick='memoView(this);'/>
					<input type='hidden' id='placeName' value='<%=wp.getPlaceName()%>'/>
					<input type='hidden' id='placeLat' value='<%=wp.getPlaceLat()%>'/>
					<input type='hidden' id='placeLng' value='<%=wp.getPlaceLng()%>'/>
					<span id="wishPlaceName"><%=wp.getPlaceName()%></span>
					<button id='delPlace' onclick='delPlace(this);'>삭제</button>
					<textarea id='placeMemo'></textarea>
				</li>
				<%} %>
			</ul>
		<%} %>
		</div>
	</div>
	<!-- 지도검색 컨태이너 -->
	<div id="searchHead" class="placeView-arcordian">
		<span id="searchPlaceTitle">지역 검색</span>
	</div>
	<div id="searchBody" class="slide-body">
		<div id="search-container" >
			
		</div>
		<ul id="drag-ul" class="list-group list-group-sortable-connected">
		</ul>
	</div>
</div>



<!-- 히든 컨테이너 -->
<div id="planWriteFrm-container">
	<!-- 일정 날짜 선택 달력 -->
	<div id="calender-container">
	</div>
	<!-- 일정작성폼 컨테이너 -->
	<div id="planFrm-container">
		<div id="planWriteList">

		</div>
	</div>
</div>
	<button id="planList-input-btn" onclick="planListInput();">저장</button>

<!--히든태그-->
<input type="hidden" id="startDay" value=""/>
<input type="hidden" id="endDay" value=""/>
</section>


<%@ include file="/WEB-INF/views/common/footer.jsp" %>
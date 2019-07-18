<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, plan.model.vo.*" %>
<%
	List<Plan> planList = (List<Plan>)request.getAttribute("planList");
	List<PlaceList> planPlaceList = (List<PlaceList>)request.getAttribute("planPlaceList");
%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>


<link rel="stylesheet" href="<%=request.getContextPath() %>/css/planList.css" />

<script src="https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/markerclusterer.js"></script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB2BbISLnHXNWyIA3jjgUEg8q-R-71NlzY&callback=initMap&libraries=places"></script>


<script>
$(function(){
	$(".planList-title").click(function(){
		$(this).next().toggle(10,function(){
			$(this).prev().toggleClass("on",function(){
				$(this).children("button").toggle(10)	
			});
		});
	});
});

function loadMap(e){
	var pNo=$(e).val();
	console.log(pNo);
	$.ajax({
		url:"<%=request.getContextPath()%>/plan/myPlanMapView",
		dataType:"html",
		data: "planNo="+pNo,
		success:function(data){
			$("#"+pNo).find("#planMap").append(data);
		},error:function(){
		
		}
		
	});
}

function deleteFunction(planNo){
	var bool = confirm("일정을 삭제하시겠습니까?");
	if(bool){
		location.href="<%=request.getContextPath()%>/plan/planDelete?planNo="+planNo;
	}
}
</script>
<style>
.on{
	border-left: 6px solid #2dac89;
	color: #2dac89;
}
</style>

<title>MyPlan</title>

<div id="myReviewPlan-profile-div">
	<table id="myReviewPlan-profile-table">
		<tr>
			<td rowspan="2">
				<div id="user-profile-div">
					<img id="profile-viewer"
						 src="<%=request.getContextPath()%>/upload/member/profile/<%=memberLoggedIn.getRenamedImgName()%>" 
						 width="200px" height="200px"
						 />
				</div>
			</td>
			<td colspan="2">
				<span id="myReviewPlan-profile-id"><%=memberLoggedIn.getMemberId()%></span>
			</td>
		</tr>
		<tr>
			<td>
				<span id="myPlanTotal-title">일정</span>
				<span id="myPlanTotal"></span>
			</td>
			<td>
				<span id="myReviewTotal-title"><a href="<%=request.getContextPath()%>/review/MyreviewList?LoggedId=<%=memberLoggedIn.getMemberId()%>">리뷰</a></span>
				<span id="myReviewTotal"></span>
			</td>
		</tr>
	
	
	</table>
</div>
<br /><br />
<div id="planList-container">
	<ul id="planList">
 	 	<%if(planList == null){%>
			<li>일정이 없습니다.</li>
		<%}else{%>
			<%for(Plan p : planList){%>
				<li class="planList-title" onclick="loadMap(this);" value="<%=p.getPlanNo()%>">
					<span id="planTitle"><%=p.getPlanTitle() %></span><br/>
					<span id="planDate"><%=p.getStartDate() %> ~ <%=p.getEndDate() %></span>
					<input type="hidden" id=planNo value=<%=p.getPlanNo() %> />
					<button id="planUpdate-btn"
							onclick="location.href='<%=request.getContextPath()%>/plan/planRewriteFrm?planNo=<%=p.getPlanNo()%>'">수정</button>
					<button id="plandelete-btn"
							onclick="deleteFunction(<%=p.getPlanNo() %>);">삭제</button>
				</li>
						<li id="<%=p.getPlanNo()%>" class="myPlan-detail">
							<div id="planMap" class="myPlan-detail-map"></div>
							<div class="myPlan-detail-list">
								<ul>					
								 	<%for(PlaceList pl : planPlaceList){ 
								 		if(pl.getPlanNo() == p.getPlanNo()){%>
											<%if(pl.getPlaceListLevel()==1){ %>
												<li class="myPlan-day">
												<div id=plan-day><%=pl.getDay() %>DAY</div><br /><br />
						 					<%}%>
												<ul class="myPlan-day-ul">
													<li>
														<span id="plan-number"><%=pl.getPlaceListLevel()%></span>
														<%=pl.getPlaceName() %>
														<%if(pl.getPlaceComment()!=null){%>
															<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span> : <%=pl.getPlaceComment() %></span>
														<%} %>
													</li>
												</ul>
												</li>
						 				<%} %>
						 			<%} %>
								</ul>
							</div>			
						</li>
	 		<%}%>
		 <%}%>
		<li><br />
			&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/images/plus_btn.png"
				 onclick="location.href='<%=request.getContextPath()%>/plan/planWriteFrm?userId=<%=memberLoggedIn.getMemberId()%>'"/>
		</li>
	</ul>



</div>
	
	
	
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
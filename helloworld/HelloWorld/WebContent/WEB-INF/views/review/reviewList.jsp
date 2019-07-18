<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, review.model.vo.*" %>
<%
	//ReviewListServlet에서 정보가지고 오기
	int cPage = (int)request.getAttribute("cPage");
	String pageBar = (String)request.getAttribute("pageBar");
	
 	List<Review> list = (List<Review>)request.getAttribute("list");
 	List<String> photoList = (List<String>)request.getAttribute("photoList");
	
%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/reviewList.css" />
<script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>
<script src="<%=request.getContextPath()%>/jquery-ui-1.12.1/jquery-ui.js"></script>
<title>리뷰게시판</title>

<script>
$(function(){
	$(".review-content").hide();
});

$(function(){
	$("#btn-write").click(function(){
	 	location.href = "<%=request.getContextPath()%>/review/reviewForm";
	<%-- 	location.href = "<%=request.getContextPath()%>/review/reviewFormTest"; --%>
	});
});

function reviewView(reviewNo){
	console.log("확인");
	console.log(reviewNo);
	var LoggedId = $("input[name=memberId]").val();
	console.log(LoggedId);
	
  	$.ajax({
		url:"<%=request.getContextPath()%>/ajax/reviewView",
		type:"get",
		data:{reviewNo:reviewNo, LoggedId:LoggedId},
		dataType:"html",
		success:function(data){
			console.log(data);
			$(".review-content").css('display', 'inline-block');
			$(".review-content").html(data);
		}
	});
	
};

</script>


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
				<span id="myPlanTotal-title"><a href="<%=request.getContextPath()%>/plan/planList">일정</a></span>
				<span id="myPlanTotal"></span>
			</td>
			<td>
				<span id="myReviewTotal-title">리뷰</span>
				<span id="myReviewTotal"></span>
			</td>
		</tr>
	
	</table>
</div>
<br /><br />

<div id="myReviewPlan-div">
	<input type="hidden" name="memberId" value="<%=memberLoggedIn!=null?memberLoggedIn.getMemberId():"" %>" />
	<section class="List-container">
		<div id="div-top">
		
		<form action="<%=request.getContextPath()%>/review/reviewListWithPlaceCode" id="div-placeCode">
		<select name="searchType" id="searchType">
			<option value="" disabled="disabled" selected="selected">여행테마 선택</option>
			<option value="1">맛집</option>
			<option value="2">쇼핑</option>
	   		<option value="3">휴양</option>
	     	<option value="4">레져</option>
	     	<option value="5">역사</option>
		</select>
		<input type="hidden" name="memberId" value="<%=memberLoggedIn!=null?memberLoggedIn.getMemberId():"" %>" />
		<input type="submit" id="searchType" value="검색" />
		</form>
	
	 	<div class="search-div">
		<input type="text" name="" id="search" placeholder="검색어를 입력해주세요" />
		<input type="submit" id="search-btn" value="검색" />
		</div>
		</div> 
		
		<div class="review-content">
		</div>
	
		<div class="review-content-view">
		<%if(!list.isEmpty()){
			 int i=0;
		     for(Review r : list){%>
		<div class="div<%=i%>">
		<img src="<%=request.getContextPath() %>/upload/review/<%=photoList.get(i)%>" alt="" width="100%" height="100%" onclick="reviewView(<%=r.getReviewNo()%>);"/>
		</div>
	
		<% i++;} 
		 }%>
		</div>
		
		<div id="paging">
			<%=pageBar %>
		</div>
		
	</section>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
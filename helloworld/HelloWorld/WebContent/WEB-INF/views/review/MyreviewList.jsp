<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, review.model.vo.*" %>
<%
	//ReviewListServlet에서 정보가지고 오기
	int cPage = (int)request.getAttribute("cPage");
	String pageBar = (String)request.getAttribute("pageBar");
	
 	List<Review> Reviewlist = (List<Review>)request.getAttribute("Reviewlist");
 	List<String> photoList = (List<String>)request.getAttribute("photoList");
	
%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/MyreviewList.css" />
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
	var LoggedId = $("input[name=memberId]").val();
	console.log(LoggedId);
	
  	$.ajax({
		url:"<%=request.getContextPath()%>/ajax/reviewView",
		type:"get",
		data:{reviewNo:reviewNo, LoggedId:LoggedId},
		dataType:"html",
		success:function(data){
			$(".review-content").toggle();
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
		<div class="review-content-view">
			<%if(!Reviewlist.isEmpty()){
				 int i=0;
			     for(Review r : Reviewlist){%>
					<img src="<%=request.getContextPath() %>/upload/review/<%=photoList.get(i)%>" 
						 width="300px" height="300px" 
						 onclick="reviewView(<%=r.getReviewNo()%>);"/>
					<% i++;
				 }
			  }%>
		</div>
		
		<div class="review-content">
		</div>
		
		<div id="paging">
			<%=pageBar %>
		</div>
	</section>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="review.model.vo.*, java.util.*, place.model.vo.*" %>
<%
	Review rv = (Review)request.getAttribute("rv");
	List<ReviewPhoto> list = (List<ReviewPhoto>)request.getAttribute("list");
	List<ReviewComment> commentList = (List<ReviewComment>)request.getAttribute("commentList");
	Place p = (Place)request.getAttribute("p");
	
	String LoggedId = (String)request.getAttribute("LoggedId");	
	System.out.println("리스트에서 넘어온 로그드아이디:"+LoggedId);
	int num = (Integer)request.getAttribute("num");
%>
<!DOCTYPE html>
<html>
<head>
<script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/reviewList.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/reviewView.css" />
<meta charset="UTF-8">
<title>Insert title here</title>

<script>
$(function(){
	var slideIndex = 0;
	showSlides();

	function showSlides() {
	    var i;
	    var slides = document.getElementsByClassName("mySlides");
	    var dots = document.getElementsByClassName("dot");
	    for (i = 0; i < slides.length; i++) {
	       slides[i].style.display = "none";  
	    }
	    slideIndex++;
	    if (slideIndex > slides.length) {slideIndex = 1}    
	    for (i = 0; i < dots.length; i++) {
	        dots[i].className = dots[i].className.replace(" active", "");
	    }
	    slides[slideIndex-1].style.display = "block";  
	    dots[slideIndex-1].className += " active";
	    setTimeout(showSlides, 2500); // Change image every 2 seconds
	}
});
</script>

<script>
function viewCommentList(reviewNo){
	console.log("확인");
	console.log(reviewNo);
	var LoggedId = $("input[name=LoggedId]").val();
	console.log(LoggedId);
	
	//팝업창
	var url = "<%=request.getContextPath()%>/review/reviewComment";
	var title = "reviewComment";
	var specs = "width=800px, height=500px, left=500px, top=100px";
		
	var popup = open("",title,specs); // 팝업의 최상위 윈도우객체를 리턴
		
	//폼과 팝업 연결
	var frm = document.moveReviewComment;
	frm.target = title; // 팝업타이틀 지정
	frm.reviewNo.value = reviewNo;
	frm.LoggedId.value = LoggedId;
	frm.action = url;
	frm.submit();  
};
</script>

<script>
function deleteReview(){
	if(!confirm("이 게시글을 정말 삭제하시겠습니까?")){
		return;
	}
	$("#reviewDelFrm").submit();
}
</script>

<script>
function addLike(){
	//좋아요 처리
	var ch = $("input[name=like]").prop("checked");
	console.log(ch);
	
	var LoggedId = $("input[name=LoggedId]").val();
	var reviewNo = <%=rv.getReviewNo()%>;
	
	console.log("로그인된아이디:"+LoggedId);
	console.log("좋아요누를글의번호:"+reviewNo);
	
 	if(ch){
 		
 		$("img#checkimg").attr("src", "<%=request.getContextPath()%>/images/full_heart.png");
 		
		$.ajax({
			url:"<%=request.getContextPath()%>/ajax/addReviewLikeCount",
			type:"get",
			data:{reviewNo:reviewNo, LoggedId:LoggedId},
			success:function(data){
				console.log(data[0].LikeCount);
				$("#likeCount").text(+data[0].LikeCount+"likes");
			}
		});
	}
	
	else{
		
		$("img#checkimg").attr("src", "<%=request.getContextPath()%>/images/empty_heart.png");
		
		$.ajax({
			url:"<%=request.getContextPath()%>/ajax/deleteReviewLikeCount",
			type:"get",
			data:{reviewNo:reviewNo, LoggedId:LoggedId},
			success:function(data){
				console.log(data[0].LikeCount);
				$("#likeCount").text(data[0].LikeCount+"likes");
			}
		});
	}; 
}
</script>

</head>

<body>
<form name="moveReviewComment" method="post">
	<input type="hidden" name="reviewNo" value="<%=rv.getReviewNo() %>" />
	<input type="hidden" name="LoggedId" value="<%=LoggedId.equals("")?"":LoggedId %>" />
</form>

<input type="hidden" name="LoggedId" value="<%=LoggedId %>" />

<div class="slideshow-container">
	<%if(!list.isEmpty()){
		 int i = 1;
	     for(ReviewPhoto rp : list){%>
	<div class="mySlides fade">
  		<img src="<%=request.getContextPath() %>/upload/review/<%=rp.getRenamedPhotoName() %>" width="350px" height="350px">
	</div>
	<%} 
	  i++;}%>
</div>
	
	
<div class="review-content-list">
	<table id="review-content-table">
		<tr>
			<td colspan="3" id="review-place">
				<%=p.getPlaceName()%>
				<%for(int i=0; i<rv.getPlaceRating(); i++){ %>
					<img id="star" src="<%=request.getContextPath()%>/images/star.png" width="15px" height="15px">
				<%} %>
			</td>
		</tr>
		<tr>
			<td colspan="2" id="review-title"><%=rv.getReviewTitle() %></td>
			<td id="review-id">
				<%=rv.getMemberId() %> <br />
				<%=rv.getReviewDate() %>
			</td>
		</tr>
		<tr>
			<td colspan="3" ><div id="review-content"><%=rv.getReviewContent() %></div></td>
		</tr>
		<tr>
			<td>
				<%if(num==1){ %>
				<input type="checkbox" name="like" id="like" checked="checked" onchange="addLike();" <%=LoggedId.equals("")?"disabled":"" %> />
				<label for="like"><img src="<%=request.getContextPath()%>/images/full_heart.png" alt="" id="checkimg" width="30px"/></label>
				<%} else{%>
				<input type="checkbox" name="like" id="like" onchange="addLike();" <%=LoggedId.equals("")?"disabled":"" %> /> 
				<label for="like"><img src="<%=request.getContextPath()%>/images/empty_heart.png" alt="" id="checkimg" width="30px"/></label>
				<%} %>
				<br /><span id="likeCount"><%=rv.getLikeCount() %>likes</span> 					
			</td>
			<td colspan="2">
			</td>
		</tr>
	</table>
		

	<div id="comment-container">
		<%if(!LoggedId.equals("")){ %>
		<form action="<%=request.getContextPath()%>/review/reviewCommentInsert" id="reviewCommentFrm" name="reviewCommentFrm" method="post">
			<textarea name="reviewCommentContent" id="reviewCommentContent" placeholder="댓글을 입력하세요"></textarea>
			<button type="submit" id="btn-insert" class="btn">등록</button>
			<input type="hidden" name="reviewRef" value="<%=rv.getReviewNo()%>" />
			<input type="hidden" name="reviewCommentWriter" value="<%=LoggedId %>" />
			<input type="hidden" name="reviewCommentLevel" value="1" />
			<input type="hidden" name="reviewCommentRef" value="0" />
		</form>
		<%} %>
	</div>
	
	<form action="<%=request.getContextPath()%>/review/reviewDelete" method="post" id="reviewDelFrm">
		<input type="hidden" name="reviewNo" value="<%=rv.getReviewNo()%>" />
	</form>
	
	<div id="review-botton">
		<%if(LoggedId.equals(rv.getMemberId())){ %>
			<button onclick="location.href='<%=request.getContextPath()%>/review/reviewUpdate?reviewNo=<%=rv.getReviewNo()%>'">수정하기</button>
			<button onclick="deleteReview();">삭제하기</button>
		<%} %>
		<button onclick="viewCommentList(<%=rv.getReviewNo()%>);" >댓글 보기</button>	
	</div>
</div>
	


</body>
</html>
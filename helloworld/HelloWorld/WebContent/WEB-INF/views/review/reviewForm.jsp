<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/reviewForm.css" />
<title>리뷰 쓰기</title>


<section id="review-container">
	<br /><br />
	<h2>Review</h2>
	<br /><hr /><br />
	
	<form action="<%=request.getContextPath()%>/review/reviewFormEnd" 
		  method="post" 
		  id="reviewForm"
		  enctype="multipart/form-data"
		  name="addMap">
		<table id="tbl-board">
			<tr>
				<th><span class="review-span">카테고리</span></th>
   				<td>
   					 <select class="" 
   					 		 id="reviewCategory-select" 
   					 		 name="placeNo">
     					 <option value="1" selected="selected">맛집</option>
     					 <option value="2">쇼핑</option>
     					 <option value="3">휴양</option>
     					 <option value="4">레져</option>
     					 <option value="5">역사</option>
    				</select>
    			</td>
			</tr>
			
			<tr>
				<th><span class="review-span">도시 선택</span></th>
   				<td>
   					 <select class="" 
   					 		 id="reviewCategory-select" 
   					 		 name="cityCode"
   					 		 required>
     					 <option value="" disabled="disabled" selected="selected">도시를 선택해주세요</option>
     					 <option value="011">오키나와</option>
     					 <option value="012">오사카</option>
     					 <option value="013">삿포로</option>
     					 <option value="014">동경</option>
     					 <option value="015">대마도</option>
     					 <option value="021">북경</option>
     					 <option value="022">상해</option>
     					 <option value="023">장가계</option>
     					 <option value="024">황산</option>
     					 <option value="031">방콕</option>
     					 <option value="032">치앙마이</option>
     					 <option value="033">보라카이</option>
     					 <option value="034">싱가포르</option>
     					 <option value="041">뉴욕</option>
     					 <option value="042">LA</option>
     					 <option value="043">하와이</option>
     					 <option value="044">샌디에고</option>
     					 <option value="051">영국</option>
     					 <option value="052">프랑스</option>
     					 <option value="053">이탈리아</option>
     					 <option value="054">스페인</option>
     					 <option value="055">포르투갈</option>
     					 <option value="061">시드니</option>
     					 <option value="062">멜버른</option>
     					 <option value="063">골드코스트</option>
     					 <option value="064">뉴질랜드</option>
    				</select>
    			</td>
			</tr>
			
			<tr>
				<th><span class="review-span">장소</span></th>
				<td>
	 				<input type="text" name="pname" value="" required/>
	 				&nbsp;
	 				<img src="<%=request.getContextPath()%>/images/addMap.png"
	 					 onclick="SelectaddMap();"
	 					 width="30px" height="30px"
	 					 id="addMap-img"/>
						<input type="hidden" name="ncode" value=""/>
						<input type="hidden" name="inlat" value=""/>
						<input type="hidden" name="inlng" value=""/>
						<input type="hidden" name="placeid" value=""/> 
				</td>
			</tr>
			<tr>
				<th><span class="review-span">제목</span></th>
				<td>
					<input type="text" 
						   name="reviewTitle"
						   required/>
					<input type="hidden" 
						   name="reviewWriter"
						   value="<%=memberLoggedIn.getMemberId()%>"/>
				</td>
			</tr>
			<tr>
				<th>
					<span class="review-span">사진</span>
				</th>
				<td>
					<div id="reviewImg-container">
						<div id="reviewImg-div-1"
							 class="reviewImg-div"
							 onclick="openFileReviewImg(1);">
							<img id="reviewImg-viewer-1"
								 width="100px" height="100px"
								 src="<%=request.getContextPath()%>/images/plus.png"/><div id="mainPhoto">대표사진</div>
						</div><input type="file" 
							   name="reviewImg-1" 
				   			   id="reviewImg-1"
				   			   style="display:none"
				   			   onchange="loadReviewImg(this, 1)"/></div>
				</td>
			</tr>
	
			<tr>
				<th><span class="review-span">내용</span></th>
				<td>
					<textarea name="reviewContent" 
							  id="reviewContent" 
							  cols="30" rows="10" required></textarea>
				</td>
			</tr>
			<tr>
				<th>
					<span class="review-span">평가</span>
				</th>
				<td>
					<span class="star-input">
						<span class="input">
					    	<input type="radio" name="star-input" value="1" id="p1">
					    	<label for="p1">&nbsp;&nbsp;너무 별로였어요<i class="fa fa-frown-o" aria-hidden="true"></i></label>
					    	<input type="radio" name="star-input" value="2" id="p2">
					    	<label for="p2">&nbsp;&nbsp;그냥 그랬어요<i class="fa fa-frown-o" aria-hidden="true"></i></label>
					    	<input type="radio" name="star-input" value="3" id="p3">
					    	<label for="p3">&nbsp;&nbsp;가성비 괜찮았어요<i class="fa fa-meh-o" aria-hidden="true"></i></label>
					    	<input type="radio" name="star-input" value="4" id="p4">
					    	<label for="p4">&nbsp;&nbsp;좋았어요<i class="fa fa-smile-o" aria-hidden="true"></i></label>
					    	<input type="radio" name="star-input" value="5" id="p5" checked>
					    	<label for="p5">&nbsp;&nbsp;정말 좋았어요<i class="fa fa-smile-o" aria-hidden="true"></i></label>
					  	</span>
					  	<output for="star-input"><b></b></output>						
					</span>
				</td>
			</tr>
			<tr>
				<th colspan="2">
					<input type="button" 
						   value="취소"
						   onclick="location.href='<%=request.getContextPath()%>'"/>
					<input type="submit" 
						   value="등록" 
						   onclick="return validate();"/>
				</th>
			</tr>
		</table>
	</form>
</section>

<form name="addMapFrm" method="post">
</form>

<script>
function SelectaddMap(){
	console.log("확인");
	//팝업창
	var url = "<%=request.getContextPath()%>/map/mapLocation";
	var title = "mapLocation";
	var specs = "width=500px, height=500px, left=500px, top=100px";
		
	var popup = open("",title,specs); // 팝업의 최상위 윈도우객체를 리턴
		
	//폼과 팝업 연결
	var frm = document.addMapFrm;
	frm.target = title; // 팝업타이틀 지정
	frm.action = url;
	frm.submit(); 
}

$(function(){
	$("#deleteProfile").click(function(){
		$("#profile-viewer").attr("src", "<%=request.getContextPath()%>/upload/member/profile/nonProfile.png");
		$("#profile").val("");
	});
});


function openFileReviewImg(num){
	var n = num;
	var f = $("#reviewImg-"+n);
	
	if($("#reviewImg-viewer-"+n).prop("src") =="http://localhost:9090/helloworld/images/plus.png"){
		$("#reviewImg-"+n).click();
	}
	else{
		$("#reviewImg-viewer-"+n).attr("src", "<%=request.getContextPath()%>/images/plus.png");
		$("#reviewImg-"+n).val("");
		$("#reviewImg-"+n).click();
	/* 	$("div#reviewImg-div-"+n).remove();
		$("input#reviewImg-"+n).remove(); */
		
	}
	
}
function loadReviewImg(f, num){
	var n = num;
	console.log(f.files);
	console.log(f.files[0]);
	
	if(f.files && f.files[0]){
		var reader = new FileReader();
		reader.readAsDataURL(f.files[0]);
		reader.onload = function(){
			$("#reviewImg-viewer-"+n).attr("src", reader.result);
		}
		
		if($("input#reviewImg-"+n)[0] == $("#reviewImg-container>input:last")[0]){
			addReviewDiv(n);		
		}
	}
	
	
}

function addReviewDiv(num){
	var n = num;
	
	if(n >= 10){
		alert("사진은 열 장까지 첨부 가능합니다.");
		return;
	}
	
	else{
		var html = "<div id='reviewImg-div-"+(n+1)+"'";
		html += "class='reviewImg-div'";
		html += "onclick='openFileReviewImg("+(n+1)+");'>";
		html += "<img id='reviewImg-viewer-"+(n+1)+"'";
		html += "width='100px' height='100px'";
		html += "src='<%=request.getContextPath()%>/images/plus.png'/></div>";
		html += "<input type='file' name='reviewImg-"+(n+1)+"'";
		html += "id='reviewImg-"+(n+1)+"'";
		html += "style='display:none'";
		html += "onchange='loadReviewImg(this, "+(n+1)+");''/>";
		
		$("div#reviewImg-container").append(html);
	}
	
}


 /* 별 평점 주기 */
var starRating = function(){
var $star = $(".star-input"),
    $result = $star.find("output>b");
	
  	$(document)
	.on("focusin", ".star-input>.input", 
		function(){
   		 $(this).addClass("focus");
 	})
		 
	   	.on("focusout", ".star-input>.input", function(){
	    	var $this = $(this);
	    	setTimeout(function(){
	      		if($this.find(":focus").length === 0){
	       			$this.removeClass("focus");
	     	 	}
	   		}, 100);
	 	 })  
		    .on("change", ".star-input :radio", function(){
		    	$result.text($(this).next().text());
		  	})
			    .on("mouseover", ".star-input label", function(){
			    	$result.text($(this).text());
			    })
				    .on("mouseleave", ".star-input>.input", function(){
				    	var $checked = $star.find(":checked");
				    		if($checked.length === 0){
				     	 		$result.text("");
			   		 	} else {
			     	 		$result.text($checked.next().text());
			    		}
  	});
};

starRating();
</script>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
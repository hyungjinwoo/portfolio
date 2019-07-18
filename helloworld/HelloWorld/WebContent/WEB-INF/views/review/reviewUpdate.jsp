<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="review.model.vo.*, java.util.*, place.model.vo.*" %>
<%
	Review rv = (Review)request.getAttribute("rv");
	List<ReviewPhoto> list = (List<ReviewPhoto>)request.getAttribute("list");
	Place p = (Place)request.getAttribute("p");
%>

<%@ include file="/WEB-INF/views/common/header.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/reviewForm.css" />
<title>리뷰 수정</title>
<script src="<%=request.getContextPath() %>/js/jquery-3.4.0.js"></script>
<script src="<%=request.getContextPath()%>/jquery-ui-1.12.1/jquery-ui.js"></script>


<script>
$(function(){
	var placeCode = $("input[name=placeCode]").val();
	console.log("장소코드:"+placeCode);
	
	$("#reviewCategory-select").val(placeCode).attr("selected", "selected");
});

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

</script>


<section id="review-container">
	<br /><br />
	<h2>Review</h2>
	<br /><hr /><br />
	<form name="addMapFrm" method="post">
	</form>
	<form action="<%=request.getContextPath()%>/review/reviewUpdateEnd?reviewNo=<%=rv.getReviewNo()%>" 
		  method="post" 
		  enctype="multipart/form-data"
		  id="reviewForm"
		  name="addMap">
		<table id="tbl-board">
			<tr>
				<th><span class="review-span">카테고리</span></th>
   				<td>
   					 <select class="" 
   					 		 id="reviewCategory-select" 
   					 		 name="placeNo" required>
     					 <option value="1">맛집</option>
     					 <option value="2">쇼핑</option>
     					 <option value="3">휴양</option>
     					 <option value="4">레져</option>
     					 <option value="5">역사</option>
    				</select>
    				<input type="hidden" name="placeCode" value="<%=p.getPlaceCode() %>" />
    			</td>
			</tr>
			
			<tr>
				<th><span class="review-span">도시 선택</span></th>
   				<td>
   					 <select class="" 
   					 		 id="reviewCategory-select" 
   					 		 name="cityCode" required>
     					 <option value="" disabled="disabled" selected="selected">도시를 선택해주세요</option>
     					 <option value="011" <%=p.getCityCode().equals("011")?"selected":"" %>>오키나와</option>
     					 <option value="012" <%=p.getCityCode().equals("012")?"selected":"" %>>오사카</option>
     					 <option value="013" <%=p.getCityCode().equals("013")?"selected":"" %>>삿포로</option>
     					 <option value="014" <%=p.getCityCode().equals("014")?"selected":"" %>>동경</option>
     					 <option value="015" <%=p.getCityCode().equals("015")?"selected":"" %>>대마도</option>
     					 <option value="021" <%=p.getCityCode().equals("021")?"selected":"" %>>북경</option>
     					 <option value="022" <%=p.getCityCode().equals("022")?"selected":"" %>>상해</option>
     					 <option value="023" <%=p.getCityCode().equals("023")?"selected":"" %>>장가계</option>
     					 <option value="024" <%=p.getCityCode().equals("024")?"selected":"" %>>황산</option>
     					 <option value="031" <%=p.getCityCode().equals("031")?"selected":"" %>>방콕</option>
     					 <option value="032" <%=p.getCityCode().equals("032")?"selected":"" %>>치앙마이</option>
     					 <option value="033" <%=p.getCityCode().equals("033")?"selected":"" %>>보라카이</option>
     					 <option value="034" <%=p.getCityCode().equals("034")?"selected":"" %>>싱가포르</option>
     					 <option value="041" <%=p.getCityCode().equals("041")?"selected":"" %>>뉴욕</option>
     					 <option value="042" <%=p.getCityCode().equals("042")?"selected":"" %>>LA</option>
     					 <option value="043" <%=p.getCityCode().equals("043")?"selected":"" %>>하와이</option>
     					 <option value="044" <%=p.getCityCode().equals("044")?"selected":"" %>>샌디에고</option>
     					 <option value="051" <%=p.getCityCode().equals("051")?"selected":"" %>>영국</option>
     					 <option value="052" <%=p.getCityCode().equals("052")?"selected":"" %>>프랑스</option>
     					 <option value="053" <%=p.getCityCode().equals("053")?"selected":"" %>>이탈리아</option>
     					 <option value="054" <%=p.getCityCode().equals("054")?"selected":"" %>>스페인</option>
     					 <option value="055" <%=p.getCityCode().equals("055")?"selected":"" %>>포르투갈</option>
     					 <option value="061" <%=p.getCityCode().equals("061")?"selected":"" %>>시드니</option>
     					 <option value="062" <%=p.getCityCode().equals("062")?"selected":"" %>>멜버른</option>
     					 <option value="063" <%=p.getCityCode().equals("063")?"selected":"" %>>골드코스트</option>
     					 <option value="064" <%=p.getCityCode().equals("064")?"selected":"" %>>뉴질랜드</option>
    				</select>
    			</td>
			</tr>
			
			<tr>
				<th><span class="review-span">장소</span></th>
				<td>
 					<input type="text" name="pname" value="<%=p.getPlaceName()%>" readonly="readonly"/>
					<%-- <img src="<%=request.getContextPath()%>/images/addMap.png"
 					 onclick="SelectaddMap();"
 					 width="30px" height="30px"
 					 id="addMap-img"/> --%>
					<input type="hidden" name="inlat" value="<%=p.getPlaceX()%>"/>
					<input type="hidden" name="inlng" value="<%=p.getPlaceY()%>"/>
					<input type="hidden" name="placeid" value="<%=p.getPlaceId()%>"/> 
				</td>
			</tr>
			
			<tr>
				<th><span class="review-span">제목</span></th>
				<td>
					<input type="text" 
						   name="reviewTitle" value="<%=rv.getReviewTitle()%>" required/>
					<input type="hidden" 
						   name="reviewWriter"
						   value="<%=rv.getMemberId()%>"/>
				</td>
			</tr>
			
<%-- 			<tr>
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
								 src="<%=request.getContextPath()%>/images/plus.png"/>
						</div><input type="file" 
							   name="reviewImg-1" 
				   			   id="reviewImg-1"
				   			   style="display:none"
				   			   onchange="loadReviewImg(this, 1)"/></div>
				</td>
			</tr> --%>
			
 			<tr>
				<th>
					<span class="review-span">사진</span>
				</th>
				<td>
				<div id="reviewImg-container">
					<%if(!list.isEmpty()){
						int i = 1;
					    for(ReviewPhoto rp : list){%>
						<div id="reviewImg-div-<%=i %>"
							 class="reviewImg-div"
							 onclick="openFileReviewImg(<%=i %>);">
							<img id="reviewImg-viewer-<%=i %>"
								 width="100px" height="100px"
								 src="<%=request.getContextPath()%>/upload/review/<%=rp.getRenamedPhotoName()%>"/>
							<input type="hidden" name="original-img-<%=i %>" value="<%=rp.getOriginalPhotoName() %>" />
							<input type="hidden" name="original-renamedimg-<%=i %>" value="<%=rp.getRenamedPhotoName() %>" />
						</div><input type="file" 
							   name="reviewImg-<%=i %>" 
				   			   id="reviewImg-<%=i %>"
				   			   style="display:none"
				   			   onchange="loadReviewImg(this, <%=i %>)"/>
				   	  <%i++;} 
				   	    }%>
				   	  <%for(int i=0; i<1; i++){ 
				   	      int k = list.size();%>
				   	  <div id="reviewImg-div-<%=k+1 %>"
							 class="reviewImg-div"
							 onclick="openFileReviewImg(<%=k+1 %>);">
							<img id="reviewImg-viewer-<%=k+1 %>"
								 width="100px" height="100px"
								 src="<%=request.getContextPath()%>/images/plus.png"/>
						</div><input type="file" 
							   name="reviewImg-<%=k+1 %>" 
				   			   id="reviewImg-<%=k+1 %>"
				   			   style="display:none"
				   			   onchange="loadReviewImg(this, <%=k+1 %>)"/>
				   	  <%} %>
				   	  </div>
				</td>
			</tr> 
	
			<tr>
				<th><span class="review-span">내용</span></th>
				<td>
					<textarea name="reviewContent" 
							  id="reviewContent" 
							  cols="30" rows="10" required><%=rv.getReviewContent() %></textarea>
				</td>
			</tr>
			<tr>
				<th>
					<span class="review-span">평가</span>
				</th>
				<td>
					<span class="star-input">
						<span class="input">
					    	<input type="radio" name="star-input" value="1" id="p1" <%=p.getPlaceRating()==1?"checked":"" %>>
					    	<label for="p1">&nbsp;&nbsp;너무 별로였어요<i class="fa fa-frown-o" aria-hidden="true"></i></label>
					    	<input type="radio" name="star-input" value="2" id="p2" <%=p.getPlaceRating()==2?"checked":"" %>>
					    	<label for="p2">&nbsp;&nbsp;그냥 그랬어요<i class="fa fa-frown-o" aria-hidden="true"></i></label>
					    	<input type="radio" name="star-input" value="3" id="p3" <%=p.getPlaceRating()==3?"checked":"" %>>
					    	<label for="p3">&nbsp;&nbsp;가성비 괜찮았어요<i class="fa fa-meh-o" aria-hidden="true"></i></label>
					    	<input type="radio" name="star-input" value="4" id="p4" <%=p.getPlaceRating()==4?"checked":"" %>>
					    	<label for="p4">&nbsp;&nbsp;좋았어요<i class="fa fa-smile-o" aria-hidden="true"></i></label>
					    	<input type="radio" name="star-input" value="5" id="p5" <%=p.getPlaceRating()==5?"checked":"" %>>
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
						   value="수정" 
						   onclick="return validate();"/>
				</th>
			</tr>
		</table>
	</form>
</section>

<script>
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
	console.log("로드리뷰이미지에서의 숫자:"+n);
	console.log(f.files);
	console.log(f.files[0]);
	
	if(f.files && f.files[0]){
		var reader = new FileReader();
		reader.readAsDataURL(f.files[0]);
		reader.onload = function(){
			$("#reviewImg-viewer-"+n).attr("src", reader.result);
			$("input[name=original-img"+num+"]").remove();
			$("input[name=original-renamedimg"+num+"]").remove();
		}
		

		if($("input#reviewImg-"+n)[0] == $("#reviewImg-container>input:last")[0]){
			console.log("파일추가버튼생성 확인");
			addReviewDiv(n);		
		}
	}
	
	
}

function addReviewDiv(num){
	console.log("파일추가 확인");
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
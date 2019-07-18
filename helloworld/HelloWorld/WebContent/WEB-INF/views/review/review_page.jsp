<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/review_page.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/common.css" />

<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@
 	page import = "java.util.*, 
 					mainPage.model.vo.*, 
 					org.json.simple.JSONArray,             
					org.json.simple.JSONObject,            
					org.json.simple.parser.JSONParser,     
					org.json.simple.parser.ParseException,
					review.model.vo.*"
%>    
<%  
    String nationName = (String)request.getAttribute("nationName");
    String cityName = (String)request.getAttribute("cityName");
    String c_name = (String)request.getAttribute("c_name");
    String wea_name=(String)request.getAttribute("wea_name");
    String crc_code = (String)request.getAttribute("crc_code");
  	
    //ReviewListServlet에서 정보가지고 오기
  	int cPage = (int)request.getAttribute("cPage");
  	String pageBar = (String)request.getAttribute("pageBar");
  	String cityCode = (String)request.getAttribute("cityCode");
  	
   	List<Review> list = (List<Review>)request.getAttribute("Reviewlist");
   	List<String> photoList = (List<String>)request.getAttribute("photoList");
    
%>
    <script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>
    <script src="<%=request.getContextPath()%>/js/app.js"></script>
    <script src="<%=request.getContextPath()%>/js/autocomplete.js"></script>
    <script src="https://use.fontawesome.com/5a964364f0.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 <title>리뷰보기</title>
<script>
$(function(){
	$(".review-content").hide();
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
			console.log(data);
			$(".review-content").toggle();
			$(".review-content").html(data);
		}
	});
	
};

</script>

<div id="reviewPageDiv">

	<div id="reviewPageTitleDiv">
<!--날씨  -->
		<div class="big_weather">
			<div class="weather" id="area1"></div> 
			<div id="cityName"><%=cityName%></div>
			 
		</div>
			
<!--환율 박스  -->
		<div id="m_box">
		<span class="exchange" id="money">
			<br>
			오늘의 환율 ▶
		 </span>  
		 	<div id="calculator">
				 <br/>환율계산기(<%=crc_code%>)
				 <br /><br />
				 <input type="text" 
				 	   id="amount" 
				 	   placeholder="<%=c_name%>"/>
				 <span id="result"></span>
		 	</div>
		</div>
	</div> <!-- end of reviewPageTitleDiv -->

<!-- 리뷰보여주는곳 -->
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
	    <input type="hidden" name="cityCode" value="<%=cityCode %>" />	
		<input type="hidden" name="memberId" value="<%=memberLoggedIn!=null?memberLoggedIn.getMemberId():"" %>" />
		<input type="hidden" name="cityName" value="<%=cityName%>" />
		<input type="hidden" name="nationName" value="<%=nationName%>" />
		
		<input type="submit" id="searchType" value="검색" />
		</form>
	
	 	<!-- <div class="search-div">
		<input type="text" name="" id="search" placeholder="검색어를 입력해주세요" />
		<input type="submit" id="search-btn" value="검색" />
		</div> -->
		</div> 
		
	
		<div class="review-content-view">
			<%if(!list.isEmpty()){
				 int i=0;
			     for(Review r : list){%>
					<img src="<%=request.getContextPath() %>/upload/review/<%=photoList.get(i)%>" alt=""width="300px" height="300px"" onclick="reviewView(<%=r.getReviewNo()%>);"/>
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
</div> <!-- end of reviewPageDiv -->
	 
	 
	 
	 
<script>
//날씨 API
console.log("weaname"+"<%=wea_name%>");
console.log("cityName"+"<%=cityName%>");
console.log("cName~!@!  "+"<%=c_name%>");
 $("#area1").weather({ city:"<%=wea_name%>", 
						tempUnit: 'C', 
						lang: 'kr',
						autocompleteMinLength:3,
						displayDescription:true,
						displayHumidity:true,
						displayMinMaxTemp:false,
						displayWind:false,
						displayDateTime:true,
						fixLocation:false,
						url:'http://api.openweathermap.org/data/2.5/forecast?appid=57c67d043c19a3d56c06032f6c370987&units=metric'});

 //환율 API
  $(function(){
	 var currency = '<%=crc_code%>'; 
	 console.log("currency: "+currency);
	  $.ajax({
		 	  url:"https://free.currconv.com/api/v7/convert?q="+currency+"_KRW&compact=ultra&apiKey=24c7907d507fdd1315c7",	  
			 type:"get", 
			 dataType:"text",
			 success:function(data){
		              var body = data;
		              console.dir(data)
		              var obj= JSON.parse(body);
		              console.dir("obj는"+obj.<%=crc_code%>_KRW);
		              var obj1 = "obj."+currency+"_KRW";
				 		console.dir(obj1);
						$("span#money").append("<%=c_name%>:").append(Math.round(obj.<%=crc_code%>_KRW)).append(" 원");
						
						$("#amount").keyup(function(key){
							 var amount = $("#amount").val();
							 console.log("amount: "+amount);
							if(key.keyCode==13){		
							$("#result").html("대한민국 KRW 총: ").append(Math.round((obj.<%=crc_code%>_KRW)*amount)).append(" 원");
							};
						})
						
				 }, 
				 error:function(jqxhr, textStatus, errorThrown){
						console.log(jqxhr);
						console.log(textStatus);
						console.log(errorThrown);	
					}
	  	}); 
	  }); 
</script>

 <%@ include file="/WEB-INF/views/common/footer.jsp" %>
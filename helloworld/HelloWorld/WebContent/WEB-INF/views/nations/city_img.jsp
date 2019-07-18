<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/city_img.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/common.css" />

<%@ include file="/WEB-INF/views/common/header.jsp" %>
 <%@
 	page import = "java.util.*, 
 					mainPage.model.vo.*, 
 					org.json.simple.JSONArray,             
					org.json.simple.JSONObject,            
					org.json.simple.parser.JSONParser,     
					org.json.simple.parser.ParseException"%>    
<%  
	int totalPage = (int)request.getAttribute("totalPage"); 
    String nationName = (String)request.getAttribute("nationName");
%>
    <script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="https://use.fontawesome.com/5a964364f0.js"></script>

	
	<span id="Nname"><%= nationName %></span>
	
	<nav>
		<div id="city-index">
		<a href="<%=request.getContextPath()%>/nationToCity?nid=일본">일본</a>&nbsp;&nbsp;
		<a href="<%=request.getContextPath()%>/nationToCity?nid=중국">중국</a>&nbsp;&nbsp;
		<a href="<%=request.getContextPath()%>/nationToCity?nid=동남아">동남아</a>&nbsp;&nbsp;
		<a href="<%=request.getContextPath()%>/nationToCity?nid=유럽">유럽</a>&nbsp;&nbsp;
		<a href="<%=request.getContextPath()%>/nationToCity?nid=미국">미국</a>&nbsp;&nbsp;
		<a href="<%=request.getContextPath()%>/nationToCity?nid=호주/뉴질랜드">호주/뉴질랜드</a>
		</div>
	</nav>

	<div id="btn-more-container">
		<i class="fa fa-angle-double-left fa-4x" id="left"></i>	
			 <div id="container_pickCity"> </div> 
		<i class="fa fa-angle-double-right fa-4x" id="right"></i>
	</div>
			
<script>
$(function(){
	//1page 값을 요청함. page 로딩시 첫번쨰 페이지 자동 호출
	getMore(1);
	//오른쪽 화살표 클릭시 더 보여준다. 
	$("i#right").click(function(){
		console.log("오른쪽 클릭");
		console.log(Number($(this).val())+1);
		getMore(Number($(this).val())+1);
		});				
	});				
	
function getMore(cPage){
		
	var param = {
			nid: "<%=nationName%>",
			cPage: cPage
	}
	
	$.ajax({
		url: "<%=request.getContextPath()%>/moreCity", 
		data: param, 
		type: "post", 
		dataType: "json",
		success: function(data){
			console.log(data);
			var html = "";
			//객체는 속성명 i에는 index값
			for(var i in data){
				
				var p = data[i];
				html += "<div class='city-container'>";
				html += "<img src='<%=request.getContextPath()%>"+p.city_image+"'/> ";
				/*클릭하면 다음 페이지 넘어가는 용  */
				  html += "<div class='middle'>";  
			    	html += "<a href='<%=request.getContextPath()%>/Review/detail?cityName="+p.city_name+"&nationName=<%=nationName%>' id='cityName-a'>"+p.city_name+"</a>"; 
				 html += "</div>"; 
				html += "</div>";
			}//end of for statement
			$("#container_pickCity").html(html);
			//현재 페이지 cPage값을 btn-more 의 value 속성에 저장
			$("i#right").val(cPage);
			$("i#left").val(cPage);
			
			// 현재 요청 페이지가 마지막 페이지라면, 더보기 버튼을 비 활성화 
			if(cPage == <%=totalPage%>){
				$("i#right").css("display","none");
			}
			else{
				$("i#right").css("display","inline-block");				
			}
			if(cPage == 1){
				$("i#left").css("display","none");				
			}
			else{
				$("i#left").css("display","inline-block");								
			}
		},
		error: function(jqxhr, textStatus, errorThrown){
			console.log(jqxhr);
			console.log(textStatus);
			console.log(errorThrown);	
		}
	});
}
//왼쪽클릭 
  $(function(){
	  $("i#left").click(function(){
		console.log("왼쪽 클릭~!!")
		console.log(Number($(this).val()));
		getMore(Number($(this).val())-1);	 
		  
	  })
  })


</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
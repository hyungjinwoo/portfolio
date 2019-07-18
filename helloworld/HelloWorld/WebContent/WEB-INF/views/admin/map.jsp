<%@page import="java.util.List"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
  <%
    JSONArray array = (JSONArray)request.getAttribute("array");
  %>
  
<!DOCTYPE html>
<html>  
<head>    
<title>위도 경도 찾기</title>
<style>
@import url('https://fonts.googleapis.com/css?family=Montserrat|Oxygen|Poiret+One|Nanum+Gothic|Song+Myung&display=swap');

#container {
    display : flex; 
    flex-direction : column;
    height: 100%;
}
           
#mapCon {
    flex : 1; 
}
#searchResult{
    padding : 5%;
    flex : 1; 
}
#searchResult table{
	margin: 0 auto;
    padding : 2%;
    width:90%;
}

#searchResult table th{
   padding: 10px;
}

#searchResult table tr#trstart{
	font-size: 20px;
}

#searchResult table td{
    text-align:center;
    padding: 10px;
}


#searchResult table #endtd{
    border-bottom:1px solid;
}
    
#mapTable{
    display:none;
}
#admin-review-del-btn{
	background: lightblue;
	border: none;
	cursor: pointer;
	font-family: "Nanum Gothic";
    font-weight: bold;
	
}
</style>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB2BbISLnHXNWyIA3jjgUEg8q-R-71NlzY&callback=initMap&libraries=places"></script>
<script>
    
   var locations = <%=array%>;
     
     function initMap() {
        
        var map = new google.maps.Map(document.getElementById('mapCon'), {
                                          zoom: 1,
                                          center: {lat: 37.4983454, lng: 127.02810099999999},
                                          mapTypeId: 'hybrid'
                                        }
                                    );
       var markers = locations.map(function(location, i) {
          return new google.maps.Marker({
            position: location,
            animation: google.maps.Animation.DROP
          });
        });
        
        $.each(markers, function(index,item){
                markers[index].addListener('dblclick', function() {
                markers[index].setMap(null);  //마우스 right 클릭시에 마커제거
            });  
            
            markers[index].addListener('click', function() {
                map.setCenter(markers[index].getPosition()); //마커을 기준으로 가운데로 지도을 옮김
                markers[index].setAnimation(google.maps.Animation.BOUNCE);
                map.setZoom(20);
            });
            
        });
        // Add a marker clusterer to manage the markers.
        var markerCluster = new MarkerClusterer(map, markers,
            {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
        map.addListener('click', function(results) {
            
            console.log(results);
                if(results.placeId==null){
                    alert("장소을 선택하세요");
                }
                
            var request = {
                      placeId: results.placeId
                    };
                    service = new google.maps.places.PlacesService(map);
                    service.getDetails(request, placeInfo);
                    function placeInfo(place, status) {
                          console.log(place); 
                          /* console.log(place); */
                          
                    $.each(place.address_components, function(index, item){
                        if(place.address_components[index].types[0]=='country'){
                            $("#ncode").val(place.address_components[index].short_name);
                        }
                    });
                          
                        $("#photo").empty();
                        var a=null;
                        $.each(place.photos, function(index, item){
                                a+="<img src="+place.photos[index].getUrl()+"/>";
                            });
                        
                        /* $("#photo").html(a); */
                        $("#inlat").html(place.geometry.location.lat());
                        $("#inlng").html(place.geometry.location.lng());
                        $("#placeid").val(place.place_id);                        
                        $('#pname').val(place.name);
                        $('#address').val(place.formatted_address);
                        $('#phone').val(place.international_phone_number);
                        $("#website").val(place.website);
                        
                        var placeId=$("#placeid").val();
                        $.ajax({
                            url:"<%=request.getContextPath()%>/admin/mapSearchReview",
                            data: "placeId="+placeId,
                            dataType:"json",
                            success:function(data){
                                
                                var table = $("<table><tr id='trstart' ><th>리뷰번호</th><th>아이디</th><th>제목</th><th>점수</th><th>좋아요 수</th><th>조회수</th><th>등록일</th></tr></table>");
                                for(var i in data){
                                    var review = data[i];
                                    console.log(review);
                                    var html="<tr trreviewNo='"+review.reviewNo+"'><td>"+review.reviewNo+"</td>";
                                    html+="<td class='reviewCon'>"+review.memberId+"</td>";
                                    //html+="<td>"+review.placeId+"</td>";
                                    html+="<td class='reviewCon'>"+review.reviewTitle+"</td>";
                                    html+="<td class='reviewCon'>"+review.placeRating+"</td>";
                                    html+="<td class='reviewCon'>"+review.likeCount+"</td>";
                                    html+="<td class='reviewCon'>"+review.readCount+"</td>";
                                    html+="<td class='reviewCon'>"+review.reviewDate+"</td>";
                                    html+="<td class='reviewCon'><button id='admin-review-del-btn' reviewNo='"+review.reviewNo+"' onclick='reviewDel(this)'>삭제</button></td></tr>";
                                    html+="<tr id='endtr'><td colspan='7' id='endtd'>"+review.reviewContent+"</td></tr>";
                                    console.log(html);
                                    table.append(html);
                                }
                                $("#searchResult").html(table); 
                            },
                            error: function(jqxhr,textStatus, errorThrown){
                                console.log("ajax처리 실패!");
                                console.log(jqxhr);
                                console.log(textStatus);
                                console.log(errorThrown);
                            }
                        });
                    }
        });
     }// end initMap()
     
     function reviewDel(e){
      
      var conf = confirm("정말로 삭제할까요?.");
      if(conf){
          var reveiwNo=$(e).attr("reviewno");
          $.ajax({
                url:"<%=request.getContextPath()%>/admin/mapReviewDel",
                data: "reveiwNo="+reveiwNo,
                dataType:"html",
                success:function(data){
                    alert("리뷰 삭제 "+data+"!");
                    if(data==="성공"){
                        $("[trreviewNo="+reveiwNo+"]").next().remove();
                        $("[trreviewNo="+reveiwNo+"]").remove();
                    }
                },
                error: function(jqxhr,textStatus, errorThrown){
                    console.log("ajax처리 실패!");
                    console.log(jqxhr);
                    console.log(textStatus);
                    console.log(errorThrown);
                }
            });
      }
     }
     
   </script>
   </head>
   
   <body>
   
   <div id="container">
    <!-- 지도 -->
    <div id="mapCon"></div>
    
    <!-- 선택한 영역에 대한 리뷰 리스트 -->
    <div id="searchResult"></div>
    
        <!-- hidden 영역 -->
        <table border="1" id="mapTable">
        <tr>
            <td colspan="2"></td>
        </tr>
        <tr>
            <th width="100">위도</th>
            <td id="inlat"></td>
        </tr>
        <tr>
            <th>경도</th>
            <td id="inlng"></td>
        </tr>
        
        <tr>  
            <th>국가코드</th>
            <td><input type="text" id="ncode" value="" size="50"/></td>
        </tr>
        <tr>  
            <th>placeId</th>
            <td><input type="text" id="placeid" value="" size="50"/></td>
        </tr>
        <tr>  
            <th>장소이름</th>
            <td><input type="text" id="pname" value="" size="50"/></td>
        </tr>
        <tr>  
            <th>주소</th>
            <td><input type="text" id="address" value="" size="50"/></td>
        </tr>
        <tr>  
            <th>phone</th>
            <td><input type="text" id="phone" value="" size="50"/></td>
        </tr>
        <tr>  
            <th>website</th>
            <td><input type="text" id="website" value="" size="50"/></td>
        </tr>
    </table>
</div>
    
  </body>
</html>
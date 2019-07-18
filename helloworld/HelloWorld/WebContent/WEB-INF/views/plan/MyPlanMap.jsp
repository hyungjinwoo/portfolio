<%@page import="java.util.List"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
  <%
/*      float lat=(Float)request.getAttribute("lat");
    float lng=(Float)request.getAttribute("lng"); */
    
    JSONArray array = (JSONArray)request.getAttribute("jsonArray");
    //List<String> list = (List<String>)request.getAttribute("jsonCommentArray");
    JSONArray list = (JSONArray)request.getAttribute("jsonCommentArray");
  %>
  
<!DOCTYPE html>
<html>  
<head>    
<title>위도 경도 찾기</title>
   <style>
      #map {
        height: 100%;
      }
      
      /* Optional: Makes the sample page fill the window. */
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
    </style>
    

    <script>
    
    $(function(){
    	initMap();
    });
    
    var locations = <%=array%>;
    var lists = <%=list%>;
    console.log(locations);
    console.log(lists);
    
    var map;
    var geocoder;
    var address;
    
    function initMap() {
            map = new google.maps.Map(document.getElementById('map'), {
                                              zoom: 1,
                                              center: locations[0],
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
                    markers[index].setMap(null);  //마우스 더블 클릭시에 마커제거
                });
                
                markers[index].addListener('click', function() {
                    markers[index].setAnimation(google.maps.Animation.BOUNCE);
                    map.setCenter(markers[index].getPosition()); //마커을 기준으로 가운데로 지도을 옮김
                    map.setZoom(20);
                    
                    if(lists[index].comment!=""){
	                    new google.maps.InfoWindow({
	                         content: lists[index].comment
	                       }).open(map, markers[index]);
                    } 
                    
                });
            });
            var markerCluster = new MarkerClusterer(map, markers,
                {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
      }// end initMap()
      
    </script>
    
    </head>
    
    <body>
    <div id="map"></div>
    
    
  </body>
</html>
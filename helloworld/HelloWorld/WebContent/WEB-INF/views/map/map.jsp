<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
  <%
/*      float lat=(Float)request.getAttribute("lat");
    float lng=(Float)request.getAttribute("lng"); */
    float lat=37.49794199999999f;
    float lng=127.02762099999995f;
  %>
  
<!DOCTYPE html>
<html>  
<head>    
<title>위도 경도 찾기</title>

<script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>
<!-- https://developers.google.com/maps/get-started/?hl=ko, 구글에서 api key 발급 -->
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB2BbISLnHXNWyIA3jjgUEg8q-R-71NlzY&callback=initMap&libraries=places" async defer></script>
<style>
@import url('https://fonts.googleapis.com/css?family=Montserrat|Oxygen|Poiret+One|Nanum+Gothic|Song+Myung&display=swap');

form { margin:0px; }

#map_canvas {
  height: 92%;
  width : 100%;
}
/* Optional: Makes the sample page fill the window. */
html, body {
  height: 100%;
  margin: 0;
  padding: 0;
  text-align: center;
}
#description {
  font-family: Roboto;
  font-size: 25px;
  font-weight: 300;
}
#infowindow-content .title {
  font-weight: bold;
}
#infowindow-content {
  display: none;
}
#map_canvas #infowindow-content {
  display: inline;
}
.pac-card {
  margin: 10px 10px 0 0;
  border-radius: 2px 0 0 2px;
  box-sizing: border-box;
  -moz-box-sizing: border-box;
  outline: none;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
  background-color: #fff;
  font-family: Roboto;
}
#pac-container {
  padding-bottom: 12px;
  margin-right: 12px;
}
.pac-controls {
  display: inline-block;
  padding: 5px 11px;
}
.pac-controls label {
  font-family: Roboto;
  font-size: 13px;
  font-weight: 300;
}
#searchVal {
  background-color: rgba(255, 255, 255,0.7);
  font-family:"Nanum Gothic", Roboto;
  font-size: 20px;
  font-weight: 300;
  padding: 0 11px 0 13px;
  text-overflow: ellipsis;
  width: 250px;
  margin-top:5px;
  border-radius: 10px;
}
#searchVal:focus {
  border-color: #4d90fe;
}
#setLocationBtn{
	border: none;
	border-radius: 50px;
	background: lightblue;
	width: 200px;
	height: 30px;
	font-family:"Nanum Gothic", Roboto;
	font-weight: bold;
	font-size: 18px;
	margin-top: 5px;
}
</style>

<script type="text/javascript">
    
    var map;
    var geocoder;
    var address;
    
//window.onload=load;
//window.onunload=GUnload;
//function load()
//  function initialize()
    function initMap() {
    
    //api 버전 확인
         console.log('Google Maps API version:' + google.maps.version);
      
     //위도,경도
          var latlng=new google.maps.LatLng(<%=lat%>,<%=lng%>);
     
          var mapOptions = {
              zoom: 18,     //확대 정도
              center: latlng,  //지도 이미지 가운데지점
              heading: 0,   //지도 이미지 회전 (default:0)
              disableDefaultUI: false, //기본 UI 비활성화
                  zoomControl: true,   //UI:화면 확대/축소
                  mapTypeControl: true,//UI:지도유형
                  scaleControl: true,   //UI: 축척요소표시 (기본으로 셋팅 되지 않음)
                  streetViewControl: true,  //UI: 길거리 뷰
                  rotateControl: true,     // UI: 화면 회전
                  fullscreenControl: true,  // UI:전체 화면
              gestureHandling: 'greedy',  //none:마우스 휠이 브라우저 스크롤만 따라감, 
                                               // cooperative: ctrl+마우스휠 - 지도 확대/축소
                                               // greedy :(마우스 맵 hover) 마우스휠 - 지도 확대/축소
              mapTypeId: 'hybrid'   //roadmap : 기본 도로지도보기를 표시 (default)
                                       //satellite : Google 어스 위성 이미지를 표시
                                       //hybrid : 정상보기와 위성보기가 혼합되어 표시
                                       //terrain : 지형 정보를 기반으로 물리적 인지도를 표시
            };
      
    //map
        map = new google.maps.Map(document.getElementById('map_canvas'),mapOptions); 
        // map객체 동적 반영
            //map.setTilt(45);  //satellite및 hybrid지도 유형은 가능한 경우 높은 줌 레벨에서 45 ° 이미지를 지원
                                // 0: 비활성화
            //map.setMapTypeId('terrain'); //mapTypeId동적 수정
            //map.setCenter(latlng, 10);
            //map.setHeading(heading + 90); //지도 이미지 회전 +90도
            
            //대중교통 레이어(특정 도시만)
            /*var transitLayer = new google.maps.TransitLayer();
                transitLayer.setMap(map); */
                
        map.addListener('click', function(results) {
            
                if(results.placeId==null){
                    alert("장소을 다시 선택하세요");
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
                    }
            }); // map.listner - end
            
    //마커 
/*     var marker = new google.maps.Marker({
                    position: latlng,
                    map: map,
                    title: 'Click to zoom',
                   draggable: true  //마커을 다른지역으로 이동가능
                  }); */
        
        //맵의 중 위치 변경시에 3초뒤 마커로 위치 재이동
         /*map.addListener('center_changed', function() {
                window.setTimeout(function() {
                  map.panTo(marker.getPosition());
                }, 3000);
              }); */
           
        //마커 더블클릭시 이벤트 발생
/*         marker.addListener('dblclick', function() {
                map.setZoom(20);
                map.setCenter(marker.getPosition());
                marker.setMap(null);  //마커 삭제
            });
 */           
        //마커 더블클릭시 바운스이벤트 발생  
/*         marker.addListener('click', toggleBounce);
            function toggleBounce() {
           //마커의 위도경도 확인
            console.log(marker.getPosition().lat(),marker.getPosition().lng()); 
              if (marker.getAnimation() !== null) {
                marker.setAnimation(null);
              } else {
                marker.setAnimation(google.maps.Animation.BOUNCE);
              }
            } */
            
    //자동완성
        var card = document.getElementById('pac-card');
        var input = document.getElementById('searchVal');
        
        map.controls[google.maps.ControlPosition.TOP_RIGHT].push(card);
        var autocomplete = new google.maps.places.Autocomplete(input);
        // Bind the map's bounds (viewport) property to the autocomplete object,
        // so that the autocomplete requests use the current map bounds for the
        // bounds option in the request.
        autocomplete.bindTo('bounds', map);
        // Set the data fields to return when the user selects a place.
        autocomplete.setFields(
            ['address_components', 'geometry', 'icon', 'name']);
        var infowindow = new google.maps.InfoWindow();
        var infowindowContent = document.getElementById('infowindow-content');
        infowindow.setContent(infowindowContent);
        var marker = new google.maps.Marker({
          map: map,
          anchorPoint: new google.maps.Point(0, -29)
        });
        
        marker.addListener('click', function() {
            map.setZoom(20);
            map.setCenter(marker.getPosition());
            marker.setMap(null);
        },1000);
        autocomplete.addListener('place_changed', function() {
          infowindow.close();
          marker.setMap(null);
          var place = autocomplete.getPlace();
          if (!place.geometry) {
            // User entered the name of a Place that was not suggested and
            // pressed the Enter key, or the Place Details request failed.
            window.alert("No details available for input: '" + place.name + "'");
            return;
          }
          // If the place has a geometry, then present it on a map.
          if (place.geometry.viewport) {
            map.fitBounds(place.geometry.viewport);
          } else {
            map.setCenter(place.geometry.location);
            map.setZoom(17);  // Why 17? Because it looks good.
          }
          marker.setPosition(place.geometry.location);
          marker.setMap(map);
          var address = '';
          if (place.address_components) {
            address = [
              (place.address_components[0] && place.address_components[0].short_name || ''),
              (place.address_components[1] && place.address_components[1].short_name || ''),
              (place.address_components[2] && place.address_components[2].short_name || '')
            ].join(' ');
          }
          infowindowContent.children['place-icon'].src = place.icon;
          infowindowContent.children['place-name'].textContent = place.name;
          infowindowContent.children['place-address'].textContent = address;
          infowindow.open(map, marker);
        });
}
    //위치 검색
    function search(){
        var request = {
            query: $("#searchVal").val(),
            fields: ['name', 'geometry'],
          };
          var service = new google.maps.places.PlacesService(map);
          service.findPlaceFromQuery(request, function(results, status) {
            if (status === google.maps.places.PlacesServiceStatus.OK) {
/*            for (var i = 0; i < results.length; i++) {
                createMarker(results[i]);
              } */
              map.setCenter(results[0].geometry.location);
            }
          });
    }
/*     function createMarker(place) {
        var marker = new google.maps.Marker({
          map: map,
          position: place.geometry.location
        });
    } */
    
    
</script>

<script>
function setLocation(){
	//부모창 frm에 접근하기(opener)
	var frm = opener.document.addMap;
	
	frm.ncode.value = $("#ncode").val();
	frm.inlat.value = $("#inlat").text();
	frm.inlng.value = $("#inlng").text();
	frm.placeid.value = $("#placeid").val();
	frm.pname.value = $("#pname").val();
	
	self.close();
}
</script>
    
</head>
<body>
    <div id="infowindow-content">
      <img src="" width="16" height="16" id="place-icon">
      <span id="place-name"  class="title"></span><br>
      <span id="place-address"></span>
    </div>
    
    <!-- <form action="#" onsubmit="search()" id="pac-card"> -->
    <div id="pac-card">
          <div id="pac-container">
            <input type="text" id="searchVal" name="q" value="" class="address_input" size="40"  method=post />
          </div>
    </div>
    <div id="map_canvas"></div>
    
    <table border="1" style="display:none;">
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
            <td><input type="text" id="ncode" name="ncode" value="" size="50"/></td>
        </tr>
        <tr>  
            <th>placeId</th>
            <td><input type="text" id="placeid" name="placeid" value="" size="50"/></td>
        </tr>
        <tr>  
            <th>장소이름</th>
            <td><input type="text" id="pname" name="pname" value="" size="50"/></td>
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
    <button id="setLocationBtn" onclick="setLocation();">확인</button>

    <div id="photo"></div>
</body>
</html>
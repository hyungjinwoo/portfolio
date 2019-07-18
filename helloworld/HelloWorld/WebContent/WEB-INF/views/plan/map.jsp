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
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB2BbISLnHXNWyIA3jjgUEg8q-R-71NlzY&callback=initMap&libraries=places" async defer></script>
<style>
@import url('https://fonts.googleapis.com/css?family=Nanum+Gothic');
body { font-family:Tahoma,굴림; font-size:9pt; color:#222222; }
form { margin:0px; }
#map_canvas {
  height: 100%;
  width : 100%;
}
      /* Optional: Makes the sample page fill the window. */
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
      #description {
        font-family: Roboto;
        font-size: 15px;
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
	    font-size: 14px;
	    padding: 4px 11px;
	    text-overflow: ellipsis;
	    width: 250px;
	    margin-top: 5px;
	    border-radius: 29px;
	    border: none;
	    font-weight: bold;
	    font-family: Nanum Gothic;
      }
      #searchVal:focus {
        outline:none;
      }
</style>
<script type="text/javascript">
    
    var map;
    var geocoder;
    var address;
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
                        
                        $("#inlat").html(place.geometry.location.lat());
                        $("#inlng").html(place.geometry.location.lng());
                        $("#placeid").val(place.place_id);                        
                        $('#pname').val(place.name);
                        $('#address').val(place.formatted_address);
                        $('#phone').val(place.international_phone_number);
                        $("#website").val(place.website);
                        
                        //일정 작성 페이지 li태그 추가
                        if($("#searchBody #drag-ul #search-result-li")!=null) $("#searchBody #drag-ul #search-result-li").remove();
                        $("#searchBody #drag-ul").html(
                        		"<li class='list-group-item list-group-item-info' draggable='true'>"+
                        		"<img src='<%=request.getContextPath()%>/images/plan-memo.png' id='inputPlaceMemo' onclick='memoView(this);'/>"+
                        		"<span id='wishPlaceName'>"+place.name+"</span>"+
                        		"<input type='hidden' id='placeName' value='"+place.name+"'/>"+
                        		"<input type='hidden' id='placeLat' value='"+place.geometry.location.lat()+"'/>"+
                        		"<input type='hidden' id='placeLng' value='"+place.geometry.location.lng()+"'/>"+
                        		"<textarea id='placeMemo'></textarea>"+
                        		"<button id='delPlace' onclick='delPlace(this);'>삭제</button></li>"
                        		);
                        $('.list-group-sortable-connected').sortable('destroy');
                        $('.list-group-sortable-connected').sortable({
                            placeholderClass: 'list-group-item',
                            connectWith: '.connected',
                            items: ':not(#day)'
                        });
                    }
            }); // map.listner - end
            
    //자동완성
        var card = document.getElementById('pac-card');
        var input = document.getElementById('searchVal');
        
        map.controls[google.maps.ControlPosition.TOP_RIGHT].push(card);
        var autocomplete = new google.maps.places.Autocomplete(input);
        console.log(input);
        console.log(autocomplete);
        
        autocomplete.bindTo('bounds', map);
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
            window.alert("No details available for input: '" + place.name + "'");
            return;
          }
          if (place.geometry.viewport) {
            map.fitBounds(place.geometry.viewport);
          } else {
            map.setCenter(place.geometry.location);
            map.setZoom(17); 
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
    $("#searchVal").on("click",function(){
        $("#searchVal").val("");
    });
}
</script>
    
</head>
<body>
    <div id="infowindow-content">
      <img src="" width="16" height="16" id="place-icon">
      <span id="place-name"  class="title"></span><br>
      <span id="place-address"></span>
    </div>
    
    <div id="pac-card">
          <div id="pac-container">
            <input type="text" id="searchVal" name="q" value="" class="address_input" size="40"  method=post />
          </div>
    </div>
    <div id="map_canvas"></div>
    
    <!-- <table border="1">
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
    <div id="photo"></div> -->
</body>
</html>
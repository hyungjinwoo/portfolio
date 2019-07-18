<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    JSONArray pieChartlist = (JSONArray)request.getAttribute("pieChartlist");
    JSONArray geoChartlist = (JSONArray)request.getAttribute("geoChartlist");
    JSONArray columnChart = (JSONArray)request.getAttribute("columnChart");
    %>
<!DOCTYPE html>
<html>
  <head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/admin/chart.css">

  
<%-- <script src="<%=request.getContextPath()%>/js/jquery-3.4.0.min.js"></script> --%>

<script>

$(function(){
	pieChart('pieChart');
	ColumnChart("ColumnChart")
	GeoChart2('main');
});

function pieChart(va){
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(draw2Chart);

    function draw2Chart() {

     var data = google.visualization.arrayToDataTable(<%=pieChartlist%>);
      
      var options = {
                title: '도시 선호도',
            	pieHole: 0.2,
    	        width: 430,
    	        height: 230
    	      };
      

      var chart = new google.visualization.PieChart(document.getElementById(va));

      chart.draw(data, options);
   }
}

function GeoChart2(va){
      google.charts.load('current', {
          'packages':['geochart'],
          'mapsApiKey': 'AIzaSyB2BbISLnHXNWyIA3jjgUEg8q-R-71NlzY'
        });
        google.charts.setOnLoadCallback(drawRegionsMap);

        function drawRegionsMap() {
        var data = google.visualization.arrayToDataTable(<%=geoChartlist%>);

		          var options = {title: '국가별 리뷰수',
			    	        width: 800,
			    	        height: 350};

		          var chart = new google.visualization.GeoChart(document.getElementById(va));

		          chart.draw(data, options);
		        }
		}
		
		function ColumnChart(va){
		    google.charts.load("current", {packages:['corechart']});
		    google.charts.setOnLoadCallback(drawChart);
		    function drawChart() {
/* 	        var data = google.visualization.arrayToDataTable([
	            ['Genre', 
	            	'Fantasy & Sci Fi', 'Romance', 'Mystery/Crime', 'General', 
	            { role: 'annotation' } ],
	            
	            ['10대', 10,20,15,18, ''],
	            ['20대', 18,65,77,10, ''],
	            ['30대', 17,40,45,8, ''],
	            ['40대', 56,25,76,55, ''],
	            ['50대', 56,25,76,55, ''],
	            ['60대', 56,25,76,55, '']
	          ]); */
	          var data = google.visualization.arrayToDataTable(<%=columnChart%>);

      var view = new google.visualization.DataView(data);
      view.setColumns([0, 1, 2,3,4,
                       {
    	  				calc: "stringify",
                         //sourceColumn: "",
                         type: "string",
                         role: "annotation" },
                       5]);

      var options = {
    		  	title: '연령대별 취향',
    	        width: 400,
    	        height: 230,
    	        legend: { position: 'top', maxLines: 3 },
    	        bar: { groupWidth: '75%' },
    	        isStacked: true,
    	      };
      
      var chart = new google.visualization.ColumnChart(document.getElementById(va));
      chart.draw(view, options);
  }
}
</script>
</head>

  <body>
  	<div id="all-container">
  	
		<div id="chart-container1">
			<div class="chart-item" id="pieChart"></div>
			<div class="chart-item" id="ColumnChart"></div>
		</div>
		
		<div id="chart-container2">
			<div class="chart-item" id="main"></div>
		</div>
		
 		<div id="chart-container3">
		</div> 
		
	</div>
  </body>
  
</html>
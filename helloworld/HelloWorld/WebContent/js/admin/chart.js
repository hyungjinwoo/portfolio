$(function(){
 
	
    $("#annotationChart").on("click",function(e){
    	 $("#main").empty();
    	$("#chart-list-container .chart-item").animate({ opacity:1},300);
    	 $("#annotationChart").animate({
             opacity:0.4
         },300,function(){
         	annotationChart('main');
         });
    });
    
    $("#geoChart").on("click",function(e){
    	 $("#main").empty();
    	$("#chart-list-container .chart-item").animate({ opacity:1},300);
	   	 $("#geoChart").animate({
	         opacity:0.4
	     },300,function(){
	    	 geoChart('main');
	     });
    });
        
    $("#pieChart").on("click",function(e){
    	 $("#main").empty();
    	$("#chart-list-container .chart-item").animate({ opacity:1},300);
	   	 $("#pieChart").animate({
	         opacity:0.4
	     },300,function(){
	    	 pieChart('main');
	     });
    });
        
    annotationChart("annotationChart");
    geoChart("geoChart");
	pieChart('pieChart');
	
	pieChart('main');
});


function annotationChart(va){

	google.charts.load('current', {'packages':['annotationchart']});
	google.charts.setOnLoadCallback(drawChart);
	
	function drawChart() {
	  var data = new google.visualization.DataTable();
	  data.addColumn('date', 'Date');
	  data.addColumn('number', 'Kepler-22b mission');
	  data.addColumn('string', 'Kepler title');
	  data.addColumn('string', 'Kepler text');
	  data.addColumn('number', 'Gliese 163 mission');
	  data.addColumn('string', 'Gliese title');
	  data.addColumn('string', 'Gliese text');
	  data.addRows([
	    [new Date(2314, 2, 15), 12400, undefined, undefined,
	                            10645, undefined, undefined],
	    [new Date(2314, 2, 16), 24045, 'Lalibertines', 'First encounter',
	                            12374, undefined, undefined],
	    [new Date(2314, 2, 17), 35022, 'Lalibertines', 'They are very tall',
	                            15766, 'Gallantors', 'First Encounter'],
	    [new Date(2314, 2, 18), 12284, 'Lalibertines', 'Attack on our crew!',
	                            34334, 'Gallantors', 'Statement of shared principles'],
	    [new Date(2314, 2, 19), 8476, 'Lalibertines', 'Heavy casualties',
	                            66467, 'Gallantors', 'Mysteries revealed'],
	    [new Date(2314, 2, 20), 0, 'Lalibertines', 'All crew lost',
	                            79463, 'Gallantors', 'Omniscience achieved']
	  ]);
	
	  console.log(document.getElementById('chart_div'));
	  var chart = new google.visualization.AnnotationChart(document.getElementById(va));
	
	  var options = {
	    displayAnnotations: true
	  };
	
	  chart.draw(data, options);
	}
}

function geoChart(va){
	google.charts.load('current', {
        'packages':['geochart'],
        // Note: you will need to get a mapsApiKey for your project.
        // See: https://developers.google.com/chart/interactive/docs/basic_load_libs#load-settings
        'mapsApiKey': 'AIzaSyB2BbISLnHXNWyIA3jjgUEg8q-R-71NlzY'
      });
      google.charts.setOnLoadCallback(drawRegionsMap);

      function drawRegionsMap() {
        var data = google.visualization.arrayToDataTable([
          ['Country',   'Latitude'],
          ['Japan', 36],
        ]);

        var options = {
          region: 'ASIA', // Africa
          colorAxis: {colors: ['#00853f', 'black', '#e31b23']},
          backgroundColor: '#81d4fa',
          datalessRegionColor: '#f8bbd0',
          defaultColor: '#f5f5f5',
        };

        var chart = new google.visualization.GeoChart(document.getElementById(va));
        chart.draw(data, options);
      };
}

function pieChart(va){
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(draw2Chart);

    function draw2Chart() {

      var data = google.visualization.arrayToDataTable([
        ['Task', 'Hours per Day'],
        ['KR',     5],
        ['JP',      8],
        ['US',  12],
        ['CN', 9]
      ]);
      
      var options = {
                title: 'top like country',
            	pieHole: 0.4
    	      };
      

      var chart = new google.visualization.PieChart(document.getElementById(va));

      chart.draw(data, options);
   }
}


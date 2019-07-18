package admin.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import admin.model.dao.AdminDAO;
import admin.model.service.AdminService;

/**
 * Servlet implementation class adminQna
 */
@WebServlet("/admin/chart")
public class AdminChart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
//[["국가명(한글)","리뷰수"],["대한민국",15],["미국",300]]
	JSONArray pieChartlist = new AdminService().pielist();

		
//[["국가명(영문)","리뷰수"],["Korea,Republic of",400],["Korea (Democratic Peoples Republic of)",200],["Central African Republic",500]]
	JSONArray geoChartlist = new AdminService().chartlist();


//[["Genre","관광","휴양","레저","레저",{"role":"annotation"}],
//	["10대",52,44,76,55,""],
//	["20대",44,12,77,90,""]]
	JSONArray columnChart = new JSONArray();
	JSONArray columnCharthead = new JSONArray();
		columnCharthead.add("Genre");
		columnCharthead.add("관광");
		columnCharthead.add("휴양");
		columnCharthead.add("레저");
		columnCharthead.add("맛집");
		JSONObject a = new JSONObject();
			a.put("role", "annotation");
			columnCharthead.add(a);
			columnChart.add(columnCharthead);

	JSONArray columnChartdata =null;
	
	
	for(int i=1; i<7; i++) {
		columnChartdata = new JSONArray();
			columnChartdata.add(i+"0대");
			for(int j=0; j<4; j++) {
				
				String like=null;
				
				switch(j){
					case 0: like="관광";break;
					case 1: like="휴양";break;
					case 2: like="레저";break;
					case 3: like="맛집";break;
				}
				int result = new AdminService().columnChart(i,like);
				columnChartdata.add(result);
			}
			columnChartdata.add("");
			columnChart.add(columnChartdata);
	}
	

		request.setAttribute("pieChartlist", pieChartlist);
		request.setAttribute("geoChartlist", geoChartlist);
		request.setAttribute("columnChart", columnChart);
		request.getRequestDispatcher("/WEB-INF/views/admin/chart.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

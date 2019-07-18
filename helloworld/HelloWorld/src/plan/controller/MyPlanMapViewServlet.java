package plan.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import plan.model.service.PlanService;


/**
 * Servlet implementation class MemberMyPlanViewServlet
 */
@WebServlet("/plan/myPlanMapView")
public class MyPlanMapViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		int planNo = Integer.parseInt(request.getParameter("planNo"));
	
		//일정 안에 있는 장소들의 리스트 순서 대로 가져오기
		JSONArray jsonArray = new PlanService().placeLatLngList(planNo);
		
		//메모 가져오기
		JSONArray jsonCommentArray = new PlanService().placeCommentList(planNo);
		
		System.out.println(jsonArray);
		System.out.println(jsonCommentArray);
		request.setAttribute("jsonArray",  jsonArray);
		request.setAttribute("jsonCommentArray", jsonCommentArray);
		request.getRequestDispatcher("/WEB-INF/views/plan/MyPlanMap.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

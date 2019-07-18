package plan.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.vo.Member;
import plan.model.service.PlanService;
import plan.model.vo.PlaceList;
import plan.model.vo.Plan;

/**
 * Servlet implementation class PlanInputServlet
 */
@WebServlet("/plan/planList")
public class PlanListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//파라미터 핸들링
		Member member = (Member)request.getSession().getAttribute("memberLoggedIn");
		String userId = member.getMemberId();
		
		//일정 리스트 가져오기
		List<Plan> planList = new PlanService().selectAllPlan(userId);
		List<Integer> planNoList = new ArrayList<>();
		for(Plan p : planList) {
			planNoList.add(p.getPlanNo());
		}
		List<PlaceList> planPlaceList = new PlanService().selectAllPlanPlaceList(planNoList);
		
		request.setAttribute("planList", planList);
		request.setAttribute("planPlaceList", planPlaceList);
		//view
		request.getRequestDispatcher("/WEB-INF/views/plan/planList.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

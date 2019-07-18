package plan.controller;

import java.io.IOException;
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
import plan.model.vo.WishPlace;

/**
 * Servlet implementation class PlanWriteFrmServlet
 */
@WebServlet("/plan/planRewriteFrm")
public class PlanRewriteFrmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//파라미터 핸들링
		Member member = (Member)request.getSession().getAttribute("memberLoggedIn");
		String userId = member.getMemberId();
		int planNo = Integer.parseInt(request.getParameter("planNo"));
		
		//업무로직
		//유저아이디로부터 위시리스트를 가져온다
		List<WishPlace> wishPlace = new PlanService().selectAllWishList(userId);
		//지금 수정하는 일정의 일정번호로부터 placeList와 plan정보를 가져온다
		Plan plan = new PlanService().selectPlan(planNo);
		
		List<PlaceList> placeList = new PlanService().selectPlaceList(planNo);
		
		request.setAttribute("plan", plan);
		request.setAttribute("placeList", placeList);
		request.setAttribute("wishPlace", wishPlace);
		//view
		request.getRequestDispatcher("/WEB-INF/views/plan/planRewriteFrm.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

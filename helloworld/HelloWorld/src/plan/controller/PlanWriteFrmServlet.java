package plan.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import plan.model.service.PlanService;
import plan.model.vo.WishPlace;

/**
 * Servlet implementation class PlanWriteFrmServlet
 */
@WebServlet("/plan/planWriteFrm")
public class PlanWriteFrmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//파라미터 핸들링
		String userId = request.getParameter("userId");
		
		//업무로직
		//유저아이디로부터 위시리스트를 가져온다
		//List<WishPlace> wishPlace = new PlanService().selectAllWishList(userId);
		
		List<WishPlace> wishPlace = new PlanService().selectAllWishList(userId);
		request.setAttribute("wishPlace", wishPlace);
		
		//view
		request.getRequestDispatcher("/WEB-INF/views/plan/planWriteFrm.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

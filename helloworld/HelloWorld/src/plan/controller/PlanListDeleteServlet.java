package plan.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.vo.Member;
import plan.model.service.PlanService;

/**
 * Servlet implementation class PlanListDeleteServlet
 */
@WebServlet("/plan/planDelete")
public class PlanListDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			//1.인코딩
			request.setCharacterEncoding("UTF-8");
			
			//2.파라미터핸들링
			int planNo = Integer.parseInt(request.getParameter("planNo"));
			
			//3.업무로직
			int result = new PlanService().deletePlan(planNo);
			String msg = "";
			String loc = "/";
			
			HttpSession session = ((HttpServletRequest)request).getSession();

			Member memberLoggedIn = (Member)session.getAttribute("memberLoggedIn");
			
		
			if(result>0) {
				msg = "일정이 삭제되었습니다.";
				loc = "/plan/planList?memberId="+memberLoggedIn.getMemberId();
			}
			else {
				msg="일정 삭제 실패";
				loc="/plan/planListmemberId="+memberLoggedIn.getMemberId();
			}
		
			
			request.setAttribute("msg", msg);
			request.setAttribute("loc", loc);
			
			//4.view단처리
			String view = "/WEB-INF/views/common/msg.jsp";
			request.getRequestDispatcher(view).forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

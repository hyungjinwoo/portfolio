package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;
import member.model.vo.Member;

@WebServlet("/member/getMemberLoggedIn")
public class GetMemberLoggedIn extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = request.getParameter("memberId");
		System.out.println("memberId=="+memberId);

		Member memberLoggedIn = new MemberService().selectOne(memberId);
		HttpSession session = request.getSession();
		session.setAttribute("memberLoggedIn", memberLoggedIn); //세션도 속성 전달 가능
		
		//자바코드에서 유효시간 설정 가능(초 단위): web.xml보다 우선 순위가 높다
		session.setMaxInactiveInterval(1800);
		memberLoggedIn = (Member)session.getAttribute("memberLoggedIn");
		
		System.out.println("멤버로그드인@겟세션서블릿=="+memberLoggedIn);
		
		response.setContentType("application/java; charset=UTF-8");
		response.getWriter().print(memberLoggedIn);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

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

@WebServlet(urlPatterns= {"/member/memberMyPage"},
			name = "MemberMyPageServlet")
public class MemberMyPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	request.setCharacterEncoding("utf-8");
	
	HttpSession session = request.getSession();
	Member memberLoggedIn = (Member)session.getAttribute("memberLoggedIn");
	
	String memberId = request.getParameter("memberId");
	if(memberId==null) {
		memberId = memberLoggedIn.getMemberId();		
	}
	System.out.println("mebmerId@mypageSERVLET=="+memberId);
	
	//업무로직
	Member m = new MemberService().selectOne(memberId);
	
	//뷰단
	request.setAttribute("member", m);
	request.getRequestDispatcher("/WEB-INF/views/member/memberMyPage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

@WebServlet(urlPatterns= {"/member/updatePasswordAfterFindEnd"},
			name = "UpdatePasswordAfterFindEndServlet")
public class UpdatePasswordAfterFindEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String memberId = request.getParameter("memberId_");
		String passwordNew = request.getParameter("passwordNew");

		Member m = new Member();
		m.setMemberId(memberId);
		m.setPassword(passwordNew);
		
		String view = "/WEB-INF/views/common/msg.jsp";
		String msg = "";
		String loc = "";

		m.setPassword(passwordNew);
		int result = new MemberService().updatePassword(m);
		if(result > 0) {
			//비밀번호 변경
			msg = "비밀번호 변경이 완료되었습니다.";
			loc = "/";
		}			
		else {
			//변경 실패
			msg = "비밀번호 변경 실패 : 관리자에게 문의하세요.";
			loc = "/";
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher(view).forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
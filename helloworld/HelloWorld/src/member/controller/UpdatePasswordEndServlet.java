package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

@WebServlet(urlPatterns= {"/member/updatePasswordEnd"},
			name = "UpdatePasswordEndServlet")
public class UpdatePasswordEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String memberId = request.getParameter("memberId");
		String passwordOld = request.getParameter("passwordOld");
		String passwordNew = request.getParameter("passwordNew");
		
		Member m = new MemberService().selectOne(memberId);
		
		String view = "/WEB-INF/views/common/msg.jsp";
		String msg = "";
		String loc = "";

		if(passwordOld.equals(m.getPassword())) {
			//passwordOld 일치
			m.setMemberId(memberId);
			m.setPassword(passwordNew);
			int result = new MemberService().updatePassword(m);
			if(result > 0) {
				//비밀번호 변경
				msg = "비밀번호 변경이 완료되었습니다.";
				loc = "/member/memberMyPage?memberId="+m.getMemberId();
			}			
			else {
				//변경 실패
				msg = "비밀번호 변경 실패 : 관리자에게 문의하세요.";
				loc = "/member/updatePassword?memberId="+m.getMemberId();
			}
				
		}
		else {
			//passwordOld 불일치
			msg = "기존 비밀번호가 일치하지 않습니다.";
			loc = "/member/updatePassword?memberId="+m.getMemberId();
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher(view).forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
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

@WebServlet(urlPatterns= {"/member/deleteMemberEnd"},
			name = "DeleteMemberServlet")
public class DeleteMemberEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = request.getParameter("memberId_");
		String password = request.getParameter("passwordNew");
		Member m = new Member();
		m.setMemberId(memberId);
		m.setPassword(password);
		System.out.println("password@deleteServlet=="+password);
		
		String view = "/WEB-INF/views/common/msg.jsp";
		String msg = "";
		String loc = "";
		
		Member mOld = new MemberService().selectOne(memberId);
		
		if(password.equals(mOld.getPassword())) {
			//비밀번호 일치
			int result = new MemberService().deleteMember(m);
			
			if(result>0) {
				//탈퇴성공
				msg = "성공적으로 탈퇴되었습니다.";
				loc = "/member/Logout"; 
				//이렇게 해도 똑같음. 위에 코드가 로그아웃에 있는 코드
			}
			else {
				//탈퇴실패
				msg = "회원 탈퇴 실패! 관리자에게 문의해 주세요";
				loc = "/member/deleteMember?memberId="+memberId;
			}
		}
		else {
			//비밀번호 불일치
			msg = "비밀번호가 일치하지 않습니다.";
			loc = "/member/deleteMember?memberId="+memberId;
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher(view).forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

package member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

@WebServlet("/member/findMemberId")
public class FindMemberIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//인코딩
		request.setCharacterEncoding("utf-8");
		//파라미터
		String memberName = request.getParameter("memberName");
		String tel = request.getParameter("tel");
		//업무로직
		List<Member> memberList = new MemberService().selectOneByName(memberName);
		
		String findIdMsg = "";
		if(!memberList.isEmpty()) {
			//이름이 있긴 있음
			
			//전화번호 비교하기
			for(Member m : memberList) {
				if(tel.equals(m.getTel())) {
					//전화번호 일치
					findIdMsg = "회원님의 아이디는 ["+m.getMemberId()+"] 입니다.";
					break;
				}
			}
			
			//전화번호 일치하는 거 없음
			if(findIdMsg.equals("")) {
				findIdMsg = "전화번호가 일치하지 않습니다.";
			}
			
		}
		else {
			//그 이름으로 가입된 회원이 없음
			findIdMsg = "가입되지 않은 이름입니다.";
		}
		
		//view단
		
		request.setAttribute("memberName", memberName);
		request.setAttribute("tel", tel);
		request.setAttribute("findIdMsg", findIdMsg);
		request.getRequestDispatcher("/WEB-INF/views/member/findMember.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

@WebServlet("/member/findMemberPassword")
public class FindMemberPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//인코딩
		request.setCharacterEncoding("utf-8");
		//파라미터
		String memberId = request.getParameter("memberId");
		String question = request.getParameter("question");
		String answer = request.getParameter("answer").replaceAll(" ", "").replaceAll("\\p{Z}", "");
		//업무로직
		Member m = new MemberService().selectOne(memberId);
		
		String findPasswordMsg = "";
		
		if(m!=null) {
			//아이디 있음
			if(question.equals(m.getQuestion())) {
				//질문 일치
				if(answer.equals(m.getAnswer())) {
					//정답까지 일치
					findPasswordMsg="<span id='updatePasswordAfterFind'>새로운 비밀번호 설정하기</span>";
					
				}
				else {
					//정답 불일치
					findPasswordMsg = "정답이 일치하지 않습니다.";
				}
			}
			else {
				//질문 불일치
				findPasswordMsg = "질문이 일치하지 않습니다.";
			}
		}
		else {
			//아이디 없음
			findPasswordMsg = "아이디가 존재하지 않습니다.";
		}
		
		request.setAttribute("memberId", memberId);
		request.setAttribute("question", question);
		request.setAttribute("answer", answer);
		request.setAttribute("findPasswordMsg", findPasswordMsg);
		request.getRequestDispatcher("/WEB-INF/views/member/findMember.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

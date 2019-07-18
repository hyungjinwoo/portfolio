package quest.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quest.model.service.QuestionService;

/**
 * Servlet implementation class QuestionClearServlet
 */
@WebServlet("/quest/questionClear")
public class QuestionClearServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int questNo = Integer.parseInt(request.getParameter("questNo"));
		System.out.println("해결완료를 체크한 글번호:"+questNo);
		
		int result = new QuestionService().QuestionCommentClear(questNo);
		
		String msg = "";
		String loc = "";
		if(result>0) {
			msg = "문의글 상태변경 성공";
		    loc = "/quest/questList";
		}
		else {
			msg = "문의글 상태변경 실패";
		    loc = "/quest/questList";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

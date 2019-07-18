package quest.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quest.model.service.QuestionService;

/**
 * Servlet implementation class QuestionCommentDeleteServlet
 */
@WebServlet("/quest/deleteQuestionComment")
public class QuestionCommentDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int questionCommentNo = Integer.parseInt(request.getParameter("questionCommentNo"));
		int questionNo = Integer.parseInt(request.getParameter("questionNo"));
		
		int result = new QuestionService().deleteQuestionComment(questionCommentNo, questionNo);
		
		String msg = "";
		String loc = "";
		if(result>0) {
			msg = "댓글 등록 성공";
			loc = "/quest/questView?questNo="+questionNo;
		}
		else {
			msg = "댓글 등록 실패";
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

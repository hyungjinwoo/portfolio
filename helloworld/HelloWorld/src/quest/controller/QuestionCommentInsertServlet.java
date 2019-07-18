package quest.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quest.model.service.QuestionService;
import quest.model.vo.QuestionComment;

/**
 * Servlet implementation class QuestionCommentInsertServlet
 */
@WebServlet("/quest/questionCommentInsert")
public class QuestionCommentInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String QuestionCommentContent = request.getParameter("QuestionCommentContent");
		int questionRef = Integer.parseInt(request.getParameter("questionRef")); // 댓글 다는 글의 번호
		
		QuestionComment qc = new QuestionComment(0, QuestionCommentContent, questionRef, null); 
		int result = new QuestionService().insertQuestionComment(qc);
		
		String msg = "";
		String loc = "";
		if(result>0) {
			msg = "댓글 등록 성공";
			loc = "/quest/questView?questNo="+questionRef;
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

package quest.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;
import quest.model.service.QuestionService;
import quest.model.vo.Question;
import quest.model.vo.QuestionComment;

/**
 * Servlet implementation class QuestViewServlet
 */
@WebServlet("/quest/questView")
public class QuestViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int questNo = Integer.parseInt(request.getParameter("questNo"));
		
		Question q = new QuestionService().selectQuestionOne(questNo);
		List<QuestionComment> list = new QuestionService().selectQuestionComment(questNo);
		System.out.println("댓글리스트목록@Servlet="+list);
		
		Member m = new MemberService().selectOne(q.getQuestionWriter());
		
		request.setAttribute("m", m);
		request.setAttribute("q", q);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/views/quest/questView.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

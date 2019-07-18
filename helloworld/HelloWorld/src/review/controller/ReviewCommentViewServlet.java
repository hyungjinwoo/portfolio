package review.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.vo.Member;
import review.model.service.ReviewService;
import review.model.vo.Review;
import review.model.vo.ReviewComment;

/**
 * Servlet implementation class ReviewCommentViewServlet
 */
@WebServlet("/review/reviewComment")
public class ReviewCommentViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int reviewNo = 0;
		HttpSession session = request.getSession();
		try {
			reviewNo = Integer.parseInt(request.getParameter("reviewNo"));			
		} catch(NumberFormatException e){
			session = request.getSession();
			reviewNo = (int)session.getAttribute("reviewNo");
		}
		String LoggedId = "";
		try {
			LoggedId = request.getParameter("LoggedId");			
		} catch(NullPointerException e) {
			session = request.getSession();
			LoggedId = (String)session.getAttribute("reviewCommentWriter");
		}
		System.out.println("@@@:"+reviewNo);
		System.out.println("@@@:"+LoggedId);
		
		Review rv = new ReviewService().selectOne(reviewNo); // 해당 리뷰글가지고오기
		List<ReviewComment> commentList = new ReviewService().selectReviewComment(reviewNo); // 해당리뷰글 댓글가져오기
	
		request.setAttribute("rv", rv);
		request.setAttribute("commentList", commentList);
		request.setAttribute("LoggedId", LoggedId);
		request.getRequestDispatcher("/WEB-INF/views/review/reviewCommentView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

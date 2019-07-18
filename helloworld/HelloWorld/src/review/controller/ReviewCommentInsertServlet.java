package review.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import review.model.service.ReviewService;
import review.model.vo.ReviewComment;

/**
 * Servlet implementation class ReviewCommentInsertServlet
 */
@WebServlet("/review/reviewCommentInsert")
public class ReviewCommentInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		int reviewRef = Integer.parseInt(request.getParameter("reviewRef"));
		String reviewCommentWriter = request.getParameter("reviewCommentWriter");
		int reviewCommentLevel = Integer.parseInt(request.getParameter("reviewCommentLevel"));
		int reviewCommentRef = Integer.parseInt(request.getParameter("reviewCommentRef"));
		String reviewCommentContent = request.getParameter("reviewCommentContent");
		System.out.println("reviewCommentContent@servlet="+reviewCommentContent);
		
		ReviewComment rc = new ReviewComment(0, reviewRef, reviewCommentWriter, reviewCommentRef, reviewCommentLevel, reviewCommentContent, null);
		
		int result = new ReviewService().insertReviewComment(rc);
		
		//header 정보 열람
		Map<String, String> headerMap = new HashMap<>();
		Enumeration<String> headerNames = request.getHeaderNames();
		//사용자가 요청한 헤더에 뭐가 왔는지 다 볼 것
		while(headerNames.hasMoreElements()) {
			String name = headerNames.nextElement();
			String value = request.getHeader(name); //key값으로 가져오는 메소드
			headerMap.put(name, value); //헤더맵에 다 집어넣기
		}
		String referer = request.getHeader("Referer"); //키값은 대소문자 구분하니 정확하게 입력
		String origin = request.getHeader("Origin");
		String url = request.getRequestURL().toString(); //리턴값이 스트링버퍼이므로 string으로 변환
		String uri = request.getRequestURI();
		
		
		String view = "/WEB-INF/views/common/msg.jsp";
		String msg = "";
		String loc = referer.replace(origin+request.getContextPath(), "");
		
		if(result>0) {
			msg = "댓글 등록 성공!";
		} else {
			msg = "댓글 등록 실패!";
		}
	
		HttpSession session = request.getSession();
		session.setAttribute("reviewNo", reviewRef); 
		session.setAttribute("reviewCommentWriter", reviewCommentWriter); 
		
	
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.setAttribute("result", result);
		request.getRequestDispatcher(view).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

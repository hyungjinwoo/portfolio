package ajax.reviewjson;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import place.model.service.PlaceService;
import place.model.vo.Place;
import review.model.service.ReviewService;
import review.model.vo.Review;
import review.model.vo.ReviewComment;
import review.model.vo.ReviewPhoto;

/**
 * Servlet implementation class AjaxReviewViewServlet
 */
@WebServlet("/ajax/reviewView")
public class AjaxReviewViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
		String LoggedId = request.getParameter("LoggedId");
		System.out.println("보여줄 글번호 확인:"+reviewNo);
		System.out.println("로그인되어있는 회원아이디:"+LoggedId);
		
		Review rv = new ReviewService().selectOne(reviewNo); // 해당 리뷰글가지고오기
		List<ReviewComment> commentList = new ReviewService().selectReviewComment(reviewNo); // 해당리뷰글 댓글가져오기
		List<ReviewPhoto> list = new ReviewService().selectPhoto(reviewNo); // 해당 리뷰글의 이미지파일 가져오기
		int num = new ReviewService().checkLike(LoggedId, reviewNo); //로그인되어있는 회원아이디로 좋아요테이블에서 해당아이디로 저장되어있는 글번호 가지고오기
		
		String point = rv.getPlaceId();
		System.out.println("선택한 리뷰글에 담긴 장소아이디:"+point);
		Place p = new PlaceService().selectPlaceOne(point); // 해당리뷰글에 등록된 장소정보 가지고오기
		
		//사용자읽음여부 쿠키검사
		Cookie[] cookies = request.getCookies();
		boolean hasRead = false;
		String reviewCookieVal = "";
		// 사이트 첫 방문시 아무런 쿠키가 없으므로 cookies는 null, 따라서 null여부도 따져야한다
		// reviewCookie = |2||3||100| 해당 글을 읽었다는 표시로 구분
		if (cookies != null) {
			for (Cookie c : cookies) {
				String name = c.getName();
				String value = c.getValue();

				// reviewCookie인 경우
				if ("reviewCookie".equals(name)) {
					reviewCookieVal = value;

					if (value.contains("|" + reviewNo + "|")) {
						hasRead = true;
						break;
					}
				}
			}
		}
		// 쿠키에 읽은 값이 없는 경우
		if (!hasRead) {
			new ReviewService().increaseReadCount(reviewNo);

			// 쿠키생성
			Cookie reviewCookie = new Cookie("reviewCookie", reviewCookieVal + "|" + reviewNo + "|");
			reviewCookie.setPath(request.getContextPath() + "/ajax/reviewView");
			// reviewCookie.setMaxAge(); <- 생략시 없어지지않고 지속됨

			// 응답객체 cookie 전송
			response.addCookie(reviewCookie);
			System.out.println("reviewCookie생성:" + reviewCookie);
		}
		
		
		request.setAttribute("LoggedId", LoggedId);
		request.setAttribute("num", num);
		
		request.setAttribute("rv", rv);
		request.setAttribute("list", list);
		request.setAttribute("commentList", commentList);
		request.setAttribute("p", p);
		
		request.getRequestDispatcher("/WEB-INF/views/review/reviewView.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

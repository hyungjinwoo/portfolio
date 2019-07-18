package review.controller;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import review.model.service.ReviewService;
import review.model.vo.ReviewPhoto;

/**
 * Servlet implementation class ReviewDeleteServlet
 */
@WebServlet("/review/reviewDelete")
public class ReviewDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
		
		
		//존재하는 사진 찾아서 저장소에서 삭제하기
		List<ReviewPhoto> list = new ReviewService().selectPhoto(reviewNo);
		String saveDirectory = getServletContext().getRealPath("/upload/review");
		for(ReviewPhoto rp : list) {
			boolean bool = new File(saveDirectory+"/"+rp.getRenamedPhotoName()).delete();
		}
		
		
		int result = new ReviewService().deleteReview(reviewNo); // 리뷰테이블 삭제
		int result_1 = new ReviewService().deleteReviewPhoto(reviewNo); // 리뷰사진테이블 삭제
		
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
				
		String loc = referer.replace(origin+request.getContextPath(), "");
		
		request.setAttribute("msg", "리뷰 삭제 완료!");
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

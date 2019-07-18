package ajax.reviewjson;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import review.model.service.ReviewService;

/**
 * Servlet implementation class AddReviewLikeCountServlet
 */
@WebServlet("/ajax/addReviewLikeCount")
public class AddReviewLikeCountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
		String LoggedId = request.getParameter("LoggedId");
		System.out.println("좋아요누를 글의 번호:"+reviewNo);
		System.out.println("좋아요누를 로그인된 회원아이디:"+LoggedId);
		
		int result =  new ReviewService().checkLike(LoggedId, reviewNo);//해당 회원이 해당글에 좋아요를 했는지 확인 (없으면0, 있으면1로 리턴)
		
		//해당글의 좋아요 수를 +1 해주고, 좋아요테이블에 기록저장
		int LikeCount = 0;
		if(result == 0) {
			new ReviewService().updateReviewLikeCount(reviewNo); 
			new ReviewService().insertLikeCount(LoggedId, reviewNo);
			LikeCount = new ReviewService().countLikeCount(reviewNo);// 현재 좋아요 숫자를 리턴
		}
		
		JSONArray jsonArr = new JSONArray();
		JSONObject jsonUser = new JSONObject();
		jsonUser.put("LikeCount", LikeCount);
		jsonArr.add(jsonUser);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonArr);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

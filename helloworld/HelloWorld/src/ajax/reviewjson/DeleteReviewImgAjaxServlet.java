package ajax.reviewjson;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import review.model.service.ReviewService;
import review.model.vo.ReviewPhoto;

/**
 * Servlet implementation class DeleteReviewImgAjaxServlet
 */
@WebServlet("/ajax/gson/review/deleteReviewImg")
public class DeleteReviewImgAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
		String img = request.getParameter("img");
		
		//체크한 파일 삭제
		String saveDirectory = getServletContext().getRealPath("/upload/review");
		boolean bool = new File(saveDirectory+"/"+img).delete();
		
		//데이터베이스 반영(이미지삭제)
		int result = new ReviewService().deleteReviewImg(img);
		
		//갱신된 리뷰이미지테이블에서 새로 가지고 온다
		List<ReviewPhoto> list = new ReviewService().selectPhoto(reviewNo);
		
		JSONArray jsonArr = new JSONArray(); // [{},{},{}...]
		for(ReviewPhoto rp : list) {
			JSONObject jsonUser = new JSONObject();
			jsonUser.put("originalFileName", rp.getOriginalPhotoName());

			jsonArr.add(jsonUser);
		}
		
		System.out.println("jsonArr확인="+jsonArr);
		
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

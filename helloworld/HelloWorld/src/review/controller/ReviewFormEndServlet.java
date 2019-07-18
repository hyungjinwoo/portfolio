package review.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import common.HelloMVCFileRenamePolicy;
import place.model.service.PlaceService;
import place.model.vo.Place;
import review.model.service.ReviewService;
import review.model.vo.Review;
import review.model.vo.ReviewPhoto;

/**
 * Servlet implementation class ReviewFormEndServlet
 */
@WebServlet("/review/reviewFormEnd")
public class ReviewFormEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!ServletFileUpload.isMultipartContent(request)) {
			request.setAttribute("msg", "파일 업로드 오류: 관리자에게 문의하세요");
			request.setAttribute("loc", "/");
			request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
			return;
		}
		
		//a.파일저장경로
		String saveDirectory = getServletContext().getRealPath("/")+"upload/review";
		
		//b.파일최대용량:10mb
		//파일최대용량을 넘어서면 IOException을 던진다
		int maxPostSize = 1024*1024*10;
				
		//c.FileRenamePolicy객체 생성
		FileRenamePolicy policy = new HelloMVCFileRenamePolicy();
				
		//MultipartRequest객체생성
		MultipartRequest multiReq = new MultipartRequest(request, saveDirectory, maxPostSize, "UTF-8", policy);
		
		// -------------------------------------------------------------------------------//
		
		//리뷰테이블로
		String reviewTitle = multiReq.getParameter("reviewTitle");
		String reviewWriter = multiReq.getParameter("reviewWriter");
		String reviewContent = multiReq.getParameter("reviewContent");
		int placeRating = Integer.parseInt(multiReq.getParameter("star-input"));
		String placeId = multiReq.getParameter("placeid"); // 장소아이디
		String cityCode = multiReq.getParameter("cityCode");//도시코드
		
		System.out.println("리뷰제목:"+reviewTitle);
		System.out.println("리뷰작성자:"+reviewWriter);
		System.out.println("리뷰내용:"+reviewContent);
		System.out.println("장소점수:"+placeRating);
		System.out.println("장소아이디:"+placeId);
		
		Review rv = new Review(0, reviewWriter, placeId, reviewTitle, reviewContent, placeRating, 0, 0, null, cityCode);
		int lastNum = new ReviewService().insertReview(rv); // result에 해당 글번호가 담겨있음
		System.out.println("실행결과 :"+lastNum);
		
		//리뷰포토테이블로
		String OrigianlreviewImg = "";
		String RenamedlreviewImg = "";
		
		for(int i=1; i<11; i++) {
			
			if(multiReq.getOriginalFileName("reviewImg-"+i)!=null) {
				
			OrigianlreviewImg += multiReq.getOriginalFileName("reviewImg-"+i);
			RenamedlreviewImg += multiReq.getFilesystemName("reviewImg-"+i);
			
			}
			else {
				OrigianlreviewImg += "없음";
				RenamedlreviewImg += "없음";
			}
			
			
			if(i<10) {
				OrigianlreviewImg += "&";
				RenamedlreviewImg += "&";
			}
		}
		
		System.out.println("============================================");
		System.out.println(OrigianlreviewImg);
		System.out.println(RenamedlreviewImg);
		
		ReviewPhoto rp = new ReviewPhoto(0, lastNum, 0, OrigianlreviewImg, RenamedlreviewImg);
		
		int ReviewPhotoResult = new ReviewService().insertReviewPhoto(rp);
		
		//장소테이블로
		String ncode = multiReq.getParameter("ncode"); // 국가코드
		Double inlat = Double.parseDouble(multiReq.getParameter("inlat")); // 위도
		Double inlng = Double.parseDouble(multiReq.getParameter("inlng")); // 경도
		String pname = multiReq.getParameter("pname"); // 장소이름
		String placeCode = multiReq.getParameter("placeNo"); // 장소분류
		
		System.out.println("국가코드:"+ncode);
		System.out.println("도시코드:"+cityCode);
		System.out.println("위도:"+inlat);
		System.out.println("경도:"+inlng);
		System.out.println("장소이름:"+pname);
		System.out.println("장소아이디:"+placeId);
		System.out.println("장소분류코드:"+placeCode);
		
		//우선 해당 장소가 있는지 없는지 확인
		int result = new PlaceService().checkPlace(placeId); 
		
		int newResult = 0;
		//해당장소가 있다면,
		if(result>0) {
			System.out.println("해당장소 있음 실행");
			int[] totalRating = new ReviewService().avgRating(placeId); // 리뷰게시판에서 해당장소에 리뷰를 남긴 총 인원과 장소평점의 총합 가져오기
			int total = (totalRating[0] + placeRating) / (totalRating[1] + 1);
			
			// 해당 장소에 장소Rating새로 계산해서 update
			newResult = new PlaceService().updatePlaceRating(placeId, total, placeCode, cityCode);
		}
		
		//해당장소가 없다면
		else {
			System.out.println("해당장소없음실행");
			Place p = new Place(placeId, cityCode, placeCode, pname, inlat, inlng, placeRating);
			newResult = new PlaceService().insertPlace(p);
		}
		
		String msg = "";
		String loc = "";
		if(newResult>0) {
			msg = "리뷰게시글 등록 성공";
			loc = "/review/MyreviewList?LoggedId="+reviewWriter;
		}
		else {
			msg = "리뷰게시글 등록 실패";
			loc = "/";
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

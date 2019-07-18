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
 * Servlet implementation class ReviewUpdateEndServlet
 */
@WebServlet("/review/reviewUpdateEnd")
public class ReviewUpdateEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//enctype=multipart/form-data로 전송했는지 여부검사
		if(!ServletFileUpload.isMultipartContent(request)) {
			request.setAttribute("msg", "파일 업로드 오류: 관리자에게 문의하세요");
			request.setAttribute("loc", "/");
			request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
			return;
		}
		
		//MultipartRequest객체 생성
		/*MultipartRequest(HttpServletRequest request,String saveDirectory,int maxPostSize,String encoding,FileRenamePolicy policy)*/
		String saveDirectory = getServletContext().getRealPath("/upload/review");
		
		int maxPostSize = 1024*1024*10;
		
		FileRenamePolicy policy = new HelloMVCFileRenamePolicy();
		
		MultipartRequest multiReq = new MultipartRequest(request, saveDirectory, maxPostSize, "UTF-8", policy);
		
		//------------------------------------------------------------
		//리뷰테이블 부분
		int reviewNo = Integer.parseInt(multiReq.getParameter("reviewNo"));
		String reviewWriter = multiReq.getParameter("reviewWriter");
		String placeId = multiReq.getParameter("placeid");
		String reviewTitle = multiReq.getParameter("reviewTitle");
		String reviewContent = multiReq.getParameter("reviewContent");
		int placeRating = Integer.parseInt(multiReq.getParameter("star-input"));
		String cityCode = multiReq.getParameter("cityCode");
		
		System.out.println("수정하는 글 번호:"+reviewNo);
		System.out.println("수정하는 글의 작성자:"+reviewWriter);
		System.out.println("수정하는 글의 장소아이디:"+placeId);
		System.out.println("수정하는 글의 제목:"+reviewTitle);
		System.out.println("수정하는 글의 내용:"+reviewContent);
		System.out.println("수정하는 글의 장소점수:"+placeRating);
		System.out.println("수정하는 글의 도시코드:"+cityCode);
		
		Review rv = new Review(reviewNo, reviewWriter, placeId, reviewTitle, reviewContent, placeRating, 0, 0, null, cityCode); // 좋아요 , 조회수 초기화
		int result = new ReviewService().updateReview(rv); // 리뷰게시판 서버에 등록
		
		//리뷰포토테이블 부분		
		String a = "없음&";
		String b = "없음&";
		if(multiReq.getParameter("original-img-1")!=null) {
			a = multiReq.getParameter("original-img-1")+"&";
		}
		if(multiReq.getParameter("original-renamedimg-1")!=null) {
			b = multiReq.getParameter("original-renamedimg-1")+"&";
		}
		System.out.println("메인사진:"+a);
		System.out.println("메인사진:"+b);
		
		String test1 = "";
		String test2 = "";
		for(int i=2; i<11; i++) {
			if(multiReq.getParameter("original-img-"+i)!=null) {
				test1 += multiReq.getParameter("original-img-"+i);
				test2 += multiReq.getParameter("original-renamedimg-"+i);
			}
			else {
				test1 += "없음";
				test2 += "없음";
			}
			if(i<10) {
				test1 += "&";
				test2 += "&";
			}
		}
		System.out.println("나머지원래사진이름:"+test1);
		System.out.println("나머지원래사진이름:"+test2);
		
		
		String c = "없음&";
		String d = "없음&";
		if(multiReq.getOriginalFileName("reviewImg-1")!=null) {
			c = multiReq.getOriginalFileName("reviewImg-1")+"&";
		}
		if(multiReq.getFilesystemName("reviewImg-1")!=null) {
			d = multiReq.getFilesystemName("reviewImg-1")+"&";
		}
		System.out.println("새로추가한 대표사진:"+c);
		System.out.println("새로추가한 대표사진:"+d);
		
		String OrigianlreviewImg = "";
		String RenamedlreviewImg = "";
		for(int i=2; i<11; i++) {
			
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
		System.out.println("새로추가한 파일이름:"+OrigianlreviewImg);
		System.out.println("새로추가한 파일이름:"+RenamedlreviewImg);
		
		String finalOfile = "";
		String finalRfile = "";
		if(!c.equals("없음&")) {
			finalOfile = c + OrigianlreviewImg + "&" + test1;
			finalRfile = d + RenamedlreviewImg + "&" + test2;
		}
		else {
			finalOfile = a + OrigianlreviewImg + "&" + test1;
			finalRfile = b + RenamedlreviewImg + "&" + test2;
		}
		System.out.println("최종업데이트 사진파일:"+finalOfile);
		System.out.println("최종업데이트 사진파일:"+finalRfile);
		
		ReviewPhoto rp = new ReviewPhoto(0, reviewNo, 0, finalOfile, finalRfile);
		
		int deletePhoto = new ReviewService().deleteReviewPhoto(reviewNo); // 해당글의 리뷰사진 전부삭제
		int result_2 = new ReviewService().insertReviewPhoto(rp); // 리뷰포토테이블에 등록
		
		//장소테이블 부분
		Double inlat = Double.parseDouble(multiReq.getParameter("inlat")); // 위도
		Double inlng = Double.parseDouble(multiReq.getParameter("inlng")); // 경도
		String pname = multiReq.getParameter("pname"); // 장소이름
		String placeCode = multiReq.getParameter("placeNo"); // 장소분류
		

		System.out.println("위도:"+inlat);
		System.out.println("경도:"+inlng);
		System.out.println("장소이름:"+pname);
		System.out.println("장소아이디:"+placeId);
		System.out.println("장소분류코드:"+placeCode);
		
		//우선 해당 장소가 있는지 없는지 확인
		int check = new PlaceService().checkPlace(placeId); 
		int newResult = 0;
		//해당장소가 있으면,
		if(check>0) {
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
		
		//===============================================================
		
	
		String msg = "";
		String loc = "/review/MyreviewList?LoggedId="+reviewWriter;
//		String loc = "/";
		if(newResult>0) {
			msg = "리뷰게시글 수정 성공";
		}
		else {
			msg = "리뷰게시글 수정 실패";
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

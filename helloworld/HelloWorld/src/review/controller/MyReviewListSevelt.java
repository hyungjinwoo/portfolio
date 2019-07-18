package review.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import review.model.service.ReviewService;
import review.model.vo.Review;

/**
 * Servlet implementation class MyReviewListSevelt
 */
@WebServlet("/review/MyreviewList")
public class MyReviewListSevelt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String LoggedId = request.getParameter("LoggedId");
		System.out.println("마이리스트에서 로그인한 아이디:"+LoggedId);
		
		int cPage = 1;
		try {
		cPage = Integer.parseInt(request.getParameter("cPage"));
		} catch(NumberFormatException e) {
			
		}
		
		int numPerPage = 10;
		try {
		numPerPage = Integer.parseInt(request.getParameter("numPerPage"));
		} catch(NumberFormatException e) {
			
		}
		
		List<Review> ReviewList = new ReviewService().getMyList(cPage, numPerPage, LoggedId); // 해당 로그인한 사용자의 리뷰글 가져오기
		
		String str = "";
		for(int i=0; i<ReviewList.size(); i++) {
			String mainPhoto = new ReviewService().selectReviewListPhoto(ReviewList.get(i).getReviewNo()); // 해당글에 대한 대표사진만 가지고 오기
			str += mainPhoto;
			if(i<(ReviewList.size()-1)) {
				str += "&";
			}
		}
		System.out.println("MyreviewList에 보여질 메인사진 3개:"+str);
		
		List<String> photoList = new ArrayList<String>();
		String[] strArr = str.split("&");
		for(int i=0; i<strArr.length; i++) {
			System.out.println(strArr[i]);
			photoList.add(strArr[i]);
		}
		System.out.println("포토리스트 목록@Servlet:"+photoList);
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//전체컨텐츠수
		int totalContent = new ReviewService().countSelectUserAll(LoggedId);
		// 전체페이지수
		int totalPage = totalContent % numPerPage == 0 ? totalContent / numPerPage : (totalContent / numPerPage) + 1;
		// 페이지바 size
		int pageBarSize = 5;
		// pageNo
		int pageStart = ((cPage - 1) / pageBarSize) * pageBarSize + 1;
		int pageEnd = pageStart + pageBarSize - 1;
		int pageNo = pageStart;

		String pageBar = "";

		//[이전]
		if(pageNo == 1) {

		} else {
			pageBar += "<a href='"+request.getContextPath()+"/review/MyreviewList?LoggedId="+LoggedId+"&cPage="+(pageNo-1)+" '>[이전]</a>";
		}

		//[pageNo]
		while(pageNo<=pageEnd && pageNo <= totalPage) {
			if(cPage == pageNo) {
				pageBar += "<span class='cPage'>"+pageNo+"</span>";
			} else {
				pageBar += "<a href='"+request.getContextPath()+"/review/MyreviewList?LoggedId="+LoggedId+"&cPage="+(pageNo)+"'>"+pageNo+"</a>";
			}
			pageNo++;
		}

		//[다음]
		if(pageNo > totalPage) {

		} else {
			pageBar += "<a href='"+request.getContextPath()+"/review/MyreviewList?LoggedId="+LoggedId+"&cPage="+(pageNo)+"'>[다음]</a>";
		}

		request.setAttribute("pageBar", pageBar);
		request.setAttribute("cPage", cPage);
		request.setAttribute("Reviewlist", ReviewList);
		request.setAttribute("photoList", photoList);

		// view단 처리
		request.getRequestDispatcher("/WEB-INF/views/review/MyreviewList.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

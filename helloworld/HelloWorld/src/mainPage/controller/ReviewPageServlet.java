package mainPage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mainPage.model.service.MainPageService;
import mainPage.model.vo.Nation;
import review.model.service.ReviewService;
import review.model.vo.Review;

/**
 * Servlet implementation class ReviewPageServlet
 */
@WebServlet("/Review/detail")
public class ReviewPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		String cityName= request.getParameter("cityName");
		String nationName = request.getParameter("nationName");
		System.out.println("내가 선택한 도시이름:"+cityName);
		System.out.println("내가 선택한 나라이름:"+nationName);
		
		String cityCode = new ReviewService().selectCityCode(cityName); // 도시이름으로 넘어오는 도시의 도시코드 찾기
		List<Review> ReviewList = new ReviewService().selectReviewWithCityCode(cPage, numPerPage, cityCode); // 해당도시코드에 해당하는 리뷰글 검색
		
		String str = "";
		for(int i=0; i<ReviewList.size(); i++) {
			String mainPhoto = new ReviewService().selectReviewListPhoto(ReviewList.get(i).getReviewNo()); // 해당글에 대한 대표사진만 가지고 오기
			str += mainPhoto;
			if(i<(ReviewList.size()-1)) {
				str += "&";
			}
		}
		
		List<String> photoList = new ArrayList<String>();
		String[] strArr = str.split("&");
		for(int i=0; i<strArr.length; i++) {
			System.out.println(strArr[i]);
			photoList.add(strArr[i]);
		}
		System.out.println("포토리스트 목록@Servlet:"+photoList);
		
		// -------------------------- 페이징바 영역시작  ---------------------------//
		// 전체컨텐츠수
		int totalContent = new ReviewService().countCityCodeAll(cityCode);
		// 전체페이지수
		int totalPage = totalContent % numPerPage == 0 ? totalContent / numPerPage : (totalContent / numPerPage) + 1;
		// 페이지바 size
		int pageBarSize = 5;
		// pageNo
		int pageStart = ((cPage - 1) / pageBarSize) * pageBarSize + 1;
		int pageEnd = pageStart + pageBarSize - 1;
		int pageNo = pageStart;

		String pageBar = "";

		// [이전]
		if (pageNo == 1) {

		} else {
			pageBar += "<a href='" + request.getContextPath() + "/Review/detail?cityName="+cityName+"&nationName="+nationName+"&cPage=" + (pageNo - 1)
					+ "' >[이전]</a>";
		}

		// [pageNo]
		while (pageNo <= pageEnd && pageNo <= totalPage) {
			if (cPage == pageNo) {
				pageBar += "<span class='cPage'><strong>" + pageNo + "</strong></span>";
			} else {
				pageBar += "<a href='" + request.getContextPath() + "/Review/detail?cityName="+cityName+"&nationName="+nationName+"&cPage=" + (pageNo) + "'>"
						+ pageNo + "</a>";
			}
			pageNo++;
		}

		// [다음]
		if (pageNo > totalPage) {

		} else {
			pageBar += "<a href='" + request.getContextPath() + "/Review/detail?cityName="+cityName+"&nationName="+nationName+"&cPage=" + (pageNo) + "'>[다음]</a>";
		}

		request.setAttribute("pageBar", pageBar);
		request.setAttribute("cPage", cPage);
		request.setAttribute("Reviewlist", ReviewList);
		request.setAttribute("photoList", photoList);
		request.setAttribute("cityCode", cityCode);
		
		
		//날씨코드
		String wea_name = "";
		//환율코드
		String crc_code = "";
		String c_name ="";
		
		List<Nation> weaList = null;
		List<Nation> crcList = null;
		
		weaList = new MainPageService().getWea_Name(nationName);
		crcList = new MainPageService().getCrc_List(nationName);
		
		if(!weaList.isEmpty()){
			for(int i=0;i<weaList.size();i++){
				Nation n = weaList.get(i);
				wea_name= n.getWea_name();}}
		if(!crcList.isEmpty()){
			for(int i=0;i<crcList.size();i++){
				Nation n = crcList.get(i);
				crc_code= n.getCrc_code();
				c_name=n.getC_name();}}
		
		request.setAttribute("nationName", nationName);
		request.setAttribute("wea_name", wea_name);
		request.setAttribute("crc_code", crc_code);
		request.setAttribute("cityName", cityName);
		request.setAttribute("c_name",c_name);
		
		request.getRequestDispatcher("/WEB-INF/views/review/review_page.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

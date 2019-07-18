package review.controller;

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
import place.model.service.PlaceService;
import review.model.service.ReviewService;
import review.model.vo.Review;

/**
 * Servlet implementation class ReviewWithPlaceCodeServlet
 */
@WebServlet("/review/reviewListWithPlaceCode")
public class ReviewWithPlaceCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int searchType = Integer.parseInt(request.getParameter("searchType"));
		String cityCode = request.getParameter("cityCode");
		System.out.println("검색하려는 장소코드 확인@Servlet:"+searchType);
		System.out.println("검색하려는 도시코드 확인@Servlet:"+cityCode);
		
		String cityName= request.getParameter("cityName");
		String nationName = request.getParameter("nationName");
		System.out.println("내가 선택한 도시이름:"+cityName);
		System.out.println("내가 선택한 나라이름:"+nationName);
		
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
		
		List<Review> list = new ArrayList<Review>();
		List<String> photoList = new ArrayList<String>();
		String str = "";
		int totalContent = 0;
		switch(searchType) {
			case 1:
				list = new ReviewService().selectReviewWithPlaceCode(cityCode, searchType, cPage, numPerPage); // reviewNo 가져오기
				
				for(int i=0; i<list.size(); i++) {
					str += new ReviewService().selectReviewListPhoto(list.get(i).getReviewNo()); // 해당 reviewNo의 대표사진 가져오기
					if(i<list.size()-1) {
						str += "&";
					}
				}
				String[] strArr1 = str.split("&");
				for(int i=0; i<strArr1.length; i++) {
					photoList.add(strArr1[i]);
				}
				System.out.println("포토리스트 목록@Servlet:"+photoList);
				
				totalContent = new PlaceService().countWithPlaceCode(cityCode, searchType); // 해당 장소분류에 해당하는 총 글의 개수
				
				break;
			
			case 2:
				list = new ReviewService().selectReviewWithPlaceCode(cityCode, searchType, cPage, numPerPage); // reviewNo 가져오기
				
				for(int i=0; i<list.size(); i++) {
					str += new ReviewService().selectReviewListPhoto(list.get(i).getReviewNo()); // 해당 reviewNo의 대표사진 가져오기
					if(i<list.size()-1) {
						str += "&";
					}
				}
				String[] strArr2 = str.split("&");
				for(int i=0; i<strArr2.length; i++) {
					photoList.add(strArr2[i]);
				}
				System.out.println("포토리스트 목록@Servlet:"+photoList);
				
				totalContent = new PlaceService().countWithPlaceCode(cityCode, searchType); // 해당 장소분류에 해당하는 총 글의 개수
				break;
				
			case 3:
				list = new ReviewService().selectReviewWithPlaceCode(cityCode, searchType, cPage, numPerPage); // reviewNo 가져오기
				
				for(int i=0; i<list.size(); i++) {
					str += new ReviewService().selectReviewListPhoto(list.get(i).getReviewNo()); // 해당 reviewNo의 대표사진 가져오기
					if(i<list.size()-1) {
						str += "&";
					}
				}
				String[] strArr3 = str.split("&");
				for(int i=0; i<strArr3.length; i++) {
					photoList.add(strArr3[i]);
				}
				System.out.println("포토리스트 목록@Servlet:"+photoList);
				
				totalContent = new PlaceService().countWithPlaceCode(cityCode, searchType); // 해당 장소분류에 해당하는 총 글의 개수
				break;
				
			case 4:
				list = new ReviewService().selectReviewWithPlaceCode(cityCode, searchType, cPage, numPerPage); // reviewNo 가져오기
				
				for(int i=0; i<list.size(); i++) {
					str += new ReviewService().selectReviewListPhoto(list.get(i).getReviewNo()); // 해당 reviewNo의 대표사진 가져오기
					if(i<list.size()-1) {
						str += "&";
					}
				}
				String[] strArr4 = str.split("&");
				for(int i=0; i<strArr4.length; i++) {
					photoList.add(strArr4[i]);
				}
				System.out.println("포토리스트 목록@Servlet:"+photoList);
				
				totalContent = new PlaceService().countWithPlaceCode(cityCode, searchType); // 해당 장소분류에 해당하는 총 글의 개수
				break;
				
			case 5:
				list = new ReviewService().selectReviewWithPlaceCode(cityCode, searchType, cPage, numPerPage); // reviewNo 가져오기
				
				for(int i=0; i<list.size(); i++) {
					str += new ReviewService().selectReviewListPhoto(list.get(i).getReviewNo()); // 해당 reviewNo의 대표사진 가져오기
					if(i<list.size()-1) {
						str += "&";
					}
				}
				String[] strArr5 = str.split("&");
				for(int i=0; i<strArr5.length; i++) {
					photoList.add(strArr5[i]);
				}
				System.out.println("포토리스트 목록@Servlet:"+photoList);
				
				totalContent = new PlaceService().countWithPlaceCode(cityCode, searchType); // 해당 장소분류에 해당하는 총 글의 개수
				break;
		}
		
		//토탈페이지수
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
			pageBar += "<a href='" + request.getContextPath() + "/review/reviewListWithPlaceCode?" + "searchType=" + searchType
				    +"&cityCode="+ cityCode + "&cPage=" + (pageNo - 1) + "'>[이전]</a>";
		}

		// [pageNo]
		while (pageNo <= pageEnd && pageNo <= totalPage) {
			if (cPage == pageNo) {
				pageBar += "<span class='cPage'>" + pageNo + "</span>";
			} else {
				pageBar += "<a href='" + request.getContextPath() + "/review/reviewListWithPlaceCode?" + "searchType=" + searchType
						+"&cityCode="+cityCode+ "&cPage=" + (pageNo)+"'>" + pageNo + "</a>";
			}
			pageNo++;
		}

		// [다음]
		if (pageNo > totalPage) {

		} else {
			pageBar += "<a href='" + request.getContextPath() + "/review/reviewListWithPlaceCode?" + "searchType=" + searchType
					+"&cityCode="+cityCode+ "&cPage=" + (pageNo) + "'>[다음]</a>";
		}
		
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

		request.setAttribute("pageBar", pageBar);
		request.setAttribute("cPage", cPage);
		request.setAttribute("cityCode", cityCode);

		request.setAttribute("list", list);
		request.setAttribute("photoList", photoList);
		
		
		request.getRequestDispatcher("/WEB-INF/views/review/reviewListWithPlaceCode.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package mainPage.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mainPage.model.service.MainPageService;

/**
 * Servlet implementation class CityListServlet
 */
@WebServlet("/CityList")
public class CityListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//String nid = request.getParameter("nid");
		//전체 페이지수 구하기 
		//int totalContent = new MainPageService().selectcityCount(nid);
		//더보기할 게시물수 
		//int numPerPage = 2;
		//총페이지 수
		//int totalPage = (int)(Math.ceil(totalContent*1.0/numPerPage));
		
		//request.setAttribute("totalPage", totalPage);
		//request.getRequestDispatcher("/WEB-INF/views/nations/city_img.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

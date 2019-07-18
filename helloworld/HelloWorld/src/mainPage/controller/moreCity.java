package mainPage.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import mainPage.model.service.MainPageService;
import mainPage.model.vo.City;
import mainPage.model.vo.City_Img;

/**
 * Servlet implementation class moreCity
 */
@WebServlet("/moreCity")
public class moreCity extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nid = request.getParameter("nid");
		int cPage = Integer.parseInt(request.getParameter("cPage"));
		int numPerPage= 2;
		System.out.println("cPage="+cPage);
		
		List<City_Img> list = new MainPageService().selectcityListMore(nid, cPage,numPerPage);
		System.out.println("cityimgList at moreCity"+list);
		
		response.setContentType("application/json; charset=utf-8");
			new Gson().toJson( list,response.getWriter());	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

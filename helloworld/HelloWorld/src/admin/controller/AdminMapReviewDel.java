package admin.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.model.service.AdminService;

/**
 * Servlet implementation class AdminMapReviewDel
 */
@WebServlet("/admin/mapReviewDel")
public class AdminMapReviewDel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		int reveiwNo=Integer.parseInt(request.getParameter("reveiwNo"));
		
		int result = new AdminService().adminMapReviewDel(reveiwNo);
		
		
		String re = "";
		if(result>0) {
			re="성공";
		}else {
			re="실패";
		}
		
		System.out.println(re);
		
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().print(re);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import admin.model.service.AdminService;
import place.model.dao.PlaceDAO;
import place.model.vo.Place;
import review.model.vo.Review;



/**
 * Servlet implementation class adminQna
 */
@WebServlet("/admin/mapSearchReview")
public class AdminMapSearchReview extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String placeId = request.getParameter("placeId");
		
		List<Review> reviewList = new AdminService().mapSearchReview(placeId);

		response.setContentType("application/json; charset=utf-8");
		
		//Gson이 java객체을 json객체로 바로 변경해줌
		new Gson().toJson(reviewList,response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

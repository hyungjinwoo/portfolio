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



/**
 * Servlet implementation class adminQna
 */
@WebServlet("/admin/map")
public class AdminMap extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
		List<Place> placeList = new AdminService().placeList();

		
		JSONArray jsonArr = new JSONArray();// [{},{},{}]
		
		for(Place p:placeList) {
			JSONObject jsonPlace = new JSONObject();
			jsonPlace.put("lat",p.getPlaceX());
			jsonPlace.put("lng", p.getPlaceY());
			jsonArr.add(jsonPlace);
		}
		System.out.println(placeList);
		System.out.println(jsonArr);
			
		request.setAttribute("array", jsonArr);

		request.getRequestDispatcher("/WEB-INF/views/admin/map.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

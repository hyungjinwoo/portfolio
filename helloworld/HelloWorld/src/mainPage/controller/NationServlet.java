package mainPage.controller;

import java.io.IOException;
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
import mainPage.model.vo.Nation;


@WebServlet("/nationToCity")
public class NationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//검색어 파라미터 핸들링
		String nid = request.getParameter("nid");

		String wea_name = "";
		//환율
		String crc_code = "";
		//페이징
		/*int cPage = Integer.parseInt(request.getParameter("cPage"));
		int numPerPage = 2;*/
		List<City_Img> cityList = null;
		
		List<Nation> weaList = null;
		List<Nation> crcList = null;
		
		int totalContent = new MainPageService().selectcityCount(nid);
		int numPerPage = 2;
		int totalPage = (int)(Math.ceil(totalContent*1.0/numPerPage));
		
		cityList = new MainPageService().selectCities(nid);
		
		weaList = new MainPageService().getWea_Name(nid);
		crcList = new MainPageService().getCrc_List(nid);
		List<City> cityNameList = new MainPageService().selectCityNames(nid);
		/*List<City_Img> list = new MainPageService().selectcityListMore(nid, cPage,numPerPage);*/
		if(!weaList.isEmpty()){
			for(int i=0;i<weaList.size();i++){
				Nation n = weaList.get(i);
				wea_name= n.getWea_name();}}
		if(!crcList.isEmpty()){
			for(int i=0;i<crcList.size();i++){
				Nation n = crcList.get(i);
				crc_code= n.getCrc_code();}}
		String view = "";
		String loc = "";
		String msg = "";
		if(cityList!=null) {
			view = "/WEB-INF/views/nations/city_img.jsp";
			request.setAttribute("cityList", cityList);
			request.setAttribute("cityNameList", cityNameList);
			request.setAttribute("nationName", nid);
			request.setAttribute("wea_name", wea_name);
			request.setAttribute("crc_code", crc_code);
			request.setAttribute("totalPage", totalPage);
			
		}else {
			view = "/WEB-INF/views/common/msg.jsp";
			loc = "/";
			request.setAttribute("msg", msg);
			request.setAttribute("loc", loc);
		}
		 request.getRequestDispatcher(view).forward(request,response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

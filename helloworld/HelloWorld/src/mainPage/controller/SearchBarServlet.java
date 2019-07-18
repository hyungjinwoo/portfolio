package mainPage.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mainPage.model.service.MainPageService;
import mainPage.model.vo.Nation;

@WebServlet("/main/search")

// 자동 완성 기능 국가 부분
public class SearchBarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1. encoding
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/csv; charset=UTF-8");
				//2. parameter 
				String srchNation = request.getParameter("srchNation");
				//3. business logic
				List <Nation> list = new MainPageService().searchNation(srchNation);
				String name = "";
				System.out.println("list@/main/search"+list);
				/*System.out.println(list);*/
				for (int i=0; i<list.size();i++) {
					if(i!=0) name +=" ";
						name += list.get(i);}
				//4. view 단 처리
				request.setAttribute("name",name);
				response.setContentType("text/csv; charset=UTF-8");
				response.getWriter().append(name);
				
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

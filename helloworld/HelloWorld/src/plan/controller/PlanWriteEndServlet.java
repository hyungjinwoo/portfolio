package plan.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import plan.model.service.PlanService;
import plan.model.vo.PlaceList;
import plan.model.vo.Plan;

/**
 * Servlet implementation class PlanWriteEndServlet
 */
@WebServlet("/plan/planWriteEnd")
public class PlanWriteEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//인코딩
		request.setCharacterEncoding("UTF-8");
		//파라미터
		String planJson = request.getParameter("planJson");
		System.out.println(planJson);
		//문자형태로 parsing후 key value형식으로 담아둔다
		Map<String, Object> planMap = new Gson().fromJson(planJson, Map.class);
		//key값으로 벨류값 분류
		String planTitle = (String)planMap.get("planTitle");
		String memberId = (String)planMap.get("memberId");
		String startDay = (String)planMap.get("startDay");
		String endDay = (String)planMap.get("endDay");
				
		//날짜 데이터 포멧
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDayinput = null;
		Date endDayinput = null;
		try {
			startDayinput = dateFormat.parse(startDay);
			endDayinput = dateFormat.parse(endDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		java.sql.Date startDayOutput = new java.sql.Date(startDayinput.getTime());
		java.sql.Date endDayOutput = new java.sql.Date(endDayinput.getTime());
		
//		Plan(int planNo, String memberId, String planTitle, Date startDate, Date endDate, 
//		int planLevel, int readCount, int likeCount)
		Plan plan = new Plan(0, memberId, planTitle, startDayOutput, endDayOutput,
							 1, 0, 0);
		int planNo = new PlanService().inputPlanOutputPlanNo(plan);
		
		if(planNo<=0) return;
		
		List<Object> list = (List<Object>)planMap.get("planContent");
		System.out.println("list="+list);
		
//		System.out.println(planTitle);
//		System.out.println(memberId);
//		System.out.println(startDayOutput);
//		System.out.println(endDayOutput);
//		System.out.println(placeListObject);
//		placeListObject.size()일차 --0번지 1일차 1번지 2일차...
		//DB에 넣기전에 PlaceList를 분류
		int result = new PlanService().inputPlaceList(planNo, list);
		
		String msg = "";
		if(result>0) {
			msg = "일정 작성 완료!";
		}
		else {
			msg = "일정 작성 오류 발생! 지속적으로 반복될 경우 관리자에게 문의 주세요.";
		}
			
		String loc = "/plan/planList?memberId="+memberId;
				
		
		//view
		request.setAttribute("loc", loc);
		request.setAttribute("msg", msg);
		request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

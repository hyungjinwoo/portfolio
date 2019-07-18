package admin.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import admin.model.service.AdminService;
import common.UserListSingletone;
import member.model.vo.Member;
import notice.model.vo.Notice;
import quest.model.vo.Question;

/**
 * Servlet implementation class AdminMain
 */
@WebServlet("/admin")
public class AdminMain extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Map<Integer,Integer> chartAllList= new AdminService().chartAllCnt();
		Map<Integer,Integer> chartMemberList= new AdminService().chartMemberList();

	int currentUserCnt= UserListSingletone.getInstance().getUserList().size();
	
	int todayQnaCnt= new AdminService().todayQnaCnt();
	int totalQnaCnt= new AdminService().totalQnaCnt();
	int ingQnaCnt= new AdminService().ingQnaCnt();
	int todayReviewCnt= new AdminService().todayReviewCnt();
	int totalReviewCnt= new AdminService().totalReviewCnt();
	int totalMemberCnt= new AdminService().totalMemberCnt();
	int totalNoticeCnt= new AdminService().totalNoticeCnt();
	
	List<Question> qnaList = new AdminService().qnaList();
	List<Notice> noticeList = new AdminService().noticeList();
	
	
/*		[['day', 'Member', 'total'],
         ['6월 8일',  165,      938],
         ['6월 9일',  135,      1120]]*/
		JSONArray chartList = new JSONArray();
			JSONArray head = new JSONArray();
			head.add("day");
			head.add("member");
			head.add("total");
			chartList.add(head);
			
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			
			for(int i=0; i<chartAllList.size();i++) {
				JSONArray body = new JSONArray();
				body.add((Calendar.getInstance().get(Calendar.MONTH)+1)+"월 "+(Calendar.getInstance().get(Calendar.DATE)-(chartAllList.size()-1)+i)+"일");
				body.add(chartMemberList.get((Integer.parseInt(sdf.format(d))-chartAllList.size())+1+i));
				body.add(chartAllList.get((Integer.parseInt(sdf.format(d))-chartAllList.size())+1+i));
				chartList.add(body);
			}
			
			System.out.println("?????????:"+chartList);

		request.setAttribute("todayQnaCnt", todayQnaCnt);
		request.setAttribute("totalQnaCnt", totalQnaCnt);
		request.setAttribute("ingQnaCnt", ingQnaCnt);
		request.setAttribute("todayReviewCnt", todayReviewCnt);
		request.setAttribute("totalReviewCnt", totalReviewCnt);
		request.setAttribute("totalMemberCnt", totalMemberCnt);
		request.setAttribute("currentUserCnt", currentUserCnt);
		request.setAttribute("totalNoticeCnt", totalNoticeCnt);
		
		request.setAttribute("qnaList", qnaList);
		request.setAttribute("noticeList", noticeList);
		request.setAttribute("chartList", chartList);
		request.getRequestDispatcher("/WEB-INF/views/admin/adminMain.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package admin.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import admin.model.service.AdminService;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class AdminMemberModi
 */
@WebServlet("/admin/memberModi")
public class AdminMemberModi extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String memberId=request.getParameter("memberId");
		String memberName=request.getParameter("memberName");
		String birthday=request.getParameter("birthday");
		Date bir=Date.valueOf(birthday);
		
		String phone=request.getParameter("phone");
		String gender=request.getParameter("gender");

		
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberName(memberName);
		member.setBirth(bir);
		member.setTel(phone);
		member.setGender(gender);
		
		System.out.println(member);
		
		int result = new AdminService().memberUpdate(member);

		
		
		String msg = null;
		if(result>=1) msg ="성공";
		else msg ="실패";
		
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(msg);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

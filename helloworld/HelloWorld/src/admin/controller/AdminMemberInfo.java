package admin.controller;

import java.io.IOException;
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
@WebServlet("/admin/memberInfo")
public class AdminMemberInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String memberId=request.getParameter("memberId");
		
		Member member = new AdminService().selectOne(memberId);

		JSONObject jsonMember = new JSONObject();
		jsonMember.put("memberId", member.getMemberId());
		jsonMember.put("memberName", member.getMemberName());
		jsonMember.put("tel", member.getTel());
		jsonMember.put("birthday", member.getBirth().toString());
		jsonMember.put("gender", member.getGender());
		
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonMember);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

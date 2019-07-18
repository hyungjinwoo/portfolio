package event.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import event.model.service.EventService;
import event.model.vo.Event;
import event.model.vo.EventPhoto;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class EventViewServlet
 */
@WebServlet("/event/eventView")
public class EventViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int eventNo = Integer.parseInt(request.getParameter("eventNo"));
		
		Event e = new EventService().selectEventOne(eventNo);
		EventPhoto ep = new EventService().selectEventPhotoOne(eventNo);
		
		Member memberAdmin = new MemberService().selectOne("admin");
		
		request.setAttribute("memberAdmin", memberAdmin);
		request.setAttribute("e", e);
		request.setAttribute("ep", ep);
		request.getRequestDispatcher("/WEB-INF/views/event/eventView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

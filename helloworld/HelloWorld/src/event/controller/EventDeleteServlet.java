package event.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import event.model.service.EventService;
import event.model.vo.EventPhoto;

/**
 * Servlet implementation class EventDeleteServlet
 */
@WebServlet("/event/eventDelete")
public class EventDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int eventNo = Integer.parseInt(request.getParameter("eventNo"));
		
		EventPhoto ep = new EventService().selectEventPhotoOne(eventNo);
		System.out.println("지울파일확인@"+ep.getRenamedFileName());
		String saveDirectory = getServletContext().getRealPath("/upload/notice");
		boolean bool = new File(saveDirectory+"/"+ep.getRenamedFileName()).delete();
		
		int result = new EventService().deleteEvent(eventNo);
		
		int result_2 = new EventService().deleteEventPhoto(eventNo);
		
		String msg = "";
		String loc = "";
		if(result>0) {
			msg = "이벤트공지사항 삭제 성공";
			loc = "/event/eventList";
		} 
		else {
			msg = "이벤트공지사항 삭제 실패";
			loc = "/";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
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

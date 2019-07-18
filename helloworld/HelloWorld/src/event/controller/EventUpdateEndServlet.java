package event.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import common.HelloMVCFileRenamePolicy;
import event.model.service.EventService;
import event.model.vo.Event;
import event.model.vo.EventPhoto;

/**
 * Servlet implementation class EventUpdateEndServlet
 */
@WebServlet("/event/eventUpdateEnd")
public class EventUpdateEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!ServletFileUpload.isMultipartContent(request)) {
			request.setAttribute("msg", "파일 업로드 오류: 관리자에게 문의하세요");
			request.setAttribute("loc", "/");
			request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
			return;
		}
		
		String saveDirectory = getServletContext().getRealPath("/upload/notice");
		
		int maxPostSize = 1024*1024*10;
		
		FileRenamePolicy policy = new HelloMVCFileRenamePolicy();
		
		MultipartRequest multiReq = new MultipartRequest(request, saveDirectory, maxPostSize, "UTF-8", policy);
		
		///////////////////////////////////////////////////////////////////////////////////
		
		int eventNo = Integer.parseInt(multiReq.getParameter("eventNo"));
		String eventTitle = multiReq.getParameter("eventTitle");
		String eventContent = multiReq.getParameter("eventContent");
		
		Event e = new Event(eventNo,eventTitle,eventContent);
		int result = new EventService().updateEvent(e);
		
		///////////////////////////////////////////////////////////////////////////////////
		
		String delFile = multiReq.getParameter("delFile");
		
		//2-1. 수정파일 관련처리
		String originalFileNameNew = multiReq.getOriginalFileName("upFile");
		String renamedFileNameNew = multiReq.getFilesystemName("upFile");
		System.out.println("수정한 업로드 파일:"+originalFileNameNew+", 리네임:"+renamedFileNameNew);
				
		String originalFileName = multiReq.getParameter("original");
		String renamedFileName = multiReq.getParameter("renamed");
		System.out.println("기존업로드 파일:"+originalFileName+", 리네임:"+renamedFileName);
		
		if(delFile!=null) {
			//기존첨부파일만 삭제
			boolean bool = new File(saveDirectory+"/"+renamedFileName).delete();
			originalFileName = "";
			renamedFileName = "";
		}
		
		//원래 파일이 없는 게시물의 경우(파일추가x)
		if(originalFileNameNew == null && originalFileName.equals("null") ) {
			originalFileName = "";
			renamedFileName = "";
		}
		
		//업로드한 파일이 있는지 없는지 여부를 먼저 확인
		//새로업로드한 파일이 있는 경우,
		int result_2 = 0;
		EventPhoto ep = new EventPhoto();
		if(originalFileNameNew!=null) {
			//기존파일을 삭제해야함.
			String delDirectory = getServletContext().getRealPath("/upload/board")+"/"+renamedFileName;
			File file = new File(delDirectory);
			file.delete();
					
			ep.setEventNo(eventNo);
			ep.setOriginalFileName(originalFileNameNew);
			ep.setRenamedFileName(renamedFileNameNew);
			System.out.println("1번동작");
			result_2 = new EventService().updateEventPhoto(ep);
			System.out.println("이미지처리결과@"+result_2);
		}
		//파일변경없이 기존파일인 경우,
		else {
			ep.setEventNo(eventNo);
			ep.setOriginalFileName(originalFileName);
			ep.setRenamedFileName(renamedFileName);
			System.out.println("2번동작");
			result_2 = new EventService().updateEventPhoto(ep);
			System.out.println("이미지처리결과@"+result_2);
		}
		
		String msg = "";
		String loc = "";
		if(result>0) {
			msg = "이벤트공지사항 수정 성공";
			loc = "/event/eventList";
		}
		else {
			msg = "이벤트공지사항 수정 실패";
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

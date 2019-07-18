package notice.controller;

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
import notice.model.service.NoticeService;
import notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeFormEndServlet
 */
@WebServlet("/notice/noticeFormEnd")
public class NoticeFormEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		if(!ServletFileUpload.isMultipartContent(request)) {
			request.setAttribute("msg", "파일 업로드 오류: 관리자에게 문의하세요");
			request.setAttribute("loc", "/");
			request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
			return;
		}
		
		//a.파일저장경로
		String saveDirectory = getServletContext().getRealPath("/")+"upload/notice";
		System.out.println("saveDirectory="+saveDirectory);
		
		//b.파일최대용량:10mb
		//파일최대용량을 넘어서면 IOException을 던진다
		int maxPostSize = 1024*1024*10;
		
		//c.FileRenamePolicy객체 생성
		FileRenamePolicy policy = new HelloMVCFileRenamePolicy();
		
		//MultipartRequest객체생성
		MultipartRequest multiReq = new MultipartRequest(request, saveDirectory, maxPostSize, "UTF-8", policy);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////
		
		int sel = Integer.parseInt(multiReq.getParameter("sel"));
		
		if(sel == 0) {
		//sel값이 0일때 (공지사항일때.. 사진x)
			String noticeTitle = multiReq.getParameter("noticeTitle");
			String noticeContent = multiReq.getParameter("noticeContent");
			
			Notice n = new Notice(0, noticeTitle, noticeContent, sel, null);

			int result = new NoticeService().insertNotice(n);
			
			String msg = "";
			String loc = "";
			if(result>0) {
				msg = "공지사항 등록 성공";
				loc = "/notice/noticeList";
			}
			else {
				msg = "공지사항 등록 실패";
				loc = "/";
			}
			
			request.setAttribute("msg", msg);
			request.setAttribute("loc", loc);
			request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
		}
		//sel값이 1일때(이벤트공지사항일때..사진o)
		else {
			String noticeTitle = multiReq.getParameter("noticeTitle");
			String noticeContent = multiReq.getParameter("noticeContent");
			String originalFileName = multiReq.getOriginalFileName("upFile");
			String renamedFileName = multiReq.getFilesystemName("upFile");
			
			if(originalFileName == null) {
				originalFileName = "";
				renamedFileName = "";
			}
			
			Event e = new Event(0, noticeTitle, noticeContent);
			int eventNo = new EventService().insertEvent(e);
			System.out.println("이벤트 게시판 최신글번호:"+eventNo);
			
			EventPhoto ep = new EventPhoto(eventNo, originalFileName, renamedFileName);
			int result = new EventService().insertEventPhoto(ep);
			
			String msg = "";
			String loc = "";
			if(result>0) {
				msg = "이벤트사항 등록 성공";
				loc = "/event/eventList";
			}
			else {
				msg = "이벤트사항 등록 실패";
				loc = "/";
			}
			
			request.setAttribute("msg", msg);
			request.setAttribute("loc", loc);
			request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
			
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

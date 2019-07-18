package quest.controller;

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
import quest.model.service.QuestionService;
import quest.model.vo.Question;

/**
 * Servlet implementation class QuestFormEndServlet
 */
@WebServlet("/quest/questFormEnd")
public class QuestFormEndServlet extends HttpServlet {
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
		
		//a.파일저장경로
		String saveDirectory = getServletContext().getRealPath("/")+"upload/question";
		
		//b.파일최대용량:10mb
		//파일최대용량을 넘어서면 IOException을 던진다
		int maxPostSize = 1024*1024*10;
				
		//c.FileRenamePolicy객체 생성
		FileRenamePolicy policy = new HelloMVCFileRenamePolicy();
				
		//MultipartRequest객체생성
		MultipartRequest multiReq = new MultipartRequest(request, saveDirectory, maxPostSize, "UTF-8", policy);
		
		// -------------------------------------------------------------------------------//
		
		String questTitle = multiReq.getParameter("questTitle");
		String questWriter = multiReq.getParameter("questWriter");
		String questContent = multiReq.getParameter("questContent");
		int questLevel = Integer.parseInt(multiReq.getParameter("sel"));
		
		String originalFileName = multiReq.getOriginalFileName("upFile");
		String renamedFileName = multiReq.getFilesystemName("upFile");
		
		Question q = new Question(0, questWriter, questTitle, questContent, originalFileName, renamedFileName, null, questLevel, 1);
		int result = new QuestionService().insertQuestion(q);
		
		String msg = "";
		String loc = "";
		if(result>0) {
			msg = "문의글 등록 성공";
			loc = "/quest/questList";
		}
		else {
			msg = "문의글 등록 실패";
			loc = "/quest/questList";
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

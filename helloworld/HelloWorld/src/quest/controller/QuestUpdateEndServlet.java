package quest.controller;

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
import quest.model.service.QuestionService;
import quest.model.vo.Question;

/**
 * Servlet implementation class QuestUpdateEndServlet
 */
@WebServlet("/quest/questUpdateEnd")
public class QuestUpdateEndServlet extends HttpServlet {
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
		
		int questNo = Integer.parseInt(multiReq.getParameter("questNo"));
		String questWriter = multiReq.getParameter("questWriter");
		String questTitle = multiReq.getParameter("questTitle");
		String questContent = multiReq.getParameter("questContent");
		int questionLevel = Integer.parseInt(multiReq.getParameter("sel"));
		
		String delFile = multiReq.getParameter("delFile");
		System.out.println("첨부파일삭제여부:"+delFile);
		
		String newOriginalFile = multiReq.getOriginalFileName("upFile");
		String newRenamedFile = multiReq.getFilesystemName("upFile");
		
		String oldOriginalFile = multiReq.getParameter("oldOriginalFile");
		String oldRenamedFile = multiReq.getParameter("oldRenamedFile");
		
		System.out.println(newOriginalFile);
		System.out.println(oldOriginalFile);
		
		
		
		String OriginalfinalFile = "";
		String RenamedfinalFile = "";
		
		if(newOriginalFile == null && oldOriginalFile == null ) {
			OriginalfinalFile = "";
			RenamedfinalFile = "";	
		}
		
		if(delFile != null) {
			boolean bool = new File(saveDirectory+"/"+oldRenamedFile).delete();
			oldOriginalFile = "";
			oldRenamedFile = "";
		}
		
		if(newOriginalFile != null) {
			OriginalfinalFile = newOriginalFile;
			RenamedfinalFile = newRenamedFile;
		}
		else {
			OriginalfinalFile = oldOriginalFile;
			RenamedfinalFile = oldRenamedFile;
		}
		System.out.println("최종저장사진1"+OriginalfinalFile);
		System.out.println("최종저장사진2"+RenamedfinalFile);
		
		Question q = new Question(questNo,questWriter,questTitle,questContent,OriginalfinalFile,RenamedfinalFile,null,questionLevel,1);
		
		int result = new QuestionService().updateQuestion(q);
		
		String msg = "";
		String loc = "";
		if(result>0) {
			msg = "문의글 수정 성공";
			loc = "/quest/questList";
		}
		else {
			msg = "문의글 수정 실패";
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

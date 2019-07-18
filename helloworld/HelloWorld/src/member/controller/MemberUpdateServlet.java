package member.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import common.HelloWorldFileRenamePolicy;
import member.model.service.MemberService;
import member.model.vo.Member;

@WebServlet("/member/memberUpdate")
public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String saveDirectory = getServletContext().getRealPath("/")+"upload/member/profile";
		//여기에 사용자가 업로드한 파일을 담을 것
		System.out.println("saveDirectory="+saveDirectory);
		
		//b. maxPostSize: 파일 최대 용량: 10mb
		//파일 최대 용량을 넘어서면 IOException 던진다
		int maxPostSize = 1024*1024*10;
		//c. FileRenamePolicy 객체 생성
		//FileRenamePolicy fileRenamePolicy = new DefaultFileRenamePolicy();
		//중복 파일에 대해서 넘버링을 해 줌
		//textFile.txt가 두 개라면 textFile1.txt, textFile2.txt 이런 식으로 넘버링. 안 덮어써지게.
		FileRenamePolicy policy = new HelloWorldFileRenamePolicy();
		
		//MultipartRequest 객체 생성
		MultipartRequest multiReq = new MultipartRequest(request, 
														 saveDirectory, 
														 maxPostSize,
														 "UTF-8",
														 policy);
		
		
		String memberId = multiReq.getParameter("memberId_");
		String question = multiReq.getParameter("question");
		String answer = multiReq.getParameter("answer").replaceAll(" ", "").replaceAll("\\p{Z}", "").replaceAll(" ", "").replaceAll("\\p{Z}", "");
		String tel = multiReq.getParameter("tel");
		String [] interests = multiReq.getParameterValues("interest");
		String interest = "";
		if(interests!=null) {
			interest = String.join(",", interests);			
		}
		
		String renamedImgName = "";
		String originalImgName = "";
		
		//0: X 누르고 기본 이미지
		//1: X 누른 적 없음 - 1)원래 기본이거나 2)원래 사진이 있거나
		String noneProfileCheck = multiReq.getParameter("noneProfileCheck");
		
		String renamedImgNameOld = multiReq.getParameter("renamedImgNameOld");
		String originalImgNameOld = multiReq.getParameter("originalImgNameOld");
		
		System.out.println("renamedImgNameOld=="+renamedImgNameOld);
		System.out.println("originalImgNameOld=="+originalImgNameOld);
		if(multiReq.getFilesystemName("profile")==null &&
				multiReq.getOriginalFileName("profile")==null &&
				"0".equals(noneProfileCheck)) {
			//파일에 아무것도 없고 x 눌러서 기본 이미지 됨
			renamedImgName = "nonProfile.png";
			originalImgName = "nonProfile.png";
			
			if(!"nonProfile.png".equals(renamedImgNameOld)) {
				File deleteFile = new File(saveDirectory+"/"+ renamedImgNameOld);
				boolean bool = deleteFile.delete();
				System.out.println(bool?"파일 삭제 성공!":"파일 삭제 실패!");
			}
			
		}
		else if(multiReq.getFilesystemName("profile")==null &&
				multiReq.getOriginalFileName("profile")==null) {
			//파일 없음 (사진 수정 안 했음)
			renamedImgName = renamedImgNameOld;
			originalImgName = originalImgNameOld;
		}
		else {
			//사진 새로 업로드함
			renamedImgName = multiReq.getFilesystemName("profile");
			originalImgName = multiReq.getOriginalFileName("profile");			
			if(!"nonProfile.png".equals(renamedImgNameOld)) {
				File deleteFile = new File(saveDirectory+"/"+ renamedImgNameOld);
				boolean bool = deleteFile.delete();
				System.out.println(bool?"파일 삭제 성공!":"파일 삭제 실패!");
			}
		}
		
		System.out.println("renamedImgName=="+renamedImgName);
		System.out.println("originalImgName=="+originalImgName);
		
		Member m = new Member();
		m.setMemberId(memberId);
		m.setQuestion(question);
		m.setAnswer(answer);
		m.setTel(tel);
		m.setInterest(interest);
		
		m.setRenamedImgName(renamedImgName);
		m.setOriginalImgName(originalImgName);
		
		System.out.println("업데이트멤버=="+m);
		
		int result = new MemberService().updateMember(m);
		
		String msg = "";
		String loc = "/member/memberMyPage?memberId="+memberId;
		String view = "/WEB-INF/views/common/msg.jsp";
		
		if(result>0) {
			msg = "정보 수정 성공!";
			/*HttpSession session = request.getSession();
			session.setAttribute("memberLoggedIn", new MemberService().selectOne(memberId));*/
		}
		else {
			msg = "정보 수정 실패!";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);

		request.getRequestDispatcher(view).forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

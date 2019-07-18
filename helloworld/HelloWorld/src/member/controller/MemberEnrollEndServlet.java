package member.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import common.HelloWorldFileRenamePolicy;
import member.model.service.MemberService;
import member.model.vo.Member;

@WebServlet("/member/memberEnrollEnd")
public class MemberEnrollEndServlet extends HttpServlet {
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
		
		//인코딩
		request.setCharacterEncoding("UTF-8");
		//파라미터 핸들링
		String memberId = multiReq.getParameter("memberId_");
		String memberName = multiReq.getParameter("memberName");
		String password = multiReq.getParameter("password_");
		String question = multiReq.getParameter("question");
		String answer = multiReq.getParameter("answer").replaceAll(" ", "").replaceAll("\\p{Z}", "");
		String gender = multiReq.getParameter("gender");
		String birth = multiReq.getParameter("birth");
		String tel = multiReq.getParameter("tel");
		String [] interests = multiReq.getParameterValues("interest");
		String interest = "";
		if(interests!=null) {
			interest = String.join(",", interests);			
		}
		
		//업무로직
		
		String renamedImgName = "";
		String originalImgName = "";
		
		//업로드한 프로필 있으면 그걸로, 없으면 기본 이미지 지정
		if(multiReq.getFilesystemName("profile")==null &&
				multiReq.getOriginalFileName("profile")==null) {
			renamedImgName = "nonProfile.png";
			originalImgName = "nonProfile.png";
			
		}
		else{
			renamedImgName = multiReq.getFilesystemName("profile");
			originalImgName = multiReq.getOriginalFileName("profile");
		}
		
		//비밀번호 암호화
		String encPwd = null;
		//암호화객체 생성(sha-512)
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		//전달받은 문자열 password를 byte[]로 변환
		byte[] bytes = password.getBytes(Charset.forName("UTF-8"));
		md.update(bytes);
		//java.util.Base64 인코더를 이용해서 암호화된 바이트 배열을 인코딩
		//문자열로 리턴
		encPwd = Base64.getEncoder().encodeToString(md.digest());
				//바이트 배열을 문자열로 바꿔 줌
				//digest() 이용해서 바이트배열 리턴 가능
		System.out.println("암호화된 비밀번호@END서블릿::"+encPwd);
		
		
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberName(memberName);
		member.setPassword(encPwd);
		member.setQuestion(question);
		member.setAnswer(answer);
		member.setGender(gender);
		
		Date birthday = java.sql.Date.valueOf(birth);
		member.setBirth(birthday);
		member.setTel(tel);
		member.setInterest(interest);
		member.setRenamedImgName(renamedImgName);
		member.setOriginalImgName(originalImgName);
		
		System.out.println(member);
		
		int result = new MemberService().insertMember(member);
		
		//view단
		String msg = "";
		String loc = "";
		String view = "/WEB-INF/views/common/msg.jsp";
		
		if(result>0) {
			msg = "회원가입 성공!";
			loc = "/";
		}
		else {
			msg = "회원가입 실패!";
			loc = "/member/memberEnroll";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);

		request.getRequestDispatcher(view).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

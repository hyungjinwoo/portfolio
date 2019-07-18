package member.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import member.model.service.MemberService;
import member.model.vo.Member;

@WebServlet(urlPatterns= {"/member/login"},
			name = "MemberLoginServlet")
public class MemberLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String memberId = request.getParameter("memberId");
		String password = request.getParameter("password");
		//아이디 저장 관련 처리
		String saveId = request.getParameter("saveId"); 
		//checkbox 전송 결과가 있다면, value 지정값이 없으므로 "on" 전송됨.
		
		Member m = new Member();
		m.setMemberId(memberId);
		m.setPassword(password);
		
		//아이디 없음 -1
		//비밀번호 틀림 0
		//로그인 성공 1
		int result = new MemberService().loginCheck(m);
		
		//header 정보 열람
		Map<String, String> headerMap = new HashMap<>();
		Enumeration<String> headerNames = request.getHeaderNames();
		//사용자가 요청한 헤더에 뭐가 왔는지 다 볼 것
		while(headerNames.hasMoreElements()) {
			String name = headerNames.nextElement();
			String value = request.getHeader(name); //key값으로 가져오는 메소드
			headerMap.put(name, value); //헤더맵에 다 집어넣기
			//System.out.println("헤더맵:::::"+name+"="+value);
		}
		
		//System.out.println("headerMap@loginServlet="+headerMap);
		
		String referer = request.getHeader("Referer"); //키값은 대소문자 구분하니 정확하게 입력
		String origin = request.getHeader("Origin");
		String url = request.getRequestURL().toString(); //리턴값이 스트링버퍼이므로 string으로 변환
		String uri = request.getRequestURI();
		
		String view = "";
		String msg = "";
		String loc = referer.replace(origin+request.getContextPath(), "");
		String visit = "first";
		//로그인 성공
		if(result == 1) {
			view = "/index.jsp";
			//로그인에 성공한 회원 정보 가져오기
			Member memberLoggedIn = new MemberService().selectOne(memberId);
		
			//session 객체 가져오기
			//세션이 있다면 해당 세션을 리턴하고 없다면 새로 생성해서 리턴
			HttpSession session = request.getSession();
			session.setAttribute("memberLoggedIn", memberLoggedIn); //세션도 속성 전달 가능
			
			//자바코드에서 유효시간 설정 가능(초 단위): web.xml보다 우선 순위가 높다
			session.setMaxInactiveInterval(1800);
			
			//로그인 성공시 아이디 저장 쿠키 처리
			if(saveId != null) {
				Cookie c = new Cookie("saveId", memberId); //키 밸류 쌍으로 저장
				c.setMaxAge(7*24*60*60); //초 단위로 유효기간 설정
				c.setPath("/"); //현재 도메인 전역에서 사용하겠다는 의미
				response.addCookie(c); //사용자에게 응답 보낼 때 이 쿠키값 가지고 가서 클라이언트에게 이 쿠키 저장
			} 
			else {
				//쿠키 는 별도의 삭제 메소드 없이 maxAge값을 0으로 해서 바로 삭제하게 만듦
				Cookie c = new Cookie("saveId", memberId);
				c.setMaxAge(0); //즉시 삭제. 음수값 사용시 현재 세션 객체가 유효한 동안만 사용하겠다는 의미
				c.setPath("/");
				response.addCookie(c);
			}
			
			request.setAttribute("visit", visit);
			System.out.println("referer="+referer);
			request.getRequestDispatcher(loc).forward(request, response);
			//response.sendRedirect(referer); 
			
		}
		//로그인 실패
		else {
			view = "/WEB-INF/views/common/msg.jsp";
			//비밀번호 불일치
			if(result == 0){
				msg = "비밀번호가 틀렸습니다.";
				
			} 
			//존재하지 않는 아이디
			else {
				msg = "존재하지 않는 아이디입니다.";
			}
			
			request.setAttribute("visit", visit);
			request.setAttribute("msg", msg);
			request.setAttribute("loc", loc);
			request.getRequestDispatcher(view).forward(request, response);
		}
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

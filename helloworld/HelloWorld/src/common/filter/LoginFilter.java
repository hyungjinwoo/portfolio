package common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import member.model.vo.Member;

@WebFilter(servletNames = { "MemberMyPageServlet" })
public class LoginFilter implements Filter {

    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}


	public void doFilter(ServletRequest request, 
						 ServletResponse response, 
						 FilterChain chain) throws IOException, ServletException {
		//현재 로그인한 사용자와 요청 사용자 비교
		//로그인한 사용자 가져오기. 
		//ServletRequest는 getSession메소드 없으므로 형변환 해 줘야 함
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpSession session = httpRequest.getSession();
		Member memberLoggedIn = (Member)session.getAttribute("memberLoggedIn");
		
		String reqMemberId = httpRequest.getParameter("memberId");
		
		//비교 
		if(!"admin".equals(memberLoggedIn.getMemberId())) {
			if(memberLoggedIn == null ||
			    reqMemberId == null ||
			   !reqMemberId.equals(memberLoggedIn.getMemberId())) {
				request.setAttribute("msg", "잘못된 경로로 접근하셨습니다.");
				request.setAttribute("loc", "/"); //인덱스로 보냄
				request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp")
					   .forward(request, response);
				return; 
			}
		}
		
		

		// pass the request along the filter chain
		//다음 필터가 있다면, 해당 필터의 doFilter메소드를 호출,
		//없다면, servlet 객체의 service()를 호출한다.
		chain.doFilter(request, response);
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

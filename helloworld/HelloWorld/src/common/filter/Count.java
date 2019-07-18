package common.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import admin.model.service.AdminService;
import member.model.vo.Member;

/**
 * Servlet Filter implementation class AdminFilter
 */
@WebFilter("/*")
public class Count implements Filter {

    /**
     * Default constructor. 
     */
    public Count() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		String memberId=null;
		String JSESSIONID=null;
		
		HttpSession session = ((HttpServletRequest)request).getSession();
		Member memberLoggedIn = (Member) session.getAttribute("memberLoggedIn");
			if(memberLoggedIn!=null) {
				memberId = memberLoggedIn.getMemberId();
			}
			
		Cookie[] cookies = ((HttpServletRequest)request).getCookies();            // 요청정보로부터 쿠키를 가져온다.
		if(cookies!=null) {
			for(int i = 0 ; i<cookies.length; i++){                            // 쿠키 배열을 반복문으로 돌린다.
				//System.out.println(i + "번째 쿠키 이름 : " + cookies[i].getName());            // 쿠키의 이름을 가져온다.
				//System.out.println(i + "번째 쿠키에 설정된 값 : " + cookies[i].getValue());    // 쿠키의 값을 가져온다.
				
				if(cookies[i].getName().equals("JSESSIONID")) {
					JSESSIONID=cookies[i].getValue();
				}
			}
		}
		
		
		if(memberId==null&&JSESSIONID!=null) {
			int chkResult = new AdminService().chkJid(JSESSIONID);
			if(chkResult==0) {
				int result = new AdminService().inJid(JSESSIONID);
			}
		}
		if(memberId!=null&&JSESSIONID!=null){
			int chkResult = new AdminService().chkMemberId(memberId,JSESSIONID);
			if(chkResult==0) {
				int result = new AdminService().inMemberId(memberId,JSESSIONID);
			}
		}
		
		
		//header정보열람
/*		Map<String,String> headerMap = new HashMap<>();
		Enumeration<String> headerNames = ((HttpServletRequest)request).getHeaderNames();
		while(headerNames.hasMoreElements()) {
			String name = headerNames.nextElement();
			String value = ((HttpServletRequest)request).getHeader(name);
			headerMap.put(name, value);
		}*/

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

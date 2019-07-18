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

import common.wrapper.EncryptWrapper;

/**
 * Servlet Filter implementation class EncryptFilter
 */
@WebFilter(
		servletNames = { 
				"MemberLoginServlet",
				"UpdatePasswordEndServlet",
				"UpdatePasswordAfterFindEndServlet",
				"DeleteMemberServlet"				
		})
public class EncryptFilter implements Filter {

    /**
     * Default constructor. 
     */
    public EncryptFilter() {
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
		//여기서 바꿔치기
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		
		//암호화 Wrapper 객체 생성
		EncryptWrapper encWrapper = new EncryptWrapper(httpRequest); //생성자 만든 거 호출한 것
		System.out.println("password 암호화 처리@EncryptWrapper");
		
		// pass the request along the filter chain
//		chain.doFilter(request, response); 
		//request 객체를 전달하는 게 아니라 encWrapper를 전달
		//자식 객체라서 바꿔 넣는 게 가능
		//EncryptWrapper가 HttpServletRequestWrapper를 상속
		//HttpServletRequestWrapper는 HttpServletRequest를 상속
		//HttpServletRequest는 ServletRequest를 상속 (인터페이스끼리니까 상속)
		//그래서 부모타입으로 담아 줄 수 있는 것. 다형성!!!!!!
		chain.doFilter(encWrapper, response);
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
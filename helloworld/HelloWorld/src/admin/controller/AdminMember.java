package admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.model.service.AdminService;
import member.model.vo.Member;

/**
 * Servlet implementation class adminQna
 */
@WebServlet("/admin/member")
public class AdminMember extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		int cPage=0;
		int numPerPage=0;
		System.out.println(request.getParameter("cPage"));
		
		if(request.getParameter("cPage")==null) {
			cPage=1;
			numPerPage=10;
		}else {
			try {
				cPage = Integer.parseInt(request.getParameter("cPage"));
			} catch (Exception e) {
			}
			
			try {
				numPerPage = Integer.parseInt(request.getParameter("numPerPage"));
			} catch (Exception e) {
			}		
		}
		
		
		List<Member> memberList = new AdminService().memberList(cPage,numPerPage);
		
		//totalContent: 전체 컨텐츠 수
			int totalContent = new AdminService().memberAllCount();
			
		//totalPage: 전체페이지수 
			int totalPage = (int)Math.ceil(((double)totalContent/numPerPage));

		//pageBarSize: 페이지바에 표시할 페이지수
			int pageBarSize =5;
			//PageNO : 페이지 번호
			int pageStart = ((cPage-1)/pageBarSize)*pageBarSize+1;
			int pageEnd = pageStart+pageBarSize-1;
			int pageNo = pageStart;
			
			String pageBar="";
			if(pageNo==1) {
			}else {
				//pageBar+= "<a href='"+request.getContextPath()+"/admin/member?cPage="+(pageNo-1)+"&numPerPage="+numPerPage+"'>[이전]</a>";
				//pageBar+= "<i cPage="+(pageNo-1)+" numPerPage="+numPerPage+" class='fas fa-angle-left'>[이전]</i>";
				pageBar+= "<i bName='all' cPage="+(pageNo-1)+" numPerPage="+numPerPage+" class='exbtn fas fa-chevron-circle-left'></i>";
			}
			
			while(pageNo<=pageEnd && pageNo<=totalPage) {
				if(cPage==pageNo) {
					//pageBar +="<span class='cPage'>"+pageNo+"</span>";
					//pageBar +="<i id='currentP' class='cPage fas fa-circle' cPage="+(pageNo)+" numPerPage="+numPerPage+"></i>";
					pageBar +="<p bName='all' id='currentP' class='cPage' cPage="+(pageNo)+" numPerPage="+numPerPage+">"+pageNo+"</p>";
				}else {
					//pageBar += "<a href='"+request.getContextPath()+"/admin/member?cPage="+(pageNo)+"&numPerPage="+numPerPage+"'>"+pageNo+"</a>";
					//pageBar += "<i class='exbtn far fa-circle' cPage="+(pageNo)+" numPerPage="+numPerPage+">"+pageNo+"</i>";
					pageBar += "<p bName='all' class='exbtn' cPage="+(pageNo)+" numPerPage="+numPerPage+">"+pageNo+"</p>";
				}
				pageNo++;
			}
			
			if(pageNo>totalPage) {
			}else {
				//pageBar+=" <a href='"+request.getContextPath()+"/admin/member?cPage="+pageNo+"&numPerPage="+numPerPage+"'>[다음]</a> ";
				//pageBar+=" <i cPage="+pageNo+" numPerPage="+numPerPage+" class='fas fa-angle-right'></i> ";
				pageBar+=" <i bName='all' cPage="+(pageNo)+" numPerPage="+numPerPage+" class='exbtn fas fa-chevron-circle-right'></i>";
				//pageBar+=" <i cPage="+pageNo+" numPerPage="+numPerPage+" class='fas fa-angle-double-right'></i>";
			}
	
	//4. view
	request.setAttribute("memberList", memberList);
	request.setAttribute("cPage", cPage);
	request.setAttribute("numPerPage", numPerPage);
	request.setAttribute("pageBar", pageBar);
	request.getRequestDispatcher("/WEB-INF/views/admin/member.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

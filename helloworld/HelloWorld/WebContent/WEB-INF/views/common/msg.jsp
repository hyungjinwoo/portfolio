<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int result = 0;
	String msg = (String)request.getAttribute("msg");
	String loc = request.getContextPath()+(String)request.getAttribute("loc");
	String script = (String)request.getAttribute("script");
	
	if(request.getAttribute("result") != null){
		result = (int)request.getAttribute("result");
		System.out.println(result);
	}
%>

<script>
alert("<%=msg%>");

if(<%=result%> > 0){
	self.close();
}

<%=script!=null?script:""%>

location.href = "<%=loc%>";
</script>
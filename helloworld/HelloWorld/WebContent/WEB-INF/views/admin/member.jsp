<%@page import="member.model.vo.Member"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<%
	List<Member> memberList= (List<Member>)request.getAttribute("memberList");

	System.out.println("member.jsp="+memberList);
	String pageBar = (String)request.getAttribute("pageBar");
	
	int cPage = (Integer)request.getAttribute("cPage");
	int numPerPage = (Integer)request.getAttribute("numPerPage"); 
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보수정</title>
<script
  src="https://code.jquery.com/jquery-3.4.0.min.js"
  integrity="sha256-BJeo0qm959uMBGb65z40ejJYGSgR7REI4+CW1fNKwOg="
  crossorigin="anonymous"></script>
  
<script src="<%=request.getContextPath()%>/js/admin/member.js"></script>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/admin/member.css">
</head>
<body>

<div id="memberFind">
	<div id="memberFindInput">
	
	<!--  -->
			<label for="leave"></label>
				<input type="radio" name="leave" id="nonleave" value="nonleave" checked>
				<label for="nonleave">현재 회원</label>
				<input type="radio" name="leave" id="leave" value="leave">
				<label for="leave">탈퇴 회원</label>
			<br>
	<!--  -->
	
			<label for="memberName"></label>
			<input type="text" name="memberName" id="memberName" placeholder="name"/>
			<br />
			<label for="findmemberId"></label>
			<input type="text" name="findmemberId" id="findmemberId" placeholder="id"/>
			<br />
			<label for="phone"></label>
			<input type="text" name="phone" id="phone" placeholder="phone"/>
			<br />
			<label for="gender"></label>
				<input type="radio" name="gender" id="gender_m" value="M">
				<label for="gender_m">Man</label>
				<input type="radio" name="gender" id="gender_f" value="F">
				<label for="gender_f">Women</label>
			<br />
			<button id="findBtn">Search</button>
	</div>
	

	<div id="memberFindBody">
		
		<div id="findResult">
			<div id="memberFindTable">
		 		<table id="memberFindTablebo">
					<tr id="memberFindTableHead">
						<th>이름</th>
						<th>아이디</th>
						<th>전화번호</th>
						<th>성별</th>
					</tr>
					
					<%if(memberList.size()<=0){
						%>
						<tr >
							<td colspan="4">회원이 없습니다.</td>
						</tr>
					<%
					}else{
					%>
						<%for(Member m:memberList){%>
						<tr onclick="memberInfoModi('<%=m.getMemberId() %>')" >
							<td><%=m.getMemberName() %></td>
							<td><%=m.getMemberId() %></td>
							<td>0<%=m.getTel() %></td>
							<td><%=m.getGender()%></td>
						</tr>
						<%}%>
					<%}%>
				</table>
			</div>
			<div id="pageBar"><%=pageBar %></div>
			
		</div>
		
	</div>
</div>
<script>
function memberInfoModi(a){

	 location.href = '<%=request.getContextPath()%>/member/memberMyPage?memberId='+a;
}
 </script>
 
</body>
</html>
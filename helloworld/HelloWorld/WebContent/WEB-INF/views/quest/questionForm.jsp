<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/question.css" />
<script src="<%=request.getContextPath()%>/js/jquery-3.4.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<title>문의사항 글쓰기</title>
<section class="question-container">
    
    <h1>question write</h1>
        
        <form  action="<%=request.getContextPath()%>/quest/questFormEnd" method="post" enctype="multipart/form-data">
            <table id="questionForm-table">
                <tr>
                    <th><div>제목</div></th>
                    <td>
                        <input type="hidden" name="questWriter" value="<%=memberLoggedIn.getMemberId()%>"/>
                        <input type="text" id="questTitle" name="questTitle"/>
                    </td>
                </tr>
                <tr>
                    <th><div>파일첨부</div></th>
                    <td id="questionForm-upfile">
                        <input type="file" name="upFile" id="upFile" />     
                    </td>
                </tr>
                <tr>
                    <th><div>내용</div></th>
                    <td>
                        <textarea name="questContent" id="questContent"></textarea>             
                    </td>
                </tr>
                <tr>
                    <th><div>공개여부</div></th>
                    <td>
                        <select name="sel" id="sel">
                            <option value="1">공개</option>
                            <option value="0">비공개</option>
                        </select>             
                    </td>
                </tr>
                <tr>
                    <th colspan="2">
                        <input type="submit" value="등록" />                
                        <input type="button" value="목록보기" onclick="location.href='<%=request.getContextPath()%>/quest/questList'"/>               
                    </th>
                </tr>
            </table>
        </form>   
</section>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
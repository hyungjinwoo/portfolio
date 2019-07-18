package common.chat.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import common.chat.model.service.ChatService;
import common.chat.model.vo.Chat;

/**
 * Servlet implementation class ChatLogListServlet
 */
@WebServlet("/chat/chatLogList.chat")
public class ChatLogListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//파라미터
		String userId = request.getParameter("userId");
		
		//userId에 해당하는 채팅로그 가져오기
		List<Chat> chatList = new ChatService().selectChatListById(userId);
		
		//응답객체에 json문자열로 출력
		response.setContentType("application/json; charset=utf-8");
		new Gson().toJson(chatList, response.getWriter());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

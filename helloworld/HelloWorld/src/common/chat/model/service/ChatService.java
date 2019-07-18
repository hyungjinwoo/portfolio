package common.chat.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import common.chat.model.dao.ChatDAO;
import common.chat.model.vo.Chat;

public class ChatService {

	public int insertChatList(List<Chat> chatList) {
		Connection conn = getConnection();
		int result = new ChatDAO().insertChatList(conn, chatList);
		
		if(result>0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}

	public List<Chat> selectChatListById(String userId) {
		Connection conn = getConnection();
		List<Chat> chatList = new ChatDAO().selectChatListById(conn, userId);
		
		close(conn);
		
		return chatList;
	}

	

	

}

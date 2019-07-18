package common.chat.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import common.chat.model.vo.Chat;

public class ChatDAO {
	private Properties prop = new Properties();
	
	public ChatDAO() {
		String fileName = getClass()
						 .getResource("/sql/chat/chat-query.properties")
						 .getPath();
//		MemberDAO의 클래스 객체로부터 getResource 메소드 호출. getPath는 절대경로를 호출하기 위해
		
		try {
			prop.load(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int insertChatList(Connection conn, List<Chat> chatList) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertChatList");
		
		//Set에 저장된 채팅로그를 DB에 입력시킨다
		for(Chat chat : chatList) {
			String sender = chat.getSender();
			String reciver = chat.getReciver();
			String content = chat.getContent();
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, sender);
				pstmt.setString(2, reciver);
				pstmt.setString(3, content);
				
				result = pstmt.executeUpdate();
				
				if(result<=0) System.out.println("채팅로그저장 실패@ChatDAO="+chat);
				else System.out.println("채팅로그저장 성공@ChatDAO="+chat);
				
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				close(pstmt);
			}
			
		}
		return result;
	}

	public List<Chat> selectChatListById(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		List<Chat> chatList = new ArrayList<>();
		ResultSet rset = null;
		String sql = prop.getProperty("selectChatListById");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userId);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Chat chat = new Chat();
				
				chat.setSender(rset.getString("sender"));
				chat.setReciver(rset.getString("reciver"));
				chat.setContent(rset.getString("chat_content"));
				
				chatList.add(chat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}
		
		
		return chatList;
	}

	



}

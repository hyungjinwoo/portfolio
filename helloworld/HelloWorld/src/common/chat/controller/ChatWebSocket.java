package common.chat.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

import common.chat.model.service.ChatService;
import common.chat.model.vo.Chat;

//tomcat이 찾을수 있는 주석 @serverEndpoint(value="주소", configurator(config객체에 userId정보를 담은 클래스))
@ServerEndpoint(value="/chatWebSocket", configurator=WebSocketConfigurator.class)
public class ChatWebSocket {
	
	//현재접속한 userId와 WebSocketSession을 매핑해서 관리할 static 필드
	public static Map<String, Session> clients = new HashMap<>();
	public static List<Chat> chatList = new ArrayList<>();
	
	@OnOpen
	public void onOpen(EndpointConfig config, Session session) {
		//사용자 정보를 config객체로 부터 얻어서 clients에 저장함
		String userId = (String)config.getUserProperties().get("userId");
		clients.put(userId, session);
		
		//WebsocketSession에도 userId를 담아둠. @OnClose에서 사용.
		session.getUserProperties().put("userId", userId);
		
		System.out.println("현재 접속자 정보@OnOpen ("+clients.size()+") : "+clients);
	}
	
	@OnMessage
	public void onMessage(String msg, Session session) {
		System.out.println("msg@OnMessage="+msg);
		Map<String, Object> map = new Gson().fromJson(msg, Map.class);
		String type = (String)map.get("type");
		
		//받는사람과 보내는사람의 세션을 채크해서 해당 인원들에게만 메세지를 보낸다
		Session senderSession = clients.get((String)map.get("sender"));
		Session toSession = clients.get((String)map.get("to"));
		
		//type이 message라면 set에 추가하기
		if("message".equals(type)) 
			chatList.add(new Chat((String)map.get("sender"), (String)map.get("to"), (String)map.get("msg")));
		
		//하나의 업무 실행 도중 사용자 변경이 일어나서는 안된다.
		//NPE유발 방지를 위해서 동기화 처리함
		synchronized (clients) {
			Collection<Session> sessionList = clients.values();
			for(Session client: sessionList) {
				//접속종료시 세션이 제거된 상태에서 message전송 오류 유발
				if("adieu".equals(type) && client.equals(session)) continue;
				
				//client(세션)이 보낸사람의 세션 혹은 받는사람의 세션과 다르다면 컨티뉴
				if(!client.equals(senderSession)&&!client.equals(toSession)) continue;
				
				try {
					client.getBasicRemote().sendText(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}catch(Exception e) {
					e.printStackTrace();
				}			
			}
		}
		
	}
	
	@OnError
	public void onEorror(Throwable e) {
		//데이터전달과정에서 에러발생시 호출되는 메소드
		e.printStackTrace();
	}
	
	@OnClose
	public void onClose(Session session) {
		String userId = (String)session.getUserProperties().get("userId");
		
		//어느채팅이라도 종료가 된다면 지금까지의 채팅로그를 DB에 업데이트 시킨다
		new ChatService().insertChatList(chatList);
		//DB에 없데이트 시킨후 set을 초기화시킴
		chatList.clear();
		
		//접속사용자 관리목록에서 제거
		clients.remove("userId");
		
		System.out.println("현재접속자@OnClose ("+clients.size()+") : "+clients);
		
	}
}

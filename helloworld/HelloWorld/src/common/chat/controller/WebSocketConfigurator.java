package common.chat.controller;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

import member.model.vo.Member;

/**
 * Configurator란 WebSocket객체를 생성할때, 기본적인 설정정보를 지정.
 * modifyHandshake메소드를 Override
 * 1.ServerEndpointConfig : 설정정보를 가지고있는 객체
 * 2.HandshakeRequest
 * 3.HandshakeResponse
 */
public class WebSocketConfigurator extends Configurator{

	@Override
	public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
//		super.modifyHandshake(sec, request, response);
		//HttpSession에 담긴 사용자 정보를 ServerEndpointConfig객체에 저장
		Member member = (Member)((HttpSession)request.getHttpSession()).getAttribute("memberLoggedIn");
		String userId = member.getMemberId();
		config.getUserProperties().put("userId", userId);
		
		System.out.println("userId@configuartor="+userId);
	}
	
	
}

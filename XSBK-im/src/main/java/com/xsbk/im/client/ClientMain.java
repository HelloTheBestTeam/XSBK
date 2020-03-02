package com.xsbk.im.client;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.alibaba.fastjson.JSON;
import com.xsbk.core.model.chat.ChatMessage;

/**
 * socket客户端
 * 
 * @author chrilwe
 *
 */
public class ClientMain {
	public static void main(String[] args) throws Exception {
		WebSocketClient wsClient = new WebSocketClient(new URI("http://localhost:9004/ws")) {
			public void onClose(int arg0, String arg1, boolean arg2) {
				// TODO Auto-generated method stub

			}

			public void onError(Exception arg0) {
				// TODO Auto-generated method stub

			}

			public void onMessage(String msg) {
				System.out.println("收到来自于服务端的消息： " + msg);
			}

			public void onOpen(ServerHandshake arg0) {

			}

		};
		boolean connectBlocking = wsClient.connectBlocking();
		System.out.println("连接建立");
		while(true) {
			ChatMessage chat = new ChatMessage();
			chat.setMessage("client");
			wsClient.send(JSON.toJSONString(chat));
		}
	}
}

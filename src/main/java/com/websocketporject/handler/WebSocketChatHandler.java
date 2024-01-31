package com.websocketporject.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocketporject.dto.ChatMessage;
import com.websocketporject.dto.ChatRoom;
import com.websocketporject.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ChatService chatService;


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : {}", payload);
//        TextMessage textMessage = new TextMessage("Welcome chatting server!");
//        session.sendMessage(textMessage);
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        log.info("[handleTextMessage] roomId : {}", chatMessage.getRoomId());
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
        room.handleActions(session, chatMessage, chatService);
    }

}

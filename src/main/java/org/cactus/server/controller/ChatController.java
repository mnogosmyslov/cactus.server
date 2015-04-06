package org.cactus.server.controller;

import org.cactus.server.api.ChatApi;
import org.cactus.share.vo.MessageVO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ChatApi.CHAT)
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/conversation")
    // TODO: Use entity, when Mongo will be ready
    public MessageVO sendMessage(MessageVO messageVO) {
//        if(messageVO.getDate()==null){
//            messageVO.setDate(new Date());
//        }
        return messageVO;
    }

}

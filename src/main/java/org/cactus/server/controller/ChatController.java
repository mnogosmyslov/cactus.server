package org.cactus.server.controller;

import org.cactus.server.api.ChatApi;
import org.cactus.share.vo.MessageVO;
import org.cactus.share.vo.OutputMessageVO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(ChatApi.CHAT)
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/conversation")
    // TODO: Use entity, when Mongo will be ready
    public OutputMessageVO sendMessage(MessageVO messageVO) {
        return new OutputMessageVO(messageVO, new Date());
    }

}

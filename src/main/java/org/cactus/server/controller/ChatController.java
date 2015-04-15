package org.cactus.server.controller;

import org.cactus.server.api.ChatApi;
import org.cactus.server.service.UserService;
import org.cactus.share.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping(ChatApi.CHAT)
public class ChatController {

    @Autowired
    private UserService userAccountService;

//    @Autowired
//    private ChatService chatService;

    private final SimpMessageSendingOperations messagingTemplate;
    private List<String> chats = new ArrayList<String>();

    @MessageMapping("/chat")
    @SendTo("/conversation")
    // TODO: Use entity, when Mongo will be ready
    public MessageVO sendMessage(MessageVO messageVO) {
        if(messageVO.getDate()==null){
            messageVO.setDate(new Date());
        }
        return messageVO;
    }

    @Autowired
    public ChatController(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @SubscribeMapping("/join")
    public List<String> join(Principal principal) {

        messagingTemplate.convertAndSend("/chat/join", principal.getName());

        return chats;
    }

    @MessageMapping("/chat/{chatID}")
    public void chatReveived(org.springframework.messaging.Message msg, @Payload MessageVO message,
                             @DestinationVariable("chatID") long chatID) {
        //TODO: Get chat members when necessary mehods is ready!
        //Chat chat = chatService.getChat(chatId);
        //Set<UserAccount> membersSet = chat.getMembers();

        List<String> members = new ArrayList<String>();
        members.add("udenfox");
        members.add("rodrigo19");

        // get members from chat by chatId
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("chatID", String.valueOf(chatID));
        for (String member : members) {
            messagingTemplate.convertAndSend("/chat/" + member, message, headers);
        }

    }

}

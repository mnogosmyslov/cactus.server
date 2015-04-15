package org.cactus.server.controller;

import org.cactus.server.api.ChatApi;
import org.cactus.server.entity.Chat;
import org.cactus.server.entity.Message;
import org.cactus.server.entity.UserAccount;
import org.cactus.server.service.ChatService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(ChatApi.CHAT)
public class ChatController {

    @Autowired
    private UserService userAccountService;

    @Autowired
    private ChatService chatService;

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

    @MessageMapping("/chat/{username}")
    public void chatReveived(org.springframework.messaging.Message msg, @Payload Message message, @DestinationVariable("username") String username) {
        UserAccount userAccount = userAccountService.getByLogin(username);
        message.setAuthorId(userAccount.getId());

        LinkedMultiValueMap<String, String> headers = (LinkedMultiValueMap<String, String>) msg.getHeaders().get("nativeHeaders");
        String s = headers.getFirst("destination");
        long chatId = Long.valueOf(s);
        Chat chat = chatService.getChat(chatId);

        Set<UserAccount> hset = chat.getMembers();

        List<String> members = new ArrayList<String>();
        // get members from chat by chatId

        for (String member : members) {
            messagingTemplate.convertAndSendToUser(member, "/chat/" + member, message);
        }
    }

}

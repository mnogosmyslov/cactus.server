package org.cactus.server.controller;

import org.cactus.server.api.ChatApi;
import org.cactus.server.entity.Chat;
import org.cactus.server.entity.History;
import org.cactus.server.entity.UserAccount;
import org.cactus.server.service.ChatService;
import org.cactus.server.service.HistoryService;
import org.cactus.server.transformer.MessageTransformer;
import org.cactus.share.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping(ChatApi.CHAT)
public class ChatController {

    @Autowired
    private ChatService chatService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private MessageTransformer messageTransformer;

    private final SimpMessageSendingOperations messagingTemplate;
    private List<String> chats = new ArrayList<String>();

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
        Chat chat = chatService.getChat(chatID);
        Set<UserAccount> members = chat.getMembers();

        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("chatID", String.valueOf(chatID));

        for (UserAccount user : members) {
	        History history = historyService.readById(chatID + "_" + user.getId());
	        message.setId(history.getHistoryOfContent().size());
	        history.getHistoryOfContent().add(messageTransformer.transform(message));
	        historyService.updateHistory(history);

            messagingTemplate.convertAndSend("/chat/" + user.getLogin(), message, headers);
        }

    }

}

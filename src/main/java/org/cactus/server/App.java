package org.cactus.server;

import org.cactus.server.entity.Chat;
import org.cactus.server.entity.History;
import org.cactus.server.entity.Message;
import org.cactus.server.entity.UserAccount;
import org.cactus.server.service.ChatService;
import org.cactus.server.service.HistoryLogic;
import org.cactus.server.service.MessageLogic;
import org.cactus.server.service.impl.ChatServiceImpl;
import org.cactus.server.service.impl.UserAccountServiceImpl;

import java.util.Date;

public class App {
	public static void main(String[] args) {
		try {

			UserAccountServiceImpl userAccountServiceImpl = new UserAccountServiceImpl();
			UserAccount userAccount = new UserAccount();
			userAccount.setEmail("hela@mail.com");
			userAccount.setLogin("Hela");
			userAccount.setPassword("123123123");
			userAccount.setName("Helerayd");
			userAccountServiceImpl.createUserAccount(userAccount);

			UserAccountServiceImpl userAccountServiceImpl2 = new UserAccountServiceImpl();
			UserAccount userAccount2 = new UserAccount();
			userAccount2.setEmail("ozy@mail.com");
			userAccount2.setLogin("Ozy");
			userAccount2.setPassword("123123123");
			userAccount2.getContacts().add(userAccount);
			userAccountServiceImpl2.createUserAccount(userAccount2);

			Message message = new Message();
			message.setAuthorId(userAccount.getId());
			message.setDate(new Date().getTime());
			message.setContent("Test message");
			message.setViewed(false);
			MessageLogic msgLogic = new MessageLogic();
			msgLogic.addNewMessage(message);

			ChatService chatService = new ChatServiceImpl();
			Chat chat = new Chat();
			chat.getMembers().add(userAccount);
			chat.getMembers().add(userAccount2);
			chat.setChatName("Test Chat");
			chat.setSecure(true);
			chat.setLast_message(message.getId());
			chatService.addChat(chat);

			History history = new History();
			history.setId(chat.getChatId() + "_" + userAccount.getId());
			history.getHistoryOfContent().add(message);
			HistoryLogic hLogic = new HistoryLogic();
			hLogic.addHistory(history, chat, userAccount);
			hLogic.addHistory(history, chat, userAccount2);

//			Message msg = messageService.readById(message.getId());
//			System.out.println("Massage: " + msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package org.cactus.server.service;

import org.cactus.server.config.SpringMongoConfig;
import org.cactus.server.entity.Chat;
import org.cactus.server.entity.History;
import org.cactus.server.entity.UserAccount;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HistoryLogic {
	ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
	HistoryService historyService = ctx.getBean("historyService", HistoryService.class);

	public void addHistory(History history, Chat chat, UserAccount userAccount) {
		history.setId(chat.getChatId() + "_" + userAccount.getId());
		historyService.createHistory(history);
	}

	public History readHistory(String id) {
		return historyService.readById(id);
	}

	public void updateHistory(History history) {
		historyService.updateHistory(history);
	}

	public void deleteHistory(String id) {
		historyService.deleteById(id);
	}
}

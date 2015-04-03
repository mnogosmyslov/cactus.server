package org.cactus.server.service;

import org.cactus.server.config.SpringMongoConfig;
import org.cactus.server.entity.Message;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MessageLogic {
	private ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
	private MessageService messageService = ctx.getBean("messageService", MessageService.class);

	public void addNewMessage(Message message) {
		messageService.createMessage(message);
	}

	public Message readMessage(String id) {
		return messageService.readById(id);
	}

	public void deleteMessage(String id) {
		messageService.deleteById(id);
	}
}

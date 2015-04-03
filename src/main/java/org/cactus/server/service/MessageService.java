package org.cactus.server.service;

import org.cactus.server.entity.Message;

public interface MessageService {
	void createMessage(Message message);
	Message readById(String id);
	int deleteById(String id);
}

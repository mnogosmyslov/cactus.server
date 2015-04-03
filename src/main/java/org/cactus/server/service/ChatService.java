package org.cactus.server.service;

import org.cactus.server.entity.Chat;

public interface ChatService {
	void addChat(Chat chat);
	Chat getChat(Long id);
	void updateChat(Chat chat);
	void deleteChat(Long id);
}

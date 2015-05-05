package org.cactus.server.service;

import org.cactus.server.entity.Chat;

import java.util.HashSet;

public interface ChatService {
	void addChat(Chat chat);
	Chat getChat(Long id);
	HashSet getAllChats(Long userAccountId);
	void updateChat(Chat chat);
	void deleteChat(Long id);
}

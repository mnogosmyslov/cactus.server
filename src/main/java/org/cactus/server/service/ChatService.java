package org.cactus.server.service;

import org.cactus.server.entity.Chat;
import org.cactus.share.vo.ChatVO;

import java.util.HashSet;

public interface ChatService {
	void addChat(Chat chat);
	Chat getChat(Long id);
	HashSet getAllChats(Long userAccountId);
	HashSet<ChatVO> getAllChatsVO(Long userAccountId);
	void updateChat(Chat chat);
	void deleteChat(Long id);
}

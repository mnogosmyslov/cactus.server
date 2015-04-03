package org.cactus.server.service;

import org.cactus.server.entity.History;

public interface HistoryService {
	void createHistory(History history);
	History readById(String id);
	void updateHistory(History history);
	int deleteById(String id);
}

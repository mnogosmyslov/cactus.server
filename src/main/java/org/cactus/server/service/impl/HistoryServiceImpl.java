package org.cactus.server.service.impl;

import com.mongodb.WriteResult;
import org.cactus.server.entity.History;
import org.cactus.server.service.HistoryService;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class HistoryServiceImpl implements HistoryService {

	private MongoOperations mongoOps;
	private static final String HISTORY_COLLECTION = "History";

	public HistoryServiceImpl(MongoTemplate mongoOps) {
		this.mongoOps = mongoOps;
	}

	public void createHistory(History history) {
		mongoOps.insert(history, HISTORY_COLLECTION);
	}

	public History readById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return mongoOps.findOne(query, History.class, HISTORY_COLLECTION);
	}

	public void updateHistory(History history) {
		mongoOps.save(history, HISTORY_COLLECTION);
	}

	public int deleteById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		WriteResult result = mongoOps.remove(query, History.class, HISTORY_COLLECTION);
		return result.getN();
	}

}

package org.cactus.server.service.impl;

import com.mongodb.WriteResult;
import org.cactus.server.entity.Message;
import org.cactus.server.service.MessageService;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class MessageServiceImpl implements MessageService {

	private MongoOperations mongoOps;
	private static final String MESSAGE_COLLECTION = "Message";

	public MessageServiceImpl(MongoTemplate mongoOps) {
		this.mongoOps = mongoOps;
	}

	public void createMessage(Message message) {
		mongoOps.insert(message, MESSAGE_COLLECTION);
	}

	public Message readById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return mongoOps.findOne(query, Message.class, MESSAGE_COLLECTION);
	}

	public int deleteById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		WriteResult result = mongoOps.remove(query, Message.class, MESSAGE_COLLECTION);
		return result.getN();
	}
}

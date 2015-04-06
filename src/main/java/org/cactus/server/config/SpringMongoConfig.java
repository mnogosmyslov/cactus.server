package org.cactus.server.config;

import com.mongodb.MongoClient;
import org.cactus.server.service.impl.HistoryServiceImpl;
import org.cactus.server.service.impl.MessageServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
public class SpringMongoConfig {

	public @Bean
	MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient("127.0.0.1", 27017), "cactusdb");
	}

	public @Bean
	MongoTemplate mongoTemplate() throws Exception {

		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
		return mongoTemplate;

	}

	@Bean
	MessageServiceImpl messageService() throws Exception {
		return new MessageServiceImpl(mongoTemplate());
	}

	@Bean
	HistoryServiceImpl historyService() throws Exception {
		return new HistoryServiceImpl(mongoTemplate());
	}

}
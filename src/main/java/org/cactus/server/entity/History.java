package org.cactus.server.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Document
public class History {

	@Id
	private String id;

	private List<Message> historyOfContent = new ArrayList<Message>();

	public History() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Message> getHistoryOfContent() {
		return historyOfContent;
	}

	public void setHistoryOfContent(List<Message> historyOfContent) {
		this.historyOfContent = historyOfContent;
	}
}

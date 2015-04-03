package org.cactus.server.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document
public class Message {
	@Id
	private String id;
	private long authorId;
	private long date;
	private String content;
	private boolean viewed;

	public Message() {
	}

	public Message(long authorId, String content) {
		this.authorId = authorId;
		this.content = content;
	}

	public Message(long authorId, long date, String content, boolean viewed) {
		this.authorId = authorId;
		this.date = date;
		this.content = content;
		this.viewed = viewed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isViewed() {
		return viewed;
	}

	public void setViewed(boolean viewed) {
		this.viewed = viewed;
	}

	@Override
	public String toString(){
		return id + "::" + authorId + "::" + date + "::" + content + "::" + viewed;
	}
}

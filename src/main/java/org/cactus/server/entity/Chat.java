package org.cactus.server.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Chat")
public class Chat implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "chatId", unique = true, nullable = false)
	private Long chatId;

	@Column(name = "chatName")
	private String chatName;

	@ElementCollection
	@ManyToMany(fetch = FetchType.LAZY)
	@Column(name = "members")
	private Set<UserAccount> members = new HashSet<UserAccount>();

	private String last_message;

	@Column(name = "secure")
	private boolean secure;

	public Chat() {
	}

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

	public String getChatName() {
		return chatName;
	}

	public void setChatName(String chatName) {
		this.chatName = chatName;
	}

	public Set<UserAccount> getMembers() {
		return members;
	}

	public void setMembers(Set<UserAccount> members) {
		this.members = members;
	}

	public String getLast_message() {
		return last_message;
	}

	public void setLast_message(String last_message) {
		this.last_message = last_message;
	}

	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}

}

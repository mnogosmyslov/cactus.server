package org.cactus.server.service;

import org.cactus.server.entity.UserAccount;

import java.util.List;

public interface UserAccountService {
	UserAccount getAccount(String email);
	UserAccount getById(long id);
	UserAccount getByLogin(String login);
	void createUserAccount(UserAccount userAccount);
	void updateUserAccount(UserAccount userAccount);
	void deleteUserAccount(long id);
	List getAllUserAccount();
}

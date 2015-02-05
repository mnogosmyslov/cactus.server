package org.cactus.server.service;


import org.cactus.server.entity.UserAccount;

import java.util.List;

public interface UserAccountService {

    UserAccount getById(long id);
    UserAccount getByLogin(String login);
	UserAccount createUserAccount(UserAccount userAccount);
	UserAccount updateUserAccount(UserAccount userAccount);
	void deleteUserAccount(long id);
	List getAllUserAccount();
}

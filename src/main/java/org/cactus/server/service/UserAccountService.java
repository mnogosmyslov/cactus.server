package org.cactus.server.service;


import org.cactus.server.entity.UserAccount;

public interface UserAccountService {

    UserAccount getById(long id);
    UserAccount getByLogin(String login);

}

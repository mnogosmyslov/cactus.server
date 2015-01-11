package org.cactus.server.service;


import org.cactus.server.entity.UserAccount;

public interface UserAccountService {
    UserAccount getByLogin(String login);
}

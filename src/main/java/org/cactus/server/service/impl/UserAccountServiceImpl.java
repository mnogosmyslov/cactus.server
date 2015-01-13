package org.cactus.server.service.impl;

import org.cactus.server.entity.UserAccount;
import org.cactus.server.repository.UserAccountRepository;
import org.cactus.server.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserAccount getByLogin(String login) {
        UserAccount userAccount = new UserAccount();
        userAccount.getLogin();
        //TODO: check, if userAccount true and auth him

        return userAccountRepository.findByLogin(login);
    }
}

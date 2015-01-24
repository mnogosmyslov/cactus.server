package org.cactus.server.service.impl;

import org.cactus.server.entity.UserAccount;
import org.cactus.server.repository.UserAccountRepository;
import org.cactus.server.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserAccount getById(long id) {
        return userAccountRepository.findOne(id);
    }
}

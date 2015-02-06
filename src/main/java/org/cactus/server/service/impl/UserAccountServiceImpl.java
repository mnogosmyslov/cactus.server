package org.cactus.server.service.impl;

import org.cactus.server.entity.UserAccount;
import org.cactus.server.repository.UserAccountRepository;
import org.cactus.server.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserAccount getById(long id) {
        return userAccountRepository.findOne(id);
    }

    @Override
    public UserAccount getByLogin(String login) {
        return userAccountRepository.findByLogin(login);
    }

	@Override
	public UserAccount createUserAccount(UserAccount userAccount) {
		return userAccountRepository.save(userAccount);
	}

	@Override
	public UserAccount updateUserAccount(UserAccount userAccount) {
		return userAccountRepository.updateUser(userAccount.getId(), userAccount.getEmail(),
												userAccount.getLogin(), userAccount.getPassword(),
												userAccount.getPhoto());
	}

	@Override
	public void deleteUserAccount(long id) {
		userAccountRepository.delete(id);
	}

	@Override
	public List<UserAccount> getAllUserAccount() {
		return userAccountRepository.findAll();
	}
}

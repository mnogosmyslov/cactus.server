package org.cactus.server.service;

import org.cactus.server.entity.UserAccount;
import org.cactus.server.repository.UserAccountRepository;
import org.cactus.server.transformer.UserAccountTransformer;
import org.cactus.server.utils.service.RemoteService;
import org.cactus.share.common.ServiceNames;
import org.cactus.share.service.UserAccountService;
import org.cactus.share.vo.UserAccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service(ServiceNames.USER_ACCOUNT_SERVICE)
@RemoteService(serviceInterface = UserAccountService.class)
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserAccountTransformer userAccountTransformer;

    @Override
    public UserAccountVO getAccount(String email) {
        Assert.notNull(email, "Email shouldn't be null");

        UserAccount userAccount = userAccountRepository.findByEmail(email);

        return userAccountTransformer.transform(userAccount);
    }

    @Override
    public UserAccountVO getById(long id) {
        UserAccount userAccount = userAccountRepository.findOne(id);

        return userAccountTransformer.transform(userAccount);
    }

    @Override
    public UserAccountVO getByLogin(String login) {
        UserAccount userAccount = userAccountRepository.findByLogin(login);

        return userAccountTransformer.transform(userAccount);
    }

//    @Override
//    public UserAccountVO createUserAccount(UserAccountVO userAccount) {
//        UserAccount user = userAccountTransformer.transform(userAccount);
//
//        return userAccountRepository.save(user);
//    }
//
//    @Override
//    public UserAccountVO updateUserAccount(UserAccountVO userAccount) {
//        UserAccount user = userAccountTransformer.transform(userAccount);
//
//        return userAccountRepository.updateUser(userAccount.getId(), userAccount.getEmail(),
//                userAccount.getLogin(), userAccount.getPassword(), userAccount.getPhoto());
//    }

    @Override
    public void deleteUserAccount(long id) {
        userAccountRepository.delete(id);
    }

    @Override
    public List<UserAccount> getAllUserAccount() {
        return userAccountRepository.findAll();
    }
}


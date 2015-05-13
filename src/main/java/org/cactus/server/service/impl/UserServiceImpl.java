package org.cactus.server.service.impl;

import org.cactus.server.entity.UserAccount;
import org.cactus.server.repository.UserAccountRepository;
import org.cactus.server.security.LoggedInChecker;
import org.cactus.server.service.HibernateUtil;
import org.cactus.server.service.UserService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final LoggedInChecker loggedInChecker;

    @Autowired
    UserServiceImpl(LoggedInChecker loggedInChecker) {
        this.loggedInChecker = loggedInChecker;
    }

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserAccount getAccount(String email) {
        Assert.notNull(email, "Email shouldn't be null");
        return userAccountRepository.findByEmail(email);
    }

    @Override
    public UserAccount getById(long id) {
        return userAccountRepository.findOne(id);
    }

    @Override
    public UserAccount getByLogin(String login) {
        return userAccountRepository.findByLogin(login);
    }

    @Override
    public void createUserAccount(UserAccount userAccount) throws SQLException	{
        userAccountRepository.save(userAccount);
    }

    @Override
    public void updateUserAccount(UserAccount userAccount) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(userAccount);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void deleteUserAccount(long id) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(getById(id));
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<UserAccount> getAllUserAccount() {
        return userAccountRepository.findAll();
    }

    @Override
    public UserAccount getAuthUser(String login, String password) {
        UserAccount userAccount = userAccountRepository.getAuth(login, password);

        return userAccount;
    }

    @Override
    public List<String> getPermissions(String username) {
        return new ArrayList<>();
    }

    @Override
    public Boolean isCurrentUserLoggedIn() {
        return loggedInChecker.getLoggedInUser() != null;
    }
}

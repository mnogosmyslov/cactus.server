package org.cactus.server.service.impl;

import org.cactus.server.entity.UserAccount;
import org.cactus.server.repository.UserAccountRepository;
import org.cactus.server.utils.service.HibernateUtil;
import org.cactus.server.service.UserAccountServiceImpl;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserAccountServiceImpl {

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
    public void createUserAccount(UserAccount userAccount)	{
        userAccountRepository.save(userAccount);
    }

    @Override
    public void updateUserAccount(UserAccount userAccount) {
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
    public void deleteUserAccount(long id) {
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
}

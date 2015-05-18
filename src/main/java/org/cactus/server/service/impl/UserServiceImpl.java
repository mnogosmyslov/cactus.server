package org.cactus.server.service.impl;

import org.cactus.server.entity.UserAccount;
import org.cactus.server.repository.UserAccountRepository;
import org.cactus.server.security.LoggedInChecker;
import org.cactus.server.service.HibernateUtil;
import org.cactus.server.service.UserService;
import org.cactus.server.transformer.UserAccountTransformer;
import org.cactus.share.vo.UserVO;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	@Autowired
	private UserAccountTransformer userAccountTransformer;

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

	public Set getAllContacts(long userId) {
		Session session = null;
		Set<UserVO> contacts = new HashSet<>();

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			UserAccount userAccount = (UserAccount) session.get(UserAccount.class, userId);
			for (Long id : userAccount.getContacts()) {
				contacts.add(userAccountTransformer.transform(userAccountRepository.findOne(id)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return contacts;
	}
}

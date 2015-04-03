package org.cactus.server.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.cactus.server.entity.UserAccount;
import org.cactus.server.repository.UserAccountRepository;
import org.cactus.server.utils.service.HibernateUtil;
import org.cactus.server.utils.service.RemoteService;
import org.cactus.share.common.ServiceNames;
import org.cactus.server.service.UserAccountService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Transactional
@Service(ServiceNames.USER_ACCOUNT_SERVICE)
@RemoteService(serviceInterface = UserAccountService.class)
public class UserAccountServiceImpl implements UserAccountService {

	private static final Logger log = Logger.getLogger(UserAccountServiceImpl.class.getName());

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserAccount getAccount(String email) {
        Assert.notNull(email, "Email shouldn't be null");

        UserAccount userAccount = userAccountRepository.findByEmail(email);

        return userAccount;
    }

    @Override
    public UserAccount getById(long id) {
        UserAccount userAccount = userAccountRepository.findOne(id);

        return userAccount;
    }

    @Override
    public UserAccount getByLogin(String login) {
        UserAccount userAccount = userAccountRepository.findByLogin(login);

        return userAccount;
    }

	@Override
	public void createUserAccount(UserAccount userAccount) {
		log.log(Level.INFO, "Persisting UserAccount instance ...");
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			userAccount.setPassword(DigestUtils.sha1Hex(userAccount.getPassword()));
			userAccount.setPhoto("/photo.png");
			session.save(userAccount);
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


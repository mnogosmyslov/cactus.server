package org.cactus.server.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.cactus.server.entity.Chat;
import org.cactus.server.entity.UserAccount;
import org.cactus.server.repository.UserAccountRepository;
import org.cactus.server.service.impl.ChatServiceImpl;
import org.cactus.server.transformer.UserAccountTransformer;
import org.cactus.server.transformer.UserTransformer;
import org.cactus.server.utils.service.RemoteService;
import org.cactus.share.common.ServiceNames;
import org.cactus.share.service.UserAccountService;
import org.cactus.share.vo.UserAccountVO;
import org.cactus.share.vo.UserVO;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service(ServiceNames.USER_ACCOUNT_SERVICE)
@RemoteService(serviceInterface = UserAccountService.class)
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserAccountTransformer userAccountTransformer;

	@Autowired
	private UserTransformer userTransformer;

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

	@Override
	public UserVO getUserVOByLogin(String login) {
		UserAccount userAccount = userAccountRepository.findByLogin(login);
		return userTransformer.transform(userAccount);
	}

    @Override
    public void createUserAccount(UserAccountVO userAccountVO) throws SQLException	{
	    Session session = null;
	    try {
		    session = HibernateUtil.getSessionFactory().openSession();
		    session.beginTransaction();
		    UserAccount user = userAccountTransformer.transform(userAccountVO);
		    user.setPassword(DigestUtils.sha1Hex(userAccountVO.getPassword()));
		    user.setPhoto("http://localhost:8080/cactus/resources/tmp/photo.png");
		    session.save(user);
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
    public void updateUserAccount(UserAccountVO userAccountVO) throws SQLException {
	    UserAccount user = userAccountTransformer.transform(userAccountVO);
	    Session session = null;
	    try {
		    session = HibernateUtil.getSessionFactory().openSession();
		    session.beginTransaction();
		    session.update(user);
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
	public boolean addContact(long id, String login) {

		HashSet<UserVO> resultSet = new HashSet<UserVO>();
		Session session = null;
		boolean repeat = false;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			UserAccount userAccount = (UserAccount) session.get(UserAccount.class, id);
			UserAccount userAccount2 = (UserAccount) session.get(UserAccount.class, userAccountRepository.findByLogin(login).getId());

			for (Long contactId : userAccount.getContacts()) {
				if (contactId.equals(userAccount2.getId())) {
					repeat = true;
					break;
				}
			}

			if (!repeat) {
				userAccount.getContacts().add(userAccountRepository.findByLogin(login).getId());
				userAccount2.getContacts().add(id);

				resultSet.add(userTransformer.transform(userAccount));
				resultSet.add(userTransformer.transform(userAccount2));

				session.beginTransaction();
				session.update(userAccount);
				session.update(userAccount2);
				session.getTransaction().commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return repeat;
	}

	public HashSet getAllContacts(UserVO userVO) {
		Session session = null;
		HashSet<UserVO> contacts = new HashSet<UserVO>();

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			UserAccount userAccount = (UserAccount) session.get(UserAccount.class, userVO.getId());
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


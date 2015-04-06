package org.cactus.server.service.impl;

import org.cactus.server.entity.Chat;
import org.cactus.server.service.ChatService;
import org.cactus.server.service.HibernateUtil;
import org.hibernate.Session;

import javax.transaction.Transactional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Transactional
public class ChatServiceImpl implements ChatService {
	private static final Logger log = Logger.getLogger(ChatServiceImpl.class.getName());

	@Override
	public void addChat(Chat chat) {
		log.log(Level.INFO, "Persisting Chat instance ...");

		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(chat);
			session.getTransaction().commit();

			log.log(Level.INFO, "Persist Chat successful...");
		} catch (Exception e) {
			e.printStackTrace();
			log.log(Level.SEVERE, "Persist Chat failed...", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@Override
	public void updateChat(Chat chat) {
		log.log(Level.INFO, "Updating Chat instance ...");
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.update(chat);
			session.getTransaction().commit();
			log.log(Level.INFO, "Updating Chat successful...");
		} catch (Exception e) {
			e.printStackTrace();
			log.log(Level.SEVERE, "Updating Chat failed...", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@Override
	public Chat getChat(Long id) {
		log.log(Level.INFO, "Getting Chat instance ...");
		Chat chat = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			chat = (Chat) session.load(Chat.class, id);
			log.log(Level.INFO, "Getting Chat successful...");
		} catch (Exception e) {
			e.printStackTrace();
			log.log(Level.SEVERE, "Getting Chat failed...", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return chat;
	}

	@Override
	public void deleteChat(Long id) {
		log.log(Level.INFO, "Deleting Chat instance ...");
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.delete(session.load(Chat.class, id));
			session.getTransaction().commit();
			log.log(Level.INFO, "Deleting Chat successful...");
		} catch (Exception e) {
			e.printStackTrace();
			log.log(Level.SEVERE, "Deleting Chat failed...", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
}

package org.cactus.server.service.impl;

import org.cactus.server.entity.Chat;
import org.cactus.server.repository.ChatRepository;
import org.cactus.server.service.ChatService;
import org.cactus.server.service.HibernateUtil;
import org.cactus.server.transformer.ChatTransformer;
import org.cactus.share.vo.ChatVO;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {
	private static final Logger log = Logger.getLogger(ChatServiceImpl.class.getName());

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private ChatTransformer chatTransformer;

	@Override
	public void addChat(Chat chat) {
//		log.log(Level.INFO, "Persisting Chat instance ...");

		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(chat);
			session.getTransaction().commit();

//			log.log(Level.INFO, "Persist Chat successful...");
		} catch (Exception e) {
			e.printStackTrace();
//			log.log(Level.SEVERE, "Persist Chat failed...", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@Override
	public void updateChat(Chat chat) {
//		log.log(Level.INFO, "Updating Chat instance ...");
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.update(chat);
			session.getTransaction().commit();
//			log.log(Level.INFO, "Updating Chat successful...");
		} catch (Exception e) {
			e.printStackTrace();
//			log.log(Level.SEVERE, "Updating Chat failed...", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@Override
	public Chat getChat(Long id) {
//		log.log(Level.INFO, "Getting Chat instance ...");
		Chat chat = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			chat = (Chat) session.get(Chat.class, id);
//			log.log(Level.INFO, "Getting Chat successful...");
		} catch (Exception e) {
			e.printStackTrace();
//			log.log(Level.SEVERE, "Getting Chat failed...", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return chat;
	}

	@Override
	public HashSet<Chat> getAllChats(Long userAccountId) {
		HashSet<Chat> list = new HashSet<Chat>();
		HashSet<BigInteger> tempList = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tempList = chatRepository.getChatList(userAccountId);
			if (!tempList.isEmpty()) {
				for (BigInteger id : tempList) {
					list.add(getChat(id.longValue()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return list;
	}

	public HashSet<ChatVO> getAllChatsVO(Long userAccountId) {
		HashSet<ChatVO> listVO = new HashSet<ChatVO>();
		HashSet<BigInteger> list = null;
		Chat chat = null;
		try {
			list = chatRepository.getChatList(userAccountId);
			if (!list.isEmpty()) {
				for (BigInteger id : list) {
					chat = getChat(id.longValue());
					listVO.add(chatTransformer.transform(chat));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listVO;
	}

	@Override
	public void deleteChat(Long id) {
//		log.log(Level.INFO, "Deleting Chat instance ...");
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.delete(session.load(Chat.class, id));
			session.getTransaction().commit();
//			log.log(Level.INFO, "Deleting Chat successful...");
		} catch (Exception e) {
			e.printStackTrace();
//			log.log(Level.SEVERE, "Deleting Chat failed...", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
}

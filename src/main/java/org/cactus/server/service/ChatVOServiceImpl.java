package org.cactus.server.service;

import org.cactus.server.entity.Chat;
import org.cactus.server.entity.History;
import org.cactus.server.repository.ChatRepository;
import org.cactus.server.transformer.ChatTransformer;
import org.cactus.server.transformer.UserTransformer;
import org.cactus.server.utils.service.RemoteService;
import org.cactus.share.common.ServiceNames;
import org.cactus.share.service.ChatService;
import org.cactus.share.vo.ChatVO;
import org.cactus.share.vo.UserVO;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.logging.Logger;

@Transactional
@Service(ServiceNames.CHAT_SERVICE)
@RemoteService(serviceInterface = ChatService.class)
public class ChatVOServiceImpl implements ChatService {
	private static final Logger log = Logger.getLogger(ChatVOServiceImpl.class.getName());

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private ChatTransformer chatTransformer;

	@Autowired
	private UserTransformer userTransformer;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private UserService userAccountService;

	public void addChat(ChatVO chatVO) {
//		log.log(Level.INFO, "Persisting Chat instance ...");
		Chat chat = chatTransformer.transform(chatVO);

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

	public void newChat(long userId, String login) {
		UserVO userVO = userTransformer.transform(userAccountService.getById(userId));
		UserVO userVO2 = userTransformer.transform(userAccountService.getByLogin(login));
		HashSet<UserVO> temp = new HashSet<UserVO>();
		temp.add(userVO);
		temp.add(userVO2);
		ChatVO chat = new ChatVO();
		chat.setMembers(temp);
		chat.setSecure(false);
		addChat(chat);

		HashSet<ChatVO> listChatVO = new HashSet<ChatVO>();
		HashSet<ChatVO> listChatVO2 = new HashSet<ChatVO>();
		listChatVO.addAll(getAllChats(userVO.getId()));
		listChatVO2.addAll(getAllChats(userVO2.getId()));
		ChatVO result = new ChatVO();

		search:for (ChatVO chatVOuser1 : listChatVO) {
			for (ChatVO chatVOuser2 : listChatVO2) {
				if (chatVOuser1.getChatId().equals(chatVOuser2.getChatId())) {
					result = chatVOuser1;
					break search;
				}
			}
		}

		try {
			for (UserVO user : temp) {
				History history = new History();
				history.setId(result.getChatId() + "_" + user.getId());
				historyService.createHistory(history);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ChatVO getChat(Long id) {
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

		return chatTransformer.transform(chat);
	}

	public HashSet<ChatVO> getAllChats(Long userAccountId) {
		HashSet<ChatVO> listVO = new HashSet<ChatVO>();
		HashSet<BigInteger> list = null;
		try {
			list = chatRepository.getChatList(userAccountId);
			if (!list.isEmpty()) {
				for (BigInteger id : list) {
					listVO.add(chatTransformer.transform(chatRepository.findOne(id.longValue())));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listVO;
	}

	public void updateChat(ChatVO chatVO) {
//		log.log(Level.INFO, "Updating Chat instance ...");
		Session session = null;
		try {
			Chat chat = chatTransformer.transform(chatVO);
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

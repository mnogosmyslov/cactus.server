package org.cactus.server.transformer;

import org.cactus.server.entity.Chat;
import org.cactus.server.entity.UserAccount;
import org.cactus.server.service.HibernateUtil;
import org.cactus.share.vo.ChatVO;
import org.cactus.share.vo.UserVO;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatTransformer extends AbstractTransformer<Chat, ChatVO> {

	@Autowired
	private UserTransformer userTransformer;

	@Autowired
	private MessageTransformer messageTransformer;

	@Override
	protected Chat populateType (ChatVO vo) {
		Chat type = new Chat();
		type.setChatId(vo.getChatId());
		type.setChatName(vo.getChatName());

		type.setSecure(vo.isSecure());

		if (!vo.getMembers().isEmpty()) {
			for (UserVO userVO : vo.getMembers()) {
				type.getMembers().add(userTransformer.populateType(userVO));
			}
		}

		try {
			type.setLast_message(messageTransformer.populateType(vo.getLast_message()));
		} catch (NullPointerException nu) {
			nu.printStackTrace();
		}

		return type;
	}

	@Override
	protected ChatVO populateVO (Chat type) {
		ChatVO vo = new ChatVO();

		Session session = null;

		try {
			vo.setChatId(type.getChatId());
			vo.setChatName(type.getChatName());
			vo.setSecure(type.isSecure());
			session = HibernateUtil.getSessionFactory().openSession();
			Chat chat = (Chat) session.get(Chat.class, type.getChatId());
			if (!chat.getMembers().isEmpty()) {
				for (UserAccount userAccount : chat.getMembers()) {
					vo.getMembers().add(userTransformer.populateVO(userAccount));
				}
			}

			try {
				vo.setLast_message(messageTransformer.populateVO(type.getLast_message()));
			} catch (NullPointerException nu) {
				nu.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return vo;
	}
}

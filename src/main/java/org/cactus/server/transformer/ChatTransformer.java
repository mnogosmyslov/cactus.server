package org.cactus.server.transformer;

import org.cactus.server.entity.Chat;
import org.cactus.server.entity.UserAccount;
import org.cactus.server.service.HibernateUtil;
import org.cactus.share.vo.ChatVO;
import org.cactus.share.vo.UserAccountVO;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatTransformer extends AbstractTransformer<Chat, ChatVO> {

	@Autowired
	UserAccountTransformer userAccountTransformer;

	@Override
	protected Chat populateType (ChatVO vo) {
		Chat type = new Chat();
		type.setChatId(vo.getChatId());
		type.setChatName(vo.getChatName());
		type.setLast_message(vo.getLast_message());
		type.setSecure(vo.isSecure());

		if (!vo.getMembers().isEmpty()) {
			for (UserAccountVO userAccountVO : vo.getMembers()) {
				type.getMembers().add(userAccountTransformer.populateType(userAccountVO));
			}
		}

		return type;
	}

	@Override
	protected ChatVO populateVO (Chat type) {
		ChatVO vo = new ChatVO();
		vo.setChatId(type.getChatId());
		vo.setChatName(type.getChatName());
		vo.setLast_message(type.getLast_message());
		vo.setSecure(type.isSecure());

		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Chat chat = (Chat) session.get(Chat.class, type.getChatId());
			if (!chat.getMembers().isEmpty()) {
				for (UserAccount userAccount : chat.getMembers()) {
					vo.getMembers().add(userAccountTransformer.populateVO(userAccount));
				}
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

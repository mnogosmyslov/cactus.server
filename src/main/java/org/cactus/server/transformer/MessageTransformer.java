package org.cactus.server.transformer;

import org.cactus.server.entity.Message;
import org.cactus.share.vo.MessageVO;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MessageTransformer extends AbstractTransformer<Message, MessageVO> {

	@Override
	protected Message populateType(MessageVO vo) {
		Message type = new Message();
		type.setId(vo.getId());
		type.setAuthorId(vo.getAuthorId());
		type.setDate(vo.getDate().getTime());
		type.setContent(vo.getMessage());
		type.setViewed(false);

		return type;
	}

	@Override
	protected MessageVO populateVO(Message type) {
		MessageVO vo = new MessageVO();
		vo.setId(type.getId());
		vo.setAuthorId(type.getAuthorId());
		vo.setMessage(type.getContent());
		vo.setDate(new Date(type.getDate()));

		return vo;
	}
}

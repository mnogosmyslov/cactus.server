package org.cactus.server.transformer;

import org.cactus.server.entity.UserAccount;
import org.cactus.share.vo.UserVO;
import org.springframework.stereotype.Component;

@Component
public class UserTransformer extends AbstractTransformer<UserAccount,UserVO> {
	@Override
	protected UserAccount populateType(UserVO vo) {
		UserAccount type = new UserAccount();
		type.setId(vo.getId());
		type.setEmail(vo.getEmail());
		type.setRole(vo.getRole());
		type.setLogin(vo.getLogin());
		type.setName(vo.getName());
		type.setPhoto(vo.getPhoto());

		return type;
	}

	@Override
	protected UserVO populateVO(UserAccount type) {
		UserVO vo = new UserVO();
		vo.setId(type.getId());
		vo.setEmail(type.getEmail());
		vo.setRole(type.getRole());
		vo.setLogin(type.getLogin());
		vo.setName(type.getName());
		vo.setPhoto(type.getPhoto());

		return vo;
	}
}

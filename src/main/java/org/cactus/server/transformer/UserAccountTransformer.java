package org.cactus.server.transformer;

import org.cactus.server.entity.UserAccount;
import org.cactus.share.vo.UserAccountVO;
import org.springframework.stereotype.Component;

@Component
public class UserAccountTransformer extends AbstractTransformer<UserAccount,UserAccountVO> {

    @Override
    protected UserAccount populateType(UserAccountVO vo) {
        UserAccount type = new UserAccount();
        type.setId(vo.getId());
        type.setEmail(vo.getEmail());
        type.setPassword(vo.getPassword());
        type.setRole(vo.getRole());
        type.setLogin(vo.getLogin());
        type.setName(vo.getName());

        return type;
    }

    @Override
    protected UserAccountVO populateVO(UserAccount type) {
        UserAccountVO vo = new UserAccountVO();
        vo.setId(type.getId());
        vo.setEmail(type.getEmail());
        vo.setPassword(type.getPassword());
        vo.setRole(type.getRole());
        vo.setLogin(type.getLogin());
        vo.setName(type.getName());

        return vo;
    }
}


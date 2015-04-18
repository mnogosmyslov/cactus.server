package org.cactus.server.transformer;

import org.cactus.server.entity.UserAccount;
import org.cactus.server.repository.UserAccountRepository;
import org.cactus.server.service.HibernateUtil;
import org.cactus.share.vo.UserAccountVO;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAccountTransformer extends AbstractTransformer<UserAccount,UserAccountVO> {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    protected UserAccount populateType(UserAccountVO vo) {
        UserAccount type = new UserAccount();
        type.setId(vo.getId());
        type.setEmail(vo.getEmail());
        type.setPassword(vo.getPassword());
        type.setRole(vo.getRole());
        type.setLogin(vo.getLogin());
        type.setName(vo.getName());
        type.setPhoto(vo.getPhoto());

        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            UserAccountVO userAccountVO = (UserAccountVO) session.get(UserAccountVO.class, vo.getId());

            if (!userAccountVO.getContacts().isEmpty()) {
                type.getContacts().addAll(userAccountVO.getContacts());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

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
        vo.setPhoto(type.getPhoto());

        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            UserAccount userAccount = (UserAccount) session.get(UserAccount.class, type.getId());

            if (!userAccount.getContacts().isEmpty()) {
                vo.getContacts().addAll(userAccount.getContacts());
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


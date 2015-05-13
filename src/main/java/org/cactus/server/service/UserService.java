package org.cactus.server.service;

import org.cactus.server.entity.UserAccount;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    UserAccount getAccount(String email);
    UserAccount getById(long id);
    UserAccount getByLogin(String login);
    void createUserAccount(UserAccount userAccount) throws SQLException;
    void updateUserAccount(UserAccount userAccount) throws SQLException;
    void deleteUserAccount(long id) throws SQLException;
    List getAllUserAccount();
    UserAccount getAuthUser(String login, String password);
    List<String> getPermissions(String username);
    Boolean isCurrentUserLoggedIn();

}

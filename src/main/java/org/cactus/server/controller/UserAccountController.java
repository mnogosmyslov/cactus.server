package org.cactus.server.controller;

import org.cactus.server.api.UserAccountApi;
import org.cactus.server.entity.UserAccount;
import org.cactus.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(UserAccountApi.USER)
public class UserAccountController {

    @Autowired
    private UserService userAccountService;

    @RequestMapping(value = UserAccountApi.BY_ID, method = RequestMethod.GET)
    public UserAccount getUser(@PathVariable long id){
        Assert.notNull(id);
        return userAccountService.getById(id);
    }

    @RequestMapping(value = UserAccountApi.BY_LOGIN, method = RequestMethod.GET)
    public UserAccount getUserByLogin(@PathVariable String login){
        Assert.notNull(login);
        return userAccountService.getByLogin(login);
    }

    @RequestMapping(value = UserAccountApi.NEW, method = RequestMethod.POST)
    public void createUser(@RequestBody UserAccount userAccount) throws SQLException {
        Assert.notNull(userAccount);
        userAccountService.createUserAccount(userAccount);
    }

    @RequestMapping(value = UserAccountApi.BY_ID, method = RequestMethod.PUT,
			headers = "Content-Type=application/json")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateUser(@PathVariable long id, @RequestBody UserAccount userAccount) throws SQLException {
		Assert.notNull(id);
		Assert.notNull(userAccount);
		userAccountService.updateUserAccount(userAccount);
	}

    @RequestMapping(value = UserAccountApi.BY_ID, method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable long id) throws SQLException {
		Assert.notNull(id);
		userAccountService.deleteUserAccount(id);
	}

    @RequestMapping(method = RequestMethod.GET)
    public List getAllUser() {
        return userAccountService.getAllUserAccount();
    }

    @RequestMapping(value = UserAccountApi.GET_AUTH, method = RequestMethod.GET, headers = "Accept=application/json")
    public UserAccount getAuth(@PathVariable String login, @PathVariable String password){
        Assert.notNull(login);
        Assert.notNull(password);

        // TODO: return http status if bad request
        return userAccountService.getAuthUser(login, password);
    }
}

package org.cactus.server.controller;

import org.cactus.server.api.UserAccountApi;
import org.cactus.server.entity.UserAccount;
import org.cactus.server.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UserAccountApi.USER)
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

	@RequestMapping(method = RequestMethod.POST,
			headers = "Content-Type=application/json")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public UserAccount createUser(@RequestBody UserAccount userAccount) {
		Assert.notNull(userAccount);
		return userAccountService.createUserAccount(userAccount);
	}

    @RequestMapping(value = UserAccountApi.BY_ID, method = RequestMethod.GET)
    public UserAccount getUser(@PathVariable long id){
        Assert.notNull(id);
        return userAccountService.getById(id);
    }

	@RequestMapping(method = RequestMethod.GET)
	public List getAllUser() {
		return userAccountService.getAllUserAccount();
	}

    @RequestMapping(value = UserAccountApi.BY_LOGIN, method = RequestMethod.GET)
    public UserAccount getUserByLogin(@PathVariable String login){
        Assert.notNull(login);
        return userAccountService.getByLogin(login);
    }

	@RequestMapping(value = UserAccountApi.BY_ID, method = RequestMethod.PUT,
			headers = "Content-Type=application/json")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateUser(@PathVariable long id, @RequestBody UserAccount userAccount) {
		Assert.notNull(id);
		Assert.notNull(userAccount);
		userAccountService.updateUserAccount(userAccount);
	}

	@RequestMapping(value = UserAccountApi.BY_ID, method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable long id) {
		Assert.notNull(id);
		userAccountService.deleteUserAccount(id);
	}
}

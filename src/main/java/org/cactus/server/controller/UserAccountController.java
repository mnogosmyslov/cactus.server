package org.cactus.server.controller;

import org.cactus.server.api.UserAccountApi;
import org.cactus.server.entity.UserAccount;
import org.cactus.server.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserAccountApi.USER)
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

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

}

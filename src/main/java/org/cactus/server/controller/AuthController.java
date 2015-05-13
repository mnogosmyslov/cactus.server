package org.cactus.server.controller;

import org.cactus.server.api.AuthApi;
import org.cactus.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = AuthApi.AUTH)
public class AuthController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = AuthApi.IS_LOGINED, method = GET)
    @PermitAll
    ResponseEntity<Boolean> isCurrentUserLoggedIn() {
        return new ResponseEntity<>(userService.isCurrentUserLoggedIn(), HttpStatus.OK);
    }

}

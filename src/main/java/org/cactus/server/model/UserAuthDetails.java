package org.cactus.server.model;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserAuthDetails<UserEntity> extends UserDetails {

    UserEntity getUser();

}
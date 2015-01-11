package org.cactus.server.repository;

import org.cactus.server.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    @Query("select u from UserAccount u where u.login = :login")
    UserAccount findByLogin(@Param("login") String login);

}

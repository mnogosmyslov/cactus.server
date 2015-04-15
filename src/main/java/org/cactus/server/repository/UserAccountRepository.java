package org.cactus.server.repository;

import org.cactus.server.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    @Query("select u from UserAccount u where u.login = :login")
    UserAccount findByLogin(@Param("login") String login);

    @Query("select u from UserAccount u where u.email = :email")
    UserAccount findByEmail(@Param("email") String email);

	@Query("update UserAccount u set u.email = :email, u.login = :login, u.password = :password, " +
			"u.photo = :photo, " +
			"u.role = :role where u.id = :id")
	UserAccount updateUser(@Param("id") long id, @Param("email") String email,
	                       @Param("login") String login, @Param("password") String password,
	                       @Param("photo") String photo);

	@Query("select u from UserAccount u where u.login = :login and u.password = :password")
	UserAccount getAuth(@Param("login") String login, @Param("password") String password);

//	@Query("select u.contacts from UserAccount u where u.useraccount_id = :id")
//	Set<Long> returnContacts(@Param("id") Long id);
}

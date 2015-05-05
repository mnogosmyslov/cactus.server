package org.cactus.server.repository;

import org.cactus.server.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;

public interface ChatRepository extends JpaRepository<Chat, Long> {
//	@Query(value = "select * from chat_users where members_id = ?0", nativeQuery = true)
	@Query(value = "select chat_chatid from chat_users where members_id = :id", nativeQuery = true)
	HashSet<BigInteger> getChatsList(@Param("id") long id);
}

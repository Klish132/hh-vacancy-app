package com.klish.vacancysearcher.vacancysearcher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select count(id) from User where login = :login")
    int checkIfExists(String login);

    @Query("select id from User where login = :login")
    int getId(String login);
}

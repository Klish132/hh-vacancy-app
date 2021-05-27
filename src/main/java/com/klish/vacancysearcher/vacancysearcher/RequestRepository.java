package com.klish.vacancysearcher.vacancysearcher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    @Query("select count(id) from Request where user_id = :user_id and skill_id = :skill_id")
    int checkIfExists(int user_id, int skill_id);

    @Query("select r from Request r where user_id = :user_id")
    List<Request> getUserRequests(int user_id);

}

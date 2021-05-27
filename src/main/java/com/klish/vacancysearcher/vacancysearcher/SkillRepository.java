package com.klish.vacancysearcher.vacancysearcher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SkillRepository extends JpaRepository<Skill, Integer> {

    @Query("select count(id) from Skill where name = :name and region = :region and occurrence_number = :on")
    int checkIfExists(String name, String region, int on);

    @Query("select id from Skill where name = :name and region = :region and occurrence_number = :on")
    int getId(String name, String region, int on);
}

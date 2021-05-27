package com.klish.vacancysearcher.vacancysearcher;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    @Column(name="name")
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name="region")
    private String region;

    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }

    @Column(name="occurrence_number")
    private Integer occurrence_number;

    public Integer getOccurrence_number() {
        return occurrence_number;
    }
    public void setOccurrence_number(Integer occurrence_number) {
        this.occurrence_number = occurrence_number;
    }

    @OneToMany(mappedBy = "skill")
    private Set<Request> request;
}


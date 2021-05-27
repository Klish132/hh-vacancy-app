package com.klish.vacancysearcher.vacancysearcher;

import javax.persistence.*;

@Entity
@Table(name="requests")
public class Request {

    @EmbeddedId
    private RequestId id = new RequestId();

    @ManyToOne
    @MapsId("user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @MapsId("skill_id")
    private Skill skill;

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    @Column(name="date")
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}


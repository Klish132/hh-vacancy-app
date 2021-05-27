package com.klish.vacancysearcher.vacancysearcher;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    @Column(name="login")
    private String login;

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    @OneToMany(mappedBy = "user")
    private Set<Request> request;
}
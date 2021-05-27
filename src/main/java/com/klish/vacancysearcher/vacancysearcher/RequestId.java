package com.klish.vacancysearcher.vacancysearcher;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RequestId implements Serializable {
    private static final long serialVersionUID = 1L;
    private int user_id;
    private int skill_id;
}

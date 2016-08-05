package com.abo.todo.domain;

import com.abo.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Todo implements Serializable{


    public Todo(String message, User owner) {
        this.message = message;
        this.owner = owner;
    }

    @Id
    @GeneratedValue
    private Long ID;

    private boolean done = false;

    private String message;

    @ManyToOne
    private User owner;
}

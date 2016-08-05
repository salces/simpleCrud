package com.abo.user.service;


import com.abo.todo.persistance.TodoRepository;
import com.abo.todo.domain.Todo;
import com.abo.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class TodoInitializer {

    @Autowired
    public TodoInitializer(TodoRepository todoRepository) {
        User user = User.builder().login("james_bond").build();
        Stream.of(new Todo("Something to do", user),
                    new Todo("And next thing to do", user),
                    new Todo("I'm already tired", user),
                    new Todo("I want go home", user))
                .forEach(todoRepository::save);
    }
}

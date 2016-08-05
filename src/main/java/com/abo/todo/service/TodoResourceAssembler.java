package com.abo.todo.service;


import com.abo.todo.domain.Todo;
import com.abo.todo.web.TodoController;
import com.abo.user.web.UserController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Service
public class TodoResourceAssembler extends ResourceAssemblerSupport<Todo, Resource> {

    public TodoResourceAssembler() {
        super(TodoController.class, Resource.class);
    }

    @Override
    public List<Resource> toResources(Iterable<? extends Todo> todos) {

        return toCollection(todos).stream()
                .map(this::toResource)
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Resource<Todo> toResource(Todo todo) {
        return new Resource(todo,
                            linkTo(TodoController.class).slash(todo.getID()).withSelfRel(),
                            linkTo(UserController.class).slash(todo.getOwner().getLogin()).withRel("owner"));
    }

    private List<Todo> toCollection(Iterable<? extends Todo> iterable) {
        List<Todo> collection = new ArrayList<>();
        iterable.forEach(collection::add);
        return collection;
    }
}

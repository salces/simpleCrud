package com.abo.todo.web;

import com.abo.security.LoggedUserGetter;
import com.abo.todo.persistance.TodoRepository;
import com.abo.todo.service.TodoResourceAssembler;
import com.abo.todo.domain.Todo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/todos")
public class TodoController {

    TodoRepository todoRepository;
    LoggedUserGetter loggedUser;
    TodoResourceAssembler assembler;


    @RequestMapping(method = GET)
    public List<Resource> getAll() {
        String username = loggedUser.getLoggedUserName();
        return assembler.toResources(todoRepository.findByOwnerLogin(username));
    }

    @RequestMapping(value = "/{ID}",method = GET)
    public Resource<Todo> getByID(@PathVariable Long ID){
        return assembler.toResource(todoRepository.findOne(ID));
    }

    @RequestMapping(method = DELETE)
    @ResponseStatus(NO_CONTENT)
    public void delete(@RequestBody Todo todo){
        if(!todoRepository.exists(todo.getID())) {
            throw new NoSuchElementException();
        }
        todoRepository.delete(todo.getID());
    }

    @RequestMapping(value = "/{ID}",method = DELETE)
    @ResponseStatus(NO_CONTENT)
    public void deleteByID(@PathVariable Long ID){
        if(!todoRepository.exists(ID)) {
            throw new NoSuchElementException();
        }
        todoRepository.delete(ID);
    }

    @RequestMapping(method = PUT)
    @ResponseStatus(OK)
    public Resource<Todo> update(@RequestBody Todo todo){
        if(todo.getID() == null ||
                !todoRepository.exists(todo.getID())) {
            throw new NoSuchElementException();
        }

        Todo updatedTodo = todoRepository.save(todo);

        return assembler.toResource(updatedTodo);
    }

    @RequestMapping(method = POST)
    @ResponseStatus(CREATED)
    public Resource<Todo> save(@RequestBody Todo todo){
        Todo newTodo = todoRepository.save(todo);
        return assembler.toResource(newTodo);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(I_AM_A_TEAPOT)
    public void I_AM_A_TEAPOT(){

    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(NOT_FOUND)
    public void handleNoSuchElementException(){
    }

}

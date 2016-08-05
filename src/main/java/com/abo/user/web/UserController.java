package com.abo.user.web;

import com.abo.user.domain.User;
import com.abo.user.persistance.UserRepository;
import com.abo.user.service.UserResourceAssembler;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/users",produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private @NonNull UserRepository userRepository;
    private @NonNull UserResourceAssembler userResourceAssembler;

    //TODO show automatic pagination with hateoas, classes: controller, user resource assembler, repository pageable(PageableInterface)
    @RequestMapping(method = RequestMethod.GET)
    @SuppressWarnings(value = "unchecked")
    public PagedResources<User> users(Pageable pageable, PagedResourcesAssembler assembler) {
        return assembler.toResource(userRepository.findAll(pageable), userResourceAssembler);
    }

}

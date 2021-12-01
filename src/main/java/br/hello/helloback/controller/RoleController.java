package br.hello.helloback.controller;

import br.hello.helloback.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import javax.validation.Valid;

import br.hello.helloback.repository.RoleRepository;

@RestController
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    // GET ALL

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public List<Role> getAll() {
        return roleRepository.findAll();
    };

    // GET ONE

    @RequestMapping(value = "/roles/{id}", method = RequestMethod.GET)
    public ResponseEntity<Role> getChannelByID(@PathVariable(value = "id") long id) {
        Optional<Role> response = roleRepository.findById(id);
        if (response.isPresent()) {
            return new ResponseEntity<Role>(response.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // POST

    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public Role createChannel(@Valid @RequestBody Role role) {
        return roleRepository.save(role);
    };

    // DELETE

    @RequestMapping(value = "/roles/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Role> deleteChannelByID(@PathVariable(value = "id") long id) {
        Optional<Role> response = roleRepository.findById(id);
        if (response.isPresent()) {
            roleRepository.delete(response.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    };

    // PUT

    @RequestMapping(value = "/roles/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Role> putChannelByID(@PathVariable(value = "id") long id, @Valid @RequestBody Role newRole) {
        Optional<Role> response = roleRepository.findById(id);
        if (response.isPresent()) {
            Role role = response.get();
            role.setAccessLevel(newRole.getAccessLevel());
            roleRepository.save(role);

            return new ResponseEntity<Role>(role, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}

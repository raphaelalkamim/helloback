package br.hello.helloback.controller;

import br.hello.helloback.dto.UserIdDTO;
import br.hello.helloback.entity.User;

import org.modelmapper.ModelMapper;
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

import br.hello.helloback.repository.UserRepository;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    // GET ALL

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    };

    // GET ONE

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserByID(@PathVariable(value = "id") long id) {
        Optional<User> response = userRepository.findById(id);
        if (response.isPresent()) {
            return new ResponseEntity<User>(response.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // GET - LOGIN VALIDATION

    @RequestMapping(value = "/users/{email}/{password}", method = RequestMethod.GET)
    public ResponseEntity<UserIdDTO> getValidationUser(@PathVariable(value = "email") String email,
            @PathVariable(value = "password") String password) {

        Optional<User> response = userRepository.findByEmail(email);
        ModelMapper modelMapper = new ModelMapper();
        
        if (response.isPresent()) {
            if (response.get().getPassword().equals(password)) {
                UserIdDTO userIdDTO = modelMapper.map(response.get(), UserIdDTO.class);
                return new ResponseEntity<UserIdDTO>(userIdDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // POST

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<UserIdDTO> createUser(@Valid @RequestBody User user) {

        ModelMapper modelMapper = new ModelMapper();

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new ResponseEntity<UserIdDTO>(HttpStatus.CONFLICT);

        } else {
            userRepository.save(user);
            UserIdDTO userIdDTO = modelMapper.map(user, UserIdDTO.class);
            return new ResponseEntity<UserIdDTO>(userIdDTO, HttpStatus.OK);
        }

    };

    // DELETE

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUserByID(@PathVariable(value = "id") long id) {
        Optional<User> response = userRepository.findById(id);
        if (response.isPresent()) {
            userRepository.delete(response.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    };

    // PUT

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> putByID(@PathVariable(value = "id") long id, @Valid @RequestBody User newUser) {
        Optional<User> response = userRepository.findById(id);
        if (response.isPresent()) {
            User user = response.get();
            user.setName(newUser.getName());
            user.setEmail(newUser.getEmail());
            user.setPassword(newUser.getPassword());
            userRepository.save(user);

            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}

package br.hello.helloback.controller;

import br.hello.helloback.dto.UnitDTO;
import br.hello.helloback.entity.AccessKey;
import br.hello.helloback.entity.Unit;
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

import br.hello.helloback.repository.AccessKeyRepository;
import br.hello.helloback.repository.UnitRepository;
import br.hello.helloback.repository.UserRepository;

@RestController
public class AccessKeyController {
    @Autowired
    private AccessKeyRepository accessKeyRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UserRepository userRepository;

    // GET ALL

    @RequestMapping(value = "/accessKeys", method = RequestMethod.GET)
    public List<AccessKey> getAll() {
        return accessKeyRepository.findAll();
    };

    // GET ONE

    @RequestMapping(value = "/accessKeys/{id}", method = RequestMethod.GET)
    public ResponseEntity<AccessKey> getAccessKeyByID(@PathVariable(value = "id") long id) {
        Optional<AccessKey> response = accessKeyRepository.findById(id);
        if (response.isPresent()) {
            return new ResponseEntity<AccessKey>(response.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // GET ACCESSKEY WITH USER

    @RequestMapping(value = "users/{userId}/accessKeys", method = RequestMethod.GET)
    public ResponseEntity<AccessKey> getAccessKeyFromUserByID(@PathVariable(value = "userId") long userId) {
        Optional<AccessKey> response = accessKeyRepository.findByUserId(userId);
        if (response.isPresent()) {
            return new ResponseEntity<AccessKey>(response.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // POST

    @RequestMapping(value = "units/{unitId}/accessKeys", method = RequestMethod.POST)
    public ResponseEntity<List<AccessKey>> createPost(
            @PathVariable(value = "unitId") Long unitId) {
        Optional<Unit> responseUnit = unitRepository.findById(unitId);
        List<AccessKey> accessKeys = new ArrayList<>();

        if (responseUnit.isPresent()) {
            if (accessKeyRepository.findByUnitId(unitId).isEmpty()) {
                Unit unit = responseUnit.get();
                for (int i = 0; i < unit.getMaxUsers(); i++) {
                    AccessKey accessKey = new AccessKey();
                    accessKey.setAccessCode(unit.getName() + Long.toString(i));
                    accessKey.setUnit(unit);
                    accessKeys.add(accessKey);

                    accessKeyRepository.save(accessKey);
                }
                return new ResponseEntity<List<AccessKey>>(accessKeys, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    };

    // DELETE

    @RequestMapping(value = "/accessKeys/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<AccessKey> deleteChannelByID(@PathVariable(value = "id") long id) {
        Optional<AccessKey> response = accessKeyRepository.findById(id);
        if (response.isPresent()) {
            accessKeyRepository.delete(response.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    };

    // PUT

    @RequestMapping(value = "/accessKeys/{id}", method = RequestMethod.PUT)
    public ResponseEntity<AccessKey> putChannelByID(@PathVariable(value = "id") long id,
            @Valid @RequestBody AccessKey newAccessKey) {
        Optional<AccessKey> response = accessKeyRepository.findById(id);
        if (response.isPresent()) {
            AccessKey accessKey = response.get();
            accessKey.setAccessCode(newAccessKey.getAccessCode());
            accessKeyRepository.save(accessKey);

            return new ResponseEntity<AccessKey>(accessKey, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // PUT USER USED ACCESSCODE

    @RequestMapping(value = "users/{userId}/accessKeys/{accessKeyCode}", method = RequestMethod.PUT)
    public ResponseEntity<AccessKey> putAccessKeyUser(@PathVariable(value = "accessKeyCode") String accessKeyCode,
            @PathVariable(value = "userId") Long userId) {
        Optional<AccessKey> responseKey = accessKeyRepository.findByAccessCode(accessKeyCode);
        Optional<User> responseUser = userRepository.findById(userId);
        if (responseKey.isPresent() && responseUser.isPresent()) {
            AccessKey accessKey = responseKey.get();
            if (accessKey.getUser() != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            accessKey.setUser(responseUser.get());
            accessKeyRepository.save(accessKey);
            return new ResponseEntity<AccessKey>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}

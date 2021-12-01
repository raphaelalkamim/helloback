package br.hello.helloback.controller;

import br.hello.helloback.entity.AccessKey;
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

@RestController
public class AccessKeyController {
    @Autowired
    private AccessKeyRepository accessKeyRepository;

    //GET ALL

    @RequestMapping(value = "/accessKeys", method = RequestMethod.GET)
    public List<AccessKey> getAll() { 
        return accessKeyRepository.findAll();
    };


    //GET ONE

    @RequestMapping(value = "/accessKeys/{id}", method = RequestMethod.GET)
    public ResponseEntity<AccessKey> getAccessKeyByID(@PathVariable(value = "id") long id) { 
        Optional<AccessKey> response = accessKeyRepository.findById(id);
        if (response.isPresent()) {
            return new ResponseEntity<AccessKey>(response.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }

    //POST

    @RequestMapping(value = "/accessKeys", method = RequestMethod.POST)
    public AccessKey createAccessKey(@Valid @RequestBody AccessKey accessKey) { 
        return accessKeyRepository.save(accessKey);
    };


    //DELETE

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

    //PUT

    @RequestMapping(value = "/accessKeys/{id}", method = RequestMethod.PUT)
    public ResponseEntity<AccessKey> putChannelByID(@PathVariable(value = "id") long id, @Valid @RequestBody AccessKey newAccessKey) { 
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


}

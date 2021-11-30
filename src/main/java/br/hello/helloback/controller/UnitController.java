package br.hello.helloback.controller;

import br.hello.helloback.entity.Unit;
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

import br.hello.helloback.repository.UnitRepository;

@RestController
public class UnitController {
    @Autowired
    private UnitRepository unitRepository;

    //GET ALL

    @RequestMapping(value = "/units", method = RequestMethod.GET)
    public List<Unit> getAll() { 
        return unitRepository.findAll();
    };


    //GET ONE

    @RequestMapping(value = "/units/{id}", method = RequestMethod.GET)
    public ResponseEntity<Unit> getByID(@PathVariable(value = "id") long id) { 
        Optional<Unit> response = unitRepository.findById(id);
        if (response.isPresent()) {
            return new ResponseEntity<Unit>(response.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }

    //POST

    @RequestMapping(value = "/units", method = RequestMethod.POST)
    public Unit createUnit(@Valid @RequestBody Unit unit) { 
        return unitRepository.save(unit);
    };


    //DELETE

    @RequestMapping(value = "/units/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Unit> deleteByID(@PathVariable(value = "id") long id) { 
        Optional<Unit> response = unitRepository.findById(id);
        if (response.isPresent()) {
            unitRepository.delete(response.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    };

    //PUT

    @RequestMapping(value = "/units/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Unit> putByID(@PathVariable(value = "id") long id, @Valid @RequestBody Unit newUnit) { 
        Optional<Unit> response = unitRepository.findById(id);
        if (response.isPresent()) {
            Unit unit = response.get();
            unit.setName(newUnit.getName());
            unit.setDescription(newUnit.getDescription());
            unit.setMaxUsers(newUnit.getMaxUsers());
            unitRepository.save(unit);
            
            
            return new ResponseEntity<Unit>(unit, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }


}

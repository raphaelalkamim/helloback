package br.hello.helloback.controller;

import br.hello.helloback.entity.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


import javax.validation.Valid;

import br.hello.helloback.repository.ChannelRepository;
import javassist.tools.web.BadHttpRequest;

@RestController
public class ChannelController {
    @Autowired
    private ChannelRepository channelRepository;

    // GET ALL

    @RequestMapping(value = "/channels", method = RequestMethod.GET)
    public List<Channel> getAll() {
        return channelRepository.findAll();
    };

    // GET ONE

    @RequestMapping(value = "/channels/{id}", method = RequestMethod.GET)
    public ResponseEntity<Channel> getChannelByID(@PathVariable(value = "id") long id) {
        Optional<Channel> response = channelRepository.findById(id);
        if (response.isPresent()) {
            return new ResponseEntity<Channel>(response.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // POST

    @RequestMapping(value = "/channels", method = RequestMethod.POST)
    public ResponseEntity<Channel> createChannel(@Valid @RequestBody Channel channel, BindingResult bidingResult) throws BadHttpRequest {
        if (bidingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            //TODO: Melhorar resposta (LUCA)
        }
        return new ResponseEntity<Channel>(channelRepository.save(channel), (HttpStatus.CREATED));
    };

    // DELETE

    @RequestMapping(value = "/channels/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Channel> deleteChannelByID(@PathVariable(value = "id") long id) {
        Optional<Channel> response = channelRepository.findById(id);
        if (response.isPresent()) {
            channelRepository.delete(response.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    };

    // PUT

    @RequestMapping(value = "/channels/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Channel> putChannelByID(@PathVariable(value = "id") long id,
            @Valid @RequestBody Channel newChannel) {
        Optional<Channel> response = channelRepository.findById(id);
        if (response.isPresent()) {
            Channel channel = response.get();
            channel.setName(newChannel.getName());
            channel.setDescription(newChannel.getDescription());
            channelRepository.save(channel);

            return new ResponseEntity<Channel>(channel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}

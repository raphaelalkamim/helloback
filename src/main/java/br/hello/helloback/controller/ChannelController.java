package br.hello.helloback.controller;

import br.hello.helloback.dto.ChannelDTO;
import br.hello.helloback.entity.Channel;
import br.hello.helloback.entity.Unit;

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

import br.hello.helloback.repository.ChannelRepository;
import br.hello.helloback.repository.UnitRepository;

@RestController
public class ChannelController {
    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private UnitRepository unitRepository;

    // GET ALL

    @RequestMapping(value = "/channels", method = RequestMethod.GET)
    public List<Channel> getAll() {
        return channelRepository.findAll();
    };

    // GET ALL CHANNELS BY UNIT

    @RequestMapping(value = "units/{id}/channels", method = RequestMethod.GET)
    public ResponseEntity<List<ChannelDTO>> getAllPostsChannel(@PathVariable(value = "id") Long id) {
        List<Channel> channels = new ArrayList<>();
        ArrayList<ChannelDTO> retorno = new ArrayList<ChannelDTO>();
        ModelMapper modelMapper = new ModelMapper();
        if (unitRepository.findById(id).isPresent()) {
            channelRepository.findByUnitId(id).forEach(channels::add);
            for (Channel channelItem : channels) {
                ChannelDTO channelDTO = modelMapper.map(channelItem, ChannelDTO.class);
                retorno.add(channelDTO);
            }
            Collections.reverse(retorno);
            return new ResponseEntity<List<ChannelDTO>>(retorno, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    // GET ONE

    @RequestMapping(value = "/channels/{id}", method = RequestMethod.GET)
    public ResponseEntity<Channel> getChannelByID(@PathVariable(value = "id") Long id) {
        Optional<Channel> response = channelRepository.findById(id);
        if (response.isPresent()) {
            return new ResponseEntity<Channel>(response.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // POST

    @RequestMapping(value = "/units/{unitId}/channels", method = RequestMethod.POST)
    public ResponseEntity<Channel> createChannel(@Valid @RequestBody Channel channel,
            @PathVariable(value = "unitId") Long unitId) {
        Optional<Unit> response = unitRepository.findById(unitId);
        if (response.isPresent()) {
            channel.setUnit(response.get());
            return new ResponseEntity<Channel>(channelRepository.save(channel), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    };

    // DELETE

    @RequestMapping(value = "/channels/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Channel> deleteChannelByID(@PathVariable(value = "id") Long id) {
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
    public ResponseEntity<Channel> putChannelByID(@PathVariable(value = "id") Long id,
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

package br.hello.helloback.controller;

import br.hello.helloback.dto.ChannelUserRoleDTO;
import br.hello.helloback.entity.Channel;
import br.hello.helloback.entity.ChannelUserRole;
import br.hello.helloback.entity.Role;
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

import br.hello.helloback.repository.ChannelRepository;
import br.hello.helloback.repository.ChannelUserRoleRepository;
import br.hello.helloback.repository.RoleRepository;
import br.hello.helloback.repository.UserRepository;


@RestController
public class ChannelUserRoleController {
    @Autowired
    private ChannelUserRoleRepository channelUserRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChannelRepository channelRepository;


    // GET ALL

    @RequestMapping(value = "/CURLink", method = RequestMethod.GET)
    public List<ChannelUserRole> getAll() {
        return channelUserRoleRepository.findAll();
    };

    // GET ONE

    @RequestMapping(value = "/channels/{channelId}/users/{userID}/CURLink", method = RequestMethod.GET)
    public ResponseEntity<ChannelUserRoleDTO> getRoleUserChannel(
        @PathVariable(value = "channelId") Long channelId,
        @PathVariable(value = "userID") Long userId) {

        Optional<ChannelUserRole> response = channelUserRoleRepository.findByUserIdAndChannelId(userId, channelId);
        ModelMapper modelMapper = new ModelMapper();
        if (response.isPresent()) {
            ChannelUserRoleDTO channelUserRoleDTO = modelMapper.map(response.get(), ChannelUserRoleDTO.class);
            return new ResponseEntity<ChannelUserRoleDTO>(channelUserRoleDTO, HttpStatus.OK);
        } else {
            ChannelUserRoleDTO leitor = new ChannelUserRoleDTO();
            Optional<User> responseUser = userRepository.findById(userId);
            Optional<Channel> responseChannel = channelRepository.findById(channelId);
            Optional<Role> responseRole = roleRepository.findById((long) 1);

            if (responseUser.isPresent() && responseChannel.isPresent() && responseRole.isPresent()) {
                leitor.setRole(responseRole.get());
                return new ResponseEntity<ChannelUserRoleDTO>(leitor ,HttpStatus.OK);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            }
            
        }

    }

    // POST

    @RequestMapping(value = "/channels/{channelId}/users/{userId}/roles/{roleId}/CURLink", method = RequestMethod.POST)
    public ResponseEntity<ChannelUserRole> postRoleUserChannel(
        @PathVariable(value = "channelId") Long channelId,
        @PathVariable(value = "userId") Long userId,
        @PathVariable(value = "roleId") Long roleId) {

        Optional<User> responseUser = userRepository.findById(userId);
        Optional<Channel> responseChannel = channelRepository.findById(channelId);
        Optional<Role> responseRole = roleRepository.findById(roleId);
        Optional<ChannelUserRole> responseCURLink = channelUserRoleRepository.findByUserIdAndChannelId(userId, channelId);

        if (!responseCURLink.isPresent()) {
            if (responseUser.isPresent() && responseChannel.isPresent() && responseRole.isPresent()) {
                ChannelUserRole newCURLink = new ChannelUserRole();
                newCURLink.setRole(responseRole.get());
                newCURLink.setUser(responseUser.get());
                newCURLink.setChannel(responseChannel.get());
                return new ResponseEntity<ChannelUserRole>(channelUserRoleRepository.save(newCURLink), HttpStatus.OK);
    
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    
            }
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
    };

    // DELETE

    @RequestMapping(value = "/CURLink/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ChannelUserRole> deleteLinkByID(@PathVariable(value = "id") Long id) {
        Optional<ChannelUserRole> response = channelUserRoleRepository.findById(id);
        if (response.isPresent()) {
            channelUserRoleRepository.delete(response.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    };

    // PUT

    @RequestMapping(value = "/CURLink/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ChannelUserRole> putLinkByID(@PathVariable(value = "id") Long id,
            @Valid @RequestBody ChannelUserRole newChannelUserRole) {
        Optional<ChannelUserRole> response = channelUserRoleRepository.findById(id);
        if (response.isPresent()) {
            ChannelUserRole channelUserRole = response.get();
            channelUserRole.setRole(newChannelUserRole.getRole());
            channelUserRole.setChannel(newChannelUserRole.getChannel());
            channelUserRole.setUser(newChannelUserRole.getUser());
            channelUserRoleRepository.save(channelUserRole);

            return new ResponseEntity<ChannelUserRole>(channelUserRole, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}

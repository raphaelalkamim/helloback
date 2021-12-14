package br.hello.helloback.controller;

import br.hello.helloback.dto.PostDTO;
import br.hello.helloback.dto.UserDTO;
import br.hello.helloback.entity.AccessKey;
import br.hello.helloback.entity.Channel;
import br.hello.helloback.entity.Notification;
import br.hello.helloback.entity.Post;
import br.hello.helloback.entity.Unit;
import br.hello.helloback.entity.User;
import br.hello.helloback.entity.Widget;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.validation.Valid;

import br.hello.helloback.repository.AccessKeyRepository;
import br.hello.helloback.repository.ChannelRepository;
import br.hello.helloback.repository.PostRepository;
import br.hello.helloback.repository.UserRepository;

@RestController
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private AccessKeyRepository accessKeyRepository;

    // GET ALL

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public List<Post> getAll() {
        return postRepository.findAll();
    };

    // GET ALL POSTS BY CHANNEL

    @RequestMapping(value = "channels/{id}/posts", method = RequestMethod.GET)
    public ResponseEntity<List<PostDTO>> getAllPostsChannel(@PathVariable(value = "id") Long id) {
        List<Post> posts = new ArrayList<>();
        ArrayList<PostDTO> retorno = new ArrayList<PostDTO>();
        ModelMapper modelMapper = new ModelMapper();
        if (channelRepository.findById(id).isPresent()) {
            postRepository.findByChannelId(id).forEach(posts::add);
            for (Post postItem : posts) {
                PostDTO postDTO = modelMapper.map(postItem, PostDTO.class);
                postDTO.setUser(modelMapper.map(postItem.getUser(), UserDTO.class));
                retorno.add(postDTO);
            }
            Collections.reverse(retorno);
            return new ResponseEntity<List<PostDTO>>(retorno, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // GET WIDGET

    @RequestMapping(value = "users/{userId}/lastPost", method = RequestMethod.GET)
    public ResponseEntity<Widget> getWidget(@PathVariable(value = "userId") Long userId) {
        Optional<AccessKey> response = accessKeyRepository.findByUserId(userId);
        if (response.isPresent()) {
            Unit unit = response.get().getUnit();
            Post lastPost = findRecentByUnit(unit);

            while (unit != null) {
                Post lastPostUnit = findRecentByUnit(unit);

                if (lastPostUnit.getId().longValue() > lastPost.getId().longValue()) {
                    lastPost = lastPostUnit;
                }
                unit = unit.getUnitMother();
            }

            Widget widget = new Widget();
            widget.setChannelName(lastPost.getChannel().getName());
            widget.setContent(lastPost.getContent());

            return new ResponseEntity<Widget>(widget, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET ONE

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public ResponseEntity<Post> getByID(@PathVariable(value = "id") Long id) {
        Optional<Post> response = postRepository.findById(id);
        if (response.isPresent()) {
            return new ResponseEntity<Post>(response.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // POST

    @RequestMapping(value = "/channels/{channelId}/users/{userID}/posts", method = RequestMethod.POST, consumes = {"application/json", "application/x-www-form-urlencoded"},
    produces = {"application/json", "application/x-www-form-urlencoded"})
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody Post post,
            @PathVariable(value = "channelId") Long channelId,
            @PathVariable(value = "userID") Long userId) {
        Optional<Channel> responseChannel = channelRepository.findById(channelId);
        Optional<User> responseUser = userRepository.findById(userId);
        ModelMapper modelMapper = new ModelMapper();
        if (responseChannel.isPresent() && responseUser.isPresent()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            post.setCreationTime(dtf.format(now));
            post.setEditionTime(dtf.format(now));
            post.setChannel(responseChannel.get());
            post.setUser(responseUser.get());
            postRepository.save(post);
            PostDTO postDTO = modelMapper.map(post, PostDTO.class);
            postDTO.setUser(modelMapper.map(post.getUser(), UserDTO.class));

            Notification notification = new Notification();
            notification.setChannelName(responseChannel.get().getName());
            notification.setContent(post.getContent());
            notification.setUsersDomains(notificationPost(responseChannel.get()));
            return new ResponseEntity<PostDTO>(postDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    };

    // DELETE

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Post> deleteByID(@PathVariable(value = "id") Long id) {
        Optional<Post> response = postRepository.findById(id);
        if (response.isPresent()) {
            postRepository.delete(response.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    };

    // PUT

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Post> putByID(@PathVariable(value = "id") Long id, @Valid @RequestBody Post newPost) {
        Optional<Post> response = postRepository.findById(id);
        if (response.isPresent()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            Post post = response.get();
            post.setContent(newPost.getContent());
            post.setEditionTime(dtf.format(now));
            postRepository.save(post);

            return new ResponseEntity<Post>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public ArrayList<String> notificationPost(Channel channel) {
        Unit unit = channel.getUnit();
        ArrayList<String> codesDomain = new ArrayList<>();
        getChildren(unit, codesDomain);
        return codesDomain;
    }

    public void getChildren(Unit unit, List<String> list) {
        List<Unit> children = new ArrayList<>(unit.getUnits());
        List<AccessKey> chaves = accessKeyRepository.findByUnitId(unit.getId());

        for (int j = 0; j < chaves.size(); j++) {
            if (chaves.get(j).getUser() != null) {
                list.add(chaves.get(j).getUser().getDeviceID());
            }
        }

        if (children != null) {
            for (int i = 0; i < children.size(); i++) {
                getChildren(children.get(i), list);
            }
        }
    }

    public Post findRecentByUnit(Unit unit) {
        List<Channel> channels = new ArrayList<>(unit.getChannels());
        Post post = new Post();
        post.setId(Long.valueOf(0));

        for (int i = 0; i < channels.size(); i++) {
            List<Post> posts = new ArrayList<>(channels.get(i).getPosts());
            System.out.println(posts);
            for (int j = 0; j < posts.size(); j++) {
                if (posts.get(j).getId().longValue() > post.getId().longValue()) {
                    post = (posts.get(j));
                }
            }
        }
        System.out.println(post);
        return post;
    }
}

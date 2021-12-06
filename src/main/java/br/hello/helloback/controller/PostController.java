package br.hello.helloback.controller;

import br.hello.helloback.entity.Channel;
import br.hello.helloback.entity.Post;
import br.hello.helloback.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // GET ALL

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public List<Post> getAll() {
        return postRepository.findAll();
    };

    // GET ALL POSTS BY CHANNEL

    @RequestMapping(value = "channels/{id}/posts", method = RequestMethod.GET)
    public ResponseEntity<List<Post>> getAllPostsChannel(@PathVariable(value = "id") Long id) {
        List<Post> posts = new ArrayList<>();
        if (channelRepository.findById(id).isPresent()) {
            postRepository.findByChannelId(id).forEach(posts::add);
            return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
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

    @RequestMapping(value = "/channels/{channelId}/users/{userID}/posts", method = RequestMethod.POST)
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post,
            @PathVariable(value = "channelId") Long channelId,
            @PathVariable(value = "userID") Long userId) {
        Optional<Channel> responseChannel = channelRepository.findById(channelId);
        Optional<User> responseUser = userRepository.findById(userId);

        if (responseChannel.isPresent() && responseUser.isPresent()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            post.setCreationTime(dtf.format(now));
            post.setEditionTime(dtf.format(now));
            post.setChannel(responseChannel.get());
            post.setUser(responseUser.get());
            return new ResponseEntity<Post>(postRepository.save(post), HttpStatus.OK);
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

    public void notificationPost() {

    }

}

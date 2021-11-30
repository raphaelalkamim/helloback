package br.hello.helloback.controller;

import br.hello.helloback.entity.Post;
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

import br.hello.helloback.repository.PostRepository;

@RestController
public class PostController {
    @Autowired
    private PostRepository postRepository;

    // GET ALL

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public List<Post> getAll() {
        return postRepository.findAll();
    };

    // GET ONE

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public ResponseEntity<Post> getByID(@PathVariable(value = "id") long id) {
        Optional<Post> response = postRepository.findById(id);
        if (response.isPresent()) {
            return new ResponseEntity<Post>(response.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // POST

    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public Post createUnit(@Valid @RequestBody Post post) {
        return postRepository.save(post);
    };

    // DELETE

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Post> deleteByID(@PathVariable(value = "id") long id) {
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
    public ResponseEntity<Post> putByID(@PathVariable(value = "id") long id, @Valid @RequestBody Post newPost) {
        Optional<Post> response = postRepository.findById(id);
        if (response.isPresent()) {
            Post post = response.get();
            post.setContent(newPost.getContent());
            post.setEditionTime(newPost.getEditionTime());
            postRepository.save(post);

            return new ResponseEntity<Post>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}

package br.hello.helloback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.hello.helloback.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    public List<Post> findByChannelId(Long id);
}

package br.hello.helloback.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.hello.helloback.entity.Channel;
import br.hello.helloback.entity.Post;
import net.bytebuddy.asm.Advice.OffsetMapping.Sort;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    public List<Post> findByChannelId(Long id);
}

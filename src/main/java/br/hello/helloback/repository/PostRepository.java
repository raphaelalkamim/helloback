package br.hello.helloback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.hello.helloback.entity.Channel;
import br.hello.helloback.entity.Post;
import net.bytebuddy.asm.Advice.OffsetMapping.Sort;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}

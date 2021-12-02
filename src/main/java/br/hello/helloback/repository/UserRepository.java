package br.hello.helloback.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.hello.helloback.entity.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    public Optional<User> findByEmail(String email);
}

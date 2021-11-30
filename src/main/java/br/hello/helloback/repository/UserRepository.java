package br.hello.helloback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.hello.helloback.entity.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

}

package br.hello.helloback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.hello.helloback.entity.AccessKey;

@Repository
public interface AccessKeyRepository extends JpaRepository <AccessKey, Long > {

}

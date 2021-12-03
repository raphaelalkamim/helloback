package br.hello.helloback.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.hello.helloback.entity.AccessKey;
import br.hello.helloback.entity.User;

@Repository
public interface AccessKeyRepository extends JpaRepository<AccessKey, Long> {

    public Optional<AccessKey> findByUserId(Long id);

    public Optional<AccessKey> findByAccessCode(String accessCode);

    public List<AccessKey> findByUnitId(Long id);

}

package br.hello.helloback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.hello.helloback.entity.Channel;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    public List<Channel> findByUnitId(Long id);

}

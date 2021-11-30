package br.hello.helloback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.hello.helloback.entity.Channel;

@Repository
public interface ChannelRepository extends JpaRepository <Channel, Long > {

}

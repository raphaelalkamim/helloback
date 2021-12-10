package br.hello.helloback.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.hello.helloback.entity.ChannelUserRole;

@Repository
public interface ChannelUserRoleRepository extends JpaRepository<ChannelUserRole, Long> {
    public Optional<ChannelUserRole> findByUserIdAndChannelId(Long userId, Long channelId);
}

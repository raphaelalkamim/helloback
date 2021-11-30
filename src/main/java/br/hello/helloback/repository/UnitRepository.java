package br.hello.helloback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.hello.helloback.entity.Unit;

@Repository
public interface UnitRepository extends JpaRepository <Unit, Long > {

}

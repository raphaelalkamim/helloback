package br.hello.helloback.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.persistence.Column;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence")
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "AccessLevel não pode ser nulo")
    private String accessLevel;


    public Role() {}

    public Role(Long id, String accessLevel) {
        this.id = id;
        this.accessLevel = accessLevel;

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }
}

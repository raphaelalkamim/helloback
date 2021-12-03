package br.hello.helloback.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    
    @Column(nullable = false)
    @NotBlank(message = "Nome não pode ser nulo")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Email não pode ser nulo")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "senha não pode ser nulo")
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "domain não pode ser nulo")
    private String domain;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}



package br.hello.helloback.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "accessKey")
public class AccessKey {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accessKey_sequence")
    private long id;
    
    @Column(nullable = false)
    @NotBlank(message = "accessCode não pode ser nulo")
    private String accessCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    public AccessKey() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public Unit getUnit() {
        return unit;
    }

    public User getUser() {
        return user;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setUser(User user) {
        this.user = user;
    }
}



package br.hello.helloback.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "channel")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "channel_sequence")
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Nome não pode ser nulo")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Descrição não pode ser nulo")
    private String description;

    // @OneToMany(mappedBy = "channel", fetch = FetchType.LAZY, cascade =
    // CascadeType.ALL)
    // private List<Post> posts;

    public Channel() {
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // public List<Post> getPosts() {
    // return posts;
    // }

    // public void setPosts(List<Post> posts) {
    // this.posts = posts;
    // }

}

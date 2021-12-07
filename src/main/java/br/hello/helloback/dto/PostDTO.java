package br.hello.helloback.dto;

public class PostDTO {
    Long id;
    String content;
    String creationTime;
    String editionTime;
    UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getEditionTime() {
        return editionTime;
    }

    public void setEditionTime(String editionTime) {
        this.editionTime = editionTime;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}

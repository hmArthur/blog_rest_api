package br.com.arthur.projeto.model.dto;

import javax.validation.constraints.NotEmpty;

public class CommentDto {
    private Long id;
    private String username;
    @NotEmpty 
    private String content;
    
    public String getUsername() {
        return username;
    }
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
    public void setUsername(String username) {
        this.username = username;
    }
}

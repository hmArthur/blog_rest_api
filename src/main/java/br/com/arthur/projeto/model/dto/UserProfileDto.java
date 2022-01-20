package br.com.arthur.projeto.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.arthur.projeto.model.User;

public class UserProfileDto {

    private String username;
    private String userImage;
    private List<PostDto> posts;

    public UserProfileDto(User user) {
        this.setUsername(user.getUsername());
        this.setUserImage(user.getUserImageUrl());
        this.setPosts(user.getPosts().stream().map(PostDto::new).collect(Collectors.toList()));
    }

    public UserProfileDto () {}

    public List<PostDto> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDto> posts) {
        this.posts = posts;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

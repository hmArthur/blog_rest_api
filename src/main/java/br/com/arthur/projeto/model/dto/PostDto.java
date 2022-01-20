package br.com.arthur.projeto.model.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.arthur.projeto.model.Comment;
import br.com.arthur.projeto.model.Post;
import br.com.arthur.projeto.model.User;

public class PostDto {

    private Long id;
    private String title;
    private String username;
    private String category;
    private String content;
    private String imageUrl;
	private List<CommentDto> comments;
    
    public PostDto( String title, User user, String category, String content, String imageUrl,
            List<Comment> comments) {
        this.setTitle(title);
        this.setUsername(user.getUsername());
        this.setCategory(category);
        this.setContent(content);
        this.setImageUrl(imageUrl);
        this.setComments(convertComment(comments));
    }

    public PostDto(Post post) {
        this.setId(post.getId());
        this.setTitle(post.getTitle());
        this.setUsername(post.getUser().getUsername());
        this.setCategory(post.getCategory());
        this.setContent(post.getContent());
        this.setImageUrl(post.getImageUrl());
        this.setComments(convertComment(post.getComments()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static List<PostDto> convert(List<Post> postsEntity) {
        List<PostDto> list = new ArrayList<PostDto>();

        postsEntity.forEach(obj -> {
            PostDto post = new PostDto(
                obj.getTitle(),
                obj.getUser(),
                obj.getCategory(),
                obj.getContent(),
                obj.getImageUrl(),
                obj.getComments());
            post.setId(obj.getId());
                
            list.add(post);    
        });

        return list;
    }

    public static List<CommentDto> convertComment(List<Comment> postsEntity) {
        List<CommentDto> list = new ArrayList<CommentDto>();

        postsEntity.forEach(obj -> {
            CommentDto post = new CommentDto();
            post.setContent(obj.getContent());
            post.setUsername(obj.getUsername());
            post.setId(obj.getId());
            list.add(post);    
        });

        return list;
    }

}
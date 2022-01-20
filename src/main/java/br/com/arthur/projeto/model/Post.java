package br.com.arthur.projeto.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne()
    private User user;
    private String username;
    private String category;
    private String content;
    private String imageUrl;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();
    // private List<Like> likes = new ArrayList<>();

    public Post() {}

    public Post(String title, User user, String category, String content, String imageUrl) {
        this.title = title;
        this.user = user;
        this.category = category;
        this.content = content;
        this.imageUrl = imageUrl;
        this.username = user.getUsername();
    }

    public String getTitle() {
        return title;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return this.user;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public int addCommentAndReturnId(Comment comment) {
        this.comments.add(comment);

        return (this.comments.indexOf(comment) + 1);
    }

    public Long getId() {
        return this.id;
    }

    public void deleteComment(int idOfComment) {
        this.comments.remove((idOfComment-1));
    }

    public Comment getComment(int idOfComments) {
        return this.getComments().get((idOfComments - 1));
    }

    public void updateComment(Comment newComment) {
        this.comments.get((newComment.getId().intValue() - 1)).setContent(newComment.getContent());
    }
}

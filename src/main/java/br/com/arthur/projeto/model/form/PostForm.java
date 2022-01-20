package br.com.arthur.projeto.model.form;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.arthur.projeto.model.Comment;
import br.com.arthur.projeto.model.Post;
import br.com.arthur.projeto.model.User;

public class PostForm {
    private Long id; 
    @NotEmpty @Size(min = 3, max = 25)
    private String title;
    private String user;
    @NotEmpty @Size(min = 3, max = 25)
    private String category;
    @NotEmpty  @Size(min  = 3, max = 250)
    private String content;
    @NotNull
    private String imageUrl;
	private List<Comment> comments;
    
    public PostForm( String title, String user, String category, String content, String imageUrl,
            List<Comment> comments) {
        this.title = title;
        this.user = user;
        this.category = category;
        this.content = content;
        this.imageUrl = imageUrl;
        this.comments = comments;
    }

    public Post converter(User user) {
        return new Post(
            this.title,
            user,
            this.category, 
            this.content, 
            this.imageUrl
        );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PostForm other = (PostForm) obj;
        if (id == null)
        {
            if (other.id != null)
                return false;
            } else if (!id.equals(other.id))
                return false;
            return true;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }
    
    public String getContent() {
        return content;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }

    public void setUser(String string) {
        this.user = string;
    }

}
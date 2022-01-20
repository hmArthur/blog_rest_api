package br.com.arthur.projeto.model.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.arthur.projeto.model.User;

public class UserForm {
    @NotEmpty @Size(min = 4)
    private String username;
    @NotEmpty @NotNull @Email
    private String email;
    @NotEmpty @NotNull @Size(min = 4)
    private String password;
    @NotEmpty
    private String userImageUrl;

    public UserForm(String username, String email, String password, String userImageUrl) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userImageUrl = userImageUrl;
    }

    public UserForm() {
        
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User convert() {
        User user = new User(
            this.username,
            this.password,
            this.email,
            this.userImageUrl
        );

        user.setRoles("USER,");

        return user;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }
}

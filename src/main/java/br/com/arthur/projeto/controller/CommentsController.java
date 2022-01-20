package br.com.arthur.projeto.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.arthur.projeto.model.Comment;
import br.com.arthur.projeto.model.Post;
import br.com.arthur.projeto.model.User;
import br.com.arthur.projeto.model.dto.CommentDto;
import br.com.arthur.projeto.model.dto.PostDto;
import br.com.arthur.projeto.repository.BlogRepository;
import br.com.arthur.projeto.repository.UserRepository;
import br.com.arthur.projeto.service.UserFromHeaderService;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentsController {

    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserFromHeaderService userFromHeaderService;

    @PostMapping
    @Transactional
    private ResponseEntity<?> addComment(
        @RequestBody @Valid CommentDto comment,
        @PathVariable(name = "postId") Long postId,
        HttpServletRequest request,
        UriComponentsBuilder uriBuilder) {

        Optional<Post> post = blogRepository.findById(postId);
        User user = userFromHeaderService.catchUser(userRepository, request);
        comment.setUsername(user.getUsername());

        if(post.isPresent()) {
            Comment commentNew = new Comment(comment);
            int idOfComment = post.get().addCommentAndReturnId(commentNew);

            blogRepository.save(post.get());
            
            URI uri = uriBuilder
                        .path("/posts/" + post.get().getId() + "/")
                        .path("comments/{id}")
                        .buildAndExpand(idOfComment)
                        .toUri();

            return ResponseEntity.created(uri).body(new PostDto(post.get()));
        }

        Map<String,String> response = new HashMap<String, String>();
        response.put("error", "Recurso não encontrado");
        return ResponseEntity.status(404).body(response);
    }

    @DeleteMapping("/{id}")
    @Transactional
    private ResponseEntity<?> deleteComment(@PathVariable(name = "postId") Long postId, @PathVariable(name = "id") int id, HttpServletRequest request) {
        Optional<Post> post = blogRepository.findById(postId);
        User user = userFromHeaderService.catchUser(userRepository, request);
        
        if (post.isPresent() && post.get().getUser().equals(userRepository.findByEmail(user.getEmail()).get())) {
            post.get().deleteComment(id);

            blogRepository.save(post.get());
			
            return ResponseEntity.status(200).build();
        }

        Map<String,String> response = new HashMap<String, String>();
        response.put("error", "Recurso não encontrado ou você não tem permissão para alterá-lo");
        return ResponseEntity.status(404).body(response);
    }

   @PutMapping("{id}")
   @Transactional
   private ResponseEntity<?> updateComment(
        @RequestBody @Valid CommentDto comment,
        @PathVariable(name = "postId") Long postId,
        @PathVariable(name = "id") int id,
        HttpServletRequest request) {

        Optional<Post> newPost = blogRepository.findById(postId);
        User user = userFromHeaderService.catchUser(userRepository, request);

        if(newPost.isPresent() && newPost.get().getUser().equals(userRepository.findByEmail(user.getEmail()).get())) {
           Comment newComment = newPost.get().getComment(id);
           newComment.setContent(comment.getContent());
           newComment.setId(Long.valueOf(id));

           newPost.get().updateComment(newComment);

           blogRepository.save(newPost.get());
           return ResponseEntity.status(200).body(new PostDto(newPost.get()));
        } 

        Map<String,String> response = new HashMap<String, String>();
        response.put("error", "Recurso não encontrado ou você não tem permissão para alterá-lo!");
        return ResponseEntity.status(404).body(response);
    }
}   

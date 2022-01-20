package br.com.arthur.projeto.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.arthur.projeto.model.Post;
import br.com.arthur.projeto.model.User;
import br.com.arthur.projeto.model.dto.PostDto;
import br.com.arthur.projeto.model.form.PostForm;
import br.com.arthur.projeto.repository.BlogRepository;
import br.com.arthur.projeto.repository.UserRepository;
import br.com.arthur.projeto.repository.specification.SpecificationPost;
import br.com.arthur.projeto.service.UserFromHeaderService;

@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserFromHeaderService userFromHeaderService;

    @GetMapping
    private ResponseEntity<Page> getPosts(
        @RequestParam(required = false) String title, 
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String username,
        @PageableDefault(page = 0, size = 10) Pageable pageable) {

        if (title != null || category != null || username != null) {

            Page<Post> posts = blogRepository.findAll(Specification
            .where(
                    SpecificationPost.title(title)
                    .or(SpecificationPost.category(category))
                    .or(SpecificationPost.user(username))
            ), pageable);

            return ResponseEntity.ok(posts.map(PostDto::new));
        } else {
            Page<Post> posts = blogRepository.findAll(pageable);
            return ResponseEntity.ok(posts.map(PostDto::new));
        }
    }

    @PostMapping
    @Transactional
    private ResponseEntity<?> savePost(@Valid @RequestBody PostForm post, HttpServletRequest request, UriComponentsBuilder uriBuilder) {
        User user = userFromHeaderService.catchUser(userRepository, request);

        post.setUser(user.getUsername());

        Post postNew = post.converter(user);
        blogRepository.save(postNew);

        URI uri = uriBuilder.path("/posts/{id}").buildAndExpand(postNew.getId()).toUri();
        return ResponseEntity.created(uri).body(new PostDto(postNew));
    }

    @PutMapping("/{id}")
    @Transactional
    private ResponseEntity<?> putPost(@PathVariable Long id, @Valid @RequestBody PostForm post, HttpServletRequest request) {
        Optional<Post> newPost = blogRepository.findById(id);
        User user = userFromHeaderService.catchUser(userRepository, request);
        
        if(newPost.isPresent() && newPost.get().getUser().equals(userRepository.findByEmail(user.getEmail()).get())) {
            newPost.get().setTitle(post.getTitle());
            newPost.get().setCategory(post.getCategory());
            newPost.get().setImageUrl(post.getImageUrl());
            newPost.get().setContent(post.getContent());
   
            blogRepository.save(newPost.get());
            return ResponseEntity.status(200).body(new PostDto(newPost.get()));
        } 

        Map<String,String> response = new HashMap<String, String>();
        response.put("error", "Recurso não encontrado ou você não tem permissão para alterá-lo");
        return ResponseEntity.status(404).body(response);
    }

    @DeleteMapping("/{id}")
    @Transactional
    private ResponseEntity<?> deletePost(@PathVariable Long id, HttpServletRequest request) {
        Optional<Post> post = blogRepository.findById(id);
        User user = userFromHeaderService.catchUser(userRepository, request);

		if (post.isPresent() && post.get().getUser().equals(userRepository.findByEmail(user.getEmail()).get())) {
			blogRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

        Map<String,String> response = new HashMap<String, String>();
        response.put("error", "Recurso não encontrado ou você não tem permissão para alterá-lo");
        return ResponseEntity.status(404).body(response);
    }
}

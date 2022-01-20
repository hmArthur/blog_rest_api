package br.com.arthur.projeto.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.arthur.projeto.model.User;
import br.com.arthur.projeto.model.dto.UserProfileDto;
import br.com.arthur.projeto.model.form.UserForm;
import br.com.arthur.projeto.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping
    @Transactional
    private ResponseEntity<?> userLogon(@Valid @RequestBody UserForm user, UriComponentsBuilder uriBuilder) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User newUser = user.convert();
        Map<String,String> response = new HashMap<String, String>();
        

        if(userRepository.findByEmail(user.getEmail()).isPresent()  || userRepository.findByUsername(user.getUsername()).isPresent()) { 
            response.put("error", "Email ou nome de usuário já cadastrados no sistema!");
            return ResponseEntity.status(422).body(response);
        }
        
        userRepository.save(newUser);

        URI uri = uriBuilder.path("/blog/{id}").buildAndExpand(newUser.getId()).toUri();
        response.put("success", "Usuário " + newUser.getUsername() + " criado com sucesso");

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{username}")
    private ResponseEntity<?> getUserProfile(@PathVariable(name = "username") String username) {
        Optional<User> user = userRepository.findByUsername(username);
        
        if (user.isPresent()) 
            return ResponseEntity.ok(new UserProfileDto(user.get()));

        Map<String,String> response = new HashMap<String, String>();
        response.put("error", "Recurso não encontrado!");
        return ResponseEntity.status(404).body(response);
    }
}

package br.com.arthur.projeto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.arthur.projeto.model.MyUserDetails;
import br.com.arthur.projeto.model.User;
import br.com.arthur.projeto.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        user.orElseThrow(() ->
             new UsernameNotFoundException(email + " não encontrado.")
        );

        return new MyUserDetails(user.get());
    }
    
}

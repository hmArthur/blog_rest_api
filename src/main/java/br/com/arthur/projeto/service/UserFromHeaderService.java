package br.com.arthur.projeto.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import br.com.arthur.projeto.model.User;
import br.com.arthur.projeto.repository.UserRepository;

@Service
public class UserFromHeaderService {
    public User catchUser(UserRepository userRepository ,HttpServletRequest request) {
        String string = new String(Base64Utils.decodeFromString(request.getHeader("Authorization").substring(6)));
        String[] list = string.split(":");
        User user = userRepository.findByEmail(list[0]).get();
        return user;
    }
}

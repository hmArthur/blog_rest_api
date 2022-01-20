package br.com.arthur.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.arthur.projeto.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
}

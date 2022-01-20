package br.com.arthur.projeto.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import br.com.arthur.projeto.model.Post;

public class SpecificationPost {
    public static Specification<Post> title(String title) {
		return (root, criteriaQuery, criteriaBuilder) -> 
			criteriaBuilder.like(root.get("title"), "%" + title + "%");
	}

    public static Specification<Post> category(String category) {
        return (root, criteriaQuery, criteriaBuilder) -> 
            criteriaBuilder.like(root.get("category"), "%" + category + "%");
    }

    public static Specification<Post> user(String username) {
        return (root, criteriaQuery, criteriaBuilder) -> 
            criteriaBuilder.like(root.get("username"), "%" + username + "%");
    }
}

package app.demo.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import app.demo.modal.Article;
import jakarta.persistence.criteria.Predicate;

public class ArticleFilter {

    public static Specification<Article> articleFilter(String search) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (search != null && !search.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + search.toLowerCase() + "%"));

            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Article> articleFilterPopular() {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            query.orderBy(cb.desc(root.get("views")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
        
    }

}

package app.demo.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import app.demo.modal.Comment;
import jakarta.persistence.criteria.Predicate;

public class CommentFilter {
        public static Specification<Comment> commentFilter(String articleId) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (articleId != null && !articleId.isBlank()) {
                predicates.add(cb.equal(root.get("article").get("id"), UUID.fromString(articleId)));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

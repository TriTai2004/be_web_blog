package app.demo.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import app.demo.modal.Comment;
import jakarta.persistence.criteria.Predicate;

public class CommentFilter {
    public static Specification<Comment> commentFilter(String articleId, String parentId) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (articleId != null && !articleId.isBlank()) {
                predicates.add(cb.equal(root.get("article").get("id"), UUID.fromString(articleId)));
            }

            if (parentId != null && !parentId.isBlank()) {
                try {
                    UUID parentUUID = UUID.fromString(parentId);
                    predicates.add(cb.equal(root.get("parent").get("id"), parentUUID));
                } catch (IllegalArgumentException e) {
                    return cb.disjunction();
                }
            } else {
                // không có parentId → lấy comment gốc
                predicates.add(cb.isNull(root.get("parent")));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}

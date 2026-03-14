package app.demo.mapper;

import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import app.demo.dto.req.ArticleRequest;
import app.demo.dto.res.ArticleResponse;
import app.demo.modal.Article;
import app.demo.modal.Category;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArticleMapper {
    
    @Mapping(target = "likes", source = ".", qualifiedByName = "mapLikes")
    ArticleResponse toResponse (Article article);

    @Named("mapLikes")
    default Integer mapLikes(Article article) {
        if (article.getLikes() != null) {
            return article.getLikes().size();
        }
        return 0;
    }
 
    List<ArticleResponse> toResponses (List<Article> articles);


    @Mapping(target = "category", source = ".", qualifiedByName = "mapCategory")

    Article toEntity (ArticleRequest request);


    @Named("mapCategory")
    default Category mapCategory(ArticleRequest request){

        if (request.getCategoryId() != null && !request.getCategoryId().isBlank()) {
            Category category = new Category();
            category.setId(UUID.fromString(request.getCategoryId()));
            return category;
        }
        return null;

    }


}

package app.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import app.demo.dto.res.CategoryResponse;
import app.demo.modal.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(Category category);

    List<CategoryResponse> toResponseList(List<Category> categories);

    
}

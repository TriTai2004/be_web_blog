package app.demo.service.Iface;

import app.demo.dto.req.CategoryRequest;
import app.demo.dto.res.CategoryResponse;

public interface ICategoryService extends IService<CategoryResponse, CategoryRequest, String>{
    CategoryResponse findBySlug(String slug);
}

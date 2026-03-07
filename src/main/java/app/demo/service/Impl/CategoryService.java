package app.demo.service.Impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import app.demo.dto.req.CategoryRequest;
import app.demo.dto.res.AccountResponse;
import app.demo.dto.res.CategoryResponse;
import app.demo.exception.ResourceNotFoundException;
import app.demo.mapper.CategoryMapper;
import app.demo.modal.Account;
import app.demo.modal.Category;
import app.demo.payload.PaginationResponse;
import app.demo.repository.CategoryRepository;
import app.demo.service.Iface.IAccountService;
import app.demo.service.Iface.ICategoryService;
import app.demo.util.SlugUtil;
import jakarta.transaction.Transactional;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired 
    private IAccountService iAccountService;

    @Override
    public CategoryResponse findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public PaginationResponse<List<CategoryResponse>> findAll(Pageable pageable) {
        
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        List<CategoryResponse> responses = categoryMapper.toResponseList(categoryPage.getContent());

        return PaginationResponse.<List<CategoryResponse>>builder()
            .data(responses)
            .currentPage(categoryPage.getNumber())
            .totalItems(categoryPage.getTotalElements())
            .totalPages(categoryPage.getTotalPages())
            .build();
    }

    @Override
    public CategoryResponse create(CategoryRequest entity, UserDetails userDetails) {

        Category category = Category.builder()
            .name(entity.getName())
            .slug(SlugUtil.toSlug(entity.getName()))
            .build();
        
        AccountResponse accountResponse = iAccountService.findById(userDetails.getUsername());

        Account account = new Account();
        account.setId(UUID.fromString(accountResponse.getId()));

        category.setAccount(account);

        category = categoryRepository.save(category);

        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse update(String id, CategoryRequest entity, UserDetails userDetails) {

        Category category = categoryRepository.findById(UUID.fromString(id));

        if (category == null) {
            throw new ResourceNotFoundException("not found category");
        }


        if (!category.getName().equals(entity.getName().trim())) {
            category.setName(entity.getName().trim());
            category.setSlug(SlugUtil.toSlug(entity.getName()));
        }

        Account account = new Account();
        account.setId(UUID.fromString(userDetails.getUsername()));

        category.setAccount(account);

        category = categoryRepository.save(category);

        return categoryMapper.toResponse(category);
    }

    @Override
    @Transactional
    public void delete(String id) {
        try {
            categoryRepository.deleteById(UUID.fromString(id));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Cannot delete category as it is referenced by other entities.");
        }
    }

    @Override
    public CategoryResponse findBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug);
        
        if (category == null) {
            throw new ResourceNotFoundException("not found category");
        }

        return categoryMapper.toResponse(category);
    }
    
}

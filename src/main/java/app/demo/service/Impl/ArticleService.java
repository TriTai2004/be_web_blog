package app.demo.service.Impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import app.demo.dto.req.ArticleRequest;
import app.demo.dto.res.ArticleResponse;
import app.demo.exception.ResourceNotFoundException;
import app.demo.mapper.ArticleMappter;
import app.demo.modal.Account;
import app.demo.modal.Article;
import app.demo.payload.PaginationResponse;
import app.demo.repository.ArticleRepository;
import app.demo.service.Iface.IArticleService;
import app.demo.util.SlugUtil;

@Service
public class ArticleService implements IArticleService{


    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleMappter articleMappter;

    @Autowired
    private Cloudinary cloudinary;


    @Override
    public ArticleResponse findById(String id) {

        Article article = articleRepository.findById(UUID.fromString(id)).orElseThrow(
            () -> new ResourceNotFoundException("Article not found")
        );

        return articleMappter.toResponse(article);
    }

    @Override
    public PaginationResponse<List<ArticleResponse>> findAll(Pageable pageable) {

        Page<Article> pages = articleRepository.findAll(pageable);

        List<ArticleResponse> results = articleMappter.toResponses(pages.getContent());

        return PaginationResponse.<List<ArticleResponse>>builder()
            .data(results)
            .currentPage(pages.getNumber())
            .totalItems(pages.getTotalElements())
            .totalPages(pages.getTotalPages())
            .build();
        
    }

    @Override
    public ArticleResponse create(ArticleRequest entity, UserDetails userDetails) {

        Article article = articleMappter.toEntity(entity);
        
        Account account = new Account();
        account.setId(UUID.fromString(userDetails.getUsername()));

        String slug = SlugUtil.generateUniqueSlugArticle(entity.getTitle(), articleRepository);

        article.setDeleted(false);
        article.setAuthor(account);
        article.setSlug(slug);

        article = articleRepository.save(article);

        return articleMappter.toResponse(article);
    }

    @Override
    public ArticleResponse update(String id, ArticleRequest entity, UserDetails userDetails) {
        
        ArticleResponse articleResponse = findById(id);

        Article article = articleMappter.toEntity(entity);
        article.setId(UUID.fromString(id));
        article.setSlug(articleResponse.getSlug());
        article.setCreatedAt(articleResponse.getCreatedAt());

        if (!articleResponse.getTitle().equalsIgnoreCase(entity.getTitle())) {
            article.setSlug(SlugUtil.generateUniqueSlugArticle(entity.getTitle(), articleRepository));
        }

        article = articleRepository.save(article);
        
        return articleMappter.toResponse(article);

    }

    @Override
    @Transactional
    public void delete(String id) {
        
        Article article = articleRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new ResourceNotFoundException("article not found"));

        article.setDeleted(true);

    }

    @Override
    public ArticleResponse findBySlug(String slug) {
        Article article = articleRepository.findBySlug(slug);
        if (article == null) {
            throw new ResourceNotFoundException("not found article");
        }

        return articleMappter.toResponse(article);
    }

    @Override
    public String uploadImage(MultipartFile file, UserDetails userDetails) throws IOException {
        
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        
        return uploadResult.get("url").toString();
    }
    
    
}

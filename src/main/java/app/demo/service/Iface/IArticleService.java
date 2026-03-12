package app.demo.service.Iface;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.demo.dto.req.ArticleRequest;
import app.demo.dto.res.ArticleResponse;
import app.demo.payload.PaginationResponse;

@Service
public interface IArticleService extends IService<ArticleResponse, ArticleRequest, String>{
    
    ArticleResponse findBySlug (String slug);
    Map<String, Object> uploadImage(MultipartFile file, UserDetails userDetails) throws IOException;
    String deleteImage(String publicId, UserDetails userDetails) throws IOException;
    PaginationResponse<List<ArticleResponse>> findAll(String search, Pageable pageable);
    PaginationResponse<List<ArticleResponse>> findAllPopular(Pageable pageable);

}

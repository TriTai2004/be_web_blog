package app.demo.service.Iface;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import app.demo.dto.req.ArticleRequest;
import app.demo.dto.res.ArticleResponse;

public interface IArticleService extends IService<ArticleResponse, ArticleRequest, String>{
    
    ArticleResponse findBySlug (String slug);
    Map<String, Object> uploadImage(MultipartFile file, UserDetails userDetails) throws IOException;
    String deleteImage(String publicId, UserDetails userDetails) throws IOException;

}

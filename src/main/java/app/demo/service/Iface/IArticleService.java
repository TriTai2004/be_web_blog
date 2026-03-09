package app.demo.service.Iface;

import java.io.IOException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import app.demo.dto.req.ArticleRequest;
import app.demo.dto.res.ArticleResponse;

public interface IArticleService extends IService<ArticleResponse, ArticleRequest, String>{
    
    ArticleResponse findBySlug (String slug);
    String uploadImage(MultipartFile file, UserDetails userDetails) throws IOException;

}

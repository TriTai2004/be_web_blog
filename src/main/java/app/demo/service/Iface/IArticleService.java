package app.demo.service.Iface;

import app.demo.dto.req.ArticleRequest;
import app.demo.dto.res.ArticleResponse;

public interface IArticleService extends IService<ArticleResponse, ArticleRequest, String>{
    
    ArticleResponse findBySlug (String slug);

}

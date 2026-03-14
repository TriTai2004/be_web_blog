package app.demo.service.Impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import app.demo.dto.req.LikeRequest;
import app.demo.dto.res.LikeResponse;
import app.demo.exception.ResourceNotFoundException;
import app.demo.mapper.LikeMapper;
import app.demo.modal.Like;
import app.demo.payload.PaginationResponse;
import app.demo.repository.LikeRepository;
import app.demo.service.Iface.ILikeService;

@Service
public class LikeService implements ILikeService{

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private LikeMapper likeMapper;

    @Override
    public LikeResponse findById(String id) {
        Like like = likeRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ResourceNotFoundException("Like not found"));
        return likeMapper.toResponse(like);
    }

    @Override
    public PaginationResponse<List<LikeResponse>> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public LikeResponse create(LikeRequest entity, UserDetails userDetails) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public LikeResponse update(String id, LikeRequest entity, UserDetails userDetails) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String id) {
        findById(id);
        likeRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public LikeResponse likeOrDislike(LikeRequest likeRequest, UserDetails userDetails) {

        if (!userDetails.getUsername().equals(likeRequest.getAuthorId())) {
            throw new SecurityException("You are not the owner of this like");
        }
        
        Like isLike = likeRepository.findByArticleIdAndAuthorId(UUID.fromString(likeRequest.getArticleId()), UUID.fromString(likeRequest.getAuthorId()));

        if (isLike != null) {
            delete(isLike.getId().toString());
            return null;
            
        }else{
            Like like = likeMapper.toEntity(likeRequest);
            Like saveLike = likeRepository.save(like);
            return likeMapper.toResponse(saveLike);
        }

    }

    @Override
    public LikeResponse findByArticleIdAndAuthorId(String articleId, String authorId) {
        Like like = likeRepository.findByArticleIdAndAuthorId(UUID.fromString(articleId), UUID.fromString(authorId));
        if (like == null) {
            return null;
        }
        return likeMapper.toResponse(like);
    }
    
}

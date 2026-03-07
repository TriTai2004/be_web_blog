package app.demo.service.Iface;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import app.demo.payload.PaginationResponse;

public interface IService <T, R, ID>{

    T findById(ID id);
    PaginationResponse<List<T>> findAll(Pageable pageable);
    T create(R entity, UserDetails userDetails);
    T update(ID id, R entity, UserDetails userDetails);
    void delete(ID id);

    
}

package app.demo.service.Iface;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import app.demo.dto.req.AccountRequest;
import app.demo.dto.res.AccountResponse;
import app.demo.payload.PaginationResponse;

public interface IAccountService extends IService<AccountResponse, AccountRequest, String>{
    
    public PaginationResponse<List<AccountResponse>> findAll(Pageable pageable, UserDetails userDetails);

    
}

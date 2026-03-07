package app.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import app.demo.dto.req.AccountRequest;
import app.demo.dto.res.AccountResponse;
import app.demo.modal.Account;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMappter {
    
    AccountResponse toResponse( Account account );


    List<AccountResponse> toResponseList( List<Account> accounts );

    Account toEntity( AccountRequest accountRequest ); 

}

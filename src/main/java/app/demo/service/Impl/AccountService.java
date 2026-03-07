package app.demo.service.Impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import app.demo.dto.req.AccountRequest;
import app.demo.dto.res.AccountResponse;
import app.demo.exception.ResourceNotFoundException;
import app.demo.mapper.AccountMappter;
import app.demo.modal.Account;
import app.demo.payload.PaginationResponse;
import app.demo.repository.AccountRepository;
import app.demo.service.Iface.IAccountService;

@Service
public class AccountService implements IAccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMappter accountMappter;

    @Override
    public AccountResponse findById(String id) {

        Account account = accountRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new ResourceNotFoundException("account not found"));

        return accountMappter.toResponse(account);
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public PaginationResponse<List<AccountResponse>> findAll(Pageable pageable, UserDetails userDetails) {

        
        Page<Account> accountPage = accountRepository.findAll(pageable);

        List<AccountResponse> responses = accountPage.getContent().stream()
            .map(accountMappter::toResponse)
            .toList();

        return PaginationResponse.<List<AccountResponse>>builder()
            .data(responses)
            .currentPage(accountPage.getNumber())
            .totalItems(accountPage.getTotalElements())
            .totalPages(accountPage.getTotalPages())
            .build();
    }


    @Override
    public PaginationResponse<List<AccountResponse>> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public AccountResponse create(AccountRequest entity, UserDetails userDetails) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public AccountResponse update(String id, AccountRequest entity, UserDetails userDetails) {

        Account existingAccount = accountRepository.findById(UUID.fromString(userDetails.getUsername()))
            .orElseThrow(() -> new ResourceNotFoundException("account not found"));

        existingAccount.setAvatar(entity.getAvatar());
        existingAccount.setFullname(entity.getFullname());

        existingAccount = accountRepository.save(existingAccount);

        return accountMappter.toResponse(existingAccount);
    }
    
}

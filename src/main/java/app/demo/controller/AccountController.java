package app.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.demo.dto.req.AccountRequest;
import app.demo.service.Iface.IAccountService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private IAccountService accountService;
    
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(
        Pageable pageable,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(accountService.findAll(pageable, userDetails));
    }

    @GetMapping("/info")
    public ResponseEntity<?> getInfo(
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(accountService.findById(userDetails.getUsername()));
    }

    @PutMapping
    public ResponseEntity<?> update(
        @AuthenticationPrincipal UserDetails userDetails,
        @Valid @RequestBody AccountRequest req

    ) {
        return ResponseEntity.ok(accountService.update(userDetails.getUsername(), req, userDetails));
    }   
}

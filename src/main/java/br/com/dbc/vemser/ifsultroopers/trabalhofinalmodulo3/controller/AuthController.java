package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.controller;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.AuthDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    @PostMapping()
    public String auth(@RequestBody @Valid AuthDTO authDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        authDTO.getLogin(),
                        authDTO.getSenha()
                );

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        String token = tokenService.getToken(authenticate);
        return token;
    }
}

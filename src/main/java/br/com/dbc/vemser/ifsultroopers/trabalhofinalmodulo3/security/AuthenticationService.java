package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.security;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.UsersEntity;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private final UsersService usersService;

    @Override
    public UsersEntity loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<UsersEntity> optionalUsuario = Optional.ofNullable(usersService.findByLogin(login));
        if (optionalUsuario.isPresent()) {
            return optionalUsuario.get();
        }
        throw new UsernameNotFoundException("User not found!");
    }
}

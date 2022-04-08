package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.UsersEntity;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.exception.BusinessRuleException;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service.UsersService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsersTest {

    @Autowired
    UsersService usersService;

    @Test
    public void documentValidatorIsFailed () {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setDocument("12345678900");

        Exception exception = assertThrows(BusinessRuleException.class, () -> usersService.validateAndSetDocument(usersEntity));
        assertTrue(exception.getMessage().contains("CPF ou CNPJ invalido!"));
    }

    @Test
    public void documentCpfValidatorIsSuccessful () {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setDocument("03001529067");

        Exception exception = assertThrows(BusinessRuleException.class, () -> usersService.validateAndSetDocument(usersEntity));
        assertNull(exception);
    }
}

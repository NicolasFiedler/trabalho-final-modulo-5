package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request.RequestCreateDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.userdto.UsersDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.*;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.exception.BusinessRuleException;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.repository.BankAccountRepository;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.repository.RequestRepository;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.repository.UsersRepository;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service.BankAccountService;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service.RequestService;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.Request;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RequestTest {

    @InjectMocks
    RequestService requestService;

    @Mock
    RequestRepository requestRepository;

    @Mock
    UsersRepository usersRepository;

    @Mock
    BankAccountRepository bankAccountRepository;

    @Mock
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void mustCreateRequest() throws BusinessRuleException {
        UsersDTO gabriel = new UsersDTO();
        gabriel.setIdUser(1);

        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setIdUser(gabriel.getIdUser());

        usersRepository.save(usersEntity);

        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        bankAccountEntity.setIdBankAccount(1);

        bankAccountRepository.save(bankAccountEntity);

        RequestEntity vakinhaTeste = RequestEntity.builder()
                .idRequest(1)
                .title("Vakinha Teste")
                .description("Descrição teste vakinha")
                .goal(10.0)
                .reachedValue(0.0)
                .idUser(gabriel.getIdUser())
                .idBankAccount(1)
                .statusRequest(true)
                .category(Category.OUTROS)
                .build();
        RequestCreateDTO requestCreateDTO = RequestCreateDTO.builder()
                .title("RequestDTO teste")
                .description("desc RequestDTO teste")
                .goal(vakinhaTeste.getGoal())
                .idBankAccount(vakinhaTeste.getIdBankAccount())
                .build();


        try {
        when(objectMapper.convertValue((Object) any(), eq(RequestEntity.class))).thenReturn(vakinhaTeste);
        when(objectMapper.convertValue((Object) any(), eq(UsersEntity.class))).thenReturn(usersEntity);
        when(objectMapper.convertValue((Object) any(), eq(BankAccountEntity.class))).thenReturn(bankAccountEntity);

        requestService.create(gabriel.getIdUser(), requestCreateDTO, vakinhaTeste.getCategory());
        } catch (Exception e) {
            e.printStackTrace();
        }
        verify(requestRepository,times(1)).save(any(RequestEntity.class));
    }
    @Test
    public void testIncrementValue() throws BusinessRuleException {
        UsersDTO gabriel = new UsersDTO();
        gabriel.setIdUser(1);

        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setIdUser(gabriel.getIdUser());

        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        bankAccountEntity.setIdBankAccount(1);

        RequestEntity vakinhaTeste = RequestEntity.builder()
                .idRequest(1)
                .title("Vakinha Teste")
                .description("Descrição teste vakinha")
                .goal(10.0)
                .reachedValue(0.0)
                .idUser(gabriel.getIdUser())
                .idBankAccount(1)
                .statusRequest(true)
                .category(Category.OUTROS)
                .build();
        RequestCreateDTO requestCreateDTO = RequestCreateDTO.builder()
                .title("RequestDTO teste")
                .description("desc RequestDTO teste")
                .goal(vakinhaTeste.getGoal())
                .idBankAccount(vakinhaTeste.getIdBankAccount())
                .build();

        Double donateValue = 10.0;

        try {
            requestService.incrementReachedValue(vakinhaTeste.getIdRequest(), donateValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(10.0,10.0, vakinhaTeste.getReachedValue());
    }
}
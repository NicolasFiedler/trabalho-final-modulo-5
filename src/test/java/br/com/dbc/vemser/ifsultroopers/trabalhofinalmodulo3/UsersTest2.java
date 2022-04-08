package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.userdto.UsersCreateDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.UsersEntity;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.exception.BusinessRuleException;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.repository.UsersRepository;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@RequiredArgsConstructor
public class UsersTest2 {

    private final UsersService usersService;
    private final UsersService mockUsersService = Mockito.mock(UsersService.class);

    private final UsersRepository mockUsersRepository = Mockito.mock(UsersRepository.class);

//    @Before
//    public void init () {
//        MockitoAnnotations.openMocks(this);
//    }

//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Before
//    public void BeforeEach() {
//        ReflectionTestUtils.setField(vagaService,"objectMapper",objectMapper);
//    }


    @Test
    public void documentValidatorIsFailed () {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setDocument("12345678900");

        Exception exception = assertThrows(BusinessRuleException.class, () -> usersService.validateAndSetDocument(usersEntity));
        assertTrue(exception.getMessage().contains("CPF ou CNPJ invalido!"));
    }

    @Test
    public void documentCnpjValidatorIsSuccessful () {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setDocument("50662597000108");
        try {
            UsersEntity userValid = usersService.validateAndSetDocument(usersEntity);
            assertEquals(usersEntity.getDocument(), userValid.getDocument());
        } catch (BusinessRuleException businessRuleException) {
            fail();
        }
    }

    @Test
    public void documentCpfValidatorIsSuccessful () {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setDocument("03001529067");
        try {
            UsersEntity userValid = usersService.validateAndSetDocument(usersEntity);
            assertEquals(usersEntity.getDocument(), userValid.getDocument());
        } catch (BusinessRuleException businessRuleException) {
            fail();
        }
    }

    @Test
    public void createUsers () {
        UsersCreateDTO usersCreateDTO = new UsersCreateDTO();
        UsersEntity usersEntity = new UsersEntity();

        Mockito.when(mockUsersRepository.save(any()))
                .thenReturn(usersEntity);

        usersCreateDTO.setDocument("03001529067");
        usersCreateDTO.setPassword("1234");

        try {
            mockUsersService.create(usersCreateDTO);
        } catch (BusinessRuleException e) {
            e.printStackTrace();
        }

        System.out.println(usersEntity.getPassword());
    }
}

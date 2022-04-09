package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.userdto.UsersCreateDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.RoleEntity;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.UsersEntity;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.exception.BusinessRuleException;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.repository.RoleRepository;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.repository.UsersRepository;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class UsersTest {

//    private final UsersService mockUsersService = Mockito.mock(UsersService.class);
//
//    private final UsersRepository mockUsersRepository = Mockito.mock(UsersRepository.class);

    @InjectMocks
    private UsersService mockUsersService;

    @Mock
    private UsersRepository mockUsersRepository;

    @Mock
    private RoleRepository mockRoleRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void BeforeEach() {
        ReflectionTestUtils.setField(mockUsersService,"objectMapper",objectMapper);
    }

    @Test
    public void shouldSuccesfulCreate () {
        UsersCreateDTO usersCreateDTO = UsersCreateDTO.builder()
                .email("a@a.com")
                .name("nome")
                .password("123")
                .document("03001529067")
                .build();
        UsersEntity usersEntity = UsersEntity.builder()
                .email("a@a.com")
                .name("nome")
                .password("123")
                .document("03001529067")
                .type(false)
                .build();
        RoleEntity roleEntity = RoleEntity.builder()
                .idRole(2)
                .name("ROLE_USER")
                .build();

        Set<UsersEntity> users = new HashSet<>();
        users.add(usersEntity);
        roleEntity.setUsers(users);

        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleEntity);
        usersEntity.setRoles(roles);

        when(mockUsersRepository.save(any()))
                .thenReturn(usersEntity);
        when(mockRoleRepository.findById(2))
                .thenReturn(Optional.of(roleEntity));

        try {
            mockUsersService.create(usersCreateDTO);
        } catch (BusinessRuleException e) {
            e.printStackTrace();
            fail();
        }

        verify(mockUsersRepository, times(1)).save(any());
        verify(mockRoleRepository, times(1)).findById(2);
    }
}



//    @Test
//    public void documentValidatorIsFailed () {
//        UsersEntity usersEntity = new UsersEntity();
//        usersEntity.setDocument("12345678900");
//
//        Exception exception = assertThrows(BusinessRuleException.class, () -> usersService.validateAndSetDocument(usersEntity));
//        assertTrue(exception.getMessage().contains("CPF ou CNPJ invalido!"));
//    }
//
//    @Test
//    public void documentCnpjValidatorIsSuccessful () {
//        UsersEntity usersEntity = new UsersEntity();
//        usersEntity.setDocument("50662597000108");
//        try {
//            UsersEntity userValid = usersService.validateAndSetDocument(usersEntity);
//            assertEquals(usersEntity.getDocument(), userValid.getDocument());
//        } catch (BusinessRuleException businessRuleException) {
//            fail();
//        }
//    }
//
//    @Test
//    public void documentCpfValidatorIsSuccessful () {
//        UsersEntity usersEntity = new UsersEntity();
//        usersEntity.setDocument("03001529067");
//        try {
//            UsersEntity userValid = usersService.validateAndSetDocument(usersEntity);
//            assertEquals(usersEntity.getDocument(), userValid.getDocument());
//        } catch (BusinessRuleException businessRuleException) {
//            fail();
//        }
//    }

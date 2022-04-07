package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.controller;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.userdto.UsersCreateDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.userdto.UsersDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.userdto.UsersWithRequestsAndDonatesDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.userdto.UsersWithRequestsDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.exception.BusinessRuleException;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service.UsersService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RestController
@RequestMapping("/users")
@Validated
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    //ADMIN
    @ApiOperation(value = "Retorna a lista de todos os usuarios")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna todos os usuarios")
    })
    @GetMapping
    public List<UsersDTO> list () {
        return usersService.list();
    }

    //ADMIN
    @ApiOperation(value = "Retorna usuarios com suas vakinhas (todos ou pelo ID)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna usuarios"),
            @ApiResponse(code = 400, message = "Usuario nao encontrado")
    })
    @GetMapping("/users-with-requests")
    public List<UsersWithRequestsDTO> listUsersWithRequests(@RequestParam(value = "id", required = false) Integer idUser) throws BusinessRuleException {
        return usersService.listWithRequests(idUser);
    }

    //ADMIN
    @ApiOperation(value = "Retorna usuarios com suas vakinhas e com donates (todos ou pelo ID)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna usuarios"),
            @ApiResponse(code = 400, message = "Usuario nao encontrado")
    })
    @GetMapping("/users-with-requests-and-donates")
    public List<UsersWithRequestsAndDonatesDTO> listWithRequestsAndDonates(@RequestParam(value = "id", required = false) Integer idUser) throws BusinessRuleException {
        return usersService.listWithRequestsAndDonates(idUser);
    }

    //ADMIN
    @GetMapping("/cpf-users")
    public List<UsersDTO> listUsersWithCPF () {
        return usersService.listByUserType(false);
    }

    //ADMIN
    @GetMapping("/cnpj-users")
    public List<UsersDTO> listUsersWithCNPJ () {
        return usersService.listByUserType(true);
    }

    //ADMIN
    @ApiOperation(value = "Retorna um usuario pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna um usuario"),
            @ApiResponse(code = 400, message = "Usuario nao encontrado")
    })
    @GetMapping("/{idUser}")
    public UsersDTO getById (@PathVariable("idUser") Integer id) throws BusinessRuleException {
        return usersService.getById(id);
    }

    //ABERTO
    @ApiOperation(value = "Insere e Retorna o Usuario inserido")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna Usuario inserido"),
            @ApiResponse(code = 400, message = "CPF ou CNPJ Invalido")
    })
    @PostMapping
    public UsersDTO create (@Valid @RequestBody UsersCreateDTO usersCreateDTO) throws BusinessRuleException {
        return usersService.create(usersCreateDTO);
    }

    //PROPRIETARIO
    @ApiOperation(value = "Atualiza e Retorna o usuario atualizado pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna Usuario atualizado"),
            @ApiResponse(code = 400, message = "Usuario nao encontrado"),
            @ApiResponse(code = 400, message = "CPF ou CNPJ Invalido")
    })
    @PutMapping
    public UsersDTO update (@Valid @RequestBody UsersCreateDTO usersCreateDTO) throws BusinessRuleException {
        String id = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usersService.update(Integer.parseInt(id), usersCreateDTO);
    }

    //ADMIN
    @ApiOperation(value = "Remove e Retorna o usuario removido pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma vakinha"),
            @ApiResponse(code = 400, message = "Usuario nao encontrado")
    })
    @DeleteMapping("/{idUser}")
    public UsersDTO delete (@PathVariable("idUser") Integer id) throws BusinessRuleException {
        return usersService.delete(id);
    }
}

package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.controller;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.bankaccount.BankAccountCreateDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.bankaccount.BankAccountDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service.BankAccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/BankAccount")
@Validated
@RequiredArgsConstructor
public class BankAcountController {

    private final BankAccountService bankAccountService;

    //USER
    @ApiOperation(value = "Cria e retorna a Bank Account criada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a Bank Account criada"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public ResponseEntity<BankAccountDTO> create(@Valid @RequestBody BankAccountCreateDTO bankAccount) throws Exception {
        BankAccountDTO bankAccountDTO = bankAccountService.create(bankAccount);
        return ResponseEntity.ok(bankAccountDTO);
    }

    //ADMIN
    @ApiOperation(value = "Retorna a lista de Bank Accounts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de Bank Account"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    public ResponseEntity<List<BankAccountDTO>> list() {
        return ResponseEntity.ok(bankAccountService.list());
    }

    //ADMIN
    @ApiOperation(value = "Retorna a lista de Bank Accounts por Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma lista de Bank Accounts por id"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/{idBankAccount}")
    public ResponseEntity<BankAccountDTO> getBankAccountById(@PathVariable("idBankAccount") Integer id) throws Exception {
        return ResponseEntity.ok(bankAccountService.getBankAccountById(id));
    }

    //ADMIN
    @ApiOperation(value = "Retorna a Bank Account editada pelo Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a donate Editada pelo Id"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PutMapping("/{idBankAccount}")
    public ResponseEntity<BankAccountDTO> update(@PathVariable("idBankAccount") Integer id,
                                            @Valid @RequestBody BankAccountCreateDTO bankAccountDTO) throws Exception {
        return  ResponseEntity.ok(bankAccountService.update(id, bankAccountDTO));
    }

    //ADMIN
    @ApiOperation(value = "Retorna a Bank Account deletada pelo Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a Bank Account deletada pelo Id"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/{idBankAccount}")
    public ResponseEntity<BankAccountDTO> delete(@PathVariable("idBankAccount") Integer id) throws Exception {
        BankAccountDTO bankAccountDTO = bankAccountService.delete(id);
//        emailService.pessoaSendEmail(pessoaDTO, "Você perdeu o acesso ao nosso sistema.", " Delet de conta");
        return ResponseEntity.ok(bankAccountDTO);
    }
}

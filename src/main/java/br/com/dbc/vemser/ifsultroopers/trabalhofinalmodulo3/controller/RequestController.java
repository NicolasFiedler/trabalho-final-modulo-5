package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.controller;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.donate.DonateDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request.RequestCreateDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request.RequestDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request.RequestUpdateDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request.RequestWithDonatesDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.Category;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.exception.BusinessRuleException;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service.RequestService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Controller
@RestController
@RequestMapping("/request")
@Validated
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    //ABERTO
    @ApiOperation(value = "Retorna a lista de vakinhas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna todas as vakinhas"),
    })
    @GetMapping
    public List<RequestDTO> list() {
        return requestService.list();
    }

    //ABERTO
    @ApiOperation(value = "Retorna uma vakinha pelo id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma vakinha"),
            @ApiResponse(code = 400, message = "Vakinha não encontrada")
    })
    @GetMapping("/{idRequest}")
    public ResponseEntity<RequestDTO> get(@PathVariable("idRequest") Integer id) throws Exception {
        RequestDTO request = requestService.getById(id);
        return ResponseEntity.ok(request);
    }

    //PROPRIETARIO && ADMIN
    @ApiOperation(value = "Retorna uma Request com seus Donates")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma Request com seus Donates"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/findDonateByIdRequest/")
    public ResponseEntity<RequestWithDonatesDTO> getDonateByIdRequest(@RequestParam Integer idRequest) throws BusinessRuleException {
        return ResponseEntity.ok(requestService.getRequestsWithDonatesByIdRequest(idRequest));
    }

    //POR PROPRIETARIO
    @ApiOperation(value = "Cria uma vakinha pelo id de um usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a vakinha criada."),
            @ApiResponse(code = 400, message = "Usuário inválido.")
    })
    @PostMapping("/create")
    @Validated
    public ResponseEntity<RequestDTO> create(@RequestBody @Valid RequestCreateDTO request, @RequestParam Category category) throws Exception {
        String id = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RequestDTO created = requestService.create(Integer.parseInt(id), request, category);

        return ResponseEntity.ok(created);
    }

    //POR PROPRIETARIO
    @ApiOperation(value = "Atualiza a vakinha pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a vakinha atualizada."),
            @ApiResponse(code = 400, message = "Vakinha não encontrada.")
    })
    @PutMapping("/{idRequest}")
    @Validated
    public ResponseEntity<RequestDTO> update(@RequestBody @Valid RequestUpdateDTO data, @RequestParam Category category) throws Exception {
        String id = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RequestDTO updated = requestService.update(Integer.parseInt(id), data, category);
        return ResponseEntity.ok(updated);
    }

    //POR PROPRIETARIO && ADMIN
    @ApiOperation(value = "Deleta a vakinha pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a vakinha deletada"),
            @ApiResponse(code = 400, message = "Vakinha não encontrada")
    })
    @DeleteMapping("/{idRequest}")
    public ResponseEntity<RequestDTO> delete(@PathVariable("idRequest") Integer id) throws Exception {
        RequestDTO deleted = requestService.delete(id);
        return ResponseEntity.ok(deleted);
    }

    //ABERTO
    @ApiOperation(value = "Retorna a lista de Vakinhas Abertas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna todas as Vakinhas Abertas"),
    })
    @GetMapping("/findByStatusRequestIsTrue")
    public ResponseEntity<List<RequestDTO>> findByStatusRequestIsTrue() {
        return ResponseEntity.ok(requestService.findByStatusRequestIsTrue());
    }

    //ABERTO
    @ApiOperation(value = "Retorna a lista de Vakinhas Fechadas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna todas as Vakinhas Fechadas"),
    })
    @GetMapping("/findByStatusRequestIsFalse")
    public ResponseEntity<List<RequestDTO>> findByStatusRequestIsFalse() {
        return ResponseEntity.ok(requestService.findByStatusRequestIsFalse());
    }
}
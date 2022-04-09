package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.bankaccount.BankAccountDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.donate.DonateDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request.RequestCreateDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request.RequestDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request.RequestUpdateDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request.RequestWithDonatesDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.userdto.UsersDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.*;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.exception.BusinessRuleException;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.repository.RequestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class RequestService {
    
    private RequestRepository requestRepository;

    private UsersService usersService;

    private ObjectMapper objectMapper;

    private BankAccountService bankAccountService;


    public List<RequestDTO> list() {
        return requestRepository.findAll()
                .stream()
                .map(requestEntity -> objectMapper.convertValue(requestEntity, RequestDTO.class))
                .collect(Collectors.toList());
    }

    public RequestDTO create(Integer id, RequestCreateDTO request, Category category) throws BusinessRuleException {
        RequestEntity requestEntity = objectMapper.convertValue(request, RequestEntity.class);

        UsersDTO usersDTO = usersService.getById(id);
        UsersEntity usersEntity = objectMapper.convertValue(usersDTO, UsersEntity.class);
        requestEntity.setUsersEntity(usersEntity);

        requestEntity.setIdUser(id);
        requestEntity.setReachedValue(0.00);
        requestEntity.setStatusRequest(true);
        requestEntity.setCategory(category);

        BankAccountDTO bankAccountDTO = bankAccountService.getBankAccountById(requestEntity.getIdBankAccount());
        BankAccountEntity bankAccountEntity = objectMapper.convertValue(bankAccountDTO, BankAccountEntity.class);
        requestEntity.setBankAccountEntity(bankAccountEntity);

        RequestEntity created = requestRepository.save(requestEntity);
        return objectMapper.convertValue(created, RequestDTO.class);
    }

    public RequestDTO getById(Integer id) throws BusinessRuleException {
        RequestEntity requestEntity = requestRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Vakinha não encontrada!"));
        return objectMapper.convertValue(requestEntity, RequestDTO.class);
    }

    public RequestWithDonatesDTO getRequestsWithDonatesByIdRequest(Integer idRequest) throws BusinessRuleException {
        String stringIdUser = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer idUser = Integer.parseInt(stringIdUser);

        UsersEntity usersEntity = usersService.getEntityById(idUser);

        for (RoleEntity roleEntity : usersEntity.getRoles()) {
            if (roleEntity.getName().equals("ROLE_ADMIN")){
                RequestEntity requestEntity = requestRepository.findById(idRequest)
                        .orElseThrow(() -> new BusinessRuleException("Request não econtrada!"));
                RequestWithDonatesDTO requestWithDonatesDTO = objectMapper.convertValue(requestEntity, RequestWithDonatesDTO.class);
                requestWithDonatesDTO.setDonateDTOList(requestEntity.getDonates().stream()
                        .map(donateEntity -> objectMapper.convertValue(donateEntity, DonateDTO.class))
                        .collect(Collectors.toList())
                );
                return requestWithDonatesDTO;
            }
        }

        RequestEntity requestEntity = requestRepository.findByIdUserAndIdRequest(idUser, idRequest)
                .orElseThrow(() -> new BusinessRuleException("Request não econtrada!"));
        RequestWithDonatesDTO requestWithDonatesDTO = objectMapper.convertValue(requestEntity, RequestWithDonatesDTO.class);
        requestWithDonatesDTO.setDonateDTOList(requestEntity.getDonates().stream()
                .map(donateEntity -> objectMapper.convertValue(donateEntity, DonateDTO.class))
                .collect(Collectors.toList())
        );
        return requestWithDonatesDTO;
    }

    public RequestDTO update(Integer idRequest, RequestUpdateDTO request, Category category) throws BusinessRuleException {
        String stringIdUser = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer idUser = Integer.parseInt(stringIdUser);

        UsersEntity usersEntity = usersService.getEntityById(idUser);

        for (RoleEntity roleEntity : usersEntity.getRoles()) {
            if (roleEntity.getName().equals("ROLE_ADMIN")){
                RequestEntity requestEntity = requestRepository.findById(idRequest)
                        .orElseThrow(() -> new BusinessRuleException("Vakinha não encontrada!"));
                requestEntity.setTitle(request.getTitle());
                requestEntity.setDescription(request.getDescription());
                requestEntity.setGoal(request.getGoal());
                requestEntity.setCategory(category);

                return objectMapper.convertValue(requestRepository.save(requestEntity), RequestDTO.class);
            }
        }

        RequestEntity requestEntity = requestRepository.findByIdUserAndIdRequest(idUser, idRequest)
                .orElseThrow(() -> new BusinessRuleException("Vakinha não encontrada!"));
        requestEntity.setTitle(request.getTitle());
        requestEntity.setDescription(request.getDescription());
        requestEntity.setGoal(request.getGoal());
        requestEntity.setCategory(category);

        return objectMapper.convertValue(requestRepository.save(requestEntity), RequestDTO.class);
    }
    
    public RequestDTO delete(Integer idRequest) throws BusinessRuleException {
        String stringIdUser = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer idUser = Integer.parseInt(stringIdUser);

        UsersEntity usersEntity = usersService.getEntityById(idUser);

        for (RoleEntity roleEntity : usersEntity.getRoles()) {
            if (roleEntity.getName().equals("ROLE_ADMIN")){
                RequestEntity requestEntity = requestRepository.findById(idRequest)
                        .orElseThrow(() -> new BusinessRuleException("Vakinha não encontrada!"));
                requestRepository.deleteById(idRequest);
                return objectMapper.convertValue(requestEntity, RequestDTO.class);
            }
        }

        Optional<RequestEntity>optionalRequestEntity = requestRepository.findByIdUserAndIdRequest(idUser, idRequest);

        if (optionalRequestEntity.isEmpty()) {
            throw new BusinessRuleException("Vakinha não encontrada!");
        }

        requestRepository.deleteById(idRequest);
        return objectMapper.convertValue(optionalRequestEntity.get(), RequestDTO.class);
    }

    public void incrementReachedValue(Integer idRequest, Double donateValue) throws BusinessRuleException {
        RequestEntity requestEntity = requestRepository.findById(idRequest)
                .orElseThrow(() -> new BusinessRuleException("Vakinha não encontrada!"));
        requestEntity.setReachedValue(reachedValue(requestEntity.getReachedValue(), donateValue));
        requestRepository.save(requestEntity);
    }

    public Double reachedValue(Double vakinhaValue, Double donateValue){
        return vakinhaValue+donateValue;
    }

    public List<RequestDTO> findByStatusRequestIsTrue() {
        return requestRepository.findByStatusRequestIsTrue()
                .stream()
                .map(requestEntity -> objectMapper.convertValue(requestEntity, RequestDTO.class))
                .collect(Collectors.toList());
    }

    public List<RequestDTO> findByStatusRequestIsFalse() {
        return requestRepository.findByStatusRequestIsFalse()
                .stream()
                .map(requestEntity -> objectMapper.convertValue(requestEntity, RequestDTO.class))
                .collect(Collectors.toList());
    }

    public void checkClosed (Integer idRequest) throws BusinessRuleException {
        RequestEntity requestEntity = requestRepository.findById(idRequest)
                .orElseThrow(()-> new BusinessRuleException("Vakinha não encontrada!"));

            requestEntity.setStatusRequest(checkClosedValue(requestEntity.getReachedValue(),requestEntity.getGoal()));

        requestRepository.save(requestEntity);
    }

    public Boolean checkClosedValue(Double valRequest, Double valGoal){
        if (valRequest >= valGoal) {
            return false;
        }
        else {return true;}
    }

}
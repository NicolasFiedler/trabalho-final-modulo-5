package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.donate.DonateDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request.RequestDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request.RequestWithDonatesDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.userdto.UsersCreateDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.userdto.UsersDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.userdto.UsersWithRequestsAndDonatesDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.userdto.UsersWithRequestsDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.RequestEntity;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.RoleEntity;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.UsersEntity;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.documents.CNPJ;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.documents.CPF;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.exception.BusinessRuleException;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.repository.RoleRepository;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final ObjectMapper objectMapper;
    private final RoleRepository roleRepository;

    public List<UsersDTO> list () {
        return usersRepository.findAll().stream()
                .map(this::formatUserDTODocument)
                .toList();
    }

    public List<UsersWithRequestsDTO> listWithRequests (Integer idUser) throws BusinessRuleException {
        List<UsersWithRequestsDTO> usersWithRequestsDTOList = new ArrayList<>();
        if (idUser == null){
            usersWithRequestsDTOList.addAll(usersRepository.findAll().stream()
                    .map(usersEntity -> {
                        UsersWithRequestsDTO usersWithRequestsDTO = objectMapper.convertValue(usersEntity, UsersWithRequestsDTO.class);
                        usersWithRequestsDTO.setRequests(usersEntity.getRequests().stream()
                                .map(requestEntity -> objectMapper.convertValue(requestEntity, RequestDTO.class))
                                .collect(Collectors.toList())
                        );

                        return usersWithRequestsDTO;
                    }).collect(Collectors.toList())
            );
        } else {
            UsersEntity usersEntity = usersRepository.findById(idUser)
                    .orElseThrow(() -> new BusinessRuleException("Usuario não econtrado!"));
            UsersWithRequestsDTO usersWithRequestsDTO = objectMapper.convertValue(usersEntity, UsersWithRequestsDTO.class);
            usersWithRequestsDTO.setRequests(usersEntity.getRequests().stream()
                    .map(requestEntity -> objectMapper.convertValue(requestEntity, RequestDTO.class))
                    .collect(Collectors.toList())
            );
            usersWithRequestsDTOList.add(usersWithRequestsDTO);
        }
        return usersWithRequestsDTOList;
    }

    public List<UsersWithRequestsAndDonatesDTO> listWithRequestsAndDonates (Integer idUser) throws BusinessRuleException {
        List<UsersWithRequestsAndDonatesDTO> usersWithRequestsDTOList = new ArrayList<>();
        if (idUser == null){
            usersWithRequestsDTOList.addAll(usersRepository.findAll().stream()
                    .map(usersEntity -> {
                        UsersWithRequestsAndDonatesDTO usersWithRequestsDTO = objectMapper.convertValue(usersEntity, UsersWithRequestsAndDonatesDTO.class);
                        usersWithRequestsDTO.setRequestWithDonatesDTOList(usersEntity.getRequests().stream()
                                .map(requestEntity -> {
                                    RequestWithDonatesDTO requestDTO = objectMapper.convertValue(requestEntity, RequestWithDonatesDTO.class);
                                    requestDTO.setDonateDTOList(requestEntity.getDonates().stream()
                                            .map(donateEntity -> objectMapper.convertValue(donateEntity, DonateDTO.class))
                                            .collect(Collectors.toList())
                                    );
                                    return requestDTO;
                                })
                                .collect(Collectors.toList())
                        );

                        return usersWithRequestsDTO;
                    }).collect(Collectors.toList())
            );
        } else {
            UsersEntity usersEntity = usersRepository.findById(idUser)
                    .orElseThrow(() -> new BusinessRuleException("Usuario não econtrado!"));
            UsersWithRequestsAndDonatesDTO usersWithRequestsDTO = objectMapper.convertValue(usersEntity, UsersWithRequestsAndDonatesDTO.class);
            usersWithRequestsDTO.setRequestWithDonatesDTOList(usersEntity.getRequests().stream()
                    .map(requestEntity -> {
                        RequestWithDonatesDTO requestDTO = objectMapper.convertValue(requestEntity, RequestWithDonatesDTO.class);
                        requestDTO.setDonateDTOList(requestEntity.getDonates().stream()
                                .map(donateEntity -> objectMapper.convertValue(donateEntity, DonateDTO.class))
                                .collect(Collectors.toList())
                        );
                        return requestDTO;
                    })
                    .collect(Collectors.toList())
            );
            usersWithRequestsDTOList.add(usersWithRequestsDTO);
        }
        return usersWithRequestsDTOList;
    }

    public List<UsersDTO> listByUserType (Boolean type) {
        return usersRepository.findByType(type).stream()
                .map(this::formatUserDTODocument)
                .collect(Collectors.toList());
    }

    public UsersDTO getById (Integer id) throws BusinessRuleException {
        UsersEntity usersEntity = usersRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Usuario nao encontrado!"));

        return formatUserDTODocument(usersEntity);
    }

    public UsersEntity getEntityById (Integer id) throws BusinessRuleException {
        return usersRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Usuario nao encontrado!"));
    }

    public UsersDTO create (UsersCreateDTO usersCreateDTO) throws BusinessRuleException {
        UsersEntity u = objectMapper.convertValue(usersCreateDTO, UsersEntity.class);
        u.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));

        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity roleEntity = roleRepository.findById(2)
                .orElseThrow(() -> new BusinessRuleException("Role not found!"));
        roles.add(roleEntity);
        u.setRoles(roles);

        try{
            return formatUserDTODocument(usersRepository.save(validateAndSetDocument(u)));
        } catch (Exception e) {
            throw new BusinessRuleException("Valores Invalidos!");
        }
    }

    public UsersDTO update (Integer id, UsersCreateDTO usersCreateDTO) throws BusinessRuleException {
        getById(id);
        UsersEntity u = objectMapper.convertValue(usersCreateDTO, UsersEntity.class);
        u.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
        u.setIdUser(id);
        return formatUserDTODocument(usersRepository.save(validateAndSetDocument(u)));
    }

    public UsersDTO delete (Integer id) throws BusinessRuleException {
        UsersDTO u = getById(id);
        usersRepository.deleteById(id);
        return u;
    }



    private UsersDTO formatUserDTODocument (UsersEntity usersEntity) {
        if ((usersEntity.getType())){
            CNPJ cnpj = new CNPJ(usersEntity.getDocument());
            usersEntity.setDocument(cnpj.getCNPJ(true));
        } else {
            CPF cpf = new CPF(usersEntity.getDocument());
            usersEntity.setDocument(cpf.getCPF(true));
        }
        return objectMapper.convertValue(usersEntity, UsersDTO.class);
    }

    public UsersEntity validateAndSetDocument (UsersEntity usersEntity) throws BusinessRuleException {

        CNPJ cnpj = new CNPJ(usersEntity.getDocument());
        CPF cpf = new CPF(usersEntity.getDocument());

        if (cnpj.isCNPJ()){
            UsersEntity u = usersRepository.findByDocument(cnpj.getCNPJ(false));
            if (u != null && validUpdateDocument(u)){
                throw new BusinessRuleException("CNPJ Invalido!");
            }
            usersEntity.setType(true);
            usersEntity.setDocument(cnpj.getCNPJ(false));
            return usersEntity;

        } else if (cpf.isCPF()){
            UsersEntity u = usersRepository.findByDocument(cpf.getCPF(false));
            if (u != null && validUpdateDocument(u)){
                throw new BusinessRuleException("CPF Invalido!");
            }
            usersEntity.setType(false);
            usersEntity.setDocument(cpf.getCPF(false));
            return usersEntity;

        } else {
            throw new BusinessRuleException("CPF ou CNPJ invalido!");
        }
    }

    private Boolean validUpdateDocument (UsersEntity usersEntity) throws BusinessRuleException {
         if (usersEntity.getIdUser() != null) {
            UsersEntity u = objectMapper.convertValue(getById(usersEntity.getIdUser()), UsersEntity.class);
             return !usersEntity.getDocument().equals(u.getDocument());
         }
        return true;
    }

    public UsersEntity findByLogin(String email){
        return usersRepository.findByEmail(email);}

}

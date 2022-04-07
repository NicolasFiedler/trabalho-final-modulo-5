package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.bankaccount.BankAccountCreateDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.bankaccount.BankAccountDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.BankAccountEntity;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.exception.BusinessRuleException;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.repository.BankAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final ObjectMapper objectMapper;

    public BankAccountDTO create(BankAccountCreateDTO bankAcccountCreate) throws Exception {

        BankAccountEntity bankAccountEntity = objectMapper.convertValue(bankAcccountCreate, BankAccountEntity.class);
        BankAccountEntity bankAccountEntityCreated = bankAccountRepository.save(bankAccountEntity);
        return objectMapper.convertValue(bankAccountEntityCreated, BankAccountDTO.class);
    }


    public BankAccountDTO update(Integer id,
                                 BankAccountCreateDTO bankAccountUpdate) throws Exception {
        BankAccountDTO bankAccountDTO = getBankAccountById(id);
        bankAccountDTO.setAccountNumber(bankAccountUpdate.getAccountNumber());
        bankAccountDTO.setAgency(bankAccountUpdate.getAgency());
        BankAccountEntity bankAccountEntity = objectMapper.convertValue(bankAccountDTO, BankAccountEntity.class);
        bankAccountRepository.save(bankAccountEntity);
        return bankAccountDTO;
    }

    public List<BankAccountDTO>list(){
        return bankAccountRepository.findAll()
                .stream()
                .map(bankAccountEntity -> objectMapper.convertValue(bankAccountEntity, BankAccountDTO.class))
                .collect(Collectors.toList());
    }

    public BankAccountDTO getBankAccountById(Integer id) throws BusinessRuleException {
        return bankAccountRepository.findById(id).stream()
                .map(bankAccountEntity -> objectMapper.convertValue(bankAccountEntity, BankAccountDTO.class))
                .findAny()
                .orElseThrow(() -> new BusinessRuleException("Conta bancaria nao encontrada!"));
    }

    public BankAccountDTO delete(Integer id) throws Exception {
        BankAccountDTO bankAccountDTO = getBankAccountById(id);
        bankAccountRepository.deleteById(id);
        return bankAccountDTO;

    }

}

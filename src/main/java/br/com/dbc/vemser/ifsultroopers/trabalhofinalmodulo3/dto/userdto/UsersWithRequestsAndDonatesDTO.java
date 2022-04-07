package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.userdto;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request.RequestWithDonatesDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersWithRequestsAndDonatesDTO extends UsersDTO{
    List<RequestWithDonatesDTO> requestWithDonatesDTOList;
}

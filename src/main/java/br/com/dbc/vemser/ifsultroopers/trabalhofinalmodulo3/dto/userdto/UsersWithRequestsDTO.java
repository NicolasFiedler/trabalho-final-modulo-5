package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.userdto;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request.RequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersWithRequestsDTO extends UsersDTO{

    private List<RequestDTO> Requests;
}

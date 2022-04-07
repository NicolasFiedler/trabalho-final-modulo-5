package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.userdto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO extends UsersCreateDTO {

    @ApiModelProperty(value = "Id do Usuário")
    private Integer idUser;

}

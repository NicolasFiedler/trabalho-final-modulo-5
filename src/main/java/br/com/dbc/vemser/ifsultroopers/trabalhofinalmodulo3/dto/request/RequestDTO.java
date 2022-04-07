package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.Category;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO extends RequestCreateDTO {

    @ApiModelProperty(value = "Id da Vakinha")
    private Integer idRequest;

    @ApiModelProperty(value = "Id do Usuário")
    private Integer idUser;

    @ApiModelProperty(value = "Valor arrecadado")
    private Double reachedValue;

    @ApiModelProperty(value = "Categoria")
    private Category category;

    @ApiModelProperty(value = "Status")
    private Boolean statusRequest;
}

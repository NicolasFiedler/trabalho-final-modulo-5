package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.Category;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateDTO {

    @ApiModelProperty(value = "Título")
    @Size(max = 250)
    private String title;

    @ApiModelProperty(value = "Descrição")
    private String description;

    @ApiModelProperty(value = "Meta")
    private Double goal;

}

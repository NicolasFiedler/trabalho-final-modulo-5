package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCreateDTO {
    @ApiModelProperty(value = "Título")
    @NotEmpty
    private String title;

    @ApiModelProperty(value = "Descrição")
    private String description;

    @ApiModelProperty(value = "Meta")
    @NotNull
    private Double goal;

    @ApiModelProperty(value = "Id do banco")
    @NotNull
    private Integer idBankAccount;
}
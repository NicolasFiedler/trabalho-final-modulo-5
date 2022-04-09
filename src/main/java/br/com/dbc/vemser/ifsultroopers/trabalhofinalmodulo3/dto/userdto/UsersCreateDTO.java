package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.userdto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersCreateDTO {
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 250)
    @ApiModelProperty(value = "Nome")
    private String name;

    @NotNull
    @NotEmpty
    @Email
    @ApiModelProperty(value = "Email")
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 8)
    @ApiModelProperty(value = "Senha")
    private String password;

    @NotNull
    @NotEmpty
    @ApiModelProperty(value = "CPF/CNPJ")
    private String document;
}

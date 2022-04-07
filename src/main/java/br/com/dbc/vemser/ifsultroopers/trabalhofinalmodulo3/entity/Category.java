package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Category {

    COMBATE_A_FOME("Combate a Fome",0),
    CRIANÇAS("Crianças",1),
    ENFERMOS("Enfermos",2),
    COMBATE_A_COVID_19("Combate a COVID-19",3),
    CAUSAS_AMBIENTAIS("Causas Ambientais",4),
    SOBREVIVENTES_DE_GUERRA("Sobreviventes de Guerra",5),
    ANIMAIS("Animais",6),
    SONHOS("Sonhos",7),
    POBREZA("Pobreza",8),
    OUTROS("Outros",9);

    private String description;
    private Integer type;

    Category(Integer type) {
        this.type = type;
    }
    public Integer getType() {
        return type;
    }

    Category(String description) {
        this.description = description;
    }
    public String getDescription() {
        return this.description;
    }

    public static Category ofType(Integer type) {
        return Arrays.stream(Category.values())
                .filter(tp -> tp.getType().equals(type))
                .findFirst()
                .get();
    }

}

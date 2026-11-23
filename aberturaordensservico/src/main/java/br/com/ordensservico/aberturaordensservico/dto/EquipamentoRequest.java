package br.com.ordensservico.aberturaordensservico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EquipamentoRequest {

    @NotBlank (message = "O nome do equipamento é obrigatório")
    private String nome;

    @NotBlank (message = "O número do patrimônio do equipamento é obrigatório")
    private String numeroPatrimonio;

    @NotNull (message = "O setor é obrigatório")
    private Integer setorId;

    public EquipamentoRequest() {
    }

    public String getNome() {
        return nome;
    }

    public String getNumeroPatrimonio() {
        return numeroPatrimonio;
    }

    public Integer getSetorId() {
        return setorId;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumeroPatrimonio(String numeroPatrimonio) {
        this.numeroPatrimonio = numeroPatrimonio;
    }

    public void setSetorId(Integer setorId) {
        this.setorId = setorId;
    }

    
}
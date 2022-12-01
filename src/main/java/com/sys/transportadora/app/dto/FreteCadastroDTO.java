package com.sys.transportadora.app.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FreteCadastroDTO {

  @NotBlank(message = "Descricao não pode ser vazio ou nulo")
  private String descricao;

  @NotNull
  @NotNull(message = "Peso não pode ser nulo")
  @DecimalMin(value = "0.00", message = "Valor não pode ser menor que zero")
  private Double peso;

  private Double valor;

  @NotNull(message = "Cliente não pode ser nulo")
  private Long clienteID;

  @NotNull(message = "Cidade não pode ser nulo")
  private Long cidadeID;
}

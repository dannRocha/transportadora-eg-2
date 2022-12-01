package com.sys.transportadora.app.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "frete")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Frete {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long codigo;

  @NotBlank(message = "Descricao não pode ser vazio ou nulo")
  private String descricao;

  @NotNull
  @NotNull(message = "Peso não pode ser nulo")
  @DecimalMin(value = "0.00", message = "Valor não pode ser menor que zero")
  private Double peso;

  @NotNull(message = "Valor não pode ser nulo")
  @DecimalMin(value = "0.00", message = "Valor não pode ser menor que zero")
  private Double valor;

  @ManyToOne
  @NotNull(message = "Cliente não pode ser nulo")
  @ToString.Exclude
  @JsonBackReference
  private Cliente cliente;

  @ManyToOne
  @NotNull(message = "Cidade não pode ser nulo")
  @ToString.Exclude
  @JsonBackReference
  private Cidade cidade;
}

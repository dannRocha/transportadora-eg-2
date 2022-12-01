package com.sys.transportadora.app.entidades;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "cidade")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Cidade {

  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long codigo;

  @NotBlank(message = "Nome não pode ser vazio ou nulo")
  private String nome;
  
  @NotBlank(message = "UF não pode ser vazio ou nulo")
  private String uf;
  
  @NotNull(message = "Taxa não pode ser vazio ou nulo")
  @DecimalMin(value = "0.00", message = "Taxa não pode ser menor que zero")
  private Double taxa;

  @OneToMany(mappedBy = "cidade")
  @ToString.Exclude
  @JsonManagedReference
  @EqualsAndHashCode.Exclude
  private List<Frete> fretes;

  public Cidade(Long codigo) {
    this.codigo = codigo;
  }

}

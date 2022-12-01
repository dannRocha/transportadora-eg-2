package com.sys.transportadora.app.entidades;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Cliente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long codigo;

  @NotBlank(message = "Nome não pode ser vazio ou nulo")
  @Column(name = "nome")
  private String nome;

  @NotBlank(message = "Endereco não pode ser vazio ou nulo")
  @Column(name = "endereco")
  private String endereco;

  @NotBlank(message = "Telefone não pode ser vazio ou nulo")
  @Column(name = "telefone")
  private String telefone;

  @OneToMany(mappedBy = "cliente")
  @JsonManagedReference
  private List<Frete> fretes;

  public Cliente(Long codigo) {
    this.codigo = codigo;
  }
}

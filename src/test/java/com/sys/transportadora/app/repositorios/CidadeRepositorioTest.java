package com.sys.transportadora.app.repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sys.transportadora.app.entidades.Cidade;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CidadeRepositorioTest {

  @Autowired
  CidadeRepositorio cidadeRepositorio;

  @Test
  void deveSalvarUmaNovaCidade() {
    var cidade = Cidade.builder()
      .nome("São Luís")
      .taxa(.20D)
      .uf("MA")
      .build();

    var cidadeSalva = cidadeRepositorio.save(cidade);

    assertNotNull(cidadeSalva);

    cidade.setCodigo(cidadeSalva.getCodigo());

    assertEquals(cidade, cidadeSalva);
  }

  @Test
  @Sql("classpath:inserir-cidades.sql")
  void deveBuscarCidadePorNome() {
    var cidade = Cidade.builder()
      .nome("Raposa")
      .uf("MA")
      .build();

      assertTrue(cidadeRepositorio.findFirstByNome(cidade.getNome()).isPresent());
  }

  @Test
  void naoDeveSalvarUmaNovaCidadeComNomeAusente() {
    var cidade = Cidade.builder()
      .taxa(.20D)
      .uf("MA")
      .build();

    var excecao = assertThrows(ConstraintViolationException .class, () -> {
      cidadeRepositorio.save(cidade);
    });

    var esperado = "Nome não pode ser vazio ou nulo";
    assertTrue(excecao.getMessage().contains(esperado));
  }

  @Test
  void naoDeveSalvarUmaNovaCidadeComUFAusente() {
    var cidade = Cidade.builder()
      .nome("São Luís")
      .taxa(.20D)
      .build();

      var excecao = assertThrows(ConstraintViolationException .class, () -> {
        cidadeRepositorio.save(cidade);
      });

      var esperado = "UF não pode ser vazio ou nulo";
      assertTrue(excecao.getMessage().contains(esperado));
  }

  @Test
  void naoDeveSalvarUmaNovaCidadeComTaxaAusente() {
    var cidade = Cidade.builder()
      .nome("São Luís")
      .uf("MA")
      .build();

      var excecao = assertThrows(ConstraintViolationException .class, () -> {
        cidadeRepositorio.save(cidade);
      });

      var esperado = "Taxa não pode ser vazio ou nulo";
      assertTrue(excecao.getMessage().contains(esperado));
  }

  @Test
  void naoDeveSalvarUmaNovaCidadeComTaxaMenorQueZero() {
    var cidade = Cidade.builder()
      .nome("São Luís")
      .taxa(-0.001)
      .uf("MA")
      .build();

      var excecao = assertThrows(ConstraintViolationException .class, () -> {
        cidadeRepositorio.save(cidade);
      });

      var esperado = "Taxa não pode ser menor que zero";
      assertTrue(excecao.getMessage().contains(esperado));
  }
}

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

import com.sys.transportadora.app.entidades.Frete;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class FreteRepositorioTest {

  @Autowired
  FreteRepositorio freteRepositorio;

  @Autowired
  ClienteRepositorio clienteRepositorio;
  
  @Autowired
  CidadeRepositorio cidadeRepositorio;

  @Test
  @Sql({"classpath:inserir-clientes.sql", "classpath:inserir-cidades.sql"})
  void deveSalvarFrente() {

    var cliente = clienteRepositorio.findFirstByNome("Cliente 1");
    var cidade = cidadeRepositorio.findFirstByNome("Raposa");

    

    var frete = Frete.builder()
      .descricao("Frente 1")
      .peso(1.5D)
      .valor(10.0D)
      .cliente(cliente.get())
      .cidade(cidade.get())
      .build();

    var freteSalvo = freteRepositorio.save(frete);

    assertNotNull(freteSalvo);

    frete.setCodigo(freteSalvo.getCodigo());

    assertEquals(frete, freteSalvo);
  }

  @Test
  @Sql({"classpath:inserir-clientes.sql", "classpath:inserir-cidades.sql"})
  void naoDeveSalvarUmNovoFreteComValorAusente() {
    
    var cliente = clienteRepositorio.findFirstByNome("Cliente 1");
    var cidade = cidadeRepositorio.findFirstByNome("Raposa");


    var frete = Frete.builder()
      .descricao("Frente 1")
      .peso(1.5D) 
      .cliente(cliente.get())
      .cidade(cidade.get())
      .build();

      var excecao = assertThrows(ConstraintViolationException.class, () -> {
        freteRepositorio.save(frete);
      });

      var esperado = "Valor não pode ser nulo";
      assertTrue(excecao.getMessage().contains(esperado));
  }

  @Test
  @Sql({"classpath:inserir-clientes.sql", "classpath:inserir-cidades.sql"})
  void naoDeveSalvarUmNovoFreteComPesoAusente() {
    
    var cliente = clienteRepositorio.findFirstByNome("Cliente 1");
    var cidade = cidadeRepositorio.findFirstByNome("Raposa");


    var frete = Frete.builder()
      .descricao("Frente 1")
      .valor(10.0D)
      .cliente(cliente.get())
      .cidade(cidade.get())
      .build();

      var excecao = assertThrows(ConstraintViolationException.class, () -> {
        freteRepositorio.save(frete);
      });

      var esperado = "Peso não pode ser nulo";
      assertTrue(excecao.getMessage().contains(esperado));
  }

  @Test
  @Sql({"classpath:inserir-clientes.sql", "classpath:inserir-cidades.sql"})
  void naoDeveSalvarUmNovoFreteComDescricaoAusente() {
    
    var cliente = clienteRepositorio.findFirstByNome("Cliente 1");
    var cidade = cidadeRepositorio.findFirstByNome("Raposa");


    var frete = Frete.builder()
      .peso(1.5D)
      .valor(10.0D)
      .cliente(cliente.get())
      .cidade(cidade.get())
      .build();

    var excecao = assertThrows(ConstraintViolationException.class, () -> {
      freteRepositorio.save(frete);
    });

    var esperado = "Descricao não pode ser vazio ou nulo";
    assertTrue(excecao.getMessage().contains(esperado));
}

  @Test
  @Sql({"classpath:inserir-clientes.sql", "classpath:inserir-cidades.sql"})
  void naoDeveSalvarUmNovoFreteComClienteAusente() {
    
    var cidade = cidadeRepositorio.findFirstByNome("Raposa");

    var frete = Frete.builder()
      .descricao("Frente 1")
      .peso(1.5D)
      .valor(10.0D)
      .cidade(cidade.get())
      .build();

    var excecao = assertThrows(ConstraintViolationException.class, () -> {
      freteRepositorio.save(frete);
    });

    var esperado = "Cliente não pode ser nulo";
    assertTrue(excecao.getMessage().contains(esperado));
  }

  @Test
  @Sql({"classpath:inserir-clientes.sql", "classpath:inserir-cidades.sql"})
  void naoDeveSalvarUmNovoFreteComCidadeAusente() {
    
    var cliente = clienteRepositorio.findFirstByNome("Cliente 1");
  
    var frete = Frete.builder()
      .descricao("Frente 1")
      .peso(1.5D)
      .valor(10.0D)
      .cliente(cliente.get())
      .build();

    var excecao = assertThrows(ConstraintViolationException.class, () -> {
      freteRepositorio.save(frete);
    });

    var esperado = "Cidade não pode ser nulo";
    assertTrue(excecao.getMessage().contains(esperado));
  }
}

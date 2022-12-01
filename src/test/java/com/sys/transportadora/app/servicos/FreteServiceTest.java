package com.sys.transportadora.app.servicos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.Validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sys.transportadora.app.entidades.Frete;
import com.sys.transportadora.app.excecoes.FreteInvalidoException;
import com.sys.transportadora.app.repositorios.CidadeRepositorio;
import com.sys.transportadora.app.repositorios.ClienteRepositorio;
import com.sys.transportadora.app.repositorios.FreteRepositorio;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class FreteServiceTest {
  
  @Autowired
  FreteRepositorio freteRepositorio;

  @Autowired
  ClienteRepositorio clienteRepositorio;
  
  @Autowired
  CidadeRepositorio cidadeRepositorio;
  
  FreteService freteService;

  @BeforeEach
  void setup() {
    var factory = Validation.buildDefaultValidatorFactory();
    freteService = new FreteService(freteRepositorio, cidadeRepositorio, clienteRepositorio, factory.getValidator());
  }


  @Test
  @Sql(scripts = "classpath:inserir-clientes.sql", config = @SqlConfig(transactionMode =  SqlConfig.TransactionMode.ISOLATED))
  void deveLancarExcecaoParaCadastroDeFreteInvalido() {
    var cliente = clienteRepositorio.findFirstByNome("Cliente 1").get();
    var cidade = cidadeRepositorio.findFirstByNome("Raposa").get();

    cliente.setCodigo(999999L);
    cidade.setCodigo(999009L);

    var frete = Frete.builder()
      .descricao("Frente 1")
      .peso(1.5D)
      .valor(10.0D)
      .cliente(cliente)
      .cidade(cidade)
      .build();

    assertThrows(FreteInvalidoException.class, () -> {
      freteService.cadastrarFrete(frete);
    });
  }



  @Test
  @Sql(scripts = "classpath:inserir-clientes.sql", config = @SqlConfig(transactionMode =  SqlConfig.TransactionMode.ISOLATED))
  @Sql(scripts = "classpath:inserir-cidades.sql", config = @SqlConfig(transactionMode =  SqlConfig.TransactionMode.ISOLATED))
  @Sql(scripts = "classpath:inserir-fretes.sql", config = @SqlConfig(transactionMode =  SqlConfig.TransactionMode.ISOLATED))
  void deveBuscarCidadeComMaiorNumeroDeFretes() {
    var espero = cidadeRepositorio.findById(3L);
    var cidade = freteService.buscarCidadeComMaiorNumeroDeFretes();
  
    assertEquals(espero.get(), cidade.get());
  }

  @Test
  @Sql(scripts = "classpath:inserir-clientes.sql", config = @SqlConfig(transactionMode =  SqlConfig.TransactionMode.ISOLATED))
  @Sql(scripts = "classpath:inserir-cidades.sql", config = @SqlConfig(transactionMode =  SqlConfig.TransactionMode.ISOLATED))
  @Sql(scripts = "classpath:inserir-fretes.sql", config = @SqlConfig(transactionMode =  SqlConfig.TransactionMode.ISOLATED))
  void deveRecuperarOFreteComMaiorValorDeCusto() {
    var espero = 70D;
    var frete = freteRepositorio.buscarMaiorValorFrete();

    assertTrue(frete.isPresent());

    assertEquals(espero, frete.get().getValor());
  }


  @Test
  @Sql({"classpath:inserir-clientes.sql", "classpath:inserir-cidades.sql"})
  @Sql({"classpath:inserir-fretes.sql"})
  void deveCalcularValorFrente() {
    var cliente = clienteRepositorio.findFirstByNome("Cliente 1").get();
    var cidade = cidadeRepositorio.findFirstByNome("Raposa").get();

    var frete = Frete.builder()
      .descricao("Frente 1")
      .peso(1.5D)
      .cliente(cliente)
      .cidade(cidade)
      .build();

    var freteSalvo = freteService.cadastrarFrete(frete);

    var esperoValorFrete = frete.getPeso() * 10D;

    assertEquals(esperoValorFrete, freteSalvo.getValor());

  }
}

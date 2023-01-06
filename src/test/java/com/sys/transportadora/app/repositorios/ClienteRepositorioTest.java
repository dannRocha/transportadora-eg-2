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

import com.sys.transportadora.app.entidades.Cliente;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ClienteRepositorioTest {
  @Autowired
  ClienteRepositorio clienteRepositorio;


  @Test
  void deveSalvarUmNovoCliente() {
    var cliente = Cliente.builder()
      .endereco("Rua N")
      .nome("Astrogildo Silva")
      .telefone("+55090")
      .build();

    var clienteSalvo = clienteRepositorio.save(cliente);

    assertNotNull(clienteSalvo);
    cliente.setCodigo(clienteSalvo.getCodigo());

    assertEquals(cliente, clienteSalvo);
  }

  @Test
  void naoDeveSalvarUmNovoClienteComNomeAusente() {
    var cliente = Cliente.builder()
      .endereco("Rua N")
      .telefone("+5598988888888")
      .build();

    var excecao = assertThrows(ConstraintViolationException.class, () -> {
      clienteRepositorio.save(cliente);
    });

    var esperado = "Nome não pode ser vazio ou nulo";
    assertTrue(excecao.getMessage().contains(esperado));
  }

   
  @Test
  void naoDeveSalvarUmNovoClienteComEnderecoAusente() {
    var cliente = Cliente.builder()
      .nome("Astrogildo Silva")
      .telefone("+5598988888888")
      .build();

    var excecao = assertThrows(ConstraintViolationException.class, () -> {
      clienteRepositorio.save(cliente);
    });

    var esperado = "Endereco não pode ser vazio ou nulo";
    assertTrue(excecao.getMessage().contains(esperado));
  }

  @Test
  void naoDeveSalvarUmNovoClienteComTelefoneAusente() {
    var cliente = Cliente.builder()
      .endereco("Rua N")
      .nome("Astrogildo Silva")
      .build();

    var excecao = assertThrows(ConstraintViolationException.class, () -> {
      clienteRepositorio.save(cliente);
    });

    var esperado = "Telefone não pode ser vazio ou nulo";
    assertTrue(excecao.getMessage().contains(esperado));
  }

  @Test
  @Sql("classpath:inserir-clientes.sql")
  void deveBuscarClientePorNome() {
    var cliente = Cliente.builder()
      .nome("Cliente 2")
      .build();

    assertTrue(clienteRepositorio.findFirstByNome(cliente.getNome()).isPresent());
  }

}

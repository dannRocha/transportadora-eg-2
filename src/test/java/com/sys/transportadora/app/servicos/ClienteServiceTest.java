package com.sys.transportadora.app.servicos;

import javax.validation.Validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sys.transportadora.app.repositorios.ClienteRepositorio;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ClienteServiceTest {
  
  @Autowired
  ClienteRepositorio clienteRepositorio;

  ClienteService clienteService;

  @BeforeEach
  void setup() {
    var factory = Validation.buildDefaultValidatorFactory();
    clienteService = new ClienteService(clienteRepositorio, factory.getValidator());
  }
}

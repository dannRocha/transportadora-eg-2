package com.sys.transportadora.app.servicos;

import java.util.Optional;

import javax.validation.Validator;

import org.springframework.stereotype.Service;

import com.sys.transportadora.app.entidades.Cliente;
import com.sys.transportadora.app.excecoes.ClienteInvalidoException;
import com.sys.transportadora.app.repositorios.ClienteRepositorio;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClienteService {
  
  private final ClienteRepositorio clienteRepositorio;
  private final Validator validator;

  public Cliente salvarCliente(Cliente cliente) {
    var violations = validator.validate(cliente);

    if(!violations.isEmpty()) {
      throw new ClienteInvalidoException();
    }
    return clienteRepositorio.save(cliente);
  }

  public Optional<Cliente> buscarClientePorTelefone(String telefone) {
    return clienteRepositorio.findByTelefone(telefone);
  }

  public Optional<Cliente> buscarClientePorNome(String nome) {
    return clienteRepositorio.findFirstByNome(nome);
  }
}

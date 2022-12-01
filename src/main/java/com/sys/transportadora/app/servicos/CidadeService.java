package com.sys.transportadora.app.servicos;


import java.util.Optional;

import javax.validation.Validator;

import org.springframework.stereotype.Service;

import com.sys.transportadora.app.entidades.Cidade;
import com.sys.transportadora.app.excecoes.CidadeInvalidaException;
import com.sys.transportadora.app.repositorios.CidadeRepositorio;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CidadeService {
  
  private final CidadeRepositorio cidadeRepositorio;
  private final Validator validator;

  public Cidade cadastrarCidade(Cidade cidade) {

    var violations = validator.validate(cidade);

    if(!violations.isEmpty()) {
      throw new CidadeInvalidaException();
    }
    return cidadeRepositorio.save(cidade);
  }

  public Optional<Cidade> buscarCidadePorNome(String nome) {
    return cidadeRepositorio.findFirstByNome(nome);
  }
}

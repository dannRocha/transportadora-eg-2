package com.sys.transportadora.app.servicos;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Validator;

import org.springframework.stereotype.Service;

import com.sys.transportadora.app.entidades.Cidade;
import com.sys.transportadora.app.entidades.Frete;
import com.sys.transportadora.app.excecoes.FreteInvalidoException;
import com.sys.transportadora.app.excecoes.http.FreteNotFoundException;
import com.sys.transportadora.app.repositorios.CidadeRepositorio;
import com.sys.transportadora.app.repositorios.ClienteRepositorio;
import com.sys.transportadora.app.repositorios.FreteRepositorio;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FreteService {
  
  private final FreteRepositorio freteRepositorio;
  private final CidadeRepositorio cidadeRepositorio;
  private final ClienteRepositorio clienteRepositorio;

  private final Validator validator;

  private static final Double FRETE_TAXA_FIXA = 10D;

  public Frete cadastrarFrete(Frete frete) {

    if(Objects.nonNull(frete.getCidade()) && Objects.nonNull(frete.getPeso())) {
      calcularValorFrete(frete);
    }

    var violations = validator.validate(frete);

    if(!violations.isEmpty()) {
      throw new FreteInvalidoException();
    }

    var cliente = clienteRepositorio.findById(frete.getCliente().getCodigo());
    var cidade = cidadeRepositorio.findById(frete.getCidade().getCodigo());

    if(cliente.isEmpty() || cidade.isEmpty()) {
      throw new FreteInvalidoException();
    }

    return freteRepositorio.save(frete);
  }

  private void calcularValorFrete(Frete frete) {
    var cidadeSalva = cidadeRepositorio.findById(frete.getCidade().getCodigo());

    cidadeSalva.ifPresent(cidade -> {
      final Double ZERO = 0.0D;
      Double peso = 1D;
      if(!ZERO.equals(frete.getPeso()) || !(frete.getPeso() < ZERO))
        peso = frete.getPeso();

      frete.setValor(peso * FRETE_TAXA_FIXA);
      
    });
  }

  public Frete buscarFretePorCodigo(Long codigo) {
    var freteSalvo = freteRepositorio.findById(codigo);

    if(freteSalvo.isEmpty())
      throw new FreteNotFoundException();

    return freteSalvo.get();
  }
  
  public Optional<Cidade> buscarCidadeComMaiorNumeroDeFretes() {
    var codigoCidade = freteRepositorio.buscarCodigoCidadeComMaiorNumeroDeFretes();
    return cidadeRepositorio.findById(codigoCidade);
  }

  public List<Frete> buscarFretes() {
    return freteRepositorio.findAll();
  }

  public Frete removerFretePorCodigo(Long codigo) {
    var freteSalvo = buscarFretePorCodigo(codigo);

    freteRepositorio.deleteById(freteSalvo.getCodigo());

    return freteSalvo;
  }
}

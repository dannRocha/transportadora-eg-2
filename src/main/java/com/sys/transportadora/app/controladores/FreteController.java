package com.sys.transportadora.app.controladores;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sys.transportadora.app.dto.FreteCadastroDTO;
import com.sys.transportadora.app.dto.FreteDetalhesDTO;
import com.sys.transportadora.app.dto.FreteRemovidoDTO;
import com.sys.transportadora.app.entidades.Cidade;
import com.sys.transportadora.app.entidades.Cliente;
import com.sys.transportadora.app.entidades.Frete;
import com.sys.transportadora.app.servicos.FreteService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/frete")
@AllArgsConstructor
public class FreteController {
  
  private final FreteService freteService;
  private final ObjectMapper mapper;

  @GetMapping
  public List<Frete> buscarTodosFretes() {
    return freteService.buscarFretes();
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public FreteDetalhesDTO buscarFretePorCodigo(@PathVariable  Long id) {
    var frete = freteService.buscarFretePorCodigo(id);
    var freteDetalhes = new FreteDetalhesDTO();
    
    BeanUtils.copyProperties(frete, freteDetalhes);

    return freteDetalhes;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Frete cadastrarFrete(@RequestBody  @Valid FreteCadastroDTO frete) {
    var freteEntity = new Frete();
    BeanUtils.copyProperties(frete, freteEntity);

    freteEntity.setCidade(new Cidade(frete.getCidadeID()));
    freteEntity.setCliente(new Cliente(frete.getClienteID()));

    return freteService.cadastrarFrete(freteEntity);
  }


  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public FreteRemovidoDTO removerFretePorCodigo(@PathVariable  Long id) {
    var freteRemovido = freteService.removerFretePorCodigo(id);
    return new FreteRemovidoDTO(freteRemovido.getCodigo());
  }
}

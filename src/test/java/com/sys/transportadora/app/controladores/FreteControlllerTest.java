package com.sys.transportadora.app.controladores;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import com.sys.transportadora.app.dto.FreteCadastroDTO;
import com.sys.transportadora.app.dto.FreteRemovidoDTO;
import com.sys.transportadora.app.repositorios.CidadeRepositorio;
import com.sys.transportadora.app.repositorios.ClienteRepositorio;
import com.sys.transportadora.app.repositorios.FreteRepositorio;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"classpath:inserir-clientes.sql", "classpath:inserir-cidades.sql"})
@Sql({"classpath:inserir-fretes.sql"})
public class FreteControlllerTest {
  
  @Autowired
	private TestRestTemplate testRestTemplate;

  @Autowired
  ClienteRepositorio clienteRepositorio;
  
  @Autowired
  CidadeRepositorio cidadeRepositorio;

  @Autowired
  FreteRepositorio freteRepositorio; 

  
  @BeforeEach
  void setup() {
  }
  
  @Test
  void deveRetornarUmaListaDeFretes() {
    var resposta = testRestTemplate.exchange("/frete/", HttpMethod.GET, null, String.class);
    
    assertFalse(resposta.getBody().isBlank());
    assertEquals(resposta.getStatusCode(), HttpStatus.OK);
  }

  @Test
  void deveSalvarUmNovoCadastroDeFrete() {
    var cliente = clienteRepositorio.findFirstByNome("Cliente 1").get();
    var cidade = cidadeRepositorio.findFirstByNome("Raposa").get();

    var frete = FreteCadastroDTO.builder()
      .descricao("Frente 1")
      .peso(1.5D)
      .clienteID(cliente.getCodigo())
      .cidadeID(cidade.getCodigo())
      .build();

    var httpEntity = new HttpEntity<>(frete);
    var resposta = testRestTemplate.exchange("/frete", HttpMethod.POST, httpEntity, FreteCadastroDTO.class);

    assertEquals(resposta.getStatusCode(), HttpStatus.CREATED);
  }


  @Test
  void naoDeveSalvarUmNovoCadastroDeFreteQuandoRequestInvalida() {
    var frete = FreteCadastroDTO.builder()
      .build();

    var httpEntity = new HttpEntity<>(frete);
    var resposta = testRestTemplate.exchange("/frete", HttpMethod.POST, httpEntity, FreteCadastroDTO.class);

    assertEquals(resposta.getStatusCode(), HttpStatus.BAD_REQUEST);
  }


  @Test
  void deveRemoverUmCadastroDeFrete() {
    var freteID = 1L;
    var freteSalvo = freteRepositorio.findById(freteID);

    assertTrue(freteSalvo.isPresent());


    var frete = freteSalvo.get();    
    var  resposta = testRestTemplate.exchange("/frete/{id}", HttpMethod.DELETE, null, FreteRemovidoDTO.class, frete.getCodigo());

    assertEquals(resposta.getStatusCode(), HttpStatus.OK);
  }

  @Test
  void deveBuscarUmFretePorCodigo() {
    var freteID = 1L;
  
    var  resposta = testRestTemplate.exchange("/frete/{id}", HttpMethod.GET, null, FreteRemovidoDTO.class, freteID);

    assertEquals(resposta.getStatusCode(), HttpStatus.OK);
  }
}

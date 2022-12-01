package com.sys.transportadora.app.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sys.transportadora.app.entidades.Cidade;
import com.sys.transportadora.app.entidades.Frete;

@Repository
public interface FreteRepositorio extends JpaRepository<Frete, Long>{

  // Long countByCidade();

  // Long countByCidade();

  // @Query("select max(count(f.cidade)) from Frete f")
  @Query(
    value = "SELECT cidade_codigo FROM (SELECT cidade_codigo, count(cidade_codigo) AS total FROM frete GROUP BY cidade_codigo ORDER BY total DESC LIMIT 1) AS F",
    nativeQuery = true)
  Long buscarCodigoCidadeComMaiorNumeroDeFretes();
  
  @Query(
    value = "SELECT * FROM frete ORDER BY valor DESC LIMIT 1",
    nativeQuery = true)
  Optional<Frete> buscarMaiorValorFrete();

  // @Query(value = "SELECT f FROM Frete f ORDER BY f.valor DESC LIMIT 1")
  // Frete buscarMaiorValorFrete();
  // countAllByCidade();
}

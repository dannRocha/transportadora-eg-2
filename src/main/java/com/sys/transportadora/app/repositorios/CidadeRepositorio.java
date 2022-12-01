package com.sys.transportadora.app.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sys.transportadora.app.entidades.Cidade;


@Repository
public interface CidadeRepositorio extends JpaRepository<Cidade, Long> {
  Optional<Cidade> findFirstByNome(String nome); 
}

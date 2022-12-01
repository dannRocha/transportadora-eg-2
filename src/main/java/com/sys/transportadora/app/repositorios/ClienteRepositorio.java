package com.sys.transportadora.app.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sys.transportadora.app.entidades.Cliente;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long>{
  Optional<Cliente> findByTelefone(String telefone);
  Optional<Cliente> findFirstByNome(String nome);
}

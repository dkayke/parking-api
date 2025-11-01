package br.edu.infnet.dannyelapi.repository;

import br.edu.infnet.dannyelapi.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCpf(String cpf);
    
    Optional<Cliente> findByEmail(String email);
}
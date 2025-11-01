package br.edu.infnet.dannyelapi.repository;

import br.edu.infnet.dannyelapi.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    List<Veiculo> findByModeloContainingIgnoreCase(String modelo);

    Optional<Veiculo> findByPlaca(String placa);
}
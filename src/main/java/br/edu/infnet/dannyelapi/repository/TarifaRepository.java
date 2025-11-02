package br.edu.infnet.dannyelapi.repository;

import br.edu.infnet.dannyelapi.model.Tarifa;
import br.edu.infnet.dannyelapi.model.TipoVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    Optional<Tarifa> findByTipoVeiculo(TipoVeiculo tipoVeiculo);
}
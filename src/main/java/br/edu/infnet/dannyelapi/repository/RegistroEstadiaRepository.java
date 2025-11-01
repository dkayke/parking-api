package br.edu.infnet.dannyelapi.repository;

import br.edu.infnet.dannyelapi.model.RegistroEstadia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RegistroEstadiaRepository extends JpaRepository<RegistroEstadia, Long> {

    List<RegistroEstadia> findByEntradaBetween(LocalDateTime inicio, LocalDateTime fim);
}
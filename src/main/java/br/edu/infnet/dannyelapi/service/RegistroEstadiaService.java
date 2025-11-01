package br.edu.infnet.dannyelapi.service;

import br.edu.infnet.dannyelapi.model.RegistroEstadia;
import br.edu.infnet.dannyelapi.model.Veiculo;
import br.edu.infnet.dannyelapi.repository.RegistroEstadiaRepository;
import br.edu.infnet.dannyelapi.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RegistroEstadiaService {

    private final RegistroEstadiaRepository registroEstadiaRepository;
    private final VeiculoRepository veiculoRepository;

    @Autowired
    public RegistroEstadiaService(RegistroEstadiaRepository registroEstadiaRepository,
            VeiculoRepository veiculoRepository) {
        this.registroEstadiaRepository = registroEstadiaRepository;
        this.veiculoRepository = veiculoRepository;
    }

    public List<RegistroEstadia> findAll() {
        return registroEstadiaRepository.findAll();
    }

    public RegistroEstadia findById(Long id) {
        return registroEstadiaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Registro de Estadia não encontrado com ID: " + id));
    }

    public RegistroEstadia save(RegistroEstadia registroEstadia, Long veiculoId) {
        Veiculo veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Veículo não encontrado com ID: " + veiculoId + " para associar ao registro."));

        registroEstadia.setVeiculo(veiculo);

        veiculo.setEstacionado(true);
        veiculoRepository.save(veiculo);

        return registroEstadiaRepository.save(registroEstadia);
    }

    public RegistroEstadia registrarSaida(Long registroId, double valor) {
        RegistroEstadia registro = findById(registroId);
        registro.setSaida(LocalDateTime.now());
        registro.setValor(valor);

        Veiculo veiculo = registro.getVeiculo();
        veiculo.setEstacionado(false);
        veiculoRepository.save(veiculo);

        return registroEstadiaRepository.save(registro);
    }

    public void deleteById(Long id) {
        if (!registroEstadiaRepository.existsById(id)) {
            throw new NoSuchElementException("Registro de Estadia não encontrado com ID: " + id);
        }
        registroEstadiaRepository.deleteById(id);
    }

    public List<RegistroEstadia> findByEntradaBetween(LocalDateTime inicio, LocalDateTime fim) {
        return registroEstadiaRepository.findByEntradaBetween(inicio, fim);
    }
}
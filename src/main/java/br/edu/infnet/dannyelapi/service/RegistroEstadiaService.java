package br.edu.infnet.dannyelapi.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.infnet.dannyelapi.model.RegistroEstadia;
import br.edu.infnet.dannyelapi.model.Tarifa;
import br.edu.infnet.dannyelapi.model.TipoVeiculo;
import br.edu.infnet.dannyelapi.model.Veiculo;
import br.edu.infnet.dannyelapi.repository.RegistroEstadiaRepository;
import br.edu.infnet.dannyelapi.repository.TarifaRepository;
import br.edu.infnet.dannyelapi.repository.VeiculoRepository;

@Service
public class RegistroEstadiaService {

    private final RegistroEstadiaRepository registroEstadiaRepository;
    private final VeiculoRepository veiculoRepository;
    private final TarifaRepository tarifaRepository;

    @Autowired
    public RegistroEstadiaService(RegistroEstadiaRepository registroEstadiaRepository,
            VeiculoRepository veiculoRepository,
            TarifaRepository tarifaRepository) {
        this.registroEstadiaRepository = registroEstadiaRepository;
        this.veiculoRepository = veiculoRepository;
        this.tarifaRepository = tarifaRepository;
    }

    public List<RegistroEstadia> findAll() {
        return registroEstadiaRepository.findAll();
    }

    public RegistroEstadia findById(Long id) {
        return registroEstadiaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Registro de Estadia não encontrado com ID: " + id));
    }

    @Transactional
    public RegistroEstadia save(RegistroEstadia registroEstadia, Long veiculoId) {
        Veiculo veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Veículo não encontrado com ID: " + veiculoId + " para associar ao registro."));

        registroEstadia.setVeiculo(veiculo);

        veiculo.setEstacionado(true);
        veiculoRepository.save(veiculo);

        return registroEstadiaRepository.save(registroEstadia);
    }

    @Transactional
    public RegistroEstadia registrarSaida(Long registroId) {
        RegistroEstadia registro = findById(registroId);

        if (registro.getSaida() != null) {
            throw new IllegalStateException("Esta estadia já foi finalizada.");
        }

        Veiculo veiculo = registro.getVeiculo();

        registro.setSaida(LocalDateTime.now());

        BigDecimal valorAPagar = calcularValor(veiculo.getTipoVeiculo(), registro.getEntrada(), registro.getSaida());
        registro.setValor(valorAPagar);

        veiculo.setEstacionado(false);
        veiculoRepository.save(veiculo);

        return registroEstadiaRepository.save(registro);
    }

    private BigDecimal calcularValor(TipoVeiculo tipoVeiculo, LocalDateTime entrada, LocalDateTime saida) {
        Tarifa tarifa = tarifaRepository.findByTipoVeiculo(tipoVeiculo)
                .orElseThrow(() -> new NoSuchElementException(
                        "Tarifa para o tipo " + tipoVeiculo + " não foi configurada."));

        long minutosTotais = Duration.between(entrada, saida).toMinutes();

        if (minutosTotais <= tarifa.getToleranciaMinutos()) {
            return BigDecimal.ZERO;
        }

        long horasTotais = (long) Math.ceil(minutosTotais / 60.0);

        long dias = horasTotais / 24;
        long horasRestantes = horasTotais % 24;

        BigDecimal valorTotal = tarifa.getValorDiaria().multiply(new BigDecimal(dias));

        if (horasRestantes > 12) {
            valorTotal = valorTotal.add(tarifa.getValorDiaria());
        } else if (horasRestantes > 0) {
            BigDecimal valorHorasRestantes = tarifa.getValorPrimeiraHora();
            if (horasRestantes > 1) {
                valorHorasRestantes = valorHorasRestantes.add(
                        tarifa.getValorHoraAdicional().multiply(new BigDecimal(horasRestantes - 1)));
            }
            if (valorHorasRestantes.compareTo(tarifa.getValorDiaria()) > 0) {
                valorHorasRestantes = tarifa.getValorDiaria();
            }
            valorTotal = valorTotal.add(valorHorasRestantes);
        }

        return valorTotal;
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
package br.edu.infnet.dannyelapi.service;

import br.edu.infnet.dannyelapi.model.Cliente;
import br.edu.infnet.dannyelapi.model.Veiculo;
import br.edu.infnet.dannyelapi.repository.ClienteRepository;
import br.edu.infnet.dannyelapi.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public VeiculoService(VeiculoRepository veiculoRepository, ClienteRepository clienteRepository) {
        this.veiculoRepository = veiculoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Veiculo> findAll() {
        return veiculoRepository.findAll();
    }

    public Veiculo findById(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Veículo não encontrado com ID: " + id));
    }

    public Veiculo save(Veiculo veiculo, Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Cliente não encontrado com ID: " + clienteId + " para associar ao veículo."));

        veiculo.setCliente(cliente);
        return veiculoRepository.save(veiculo);
    }

    public void deleteById(Long id) {
        if (!veiculoRepository.existsById(id)) {
            throw new NoSuchElementException("Veículo não encontrado com ID: " + id);
        }
        veiculoRepository.deleteById(id);
    }

    public List<Veiculo> findByModelo(String modelo) {
        return veiculoRepository.findByModeloContainingIgnoreCase(modelo);
    }
}
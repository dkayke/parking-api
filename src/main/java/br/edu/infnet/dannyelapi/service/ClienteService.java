package br.edu.infnet.dannyelapi.service;

import br.edu.infnet.dannyelapi.model.Cliente;
import br.edu.infnet.dannyelapi.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com ID: " + id));
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void deleteById(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new NoSuchElementException("Cliente não encontrado com ID: " + id);
        }
        clienteRepository.deleteById(id);
    }

    public Cliente update(Long id, Cliente clienteAtualizado) {
        Cliente clienteExistente = findById(id);

        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setTelefone(clienteAtualizado.getTelefone());
        clienteExistente.setEmail(clienteAtualizado.getEmail());

        return clienteRepository.save(clienteExistente);
    }
}
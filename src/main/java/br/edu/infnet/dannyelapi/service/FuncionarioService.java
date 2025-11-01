package br.edu.infnet.dannyelapi.service;

import br.edu.infnet.dannyelapi.model.Funcionario;
import br.edu.infnet.dannyelapi.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();
    }

    public Funcionario findById(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Funcionário não encontrado com ID: " + id));
    }

    public Funcionario save(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    public void deleteById(Long id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new NoSuchElementException("Funcionário não encontrado com ID: " + id);
        }
        funcionarioRepository.deleteById(id);
    }
}
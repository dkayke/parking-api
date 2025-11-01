package br.edu.infnet.dannyelapi.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.dannyelapi.model.RegistroEstadia;
import br.edu.infnet.dannyelapi.service.RegistroEstadiaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/estadias")
public class RegistroEstadiaController {

    private final RegistroEstadiaService registroEstadiaService;

    @Autowired
    public RegistroEstadiaController(RegistroEstadiaService registroEstadiaService) {
        this.registroEstadiaService = registroEstadiaService;
    }

    @GetMapping
    public ResponseEntity<List<RegistroEstadia>> findAll() {
        return ResponseEntity.ok(registroEstadiaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroEstadia> findById(@PathVariable Long id) {
        RegistroEstadia registro = registroEstadiaService.findById(id);
        return ResponseEntity.ok(registro);
    }

    @GetMapping("/search/date")
    public ResponseEntity<List<RegistroEstadia>> findByEntradaBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {

        List<RegistroEstadia> registros = registroEstadiaService.findByEntradaBetween(inicio, fim);
        return ResponseEntity.ok(registros);
    }

    @PostMapping
    public ResponseEntity<RegistroEstadia> save(
            @Valid @RequestBody RegistroEstadia registroEstadia,
            @RequestParam Long veiculoId) {

        if (registroEstadia.getEntrada() == null) {
            registroEstadia.setEntrada(LocalDateTime.now());
        }

        RegistroEstadia novoRegistro = registroEstadiaService.save(registroEstadia, veiculoId);
        URI location = URI.create(String.format("/estadias/%s", novoRegistro.getId()));
        return ResponseEntity.created(location).body(novoRegistro);
    }

    @PutMapping("/{id}/saida")
    public ResponseEntity<RegistroEstadia> registrarSaida(@PathVariable Long id, @RequestParam Double valor) {
        RegistroEstadia registroAtualizado = registroEstadiaService.registrarSaida(id, valor);
        return ResponseEntity.ok(registroAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        registroEstadiaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
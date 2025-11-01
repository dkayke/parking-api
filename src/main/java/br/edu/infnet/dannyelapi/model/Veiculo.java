package br.edu.infnet.dannyelapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_veiculo")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A placa é obrigatória.")
    @Pattern(regexp = "^[A-Z]{3}-\\d{4}$", message = "Placa inválida (formato ABC-1234)")
    @Column(unique = true, nullable = false, length = 8)
    private String placa;

    @NotBlank(message = "O modelo é obrigatório.")
    private String modelo;

    @NotBlank(message = "A cor é obrigatória.")
    private String cor;

    @NotBlank(message = "A vaga é obrigatória.")
    private String vaga;

    private boolean estacionado = false;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<RegistroEstadia> estadias = new ArrayList<>();

    public Veiculo(String placa, String modelo, String cor, String vaga, Cliente cliente) {
        this.placa = placa;
        this.modelo = modelo;
        this.cor = cor;
        this.vaga = vaga;
        this.cliente = cliente;
    }
}
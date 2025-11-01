package br.edu.infnet.dannyelapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_registro_estadia")
public class RegistroEstadia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data de entrada é obrigatória.")
    private LocalDateTime entrada;

    private LocalDateTime saida;

    @Min(value = 0, message = "O valor deve ser positivo.")
    private Double valor;
 
    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    @NotNull(message = "A estadia deve estar associada a um veículo.")
    private Veiculo veiculo;

    public RegistroEstadia(LocalDateTime entrada, Veiculo veiculo) {
        this.entrada = entrada;
        this.veiculo = veiculo;
    }
}
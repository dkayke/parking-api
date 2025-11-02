package br.edu.infnet.dannyelapi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(precision = 10, scale = 2)
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    @NotNull(message = "A estadia deve estar associada a um veículo.")
    private Veiculo veiculo;

    public RegistroEstadia(LocalDateTime entrada, Veiculo veiculo) {
        this.entrada = entrada;
        this.veiculo = veiculo;
    }
}
package br.edu.infnet.dannyelapi.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_tarifa", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "tipoVeiculo" })
})
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private TipoVeiculo tipoVeiculo;

    @NotNull(message = "O valor da primeira hora é obrigatório.")
    @Min(0)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPrimeiraHora;

    @NotNull(message = "O valor da hora adicional é obrigatório.")
    @Min(0)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorHoraAdicional;

    @NotNull(message = "O valor da diária (12h+) é obrigatório.")
    @Min(0)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorDiaria;

    @Min(0)
    @Column(nullable = false)
    private int toleranciaMinutos;

    public Tarifa(TipoVeiculo tipoVeiculo, BigDecimal valorPrimeiraHora, BigDecimal valorHoraAdicional,
            BigDecimal valorDiaria, int toleranciaMinutos) {
        this.tipoVeiculo = tipoVeiculo;
        this.valorPrimeiraHora = valorPrimeiraHora;
        this.valorHoraAdicional = valorHoraAdicional;
        this.valorDiaria = valorDiaria;
        this.toleranciaMinutos = toleranciaMinutos;
    }
}
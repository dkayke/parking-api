package br.edu.infnet.dannyelapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_funcionario")
public class Funcionario extends Pessoa {

    @NotBlank(message = "O cargo é obrigatório.")
    private String cargo;

    @Min(value = 0, message = "O salário não pode ser negativo.")
    @Column(nullable = false)
    private double salario;

    public Funcionario(String nome, String cpf, String telefone, String cargo, double salario) {
        super(nome, cpf, telefone);
        this.cargo = cargo;
        this.salario = salario;
    }
}
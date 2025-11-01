package br.edu.infnet.dannyelapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_cliente")
public class Cliente extends Pessoa {

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "Formato de email inválido.")
    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Veiculo> veiculos = new ArrayList<>();

    public Cliente(String nome, String cpf, String telefone, String email) {
        super(nome, cpf, telefone);
        this.email = email;
    }
}
package br.edu.infnet.dannyelapi.loader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.edu.infnet.dannyelapi.model.Cliente;
import br.edu.infnet.dannyelapi.model.RegistroEstadia;
import br.edu.infnet.dannyelapi.model.Tarifa;
import br.edu.infnet.dannyelapi.model.TipoVeiculo;
import br.edu.infnet.dannyelapi.model.Veiculo;
import br.edu.infnet.dannyelapi.repository.ClienteRepository;
import br.edu.infnet.dannyelapi.repository.RegistroEstadiaRepository;
import br.edu.infnet.dannyelapi.repository.TarifaRepository;
import br.edu.infnet.dannyelapi.repository.VeiculoRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private RegistroEstadiaRepository registroEstadiaRepository;

    @Autowired
    private TarifaRepository tarifaRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("--- Iniciando carregamento de dados ---");

        loadTarifas(); // Tarifas devem vir primeiro
        loadClientes();
        loadVeiculos(); // Veículos dependem de Clientes
        loadRegistros(); // Registros dependem de Veículos

        System.out.println("--- Carregamento de dados concluído ---");
    }

    private void loadTarifas() throws Exception {
        System.out.println("Carregando tarifas...");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource("tarifas.txt").getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(";");
                if (fields.length == 5) {
                    TipoVeiculo tipo = TipoVeiculo.valueOf(fields[0]);

                    if (tarifaRepository.findByTipoVeiculo(tipo).isEmpty()) {
                        Tarifa tarifa = new Tarifa(
                                tipo,
                                new BigDecimal(fields[1]), // valorPrimeiraHora
                                new BigDecimal(fields[2]), // valorHoraAdicional
                                new BigDecimal(fields[3]), // valorDiaria
                                Integer.parseInt(fields[4]) // toleranciaMinutos
                        );
                        tarifaRepository.save(tarifa);
                    }
                }
            }
        }
    }

    private void loadClientes() throws Exception {
        System.out.println("Carregando clientes...");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource("clientes.txt").getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(";");
                if (fields.length == 4) {
                    if (clienteRepository.findByCpf(fields[1]).isEmpty()) {
                        Cliente cliente = new Cliente(fields[0], fields[1], fields[2], fields[3]);
                        clienteRepository.save(cliente);
                    }
                }
            }
        }
    }

    private void loadVeiculos() throws Exception {
        System.out.println("Carregando veículos...");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource("veiculos.txt").getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(";");
                if (fields.length == 6) {
                    if (veiculoRepository.findByPlaca(fields[0]).isEmpty()) {

                        Cliente cliente = clienteRepository.findByCpf(fields[4])
                                .orElseThrow(() -> new RuntimeException(
                                        "Falha no loader: Cliente com CPF " + fields[4] + " não encontrado."));

                        TipoVeiculo tipoVeiculo = TipoVeiculo.valueOf(fields[5]);

                        Veiculo veiculo = new Veiculo(
                                fields[0], // placa
                                fields[1], // modelo
                                fields[2], // cor
                                fields[3], // vaga
                                tipoVeiculo, // tipoVeiculo
                                cliente // cliente
                        );
                        veiculoRepository.save(veiculo);
                    }
                }
            }
        }
    }

    private void loadRegistros() throws Exception {
        System.out.println("Carregando registros de estadia...");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource("registros.txt").getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(";");
                if (fields.length == 2) {
                    Veiculo veiculo = veiculoRepository.findByPlaca(fields[0])
                            .orElseThrow(() -> new RuntimeException(
                                    "Falha no loader: Veículo com placa " + fields[0] + " não encontrado."));

                    LocalDateTime entrada = LocalDateTime.parse(fields[1]);

                    RegistroEstadia registro = new RegistroEstadia(entrada, veiculo);

                    veiculo.setEstacionado(true);
                    veiculoRepository.save(veiculo);

                    registroEstadiaRepository.save(registro);
                }
            }
        }
    }
}
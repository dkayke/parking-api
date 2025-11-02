# 🚗 Parking API

API RESTful para **gerenciamento de estacionamentos**, desenvolvida em **Java 21** com **Spring Boot** e **banco de dados H2**.  
O sistema permite o **cadastro e controle de veículos, vagas e movimentações de entrada e saída**, oferecendo uma base sólida para integrações com aplicações web e mobile.
<br><br>
 
## 🧠 Visão Geral

O objetivo da Parking API é automatizar e organizar o controle operacional de estacionamentos, garantindo **agilidade, padronização e confiabilidade dos dados**.  
A aplicação segue boas práticas de arquitetura em camadas (Controller, Service, Repository) e princípios REST.
<br><br>

## 🧪 Testando com Postman

Você pode testar todos os endpoints da **Parking API** facilmente utilizando o **Postman**.


1. Baixe o arquivo da coleção Postman: 👉 [ParkingAPI postman_collection.json](./ParkingAPI.postman_collection.json)

2. No Postman, clique em **Import > Upload Files** e selecione o arquivo.  
3. A coleção será carregada com as requisições prontas para uso.

## ⚙️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3**
  - Spring Web
  - Spring Data JPA
- **Banco de Dados H2**
- **Maven**
 <br><br>

## 🚀 Features

Feature 1 (Configuração Essencial): Criação do projeto Spring Boot (Java 21), configuração do H2 e estrutura de pacotes (model, controller, service, etc.).

Feature 2 (Herança e Associação): Implementação da herança (Pessoa -> Cliente/Funcionario) e o relacionamento 1:N entre Cliente e Veiculo.

Feature 3 (Persistência JPA): Migração de todas as entidades para o banco de dados com @Entity e criação dos Repositories (JPA), abandonando a lógica em memória.

Feature 4 (Validação e Estadia): Adição de validações (@Pattern), tratamento global de exceções (@ControllerAdvice) e criação da entidade RegistroEstadia.

Feature 5 (DataLoader): Implementação de um CommandLineRunner para ler arquivos .txt (clientes.txt, veiculos.txt) e popular o banco de dados na inicialização.

Feature 6 (Tarifário Dinâmico): Refatoração do sistema para que o backend calcule o preço da estadia (via PUT /estadias/{id}/saida) com base em uma nova tabela de Tarifa (configurável por TipoVeiculo), em vez de receber o valor pela API.

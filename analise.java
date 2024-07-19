/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: analise.java
Data: 29.05.2024
--------------------------------------*/

/*
1. Objectivo
Este projecto tem o objectivo de registar a compra e cancelamento de bilhetes de aviação e a gestão de informações sobre passageiros, voos, rotas, aviões, funcionários e localidades (países e estados).

2. ModeloInterface [Interfaces Graficas]
- ApresentacaoModeloInterface
- LoginModeloInterface
- MenuPrincipal
- PassageiroModeloInterface
- VooModeloInterface
- BilheteModeloInterface
- AviaoModeloInterface
- FuncionarioModeloInterface

3. Entidades Fortes e Seus Atributos (Modelo)

- VooModelo
    int id
    String dataPartida
    String dataChegada
    StatusVoo status // (Indisponivel, Disponivel)
    AviaoModelo aviao [Fk]
    RotaModelo rota [Fk]

-PassageiroModelo
    int id
    String nomePassageiro
    char genero
    date dataDeNascimento
    String tipodeDocumento
    String numDoDocumento
    String telefone
    String Email

- BilheteModelo
    int id
    PassageiroModelo passageiro [Fk]
    VooModelo voo [FK]
    String classe
    double preco
    String dataCompra 
    StatusBilhete status // (ATIVO, INATIVO, CANCELADO)

- AviaoModelo
    int id
    int numDeAcentos
    String modelo
    String companhiaAerea
    PassageiroModelo 

- RotaModelo
    int id
    String destino
    String origem

- FuncionarioModelo
    int id  
    String nome
    String senha
    String cargo
    String numTelefone  
    String genero
    String tipoDeDocumento

4. Ficheiro | persistencia de dados
- Passageiro.dat
- Voo.dat
- Bilhete.dat
- Rota.dat
- Aviao.dat
- Funcionario.dat

5. Tabelas de Apoio (Auxiliares) = Entidades Fracas
- TipoDocumentos.tab

6. Diversos
6.1 - Implementação: Java Swing
6.2 - IDE: Notepad++
*/



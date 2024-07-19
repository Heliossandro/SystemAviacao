/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: BilheteModelo.java
Data: 23.06.2024
--------------------------------------*/

import java.io.Serializable;

public class BilheteModelo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private PassageiroModelo passageiro;
    private VooModelo voo;
    private StringBufferModelo classe; // Usando StringBufferModelo para classe
    private double preco;
    private StringBufferModelo dataCompra; // Usando StringBufferModelo para data
    private StatusBilhete status; // Usando StringBufferModelo para status

    public enum StatusBilhete {
        ATIVO, INATIVO, CANCELADO
    }

    public BilheteModelo(int id, PassageiroModelo passageiro, VooModelo voo, String classe, double preco, String dataCompra, StatusBilhete status) {
        this.id = id;
        this.passageiro = passageiro;
        this.voo = voo;
        this.classe = new StringBufferModelo(classe);
        this.preco = preco;
        this.dataCompra = new StringBufferModelo(dataCompra);
        this.status = status;
    }

    // Getters
    public int getId() {
        return id;
    }

    public PassageiroModelo getPassageiro() {
        return passageiro;
    }

    public VooModelo getVoo() {
        return voo;
    }

    public String getClasse() {
        return classe.get();
    }

    public double getPreco() {
        return preco;
    }

    public String getDataCompra() {
        return dataCompra.get();
    }

    public StatusBilhete getStatus() {
        return status;
    }

    // Métodos adicionais para obter nome do passageiro e número do voo
    public String getNomePassageiro() {
        return passageiro != null ? passageiro.getNomePassageiro() : "Desconhecido";
    }

    public int getNumeroVoo() {
        return voo != null ? voo.getId() : -1;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setPassageiro(PassageiroModelo passageiro) {
        this.passageiro = passageiro;
    }

    public void setVoo(VooModelo voo) {
        this.voo = voo;
    }

    public void setClasse(String classe) {
        this.classe.set(classe);
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setDataCompra(String dataCompra) {
        this.dataCompra.set(dataCompra);
    }

    public void setStatus(StatusBilhete status) {
        this.status = status;
    }

    // Métodos adicionais para definir nome do passageiro e número do voo
    public void setNomePassageiro(String nomePassageiro) {
        if (this.passageiro != null) {
            this.passageiro.setNomePassageiro(nomePassageiro);
        }
    }

    public void setNumeroVoo(int numeroVoo) {
        if (this.voo != null) {
            this.voo.setId(numeroVoo);
        }
    }

    @Override
    public String toString() {
        return "BilheteModelo{\n" +
                "\n id=" + id +
                ",\n passageiro=" + passageiro +
                ",\n voo=" + voo +
                ",\n classe='" + classe + '\'' +
                ",\n preco=" + preco +
                ",\n dataCompra='" + dataCompra + '\'' +
                ",\n status=" + status +
                '}';
    }
}



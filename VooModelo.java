/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: VooModelo.java
Data: 17.06.2024
--------------------------------------*/

import java.io.Serializable;

public class VooModelo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private StringBufferModelo dataPartida;
    private StringBufferModelo horaPartida;
    private StringBufferModelo dataChegada;
    private StringBufferModelo horaChegada;
    private StatusVoo status;
    private AviaoModelo aviao;
    private RotaModelo rota;
    private double preco; 

    public enum StatusVoo {
        INDISPONIVEL, DISPONIVEL
    }

    public VooModelo(int id, String dataPartida, String horaPartida, String dataChegada, String horaChegada, StatusVoo status, AviaoModelo aviao, RotaModelo rota, double preco) {
        this.id = id;
        this.dataPartida = new StringBufferModelo(dataPartida);
        this.horaPartida = new StringBufferModelo(horaPartida);
        this.dataChegada = new StringBufferModelo(dataChegada);
        this.horaChegada = new StringBufferModelo(horaChegada);
        this.status = status;
        this.aviao = aviao;
        this.rota = rota;
        this.preco = preco;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDataPartida() {
        return dataPartida.get();
    }

    public String getHoraPartida() {
        return horaPartida.get();
    }

    public String getDataChegada() {
        return dataChegada.get();
    }

    public String getHoraChegada() {
        return horaChegada.get();
    }

    public StatusVoo getStatus() {
        return status;
    }

    public AviaoModelo getAviao() {
        return aviao;
    }

    public RotaModelo getRota() {
        return rota;
    }

    public double getPreco() {
        return preco;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDataPartida(String dataPartida) {
        this.dataPartida.set(dataPartida);
    }

    public void setHoraPartida(String horaPartida) {
        this.horaPartida.set(horaPartida);
    }

    public void setDataChegada(String dataChegada) {
        this.dataChegada.set(dataChegada);
    }

    public void setHoraChegada(String horaChegada) {
        this.horaChegada.set(horaChegada);
    }

    public void setStatus(StatusVoo status) {
        this.status = status;
    }

    public void setAviao(AviaoModelo aviao) {
        this.aviao = aviao;
    }

    public void setRota(RotaModelo rota) {
        this.rota = rota;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "VooModelo{" +
                "id=" + id +
                ", dataPartida='" + dataPartida + '\'' +
                ", horaPartida='" + horaPartida + '\'' +
                ", dataChegada='" + dataChegada + '\'' +
                ", horaChegada='" + horaChegada + '\'' +
                ", status=" + status +
                ", aviao=" + aviao +
                ", rota=" + rota +
                ", preco=" + preco + // Inclui o campo preco no toString
                '}';
    }
}

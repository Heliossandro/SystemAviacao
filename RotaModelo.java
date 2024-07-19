/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: RotaModelo.java
Data: 08.06.2024
--------------------------------------*/

import java.io.Serializable;

public class RotaModelo implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private StringBufferModelo partida;
    private StringBufferModelo destino;

    public RotaModelo(int id, String partida, String destino) {
        this.id = id;
        this.partida = new StringBufferModelo(partida);
        this.destino = new StringBufferModelo(destino);
    }

    public int getId() {
        return id;
    }

    public String getPartida() {
        return partida.get();
    }

    public void setPartida(String partida) {
        this.partida.set(partida);
    }

    public String getDestino() {
        return destino.get();
    }

    public void setDestino(String destino) {
        this.destino.set(destino);
    }

    @Override
    public String toString() {
        return partida.get() + " - " + destino.get();
    }
}
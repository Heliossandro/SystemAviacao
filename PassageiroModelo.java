/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: PassageiroModelo.java
Data: 17.06.2024
--------------------------------------*/

import java.io.Serializable;

public class PassageiroModelo implements Serializable {

    private int id;
    private StringBufferModelo nomePassageiro;
    private StringBufferModelo genero;
    private StringBufferModelo dataDeNascimento;
    private StringBufferModelo tipodeDocumento;
    private StringBufferModelo numDoDocumento;
    private StringBufferModelo telefone;
    private StringBufferModelo email;

    public PassageiroModelo(int id, String nomePassageiro, String genero, String dataDeNascimento, String tipodeDocumento, String numDoDocumento, String telefone, String email) {
        this.id = id;
        this.nomePassageiro = new StringBufferModelo(nomePassageiro);
        this.genero = new StringBufferModelo(genero);
        this.dataDeNascimento = new StringBufferModelo(dataDeNascimento);
        this.tipodeDocumento = new StringBufferModelo(tipodeDocumento);
        this.numDoDocumento = new StringBufferModelo(numDoDocumento);
        this.telefone = new StringBufferModelo(telefone);
        this.email = new StringBufferModelo(email);
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNomePassageiro() {
        return nomePassageiro.get();
    }

    public String getGenero() {
        return genero.get();
    }

    public String getDataDeNascimento() {
        return dataDeNascimento.get();
    }

    public String getTipodeDocumento() {
        return tipodeDocumento.get();
    }

    public String getNumDoDocumento() {
        return numDoDocumento.get();
    }

    public String getTelefone() {
        return telefone.get();
    }

    public String getEmail() {
        return email.get();
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNomePassageiro(String nomePassageiro) {
        this.nomePassageiro.set(nomePassageiro);
    }

    public void setGenero(String genero) {
        this.genero.set(genero);
    }

    public void setDataDeNascimento(String dataDeNascimento) {
        this.dataDeNascimento.set(dataDeNascimento);
    }

    public void setTipodeDocumento(String tipodeDocumento) {
        this.tipodeDocumento.set(tipodeDocumento);
    }

    public void setNumDoDocumento(String numDoDocumento) {
        this.numDoDocumento.set(numDoDocumento);
    }

    public void setTelefone(String telefone) {
        this.telefone.set(telefone);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    @Override
    public String toString() {
        return "PassageiroModelo{" +
                "id=" + id +
                ", nomePassageiro='" + nomePassageiro + '\'' +
                ", genero='" + genero + '\'' +
                ", dataDeNascimento='" + dataDeNascimento + '\'' +
                ", tipodeDocumento='" + tipodeDocumento + '\'' +
                ", numDoDocumento='" + numDoDocumento + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

    
/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: FuncionarioModelo.java
Data: 09.06.2024
--------------------------------------*/
import java.io.Serializable;

class FuncionarioModelo implements Serializable {
	
    private int id;
    private String nome;
    private String senha;
    private String numDocumento;
    private String cargo;
    private String tipoDeDocumento;
    private char genero;
    private String nacionalidade;

    public FuncionarioModelo(int id, String nome, String senha, String numDocumento, String cargo, String tipoDeDocumento, char genero, String nacionalidade) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.numDocumento = numDocumento;
        this.cargo = cargo;
        this.tipoDeDocumento = tipoDeDocumento;
        this.genero = genero;
        this.nacionalidade = nacionalidade;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public String getCargo() {
        return cargo;
    }

    public String getTipoDeDocumento() {
        return tipoDeDocumento;
    }

    public char getGenero() {
        return genero;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setTipoDeDocumento(String tipoDeDocumento) {
        this.tipoDeDocumento = tipoDeDocumento;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    @Override
    public String toString() {
        return "FuncionarioModelo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                ", numDocumento='" + numDocumento + '\'' +
                ", cargo='" + cargo + '\'' +
                ", tipoDeDocumento='" + tipoDeDocumento + '\'' +
                ", genero=" + genero +
                ", nacionalidade='" + nacionalidade + '\'' +
                '}';
    }
}
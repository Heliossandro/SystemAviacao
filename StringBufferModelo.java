/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: StringBufferModelo.java
Data: 08.06.2024
--------------------------------------*/

import java.io.Serializable;

public class StringBufferModelo implements Serializable {
    private static final long serialVersionUID = 1L;
    private StringBuffer buffer;

    public StringBufferModelo() {
        this.buffer = new StringBuffer();
    }

    public StringBufferModelo(String str) {
        this.buffer = new StringBuffer(str);
    }

    public void append(String str) {
        buffer.append(str);
    }

    public void set(String str) {
        buffer.setLength(0);
        buffer.append(str);
    }

    public String get() {
        return buffer.toString();
    }

    @Override
    public String toString() {
        return buffer.toString();
    }
}

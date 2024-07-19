/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: PassageiroFile.java
Data: 19.06.2024
--------------------------------------*/
import javax.swing.*;
import SwingComponents.*;
import Calendario.*;
import java.io.*;

public class PassageiroFile extends ObjectsFile {

    public PassageiroFile() {
        super("Passageiro.dat", new PassageiroModelo));
    }

    public void salvarDados(PassageiroModelo modelo) {
        try {
            // colocar o file pointer no final do ficheiro
            stream.seek(stream.length());

            // escrever os dados no ficheiro
            modelo.write(stream);

            incrementarProximoCodigo();

            JOptionPane.showMessageDialog(null, "Dados Salvos com Sucesso!");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Falha ao Salvar um Novo Passageiro");
        }
    }

    public static void listarPassageiros() {
        PassageiroFile ficheiro = new PassageiroFile();
        PassageiroModelo modelo = new PassageiroModelo();
        String output = "Listagem de Dados do Ficheiro\n\n";

        try {
            ficheiro.stream.seek(4);

            for (int i = 0; i < ficheiro.getNregistos(); ++i) {
                modelo.read(ficheiro.stream);

                output += "---------------------------------\n";
                output += modelo.toString() + "\n";
            }

            JTextArea area = new JTextArea(40, 60);
            area.setText(output);
            area.setFocusable(false);
            JOptionPane.showMessageDialog(null, new JScrollPane(area),
                    "Gestão de Passageiros", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static StringVector getAllNames() {
        PassageiroFile ficheiro = new PassageiroFile();
        PassageiroModelo modelo = new PassageiroModelo();
        StringVector vector = new StringVector();

        try {
            ficheiro.stream.seek(4);

            for (int i = 0; i < ficheiro.getNregistos(); ++i) {
                modelo.read(ficheiro.stream);

                vector.add(modelo.getNomePassageiro());
            }

            vector.sort();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return vector;
    }

    public static void pesquisarPassageiroPorNome(String nomeProcurado) {
        PassageiroFile ficheiro = new PassageiroFile();
        PassageiroModelo modelo = new PassageiroModelo();

        try {
            ficheiro.stream.seek(4);

            for (int i = 0; i < ficheiro.getNregistos(); ++i) {
                modelo.read(ficheiro.stream);

                if (modelo.getNomePassageiro().equalsIgnoreCase(nomeProcurado)) {
                    JOptionPane.showMessageDialog(null, modelo.toString(),
                            "Gestão de Passageiros", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

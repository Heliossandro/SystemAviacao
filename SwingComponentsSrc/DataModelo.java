package SwingComponents;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class DataModelo extends ObjectSerializableGeneric implements Serializable {
    private static final long serialVersionUID = 1L;

    private int dia, mes, ano;
    private static final int DIA = 0, MES = DIA + 1, ANO = MES + 1;
    
    public static final String[] meses = {
        "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    };
    
    public DataModelo() {
        this.dia = 1;
        this.mes = 1;
        this.ano = 2005;
    }
    
    public DataModelo(String data) {
        StringTokenizer st = new StringTokenizer(data, "/-");
        for (int i = 0; st.hasMoreTokens() && i < 3; i++) {
            try {
                switch (i) {
                    case DIA:
                        dia = Integer.parseInt(st.nextToken());
                        break;
                    case MES:
                        geraMes(st.nextToken());
                        break;
                    case ANO:
                        ano = Integer.parseInt(st.nextToken());
                        break;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Formato de data inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void geraMes(String mes) {
        for (int i = 0; i < meses.length; i++) {
            if (mes.equalsIgnoreCase(meses[i].substring(0, 3))) {
                this.mes = i + 1;
                return;
            }
        }
        throw new IllegalArgumentException("Mês inválido: " + mes);
    }
    
    public int getDia() {
        return dia;
    }
    
    public int getMes() {
        return mes;
    }
    
    public int getAno() {
        return ano;
    }
    
    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", dia, mes, ano);
    }
    
    public void write(RandomAccessFile stream) {
        try {
            stream.writeInt(dia);
            stream.writeInt(mes);
            stream.writeInt(ano);
        } catch (IOException ex) {
            String msg = "Falha na escrita da data no ficheiro.";
            JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    
    public void read(RandomAccessFile stream) {
        try {
            dia = stream.readInt();
            mes = stream.readInt();
            ano = stream.readInt();
        } catch (IOException ex) {
            String msg = "Falha na leitura da data no ficheiro.";
            JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TipoDocumentoArquivoUtil {
    private static final String FILE_NAME = "tipos_documento.tab";
    private static final String[] TIPOS_DOCUMENTO_PADRAO = {"Bilhete", "CPF", "Passaporte"};

    static {
        criarArquivoSeNaoExistir();
    }

    private static void criarArquivoSeNaoExistir() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String tipoDocumento : TIPOS_DOCUMENTO_PADRAO) {
                    writer.write(tipoDocumento);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> getTiposDocumento() {
        List<String> tiposDocumento = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                tiposDocumento.add(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tiposDocumento;
    }

    public void adicionarTipoDocumento(String tipoDocumento) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(tipoDocumento);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

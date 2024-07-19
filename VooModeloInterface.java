/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: VooModeloInterface.java
Data: 23.06.2024
--------------------------------------*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VooModeloInterface extends JFrame {
    PainelCenter centro;
    PainelSul sul;
    int ultimoId = 0; // Mantém o controle do último ID atribuído
    List<VooModelo> voos; // Lista para armazenar todos os voos
    List<RotaModelo> listaRotas; // Lista para armazenar todas as rotas
    List<AviaoModelo> listaAvioes; // Lista para armazenar todos os aviões

    public VooModeloInterface() {
        setTitle("Gestão de Voos");
        setSize(800, 500);
        setLocationRelativeTo(null);

        JPanel painelNorte = new JPanel();

        getContentPane().add(painelNorte, BorderLayout.NORTH);
        getContentPane().add(centro = new PainelCenter(), BorderLayout.CENTER);
        getContentPane().add(sul = new PainelSul(), BorderLayout.SOUTH);

        // Inicializa as listas de voos, rotas e aviões
        voos = carregarVoosDeArquivo(); // Atualizado para carregar voos do arquivo ao iniciar
        listaRotas = new ArrayList<>();
        listaAvioes = new ArrayList<>();

        // Atualiza ultimoId com o maior ID existente nos voos carregados
        atualizarUltimoId();

        // Carrega os dados de rotas e aviões dos arquivos
        centro.carregarRotas();
        centro.carregarAvioes();

        setVisible(true);
    }

    private void atualizarUltimoId() {
        for (VooModelo voo : voos) {
            if (voo.getId() > ultimoId) {
                ultimoId = voo.getId();
            }
        }
    }

    class PainelCenter extends JPanel {
        JTextField txtDataPartida, txtHoraPartida, txtDataChegada, txtHoraChegada, txtPreco;
        JComboBox<String> cbStatus;
        JComboBox<AviaoModelo> cbAviao;
        JComboBox<String> cbRota; // Alterado para JComboBox<String> para armazenar String no combo box

        public PainelCenter() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre componentes
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // linha 1
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            add(new JLabel("Data de Partida:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            txtDataPartida = new JTextField(10);
            add(txtDataPartida, gbc);

            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            add(new JLabel("Hora de Partida:"), gbc);

            gbc.gridx = 3;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            txtHoraPartida = new JTextField(10);
            add(txtHoraPartida, gbc);

            // linha 2
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            add(new JLabel("Data de Chegada:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            txtDataChegada = new JTextField(10);
            add(txtDataChegada, gbc);

            gbc.gridx = 2;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            add(new JLabel("Hora de Chegada:"), gbc);

            gbc.gridx = 3;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            txtHoraChegada = new JTextField(10);
            add(txtHoraChegada, gbc);

            // linha 3
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            add(new JLabel("Status:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            cbStatus = new JComboBox<>(new String[]{"INDISPONIVEL", "DISPONIVEL"});
            add(cbStatus, gbc);

            // linha 4
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 1;
            add(new JLabel("Avião:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 3;
            gbc.gridwidth = 3;
            cbAviao = new JComboBox<>();
            add(cbAviao, gbc); // Adicionado sem carregar inicialmente

            // linha 5
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 1;
            add(new JLabel("Rota:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 4;
            gbc.gridwidth = 3;
            cbRota = new JComboBox<>();
            add(cbRota, gbc); // Adicionado sem carregar inicialmente

            // linha 6 - novo campo para preço
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth = 1;
            add(new JLabel("Preço:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 5;
            gbc.gridwidth = 1;
            txtPreco = new JTextField(10);
            add(txtPreco, gbc);
        }

        public void carregarAvioes() {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("avioes.dat"))) {
                ArrayList<AviaoModelo> avioes = (ArrayList<AviaoModelo>) ois.readObject();
                for (AviaoModelo aviao : avioes) {
                    cbAviao.addItem(aviao);
                }
                listaAvioes = avioes; // Atualiza a lista de aviões
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        public void carregarRotas() {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("rotas.dat"))) {
                ArrayList<RotaModelo> rotas = (ArrayList<RotaModelo>) ois.readObject();
                for (RotaModelo rota : rotas) {
                    cbRota.addItem(rota.getPartida() + " -> " + rota.getDestino());
                }
                listaRotas = rotas; // Atualiza a lista de rotas
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        public void salvar() {
            ultimoId++;
            int id = ultimoId;
            String dataPartida = txtDataPartida.getText();
            String horaPartida = txtHoraPartida.getText();
            String dataChegada = txtDataChegada.getText();
            String horaChegada = txtHoraChegada.getText();
            VooModelo.StatusVoo status = VooModelo.StatusVoo.valueOf((String) cbStatus.getSelectedItem());
            AviaoModelo aviao = (AviaoModelo) cbAviao.getSelectedItem();
            String rotaString = (String) cbRota.getSelectedItem();
            double preco = Double.parseDouble(txtPreco.getText()); // Obtém o preço do campo de texto

            // Encontra a rota correspondente na lista de rotas
            RotaModelo rota = encontrarRotaPorDescricao(rotaString);

            // Cria o objeto VooModelo
            VooModelo voo = new VooModelo(id, dataPartida, horaPartida, dataChegada, horaChegada, status, aviao, rota, preco);
            // Adiciona o novo voo à lista em memória
            voos.add(voo);

            // Salva a lista de voos no arquivo .dat
            salvarVoosEmArquivo();

            System.out.println(voo);
            JOptionPane.showMessageDialog(this, "Voo salvo com sucesso!");
            limparCampos(); // Limpa os campos após salvar
        }

        private RotaModelo encontrarRotaPorDescricao(String descricao) {
            // Percorre a lista de rotas para encontrar a rota com a descrição correspondente
            for (RotaModelo rota : listaRotas) {
                if ((rota.getPartida() + " -> " + rota.getDestino()).equals(descricao)) {
                    return rota;
                }
            }
            return null; // Retorna null se não encontrar a rota correspondente (tratar isso conforme necessidade do seu projeto)
        }

        private void limparCampos() {
            txtDataPartida.setText("");
            txtHoraPartida.setText("");
            txtDataChegada.setText("");
            txtHoraChegada.setText("");
            txtPreco.setText(""); // Limpa o campo de preço
            cbStatus.setSelectedIndex(0);
            // Não é necessário limpar os JComboBox de avião e rota, eles serão carregados novamente
        }
    }

    class PainelSul extends JPanel implements ActionListener {
        JButton salvarJB, cancelarJB;

        public PainelSul() {
            add(salvarJB = new JButton("Salvar"));
            add(cancelarJB = new JButton("Cancelar"));

            salvarJB.addActionListener(this);
            cancelarJB.addActionListener(this);
        }

        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() == salvarJB)
                centro.salvar();
            else
                dispose();
        }
    }

    private List<VooModelo> carregarVoosDeArquivo() {
        List<VooModelo> voos = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("voos.dat"))) {
            voos = (List<VooModelo>) ois.readObject();
        } catch (EOFException e) {
            // Chegou ao final do arquivo, não há mais objetos para ler
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return voos;
    }

    private void salvarVoosEmArquivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("voos.dat"))) {
            oos.writeObject(voos);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar os voos no arquivo", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Define o look and feel
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            new VooModeloInterface();
        });
    }
}

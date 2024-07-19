import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BilheteModeloInterface extends JFrame {

    private PainelCenter centro;
    private PainelSul sul;
    private int ultimoId = 0; // Mantém o controle do último ID atribuído
    private List<BilheteModelo> bilhetes; // Lista para armazenar todos os bilhetes
    private List<VooModelo> voos; // Lista para armazenar todos os voos
    private List<PassageiroModelo> passageiros; // Lista para armazenar todos os passageiros

    public BilheteModeloInterface() {
        setTitle("Gestão de Bilhetes");
        setSize(1300, 700);
        setLocationRelativeTo(null);

        // Inicializa as listas de bilhetes, voos e passageiros
        bilhetes = carregarBilhetesDeArquivo(); // Carrega os bilhetes do arquivo ao iniciar
        voos = new ArrayList<>();
        passageiros = new ArrayList<>();
        atualizarUltimoId();

        // Usa JTabbedPane para adicionar abas
        JTabbedPane tabbedPane = new JTabbedPane();

        centro = new PainelCenter();
        getContentPane().add(centro, BorderLayout.CENTER);
        getContentPane().add(sul = new PainelSul(), BorderLayout.SOUTH);

        // Carrega os dados de voos e passageiros dos arquivos
        centro.carregarVoos();
        centro.carregarPassageiros();

        setVisible(true);
    }

    private void atualizarUltimoId() {
        for (BilheteModelo bilhete : bilhetes) {
            if (bilhete.getId() > ultimoId) {
                ultimoId = bilhete.getId();
            }
        }
    }

    class PainelCenter extends JPanel {
        JComboBox<VooModelo> cbVoo;
        JComboBox<PassageiroModelo> cbPassageiro;
        JComboBox<String> cbClasse;
        JTextField txtPreco;
        JTextField txtTotalVendas;
        JLabel lblDataCompra; // Campo para exibir a data de compra
        DefaultTableModel modeloTabela;
        JTable tabelaBilhetes;
        private final String[] colunas = {"ID", "Passageiro", "Numero do Voo", "Classe", "Preço"};

        public PainelCenter() {
            setLayout(new BorderLayout());

            JPanel painelInputs = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre componentes
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // linha 1
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            painelInputs.add(new JLabel("Voo:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.gridwidth = 3;
            cbVoo = new JComboBox<>();
            painelInputs.add(cbVoo, gbc); // Adicionado sem carregar inicialmente

            // linha 2
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            painelInputs.add(new JLabel("Classe:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.gridwidth = 3;
            cbClasse = new JComboBox<>(new String[]{"Econômica", "Executiva", "Primeira Classe"});
            painelInputs.add(cbClasse, gbc);

            // linha 3
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            painelInputs.add(new JLabel("Preço:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 2;
            gbc.gridwidth = 3;
            txtPreco = new JTextField(10);
            painelInputs.add(txtPreco, gbc);

            // linha 4
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 1;
            painelInputs.add(new JLabel("Data de Compra:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 3;
            gbc.gridwidth = 3;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            lblDataCompra = new JLabel(LocalDateTime.now().format(formatter));
            painelInputs.add(lblDataCompra, gbc);

            // linha 5
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 1;
            painelInputs.add(new JLabel("Passageiro:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 4;
            gbc.gridwidth = 3;
            cbPassageiro = new JComboBox<>();
            painelInputs.add(cbPassageiro, gbc);

            add(painelInputs, BorderLayout.NORTH);

            modeloTabela = new DefaultTableModel(colunas, 0);
            tabelaBilhetes = new JTable(modeloTabela);
            JScrollPane scrollPane = new JScrollPane(tabelaBilhetes);
            add(scrollPane, BorderLayout.CENTER);

            JPanel painelTotalVendas = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            painelTotalVendas.add(new JLabel("Total de Vendas:"));
            txtTotalVendas = new JTextField(10);
            txtTotalVendas.setEditable(false);
            painelTotalVendas.add(txtTotalVendas);
            add(painelTotalVendas, BorderLayout.SOUTH);

            carregarBilhetes();
            calcularTotalVendas();
        }

        public void carregarVoos() {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("voos.dat"))) {
                ArrayList<VooModelo> listaVoos = (ArrayList<VooModelo>) ois.readObject();
                for (VooModelo voo : listaVoos) {
                    cbVoo.addItem(voo);
                }
                voos = listaVoos; // Atualiza a lista de voos
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        class PassageiroRenderer extends DefaultListCellRenderer {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof PassageiroModelo) {
                    PassageiroModelo passageiro = (PassageiroModelo) value;
                    setText(passageiro.getNomePassageiro());
                }
                return this;
            }
        }

        public void carregarPassageiros() {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("passageiros.dat"))) {
                ArrayList<PassageiroModelo> listaPassageiros = (ArrayList<PassageiroModelo>) ois.readObject();

                // Limpa o combo box antes de adicionar os novos itens
                cbPassageiro.removeAllItems();

                // Configura o renderer personalizado para exibir apenas o nome do passageiro
                cbPassageiro.setRenderer(new PassageiroRenderer());

                for (PassageiroModelo passageiro : listaPassageiros) {
                    cbPassageiro.addItem(passageiro);
                }
                passageiros = listaPassageiros; // Atualiza a lista de passageiros
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        public void salvar() {
            if (bilhetes == null) {
                bilhetes = new ArrayList<>(); // Inicializa bilhetes se ainda não estiver inicializado
            }

            ultimoId++; // Incrementa o último ID antes de atribuir ao novo bilhete
            int id = ultimoId;
            VooModelo voo = (VooModelo) cbVoo.getSelectedItem();
            PassageiroModelo passageiro = (PassageiroModelo) cbPassageiro.getSelectedItem();
            String classe = (String) cbClasse.getSelectedItem();
            double preco = Double.parseDouble(txtPreco.getText());
            String dataCompraStr = lblDataCompra.getText(); // Usa a data e hora atuais

            BilheteModelo bilhete = new BilheteModelo(id, passageiro, voo, classe, preco, dataCompraStr, BilheteModelo.StatusBilhete.ATIVO);
            bilhetes.add(bilhete);

            Object[] linha = {
                bilhete.getId(),
                bilhete.getPassageiro().getNomePassageiro(),
                bilhete.getVoo().getId(),
                bilhete.getClasse(),
                bilhete.getPreco()
            };
            modeloTabela.addRow(linha);

            salvarBilhetesEmArquivo(); // Salva os bilhetes após adicionar um novo
            atualizarUltimoId(); // Atualiza o último ID após adicionar um novo bilhete
            calcularTotalVendas();

            JOptionPane.showMessageDialog(this, "Bilhete salvo com sucesso!");
            limparCampos();
        }

        private void limparCampos() {
            cbVoo.setSelectedIndex(0);
            cbClasse.setSelectedIndex(0);
            txtPreco.setText("");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            lblDataCompra.setText(LocalDateTime.now().format(formatter)); // Atualiza com a data e hora atuais
        }

        private void carregarBilhetes() {
            for (BilheteModelo bilhete : bilhetes) {
                Object[] linha = {
                    bilhete.getId(),
                    bilhete.getPassageiro().getNomePassageiro(),
                    bilhete.getVoo().getId(),
                    bilhete.getClasse(),
                    bilhete.getPreco()
                };
                modeloTabela.addRow(linha);
            }
        }

        private void calcularTotalVendas() {
            double total = 0.0;
            for (BilheteModelo bilhete : bilhetes) {
                total += bilhete.getPreco();
            }
            txtTotalVendas.setText(String.format("%.2f", total));
        }
    }

    class PainelSul extends JPanel implements ActionListener {
        JButton salvarJB, cancelarJB;

        public PainelSul() {
            salvarJB = new JButton("Salvar");
            cancelarJB = new JButton("Cancelar");

            // Ajuste de estilo dos botões
            salvarJB.setPreferredSize(new Dimension(120, 30));
            cancelarJB.setPreferredSize(new Dimension(120, 30));
            salvarJB.setBackground(new Color(59, 89, 182));
            salvarJB.setForeground(Color.WHITE);
            cancelarJB.setBackground(new Color(255, 51, 51));
            cancelarJB.setForeground(Color.WHITE);
            salvarJB.setFont(new Font("Arial", Font.BOLD, 14));
            cancelarJB.setFont(new Font("Arial", Font.BOLD, 14));

            salvarJB.addActionListener(this);
            cancelarJB.addActionListener(this);

            add(salvarJB);
            add(cancelarJB);
        }

        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() == salvarJB)
                centro.salvar();
            else
                dispose();
        }
    }

    private List<BilheteModelo> carregarBilhetesDeArquivo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("bilhetes.dat"))) {
            return (List<BilheteModelo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Retorna uma lista vazia se ocorrer erro
        }
    }

    private void salvarBilhetesEmArquivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("bilhetes.dat"))) {
            oos.writeObject(bilhetes);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar os bilhetes no arquivo", "Erro", JOptionPane.ERROR_MESSAGE);
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
            new BilheteModeloInterface();
        });
    }
}

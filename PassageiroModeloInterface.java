/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: PassageiroModeloInterface.java
Data: 22.06.2024
--------------------------------------*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PassageiroModeloInterface extends JFrame {
    PainelCenter centro;
    PainelSul sul;
    int ultimoId = 0; // Mantém o controle do último ID atribuído
    List<PassageiroModelo> passageiros; // Lista para armazenar todos os passageiros

    public PassageiroModeloInterface() {
        setTitle("Passageiros");
        setSize(600, 500);
        setLocationRelativeTo(null);

        JPanel painelNorte = new JPanel();

        getContentPane().add(painelNorte, BorderLayout.NORTH);
        getContentPane().add(centro = new PainelCenter(), BorderLayout.CENTER);
        getContentPane().add(sul = new PainelSul(), BorderLayout.SOUTH);

        // Inicializa a lista de passageiros
        passageiros = new ArrayList<>();
		
		atualizarUltimoId();

        // Carrega todos os passageiros do arquivo para a lista em memória
        passageiros = carregarPassageirosDeArquivo();

        setVisible(true);
        }

		    private void atualizarUltimoId() {
				for (PassageiroModelo passageiro : passageiros) {
					if (passageiro.getId() > ultimoId) {
						ultimoId = passageiro.getId();
					}
				}
			}
    class PainelCenter extends JPanel {
        JTextField txtNome, txtDataNascimento, txtNumDocumento, txtTelefone, txtEmail;
        JComboBox<String> cbTipoDeDocumento, cbGenero;
        String[] generos = {"Masculino", "Feminino"};
        String[] tiposDocumento = {"Bilhete", "CPF", "Passaporte"};

        public PainelCenter() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre componentes
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // linha 1
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            add(new JLabel("Nome:"), gbc);
            
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.gridwidth = 3;
            txtNome = new JTextField(20);
            add(txtNome, gbc);

            // linha 2
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            add(new JLabel("Data de Nascimento:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.gridwidth = 3;
            txtDataNascimento = new JTextField(20);
            add(txtDataNascimento, gbc);

            // linha 3
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            add(new JLabel("Tipo de Documento:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            cbTipoDeDocumento = new JComboBox<>(tiposDocumento);
            add(cbTipoDeDocumento, gbc);

            gbc.gridx = 2;
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            add(new JLabel("Número do Documento:"), gbc);

            gbc.gridx = 3;
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            txtNumDocumento = new JTextField(15);
            add(txtNumDocumento, gbc);

            // linha 4
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 1;
            add(new JLabel("Gênero:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 3;
            gbc.gridwidth = 1;
            cbGenero = new JComboBox<>(generos);
            add(cbGenero, gbc);

            // linha 5
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 1;
            add(new JLabel("Telefone:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 4;
            gbc.gridwidth = 3;
            txtTelefone = new JTextField(20);
            add(txtTelefone, gbc);

            // linha 6
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth = 1;
            add(new JLabel("Email:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 5;
            gbc.gridwidth = 3;
            txtEmail = new JTextField(20);
            add(txtEmail, gbc);
        }

        public void salvar() {
            ultimoId++;
            int id = ultimoId;
            String nome = txtNome.getText();
            String genero = (String) cbGenero.getSelectedItem();
            String dataDeNascimento = txtDataNascimento.getText();
            String tipoDeDocumento = (String) cbTipoDeDocumento.getSelectedItem();
            String numDocumento = txtNumDocumento.getText();
            String telefone = txtTelefone.getText();
            String email = txtEmail.getText();
            
            // Cria o objeto PassageiroModelo
            PassageiroModelo passageiro = new PassageiroModelo(id, nome, genero, dataDeNascimento, tipoDeDocumento, numDocumento, telefone, email);
            // Adiciona o novo passageiro à lista em memória
            passageiros.add(passageiro);

            // Salva a lista de passageiros no arquivo .dat
            salvarPassageirosEmArquivo();

            System.out.println(passageiro);
            JOptionPane.showMessageDialog(this, "Passageiro salvo com sucesso!");
            limparCampos(); // Limpa os campos após salvar
        }

        private void limparCampos() {
            txtNome.setText("");
            txtDataNascimento.setText("");
            txtNumDocumento.setText("");
            cbTipoDeDocumento.setSelectedIndex(0);
            cbGenero.setSelectedIndex(0);
            txtTelefone.setText("");
            txtEmail.setText("");
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

    private List<PassageiroModelo> carregarPassageirosDeArquivo() {
        List<PassageiroModelo> passageiros = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("passageiros.dat"))) {
            passageiros = (List<PassageiroModelo>) ois.readObject();
        } catch (EOFException e) {
            // Chegou ao final do arquivo, não há mais objetos para ler
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return passageiros;
    }

    private void salvarPassageirosEmArquivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("passageiros.dat"))) {
            oos.writeObject(passageiros);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar os passageiros no arquivo", "Erro", JOptionPane.ERROR_MESSAGE);
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
            }
            new PassageiroModeloInterface();
        });
    }
}
/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: FuncionarioModeloInterface.java
Data: 10.06.2024
--------------------------------------*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioModeloInterface extends JFrame {
    PainelCenter centro;
    PainelSul sul;
    int ultimoId = 0; // Mantém o controle do último ID atribuído
    List<FuncionarioModelo> funcionarios; // Lista para armazenar todos os funcionários

    public FuncionarioModeloInterface() {
        setTitle("Funcionarios");
        setSize(600, 500);
        setLocationRelativeTo(null);

        JPanel painelNorte = new JPanel();

        getContentPane().add(painelNorte, BorderLayout.NORTH);
        getContentPane().add(centro = new PainelCenter(), BorderLayout.CENTER);
        getContentPane().add(sul = new PainelSul(), BorderLayout.SOUTH);

        // Inicializa a lista de funcionários
        funcionarios = new ArrayList<>();

        // Carrega todos os funcionários do arquivo para a lista em memória
        funcionarios = carregarFuncionariosDeArquivo();

			atualizarUltimoId();
			
			setVisible(true);
        }

		    private void atualizarUltimoId() {
				for (FuncionarioModelo funcionario : funcionarios) {
					if (funcionario.getId() > ultimoId) {
						ultimoId = funcionario.getId();
					}
				}
			}
    class PainelCenter extends JPanel {
        JTextField txtNome, txtNumTelFuncionario, numeroDocumentoJTF;
        JPasswordField txtSenha;
        JComboBox<String> cbCargo, cbTipoDeDocumento, cbGenero, nacionalidadeJCB;
        String[] generos = {"Masculino", "Feminino"};
        String[] tiposDocumento = {"Bilhete", "CPF", "Passaporte"};
        String[] cargos = {"Gerente", "Atendente", "Piloto", "Copiloto", "Aeromoça"};
        String[] nacionalidades = {"Angolana", "Brasileira", "Americana", "Canadense"};

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
            add(new JLabel("Senha:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.gridwidth = 3;
            txtSenha = new JPasswordField(20);
            add(txtSenha, gbc);

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
            add(new JLabel("Numero do Documento:"), gbc);

            gbc.gridx = 3;
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            numeroDocumentoJTF = new JTextField(15);
            add(numeroDocumentoJTF, gbc);

            // linha 4
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 1;
            add(new JLabel("Genero:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 3;
            gbc.gridwidth = 1;
            cbGenero = new JComboBox<>(generos);
            add(cbGenero, gbc);

            gbc.gridx = 2;
            gbc.gridy = 3;
            gbc.gridwidth = 1;
            add(new JLabel("Nacionalidade:"), gbc);

            gbc.gridx = 3;
            gbc.gridy = 3;
            gbc.gridwidth = 1;
            nacionalidadeJCB = new JComboBox<>(nacionalidades);
            add(nacionalidadeJCB, gbc);
			
			// linha 5
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbc.gridwidth = 1;
			add(new JLabel("Cargo:"), gbc);

			gbc.gridx = 1;
			gbc.gridy = 4;
			gbc.gridwidth = 1;
			cbCargo = new JComboBox<>(cargos);
			add(cbCargo, gbc);
        }

        public void salvar() {
			ultimoId++;
			int id = ultimoId;
            String nome = txtNome.getText();
            String senha = new String(txtSenha.getPassword());
            String tipoDeDocumento = (String) cbTipoDeDocumento.getSelectedItem();
            String numDocumento = numeroDocumentoJTF.getText();
            String genero = (String) cbGenero.getSelectedItem();
            String nacionalidade = (String) nacionalidadeJCB.getSelectedItem();
			String cargo = cargos[0]; // Seleciona o primeiro cargo como padrão
            
            // Cria o objeto FuncionarioModelo
            FuncionarioModelo funcionario = new FuncionarioModelo(id, nome, senha, numDocumento, cargo, tipoDeDocumento, genero.charAt(0), nacionalidade);
            // Adiciona o novo funcionário à lista em memória
            funcionarios.add(funcionario);

            // Salva a lista de funcionários no arquivo .dat
            salvarFuncionariosEmArquivo();

            System.out.println(funcionario);
            JOptionPane.showMessageDialog(this, "Funcionario salvo com sucesso!");
            limparCampos(); // Limpa os campos após salvar
        }

        private void limparCampos() {
            txtNome.setText("");
            txtSenha.setText("");
            numeroDocumentoJTF.setText("");
            cbTipoDeDocumento.setSelectedIndex(0);
            cbGenero.setSelectedIndex(0);
            nacionalidadeJCB.setSelectedIndex(0);
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

    private List<FuncionarioModelo> carregarFuncionariosDeArquivo() {
    List<FuncionarioModelo> funcionarios = new ArrayList<>();
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("funcionarios.dat"))) {
        funcionarios = (List<FuncionarioModelo>) ois.readObject();
    } catch (EOFException e) {
        // Chegou ao final do arquivo, não há mais objetos para ler
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
    return funcionarios;
}


    private void salvarFuncionariosEmArquivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("funcionarios.dat"))) {
            oos.writeObject(funcionarios);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar os funcionários no arquivo", "Erro", JOptionPane.ERROR_MESSAGE);
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
            new FuncionarioModeloInterface();
        });
    }
}
/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: AviaoModeloInterface.java
Data: 10.06.2024
--------------------------------------*/
import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class AviaoModeloInterface extends JFrame {
    private List<AviaoModelo> avioes;
    private JFormattedTextField assentosField;
    private JTextField modeloField, companhiaField;
    private int ultimoId = 0;

    public AviaoModeloInterface() {
        setTitle("Gestão de Aviões");
        setSize(400, 300);
        setLocationRelativeTo(null);

        avioes = carregarAvioesDeArquivo();

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Número de Assentos
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Número de Assentos:"), gbc);

        gbc.gridx = 1;
        assentosField = criarCampoNumerico();
        gbc.weightx = 1.0;
        formPanel.add(assentosField, gbc);

        // Modelo
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Modelo:"), gbc);

        gbc.gridx = 1;
        modeloField = new JTextField(15); // Largura aumentada
        formPanel.add(modeloField, gbc);

        // Companhia Aérea
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Companhia Aérea:"), gbc);

        gbc.gridx = 1;
        companhiaField = new JTextField(15); // Largura aumentada
        formPanel.add(companhiaField, gbc);

        // Carregar a imagem do botão
        ImageIcon icon = new ImageIcon("C:\\Users\\Heliossandro Afonso\\Documents\\Aulas\\programação 2\\openJava\\HeliossandroAfonso33048\\imagens\\imgAdicionar.png");
        Image originalImage = icon.getImage();
        Image resizedImage = originalImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // Criar botão com a imagem
        JButton addButton = new JButton(resizedIcon);
        addButton.setPreferredSize(new Dimension(60, 40));
        addButton.setContentAreaFilled(false);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarAviao();
            }
        });

        // Painel de Botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);

		atualizarUltimoId();
		
        getContentPane().add(formPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
			
        }

		private void atualizarUltimoId() {
			for (AviaoModelo aviao : avioes) {
				if (aviao.getId() > ultimoId) {
					ultimoId = aviao.getId();
				}
			}
		}
			
    private JFormattedTextField criarCampoNumerico() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(1);
        JFormattedTextField campoNumerico = new JFormattedTextField(numberFormatter);
        campoNumerico.setColumns(15); // Largura aumentada
        return campoNumerico;
    }

    private void adicionarAviao() {
        ultimoId++;

        int id = ultimoId;
        int numDeAssentos = ((Number) assentosField.getValue()).intValue();
        String modelo = modeloField.getText();
        String companhiaAerea = companhiaField.getText();

        AviaoModelo aviao = new AviaoModelo(id, numDeAssentos, modelo, companhiaAerea);
        avioes.add(aviao);
        salvarAvioesEmArquivo();
        JOptionPane.showMessageDialog(this, "Avião adicionado com sucesso!");
        limparCampos();
    }

    private void limparCampos() {
        assentosField.setValue(null);
        modeloField.setText("");
        companhiaField.setText("");
    }

    private List<AviaoModelo> carregarAvioesDeArquivo() {
        List<AviaoModelo> avioes = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("avioes.dat"))) {
            avioes = (List<AviaoModelo>) ois.readObject();
        } catch (EOFException e) {
            // Arquivo vazio, não há aviões para carregar
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return avioes;
    }

    private void salvarAvioesEmArquivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("avioes.dat"))) {
            oos.writeObject(avioes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AviaoModeloInterface());
    }
}

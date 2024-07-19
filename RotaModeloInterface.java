/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: RotaModeloInterface.java
Data: 08.06.2024
--------------------------------------*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RotaModeloInterface extends JFrame {
    private JComboBox<String> cmbEstadosDestino, cmbPaisesDestino, cmbPaisesOrigem, cmbEstadosOrigem;
    private List<RotaModelo> rotas;

    public RotaModeloInterface() {
        rotas = carregarRotasDeArquivo();

        setTitle("Adicionar Rota");
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Criação dos componentes de origem
        JLabel lblOrigem = new JLabel("Origem");
        JLabel lblPaisOrigem = new JLabel("País: ");
        JLabel lblEstadoOrigem = new JLabel("Estado: ");

        String[] paises = {"Angola", "Brasil", "EUA"};
        cmbPaisesOrigem = new JComboBox<>(paises);
        cmbEstadosOrigem = new JComboBox<>();

        // Atualizar lista de estados com base no país selecionado para a origem
        cmbPaisesOrigem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarEstados(cmbPaisesOrigem, cmbEstadosOrigem);
            }
        });

        // Criação dos componentes de destino
        JLabel lblDestino = new JLabel("Destino");
        JLabel lblPaisDestino = new JLabel("País: ");
        JLabel lblEstadoDestino = new JLabel("Estado: ");

        cmbPaisesDestino = new JComboBox<>(paises);
        cmbEstadosDestino = new JComboBox<>();

        // Atualizar lista de estados com base no país selecionado para o destino
        cmbPaisesDestino.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarEstados(cmbPaisesDestino, cmbEstadosDestino);
            }
        });

        // Carregar a imagem do botão
        ImageIcon icon = new ImageIcon("C:\\Users\\Heliossandro Afonso\\Documents\\Aulas\\programação 2\\openJava\\HeliossandroAfonso33048\\imagens\\imgAdicionar.png");
        Image originalImage = icon.getImage();
        Image resizedImage = originalImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // Criar botão com a imagem
        JButton btnAdicionar = new JButton(resizedIcon);
        btnAdicionar.setPreferredSize(new Dimension(60, 40));
        btnAdicionar.setContentAreaFilled(false);
        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarRota();
            }
        });

        // Personalizar componentes
        personalizarComboBox(cmbPaisesOrigem);
        personalizarComboBox(cmbEstadosOrigem);
        personalizarComboBox(cmbPaisesDestino);
        personalizarComboBox(cmbEstadosDestino);
        personalizarLabel(lblOrigem, true);
        personalizarLabel(lblDestino, true);
        personalizarLabel(lblPaisOrigem, false);
        personalizarLabel(lblEstadoOrigem, false);
        personalizarLabel(lblPaisDestino, false);
        personalizarLabel(lblEstadoDestino, false);

        // Adicionar componentes ao layout
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel painelOrigem = criarPainel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0);
        adicionarComponentesPainel(painelOrigem, lblOrigem, lblPaisOrigem, cmbPaisesOrigem, lblEstadoOrigem, cmbEstadosOrigem);
        painelPrincipal.add(painelOrigem, gbc);

        JPanel painelDestino = criarPainel();
        gbc.gridy = 1;
        adicionarComponentesPainel(painelDestino, lblDestino, lblPaisDestino, cmbPaisesDestino, lblEstadoDestino, cmbEstadosDestino);
        painelPrincipal.add(painelDestino, gbc);

        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        painelPrincipal.add(btnAdicionar, gbc);

        // Adiciona o painel principal à janela
        add(painelPrincipal);

        setVisible(true);
    }

    // Método para atualizar a lista de estados com base no país selecionado
    private void atualizarEstados(JComboBox<String> cmbPaises, JComboBox<String> cmbEstados) {
        String paisSelecionado = (String) cmbPaises.getSelectedItem();
        cmbEstados.removeAllItems();
        switch (paisSelecionado) {
            case "Angola":
                String[] estadosAngola = {"Luanda", "Namibe", "Benguela"};
                for (String estado : estadosAngola) {
                    cmbEstados.addItem(estado);
                }
                break;
            case "Brasil":
                String[] estadosBrasil = {"São Paulo", "Rio de Janeiro", "Minas Gerais", "Bahia"};
                for (String estado : estadosBrasil) {
                    cmbEstados.addItem(estado);
                }
                break;
            case "EUA":
                String[] estadosEUA = {"Califórnia", "Texas", "Nova York", "Flórida"};
                for (String estado : estadosEUA) {
                    cmbEstados.addItem(estado);
                }
                break;
        }
    }

    private void personalizarComboBox(JComboBox<String> comboBox) {
        comboBox.setPreferredSize(new Dimension(150, 25));
        comboBox.setBackground(Color.WHITE);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    private void personalizarLabel(JLabel label, boolean isTitle) {
        if (isTitle) {
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setForeground(new Color(0, 102, 204));
        } else {
            label.setFont(new Font("Arial", Font.PLAIN, 14));
        }
    }

    private JPanel criarPainel() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setPreferredSize(new Dimension(350, 150));
        painel.setBackground(new Color(230, 230, 230));

        // Criar borda arredondada
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(1, 1, 1, 1),
                BorderFactory.createLineBorder(new Color(230, 230, 230), 10, true)
        ));

        return painel;
    }

    private void adicionarComponentesPainel(JPanel painel, JLabel lblTitulo, JLabel lblPais, JComboBox<String> cmbPais, JLabel lblEstado, JComboBox<String> cmbEstado) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 10, 10); // Adiciona mais espaçamento
        painel.add(lblTitulo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painel.add(lblPais, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 10, 10);
        painel.add(cmbPais, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        painel.add(lblEstado, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 10, 10);
        painel.add(cmbEstado, gbc);
    }

    private void adicionarRota() {
        String origem = cmbPaisesOrigem.getSelectedItem() + ", " + cmbEstadosOrigem.getSelectedItem();
        String destino = cmbPaisesDestino.getSelectedItem() + ", " + cmbEstadosDestino.getSelectedItem();

        RotaModelo novaRota = new RotaModelo(rotas.size() + 1, origem, destino);
        rotas.add(novaRota);
        salvarRotaEmArquivo();

        JOptionPane.showMessageDialog(null, "Rota Adicionada");
    }

    private void salvarRotaEmArquivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("rotas.dat"))) {
            oos.writeObject(rotas);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar a rota no arquivo");
        }
    }

    private List<RotaModelo> carregarRotasDeArquivo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("rotas.dat"))) {
            return (List<RotaModelo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RotaModeloInterface());
    }
}
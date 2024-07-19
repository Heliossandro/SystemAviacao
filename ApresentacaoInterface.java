/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: ApresentacaoInterface.java
Data: 23.06.2024
--------------------------------------*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ApresentacaoInterface extends JFrame {
    private PainelCentro centro;
    private PainelSul sul;

    public ApresentacaoInterface() {
        setTitle("Tela de boas vindas");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String caminhoImg = "C:\\Users\\Heliossandro Afonso\\Documents\\Aulas\\programação 2\\openJava\\HeliossandroAfonso33048\\imagens\\banerApresentacao.png";
        JLabel lblImage = new JLabel();
        try {
            // Carregar a imagem original
            BufferedImage originalImage = ImageIO.read(new File(caminhoImg));
            // Definir a nova altura
            int newHeight = 250; // altura desejada
            // Calcular a nova largura mantendo a proporção
            int newWidth = (newHeight * originalImage.getWidth()) / originalImage.getHeight();
            // Redimensionar a imagem
            Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            // Criar um ImageIcon a partir da imagem redimensionada
            ImageIcon imageIcon = new ImageIcon(resizedImage);
            lblImage.setIcon(imageIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel painelNorte = new JPanel();
        painelNorte.add(lblImage);

        add(painelNorte, BorderLayout.NORTH);

        centro = new PainelCentro();
        add(centro, BorderLayout.CENTER);

        sul = new PainelSul();
        add(sul, BorderLayout.SOUTH);

        setVisible(true);
    }

    public class PainelCentro extends JPanel {
        JTextArea txtAInfPrincipal;
        JCheckBox jcbCheck;

        public PainelCentro() {
            setLayout(new GridLayout(2, 1));

            txtAInfPrincipal = new JTextArea(80, 30);
            txtAInfPrincipal.setText("Bem vindo ao AEROBILHETE, somos uma agencia com a finalidade de venda \n" +
                    "de bilhetes de aviação, gerenciamento de dados de uma aviação e muito mais. \n" +
                    "Este projeto foi criado no âmbito da cadeira de Fundamentos de Programação II, \n" +
                    "no curso de Engenharia Informática da UCAN. \n" +
                    "É de uso exclusivo para alunos e professores.\n" +
                    "Criador: Heliossandro Afonso, id: 33048, Tema: Gestão de Bilhetes de Aviação \n" +
                    "Se concorda com os termos e condições, clique em concordar.");
            txtAInfPrincipal.setFocusable(false);
            add(new JScrollPane(txtAInfPrincipal));

            jcbCheck = new JCheckBox("Concordo com os termos de uso");
            jcbCheck.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if (jcbCheck.isSelected()) {
                        sul.ativarBtn(true);
                    } else {
                        sul.ativarBtn(false);
                    }
                }
            });
            add(jcbCheck);
        }
    }

    public class PainelSul extends JPanel {
        private JButton btnEntrar, btnSair;

        public PainelSul() {
            btnEntrar = new JButton("Entrar");
            btnEntrar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    new LoginInterface();
					dispose();
                }
            });
            add(btnEntrar);

            btnSair = new JButton("Sair");
            btnSair.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    dispose();
                }
            });
            add(btnSair);

            ativarBtn(false);
        }

        public void ativarBtn(boolean status) {
            btnEntrar.setEnabled(status);
        }
    }

    public static void main(String args[]) {
        // Vector_Tabelas.inic();
        new ApresentacaoInterface();
    }
}

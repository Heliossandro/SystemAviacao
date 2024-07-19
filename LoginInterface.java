import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LoginInterface extends JFrame {
    // Dados fixos para email e senha
    private static final String EMAIL_FIXO = "12345";
    private static final String SENHA_FIXA = "12345";

    public LoginInterface() {
        setTitle("Login");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painelLogin = new JPanel(new BorderLayout());
        painelLogin.setBackground(new Color(230, 230, 250));

        // Painel com a imagem à esquerda
        JPanel painelEsquerdo = new JPanel();
        painelEsquerdo.setBackground(new Color(230, 230, 250));
        String caminhoImgLogin = "C:\\Users\\Heliossandro Afonso\\Documents\\Aulas\\programação 2\\openJava\\HeliossandroAfonso33048\\imagens\\Login.png";
        JLabel lblImageLogin = new JLabel();
        try {
            Image originalImageLogin = ImageIO.read(new File(caminhoImgLogin));
            Image resizedImageLogin = originalImageLogin.getScaledInstance(400, 600, Image.SCALE_SMOOTH);
            ImageIcon imageIconLogin = new ImageIcon(resizedImageLogin);
            lblImageLogin.setIcon(imageIconLogin);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar imagem de login.", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        painelEsquerdo.add(lblImageLogin);
        painelLogin.add(painelEsquerdo, BorderLayout.WEST);

        // Painel com campos de email e senha à direita
        JPanel painelDireito = new JPanel(new GridBagLayout());
        painelDireito.setBackground(new Color(230, 230, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Adiciona campos de entrada
        InputField emailField = new InputField("Email:");
        emailField.setPreferredSize(new Dimension(300, 30));
        painelDireito.add(emailField, gbc);

        gbc.gridy = 1;
        InputField senhaField = new InputField("Senha:");
        senhaField.setPreferredSize(new Dimension(300, 30));
        senhaField.setAsPasswordField(); // Transforma em campo de senha
        painelDireito.add(senhaField, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        // Adiciona painel de botões
        ButtonPanel buttonPanel = new ButtonPanel(emailField, senhaField);
        painelDireito.add(buttonPanel, gbc);

        painelLogin.add(painelDireito, BorderLayout.CENTER);

        add(painelLogin);
        setVisible(true);

        // Adiciona ActionListener para o campo de email
        emailField.textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Transfere o foco para o campo de senha ao pressionar Enter
                senhaField.textField.requestFocusInWindow();
            }
        });
    }

    // Classe para campos de entrada
    private class InputField extends JPanel {
        private JLabel lbl;
        private JTextField textField;

        public InputField(String label) {
            setLayout(new BorderLayout());
            lbl = new JLabel(label);
            lbl.setFont(new Font("Arial", Font.PLAIN, 18));
            textField = new JTextField();
            textField.setFont(new Font("Arial", Font.PLAIN, 18));
            add(lbl, BorderLayout.WEST);
            add(textField, BorderLayout.CENTER);
        }

        public String getText() {
            return textField.getText();
        }

        public void addKeyListener(KeyAdapter adapter) {
            textField.addKeyListener(adapter);
        }

        public void setAsPasswordField() {
            textField = new JPasswordField();
            textField.setFont(new Font("Arial", Font.PLAIN, 18));
            removeAll();
            add(lbl, BorderLayout.WEST);
            add(textField, BorderLayout.CENTER);
            revalidate();
            repaint();
        }

        public char[] getPassword() {
            if (textField instanceof JPasswordField) {
                return ((JPasswordField) textField).getPassword();
            }
            return new char[0];
        }
    }

    // Classe para o painel de botões
    private class ButtonPanel extends JPanel {
        public ButtonPanel(InputField emailField, InputField senhaField) {
            setBackground(new Color(230, 230, 250));

            JButton btnEntrarLogin = new JButton("Entrar");
            btnEntrarLogin.setFont(new Font("Arial", Font.BOLD, 18));
            btnEntrarLogin.setBackground(new Color(100, 149, 237));
            btnEntrarLogin.setForeground(Color.WHITE);

            btnEntrarLogin.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    String email = emailField.getText();
                    String senha = new String(senhaField.getPassword());

                    if (EMAIL_FIXO.equals(email) && SENHA_FIXA.equals(senha)) {
                        JOptionPane.showMessageDialog(null, "Login realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        MenuPrincipal menu = new MenuPrincipal();
                        menu.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Email ou senha incorretos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            // Adiciona KeyListener para ativar o botão Entrar ao pressionar Enter
            senhaField.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        btnEntrarLogin.doClick();
                    }
                }
            });

            JButton btnSairLogin = new JButton("Sair");
            btnSairLogin.setFont(new Font("Arial", Font.BOLD, 18));
            btnSairLogin.setBackground(new Color(255, 69, 0));
            btnSairLogin.setForeground(Color.WHITE);

            btnSairLogin.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    dispose();
                }
            });

            add(btnEntrarLogin);
            add(Box.createRigidArea(new Dimension(10, 0))); // Espaçamento entre botões
            add(btnSairLogin);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginInterface();
            }
        });
    }
}

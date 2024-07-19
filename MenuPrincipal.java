/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossandro Afonso
Numero: 33048
Ficheiro: MenuPrincipal.java
Data: 08.06.2024
--------------------------------------*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class MenuPrincipal extends JFrame implements ActionListener {
    private JMenuBar menuBar;
    private JMenu vooMenu, rotasMenu, aviaoMenu, funcionariosMenu, sobreMenu, bilhetesMenu, passageiroMenu;
    private JMenuItem adicionarRota, listarRotas, adicionarVoo, listarVoo, adicionarBilhetes, listarBilhetes,
            adicionarAviao, listarAviao, adicionarFuncionario, listarFuncionario, sobreOAutor, sobreOPrograma,
            cadastrarPassageiro, listarPassageiros, listarTodosBilhetes, pesquisarBilhetePorId, pesquisarBilhetePorNome;
    private JTextField txtPesquisa;

    private ArrayList<BilheteModelo> bilhetes; // Lista para armazenar todos os bilhetes

    public MenuPrincipal() {
        instanciarObjetos();
        configurarJanela();
        adicionarComponentes();
        bilhetes = carregarBilhetesDeArquivo(); // Carrega os bilhetes do arquivo ao iniciar
        setVisible(true);
    }

    private void instanciarObjetos() {
        menuBar = new JMenuBar();

        JMenu menu = new JMenu("\u2630"); // Unicode para o ícone de hambúrguer
        menu.setFont(new Font("Arial", Font.PLAIN, 30)); // Ajusta o tamanho do ícone

        vooMenu = new JMenu("Voo");
        rotasMenu = new JMenu("Rotas");
        aviaoMenu = new JMenu("Avião");
        bilhetesMenu = new JMenu("Bilhetes");
        funcionariosMenu = new JMenu("Funcionários");
        passageiroMenu = new JMenu("Passageiros");
        sobreMenu = new JMenu("Sobre");

        // Adiciona os itens ao menu
        menu.add(vooMenu);
        menu.add(rotasMenu);
        menu.add(aviaoMenu);
        menu.add(bilhetesMenu);
        menu.add(funcionariosMenu);
        menu.add(passageiroMenu);
        menu.add(sobreMenu);

        // Itens do Voo
        vooMenu.add(adicionarVoo = new JMenuItem("Adicionar Voo"));
        vooMenu.add(listarVoo = new JMenuItem("Listar Voo"));

        // Itens do Rotas
        rotasMenu.add(adicionarRota = new JMenuItem("Adicionar Rota"));
        rotasMenu.add(listarRotas = new JMenuItem("Listar Rotas"));

        // Itens do avião
        aviaoMenu.add(adicionarAviao = new JMenuItem("Adicionar avião"));
        aviaoMenu.add(listarAviao = new JMenuItem("Listar Avião"));

        // Itens do Bilhetes
        bilhetesMenu.add(adicionarBilhetes = new JMenuItem("Adicionar Bilhetes"));
        bilhetesMenu.add(listarBilhetes = new JMenuItem("Listar Bilhetes"));
        bilhetesMenu.add(pesquisarBilhetePorId = new JMenuItem("Pesquisar Bilhete por ID"));
        bilhetesMenu.add(pesquisarBilhetePorNome = new JMenuItem("Pesquisar Bilhete por Nome"));

        // Itens do Funcionarios
        funcionariosMenu.add(adicionarFuncionario = new JMenuItem("Cadastrar funcionário"));
        funcionariosMenu.add(listarFuncionario = new JMenuItem("Listar funcionário"));

        // Itens do Passageiros
        passageiroMenu.add(cadastrarPassageiro = new JMenuItem("Cadastrar Passageiro"));
        passageiroMenu.add(listarPassageiros = new JMenuItem("Lista dos passageiros"));

        // Itens do Sobre
        sobreMenu.add(sobreOPrograma = new JMenuItem("Sobre o software"));
        sobreMenu.add(sobreOAutor = new JMenuItem("Autor"));

        adicionarVoo.addActionListener(this);
        listarVoo.addActionListener(this);
        adicionarRota.addActionListener(this);
        listarRotas.addActionListener(this);
        adicionarFuncionario.addActionListener(this);
        listarFuncionario.addActionListener(this);
        adicionarAviao.addActionListener(this);
        listarAviao.addActionListener(this);
        adicionarBilhetes.addActionListener(this);
        listarBilhetes.addActionListener(this);
        pesquisarBilhetePorId.addActionListener(this);
        pesquisarBilhetePorNome.addActionListener(this);
        cadastrarPassageiro.addActionListener(this);
        listarPassageiros.addActionListener(this);
        sobreOAutor.addActionListener(this);

        // Adiciona o menu à barra de menu
        menuBar.add(Box.createHorizontalGlue()); // Empurra o menu para a direita
        menuBar.add(menu);
    }

    private void configurarJanela() {
        setTitle("Aerobilhete");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setJMenuBar(menuBar);
    }

    private void adicionarComponentes() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(50, 50, 50)); // Cor de fundo do painel

        JLabel titulo = new JLabel("Bem-vindo ao Sistema de Gestão de Bilhetes de Aviação");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        painel.add(titulo, BorderLayout.NORTH);
        
        JPanel centralPanel = new JPanel(new GridBagLayout());     
        painel.add(centralPanel, BorderLayout.CENTER);

        add(painel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == adicionarRota) {
            new RotaModeloInterface();
        }

        if (e.getSource() == adicionarFuncionario) {
            new FuncionarioModeloInterface();
        }

        if (e.getSource() == listarFuncionario) {
            new ListarFuncionariosInterface();
        }

        if (e.getSource() == adicionarAviao) {
            new AviaoModeloInterface();
        }

        if (e.getSource() == listarRotas) {
            new ListarRotasInterface();
        }

        if (e.getSource() == listarAviao) {
            new ListarAvioesInterface();
        }

        if (e.getSource() == adicionarBilhetes) {
            new BilheteModeloInterface();
        }

        if (e.getSource() == listarBilhetes) {
            new ListarBilhetesInterface1();
        }

        if (e.getSource() == cadastrarPassageiro) {
            new PassageiroModeloInterface();
        }

        if (e.getSource() == listarPassageiros){
            new ListarPassageirosInterface();
        }

        if (e.getSource() == adicionarVoo) {
            new VooModeloInterface();
        }

        if (e.getSource() == listarVoo) {
            new ListarVoosInterface();
        }

        if (e.getSource() == sobreOAutor) {
            JOptionPane.showMessageDialog(null, "Autor: Heliossandro Afonso");
        }

        if (e.getSource() == listarTodosBilhetes) {
            listarTodosBilhetes();
        }

        if (e.getSource() == pesquisarBilhetePorId) {
            String idStr = JOptionPane.showInputDialog(null, "Digite o ID do bilhete:");
            if (idStr != null && !idStr.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    pesquisarBilhetePorId(id);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "ID inválido. Digite um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (e.getSource() == pesquisarBilhetePorNome) {
            String nome = JOptionPane.showInputDialog(null, "Digite o nome do passageiro:");
            if (nome != null && !nome.trim().isEmpty()) {
                pesquisarBilhetePorNome(nome);
            }
        }
    }

    private void listarTodosBilhetes() {
        StringBuilder sb = new StringBuilder();
        for (BilheteModelo bilhete : bilhetes) {
            sb.append(bilhete.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Todos os Bilhetes", JOptionPane.PLAIN_MESSAGE);
    }

    private void pesquisarBilhetePorId(int id) {
        for (BilheteModelo bilhete : bilhetes) {
            if (bilhete.getId() == id) {
                JOptionPane.showMessageDialog(null, bilhete.toString(), "Bilhete encontrado", JOptionPane.PLAIN_MESSAGE);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Nenhum bilhete encontrado com o ID: " + id, "Bilhete não encontrado", JOptionPane.ERROR_MESSAGE);
    }

    private void pesquisarBilhetePorNome(String nome) {
        StringBuilder sb = new StringBuilder();
        boolean encontrado = false;
        for (BilheteModelo bilhete : bilhetes) {
            if (bilhete.getNomePassageiro().equalsIgnoreCase(nome)) {
                sb.append(bilhete.toString()).append("\n");
                encontrado = true;
            }
        }
        if (encontrado) {
            JOptionPane.showMessageDialog(null, sb.toString(), "Bilhetes de " + nome, JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum bilhete encontrado para o passageiro: " + nome, "Bilhetes não encontrados", JOptionPane.ERROR_MESSAGE);
        }
    }

    private ArrayList<BilheteModelo> carregarBilhetesDeArquivo() {
        ArrayList<BilheteModelo> bilhetes = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("bilhetes.dat"))) {
            bilhetes = (ArrayList<BilheteModelo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bilhetes;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuPrincipal::new);
    }
}

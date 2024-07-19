/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: ListarBilhetesInterface1.java
Data: 17.06.2024
--------------------------------------*/

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListarBilhetesInterface extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private List<BilheteModelo> bilhetes;

    public ListarBilhetesInterface() {
        bilhetes = carregarBilhetesDeArquivo();

        setTitle("Listar Bilhetes");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"ID", "Nome do Passageiro", "Número do Voo", "Classe", "Preço", "Editar", "Apagar", "Detalhes"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 5; // A partir da coluna "Editar", todas são editáveis
            }
        };

        preencherTabela();

        table.getColumn("Editar").setCellRenderer(new ButtonRenderer());
        table.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn("Apagar").setCellRenderer(new ButtonRenderer());
        table.getColumn("Apagar").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn("Detalhes").setCellRenderer(new ButtonRenderer());
        table.getColumn("Detalhes").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void preencherTabela() {
        for (BilheteModelo bilhete : bilhetes) {
            tableModel.addRow(new Object[]{
                    bilhete.getId(),
                    bilhete.getNomePassageiro(),
                    bilhete.getNumeroVoo(),
                    bilhete.getClasse(),
                    bilhete.getPreco(),
                    "Editar",
                    "Apagar",
                    "Detalhes"
            });
        }
    }

    private List<BilheteModelo> carregarBilhetesDeArquivo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("bilhetes.dat"))) {
            return (List<BilheteModelo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private void salvarBilhetesEmArquivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("bilhetes.dat"))) {
            oos.writeObject(bilhetes);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar os bilhetes no arquivo", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            JButton button = new JButton(label);
            button.addActionListener(e -> {
                if ("Editar".equals(label)) {
                    editarBilhete(row);
                } else if ("Apagar".equals(label)) {
                    apagarBilhete(row);
                } else if ("Detalhes".equals(label)) {
                    mostrarDetalhesBilhete(row);
                }
                fireEditingStopped();
            });
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }

        private void editarBilhete(int row) {
            BilheteModelo bilhete = bilhetes.get(row);

            // Exemplo de edição para cada campo
            String novoNomePassageiro = JOptionPane.showInputDialog(null, "Editar Nome do Passageiro:", bilhete.getNomePassageiro());
            String novoNumeroVoo = JOptionPane.showInputDialog(null, "Editar Número do Voo:", bilhete.getNumeroVoo());
            String novaClasse = JOptionPane.showInputDialog(null, "Editar Classe:", bilhete.getClasse());
            String novoPreco = JOptionPane.showInputDialog(null, "Editar Preço:", bilhete.getPreco());

            // Verifica se algum campo foi alterado e atualiza o modelo e a tabela
            if (novoNomePassageiro != null || novoNumeroVoo != null || novaClasse != null || novoPreco != null) {
                if (novoNomePassageiro != null) {
                    bilhete.setNomePassageiro(novoNomePassageiro);
                    tableModel.setValueAt(novoNomePassageiro, row, 1); // Atualiza a tabela
                }
                if (novoNumeroVoo != null) {
                    bilhete.setNumeroVoo(novoNumeroVoo);
                    tableModel.setValueAt(novoNumeroVoo, row, 2); // Atualiza a tabela
                }
                if (novaClasse != null) {
                    bilhete.setClasse(novaClasse);
                    tableModel.setValueAt(novaClasse, row, 3); // Atualiza a tabela
                }
                if (novoPreco != null) {
                    bilhete.setPreco(novoPreco);
                    tableModel.setValueAt(novoPreco, row, 4); // Atualiza a tabela
                }

                salvarBilhetesEmArquivo(); // Salva as alterações no arquivo
            }
        }

        private void apagarBilhete(int row) {
            int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja apagar este bilhete?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                bilhetes.remove(row);
                tableModel.removeRow(row); // Remove da tabela
                salvarBilhetesEmArquivo();
            }
        }

        private void mostrarDetalhesBilhete(int row) {
            BilheteModelo bilhete = bilhetes.get(row);
            JOptionPane.showMessageDialog(null, bilhete.toString(), "Detalhes do Bilhete", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ListarBilhetesInterface::new);
    }
}

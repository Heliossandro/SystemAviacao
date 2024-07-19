/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: ListarVoosInterface.java
Data: 23.06.2024
--------------------------------------*/

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListarVoosInterface extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private List<VooModelo> voos;

    public ListarVoosInterface() {
        voos = carregarVoosDeArquivo();

        setTitle("Listar Voos");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"ID", "Data de Partida", "Hora de Partida", "Data de Chegada", "Hora de Chegada", "Status", "Avião", "Rota", "Preço", "Editar", "Apagar", "Detalhes"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 9; // A partir da coluna de "Editar", todas são editáveis
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
        for (VooModelo voo : voos) {
            tableModel.addRow(new Object[]{
                    voo.getId(),
                    voo.getDataPartida(),
                    voo.getHoraPartida(),
                    voo.getDataChegada(),
                    voo.getHoraChegada(),
                    voo.getStatus(),
                    voo.getAviao(),
                    voo.getRota().getPartida() + " -> " + voo.getRota().getDestino(),
                    voo.getPreco(), // Adicionado o preço aqui
                    "Editar",
                    "Apagar",
                    "Detalhes"
            });
        }
    }

    private List<VooModelo> carregarVoosDeArquivo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("voos.dat"))) {
            return (List<VooModelo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private void salvarVoosEmArquivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("voos.dat"))) {
            oos.writeObject(voos);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar os voos no arquivo", "Erro", JOptionPane.ERROR_MESSAGE);
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
                    editarVoo(row);
                } else if ("Apagar".equals(label)) {
                    apagarVoo(row);
                } else if ("Detalhes".equals(label)) {
                    mostrarDetalhesVoo(row);
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

        private void editarVoo(int row) {
            VooModelo voo = voos.get(row);

            // Exemplo de edição para cada campo
            String novaDataPartida = JOptionPane.showInputDialog(null, "Editar Data de Partida:", voo.getDataPartida());
            String novaHoraPartida = JOptionPane.showInputDialog(null, "Editar Hora de Partida:", voo.getHoraPartida());
            String novaDataChegada = JOptionPane.showInputDialog(null, "Editar Data de Chegada:", voo.getDataChegada());
            String novaHoraChegada = JOptionPane.showInputDialog(null, "Editar Hora de Chegada:", voo.getHoraChegada());
            String novoStatus = JOptionPane.showInputDialog(null, "Editar Status:", voo.getStatus().toString());
            String novoPreco = JOptionPane.showInputDialog(null, "Editar Preço:", voo.getPreco()); // Novo campo para editar o preço

            // Verifica se algum campo foi alterado e atualiza o modelo e a tabela
            if (novaDataPartida != null || novaHoraPartida != null || novaDataChegada != null || novaHoraChegada != null || novoStatus != null || novoPreco != null) {
                if (novaDataPartida != null) {
                    voo.setDataPartida(novaDataPartida);
                    tableModel.setValueAt(novaDataPartida, row, 1); // Atualiza a tabela
                }
                if (novaHoraPartida != null) {
                    voo.setHoraPartida(novaHoraPartida);
                    tableModel.setValueAt(novaHoraPartida, row, 2); // Atualiza a tabela
                }
                if (novaDataChegada != null) {
                    voo.setDataChegada(novaDataChegada);
                    tableModel.setValueAt(novaDataChegada, row, 3); // Atualiza a tabela
                }
                if (novaHoraChegada != null) {
                    voo.setHoraChegada(novaHoraChegada);
                    tableModel.setValueAt(novaHoraChegada, row, 4); // Atualiza a tabela
                }
                if (novoStatus != null) {
                    try {
                        VooModelo.StatusVoo status = VooModelo.StatusVoo.valueOf(novoStatus);
                        voo.setStatus(status);
                        tableModel.setValueAt(status, row, 5); // Atualiza a tabela
                    } catch (IllegalArgumentException e) {
                        JOptionPane.showMessageDialog(null, "Status inválido. Utilize DISPONIVEL ou INDISPONIVEL.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (novoPreco != null) {
                    try {
                        double preco = Double.parseDouble(novoPreco);
                        voo.setPreco(preco);
                        tableModel.setValueAt(preco, row, 8); // Atualiza a tabela
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Preço inválido. Insira um valor numérico.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }

                salvarVoosEmArquivo(); // Salva as alterações no arquivo
            }
        }

        private void apagarVoo(int row) {
            int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja apagar este voo?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                voos.remove(row);
                tableModel.removeRow(row); // Remove da tabela
                salvarVoosEmArquivo();
            }
        }

        private void mostrarDetalhesVoo(int row) {
            VooModelo voo = voos.get(row);
            JOptionPane.showMessageDialog(null, voo.toString(), "Detalhes do Voo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ListarVoosInterface::new);
    }
}

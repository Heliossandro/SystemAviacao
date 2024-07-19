/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: ListarAvioesInterface.java
Data: 10.06.2024
--------------------------------------*/

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListarAvioesInterface extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private List<AviaoModelo> avioes;

    public ListarAvioesInterface() {
        avioes = carregarAvioesDeArquivo();

        setTitle("Listar Aviões");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"ID", "Número de Assentos", "Modelo", "Companhia Aérea", "Editar", "Apagar"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4 || column == 5; // Apenas as colunas de editar e apagar são editáveis
            }
        };

        preencherTabela();

        table.getColumn("Editar").setCellRenderer(new ButtonRenderer());
        table.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn("Apagar").setCellRenderer(new ButtonRenderer());
        table.getColumn("Apagar").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void preencherTabela() {
        for (AviaoModelo aviao : avioes) {
            tableModel.addRow(new Object[]{aviao.getId(), aviao.getNumDeAssentos(), aviao.getModelo(), aviao.getCompanhiaAerea(), "Editar", "Apagar"});
        }
    }

    private List<AviaoModelo> carregarAvioesDeArquivo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("avioes.dat"))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<AviaoModelo>) obj;
            } else {
                System.out.println("Arquivo vazio ou formato inválido.");
                return new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar os aviões do arquivo: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void salvarAvioesEmArquivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("avioes.dat"))) {
            oos.writeObject(avioes);
            System.out.println("Aviões salvos com sucesso.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar os aviões no arquivo", "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro ao salvar os aviões no arquivo: " + e.getMessage());
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
                    editarAviao(row);
                } else if ("Apagar".equals(label)) {
                    apagarAviao(row);
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

        private void editarAviao(int row) {
            AviaoModelo aviao = avioes.get(row);
            String novoModelo = JOptionPane.showInputDialog(null, "Editar Modelo:", aviao.getModelo());
            String novaCompanhiaAerea = JOptionPane.showInputDialog(null, "Editar Companhia Aérea:", aviao.getCompanhiaAerea());
            String novoNumDeAssentosStr = JOptionPane.showInputDialog(null, "Editar Número de Assentos:", aviao.getNumDeAssentos());
            int novoNumDeAssentos = Integer.parseInt(novoNumDeAssentosStr);

            if (novoModelo != null && novaCompanhiaAerea != null && novoNumDeAssentosStr != null) {
                aviao.setModelo(novoModelo);
                aviao.setCompanhiaAerea(novaCompanhiaAerea);
                aviao.setNumDeAssentos(novoNumDeAssentos);

                // Atualiza a tabela com os novos dados
                tableModel.setValueAt(novoNumDeAssentos, row, 1);
                tableModel.setValueAt(novoModelo, row, 2);
                tableModel.setValueAt(novaCompanhiaAerea, row, 3);

                salvarAvioesEmArquivo();
            }
        }

        private void apagarAviao(int row) {
            int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja apagar este avião?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                avioes.remove(row);
                tableModel.removeRow(row);
                salvarAvioesEmArquivo();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ListarAvioesInterface());
    }
}

/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: ListarRotasInterface.java
Data: 08.06.2024
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

public class ListarRotasInterface extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private List<RotaModelo> rotas;

    public ListarRotasInterface() {
        rotas = carregarRotasDeArquivo();

        setTitle("Listar Rotas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"ID", "Origem", "Destino", "Editar", "Apagar"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3 || column == 4; // Apenas as colunas de editar e apagar são editáveis
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
        for (RotaModelo rota : rotas) {
            tableModel.addRow(new Object[]{rota.getId(), rota.getPartida(), rota.getDestino(), "Editar", "Apagar"});
        }
    }

    private List<RotaModelo> carregarRotasDeArquivo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("rotas.dat"))) {
            return (List<RotaModelo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private void salvarRotasEmArquivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("rotas.dat"))) {
            oos.writeObject(rotas);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar as rotas no arquivo", "Erro", JOptionPane.ERROR_MESSAGE);
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
                    editarRota(row);
                } else if ("Apagar".equals(label)) {
                    apagarRota(row);
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

        private void editarRota(int row) {
            RotaModelo rota = rotas.get(row);
            String novaPartida = JOptionPane.showInputDialog(null, "Editar Origem:", rota.getPartida());
            String novoDestino = JOptionPane.showInputDialog(null, "Editar Destino:", rota.getDestino());

            if (novaPartida != null && novoDestino != null) {
                rota.setPartida(novaPartida);
                rota.setDestino(novoDestino);
                tableModel.setValueAt(novaPartida, row, 1);
                tableModel.setValueAt(novoDestino, row, 2);
                salvarRotasEmArquivo();
            }
        }

        private void apagarRota(int row) {
            int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja apagar esta rota?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                rotas.remove(row);
                tableModel.removeRow(row);
                salvarRotasEmArquivo();
            }
        }
    }
}
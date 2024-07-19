/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: ListarPassageirosInterface.java
Data: 10.06.2024
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

public class ListarPassageirosInterface extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private List<PassageiroModelo> passageiros;

    public ListarPassageirosInterface() {
        passageiros = carregarPassageirosDeArquivo();

        setTitle("Listar Passageiros");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"ID", "Nome", "Gênero", "Data de Nascimento", "Tipo de Documento", "Número do Documento", "Telefone", "Email", "Editar", "Apagar", "Detalhes"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 8; // A partir da coluna de "Editar", todas são editáveis
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
        for (PassageiroModelo passageiro : passageiros) {
            tableModel.addRow(new Object[]{
                    passageiro.getId(),
                    passageiro.getNomePassageiro(),
                    passageiro.getGenero(),
                    passageiro.getDataDeNascimento(),
                    passageiro.getTipodeDocumento(),
                    passageiro.getNumDoDocumento(),
                    passageiro.getTelefone(),
                    passageiro.getEmail(),
                    "Editar",
                    "Apagar",
                    "Detalhes"
            });
        }
    }

    private List<PassageiroModelo> carregarPassageirosDeArquivo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("passageiros.dat"))) {
            return (List<PassageiroModelo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private void salvarPassageirosEmArquivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("passageiros.dat"))) {
            oos.writeObject(passageiros);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar os passageiros no arquivo", "Erro", JOptionPane.ERROR_MESSAGE);
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
                    editarPassageiro(row);
                } else if ("Apagar".equals(label)) {
                    apagarPassageiro(row);
                } else if ("Detalhes".equals(label)) {
                    mostrarDetalhesPassageiro(row);
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

		private void editarPassageiro(int row) {
			PassageiroModelo passageiro = passageiros.get(row);

			// Exemplo de edição para cada campo
			String novoNome = JOptionPane.showInputDialog(null, "Editar Nome:", passageiro.getNomePassageiro());
			String novoGenero = JOptionPane.showInputDialog(null, "Editar Gênero:", passageiro.getGenero());
			String novaDataNascimento = JOptionPane.showInputDialog(null, "Editar Data de Nascimento:", passageiro.getDataDeNascimento());
			String novoTipoDocumento = JOptionPane.showInputDialog(null, "Editar Tipo de Documento:", passageiro.getTipodeDocumento());
			String novoNumDocumento = JOptionPane.showInputDialog(null, "Editar Número do Documento:", passageiro.getNumDoDocumento());
			String novoTelefone = JOptionPane.showInputDialog(null, "Editar Telefone:", passageiro.getTelefone());
			String novoEmail = JOptionPane.showInputDialog(null, "Editar Email:", passageiro.getEmail());

			// Verifica se algum campo foi alterado e atualiza o modelo e a tabela
			if (novoNome != null || novoGenero != null || novaDataNascimento != null ||
					novoTipoDocumento != null || novoNumDocumento != null || novoTelefone != null || novoEmail != null) {
				if (novoNome != null) {
					passageiro.setNomePassageiro(novoNome);
					tableModel.setValueAt(novoNome, row, 1); // Atualiza a tabela
				}
				if (novoGenero != null) {
					passageiro.setGenero(novoGenero);
					tableModel.setValueAt(novoGenero, row, 2); // Atualiza a tabela
				}
				if (novaDataNascimento != null) {
					passageiro.setDataDeNascimento(novaDataNascimento);
					tableModel.setValueAt(novaDataNascimento, row, 3); // Atualiza a tabela
				}
				if (novoTipoDocumento != null) {
					passageiro.setTipodeDocumento(novoTipoDocumento);
					tableModel.setValueAt(novoTipoDocumento, row, 4); // Atualiza a tabela
				}
				if (novoNumDocumento != null) {
					passageiro.setNumDoDocumento(novoNumDocumento);
					tableModel.setValueAt(novoNumDocumento, row, 5); // Atualiza a tabela
				}
				if (novoTelefone != null) {
					passageiro.setTelefone(novoTelefone);
					tableModel.setValueAt(novoTelefone, row, 6); // Atualiza a tabela
				}
				if (novoEmail != null) {
					passageiro.setEmail(novoEmail);
					tableModel.setValueAt(novoEmail, row, 7); // Atualiza a tabela
				}
				
				salvarPassageirosEmArquivo(); // Salva as alterações no arquivo
			}
		}


        private void apagarPassageiro(int row) {
            int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja apagar este passageiro?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                passageiros.remove(row);
                tableModel.removeRow(row); // Remove da tabela
                salvarPassageirosEmArquivo();
            }
        }

        private void mostrarDetalhesPassageiro(int row) {
            PassageiroModelo passageiro = passageiros.get(row);
            JOptionPane.showMessageDialog(null, passageiro.toString(), "Detalhes do Passageiro", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ListarPassageirosInterface::new);
    }
}

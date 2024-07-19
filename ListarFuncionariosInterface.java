/*------------------------------------
Tema: Gestão de Bilhetes de Aviação
Nome: Heliossansdro Afonso
Numero: 33048
Ficheiro: ListarFuncionariosInterface.java
Data: 09.06.2024
--------------------------------------*/

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListarFuncionariosInterface extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private List<FuncionarioModelo> funcionarios;

    // Opções para o tipo de documento e cargo
    private String[] tiposDocumento = {"RG", "CPF", "Passaporte"};
    private String[] cargos = {"Gerente", "Analista", "Desenvolvedor", "Estagiário"};

    public ListarFuncionariosInterface() {
        funcionarios = carregarFuncionariosDeArquivo();

        setTitle("Listar Funcionários");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"ID", "Nome", "Senha", "Tipo de Documento", "Número do Documento", "Gênero", "Nacionalidade", "Cargo", "Editar", "Apagar"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8 || column == 9; // Apenas as colunas de editar e apagar são editáveis
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
        for (FuncionarioModelo funcionario : funcionarios) {
            tableModel.addRow(new Object[]{funcionario.getId(), funcionario.getNome(), funcionario.getSenha(), funcionario.getTipoDeDocumento(), funcionario.getNumDocumento(), funcionario.getGenero(), funcionario.getNacionalidade(), funcionario.getCargo(), "Editar", "Apagar"});
        }
    }

    private List<FuncionarioModelo> carregarFuncionariosDeArquivo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("funcionarios.dat"))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<FuncionarioModelo>) obj;
            } else {
                System.out.println("Arquivo vazio ou formato inválido.");
                return new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar os funcionários do arquivo: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void salvarFuncionariosEmArquivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("funcionarios.dat"))) {
            oos.writeObject(funcionarios);
            System.out.println("Funcionários salvos com sucesso.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar os funcionários no arquivo", "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro ao salvar os funcionários no arquivo: " + e.getMessage());
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
                    editarFuncionario(row);
                } else if ("Apagar".equals(label)) {
                    apagarFuncionario(row);
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

        private void editarFuncionario(int row) {
            FuncionarioModelo funcionario = funcionarios.get(row);
            String novoNome = JOptionPane.showInputDialog(null, "Editar Nome:", funcionario.getNome());
            String novaSenha = JOptionPane.showInputDialog(null, "Editar Senha:", funcionario.getSenha());
            String novoGenero = JOptionPane.showInputDialog(null, "Editar Gênero:", funcionario.getGenero());
            String novaNacionalidade = JOptionPane.showInputDialog(null, "Editar Nacionalidade:", funcionario.getNacionalidade());
            String novoCargo = (String) JOptionPane.showInputDialog(null, "Editar Cargo:", "Selecionar Cargo", JOptionPane.QUESTION_MESSAGE, null, cargos, funcionario.getCargo());
            String novoTipoDocumento = (String) JOptionPane.showInputDialog(null, "Editar Tipo de Documento:", "Selecionar Tipo de Documento", JOptionPane.QUESTION_MESSAGE, null, tiposDocumento, funcionario.getTipoDeDocumento());
            String novoNumeroDocumento = JOptionPane.showInputDialog(null, "Editar Número do Documento:", funcionario.getNumDocumento());

            if (novoNome != null && novaSenha != null && novoGenero != null && novaNacionalidade != null && novoCargo != null && novoTipoDocumento != null && novoNumeroDocumento != null) {
                funcionario.setNome(novoNome);
                funcionario.setSenha(novaSenha);
                funcionario.setTipoDeDocumento(novoTipoDocumento);
                funcionario.setNumDocumento(novoNumeroDocumento);
                funcionario.setGenero(novoGenero.charAt(0));
                funcionario.setNacionalidade(novaNacionalidade);
                funcionario.setCargo(novoCargo);

                // Atualiza a tabela com os novos dados
                tableModel.setValueAt(novoNome, row, 1);
                tableModel.setValueAt(novaSenha, row, 2);
                tableModel.setValueAt(novoTipoDocumento, row, 3);
                tableModel.setValueAt(novoNumeroDocumento, row, 4);
                tableModel.setValueAt(novoGenero, row, 5);
                tableModel.setValueAt(novaNacionalidade, row, 6);
                tableModel.setValueAt(novoCargo, row, 7);

                salvarFuncionariosEmArquivo();
            }
        }

        private void apagarFuncionario(int row) {
			int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja apagar este funcionário?", "Confirmação", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
			funcionarios.remove(row);
			tableModel.removeRow(row);
			salvarFuncionariosEmArquivo();
			}
		}
	}
}
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main extends JFrame {
    private JPanel MainPanel;
    private JLabel RelaçaoPeso;
    private JTextField peso5field;
    private JLabel peso1;
    private JLabel peso2;
    private JLabel peso3;
    private JLabel peso4;
    private JLabel peso5;
    private JButton LimpButton;
    private JButton CalcButton;
    private JButton SaveButton;
    private JLabel mediadospesos;
    private JLabel resultadomedia;
    private JTextField peso1field;
    private JTextField peso2field;
    private JTextField peso3field;
    private JTextField peso4field;
    private JLabel Amplitudedospesos;
    private JLabel resultadoamplitude;
    private JTextField responsavelField;
    private JTextField produtoField;
    private JLabel resultadoAmplitude;  // Adicione a JLabel para exibir a amplitude

    public Main() {
        setContentPane(MainPanel);
        setTitle("Relação de Peso");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(460, 490);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        // Adiciona funcionalidade ao botão "Salvar"
        SaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarDadosEmExcel();
            }
        });

        // Adiciona funcionalidade ao botão "Calcular"
        CalcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularMediaEAmplitude();
            }
        });

        // Adiciona funcionalidade ao botão "Limpar"
        LimpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
    }


    private void calcularMediaEAmplitude() {
        try {
            // Substitui vírgula por ponto e converte para double
            double peso1 = Double.parseDouble(peso1field.getText().replace(",", "."));
            double peso2 = Double.parseDouble(peso2field.getText().replace(",", "."));
            double peso3 = Double.parseDouble(peso3field.getText().replace(",", "."));
            double peso4 = Double.parseDouble(peso4field.getText().replace(",", "."));
            double peso5 = Double.parseDouble(peso5field.getText().replace(",", "."));

            // Calcula a média
            double media = (peso1 + peso2 + peso3 + peso4 + peso5) / 5;

            // Calcula a amplitude
            double[] pesos = {peso1, peso2, peso3, peso4, peso5};
            double max = Arrays.stream(pesos).max().getAsDouble();
            double min = Arrays.stream(pesos).min().getAsDouble();
            double amplitude = max - min;

            // Exibe o resultado nas labels
            resultadomedia.setText(String.format("Média: %.2f", media));
            resultadoamplitude.setText(String.format("Amplitude: %.2f", amplitude));

        } catch (NumberFormatException ex) {
            // Caso algum dos valores não seja um número válido, exibe uma mensagem de erro
            JOptionPane.showMessageDialog(this, "Por favor, insira valores numéricos válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void salvarDadosEmExcel() {
        JFileChooser fileChooser = new JFileChooser();
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return; // Usuário cancelou a operação
        }

        File fileToSave = fileChooser.getSelectedFile();
        String filePath = fileToSave.getAbsolutePath();
        if (!filePath.endsWith(".xlsx")) {
            filePath += ".xlsx"; // Adiciona a extensão se não estiver presente
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Dados de Peso");

        // Cria o cabeçalho
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Peso 1");
        headerRow.createCell(1).setCellValue("Peso 2");
        headerRow.createCell(2).setCellValue("Peso 3");
        headerRow.createCell(3).setCellValue("Peso 4");
        headerRow.createCell(4).setCellValue("Peso 5");
        headerRow.createCell(5).setCellValue("Responsável");
        headerRow.createCell(6).setCellValue("Produto");
        headerRow.createCell(7).setCellValue("Média");
        headerRow.createCell(8).setCellValue("Amplitude");

        // Adiciona os dados na segunda linha
        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue(peso1field.getText());
        dataRow.createCell(1).setCellValue(peso2field.getText());
        dataRow.createCell(2).setCellValue(peso3field.getText());
        dataRow.createCell(3).setCellValue(peso4field.getText());
        dataRow.createCell(4).setCellValue(peso5field.getText());
        dataRow.createCell(5).setCellValue(responsavelField.getText());
        dataRow.createCell(6).setCellValue(produtoField.getText());
        dataRow.createCell(7).setCellValue(resultadomedia.getText().replace("Média: ", ""));
        dataRow.createCell(8).setCellValue(resultadoamplitude.getText().replace("Amplitude: ", ""));

        // Ajusta o tamanho das colunas
        for (int i = 0; i < 9; i++) {
            sheet.autoSizeColumn(i);
        }

        // Salva o arquivo Excel
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            JOptionPane.showMessageDialog(this, "Arquivo salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao fechar o arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparCampos() {
        peso1field.setText("");
        peso2field.setText("");
        peso3field.setText("");
        peso4field.setText("");
        peso5field.setText("");
        resultadomedia.setText("");
        resultadoamplitude.setText("");
        responsavelField.setText("");
        produtoField.setText("");
    }

    public static void main(String[] args) {
        new Main();
    }
}

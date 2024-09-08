import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExportadorExcel {

    public void salvarDados(String filePath, String[] cabecalhos, String[] dados) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Dados de Peso");

        // Cria o cabeçalho
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < cabecalhos.length; i++) {
            headerRow.createCell(i).setCellValue(cabecalhos[i]);
        }

        // Adiciona os dados na segunda linha
        Row dataRow = sheet.createRow(1);
        for (int i = 0; i < dados.length; i++) {
            dataRow.createCell(i).setCellValue(dados[i]);
        }

        // Ajusta o tamanho das colunas
        for (int i = 0; i < cabecalhos.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Salva o arquivo Excel
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            JOptionPane.showMessageDialog(null, "Arquivo salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar o arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

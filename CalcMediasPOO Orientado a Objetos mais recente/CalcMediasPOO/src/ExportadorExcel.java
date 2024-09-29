import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExportadorExcel {

    public void salvarDados(String filePath, String[] cabecalhos, String[] dados) {
        Workbook workbook = null; // Inicialização da variável workbook
        Sheet sheet;

        File file = new File(filePath);
        boolean arquivoExiste = file.exists();

        // Se o arquivo já existe, abrir o arquivo, caso contrário, criar um novo
        try {
            if (arquivoExiste) {
                FileInputStream fileInputStream = new FileInputStream(file);
                workbook = WorkbookFactory.create(fileInputStream);
                sheet = workbook.getSheetAt(0);
                fileInputStream.close();
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Dados de Peso");

                // cria o cabeçalho com uma coluna extra para a hora
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < cabecalhos.length; i++) {
                    headerRow.createCell(i).setCellValue(cabecalhos[i]);
                }
                headerRow.createCell(cabecalhos.length).setCellValue("Hora"); // Adiciona a coluna de hora
            }

            // Determinar a próxima linha disponível
            int linhaDisponivel = sheet.getPhysicalNumberOfRows(); // Número de linhas preenchidas

            // Adiciona os dados na próxima linha disponível
            Row dataRow = sheet.createRow(linhaDisponivel);
            for (int i = 0; i < dados.length; i++) {
                dataRow.createCell(i).setCellValue(dados[i]);
            }

            // Adiciona a hora atual na nova coluna sem segundos
            String horaAtual = new SimpleDateFormat("HH:mm").format(new Date());
            dataRow.createCell(dados.length).setCellValue(horaAtual); // Adiciona a hora na última coluna

            // Ajusta o tamanho das colunas
            for (int i = 0; i < cabecalhos.length + 1; i++) { // +1 para a coluna de hora
                sheet.autoSizeColumn(i);
            }

            // Salva o arquivo Excel
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                JOptionPane.showMessageDialog(null, "Arquivo salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Fecha o workbook se ele foi inicializado
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Erro ao fechar o arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}

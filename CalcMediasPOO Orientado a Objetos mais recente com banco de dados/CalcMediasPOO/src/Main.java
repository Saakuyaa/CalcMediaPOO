import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
//classe main que herda do jframe pra fazer a interface grafica
public class Main extends JFrame {
    private JPanel MainPanel;
    private JLabel RelaçaoPeso;
    private JLabel mediadospesos;
    private JLabel Amplitudedospesos;
    private JLabel peso1, peso2, peso3, peso4, peso5;
    private JTextField peso1field, peso2field, peso3field, peso4field, peso5field;
    private JLabel resultadomedia, resultadoamplitude;
    private JTextField responsavelField, produtoField;
    private JButton LimpButton, CalcButton, SaveButton;
//construtor main  pra configurar a janela principal da interface
    public Main() {
        setContentPane(MainPanel);
        setTitle("Relação de Peso");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(460, 490);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
//adicionando os listeners de ação para os botoes
        SaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarDadosEmExcel();
            }
        });

        CalcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularMediaEAmplitude();
            }
        });

        LimpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
    }
//converte o (.) para (,) por ser o padrao brasileiro
    private void calcularMediaEAmplitude() {
        try {
            double peso1 = Double.parseDouble(peso1field.getText().replace(",", "."));
            double peso2 = Double.parseDouble(peso2field.getText().replace(",", "."));
            double peso3 = Double.parseDouble(peso3field.getText().replace(",", "."));
            double peso4 = Double.parseDouble(peso4field.getText().replace(",", "."));
            double peso5 = Double.parseDouble(peso5field.getText().replace(",", "."));
//Cria uma instancia de CalculadoraPeso passando os pesos e  Calcula a média e a amplitude usando calcularMedia() e calcularAmplitude()
            CalculadoraPeso calculadora = new CalculadoraPeso(new double[]{peso1, peso2, peso3, peso4, peso5});
            double media = calculadora.calcularMedia();
            double amplitude = calculadora.calcularAmplitude();

            resultadomedia.setText(String.format("Média: %.2f", media));
            resultadoamplitude.setText(String.format("Amplitude: %.2f", amplitude));
//try catch com excecao pra exibir mensagem de erro
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira valores numéricos válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
//abre a janela pra escolher o caminho que vai salvar o arquivo
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
//Cria o vetor para armazenar os cabeçalhos e os dados a serem salvos no Excel.
        String[] cabecalhos = {"Peso 1", "Peso 2", "Peso 3", "Peso 4", "Peso 5", "Responsável", "Produto", "Média", "Amplitude"};
        String[] dados = {
                peso1field.getText(), peso2field.getText(), peso3field.getText(), peso4field.getText(), peso5field.getText(),
                responsavelField.getText(), produtoField.getText(),
                resultadomedia.getText().replace("Média: ", ""),
                resultadoamplitude.getText().replace("Amplitude: ", "")
        };
// Chama o metodo salvar dados para salvar em excel
        ExportadorExcel exportador = new ExportadorExcel();
        exportador.salvarDados(filePath, cabecalhos, dados);

        // Após salvar o Excel, salvar os dados no banco de dados
        BancoDados bancoDados = new BancoDados();
        double[] pesos = {
                Double.parseDouble(peso1field.getText().replace(",", ".")),
                Double.parseDouble(peso2field.getText().replace(",", ".")),
                Double.parseDouble(peso3field.getText().replace(",", ".")),
                Double.parseDouble(peso4field.getText().replace(",", ".")),
                Double.parseDouble(peso5field.getText().replace(",", "."))
        };
        String responsavel = responsavelField.getText();
        String produto = produtoField.getText();
        String hora = java.time.LocalTime.now().toString(); // Hora atual

        bancoDados.salvarDados(pesos, responsavel, produto, hora);
    }
//limpa os campos de entrada de informaçao
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
//psvm
    public static void main(String[] args) {
        new Main();
    }
}

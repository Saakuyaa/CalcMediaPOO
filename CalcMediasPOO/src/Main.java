import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        // Adicionando a funcionalidade de calcular a média e a amplitude
        CalcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                    // Exibe o resultado na label
                    resultadomedia.setText(String.format("Média: %.2f", media));
                    resultadoamplitude.setText(String.format("Amplitude: %.2f", amplitude));

                } catch (NumberFormatException ex) {
                    // Caso algum dos valores não seja um número válido, exibe uma mensagem de erro
                    JOptionPane.showMessageDialog(Main.this, "Por favor, insira valores numéricos válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionando funcionalidade de limpar os campos de texto
        LimpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                peso1field.setText("");
                peso2field.setText("");
                peso3field.setText("");
                peso4field.setText("");
                peso5field.setText("");
                resultadomedia.setText("");
                resultadoamplitude.setText("");
                responsavelField.setText("");
                produtoField.setText("");// Limpa a label de amplitude
            }
        });
    }

    public static void main(String[] args) {
        new Main();
    }
}

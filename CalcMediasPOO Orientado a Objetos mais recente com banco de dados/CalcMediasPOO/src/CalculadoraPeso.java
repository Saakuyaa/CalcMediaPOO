import java.util.Arrays;

public class CalculadoraPeso {
    private double[] pesos;

    public CalculadoraPeso(double[] pesos) {
        this.pesos = pesos;
    }

    // calcula a media com um for e iteração i++
    public double calcularMedia() {
        double soma = 0;
        int quantidade = 0;

        for (int i = 0; i < pesos.length; i++) {
            soma += pesos[i];
            quantidade++;
        }

        return quantidade > 0 ? soma / quantidade : 0;
    }

    // calcula a amplitude com um for checando todos os pesos pra salvar o maior em max e o menor em min
    public double calcularAmplitude() {
        double max = pesos[0];
        double min = pesos[0];

        for (int i = 1; i < pesos.length; i++) {
            if (pesos[i] > max) {
                max = pesos[i];
            }
            if (pesos[i] < min) {
                min = pesos[i];
            }
        }

        return max - min; // Retorna a amplitude
    }
}
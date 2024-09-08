import java.util.Arrays;

public class CalculadoraPeso {
    private double[] pesos;

    public CalculadoraPeso(double[] pesos) {
        this.pesos = pesos;
    }

    public double calcularMedia() {
        double soma = 0;
        for (double peso : pesos) {
            soma += peso;
        }
        return soma / pesos.length;
    }

    public double calcularAmplitude() {
        double max = Arrays.stream(pesos).max().getAsDouble();
        double min = Arrays.stream(pesos).min().getAsDouble();
        return max - min;
    }
}
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BancoDados {

    private String url = "jdbc:mysql://localhost:3306/javadb"; // URL do banco de dados
    private String user = "root"; // usuário do banco de dados
    private String password = ""; // senha do banco de dados

    // metodo para salvar os dados
    public void salvarDados(double[] pesos, String responsavel, String produto, String hora) {
        String query = "INSERT INTO pesos (peso1, peso2, peso3, peso4, peso5, responsavel, produto, media, amplitude, hora) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Defina os valores dos parâmetros
            for (int i = 0; i < pesos.length; i++) {
                pstmt.setDouble(i + 1, pesos[i]); // Para os pesos
            }
            pstmt.setString(6, responsavel); // Adiciona o responsável
            pstmt.setString(7, produto); // Adiciona o produto
            pstmt.setDouble(8, calcularMedia(pesos)); // Adiciona a média
            pstmt.setDouble(9, calcularAmplitude(pesos)); // Adiciona a amplitude
            pstmt.setString(10, hora); // Adiciona a hora

            // Execute a inserção
            pstmt.executeUpdate();
            System.out.println("Dados salvos com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    // metodo para calcular a média dos pesos
    private double calcularMedia(double[] pesos) {
        double soma = 0;
        for (double peso : pesos) {
            soma += peso;
        }
        return pesos.length > 0 ? soma / pesos.length : 0;
    }

    // metodo para calcular a amplitude dos pesos
    private double calcularAmplitude(double[] pesos) {
        double max = pesos[0];
        double min = pesos[0];

        for (double peso : pesos) {
            if (peso > max) {
                max = peso;
            }
            if (peso < min) {
                min = peso;
            }
        }

        return max - min; // Retorna a amplitude
    }

    // metodo para testar a inserção de dados
    public void testeSalvarDados() {
        double[] pesos = {10.0, 12.5, 15.0, 9.5, 11.0}; // Exemplos de pesos
        String responsavel = "Prof Gilson"; // Nome do responsável
        String produto = "Produto Shampoo"; // Nome do produto
        String hora = "12:30:00"; // Hora atual (formato HH:mm:ss)

        // Chama o metodo para salvar os dados
        salvarDados(pesos, responsavel, produto, hora);
    }

    public static void main(String[] args) {
        BancoDados bancoDados = new BancoDados();
        bancoDados.testeSalvarDados(); // Testa a inserção de dados
    }
}

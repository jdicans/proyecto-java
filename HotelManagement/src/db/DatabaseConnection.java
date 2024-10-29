package HotelManagement.src.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/gestor_hotel?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "tu_contraseña";

    public static Connection connect() {
        Connection connection = null;
        try {
            // Registrar el controlador manualmente
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el controlador de MySQL: " + e.getMessage());
        }
        return connection;
    }

    public static void main(String[] args) {
        Connection connection = connect();
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}

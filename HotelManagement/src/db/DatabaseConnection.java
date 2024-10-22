package HotelManagement.src.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    public class DatabaseConnection {
        private static final String URL = "jdbc:mysql://localhost:3306/gestor_hotel";
        private static final String USER = "root"; // Usuario por defecto en XAMPP
        private static final String PASSWORD = ""; // Contraseña por defecto en XAMPP

        public static Connection connect() {
            Connection connection = null;
            try {
                // Establecer la conexión
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión exitosa a la base de datos.");
            } catch (SQLException e) {
                System.out.println("Error de conexión: " + e.getMessage());
            }
            return connection;
        }

        public static void main(String[] args) {
            Connection connection = connect(); // Probar la conexión
            if (connection != null) {
                try {
                    connection.close(); // Cerrar la conexión
                    System.out.println("Conexión cerrada.");
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }



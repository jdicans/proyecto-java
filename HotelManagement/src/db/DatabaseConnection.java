package HotelManagement.src.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL =
            "jdbc:mysql://srv1618.hstgr.io:3306/u637372565_gestor_hotel" +
                    "?autoReconnect=true&useSSL=false&connectTimeout=30000&socketTimeout=30000";
    private static final String USER = "u637372565_java";
    private static final String PASSWORD = "Proyecto_java1023456789";

    public static Connection connect() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
            return connection;
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            throw e;
        }
    }

    public static void main(String[] args) {
        try (Connection connection = connect()) {
            if (connection != null) {
                System.out.println("Conexión establecida y cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error durante la prueba de conexión: " + e.getMessage());
        }
    }
}

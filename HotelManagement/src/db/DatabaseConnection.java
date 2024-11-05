package HotelManagement.src.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/gestor_hotel"; // Cambia esto a tu base de datos
    private static final String USER = "root"; // Cambia esto a tu usuario
    private static final String PASSWORD = "Andres30!"; // Cambia esto a tu contraseña

    // Método para obtener la conexión
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    // Método para agregar un cliente
    public static void agregarCliente(String nombre, String apellidos, String email, String telefono) {
        String sql = "INSERT INTO Clientes (Nombre, Apellidos, Email, Telefono) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, apellidos);
            pstmt.setString(3, email);
            pstmt.setString(4, telefono);
            pstmt.executeUpdate();

            System.out.println("Cliente agregado: " + nombre + " " + apellidos);

        } catch (SQLException e) {
            System.err.println("Error al agregar cliente: " + e.getMessage());
        }
    }

    // Método para agregar una reserva
    public static void agregarReserva(int clienteId, int habitacionId, Date fechaEntrada, Date fechaSalida, String estado) {
        String sql = "INSERT INTO Reservas (ClienteID, HabitacionID, FechaEntrada, FechaSalida, Estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, clienteId);
            pstmt.setInt(2, habitacionId);
            pstmt.setDate(3, new java.sql.Date(fechaEntrada.getTime()));
            pstmt.setDate(4, new java.sql.Date(fechaSalida.getTime()));
            pstmt.setString(5, estado);
            pstmt.executeUpdate();

            System.out.println("Reserva agregada para el cliente ID: " + clienteId);

        } catch (SQLException e) {
            System.err.println("Error al agregar reserva: " + e.getMessage());
        }
    }

    // Método para actualizar una reserva
    public static boolean actualizarReserva(int reservaId, int clienteId, int habitacionId, Date fechaEntrada, Date fechaSalida, String estado) {
        String sql = "UPDATE Reservas SET ClienteID = ?, HabitacionID = ?, FechaEntrada = ?, FechaSalida = ?, Estado = ? WHERE ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, clienteId);
            pstmt.setInt(2, habitacionId);
            pstmt.setDate(3, new java.sql.Date(fechaEntrada.getTime()));
            pstmt.setDate(4, new java.sql.Date(fechaSalida.getTime()));
            pstmt.setString(5, estado);
            pstmt.setInt(6, reservaId);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Reserva actualizada: ID " + reservaId);
                return true; // Indicar que la operación fue exitosa
            } else {
                System.out.println("No se encontró la reserva con ID " + reservaId);
                return false; // Indicar que no se actualizó nada
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar reserva: " + e.getMessage());
            return false; // Indicar que hubo un error
        }
    }

    // Método para cargar todos los clientes desde la base de datos
    public static List<String> cargarClientes() {
        List<String> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String nombre = rs.getString("Nombre");
                String apellidos = rs.getString("Apellidos");
                clientes.add(nombre + " " + apellidos);
            }

        } catch (SQLException e) {
            System.err.println("Error al cargar clientes: " + e.getMessage());
        }
        return clientes;
    }

    // Método para cargar todas las habitaciones desde la base de datos
    public static List<String> cargarRooms() {
        List<String> rooms = new ArrayList<>();
        String sql = "SELECT NumeroHabitacion FROM Habitaciones";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                rooms.add(rs.getString("NumeroHabitacion"));
            }

        } catch (SQLException e) {
            System.err.println("Error al cargar habitaciones: " + e.getMessage());
        }
        return rooms;
    }

    // Método para obtener el ID del cliente basado en el nombre completo
    public static int obtenerClienteID(String nombreCompleto) {
        String sql = "SELECT ID FROM Clientes WHERE CONCAT(Nombre, ' ', Apellidos) = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreCompleto);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ID de cliente: " + e.getMessage());
        }
        return -1; // Indica que no se encontró el cliente
    }

    // Método para obtener el ID de la habitación basada en el número
    public static int obtenerHabitacionID(String numeroHabitacion) {
        String sql = "SELECT ID FROM Habitaciones WHERE NumeroHabitacion = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, numeroHabitacion);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ID de habitación: " + e.getMessage());
        }
        return -1; // Indica que no se encontró la habitación
    }

    // Método para agregar una habitación
    public static boolean agregarHabitacion(String numeroHabitacion, String tipo, String estado) {
        String sql = "INSERT INTO Habitaciones (NumeroHabitacion, Tipo, Estado) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, numeroHabitacion);
            pstmt.setString(2, tipo);
            pstmt.setString(3, estado);
            pstmt.executeUpdate();

            System.out.println("Habitación agregada: " + numeroHabitacion);
            return true; // Indicar que la operación fue exitosa

        } catch (SQLException e) {
            System.err.println("Error al agregar habitación: " + e.getMessage());
            return false; // Indicar que hubo un error
        }
    }
}

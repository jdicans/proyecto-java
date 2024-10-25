package HotelManagement.src.views;

import HotelManagement.src.db.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Reservation extends JFrame {

    private JComboBox<String> cmbCliente;
    private JComboBox<String> cmbHabitacion;
    private JSpinner spinnerFechaEntrada;
    private JSpinner spinnerFechaSalida;
    private JComboBox<String> cmbEstado;
    private JTextArea txtAreaResultado;

    public Reservation() {
        setTitle("Agregar Reserva");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        // Crear panel de formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(6, 2));

        panelFormulario.add(new JLabel("Cliente:"));
        cmbCliente = new JComboBox<>();
        cargarClientes(); // Cargar clientes desde la base de datos
        panelFormulario.add(cmbCliente);

        panelFormulario.add(new JLabel("Habitación:"));
        cmbHabitacion = new JComboBox<>();
        cargarHabitaciones(); // Cargar habitaciones desde la base de datos
        panelFormulario.add(cmbHabitacion);

        panelFormulario.add(new JLabel("Fecha Entrada:"));
        spinnerFechaEntrada = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditorEntrada = new JSpinner.DateEditor(spinnerFechaEntrada, "dd/MM/yyyy");
        spinnerFechaEntrada.setEditor(dateEditorEntrada);
        panelFormulario.add(spinnerFechaEntrada);

        panelFormulario.add(new JLabel("Fecha Salida:"));
        spinnerFechaSalida = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditorSalida = new JSpinner.DateEditor(spinnerFechaSalida, "dd/MM/yyyy");
        spinnerFechaSalida.setEditor(dateEditorSalida);
        panelFormulario.add(spinnerFechaSalida);

        panelFormulario.add(new JLabel("Estado:"));
        String[] estados = {"Activa", "Cancelada", "Completada"};
        cmbEstado = new JComboBox<>(estados);
        panelFormulario.add(cmbEstado);

        JButton btnAgregar = new JButton("Agregar Reserva");
        panelFormulario.add(btnAgregar);

        // Área de resultado
        txtAreaResultado = new JTextArea();
        txtAreaResultado.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaResultado);

        // Configurar el contenedor principal
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(panelFormulario, BorderLayout.CENTER);
        container.add(scrollPane, BorderLayout.SOUTH);

        // Acción del botón
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarReserva();
            }
        });
    }

    private void cargarClientes() {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT CONCAT(Nombre, ' ', Apellidos) AS Cliente, ID FROM Clientes";
            try (PreparedStatement pstmt = connection.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cmbCliente.addItem(rs.getString("Cliente") + " (ID: " + rs.getString("ID") + ")"); // Cargar nombres de clientes
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage());
        }
    }

    private void cargarHabitaciones() {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT NumeroHabitacion, ID FROM Habitaciones WHERE Estado = 'disponible'";
            try (PreparedStatement pstmt = connection.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cmbHabitacion.addItem(rs.getString("NumeroHabitacion") + " (ID: " + rs.getString("ID") + ")"); // Cargar habitaciones disponibles
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar habitaciones: " + e.getMessage());
        }
    }

    private void agregarReserva() {
        // Obtener el ID del cliente y de la habitación seleccionados
        String clienteSeleccionado = (String) cmbCliente.getSelectedItem();
        String habitacionSeleccionada = (String) cmbHabitacion.getSelectedItem();
        String clienteID = clienteSeleccionado.split(" \\(ID: ")[1].replace(")", ""); // Obtener ID del cliente
        String habitacionID = habitacionSeleccionada.split(" \\(ID: ")[1].replace(")", ""); // Obtener ID de la habitación

        Date fechaEntrada = (Date) spinnerFechaEntrada.getValue();
        Date fechaSalida = (Date) spinnerFechaSalida.getValue();
        String estado = (String) cmbEstado.getSelectedItem();

        try (Connection connection = DatabaseConnection.connect()) {
            String query = "INSERT INTO Reservas (ClienteID, HabitacionID, FechaEntrada, FechaSalida, Estado) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, clienteID);
                pstmt.setString(2, habitacionID);
                pstmt.setDate(3, new java.sql.Date(fechaEntrada.getTime())); // Convertir Date a java.sql.Date
                pstmt.setDate(4, new java.sql.Date(fechaSalida.getTime())); // Convertir Date a java.sql.Date
                pstmt.setString(5, estado);

                pstmt.executeUpdate(); // Ejecutar la inserción
                JOptionPane.showMessageDialog(this, "Reserva agregada con éxito.");

                // Mostrar el resultado en el área de texto
                txtAreaResultado.append("Reserva agregada:\n"
                        + "Cliente: " + clienteSeleccionado + "\n"
                        + "Habitación: " + habitacionSeleccionada + "\n"
                        + "Fecha Entrada: " + fechaEntrada + "\n"
                        + "Fecha Salida: " + fechaSalida + "\n"
                        + "Estado: " + estado + "\n\n");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar reserva: " + e.getMessage());
        }

        // Limpiar los campos
        cmbCliente.setSelectedIndex(0);
        cmbHabitacion.setSelectedIndex(0);
        spinnerFechaEntrada.setValue(new Date());
        spinnerFechaSalida.setValue(new Date());
        cmbEstado.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Reservation().setVisible(true);
            }
        });
    }
}

package HotelManagement.src.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Reservation extends JFrame {

    private JComboBox<String> cmbCliente;
    private JComboBox<String> cmbHabitacion;
    private JSpinner spinnerFechaEntrada;
    private JSpinner spinnerFechaSalida;
    private JComboBox<String> cmbEstado;
    private JTextArea txtAreaResultado;

    public Reservation() {
        setTitle("Agregar Reserva");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        // Cambiar el color de fondo
        getContentPane().setBackground(new Color(250, 250, 250));

        // Crear panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(240, 240, 240));

        // Crear y configurar el título
        JLabel lblTitulo = new JLabel("Agregar Reserva", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(51, 102, 153));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Crear panel de formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(6, 2, 10, 10)); // Espacio entre celdas
        panelFormulario.setBackground(new Color(240, 240, 240)); // Color de fondo del panel

        panelFormulario.add(new JLabel("Cliente:"));
        cmbCliente = new JComboBox<>();
        cargarClientes(); // Llenar el JComboBox de clientes
        panelFormulario.add(cmbCliente);

        panelFormulario.add(new JLabel("Habitación:"));
        cmbHabitacion = new JComboBox<>();
        cargarHabitaciones(); // Llenar el JComboBox de habitaciones
        panelFormulario.add(cmbHabitacion);

        panelFormulario.add(new JLabel("Fecha Entrada:"));
        spinnerFechaEntrada = createDateSpinner();
        panelFormulario.add(spinnerFechaEntrada);

        panelFormulario.add(new JLabel("Fecha Salida:"));
        spinnerFechaSalida = createDateSpinner();
        panelFormulario.add(spinnerFechaSalida);

        panelFormulario.add(new JLabel("Estado:"));
        String[] estados = {"activa", "cancelada", "completada"};
        cmbEstado = new JComboBox<>(estados);
        panelFormulario.add(cmbEstado);

        // Crear panel para el botón y centrarlo
        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(240, 240, 240));
        JButton btnAgregar = new JButton("Agregar Reserva");
        btnAgregar.setBackground(new Color(51, 153, 255)); // Color de fondo del botón
        btnAgregar.setForeground(Color.WHITE); // Color del texto del botón
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 14));
        panelBoton.add(btnAgregar); // Agregar el botón centrado en el panel
        panelFormulario.add(panelBoton);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // Área de resultado
        txtAreaResultado = new JTextArea();
        txtAreaResultado.setEditable(false);
        txtAreaResultado.setBackground(Color.WHITE);
        txtAreaResultado.setForeground(new Color(51, 51, 51));
        txtAreaResultado.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(txtAreaResultado);

        // Configurar el contenedor principal
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(panelPrincipal, BorderLayout.CENTER);
        container.add(scrollPane, BorderLayout.SOUTH);

        // Acción del botón
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarReserva();
            }
        });
    }

    private JSpinner createDateSpinner() {
        JSpinner spinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(dateEditor);
        spinner.setValue(new Date()); // Establecer fecha actual como valor predeterminado
        return spinner;
    }

    private void cargarClientes() {
        try (Connection conn = HotelManagement.src.db.DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT Nombre, Apellidos FROM Clientes");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String cliente = rs.getString("Nombre") + " " + rs.getString("Apellidos");
                cmbCliente.addItem(cliente);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarHabitaciones() {
        try {
            List<String> habitaciones = HotelManagement.src.db.DatabaseConnection.cargarRooms();
            for (String habitacion : habitaciones) {
                cmbHabitacion.addItem(habitacion);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar habitaciones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarReserva() {
        String cliente = (String) cmbCliente.getSelectedItem();
        String habitacion = (String) cmbHabitacion.getSelectedItem();
        Date fechaEntrada = (Date) spinnerFechaEntrada.getValue();
        Date fechaSalida = (Date) spinnerFechaSalida.getValue();
        String estado = (String) cmbEstado.getSelectedItem();

        if (fechaSalida.before(fechaEntrada)) {
            JOptionPane.showMessageDialog(this, "La fecha de salida debe ser posterior a la fecha de entrada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        guardarReserva(cliente, habitacion, fechaEntrada, fechaSalida, estado);

        txtAreaResultado.append("Reserva agregada:\n"
                + "Cliente: " + cliente + "\n"
                + "Habitación: " + habitacion + "\n"
                + "Fecha Entrada: " + fechaEntrada + "\n"
                + "Fecha Salida: " + fechaSalida + "\n"
                + "Estado: " + estado + "\n\n");

        cmbCliente.setSelectedIndex(0);
        cmbHabitacion.setSelectedIndex(0);
        spinnerFechaEntrada.setValue(new Date());
        spinnerFechaSalida.setValue(new Date());
        cmbEstado.setSelectedIndex(0);
    }

    private void guardarReserva(String cliente, String habitacion, Date fechaEntrada, Date fechaSalida, String estado) {
        try (Connection conn = HotelManagement.src.db.DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Reservas (ClienteID, HabitacionID, FechaEntrada, FechaSalida, Estado) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                int clienteID = obtenerClienteID(cliente);
                int habitacionID = obtenerHabitacionID(habitacion);

                pstmt.setInt(1, clienteID);
                pstmt.setInt(2, habitacionID);
                pstmt.setDate(3, new java.sql.Date(fechaEntrada.getTime()));
                pstmt.setDate(4, new java.sql.Date(fechaSalida.getTime()));
                pstmt.setString(5, estado);

                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar reserva: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int obtenerClienteID(String cliente) {
        try (Connection conn = HotelManagement.src.db.DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT ID FROM Clientes WHERE CONCAT(Nombre, ' ', Apellidos) = ?")) {
            stmt.setString(1, cliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener ID de cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return -1;
    }

    private int obtenerHabitacionID(String habitacion) {
        try (Connection conn = HotelManagement.src.db.DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT ID FROM Habitaciones WHERE NumeroHabitacion = ?")) {
            stmt.setString(1, habitacion);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener ID de habitación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Reservation().setVisible(true));
    }
}

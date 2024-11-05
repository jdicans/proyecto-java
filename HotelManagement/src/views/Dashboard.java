package HotelManagement.src.views;

import HotelManagement.src.db.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Dashboard extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTable tableClientes;
    private JTable tableRooms;
    private JTable tableReservas;

    public Dashboard() {
        setTitle("Gestión Hotelera");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializar el CardLayout y el panel principal
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Crear menú
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);

        // Crear los paneles principales
        mainPanel.add(crearPanelClientes(), "Clientes");
        mainPanel.add(crearPanelRooms(), "Habitaciones");
        mainPanel.add(crearPanelReservas(), "Reservas");

        // Panel de botones de navegación
        JPanel buttonPanel = createNavigationPanel();

        // Organizar el layout principal
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(buttonPanel, BorderLayout.NORTH);
        container.add(mainPanel, BorderLayout.CENTER);

        // Cargar datos iniciales
        cargarDatos();
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        JMenu menuAyuda = new JMenu("Ayuda");

        JMenuItem itemInicio = new JMenuItem("Inicio");
        JMenuItem itemAgregarCliente = new JMenuItem("Agregar Cliente");
        JMenuItem itemAgregarRoom = new JMenuItem("Agregar Room");
        JMenuItem itemAgregarReserva = new JMenuItem("Agregar Reserva");
        JMenuItem itemAcercaDe = new JMenuItem("Acerca de");

        menuArchivo.add(itemInicio);
        menuArchivo.add(itemAgregarCliente);
        menuArchivo.add(itemAgregarRoom);
        menuArchivo.add(itemAgregarReserva);
        menuAyuda.add(itemAcercaDe);

        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);

        return menuBar;
    }

    private JPanel createNavigationPanel() {
        JPanel buttonPanel = new JPanel();
        JButton btnClientes = new JButton("Clientes");
        JButton btnHabitaciones = new JButton("Habitaciones");
        JButton btnReservas = new JButton("Reservas");

        btnClientes.addActionListener(e -> cardLayout.show(mainPanel, "Clientes"));
        btnHabitaciones.addActionListener(e -> cardLayout.show(mainPanel, "Habitaciones"));
        btnReservas.addActionListener(e -> cardLayout.show(mainPanel, "Reservas"));

        buttonPanel.add(btnClientes);
        buttonPanel.add(btnHabitaciones);
        buttonPanel.add(btnReservas);

        return buttonPanel;
    }

    // Los métodos crearPanelClientes, crearPanelRooms, crearPanelReservas permanecen igual que en tu segundo código
    private JPanel crearPanelClientes() {
        JPanel panelClientes = new JPanel(new BorderLayout());
        DefaultTableModel modeloClientes = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellidos", "Email", "Telefono"}, 0);
        tableClientes = new JTable(modeloClientes);
        JScrollPane scrollClientes = new JScrollPane(tableClientes);

        JPanel panelBotonesClientes = new JPanel();
        JButton btnActualizarCliente = new JButton("Actualizar Cliente");
        JButton btnEliminarCliente = new JButton("Eliminar Cliente");

        btnEliminarCliente.addActionListener(e -> eliminarRegistro("Clientes", tableClientes, modeloClientes));
        btnActualizarCliente.addActionListener(e -> actualizarCliente(modeloClientes));

        panelBotonesClientes.add(btnActualizarCliente);
        panelBotonesClientes.add(btnEliminarCliente);

        panelClientes.add(new JLabel("Clientes", SwingConstants.CENTER), BorderLayout.NORTH);
        panelClientes.add(scrollClientes, BorderLayout.CENTER);
        panelClientes.add(panelBotonesClientes, BorderLayout.SOUTH);

        return panelClientes;
    }

    private JPanel crearPanelRooms() {
        JPanel panelRooms = new JPanel(new BorderLayout());
        DefaultTableModel modeloRooms = new DefaultTableModel(new String[]{"ID", "Número Habitación", "Tipo", "Estado"}, 0);
        tableRooms = new JTable(modeloRooms);
        JScrollPane scrollRooms = new JScrollPane(tableRooms);

        JPanel panelBotonesRooms = new JPanel();
        JButton btnActualizarRoom = new JButton("Actualizar Room");
        JButton btnEliminarRoom = new JButton("Eliminar Room");

        btnEliminarRoom.addActionListener(e -> eliminarRegistro("Habitaciones", tableRooms, modeloRooms));
        btnActualizarRoom.addActionListener(e -> actualizarRoom(modeloRooms));

        panelBotonesRooms.add(btnActualizarRoom);
        panelBotonesRooms.add(btnEliminarRoom);

        panelRooms.add(new JLabel("Habitaciones", SwingConstants.CENTER), BorderLayout.NORTH);
        panelRooms.add(scrollRooms, BorderLayout.CENTER);
        panelRooms.add(panelBotonesRooms, BorderLayout.SOUTH);

        cargarHabitaciones(modeloRooms); // Cargar habitaciones al iniciar el panel

        return panelRooms;
    }

    private JPanel crearPanelReservas() {
        JPanel panelReservas = new JPanel(new BorderLayout());
        DefaultTableModel modeloReservas = new DefaultTableModel(new String[]{"ID", "Cliente", "Habitación", "Fecha Entrada", "Fecha Salida", "Estado"}, 0);
        tableReservas = new JTable(modeloReservas);
        JScrollPane scrollReservas = new JScrollPane(tableReservas);

        JPanel panelBotonesReservas = new JPanel();
        JButton btnActualizarReserva = new JButton("Actualizar Reserva");
        JButton btnEliminarReserva = new JButton("Eliminar Reserva");

        btnEliminarReserva.addActionListener(e -> eliminarRegistro("Reservas", tableReservas, modeloReservas));
        btnActualizarReserva.addActionListener(e -> actualizarReserva(modeloReservas));

        panelBotonesReservas.add(btnActualizarReserva);
        panelBotonesReservas.add(btnEliminarReserva);

        panelReservas.add(new JLabel("Reservas", SwingConstants.CENTER), BorderLayout.NORTH);
        panelReservas.add(scrollReservas, BorderLayout.CENTER);
        panelReservas.add(panelBotonesReservas, BorderLayout.SOUTH);

        cargarReservas(modeloReservas); // Cargar reservas al iniciar el panel

        return panelReservas;
    }

    private void cargarClientes(DefaultTableModel modelo) {
        modelo.setRowCount(0);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id, Nombre, Apellidos, Email, Telefono FROM Clientes");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getString("id"),
                        rs.getString("Nombre"),
                        rs.getString("Apellidos"),
                        rs.getString("Email"),
                        rs.getString("Telefono")
                };
                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarHabitaciones(DefaultTableModel modelo) {
        modelo.setRowCount(0);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id, NumeroHabitacion, Tipo, Estado FROM Habitaciones");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getString("id"),
                        rs.getString("NumeroHabitacion"),
                        rs.getString("Tipo"),
                        rs.getString("Estado")
                };
                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar habitaciones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarReservas(DefaultTableModel modelo) {
        modelo.setRowCount(0);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT r.id, CONCAT(c.Nombre, ' ', c.Apellidos) AS Cliente, h.NumeroHabitacion, r.FechaEntrada, r.FechaSalida, r.Estado FROM Reservas r JOIN Clientes c ON r.ClienteID = c.id JOIN Habitaciones h ON r.HabitacionID = h.id");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getString("id"),
                        rs.getString("Cliente"),
                        rs.getString("NumeroHabitacion"),
                        rs.getDate("FechaEntrada"),
                        rs.getDate("FechaSalida"),
                        rs.getString("Estado")
                };
                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar reservas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarRegistro(String tabla, JTable tablaDatos, DefaultTableModel modelo) {
        int filaSeleccionada = tablaDatos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String id = tablaDatos.getValueAt(filaSeleccionada, 0).toString();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + tabla + " WHERE id = ?")) {

            stmt.setString(1, id);
            stmt.executeUpdate();
            modelo.removeRow(filaSeleccionada);
            JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar registro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarCliente(DefaultTableModel modelo) {
        int filaSeleccionada = tableClientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = modelo.getValueAt(filaSeleccionada, 0).toString();
        String nombreActual = modelo.getValueAt(filaSeleccionada, 1).toString();
        String apellidosActual = modelo.getValueAt(filaSeleccionada, 2).toString();
        String emailActual = modelo.getValueAt(filaSeleccionada, 3).toString();
        String telefonoActual = modelo.getValueAt(filaSeleccionada, 4).toString();

        JTextField campoNombre = new JTextField(nombreActual);
        JTextField campoApellidos = new JTextField(apellidosActual);
        JTextField campoEmail = new JTextField(emailActual);
        JTextField campoTelefono = new JTextField(telefonoActual);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Nombre:"));
        panel.add(campoNombre);
        panel.add(new JLabel("Apellidos:"));
        panel.add(campoApellidos);
        panel.add(new JLabel("Email:"));
        panel.add(campoEmail);
        panel.add(new JLabel("Teléfono:"));
        panel.add(campoTelefono);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Actualizar Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("UPDATE Clientes SET Nombre = ?, Apellidos = ?, Email = ?, Telefono = ? WHERE id = ?")) {

                stmt.setString(1, campoNombre.getText());
                stmt.setString(2, campoApellidos.getText());
                stmt.setString(3, campoEmail.getText());
                stmt.setString(4, campoTelefono.getText());
                stmt.setString(5, id);
                stmt.executeUpdate();

                // Actualizar la tabla
                modelo.setValueAt(campoNombre.getText(), filaSeleccionada, 1);
                modelo.setValueAt(campoApellidos.getText(), filaSeleccionada, 2);
                modelo.setValueAt(campoEmail.getText(), filaSeleccionada, 3);
                modelo.setValueAt(campoTelefono.getText(), filaSeleccionada, 4);

                JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarRoom(DefaultTableModel modelo) {
        int filaSeleccionada = tableRooms.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una habitación para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = modelo.getValueAt(filaSeleccionada, 0).toString();
        String numeroHabitacionActual = modelo.getValueAt(filaSeleccionada, 1).toString();
        String tipoActual = modelo.getValueAt(filaSeleccionada, 2).toString();
        String estadoActual = modelo.getValueAt(filaSeleccionada, 3).toString();

        JTextField campoNumeroHabitacion = new JTextField(numeroHabitacionActual);

        // Crear JComboBox para el tipo de habitación
        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Simple", "Doble", "Suite"}); // Agrega aquí los tipos de habitaciones que necesites
        comboTipo.setSelectedItem(tipoActual); // Seleccionar el tipo actual

        JComboBox<String> comboEstado = new JComboBox<>(new String[]{"Disponible", "Ocupada", "Reservada"});
        comboEstado.setSelectedItem(estadoActual);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Número Habitación:"));
        panel.add(campoNumeroHabitacion);
        panel.add(new JLabel("Tipo:"));
        panel.add(comboTipo); // Reemplazar el JTextField por el JComboBox
        panel.add(new JLabel("Estado:"));
        panel.add(comboEstado);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Actualizar Habitación", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("UPDATE Habitaciones SET NumeroHabitacion = ?, Tipo = ?, Estado = ? WHERE id = ?")) {

                stmt.setString(1, campoNumeroHabitacion.getText());
                stmt.setString(2, comboTipo.getSelectedItem().toString()); // Obtener el tipo seleccionado
                stmt.setString(3, comboEstado.getSelectedItem().toString());
                stmt.setString(4, id);
                stmt.executeUpdate();

                // Actualizar la tabla
                modelo.setValueAt(campoNumeroHabitacion.getText(), filaSeleccionada, 1);
                modelo.setValueAt(comboTipo.getSelectedItem().toString(), filaSeleccionada, 2); // Actualizar el tipo
                modelo.setValueAt(comboEstado.getSelectedItem().toString(), filaSeleccionada, 3);

                JOptionPane.showMessageDialog(this, "Habitación actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar habitación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarReserva(DefaultTableModel modelo) {
        int filaSeleccionada = tableReservas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = modelo.getValueAt(filaSeleccionada, 0).toString();
        JComboBox<String> comboCliente = new JComboBox<>();
        JComboBox<String> comboHabitacion = new JComboBox<>();
        JSpinner fechaEntradaSpinner = createDateSpinner();
        JSpinner fechaSalidaSpinner = createDateSpinner();
        JComboBox<String> comboEstado = new JComboBox<>(new String[]{"Activa", "Cancelada", "Completada"});

        // Cargar clientes y habitaciones
        cargarClientesCombo(comboCliente);
        cargarHabitacionesCombo(comboHabitacion);

        // Rellenar los campos actuales
        String clienteActual = modelo.getValueAt(filaSeleccionada, 1).toString();
        comboCliente.setSelectedItem(clienteActual);
        String habitacionActual = modelo.getValueAt(filaSeleccionada, 2).toString();
        comboHabitacion.setSelectedItem(habitacionActual);
        java.util.Date fechaEntradaActual = (java.util.Date) modelo.getValueAt(filaSeleccionada, 3);
        java.util.Date fechaSalidaActual = (java.util.Date) modelo.getValueAt(filaSeleccionada, 4);
        fechaEntradaSpinner.setValue(fechaEntradaActual);
        fechaSalidaSpinner.setValue(fechaSalidaActual);

        // Crear un panel para el diálogo
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Cliente:"));
        panel.add(comboCliente);
        panel.add(new JLabel("Habitación:"));
        panel.add(comboHabitacion);
        panel.add(new JLabel("Fecha Entrada:"));
        panel.add(fechaEntradaSpinner);
        panel.add(new JLabel("Fecha Salida:"));
        panel.add(fechaSalidaSpinner);
        panel.add(new JLabel("Estado:"));
        panel.add(comboEstado);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Actualizar Reserva", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("UPDATE Reservas SET ClienteID = ?, HabitacionID = ?, FechaEntrada = ?, FechaSalida = ?, Estado = ? WHERE id = ?")) {

                stmt.setString(1, obtenerClienteID(comboCliente.getSelectedItem().toString())); // ID del cliente
                stmt.setString(2, obtenerHabitacionID(comboHabitacion.getSelectedItem().toString())); // ID de la habitación
                stmt.setDate(3, new java.sql.Date(((java.util.Date) fechaEntradaSpinner.getValue()).getTime()));
                stmt.setDate(4, new java.sql.Date(((java.util.Date) fechaSalidaSpinner.getValue()).getTime()));
                stmt.setString(5, comboEstado.getSelectedItem().toString());
                stmt.setString(6, id);
                stmt.executeUpdate();

                // Actualizar la tabla
                modelo.setValueAt(comboCliente.getSelectedItem().toString(), filaSeleccionada, 1);
                modelo.setValueAt(comboHabitacion.getSelectedItem().toString(), filaSeleccionada, 2);
                modelo.setValueAt(fechaEntradaSpinner.getValue(), filaSeleccionada, 3);
                modelo.setValueAt(fechaSalidaSpinner.getValue(), filaSeleccionada, 4);
                modelo.setValueAt(comboEstado.getSelectedItem().toString(), filaSeleccionada, 5);

                JOptionPane.showMessageDialog(this, "Reserva actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar reserva: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarClientesCombo(JComboBox<String> combo) {
        combo.removeAllItems();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT CONCAT(Nombre, ' ', Apellidos) AS Cliente FROM Clientes");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                combo.addItem(rs.getString("Cliente"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarHabitacionesCombo(JComboBox<String> combo) {
        combo.removeAllItems();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT NumeroHabitacion FROM Habitaciones");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                combo.addItem(rs.getString("NumeroHabitacion"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar habitaciones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String obtenerClienteID(String cliente) {
        String clienteID = "";
        // La cadena " " es utilizada para separar el nombre y apellidos
        String[] partesNombre = cliente.split(" ");
        String nombre = partesNombre[0]; // Suponiendo que el nombre es la primera parte
        String apellidos = partesNombre.length > 1 ? partesNombre[1] : ""; // El apellido es la segunda parte (opcional)

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM Clientes WHERE Nombre = ? AND Apellidos = ?")) {

            stmt.setString(1, nombre);
            stmt.setString(2, apellidos);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                clienteID = rs.getString("id");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener ID del cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return clienteID;
    }

    private String obtenerHabitacionID(String numeroHabitacion) {
        String habitacionID = "";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM Habitaciones WHERE NumeroHabitacion = ?")) {

            stmt.setString(1, numeroHabitacion);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                habitacionID = rs.getString("id");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener ID de la habitación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return habitacionID;
    }


    private JSpinner createDateSpinner() {
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        return dateSpinner;
    }

    private void cargarDatos() {
        // Este método se llamará al inicio para cargar los datos
        DefaultTableModel modeloClientes = (DefaultTableModel) tableClientes.getModel();
        DefaultTableModel modeloRooms = (DefaultTableModel) tableRooms.getModel();
        DefaultTableModel modeloReservas = (DefaultTableModel) tableReservas.getModel();

        cargarClientes(modeloClientes);
        cargarHabitaciones(modeloRooms);
        cargarReservas(modeloReservas);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Dashboard dashboard = new Dashboard();
            dashboard.setVisible(true);
        });
    }
}
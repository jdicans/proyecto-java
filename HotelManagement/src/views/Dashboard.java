package HotelManagement.src.views;

import HotelManagement.src.db.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dashboard extends JFrame {
    private JTable tableClientes;
    private JTable tableHabitaciones;
    private JTable tableReservas;

    public Dashboard() {
        setTitle("Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        // Crear barra de menú
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");
        JMenu helpMenu = new JMenu("Ayuda");

        JMenuItem addClientItem = new JMenuItem("Agregar Cliente");
        JMenuItem addRoomItem = new JMenuItem("Agregar Habitación");
        JMenuItem viewReservationsItem = new JMenuItem("Agregar Reserva");
        JMenuItem aboutItem = new JMenuItem("Acerca de");
        JMenuItem homeItem = new JMenuItem("Inicio");

        fileMenu.add(homeItem);
        fileMenu.add(addClientItem);
        fileMenu.add(addRoomItem);
        fileMenu.add(viewReservationsItem);
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(3, 1));

        // Crear modelo y tabla para clientes
        DefaultTableModel modelClientes = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellidos", "Email", "Teléfono"}, 0);
        tableClientes = new JTable(modelClientes);
        JScrollPane scrollClientes = new JScrollPane(tableClientes);
        JPanel panelClientes = new JPanel(new BorderLayout());
        panelClientes.add(new JLabel("Clientes"), BorderLayout.NORTH);
        panelClientes.add(scrollClientes, BorderLayout.CENTER);

        // Botones para clientes
        JPanel clienteButtons = new JPanel();
        JButton btnUpdateClient = new JButton("Actualizar Cliente");
        JButton btnDeleteClient = new JButton("Eliminar Cliente");
        clienteButtons.add(btnUpdateClient);
        clienteButtons.add(btnDeleteClient);
        panelClientes.add(clienteButtons, BorderLayout.SOUTH);
        panelPrincipal.add(panelClientes);

        // Crear modelo y tabla para habitaciones
        DefaultTableModel modelHabitaciones = new DefaultTableModel(new String[]{"ID", "Número de Habitación", "Tipo", "Estado"}, 0);
        tableHabitaciones = new JTable(modelHabitaciones);
        JScrollPane scrollHabitaciones = new JScrollPane(tableHabitaciones);
        JPanel panelHabitaciones = new JPanel(new BorderLayout());
        panelHabitaciones.add(new JLabel("Habitaciones"), BorderLayout.NORTH);
        panelHabitaciones.add(scrollHabitaciones, BorderLayout.CENTER);

        // Botones para habitaciones
        JPanel habitacionButtons = new JPanel();
        JButton btnUpdateRoom = new JButton("Actualizar Habitación");
        JButton btnDeleteRoom = new JButton("Eliminar Habitación");
        habitacionButtons.add(btnUpdateRoom);
        habitacionButtons.add(btnDeleteRoom);
        panelHabitaciones.add(habitacionButtons, BorderLayout.SOUTH);
        panelPrincipal.add(panelHabitaciones);

        // Crear modelo y tabla para reservas
        DefaultTableModel modelReservas = new DefaultTableModel(new String[]{"ID", "Cliente", "Habitación", "Fecha Entrada", "Fecha Salida", "Estado"}, 0);
        tableReservas = new JTable(modelReservas);
        JScrollPane scrollReservas = new JScrollPane(tableReservas);
        JPanel panelReservas = new JPanel(new BorderLayout());
        panelReservas.add(new JLabel("Reservas"), BorderLayout.NORTH);
        panelReservas.add(scrollReservas, BorderLayout.CENTER);

        // Botones para reservas
        JPanel reservaButtons = new JPanel();
        JButton btnUpdateReservation = new JButton("Actualizar Reserva");
        JButton btnDeleteReservation = new JButton("Eliminar Reserva");
        reservaButtons.add(btnUpdateReservation);
        reservaButtons.add(btnDeleteReservation);
        panelReservas.add(reservaButtons, BorderLayout.SOUTH);
        panelPrincipal.add(panelReservas);

        // Crear panel de navegación
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton btnHome = new JButton("Inicio");
        JButton btnClientes = new JButton("Clientes");
        JButton btnReservas = new JButton("Reservas");
        JButton btnHabitaciones = new JButton("Habitaciones");

        buttonPanel.add(btnHome);
        buttonPanel.add(btnClientes);
        buttonPanel.add(btnReservas);
        buttonPanel.add(btnHabitaciones);

        // Configurar el contenedor principal
        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);

        // Acciones de los botones de navegación
        btnHome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new HotelManager().setVisible(true);
                dispose(); // Cierra el Dashboard actual
            }
        });

        btnClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Client().setVisible(true); // Abre el formulario de agregar cliente
            }
        });

        btnReservas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Reservation().setVisible(true); // Abre el formulario de agregar reserva
            }
        });

        btnHabitaciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Room().setVisible(true); // Abre el formulario de agregar habitación
            }
        });

        // Inicializar el menú de ayuda
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Gestor Hotelero\nVersión 1.0", "Acerca de", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Cargar los datos al inicializar el Dashboard
        cargarDatos();

        // Acciones de botones de actualización y eliminación
        btnUpdateClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableClientes.getSelectedRow();
                if (selectedRow >= 0) {
                    // Aquí debes implementar la lógica para actualizar el cliente
                    int clienteID = (int) modelClientes.getValueAt(selectedRow, 0);
                    String nuevoNombre = JOptionPane.showInputDialog(Dashboard.this, "Nuevo Nombre", modelClientes.getValueAt(selectedRow, 1));
                    String nuevosApellidos = JOptionPane.showInputDialog(Dashboard.this, "Nuevos Apellidos", modelClientes.getValueAt(selectedRow, 2));
                    String nuevoEmail = JOptionPane.showInputDialog(Dashboard.this, "Nuevo Email", modelClientes.getValueAt(selectedRow, 3));
                    String nuevoTelefono = JOptionPane.showInputDialog(Dashboard.this, "Nuevo Teléfono", modelClientes.getValueAt(selectedRow, 4));

                    // Lógica para actualizar en la base de datos
                    actualizarCliente(clienteID, nuevoNombre, nuevosApellidos, nuevoEmail, nuevoTelefono);

                    // Actualizar en la tabla
                    modelClientes.setValueAt(nuevoNombre, selectedRow, 1);
                    modelClientes.setValueAt(nuevosApellidos, selectedRow, 2);
                    modelClientes.setValueAt(nuevoEmail, selectedRow, 3);
                    modelClientes.setValueAt(nuevoTelefono, selectedRow, 4);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un cliente para actualizar.");
                }
            }
        });

        btnDeleteClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableClientes.getSelectedRow();
                if (selectedRow >= 0) {
                    int clienteID = (int) modelClientes.getValueAt(selectedRow, 0);
                    eliminarCliente(clienteID);
                    modelClientes.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un cliente para eliminar.");
                }
            }
        });

        btnUpdateRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableHabitaciones.getSelectedRow();
                if (selectedRow >= 0) {
                    // Aquí debes implementar la lógica para actualizar la habitación
                    int habitacionID = (int) modelHabitaciones.getValueAt(selectedRow, 0);
                    String nuevoNumero = JOptionPane.showInputDialog(Dashboard.this, "Nuevo Número de Habitación", modelHabitaciones.getValueAt(selectedRow, 1));
                    String nuevoTipo = JOptionPane.showInputDialog(Dashboard.this, "Nuevo Tipo", modelHabitaciones.getValueAt(selectedRow, 2));
                    String nuevoEstado = JOptionPane.showInputDialog(Dashboard.this, "Nuevo Estado", modelHabitaciones.getValueAt(selectedRow, 3));

                    // Lógica para actualizar en la base de datos
                    actualizarHabitacion(habitacionID, nuevoNumero, nuevoTipo, nuevoEstado);

                    // Actualizar en la tabla
                    modelHabitaciones.setValueAt(nuevoNumero, selectedRow, 1);
                    modelHabitaciones.setValueAt(nuevoTipo, selectedRow, 2);
                    modelHabitaciones.setValueAt(nuevoEstado, selectedRow, 3);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una habitación para actualizar.");
                }
            }
        });

        btnDeleteRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableHabitaciones.getSelectedRow();
                if (selectedRow >= 0) {
                    int habitacionID = (int) modelHabitaciones.getValueAt(selectedRow, 0);
                    eliminarHabitacion(habitacionID);
                    modelHabitaciones.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una habitación para eliminar.");
                }
            }
        });

        btnUpdateReservation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableReservas.getSelectedRow();
                if (selectedRow >= 0) {
                    // Aquí debes implementar la lógica para actualizar la reserva
                    int reservaID = (int) modelReservas.getValueAt(selectedRow, 0);
                    String nuevoCliente = JOptionPane.showInputDialog(Dashboard.this, "Nuevo Cliente", modelReservas.getValueAt(selectedRow, 1));
                    String nuevaHabitacion = JOptionPane.showInputDialog(Dashboard.this, "Nueva Habitación", modelReservas.getValueAt(selectedRow, 2));
                    String nuevaFechaEntrada = JOptionPane.showInputDialog(Dashboard.this, "Nueva Fecha Entrada", modelReservas.getValueAt(selectedRow, 3));
                    String nuevaFechaSalida = JOptionPane.showInputDialog(Dashboard.this, "Nueva Fecha Salida", modelReservas.getValueAt(selectedRow, 4));
                    String nuevoEstado = JOptionPane.showInputDialog(Dashboard.this, "Nuevo Estado", modelReservas.getValueAt(selectedRow, 5));

                    // Lógica para actualizar en la base de datos
                    actualizarReserva(reservaID, nuevoCliente, nuevaHabitacion, nuevaFechaEntrada, nuevaFechaSalida, nuevoEstado);

                    // Actualizar en la tabla
                    modelReservas.setValueAt(nuevoCliente, selectedRow, 1);
                    modelReservas.setValueAt(nuevaHabitacion, selectedRow, 2);
                    modelReservas.setValueAt(nuevaFechaEntrada, selectedRow, 3);
                    modelReservas.setValueAt(nuevaFechaSalida, selectedRow, 4);
                    modelReservas.setValueAt(nuevoEstado, selectedRow, 5);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una reserva para actualizar.");
                }
            }
        });

        btnDeleteReservation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableReservas.getSelectedRow();
                if (selectedRow >= 0) {
                    int reservaID = (int) modelReservas.getValueAt(selectedRow, 0);
                    eliminarReserva(reservaID);
                    modelReservas.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una reserva para eliminar.");
                }
            }
        });
    }

    // Cargar datos al inicializar el Dashboard
    private void cargarDatos() {
        cargarClientes();
        cargarHabitaciones();
        cargarReservas();
    }

    private void cargarClientes() {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM Clientes";
            try (PreparedStatement pstmt = connection.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) tableClientes.getModel();
                model.setRowCount(0); // Limpiar la tabla antes de cargar
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("ID"),
                            rs.getString("Nombre"),
                            rs.getString("Apellidos"),
                            rs.getString("Email"),
                            rs.getString("Telefono")
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage());
        }
    }

    private void cargarHabitaciones() {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM Habitaciones";
            try (PreparedStatement pstmt = connection.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) tableHabitaciones.getModel();
                model.setRowCount(0); // Limpiar la tabla antes de cargar
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("ID"),
                            rs.getString("NumeroHabitacion"),
                            rs.getString("Tipo"),
                            rs.getString("Estado")
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar habitaciones: " + e.getMessage());
        }
    }

    private void cargarReservas() {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT r.ID, CONCAT(c.Nombre, ' ', c.Apellidos) AS Cliente, h.NumeroHabitacion, r.FechaEntrada, r.FechaSalida, r.Estado " +
                    "FROM Reservas r " +
                    "JOIN Clientes c ON r.ClienteID = c.ID " +
                    "JOIN Habitaciones h ON r.HabitacionID = h.ID";
            try (PreparedStatement pstmt = connection.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) tableReservas.getModel();
                model.setRowCount(0); // Limpiar la tabla antes de cargar
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("ID"),
                            rs.getString("Cliente"),
                            rs.getString("NumeroHabitacion"),
                            rs.getDate("FechaEntrada"),
                            rs.getDate("FechaSalida"),
                            rs.getString("Estado")
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar reservas: " + e.getMessage());
        }
    }

    private void actualizarCliente(int clienteID, String nuevoNombre, String nuevosApellidos, String nuevoEmail, String nuevoTelefono) {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "UPDATE Clientes SET Nombre = ?, Apellidos = ?, Email = ?, Telefono = ? WHERE ID = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, nuevoNombre);
                pstmt.setString(2, nuevosApellidos);
                pstmt.setString(3, nuevoEmail);
                pstmt.setString(4, nuevoTelefono);
                pstmt.setInt(5, clienteID);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Cliente actualizado con éxito.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar cliente: " + e.getMessage());
        }
    }

    private void eliminarCliente(int clienteID) {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "DELETE FROM Clientes WHERE ID = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, clienteID);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Cliente eliminado con éxito.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar cliente: " + e.getMessage());
        }
    }

    private void actualizarHabitacion(int habitacionID, String nuevoNumero, String nuevoTipo, String nuevoEstado) {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "UPDATE Habitaciones SET NumeroHabitacion = ?, Tipo = ?, Estado = ? WHERE ID = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, nuevoNumero);
                pstmt.setString(2, nuevoTipo);
                pstmt.setString(3, nuevoEstado);
                pstmt.setInt(4, habitacionID);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Habitación actualizada con éxito.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar habitación: " + e.getMessage());
        }
    }

    private void eliminarHabitacion(int habitacionID) {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "DELETE FROM Habitaciones WHERE ID = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, habitacionID);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Habitación eliminada con éxito.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar habitación: " + e.getMessage());
        }
    }

    private void actualizarReserva(int reservaID, String nuevoCliente, String nuevaHabitacion, String nuevaFechaEntrada, String nuevaFechaSalida, String nuevoEstado) {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "UPDATE Reservas SET ClienteID = (SELECT ID FROM Clientes WHERE CONCAT(Nombre, ' ', Apellidos) = ?), " +
                    "HabitacionID = (SELECT ID FROM Habitaciones WHERE NumeroHabitacion = ?), " +
                    "FechaEntrada = ?, FechaSalida = ?, Estado = ? WHERE ID = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, nuevoCliente);
                pstmt.setString(2, nuevaHabitacion);
                pstmt.setDate(3, java.sql.Date.valueOf(nuevaFechaEntrada));
                pstmt.setDate(4, java.sql.Date.valueOf(nuevaFechaSalida));
                pstmt.setString(5, nuevoEstado);
                pstmt.setInt(6, reservaID);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Reserva actualizada con éxito.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar reserva: " + e.getMessage());
        }
    }

    private void eliminarReserva(int reservaID) {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "DELETE FROM Reservas WHERE ID = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, reservaID);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Reserva eliminada con éxito.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar reserva: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }
}

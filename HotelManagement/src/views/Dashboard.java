package HotelManagement.src.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        fileMenu.add(homeItem); // Agregar opción de Inicio
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

        // Acciones de botones de actualización y eliminación
        btnUpdateClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableClientes.getSelectedRow();
                if (selectedRow >= 0) {
                    // Aquí debes implementar la lógica para actualizar el cliente
                    JOptionPane.showMessageDialog(null, "Actualizar Cliente: " + modelClientes.getValueAt(selectedRow, 1));
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un cliente para actualizar.");
                }
            }
        });

        btnDeleteClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableClientes.getSelectedRow();
                if (selectedRow >= 0) {
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
                    JOptionPane.showMessageDialog(null, "Actualizar Habitación: " + modelHabitaciones.getValueAt(selectedRow, 1));
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una habitación para actualizar.");
                }
            }
        });

        btnDeleteRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableHabitaciones.getSelectedRow();
                if (selectedRow >= 0) {
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
                    JOptionPane.showMessageDialog(null, "Actualizar Reserva: " + modelReservas.getValueAt(selectedRow, 1));
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una reserva para actualizar.");
                }
            }
        });

        btnDeleteReservation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableReservas.getSelectedRow();
                if (selectedRow >= 0) {
                    modelReservas.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una reserva para eliminar.");
                }
            }
        });
    }

    // Métodos para agregar datos en las tablas
    public void agregarCliente(int id, String nombre, String apellidos, String email, String telefono) {
        DefaultTableModel model = (DefaultTableModel) tableClientes.getModel();
        model.addRow(new Object[]{id, nombre, apellidos, email, telefono});
    }

    public void agregarHabitacion(int id, String numeroHabitacion, String tipo, String estado) {
        DefaultTableModel model = (DefaultTableModel) tableHabitaciones.getModel();
        model.addRow(new Object[]{id, numeroHabitacion, tipo, estado});
    }

    public void agregarReserva(int id, String cliente, String habitacion, String fechaEntrada, String fechaSalida, String estado) {
        DefaultTableModel model = (DefaultTableModel) tableReservas.getModel();
        model.addRow(new Object[]{id, cliente, habitacion, fechaEntrada, fechaSalida, estado});
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }
}

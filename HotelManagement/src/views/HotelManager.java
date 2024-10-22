package HotelManagement.src.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HotelManager extends JFrame {

    public HotelManager() {
        setTitle("Gestor Hotelero");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        // Crear barra de menú
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Opciones");
        JMenu helpMenu = new JMenu("Ayuda");

        JMenuItem addClientItem = new JMenuItem("Agregar Cliente");
        JMenuItem addRoomItem = new JMenuItem("Agregar Habitación");
        JMenuItem viewReservationsItem = new JMenuItem("Agregar Reserva");
        JMenuItem aboutItem = new JMenuItem("Acerca de");
        JMenuItem homeItem = new JMenuItem("Inicio");
        JMenuItem dashboardItem = new JMenuItem("Tablero");

        fileMenu.add(homeItem); // Agregar opción de Inicio
        fileMenu.add(addClientItem);
        fileMenu.add(addRoomItem);
        fileMenu.add(viewReservationsItem);
        fileMenu.add(dashboardItem); // Agregar opción de Tablero
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Título centrado
        JLabel titleLabel = new JLabel("Gestor de Hotel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Área de contenido
        JTextArea contentArea = new JTextArea();
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setText(getHomeText()); // Inicializar con el texto de inicio

        mainPanel.add(contentArea, BorderLayout.CENTER);

        // Panel para botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton btnHome = new JButton("Inicio");
        JButton btnClientes = new JButton("Clientes");
        JButton btnReservas = new JButton("Reservas");
        JButton btnHabitaciones = new JButton("Habitaciones");
        JButton btnDashboard = new JButton("Tablero");

        buttonPanel.add(btnHome);
        buttonPanel.add(btnClientes);
        buttonPanel.add(btnReservas);
        buttonPanel.add(btnHabitaciones);
        buttonPanel.add(btnDashboard); // Agregar botón de Tablero

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Acciones de los menús
        addClientItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Client().setVisible(true);
            }
        });

        addRoomItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Room().setVisible(true);
            }
        });

        viewReservationsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Reservation().setVisible(true);
            }
        });

        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Gestor Hotelero\nVersión 1.0\nHecho por:\nJuan Diego Calle\nHarbey Alexander Camaron", "Acerca de", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Acciones de los botones
        btnClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Client().setVisible(true);
            }
        });

        btnReservas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Reservation().setVisible(true);
            }
        });

        btnHabitaciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Room().setVisible(true);
            }
        });

        // Acción del botón de Inicio
        btnHome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contentArea.setText(getHomeText());
            }
        });

        // Acción del menú de Inicio
        homeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contentArea.setText(getHomeText());
            }
        });

        // Acción del botón de Tablero
        btnDashboard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Dashboard dashboard = new Dashboard();
                dashboard.setVisible(true);
            }
        });

        // Acción del menú de Tablero
        dashboardItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Dashboard dashboard = new Dashboard();
                dashboard.setVisible(true);
            }
        });
    }

    // Método para obtener el texto de inicio
    private String getHomeText() {
        return "Bienvenido al Gestor Hotelero.\n\n"
                + "Este sistema te permite gestionar clientes, habitaciones y reservas "
                + "de manera eficiente y sencilla.\n\n"
                + "Utiliza las opciones del menú o los botones a continuación para navegar "
                + "por las diferentes secciones.\n\n"
                + "¡Comienza a administrar tu hotel de manera efectiva!";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HotelManager().setVisible(true);
            }
        });
    }
}

package HotelManagement.src.views;

import javax.swing.*;
import java.awt.*;

public class HotelManager extends JFrame {

    public HotelManager() {
        setTitle("Gestor Hotelero");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        // Cambiar el color de fondo
        getContentPane().setBackground(new Color(250, 250, 250));

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

        fileMenu.add(homeItem);
        fileMenu.add(addClientItem);
        fileMenu.add(addRoomItem);
        fileMenu.add(viewReservationsItem);
        fileMenu.add(dashboardItem);
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240)); // Color de fondo del panel principal

        // Título centrado
        JLabel titleLabel = new JLabel("Gestor de Hotel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(51, 51, 51)); // Color del texto del título
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Área de contenido
        JTextArea contentArea = new JTextArea();
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setText(getHomeText());
        contentArea.setBackground(new Color(255, 255, 255)); // Fondo blanco
        contentArea.setForeground(new Color(51, 51, 51)); // Color del texto
        contentArea.setFont(new Font("Arial", Font.PLAIN, 16));
        mainPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER); // Agregar ScrollPane

        // Panel para botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(new Color(240, 240, 240)); // Color de fondo del panel de botones

        JButton btnHome = new JButton("Inicio");
        JButton btnClientes = new JButton("Clientes");
        JButton btnReservas = new JButton("Reservas");
        JButton btnHabitaciones = new JButton("Habitaciones");
        JButton btnDashboard = new JButton("Tablero");

        // Estilizar botones
        JButton[] buttons = {btnHome, btnClientes, btnReservas, btnHabitaciones, btnDashboard};
        for (JButton button : buttons) {
            button.setBackground(new Color(51, 153, 255)); // Color de fondo de los botones
            button.setForeground(Color.WHITE); // Color del texto de los botones
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Espaciado interno
            buttonPanel.add(button);
        }

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Acciones de los menús
        addClientItem.addActionListener(e -> openClientWindow());
        addRoomItem.addActionListener(e -> openRoomWindow());
        viewReservationsItem.addActionListener(e -> openReservationWindow());

        aboutItem.addActionListener(e -> showAboutDialog());

        // Acciones de los botones
        btnClientes.addActionListener(e -> openClientWindow());
        btnReservas.addActionListener(e -> openReservationWindow());
        btnHabitaciones.addActionListener(e -> openRoomWindow());

        // Acción del botón de Inicio
        btnHome.addActionListener(e -> contentArea.setText(getHomeText()));
        homeItem.addActionListener(e -> contentArea.setText(getHomeText()));

        // Acción del botón de Tablero
        btnDashboard.addActionListener(e -> openDashboardWindow());
        dashboardItem.addActionListener(e -> openDashboardWindow());
    }

    // Métodos para abrir ventanas
    private void openClientWindow() {
        new HotelManagement.src.views.Client().setVisible(true);
    }

    private void openRoomWindow() {
        new HotelManagement.src.views.Room().setVisible(true);
    }

    private void openReservationWindow() {
        new HotelManagement.src.views.Reservation().setVisible(true);
    }

    private void openDashboardWindow() {
        new HotelManagement.src.views.Dashboard().setVisible(true);
    }

    // Método para mostrar el cuadro de diálogo "Acerca de"
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
                "Gestor Hotelero\nVersión 1.0\nHecho por:\nJuan Diego Calle\nHarbey Alexander Camaron",
                "Acerca de", JOptionPane.INFORMATION_MESSAGE);
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
        SwingUtilities.invokeLater(() -> new HotelManager().setVisible(true));
    }
}

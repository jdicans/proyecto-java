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
        JMenu fileMenu = new JMenu("Archivo");
        JMenu helpMenu = new JMenu("Ayuda");

        JMenuItem aboutItem = new JMenuItem("Acerca de");
        JMenuItem homeItem = new JMenuItem("Inicio");

        fileMenu.add(homeItem);
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        // Crear panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton btnClientes = new JButton("Clientes");
        JButton btnReservas = new JButton("Reservas");
        JButton btnHabitaciones = new JButton("Habitaciones");
        JButton btnDashboard = new JButton("Tablero");

        buttonPanel.add(btnClientes);
        buttonPanel.add(btnReservas);
        buttonPanel.add(btnHabitaciones);
        buttonPanel.add(btnDashboard);

        // Configurar el contenedor principal
        getContentPane().add(buttonPanel, BorderLayout.NORTH);

        // Acciones de los botones
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

        btnDashboard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Dashboard().setVisible(true); // Abre el Dashboard
            }
        });

        // Inicializar el menú de ayuda
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Gestor Hotelero\nVersión 1.0\nHecho por:\nJUAN DIEGO\nHARBEY ALEXANDER\nMAYERLIS ARANGO", "Acerca de", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HotelManager().setVisible(true);
            }
        });
    }
}

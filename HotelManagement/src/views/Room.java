package HotelManagement.src.views;

import HotelManagement.src.db.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Room extends JFrame {
    private JTextField txtNumeroHabitacion;
    private JComboBox<String> cmbTipo;
    private JComboBox<String> cmbEstado;

    public Room() {
        setTitle("Agregar Habitación");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        // Crear panel de formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(4, 2));

        panelFormulario.add(new JLabel("Número de Habitación:"));
        txtNumeroHabitacion = new JTextField();
        panelFormulario.add(txtNumeroHabitacion);

        panelFormulario.add(new JLabel("Tipo:"));
        String[] tipos = { "Sencilla", "Doble", "Suite" };
        cmbTipo = new JComboBox<>(tipos);
        panelFormulario.add(cmbTipo);

        panelFormulario.add(new JLabel("Estado:"));
        String[] estados = { "Disponible", "Reservada", "En Mantenimiento" };
        cmbEstado = new JComboBox<>(estados);
        panelFormulario.add(cmbEstado);

        JButton btnAgregar = new JButton("Agregar Habitación");
        panelFormulario.add(btnAgregar);

        // Configurar el contenedor principal
        getContentPane().add(panelFormulario, BorderLayout.CENTER);

        // Acción del botón
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarHabitacion();
            }
        });
    }

    private void agregarHabitacion() {
        String numeroHabitacion = txtNumeroHabitacion.getText();
        String tipo = (String) cmbTipo.getSelectedItem();
        String estado = (String) cmbEstado.getSelectedItem();

        // Establecer conexión
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "INSERT INTO Habitaciones (NumeroHabitacion, Tipo, Estado) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, numeroHabitacion);
                pstmt.setString(2, tipo);
                pstmt.setString(3, estado);
                pstmt.executeUpdate(); // Ejecutar la inserción
                JOptionPane.showMessageDialog(this, "Habitación agregada con éxito.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar habitación: " + e.getMessage());
        }

        // Limpiar los campos
        txtNumeroHabitacion.setText("");
        cmbTipo.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);
    }
}

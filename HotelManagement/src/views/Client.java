package HotelManagement.src.views;


import HotelManagement.src.db.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Client extends JFrame {
    private JTextField txtNombre;
    private JTextField txtApellidos;
    private JTextField txtEmail;
    private JTextField txtTelefono;

    public Client() {
        setTitle("Agregar Cliente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        // Crear panel de formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(5, 2));

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Apellidos:"));
        txtApellidos = new JTextField();
        panelFormulario.add(txtApellidos);

        panelFormulario.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelFormulario.add(txtEmail);

        panelFormulario.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);

        JButton btnAgregar = new JButton("Agregar Cliente");
        panelFormulario.add(btnAgregar);

        // Configurar el contenedor principal
        getContentPane().add(panelFormulario, BorderLayout.CENTER);

        // Acción del botón
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarCliente();
            }
        });
    }

    private void agregarCliente() {
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String email = txtEmail.getText();
        String telefono = txtTelefono.getText();

        // Establecer conexión
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "INSERT INTO Clientes (Nombre, Apellidos, Email, Telefono) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, nombre);
                pstmt.setString(2, apellidos);
                pstmt.setString(3, email);
                pstmt.setString(4, telefono);
                pstmt.executeUpdate(); // Ejecutar la inserción
                JOptionPane.showMessageDialog(this, "Cliente agregado con éxito.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar cliente: " + e.getMessage());
        }

        // Limpiar los campos
        txtNombre.setText("");
        txtApellidos.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
    }
}

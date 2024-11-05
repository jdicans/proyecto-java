package HotelManagement.src.views;

import HotelManagement.src.db.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client extends JFrame {

    private JTextField txtNombre;
    private JTextField txtApellidos;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JTextArea txtAreaResultado;

    public Client() {
        setTitle("Agregar Cliente");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        // Cambiar el color de fondo
        getContentPane().setBackground(new Color(250, 250, 250));

        // Crear panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(240, 240, 240));

        // Crear y configurar el título
        JLabel lblTitulo = new JLabel("Agregar Cliente", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(51, 102, 153));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Crear panel de formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridBagLayout());
        panelFormulario.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Añadir componentes al panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        txtNombre = new JTextField(20);
        gbc.gridx = 1;
        panelFormulario.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("Apellidos:"), gbc);
        txtApellidos = new JTextField(20);
        gbc.gridx = 1;
        panelFormulario.add(txtApellidos, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        panelFormulario.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(new JLabel("Teléfono:"), gbc);
        txtTelefono = new JTextField(20);
        gbc.gridx = 1;
        panelFormulario.add(txtTelefono, gbc);

        // Botón Agregar Cliente y centrarlo
        JButton btnAgregar = new JButton("Agregar Cliente");
        btnAgregar.setBackground(new Color(51, 153, 255));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Centrar el botón
        panelFormulario.add(btnAgregar, gbc);

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
                agregarCliente();
            }
        });
    }

    private void agregarCliente() {
        String nombre = txtNombre.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();

        // Validaciones
        if (nombre.isEmpty() || apellidos.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un email válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Llamar al método para agregar el cliente en la base de datos
        DatabaseConnection.agregarCliente(nombre, apellidos, email, telefono);

        // Mostrar el resultado en el área de texto
        txtAreaResultado.append("Cliente agregado: " + nombre + " " + apellidos +
                "\nEmail: " + email + "\nTeléfono: " + telefono + "\n\n");

        // Limpiar los campos
        txtNombre.setText("");
        txtApellidos.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Client().setVisible(true));
    }
}

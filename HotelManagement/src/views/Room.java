package HotelManagement.src.views;

import HotelManagement.src.db.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Room extends JFrame {

    private JTextField txtNumeroHabitacion;
    private JComboBox<String> cmbTipo;
    private JComboBox<String> cmbEstado;
    private JTextArea txtAreaResultado;
    private JButton btnAgregar;

    public Room() {
        setTitle("Agregar Habitación");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        // Cambiar el color de fondo
        getContentPane().setBackground(new Color(250, 250, 250));

        // Crear panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(240, 240, 240));

        // Crear y configurar el título
        JLabel lblTitulo = new JLabel("Agregar Habitación", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(51, 102, 153));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Crear panel de formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(4, 2, 10, 10)); // Espacio entre celdas
        panelFormulario.setBackground(new Color(240, 240, 240)); // Fondo del panel

        panelFormulario.add(new JLabel("Número de Habitación:"));
        txtNumeroHabitacion = new JTextField();
        panelFormulario.add(txtNumeroHabitacion);

        panelFormulario.add(new JLabel("Tipo:"));
        String[] tipos = {"Sencilla", "Doble", "Suite"};
        cmbTipo = new JComboBox<>(tipos);
        panelFormulario.add(cmbTipo);

        panelFormulario.add(new JLabel("Estado:"));
        String[] estados = {"Disponible", "Reservada", "En Mantenimiento"};
        cmbEstado = new JComboBox<>(estados);
        panelFormulario.add(cmbEstado);

        // Crear panel para el botón y centrarlo
        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(240, 240, 240));
        btnAgregar = new JButton("Agregar Habitación");
        btnAgregar.setBackground(new Color(51, 153, 255)); // Color de fondo del botón
        btnAgregar.setForeground(Color.WHITE); // Color del texto del botón
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 14));
        panelBoton.add(btnAgregar); // Agregar el botón centrado en el panel
        panelFormulario.add(panelBoton);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // Área de resultado
        txtAreaResultado = new JTextArea();
        txtAreaResultado.setEditable(false);
        txtAreaResultado.setBackground(Color.WHITE); // Fondo blanco
        txtAreaResultado.setForeground(new Color(51, 51, 51)); // Color del texto
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
                agregarHabitacion();
            }
        });
    }

    private void agregarHabitacion() {
        String numeroHabitacion = txtNumeroHabitacion.getText().trim();
        String tipo = (String) cmbTipo.getSelectedItem();
        String estado = (String) cmbEstado.getSelectedItem();

        // Validar que el número de habitación no esté vacío y sea numérico
        if (numeroHabitacion.isEmpty() || !numeroHabitacion.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un número de habitación válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Llamar al método para agregar habitación en la base de datos
        boolean success = DatabaseConnection.agregarHabitacion(numeroHabitacion, tipo, estado);
        if (success) {
            JOptionPane.showMessageDialog(this, "Habitación agregada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            // Mostrar resultado en el área de texto
            txtAreaResultado.append("Habitación agregada: " + numeroHabitacion +
                    "\nTipo: " + tipo + "\nEstado: " + estado + "\n\n");
            // Limpiar los campos
            txtNumeroHabitacion.setText("");
            cmbTipo.setSelectedIndex(0);
            cmbEstado.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar habitación. Verifique la conexión con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Room().setVisible(true);
            }
        });
    }
}

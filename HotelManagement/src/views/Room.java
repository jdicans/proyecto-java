package HotelManagement.src.views;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Room extends JFrame {

    private JTextField txtNumeroHabitacion;
    private JComboBox<String> cmbTipo;
    private JComboBox<String> cmbEstado;
    private JTextArea txtAreaResultado;

    public Room() {
        setTitle("Agregar Habitación");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        // Crear panel de formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(5, 2));

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

        // Área de resultado
        txtAreaResultado = new JTextArea();
        txtAreaResultado.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaResultado);

        // Configurar el contenedor principal
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(panelFormulario, BorderLayout.CENTER);
        container.add(scrollPane, BorderLayout.SOUTH);

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

        // Aquí puedes agregar la lógica para almacenar la habitación en la base de datos

        // Mostrar el resultado en el área de texto
        txtAreaResultado.append("Habitación agregada: " + numeroHabitacion +
                "\nTipo: " + tipo + "\nEstado: " + estado + "\n\n");

        // Limpiar los campos
        txtNumeroHabitacion.setText("");
        cmbTipo.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Room().setVisible(true);
            }
        });
    }
}

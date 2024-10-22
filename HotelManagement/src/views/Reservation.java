package HotelManagement.src.views;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

public class Reservation extends JFrame {

    private JComboBox<String> cmbCliente;
    private JComboBox<String> cmbHabitacion;
    private JSpinner spinnerFechaEntrada;
    private JSpinner spinnerFechaSalida;
    private JComboBox<String> cmbEstado;
    private JTextArea txtAreaResultado;

    public Reservation() {
        setTitle("Agregar Reserva");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        // Crear panel de formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(6, 2));

        panelFormulario.add(new JLabel("Cliente:"));
        cmbCliente = new JComboBox<>(new String[]{"Cliente 1", "Cliente 2", "Cliente 3"}); // Aquí puedes cargar desde la base de datos
        panelFormulario.add(cmbCliente);

        panelFormulario.add(new JLabel("Habitación:"));
        cmbHabitacion = new JComboBox<>(new String[]{"Habitación 101", "Habitación 102", "Habitación 103"}); // Aquí puedes cargar desde la base de datos
        panelFormulario.add(cmbHabitacion);

        panelFormulario.add(new JLabel("Fecha Entrada:"));
        spinnerFechaEntrada = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditorEntrada = new JSpinner.DateEditor(spinnerFechaEntrada, "dd/MM/yyyy");
        spinnerFechaEntrada.setEditor(dateEditorEntrada);
        panelFormulario.add(spinnerFechaEntrada);

        panelFormulario.add(new JLabel("Fecha Salida:"));
        spinnerFechaSalida = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditorSalida = new JSpinner.DateEditor(spinnerFechaSalida, "dd/MM/yyyy");
        spinnerFechaSalida.setEditor(dateEditorSalida);
        panelFormulario.add(spinnerFechaSalida);

        panelFormulario.add(new JLabel("Estado:"));
        String[] estados = {"Activa", "Cancelada", "Completada"};
        cmbEstado = new JComboBox<>(estados);
        panelFormulario.add(cmbEstado);

        JButton btnAgregar = new JButton("Agregar Reserva");
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
                agregarReserva();
            }
        });
    }

    private void agregarReserva() {
        String cliente = (String) cmbCliente.getSelectedItem();
        String habitacion = (String) cmbHabitacion.getSelectedItem();
        Date fechaEntrada = (Date) spinnerFechaEntrada.getValue();
        Date fechaSalida = (Date) spinnerFechaSalida.getValue();
        String estado = (String) cmbEstado.getSelectedItem();

        // Aquí puedes agregar la lógica para almacenar la reserva en la base de datos

        // Mostrar el resultado en el área de texto
        txtAreaResultado.append("Reserva agregada:\n"
                + "Cliente: " + cliente + "\n"
                + "Habitación: " + habitacion + "\n"
                + "Fecha Entrada: " + fechaEntrada + "\n"
                + "Fecha Salida: " + fechaSalida + "\n"
                + "Estado: " + estado + "\n\n");

        // Limpiar los campos
        cmbCliente.setSelectedIndex(0);
        cmbHabitacion.setSelectedIndex(0);
        spinnerFechaEntrada.setValue(new Date());
        spinnerFechaSalida.setValue(new Date());
        cmbEstado.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Reservation().setVisible(true);
            }
        });
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Comparator;

public class InterfazGrafica extends JFrame {
    private Agenda agenda;
    private JTextArea resultadoArea;
    private JButton btnCargar, btnInsertar, btnBuscar, btnEliminar, btnExportar, btnSalir;

    public InterfazGrafica() {
        agenda = new Agenda();
        
        setTitle("Agenda de Contactos");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel de botones arriba
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());
        
        btnCargar = new JButton("Cargar Archivo");
        btnInsertar = new JButton("Insertar");
        btnBuscar = new JButton("Buscar");
        btnEliminar = new JButton("Eliminar");
        btnExportar = new JButton("Exportar");
        btnSalir = new JButton("Salir");

        panelBotones.add(btnCargar);
        panelBotones.add(btnInsertar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnExportar);
        panelBotones.add(btnSalir);

        // Área de resultados
        resultadoArea = new JTextArea(15, 60);
        resultadoArea.setEditable(false);
        resultadoArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scrollPane = new JScrollPane(resultadoArea);

        // Agregar listeners
        btnCargar.addActionListener(e -> cargarArchivo());
        btnInsertar.addActionListener(e -> insertarContacto());
        btnBuscar.addActionListener(e -> buscarContacto());
        btnEliminar.addActionListener(e -> eliminarContacto());
        btnExportar.addActionListener(e -> exportarContactos());
        btnSalir.addActionListener(e -> System.exit(0));

        // Agregar al frame
        add(panelBotones, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void cargarArchivo() {
        String ruta = JOptionPane.showInputDialog(this, "Ingrese ruta del archivo (ej: contactos.txt):");
        if (ruta != null && !ruta.isEmpty()) {
            String resultado = agenda.cargarContactosDesdeArchivo(ruta);
            resultadoArea.setText(resultado);
        }
    }

    private void insertarContacto() {
        JTextField nombre = new JTextField(15);
        JTextField apellido = new JTextField(15);
        JTextField apodo = new JTextField(15);
        JTextField movil = new JTextField(15);
        JTextField conv = new JTextField(15);
        JTextField email = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombre);
        panel.add(new JLabel("Apellido:"));
        panel.add(apellido);
        panel.add(new JLabel("Apodo:"));
        panel.add(apodo);
        panel.add(new JLabel("Tel. Móvil:"));
        panel.add(movil);
        panel.add(new JLabel("Tel. Conv:"));
        panel.add(conv);
        panel.add(new JLabel("Email:"));
        panel.add(email);

        int result = JOptionPane.showConfirmDialog(this, panel, "Insertar Contacto", 
                                                    JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                if (nombre.getText().isEmpty() || apellido.getText().isEmpty() || apodo.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Los campos nombre, apellido y apodo son obligatorios.");
                    return;
                }
                Contacto c = new Contacto(nombre.getText(), apellido.getText(), apodo.getText(),
                                         movil.getText(), conv.getText(), email.getText());
                agenda.insertarContacto(c);
                resultadoArea.setText("Contacto insertado exitosamente.");
            } catch (Exception e) {
                resultadoArea.setText("Error al insertar contacto.");
            }
        }
    }

    private void buscarContacto() {
        String prefijo = JOptionPane.showInputDialog(this, "Ingrese el prefijo a buscar:");
        if (prefijo == null || prefijo.isEmpty()) {
            return;
        }

        LinkedList<Contacto> resultados = agenda.buscar(prefijo);

        if (resultados.isEmpty()) {
            resultadoArea.setText("No se encontraron contactos.");
        } else {
            // Incrementar frecuencia
            for (Contacto c : resultados) {
                c.frecuencia++;
            }

            // Usar Heap para ordenar por frecuencia (descendente)
            Comparator<Contacto> comparador = (c1, c2) -> Integer.compare(c2.frecuencia, c1.frecuencia);
            Heap<Contacto> heap = new Heap<>(resultados.size(), true, comparador);
            
            for (Contacto c : resultados) {
                heap.insertar(c);
            }

            StringBuilder sb = new StringBuilder("Resultados para prefijo '" + prefijo + "' (ordenados por frecuencia):\n\n");
            while (!heap.estaVacio()) {
                Contacto c = heap.extraer();
                sb.append(c).append(" [Búsquedas: ").append(c.frecuencia).append("]\n");
            }
            resultadoArea.setText(sb.toString());
        }
    }

    private void eliminarContacto() {
        JTextField nombre = new JTextField(15);
        JTextField apellido = new JTextField(15);
        JTextField apodo = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombre);
        panel.add(new JLabel("Apellido:"));
        panel.add(apellido);
        panel.add(new JLabel("Apodo:"));
        panel.add(apodo);

        int result = JOptionPane.showConfirmDialog(this, panel, "Eliminar Contacto", 
                                                    JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                if (nombre.getText().isEmpty() || apellido.getText().isEmpty() || apodo.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Los campos nombre, apellido y apodo son obligatorios.");
                    return;
                }
                String resultado = agenda.eliminarContactoPorDatos(nombre.getText(), apellido.getText(), apodo.getText());
                resultadoArea.setText(resultado);
            } catch (Exception e) {
                resultadoArea.setText("Error al eliminar contacto.");
            }
        }
    }

    private void exportarContactos() {
        String ruta = JOptionPane.showInputDialog(this, "Nombre del archivo para exportar (ej: backup.txt):");
        if (ruta != null && !ruta.isEmpty()) {
            String resultado = agenda.exportarContactosAArchivo(ruta);
            resultadoArea.setText(resultado);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazGrafica());
    }
}

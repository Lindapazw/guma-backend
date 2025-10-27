package com.guma.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

/**
 * Programa de prueba para verificar funcionalidad de Swing y conexión a BD.
 * 
 * Demuestra:
 * - Creación de ventana Swing
 * - Componentes básicos (botones, labels, áreas de texto)
 * - Eventos de usuario
 * - Integración Swing + JDBC
 * - Ejecución de operaciones en hilos separados (no bloquear EDT)
 * 
 * @author GUMA Team
 * @version 1.0
 */
public class PruebaSwing extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Punto de entrada de la aplicación
     */
    public static void main(String[] args) {
        // Usar el Look and Feel del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo establecer el Look and Feel del sistema");
        }

        // Crear y mostrar la ventana en el Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PruebaSwing ventana = new PruebaSwing();
                ventana.setVisible(true);

                System.out.println("═══════════════════════════════════════════════════════");
                System.out.println("   GUMA - Prueba de Swing + JDBC");
                System.out.println("═══════════════════════════════════════════════════════");
                System.out.println();
                System.out.println("✅ Ventana de prueba iniciada correctamente");
                System.out.println("📋 Usa los botones para probar la funcionalidad");
                System.out.println();
            }
        });
    }

    // Componentes de la interfaz
    private JTextArea areaResultados;
    private JButton btnProbarConexion;
    private JButton btnListarTablas;
    private JButton btnLimpiar;
    private JLabel lblEstado;

    private JProgressBar barraProgreso;

    /**
     * Constructor que inicializa la interfaz
     */
    public PruebaSwing() {
        // Configuración básica - igual que PruebaSwingSimple
        setTitle("GUMA - Prueba de Swing + JDBC");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear componentes
        crearComponentes();
    }

    /**
     * Crea todos los componentes de la interfaz
     */
    private void crearComponentes() {
        // Crear panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));

        // Panel superior con título
        JLabel lblTitulo = new JLabel("🐾 Sistema GUMA - Prueba de Frontend", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Botones de acción
        btnProbarConexion = new JButton("🔗 Probar Conexión");
        btnProbarConexion.setFont(new Font("Arial", Font.PLAIN, 14));
        btnProbarConexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                probarConexion();
            }
        });

        btnListarTablas = new JButton("📋 Listar Tablas");
        btnListarTablas.setFont(new Font("Arial", Font.PLAIN, 14));
        btnListarTablas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarTablas();
            }
        });

        btnLimpiar = new JButton("🗑️ Limpiar");
        btnLimpiar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                areaResultados.setText("");
                lblEstado.setText("Estado: Listo");
                lblEstado.setForeground(Color.BLACK);
            }
        });

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.add(btnProbarConexion);
        panelBotones.add(btnListarTablas);
        panelBotones.add(btnLimpiar);

        // Área de resultados
        areaResultados = new JTextArea(20, 60);
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(areaResultados);

        // Panel central con botones y área de resultados
        JPanel panelCentral = new JPanel(new BorderLayout(5, 5));
        panelCentral.add(panelBotones, BorderLayout.NORTH);
        panelCentral.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        // Barra de progreso
        barraProgreso = new JProgressBar();
        barraProgreso.setIndeterminate(false);
        barraProgreso.setVisible(false);

        // Label de estado
        lblEstado = new JLabel("Estado: Listo");

        // Panel inferior con estado y barra de progreso
        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
        panelInferior.add(lblEstado, BorderLayout.WEST);
        panelInferior.add(barraProgreso, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        // Agregar el panel principal al frame
        add(panelPrincipal);
    }

    /**
     * Prueba la conexión a la base de datos en un hilo separado
     */
    private void probarConexion() {
        // Deshabilitar botones durante la operación
        deshabilitarBotones();
        mostrarProgreso(true);
        actualizarEstado("Conectando a la base de datos...", Color.BLUE);

        // Ejecutar en un hilo separado para no bloquear la interfaz
        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                StringBuilder resultado = new StringBuilder();
                resultado.append("═══════════════════════════════════════════════════════\n");
                resultado.append("   PRUEBA DE CONEXIÓN A BASE DE DATOS\n");
                resultado.append("═══════════════════════════════════════════════════════\n\n");

                try {
                    // Cargar propiedades
                    Properties props = cargarPropiedades();
                    String url = props.getProperty("db.url");
                    String usuario = props.getProperty("db.username");
                    String password = props.getProperty("db.password");

                    resultado.append("📄 Configuración:\n");
                    resultado.append("   URL: ").append(url).append("\n");
                    resultado.append("   Usuario: ").append(usuario).append("\n\n");

                    // Cargar driver
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    resultado.append("🔌 Driver MySQL JDBC cargado correctamente\n\n");

                    // Conectar
                    try (Connection conn = DriverManager.getConnection(url, usuario, password)) {
                        resultado.append("✅ ¡Conexión establecida exitosamente!\n\n");

                        // Información de la BD
                        resultado.append("📊 Información del servidor:\n");
                        resultado.append("   - Producto: ").append(conn.getMetaData().getDatabaseProductName())
                                .append("\n");
                        resultado.append("   - Versión: ").append(conn.getMetaData().getDatabaseProductVersion())
                                .append("\n\n");

                        // Consulta de prueba
                        try (Statement stmt = conn.createStatement();
                                ResultSet rs = stmt.executeQuery("SELECT DATABASE() as db, NOW() as fecha")) {

                            if (rs.next()) {
                                resultado.append("📌 Detalles:\n");
                                resultado.append("   - Base de datos: ").append(rs.getString("db")).append("\n");
                                resultado.append("   - Fecha/Hora: ").append(rs.getString("fecha")).append("\n");
                            }
                        }
                    }

                    resultado.append("\n═══════════════════════════════════════════════════════\n");
                    resultado.append("   ✅ PRUEBA EXITOSA\n");
                    resultado.append("═══════════════════════════════════════════════════════\n");

                    return resultado.toString();

                } catch (Exception e) {
                    resultado.append("\n❌ ERROR: ").append(e.getMessage()).append("\n\n");
                    resultado.append("Detalles técnicos:\n");
                    resultado.append(e.getClass().getName()).append("\n");
                    throw e;
                }
            }

            @Override
            protected void done() {
                mostrarProgreso(false);
                habilitarBotones();

                try {
                    String resultado = get();
                    areaResultados.setText(resultado);
                    actualizarEstado("✅ Conexión exitosa", Color.GREEN.darker());
                } catch (Exception e) {
                    areaResultados.setText("❌ Error al conectar:\n\n" + e.getMessage());
                    actualizarEstado("❌ Error de conexión", Color.RED);

                    JOptionPane.showMessageDialog(
                            PruebaSwing.this,
                            "Error al conectar con la base de datos.\n" +
                                    "Verifica que el túnel SSH esté activo.",
                            "Error de Conexión",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        worker.execute();
    }

    /**
     * Lista todas las tablas de la base de datos
     */
    private void listarTablas() {
        deshabilitarBotones();
        mostrarProgreso(true);
        actualizarEstado("Obteniendo lista de tablas...", Color.BLUE);

        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                StringBuilder resultado = new StringBuilder();
                resultado.append("═══════════════════════════════════════════════════════\n");
                resultado.append("   TABLAS EN LA BASE DE DATOS\n");
                resultado.append("═══════════════════════════════════════════════════════\n\n");

                try {
                    Properties props = cargarPropiedades();
                    String url = props.getProperty("db.url");
                    String usuario = props.getProperty("db.username");
                    String password = props.getProperty("db.password");

                    Class.forName("com.mysql.cj.jdbc.Driver");

                    try (Connection conn = DriverManager.getConnection(url, usuario, password);
                            Statement stmt = conn.createStatement();
                            ResultSet rs = stmt.executeQuery("SHOW TABLES")) {

                        int contador = 0;
                        while (rs.next()) {
                            contador++;
                            resultado.append(String.format("%3d. %s\n", contador, rs.getString(1)));
                        }

                        resultado.append("\n═══════════════════════════════════════════════════════\n");
                        resultado.append(String.format("   Total: %d tablas encontradas\n", contador));
                        resultado.append("═══════════════════════════════════════════════════════\n");
                    }

                    return resultado.toString();

                } catch (Exception e) {
                    resultado.append("\n❌ ERROR: ").append(e.getMessage()).append("\n");
                    throw e;
                }
            }

            @Override
            protected void done() {
                mostrarProgreso(false);
                habilitarBotones();

                try {
                    String resultado = get();
                    areaResultados.setText(resultado);
                    actualizarEstado("✅ Tablas listadas correctamente", Color.GREEN.darker());
                } catch (Exception e) {
                    areaResultados.setText("❌ Error al listar tablas:\n\n" + e.getMessage());
                    actualizarEstado("❌ Error", Color.RED);
                }
            }
        };

        worker.execute();
    }

    /**
     * Carga las propiedades desde application.properties
     */
    private Properties cargarPropiedades() throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("resources/application.properties")) {
            props.load(fis);
        }
        return props;
    }

    /**
     * Actualiza el label de estado
     */
    private void actualizarEstado(String mensaje, Color color) {
        lblEstado.setText("Estado: " + mensaje);
        lblEstado.setForeground(color);
    }

    /**
     * Muestra u oculta la barra de progreso
     */
    private void mostrarProgreso(boolean mostrar) {
        barraProgreso.setVisible(mostrar);
        barraProgreso.setIndeterminate(mostrar);
    }

    /**
     * Deshabilita los botones durante operaciones
     */
    private void deshabilitarBotones() {
        btnProbarConexion.setEnabled(false);
        btnListarTablas.setEnabled(false);
        btnLimpiar.setEnabled(false);
    }

    /**
     * Habilita los botones después de operaciones
     */
    private void habilitarBotones() {
        btnProbarConexion.setEnabled(true);
        btnListarTablas.setEnabled(true);
        btnLimpiar.setEnabled(true);
    }
}

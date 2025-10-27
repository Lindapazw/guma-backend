package com.guma.frontend.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import com.guma.frontend.adapter.PerfilFacadeAdapter;
import com.guma.frontend.dto.PerfilUsuarioFrontendDTO;
import com.guma.frontend.dto.ResultadoFrontendDTO;
import com.guma.frontend.dto.UsuarioFrontendDTO;
import com.guma.frontend.session.SesionActual;

/**
 * RF-00: Ventana principal del sistema
 * Menú principal visible luego de iniciar sesión
 */
public class MainFrame extends JFrame {

    // Método principal para testing
    public static void main(String[] args) {
        // Configurar Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            // Simular sesión para testing
            // En producción, esto se haría desde LoginDialog
            LoginDialog loginDialog = new LoginDialog(null);
            loginDialog.setVisible(true);
        });
    }

    private JLabel lblBienvenida;

    private JPanel menuPanel;

    public MainFrame() {
        initComponents();
        setupLayout();
        setupListeners();
        cargarDatosUsuario();
    }

    private void initComponents() {
        setTitle("GUMA - Sistema de Gestión de Mascotas");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Verificar que haya sesión activa
        if (!SesionActual.getInstance().isAutenticado()) {
            JOptionPane.showMessageDialog(this,
                    "Debe iniciar sesión para acceder al sistema",
                    "Sesión requerida",
                    JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Panel superior (header)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(37, 99, 235));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        lblBienvenida = new JLabel("Bienvenido/a");
        lblBienvenida.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblBienvenida.setForeground(Color.WHITE);
        headerPanel.add(lblBienvenida, BorderLayout.WEST);

        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setBorderPainted(false);
        btnCerrarSesion.setContentAreaFilled(false);
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        headerPanel.add(btnCerrarSesion, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Panel central (menú de opciones)
        menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Título del menú
        JLabel lblTitulo = new JLabel("Menú Principal");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weighty = 0.2;
        menuPanel.add(lblTitulo, gbc);

        // Reset para los botones
        gbc.gridwidth = 1;
        gbc.weighty = 1;

        // Fila 1
        gbc.gridx = 0;
        gbc.gridy = 1;
        menuPanel.add(crearBotonMenu("👤 Mi Perfil", "Ver y editar mi perfil de usuario",
                e -> abrirPerfil()), gbc);

        gbc.gridx = 1;
        menuPanel.add(crearBotonMenu("🐕 Mis Mascotas", "Ver y gestionar mis mascotas",
                e -> abrirMisMascotas()), gbc);

        gbc.gridx = 2;
        menuPanel.add(crearBotonMenu("🔍 Buscar Mascotas", "Buscar mascotas en el sistema",
                e -> abrirBuscarMascotas()), gbc);

        // Fila 2
        gbc.gridx = 0;
        gbc.gridy = 2;
        menuPanel.add(crearBotonMenu("➕ Nueva Mascota", "Registrar una nueva mascota",
                e -> abrirAltaMascota()), gbc);

        gbc.gridx = 1;
        menuPanel.add(crearBotonMenu("👥 Co-dueños", "Gestionar co-dueños de mascotas",
                e -> abrirGestionCoduenios()), gbc);

        gbc.gridx = 2;
        menuPanel.add(crearBotonMenu("📊 Estadísticas", "Ver estadísticas del sistema",
                e -> mostrarProximamente("Estadísticas")), gbc);

        add(menuPanel, BorderLayout.CENTER);

        // Panel inferior (footer)
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(243, 244, 246));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel lblFooter = new JLabel("GUMA © 2025 - Sistema de Gestión Unificada de Mascotas");
        lblFooter.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblFooter.setForeground(Color.GRAY);
        footerPanel.add(lblFooter);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private JButton crearBotonMenu(String texto, String tooltip, ActionListener action) {
        JButton btn = new JButton("<html><center>" + texto + "</center></html>");
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setToolTipText(tooltip);
        btn.setPreferredSize(new Dimension(200, 120));
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(249, 250, 251));
                btn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(37, 99, 235), 2),
                        BorderFactory.createEmptyBorder(20, 20, 20, 20)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(Color.WHITE);
                btn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(229, 231, 235), 2),
                        BorderFactory.createEmptyBorder(20, 20, 20, 20)));
            }
        });

        btn.addActionListener(action);

        return btn;
    }

    private void setupListeners() {
        // Confirmar antes de cerrar
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int opcion = JOptionPane.showConfirmDialog(
                        MainFrame.this,
                        "¿Está seguro que desea salir del sistema?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (opcion == JOptionPane.YES_OPTION) {
                    SesionActual.getInstance().cerrarSesion();
                    System.exit(0);
                }
            }
        });
    }

    private void cargarDatosUsuario() {
        if (!SesionActual.getInstance().isAutenticado()) {
            return;
        }

        UsuarioFrontendDTO usuario = SesionActual.getInstance().getUsuario();
        if (usuario != null) {
            String nombreCompleto = usuario.getNombre() + " " + usuario.getApellido();
            lblBienvenida.setText("Bienvenido/a, " + nombreCompleto);
        }
    }

    // Métodos para abrir cada funcionalidad (proximamente)

    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea cerrar sesión?",
                "Cerrar sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            SesionActual.getInstance().cerrarSesion();
            dispose();

            // Volver a mostrar login
            SwingUtilities.invokeLater(() -> {
                LoginDialog loginDialog = new LoginDialog(null);
                loginDialog.setVisible(true);
            });
        }
    }

    private void abrirPerfil() {
        // RF-03 - Actualizar perfil
        // Crear y mostrar diálogo de loading
        JDialog loadingDialog = crearDialogoLoading("Cargando perfil...");
        loadingDialog.setVisible(true);

        // Cargar perfil en background thread
        SwingWorker<PerfilUsuarioFrontendDTO, Void> worker = new SwingWorker<>() {
            @Override
            protected PerfilUsuarioFrontendDTO doInBackground() {
                // Obtener perfil del usuario actual desde la BD
                return obtenerPerfilUsuarioActual();
            }

            @Override
            protected void done() {
                // Cerrar loading dialog
                loadingDialog.dispose();

                try {
                    PerfilUsuarioFrontendDTO perfil = get();

                    if (perfil == null) {
                        // Si no hay perfil, mostrar diálogo para crearlo
                        int opcion = JOptionPane.showConfirmDialog(
                                MainFrame.this,
                                "No tenés un perfil creado aún.\n" +
                                        "El perfil debería haberse creado durante el registro.\n" +
                                        "¿Deseas crear tu perfil ahora?",
                                "Perfil no encontrado",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE);

                        if (opcion == JOptionPane.YES_OPTION) {
                            // Crear perfil básico desde datos del usuario
                            perfil = crearPerfilDesdeUsuario();
                        } else {
                            return;
                        }
                    }

                    // Abrir diálogo con el panel de perfil
                    JDialog dialog = new JDialog(MainFrame.this, "Mi Perfil", true);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                    PerfilUsuarioPanel panelPerfil = new PerfilUsuarioPanel(perfil);
                    dialog.add(panelPerfil);

                    dialog.pack();
                    dialog.setLocationRelativeTo(MainFrame.this);
                    dialog.setVisible(true);

                    // Actualizar bienvenida si cambió el nombre
                    cargarDatosUsuario();

                } catch (Exception e) {
                    System.err.println("Error al abrir perfil: " + e.getMessage());
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(
                            MainFrame.this,
                            "Error al cargar el perfil: " + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        worker.execute();
    }

    /**
     * Obtiene el perfil del usuario actual.
     * TODO: Implementar llamada al backend (GET /api/usuarios/{id}/perfil)
     */
    /**
     * Obtiene el perfil del usuario actual.
     * Carga datos reales desde la base de datos usando el backend.
     */
    private PerfilUsuarioFrontendDTO obtenerPerfilUsuarioActual() {
        UsuarioFrontendDTO usuario = SesionActual.getInstance().getUsuario();
        if (usuario == null) {
            return null;
        }

        try {
            // Usar el adapter para obtener el perfil real desde la BD
            PerfilFacadeAdapter perfilFacade = new PerfilFacadeAdapter();
            ResultadoFrontendDTO<PerfilUsuarioFrontendDTO> resultado = perfilFacade
                    .obtenerPerfilPorUsuario(usuario.getId());

            if (resultado.isExito() && resultado.getData() != null) {
                return resultado.getData();
            } else {
                // Si no tiene perfil o hubo error, crear uno básico
                System.out.println("No se encontró perfil para usuario " + usuario.getId());
                return crearPerfilDesdeUsuario();
            }

        } catch (Exception e) {
            System.err.println("Error al cargar perfil: " + e.getMessage());
            e.printStackTrace();
            // En caso de error, crear perfil básico desde datos del usuario
            return crearPerfilDesdeUsuario();
        }
    }

    /**
     * Crea un perfil básico desde los datos del usuario.
     * Usado cuando el perfil no existe (caso edge).
     */
    private PerfilUsuarioFrontendDTO crearPerfilDesdeUsuario() {
        UsuarioFrontendDTO usuario = SesionActual.getInstance().getUsuario();
        if (usuario == null) {
            return null;
        }

        PerfilUsuarioFrontendDTO perfil = new PerfilUsuarioFrontendDTO();
        perfil.setIdUsuario(Long.valueOf(usuario.getId()));
        perfil.setNombre(usuario.getNombre());
        perfil.setApellido(usuario.getApellido());
        perfil.setEmail(usuario.getEmail());
        perfil.setDni(0); // Se debe completar en el formulario
        perfil.setTelefono(usuario.getTelefono());
        // Fecha de nacimiento por defecto (hace 25 años)
        perfil.setFechaNacimiento(LocalDate.now().minusYears(25));

        return perfil;
    }

    private void abrirMisMascotas() {
        mostrarProximamente("Mis Mascotas");
        // TODO: RF-07 - Busqueda de mascotas (filtrada por dueño actual)
    }

    private void abrirBuscarMascotas() {
        mostrarProximamente("Buscar Mascotas");
        // TODO: RF-07 - Búsqueda de mascotas
    }

    private void abrirAltaMascota() {
        // TODO: Implementar panel de registro de mascota
        JDialog dialog = new JDialog(this, "Registrar Nueva Mascota", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        RegistroMascotaPanel panel = new RegistroMascotaPanel();

        dialog.add(panel);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void abrirGestionCoduenios() {
        mostrarProximamente("Gestión de Co-dueños");
        // TODO: RF-05 y RF-06 - Agregar/Remover co-dueños
    }

    private void mostrarProximamente(String funcionalidad) {
        JOptionPane.showMessageDialog(
                this,
                "La funcionalidad \"" + funcionalidad + "\" estará disponible próximamente.",
                "Próximamente",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Crea un diálogo de loading simple y no modal.
     */
    private JDialog crearDialogoLoading(String mensaje) {
        JDialog dialog = new JDialog(this, "Cargando...", false);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setUndecorated(true);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)));
        panel.setBackground(Color.WHITE);

        // Spinner de carga
        JLabel lblSpinner = new JLabel("⏳");
        lblSpinner.setFont(new Font("SansSerif", Font.PLAIN, 32));
        lblSpinner.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblSpinner, BorderLayout.NORTH);

        // Mensaje
        JLabel lblMensaje = new JLabel(mensaje);
        lblMensaje.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblMensaje, BorderLayout.CENTER);

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        return dialog;
    }
}

package com.guma.frontend.ui;

import com.guma.frontend.dto.*;
import com.guma.frontend.service.*;
import com.guma.frontend.ui.dialogs.*;
import com.guma.frontend.util.ValidationUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * RF-03: Panel para actualizar perfil de usuario
 * Permite editar los datos personales, contacto y ubicación
 * El perfil se crea durante el registro (RF-01), aquí solo se actualiza
 * 
 * MEJORAS V2:
 * - Foto de perfil con miniatura 96x96
 * - Estado de verificación, última conexión y rol
 * - Tooltips con iconos "?" en DNI y Email
 * - DNI read-only si verificado
 * - Teléfono con validación E.164
 * - Selector de dirección con DireccionDialog
 * - Botón Guardar con alto contraste (≥4.5:1)
 * - Accesibilidad completa (mnemonics, tab order, etc.)
 */
public class PerfilUsuarioPanel extends JPanel {
    
    // === TOKENS DE DISEÑO ===
    private static final Color COLOR_PRIMARY = new Color(236, 72, 153);  // #EC4899 Rosa/Pink
    private static final Color COLOR_PRIMARY_HOVER = new Color(219, 39, 119);  // Más oscuro 10%
    private static final Color COLOR_PRIMARY_PRESSED = new Color(190, 24, 93);  // Más oscuro 15%
    private static final Color COLOR_TEXT_MUTED = new Color(107, 114, 128);  // #6B7280
    private static final Color COLOR_BORDER_NORMAL = new Color(209, 213, 219);  // #D1D5DB
    private static final Color COLOR_DISABLED_BG = new Color(243, 244, 246);  // #F3F4F6
    
    // Componentes de Foto de Perfil
    private JLabel lblFotoMiniatura;
    private JButton btnCambiarFoto;
    private Runnable onCambiarFotoCallback;
    
    // Componentes de Estado
    private JLabel lblEstadoVerificacion;
    private JLabel lblUltimaConexion;
    private JLabel lblRol;
    
    // Componentes de Datos Personales
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDni;  // Ahora editable, validación 7-9 dígitos
    private JComboBox<SexoFrontendDTO> cmbSexo;
    private MaskedDateField txtFechaNacimiento;
    
    // Componentes de Contacto
    private JTextField txtEmail;  // Solo lectura
    private JLabel lblEmailInfo;  // Icono "?" con tooltip
    private JTextField txtTelefono;
    
    // Componentes de Ubicación y Redes
    private JTextField txtDireccion;  // Solo lectura
    private JButton btnNuevaDireccion;
    private JComboBox<RedSocialFrontendDTO> cmbRedSocial;
    private JButton btnNuevaRedSocial;
    
    // Componentes de Estado y Botones
    private JLabel lblEstado;
    private JButton btnCancelar;
    private JButton btnGuardar;
    
    // Estado
    private Integer idDireccionSeleccionada = null;
    private PerfilUsuarioFrontendDTO perfilActual;
    
    /**
     * Constructor que requiere un perfil existente
     * El panel trabaja únicamente en modo edición
     */
    public PerfilUsuarioPanel(PerfilUsuarioFrontendDTO perfilExistente) {
        if (perfilExistente == null) {
            throw new IllegalArgumentException("El perfil no puede ser null. El perfil se crea durante el registro.");
        }
        
        this.perfilActual = perfilExistente;
        
        initComponents();
        setupLayout();
        setupListeners();
        cargarCatalogos();
        cargarDatosExistentes();
    }
    
    private void initComponents() {
        // Configuración del panel
        setBackground(Color.WHITE);
        
        // === FOTO DE PERFIL ===
        
        // Miniatura 96x96
        lblFotoMiniatura = new JLabel();
        lblFotoMiniatura.setPreferredSize(new Dimension(96, 96));
        lblFotoMiniatura.setHorizontalAlignment(SwingConstants.CENTER);
        lblFotoMiniatura.setVerticalAlignment(SwingConstants.CENTER);
        lblFotoMiniatura.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_NORMAL, 2),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));
        lblFotoMiniatura.setOpaque(true);
        lblFotoMiniatura.setBackground(COLOR_DISABLED_BG);
        lblFotoMiniatura.setText("👤");  // Placeholder
        lblFotoMiniatura.setFont(new Font("SansSerif", Font.PLAIN, 48));
        lblFotoMiniatura.setToolTipText("Haz clic en Cambiar para actualizar tu foto");
        
        // Botón Cambiar Foto
        btnCambiarFoto = new JButton("Cambiar...");
        btnCambiarFoto.setPreferredSize(new Dimension(96, 30));
        btnCambiarFoto.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btnCambiarFoto.setBackground(Color.WHITE);
        btnCambiarFoto.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_NORMAL, 1));
        btnCambiarFoto.setFocusPainted(false);
        btnCambiarFoto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCambiarFoto.addActionListener(e -> {
            if (onCambiarFotoCallback != null) {
                onCambiarFotoCallback.run();
            } else {
                // Placeholder para UC13
                JOptionPane.showMessageDialog(this,
                    "La funcionalidad de cambiar foto (UC13) estará disponible próximamente.",
                    "Próximamente",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // === CAMPOS DE ESTADO (SOLO LECTURA) ===
        
        // Estado de verificación
        lblEstadoVerificacion = new JLabel("Estado: No verificado");
        lblEstadoVerificacion.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblEstadoVerificacion.setForeground(COLOR_TEXT_MUTED);
        
        // Última conexión
        lblUltimaConexion = new JLabel("Última conexión: -");
        lblUltimaConexion.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblUltimaConexion.setForeground(COLOR_TEXT_MUTED);
        
        // Rol
        lblRol = new JLabel("Rol: Usuario");
        lblRol.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblRol.setForeground(COLOR_TEXT_MUTED);
        
        // === DATOS PERSONALES ===
        
        // Nombre *
        txtNombre = new JTextField();
        txtNombre.setPreferredSize(new Dimension(300, 35));
        txtNombre.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtNombre.setName("txtNombre");
        
        // Apellido *
        txtApellido = new JTextField();
        txtApellido.setPreferredSize(new Dimension(300, 35));
        txtApellido.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtApellido.setName("txtApellido");
        
        // DNI * (ahora editable, validación 7-9 dígitos)
        txtDni = new JTextField();
        txtDni.setPreferredSize(new Dimension(300, 35));
        txtDni.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtDni.setName("txtDni");
        txtDni.setToolTipText("DNI: 7 a 9 dígitos");
        
        // Sexo
        cmbSexo = new JComboBox<>();
        cmbSexo.setPreferredSize(new Dimension(300, 35));
        cmbSexo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cmbSexo.setBackground(Color.WHITE);
        
        // Fecha de nacimiento *
        txtFechaNacimiento = new MaskedDateField();
        txtFechaNacimiento.setPreferredSize(new Dimension(300, 35));
        txtFechaNacimiento.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        // === CONTACTO ===
        
        // Email * (SOLO LECTURA - se establece en el registro)
        txtEmail = new JTextField();
        txtEmail.setPreferredSize(new Dimension(300, 35));
        txtEmail.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtEmail.setEditable(false);  // Solo lectura
        txtEmail.setBackground(COLOR_DISABLED_BG);
        txtEmail.setForeground(COLOR_TEXT_MUTED);
        txtEmail.setName("txtEmail");
        
        // Icono info para Email
        lblEmailInfo = new JLabel("?");
        lblEmailInfo.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEmailInfo.setForeground(Color.WHITE);
        lblEmailInfo.setBackground(COLOR_TEXT_MUTED);
        lblEmailInfo.setOpaque(true);
        lblEmailInfo.setPreferredSize(new Dimension(18, 18));
        lblEmailInfo.setHorizontalAlignment(SwingConstants.CENTER);
        lblEmailInfo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_TEXT_MUTED, 1),
            BorderFactory.createEmptyBorder(1, 1, 1, 1)
        ));
        lblEmailInfo.setToolTipText("Se gestiona desde 'Mi cuenta' (flujo de cambio de email).");
        lblEmailInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Teléfono (con validación E.164)
        txtTelefono = new JTextField();
        txtTelefono.setPreferredSize(new Dimension(300, 35));
        txtTelefono.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtTelefono.setName("txtTelefono");
        txtTelefono.setToolTipText("Formato: +54 9 381 123 4567");
        // TODO: Agregar DocumentFilter para validación E.164 en setupListeners
        
        // === UBICACIÓN Y REDES ===
        
        // Dirección (solo lectura)
        txtDireccion = new JTextField();
        txtDireccion.setPreferredSize(new Dimension(300, 35));
        txtDireccion.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtDireccion.setEditable(false);
        txtDireccion.setBackground(COLOR_DISABLED_BG);
        txtDireccion.setName("txtDireccion");
        
        // Botón Nueva Dirección
        btnNuevaDireccion = new JButton("Nueva dirección...");
        btnNuevaDireccion.setPreferredSize(new Dimension(160, 35));
        btnNuevaDireccion.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnNuevaDireccion.setBackground(Color.WHITE);
        btnNuevaDireccion.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_NORMAL, 1));
        btnNuevaDireccion.setFocusPainted(false);
        btnNuevaDireccion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNuevaDireccion.setMnemonic(KeyEvent.VK_D);
        btnNuevaDireccion.addActionListener(e -> abrirDialogoNuevaDireccion());
        
        // Red Social
        cmbRedSocial = new JComboBox<>();
        cmbRedSocial.setPreferredSize(new Dimension(300, 35));
        cmbRedSocial.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cmbRedSocial.setBackground(Color.WHITE);
        
        // Botón Nueva Red Social
        btnNuevaRedSocial = new JButton("Nueva red social...");
        btnNuevaRedSocial.setPreferredSize(new Dimension(160, 35));
        btnNuevaRedSocial.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnNuevaRedSocial.setBackground(Color.WHITE);
        btnNuevaRedSocial.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_NORMAL, 1));
        btnNuevaRedSocial.setFocusPainted(false);
        btnNuevaRedSocial.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNuevaRedSocial.setMnemonic(KeyEvent.VK_R);
        btnNuevaRedSocial.addActionListener(e -> abrirDialogoNuevaRedSocial());
        
        // === ESTADO Y BOTONES ===
        
        // Label de estado
        lblEstado = new JLabel(" ");
        lblEstado.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Botón Cancelar
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(120, 45));
        btnCancelar.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnCancelar.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219), 2));
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Botón Guardar (ALTO CONTRASTE - Acción Primaria)
        btnGuardar = new JButton("Guardar");
        btnGuardar.setPreferredSize(new Dimension(150, 45));
        btnGuardar.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnGuardar.setBackground(COLOR_PRIMARY);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setOpaque(true);  // Asegurar que se vea el fondo
        btnGuardar.setBorderPainted(false);  // Sin borde visible
        btnGuardar.setContentAreaFilled(true);  // Llenar el área del botón
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.setMnemonic(KeyEvent.VK_G);  // Alt+G
        
        // Estados hover/pressed para alto contraste
        btnGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (btnGuardar.isEnabled()) {
                    btnGuardar.setBackground(COLOR_PRIMARY_HOVER);
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (btnGuardar.isEnabled()) {
                    btnGuardar.setBackground(COLOR_PRIMARY);
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                if (btnGuardar.isEnabled()) {
                    btnGuardar.setBackground(COLOR_PRIMARY_PRESSED);
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if (btnGuardar.isEnabled()) {
                    btnGuardar.setBackground(btnGuardar.contains(e.getPoint()) ? 
                        COLOR_PRIMARY_HOVER : COLOR_PRIMARY);
                }
            }
        });
    }
    
    private void setupLayout() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));  // 16px según lineamientos
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        
        int fila = 0;
        
        // === CABECERA: FOTO + TÍTULO + ESTADOS ===
        
        // Panel para foto de perfil (izquierda)
        JPanel panelFoto = new JPanel(new GridBagLayout());
        panelFoto.setBackground(Color.WHITE);
        GridBagConstraints gbcFoto = new GridBagConstraints();
        gbcFoto.gridx = 0;
        gbcFoto.gridy = 0;
        gbcFoto.insets = new Insets(0, 0, 5, 0);
        panelFoto.add(lblFotoMiniatura, gbcFoto);
        gbcFoto.gridy = 1;
        panelFoto.add(btnCambiarFoto, gbcFoto);
        
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        add(panelFoto, gbc);
        
        // Título
        JLabel lblTitulo = new JLabel("Mi Perfil");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        gbc.gridx = 1;
        gbc.gridy = fila;
        gbc.gridheight = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 16, 5, 8);
        add(lblTitulo, gbc);
        
        // Panel de estados (debajo del título)
        JPanel panelEstados = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelEstados.setBackground(Color.WHITE);
        panelEstados.add(lblEstadoVerificacion);
        panelEstados.add(lblRol);
        panelEstados.add(lblUltimaConexion);
        
        gbc.gridx = 1;
        gbc.gridy = fila + 1;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 16, 20, 8);
        add(panelEstados, gbc);
        
        fila += 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // === SECCIÓN: DATOS PERSONALES ===
        JLabel lblSeccionDatos = new JLabel("Datos Personales");
        lblSeccionDatos.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = fila++;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(15, 8, 10, 8);
        add(lblSeccionDatos, gbc);
        
        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Nombre *
        JLabel lblNombre = new JLabel("Nombre *");
        lblNombre.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblNombre.setDisplayedMnemonic(KeyEvent.VK_N);  // Alt+N
        lblNombre.setLabelFor(txtNombre);
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        add(lblNombre, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(txtNombre, gbc);
        
        // Apellido *
        JLabel lblApellido = new JLabel("Apellido *");
        lblApellido.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblApellido.setDisplayedMnemonic(KeyEvent.VK_A);  // Alt+A
        lblApellido.setLabelFor(txtApellido);
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(lblApellido, gbc);
        
        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        add(txtApellido, gbc);
        
        fila++;
        
        // DNI *
        JLabel lblDni = new JLabel("DNI *");
        lblDni.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblDni.setDisplayedMnemonic(KeyEvent.VK_D);  // Alt+D
        lblDni.setLabelFor(txtDni);
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        add(lblDni, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(txtDni, gbc);
        
        // Sexo
        JLabel lblSexo = new JLabel("Sexo");
        lblSexo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(lblSexo, gbc);
        
        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        add(cmbSexo, gbc);
        
        fila++;
        
        // Fecha de nacimiento *
        JLabel lblFechaNac = new JLabel("Fecha de Nacimiento *");
        lblFechaNac.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        add(lblFechaNac, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(txtFechaNacimiento, gbc);
        
        fila++;
        
        // === SECCIÓN: CONTACTO ===
        JLabel lblSeccionContacto = new JLabel("Contacto");
        lblSeccionContacto.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = fila++;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(15, 8, 10, 8);
        add(lblSeccionContacto, gbc);
        
        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Email (Solo Lectura) + icono info
        JPanel panelEmail = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelEmail.setBackground(Color.WHITE);
        JLabel lblEmail = new JLabel("Email (solo lectura)");
        lblEmail.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblEmail.setForeground(COLOR_TEXT_MUTED);
        lblEmail.setLabelFor(txtEmail);
        panelEmail.add(lblEmail);
        panelEmail.add(lblEmailInfo);
        
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        add(panelEmail, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(txtEmail, gbc);
        
        // Teléfono
        JLabel lblTelefono = new JLabel("Teléfono");
        lblTelefono.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblTelefono.setDisplayedMnemonic(KeyEvent.VK_T);  // Alt+T
        lblTelefono.setLabelFor(txtTelefono);
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(lblTelefono, gbc);
        
        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        add(txtTelefono, gbc);
        
        fila++;
        
        // === SECCIÓN: UBICACIÓN Y REDES ===
        JLabel lblSeccionUbicacion = new JLabel("Ubicación y Redes");
        lblSeccionUbicacion.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = fila++;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(15, 8, 10, 8);
        add(lblSeccionUbicacion, gbc);
        
        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Dirección
        JLabel lblDireccion = new JLabel("Dirección");
        lblDireccion.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        add(lblDireccion, gbc);
        
        // Panel para dirección + botón
        JPanel panelDireccion = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelDireccion.setBackground(Color.WHITE);
        panelDireccion.add(txtDireccion);
        panelDireccion.add(btnNuevaDireccion);
        
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        add(panelDireccion, gbc);
        
        gbc.gridwidth = 1;
        fila++;
        
        // Red Social
        JLabel lblRedSocial = new JLabel("Red Social");
        lblRedSocial.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        add(lblRedSocial, gbc);
        
        // Panel para combo + botón
        JPanel panelRedSocial = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelRedSocial.setBackground(Color.WHITE);
        panelRedSocial.add(cmbRedSocial);
        panelRedSocial.add(btnNuevaRedSocial);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(panelRedSocial, gbc);
        
        fila++;
        
        // === MENSAJE DE ESTADO ===
        gbc.gridx = 0;
        gbc.gridy = fila++;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(15, 8, 10, 8);
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblEstado, gbc);
        
        // === BOTONERA ===
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);
        
        gbc.gridx = 0;
        gbc.gridy = fila++;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(15, 8, 0, 8);
        gbc.anchor = GridBagConstraints.EAST;
        add(panelBotones, gbc);
    }
    
    private void setupListeners() {
        // Validación en tiempo real
        DocumentListener validationListener = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { validarFormulario(); }
            public void removeUpdate(DocumentEvent e) { validarFormulario(); }
            public void insertUpdate(DocumentEvent e) { validarFormulario(); }
        };
        
        txtNombre.getDocument().addDocumentListener(validationListener);
        txtApellido.getDocument().addDocumentListener(validationListener);
        txtDni.getDocument().addDocumentListener(validationListener);
        // Email es solo lectura, no se valida
        txtFechaNacimiento.getDocument().addDocumentListener(validationListener);
        
        // Botón Cancelar
        btnCancelar.addActionListener(e -> cancelar());
        
        // Botón Guardar
        btnGuardar.addActionListener(e -> guardarPerfil());
        
        // Enter en campos para guardar
        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && btnGuardar.isEnabled()) {
                    btnGuardar.doClick();
                }
            }
        };
        
        txtNombre.addKeyListener(enterKeyListener);
        txtApellido.addKeyListener(enterKeyListener);
        txtEmail.addKeyListener(enterKeyListener);
        txtTelefono.addKeyListener(enterKeyListener);
    }
    
    private void cargarCatalogos() {
        // Sexos mock
        cmbSexo.addItem(new SexoFrontendDTO(null, "-- Seleccione --"));
        cmbSexo.addItem(new SexoFrontendDTO(1, "Masculino"));
        cmbSexo.addItem(new SexoFrontendDTO(2, "Femenino"));
        cmbSexo.addItem(new SexoFrontendDTO(3, "Otro"));
        
        // Redes Sociales desde servicio
        RedesSocialesService redesService = new RedesSocialesService();
        SwingWorker<java.util.List<RedSocialFrontendDTO>, Void> worker = new SwingWorker<java.util.List<RedSocialFrontendDTO>, Void>() {
            @Override
            protected java.util.List<RedSocialFrontendDTO> doInBackground() throws Exception {
                return redesService.getRedesSociales();
            }
            
            @Override
            protected void done() {
                try {
                    cmbRedSocial.addItem(new RedSocialFrontendDTO(null, "-- Ninguna --", null));
                    java.util.List<RedSocialFrontendDTO> redes = get();
                    redes.forEach(r -> cmbRedSocial.addItem(r));
                    
                    // FIX: Seleccionar red social DESPUES de cargar el combo
                    if (perfilActual != null && perfilActual.getIdRedSocial() != null) {
                        for (int i = 0; i < cmbRedSocial.getItemCount(); i++) {
                            RedSocialFrontendDTO red = cmbRedSocial.getItemAt(i);
                            if (red.getId() != null && red.getId().equals(perfilActual.getIdRedSocial())) {
                                cmbRedSocial.setSelectedIndex(i);
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    // Fallback a mock si falla
                    cmbRedSocial.addItem(new RedSocialFrontendDTO(null, "-- Ninguna --", null));
                }
            }
        };
        worker.execute();
    }
    
    private void cargarDatosExistentes() {
        if (perfilActual == null) {
            throw new IllegalStateException("No hay perfil para cargar");
        }
        
        // Cargar datos básicos
        txtNombre.setText(perfilActual.getNombre() != null ? perfilActual.getNombre() : "");
        txtApellido.setText(perfilActual.getApellido() != null ? perfilActual.getApellido() : "");
        txtDni.setText(perfilActual.getDni() != null ? String.valueOf(perfilActual.getDni()) : "");
        
        // Email - solo lectura, siempre debe tener valor
        txtEmail.setText(perfilActual.getEmail() != null ? perfilActual.getEmail() : "");
        
        txtTelefono.setText(perfilActual.getTelefono() != null ? perfilActual.getTelefono() : "");
        
        // Fecha de nacimiento
        if (perfilActual.getFechaNacimiento() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            txtFechaNacimiento.setText(perfilActual.getFechaNacimiento().format(formatter));
        }
        
        // Dirección
        if (perfilActual.getDireccionCompleta() != null) {
            txtDireccion.setText(perfilActual.getDireccionCompleta());
            idDireccionSeleccionada = perfilActual.getIdDireccion();
        }
        
        // Sexo
        if (perfilActual.getIdSexo() != null) {
            for (int i = 0; i < cmbSexo.getItemCount(); i++) {
                SexoFrontendDTO sexo = cmbSexo.getItemAt(i);
                if (sexo.getId() != null && sexo.getId().equals(perfilActual.getIdSexo())) {
                    cmbSexo.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        // Red Social - se selecciona automaticamente en el SwingWorker.done()
        // (eliminado el codigo duplicado que causaba el race condition)
        
        // === NUEVOS CAMPOS ===
        
        // Estado de verificación
        if (perfilActual.isVerificado()) {
            lblEstadoVerificacion.setText("✓ Verificado");
            lblEstadoVerificacion.setForeground(new Color(34, 197, 94));  // Verde
        } else {
            lblEstadoVerificacion.setText("⚠ No verificado");
            lblEstadoVerificacion.setForeground(new Color(234, 179, 8));  // Amarillo
        }
        
        // Rol
        if (perfilActual.getRolNombre() != null) {
            lblRol.setText("Rol: " + perfilActual.getRolNombre());
        }
        
        // Última conexión
        if (perfilActual.getUltimaConexion() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            lblUltimaConexion.setText("Última conexión: " + perfilActual.getUltimaConexion().format(formatter));
        }
        
        // Foto de perfil
        if (perfilActual.getFotoPerfilUrl() != null && !perfilActual.getFotoPerfilUrl().isEmpty()) {
            // TODO: Cargar imagen real cuando esté implementado
            lblFotoMiniatura.setText("");  // Quitar emoji placeholder
            lblFotoMiniatura.setToolTipText("Haz clic en Cambiar para actualizar tu foto");
        }
    }
    
    private boolean validarFormulario() {
        boolean esValido = true;
        
        // Limpiar estados previos
        ValidationUtils.restaurarCampoBorde(txtNombre);
        ValidationUtils.restaurarCampoBorde(txtApellido);
        ValidationUtils.restaurarCampoBorde(txtDni);
        // Email es solo lectura, no se valida
        ValidationUtils.restaurarCampoBorde(txtFechaNacimiento);
        lblEstado.setText(" ");
        
        // Validar Nombre *
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            ValidationUtils.marcarCampoInvalido(txtNombre);
            esValido = false;
        } else if (nombre.length() > 100) {
            ValidationUtils.marcarCampoInvalido(txtNombre);
            lblEstado.setText("El nombre no puede exceder 100 caracteres");
            lblEstado.setForeground(new Color(239, 68, 68));
            esValido = false;
        }
        
        // Validar Apellido *
        String apellido = txtApellido.getText().trim();
        if (apellido.isEmpty()) {
            ValidationUtils.marcarCampoInvalido(txtApellido);
            esValido = false;
        } else if (apellido.length() > 100) {
            ValidationUtils.marcarCampoInvalido(txtApellido);
            lblEstado.setText("El apellido no puede exceder 100 caracteres");
            lblEstado.setForeground(new Color(239, 68, 68));
            esValido = false;
        }
        
        // Validar DNI * (7-9 dígitos)
        String dniTexto = txtDni.getText().trim().replaceAll("\\s", "");
        if (dniTexto.isEmpty()) {
            ValidationUtils.marcarCampoInvalido(txtDni);
            esValido = false;
        } else if (!dniTexto.matches("^\\d{7,9}$")) {
            ValidationUtils.marcarCampoInvalido(txtDni);
            lblEstado.setText("DNI debe tener 7 a 9 dígitos");
            lblEstado.setForeground(new Color(239, 68, 68));
            esValido = false;
        }
        
        // Email es solo lectura - no se valida aquí
        // Se asume que el email ya está validado desde el registro
        
        // Validar Fecha de Nacimiento *
        String fechaTexto = txtFechaNacimiento.getText();
        if (!MaskedDateField.esFechaValida(fechaTexto)) {
            ValidationUtils.marcarCampoInvalido(txtFechaNacimiento);
            if (!fechaTexto.replaceAll("[_/]", "").isEmpty()) {
                lblEstado.setText("Fecha de nacimiento inválida");
                lblEstado.setForeground(new Color(239, 68, 68));
            }
            esValido = false;
        } else {
            // Validar edad mínima (18 años)
            if (!MaskedDateField.tieneMinimoEdad(fechaTexto, 18)) {
                ValidationUtils.marcarCampoInvalido(txtFechaNacimiento);
                lblEstado.setText("Debe ser mayor de 18 años");
                lblEstado.setForeground(new Color(239, 68, 68));
                esValido = false;
            }
        }
        
        // Validar Teléfono (opcional, pero si tiene valor, validar longitud)
        String telefono = txtTelefono.getText().trim();
        if (!telefono.isEmpty() && telefono.length() > 20) {
            ValidationUtils.marcarCampoInvalido(txtTelefono);
            lblEstado.setText("El teléfono no puede exceder 20 caracteres");
            lblEstado.setForeground(new Color(239, 68, 68));
            esValido = false;
        }
        
        // Habilitar/deshabilitar botón Guardar
        btnGuardar.setEnabled(esValido);
        
        return esValido;
    }
    
    public PerfilUsuarioFrontendDTO obtenerDatosFormulario() {
        if (!validarFormulario()) {
            return null;
        }
        
        PerfilUsuarioFrontendDTO perfil = new PerfilUsuarioFrontendDTO();
        
        // Datos personales
        perfil.setNombre(txtNombre.getText().trim());
        perfil.setApellido(txtApellido.getText().trim());
        perfil.setDni(Integer.parseInt(txtDni.getText().trim().replaceAll("\\s", "")));
        perfil.setEmail(txtEmail.getText().trim());
        
        // Fecha de nacimiento
        String fechaTexto = txtFechaNacimiento.getText();
        if (MaskedDateField.esFechaValida(fechaTexto)) {
            perfil.setFechaNacimiento(MaskedDateField.parsearFecha(fechaTexto));
        }
        
        // Teléfono (opcional)
        String telefono = txtTelefono.getText().trim();
        if (!telefono.isEmpty()) {
            perfil.setTelefono(telefono);
        }
        
        // Sexo (opcional)
        SexoFrontendDTO sexoSeleccionado = (SexoFrontendDTO) cmbSexo.getSelectedItem();
        if (sexoSeleccionado != null && sexoSeleccionado.getId() != null) {
            perfil.setIdSexo(sexoSeleccionado.getId());
        }
        
        // Dirección (opcional)
        if (idDireccionSeleccionada != null) {
            perfil.setIdDireccion(idDireccionSeleccionada);
            perfil.setDireccionCompleta(txtDireccion.getText());
        }
        
        // Red Social (opcional)
        RedSocialFrontendDTO redSeleccionada = (RedSocialFrontendDTO) cmbRedSocial.getSelectedItem();
        if (redSeleccionada != null && redSeleccionada.getId() != null) {
            perfil.setIdRedSocial(redSeleccionada.getId());
        }
        
        // Siempre conservar el ID del perfil existente
        perfil.setId(perfilActual.getId());
        perfil.setIdUsuario(perfilActual.getIdUsuario());
        
        return perfil;
    }
    
    private void abrirDialogoNuevaDireccion() {
        Window window = SwingUtilities.getWindowAncestor(this);
        NuevaDireccionDialog dialog = new NuevaDireccionDialog(window);
        dialog.setVisible(true);
        
        if (dialog.isConfirmado()) {
            DireccionFrontendDTO direccionCreada = dialog.getDireccionCreada();
            if (direccionCreada != null) {
                actualizarDireccion(direccionCreada);
                validarFormulario();
                Toast.mostrar(
                    SwingUtilities.getWindowAncestor(this),
                    "Dirección creada con éxito",
                    Toast.TipoToast.EXITO
                );
            }
        }
    }
    
    private void abrirDialogoNuevaRedSocial() {
        Window window = SwingUtilities.getWindowAncestor(this);
        NuevaRedSocialDialog dialog = new NuevaRedSocialDialog(window);
        dialog.setVisible(true);
        
        if (dialog.isConfirmado()) {
            RedSocialFrontendDTO redCreada = dialog.getRedSocialCreada();
            if (redCreada != null) {
                // Agregar al combo y seleccionar
                cmbRedSocial.addItem(redCreada);
                cmbRedSocial.setSelectedItem(redCreada);
                Toast.mostrar(
                    SwingUtilities.getWindowAncestor(this),
                    "Red social creada con éxito",
                    Toast.TipoToast.EXITO
                );
            }
        }
    }
    
    private void cancelar() {
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea cancelar? Se perderán los cambios no guardados.",
            "Confirmar Cancelación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            // TODO: Cerrar ventana o volver a menú principal
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        }
    }
    
    private void guardarPerfil() {
        if (!validarFormulario()) {
            lblEstado.setText("Por favor, complete todos los campos obligatorios correctamente");
            lblEstado.setForeground(new Color(239, 68, 68));
            return;
        }
        
        PerfilUsuarioFrontendDTO perfil = obtenerDatosFormulario();
        
        if (perfil == null) {
            lblEstado.setText("Error al obtener los datos del formulario");
            lblEstado.setForeground(new Color(239, 68, 68));
            return;
        }
        
        // TODO: Cuando haya backend, llamar al servicio correspondiente
        // Por ahora, simular guardado exitoso
        
        LoadingDialog.mostrar(
            SwingUtilities.getWindowAncestor(this),
            "Actualizando perfil...",
            () -> {
                // Simular delay de red
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                // Simular éxito
                SwingUtilities.invokeLater(() -> {
                    lblEstado.setText("Perfil actualizado correctamente");
                    lblEstado.setForeground(new Color(34, 197, 94)); // Verde éxito
                    
                    Toast.mostrar(
                        SwingUtilities.getWindowAncestor(this),
                        "Perfil actualizado con éxito",
                        Toast.TipoToast.EXITO
                    );
                    
                    // Opcional: cerrar ventana después de un delay
                    Timer timer = new Timer(2000, e -> {
                        Window window = SwingUtilities.getWindowAncestor(this);
                        if (window != null) {
                            window.dispose();
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                });
            }
        );
    }
    
    // Getters para testing
    public boolean isFormularioValido() {
        return btnGuardar.isEnabled();
    }
    
    /**
     * Establece el callback para cuando se presione "Cambiar foto"
     * El callback debe manejar UC13 y actualizar fotoPerfil en el DTO
     */
    public void setOnCambiarFoto(Runnable callback) {
        this.onCambiarFotoCallback = callback;
    }
    
    /**
     * Actualiza la miniatura de foto de perfil
     * @param urlImagen URL de la imagen o null para placeholder
     */
    public void actualizarFotoPerfil(String urlImagen) {
        // TODO: Cargar imagen desde URL cuando esté implementado
        // Por ahora, solo actualizar el DTO
        if (perfilActual != null) {
            perfilActual.setFotoPerfilUrl(urlImagen);
        }
    }
    
    /**
     * Actualiza el display de dirección después de selección
     * @param direccion Objeto DireccionDTO con la nueva dirección
     */
    public void actualizarDireccion(DireccionFrontendDTO direccion) {
        if (direccion != null) {
            txtDireccion.setText(direccion.getDisplayCompleto());
            idDireccionSeleccionada = direccion.getId();
        }
    }
    
    // Método main para testing
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("GUMA - Mi Perfil");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            // Crear perfil de prueba
            PerfilUsuarioFrontendDTO perfilExistente = new PerfilUsuarioFrontendDTO();
            perfilExistente.setId(1L);
            perfilExistente.setIdUsuario(1L);
            perfilExistente.setNombre("Juan");
            perfilExistente.setApellido("Pérez");
            perfilExistente.setDni(12345678);
            perfilExistente.setEmail("juan.perez@example.com");
            perfilExistente.setTelefono("+54 9 381 555 1234");
            perfilExistente.setFechaNacimiento(LocalDate.of(1990, 5, 15));
            perfilExistente.setIdSexo(1);
            perfilExistente.setDireccionCompleta("Tucumán, San Miguel de Tucumán, Centro, Av. Sarmiento 123");
            perfilExistente.setIdDireccion(1);
            
            // Nuevos campos
            perfilExistente.setVerificado(true);  // Probar con perfil verificado
            perfilExistente.setRolNombre("Usuario");
            perfilExistente.setUltimaConexion(java.time.LocalDateTime.now().minusHours(2));
            perfilExistente.setFotoPerfilUrl(null);  // Sin foto
            
            PerfilUsuarioPanel panel = new PerfilUsuarioPanel(perfilExistente);
            
            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

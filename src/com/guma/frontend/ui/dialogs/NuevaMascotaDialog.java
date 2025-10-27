package com.guma.frontend.ui.dialogs;

import com.guma.frontend.dto.*;
import com.guma.frontend.service.MascotasService;
import com.guma.frontend.ui.MaskedDateField;
import com.guma.frontend.ui.Toast;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.List;

/**
 * Diálogo modal para registrar una nueva mascota
 * RF-04: Alta de Mascota
 * 
 * Características:
 * - 6 campos obligatorios: Nombre, Especie, Raza, Sexo, Estado Vital, Fecha de Nacimiento
 * - Edad calculada automáticamente desde la fecha
 * - Panel colapsable para 16 campos opcionales
 * - Filtrado dinámico Especie → Raza
 * - Campos condicionales para Hembra (Última Fecha Celo, Número Crías)
 * - Preview de imagen 96×96px
 * - Contraste AA en botón Guardar (#EC4899 rosa con texto blanco)
 */
public class NuevaMascotaDialog extends JDialog {
    
    // Colores según lineamientos
    private static final Color COLOR_PRIMARY = new Color(236, 72, 153); // #EC4899 rosa
    private static final Color COLOR_PRIMARY_HOVER = new Color(219, 39, 119);
    private static final Color COLOR_PRIMARY_PRESSED = new Color(190, 24, 93);
    private static final Color COLOR_FONDO_DISABLED = new Color(243, 244, 246);
    private static final Color COLOR_BORDE_NORMAL = new Color(209, 213, 219);
    private static final Color COLOR_BORDE_ERROR = new Color(239, 68, 68);

    // Servicio
    private MascotasService service;

    // Resultado
    private MascotaDTO mascotaCreada;

    // Componentes - Preview Imagen
    private JLabel lblImagenPreview;
    private JButton btnSeleccionarImagen;

    // Componentes - Obligatorios
    private JTextField txtNombre;
    private JComboBox<EspecieDTO> cmbEspecie;
    private JComboBox<RazaDTO> cmbRaza;
    private JComboBox<SexoDTO> cmbSexo;
    private JComboBox<EstadoVitalDTO> cmbEstadoVital;

    // Componentes - Fecha (obligatoria)
    private MaskedDateField txtFechaNacimiento;
    private JLabel lblEdadCalculada;

    // Panel colapsable
    private JButton btnToggleOpcionales;
    private JPanel panelOpcionales;
    private boolean opcionalesVisible = false;

    // Componentes - Opcionales
    private JSpinner spnPeso;
    private JComboBox<EstadoReproductivoDTO> cmbEstadoReproductivo;
    private JComboBox<NivelActividadDTO> cmbNivelActividad;
    private JComboBox<TipoAlimentacionDTO> cmbTipoAlimentacion;
    private JTextArea txtAlimentoDesc;
    private JComboBox<TemperamentoDTO> cmbTemperamento;
    private MaskedDateField txtUltimaFechaCelo;
    private JSpinner spnNumeroCrias;
    private JComboBox<ColorDTO> cmbColor;
    private JComboBox<TamanoDTO> cmbTamano;
    private JComboBox<RangoEdadDTO> cmbRangoEdad;
    private JTextArea txtDescripcion;

    // Labels condicionales
    private JLabel lblUltimaFechaCelo;
    private JLabel lblNumeroCrias;

    // Botones de acción
    private JButton btnGuardarMascota;
    private JButton btnCancelar;

    // Catálogos
    private List<EspecieDTO> especies;
    private List<RazaDTO> razas;

    public NuevaMascotaDialog(Frame parent, MascotasService service) {
        super(parent, "Registrar Nueva Mascota", true);
        this.service = service;
        cargarCatalogos();
        initComponents();
        setupLayout();
        setupListeners();
        setSize(800, 700);
        setLocationRelativeTo(parent);
    }

    private void cargarCatalogos() {
        especies = service.getEspecies();
        razas = service.getRazas();
    }

    private void initComponents() {
        // Preview imagen
        lblImagenPreview = new JLabel();
        lblImagenPreview.setPreferredSize(new Dimension(96, 96));
        lblImagenPreview.setIcon(crearIconoPlaceholder());
        lblImagenPreview.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_NORMAL));

        btnSeleccionarImagen = new JButton("Seleccionar imagen");
        btnSeleccionarImagen.setPreferredSize(new Dimension(150, 35));
        btnSeleccionarImagen.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnSeleccionarImagen.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Obligatorios
        txtNombre = new JTextField();
        txtNombre.setPreferredSize(new Dimension(450, 35));
        txtNombre.setFont(new Font("SansSerif", Font.PLAIN, 14));

        cmbEspecie = new JComboBox<>();
        especies.forEach(e -> cmbEspecie.addItem(e));
        cmbEspecie.setPreferredSize(new Dimension(450, 35));
        cmbEspecie.setFont(new Font("SansSerif", Font.PLAIN, 14));

        cmbRaza = new JComboBox<>();
        cmbRaza.setPreferredSize(new Dimension(450, 35));
        cmbRaza.setFont(new Font("SansSerif", Font.PLAIN, 14));

        cmbSexo = new JComboBox<>();
        service.getSexos().forEach(s -> cmbSexo.addItem(s));
        cmbSexo.setPreferredSize(new Dimension(450, 35));
        cmbSexo.setFont(new Font("SansSerif", Font.PLAIN, 14));

        cmbEstadoVital = new JComboBox<>();
        service.getEstadosVitales().forEach(e -> cmbEstadoVital.addItem(e));
        cmbEstadoVital.setPreferredSize(new Dimension(450, 35));
        cmbEstadoVital.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Fecha de Nacimiento (obligatoria)
        txtFechaNacimiento = new MaskedDateField();
        txtFechaNacimiento.setPreferredSize(new Dimension(450, 35));
        txtFechaNacimiento.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Label para mostrar edad calculada
        lblEdadCalculada = new JLabel("");
        lblEdadCalculada.setFont(new Font("SansSerif", Font.ITALIC, 12));
        lblEdadCalculada.setForeground(Color.GRAY);

        // Botón toggle
        btnToggleOpcionales = new JButton("▶ Mostrar Datos Opcionales");
        btnToggleOpcionales.setPreferredSize(new Dimension(450, 40));
        btnToggleOpcionales.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnToggleOpcionales.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnToggleOpcionales.setFocusPainted(false);
        btnToggleOpcionales.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_NORMAL, 2));
        btnToggleOpcionales.setContentAreaFilled(false);

        // Panel opcionales
        panelOpcionales = new JPanel(new GridBagLayout());
        panelOpcionales.setBackground(Color.WHITE);
        panelOpcionales.setVisible(false);

        // Opcionales
        SpinnerNumberModel modelPeso = new SpinnerNumberModel(0.0, 0.0, 500.0, 0.1);
        spnPeso = new JSpinner(modelPeso);
        spnPeso.setPreferredSize(new Dimension(450, 35));
        spnPeso.setFont(new Font("SansSerif", Font.PLAIN, 14));

        cmbEstadoReproductivo = new JComboBox<>();
        cmbEstadoReproductivo.addItem(null); // Opcional
        service.getEstadosReproductivos().forEach(e -> cmbEstadoReproductivo.addItem(e));
        cmbEstadoReproductivo.setPreferredSize(new Dimension(450, 35));
        cmbEstadoReproductivo.setFont(new Font("SansSerif", Font.PLAIN, 14));

        cmbNivelActividad = new JComboBox<>();
        cmbNivelActividad.addItem(null);
        service.getNivelesActividad().forEach(n -> cmbNivelActividad.addItem(n));
        cmbNivelActividad.setPreferredSize(new Dimension(450, 35));
        cmbNivelActividad.setFont(new Font("SansSerif", Font.PLAIN, 14));

        cmbTipoAlimentacion = new JComboBox<>();
        cmbTipoAlimentacion.addItem(null);
        service.getTiposAlimentacion().forEach(t -> cmbTipoAlimentacion.addItem(t));
        cmbTipoAlimentacion.setPreferredSize(new Dimension(450, 35));
        cmbTipoAlimentacion.setFont(new Font("SansSerif", Font.PLAIN, 14));

        txtAlimentoDesc = new JTextArea(3, 20);
        txtAlimentoDesc.setLineWrap(true);
        txtAlimentoDesc.setWrapStyleWord(true);
        txtAlimentoDesc.setFont(new Font("SansSerif", Font.PLAIN, 14));

        cmbTemperamento = new JComboBox<>();
        cmbTemperamento.addItem(null);
        service.getTemperamentos().forEach(t -> cmbTemperamento.addItem(t));
        cmbTemperamento.setPreferredSize(new Dimension(450, 35));
        cmbTemperamento.setFont(new Font("SansSerif", Font.PLAIN, 14));

        txtUltimaFechaCelo = new MaskedDateField();
        txtUltimaFechaCelo.setPreferredSize(new Dimension(450, 35));
        txtUltimaFechaCelo.setFont(new Font("SansSerif", Font.PLAIN, 14));

        SpinnerNumberModel modelCrias = new SpinnerNumberModel(0, 0, 20, 1);
        spnNumeroCrias = new JSpinner(modelCrias);
        spnNumeroCrias.setPreferredSize(new Dimension(450, 35));
        spnNumeroCrias.setFont(new Font("SansSerif", Font.PLAIN, 14));

        cmbColor = new JComboBox<>();
        cmbColor.addItem(null);
        service.getColores().forEach(c -> cmbColor.addItem(c));
        cmbColor.setPreferredSize(new Dimension(450, 35));
        cmbColor.setFont(new Font("SansSerif", Font.PLAIN, 14));

        cmbTamano = new JComboBox<>();
        cmbTamano.addItem(null);
        service.getTamanos().forEach(t -> cmbTamano.addItem(t));
        cmbTamano.setPreferredSize(new Dimension(450, 35));
        cmbTamano.setFont(new Font("SansSerif", Font.PLAIN, 14));

        cmbRangoEdad = new JComboBox<>();
        cmbRangoEdad.addItem(null);
        service.getRangosEdad().forEach(r -> cmbRangoEdad.addItem(r));
        cmbRangoEdad.setPreferredSize(new Dimension(450, 35));
        cmbRangoEdad.setFont(new Font("SansSerif", Font.PLAIN, 14));

        txtDescripcion = new JTextArea(4, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Botón guardar con texto blanco forzado
        btnGuardarMascota = new JButton("Guardar Mascota") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Pintar texto blanco manualmente
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString(getText(), x, y);
                g2d.dispose();
            }
        };
        btnGuardarMascota.setPreferredSize(new Dimension(250, 45));
        btnGuardarMascota.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnGuardarMascota.setBackground(COLOR_PRIMARY);
        btnGuardarMascota.setOpaque(true);
        btnGuardarMascota.setBorderPainted(false);
        btnGuardarMascota.setContentAreaFilled(true);
        btnGuardarMascota.setFocusPainted(false);
        btnGuardarMascota.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardarMascota.setEnabled(false);

        // Botón cancelar
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(160, 45));
        btnCancelar.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnCancelar.setBackground(Color.WHITE);
        btnCancelar.setForeground(Color.DARK_GRAY);
        btnCancelar.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_NORMAL, 2));
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void setupLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Content con scroll
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);

        // Header con imagen
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.add(lblImagenPreview);
        headerPanel.add(btnSeleccionarImagen);
        contentPanel.add(headerPanel, gbc);

        // Separador
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 15, 0);
        JSeparator sep1 = new JSeparator();
        contentPanel.add(sep1, gbc);

        // Obligatorios
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 5, 0);
        JLabel lblSeccionOblig = new JLabel("Datos Obligatorios");
        lblSeccionOblig.setFont(new Font("SansSerif", Font.BOLD, 16));
        contentPanel.add(lblSeccionOblig, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 5, 0);
        contentPanel.add(crearCampo("Nombre *", txtNombre), gbc);

        gbc.gridy++;
        contentPanel.add(crearCampo("Especie *", cmbEspecie), gbc);

        gbc.gridy++;
        JPanel panelRaza = crearCampo("Raza *", cmbRaza);
        JLabel lblHelper = new JLabel("<html><i>Se lista según especie seleccionada</i></html>");
        lblHelper.setFont(new Font("SansSerif", Font.ITALIC, 11));
        lblHelper.setForeground(Color.GRAY);
        ((JPanel)panelRaza.getComponent(1)).add(lblHelper);
        contentPanel.add(panelRaza, gbc);

        gbc.gridy++;
        contentPanel.add(crearCampo("Sexo *", cmbSexo), gbc);

        gbc.gridy++;
        contentPanel.add(crearCampo("Estado Vital *", cmbEstadoVital), gbc);

        // Fecha de Nacimiento (obligatoria con edad calculada)
        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 5, 0);
        JPanel panelFecha = crearCampo("Fecha de Nacimiento *", txtFechaNacimiento);
        ((JPanel)panelFecha.getComponent(1)).add(lblEdadCalculada);
        contentPanel.add(panelFecha, gbc);

        // Botón toggle
        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 10, 0);
        contentPanel.add(btnToggleOpcionales, gbc);

        // Panel opcionales
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 0, 0);
        setupPanelOpcionales();
        contentPanel.add(panelOpcionales, gbc);

        // Scroll
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Footer con botones
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, COLOR_BORDE_NORMAL));
        footerPanel.add(btnCancelar);
        footerPanel.add(btnGuardarMascota);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void setupPanelOpcionales() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 5, 0);

        panelOpcionales.add(crearCampo("Peso (kg)", spnPeso), gbc);

        gbc.gridy++;
        panelOpcionales.add(crearCampo("Estado Reproductivo", cmbEstadoReproductivo), gbc);

        gbc.gridy++;
        panelOpcionales.add(crearCampo("Nivel de Actividad", cmbNivelActividad), gbc);

        gbc.gridy++;
        panelOpcionales.add(crearCampo("Tipo de Alimentación", cmbTipoAlimentacion), gbc);

        gbc.gridy++;
        JScrollPane spAlimento = new JScrollPane(txtAlimentoDesc);
        spAlimento.setPreferredSize(new Dimension(450, 70));
        panelOpcionales.add(crearCampo("Descripción del Alimento", spAlimento), gbc);

        gbc.gridy++;
        panelOpcionales.add(crearCampo("Temperamento", cmbTemperamento), gbc);

        // Condicionales para Hembra
        gbc.gridy++;
        lblUltimaFechaCelo = new JLabel("Última Fecha de Celo");
        lblUltimaFechaCelo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JPanel panelCelo = new JPanel(new BorderLayout(0, 5));
        panelCelo.setBackground(Color.WHITE);
        panelCelo.add(lblUltimaFechaCelo, BorderLayout.NORTH);
        JPanel wrapperCelo = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapperCelo.setBackground(Color.WHITE);
        wrapperCelo.add(txtUltimaFechaCelo);
        panelCelo.add(wrapperCelo, BorderLayout.CENTER);
        panelCelo.setVisible(false);
        panelOpcionales.add(panelCelo, gbc);

        gbc.gridy++;
        lblNumeroCrias = new JLabel("Número de Crías");
        lblNumeroCrias.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JPanel panelCrias = new JPanel(new BorderLayout(0, 5));
        panelCrias.setBackground(Color.WHITE);
        panelCrias.add(lblNumeroCrias, BorderLayout.NORTH);
        JPanel wrapperCrias = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapperCrias.setBackground(Color.WHITE);
        wrapperCrias.add(spnNumeroCrias);
        panelCrias.add(wrapperCrias, BorderLayout.CENTER);
        panelCrias.setVisible(false);
        panelOpcionales.add(panelCrias, gbc);

        gbc.gridy++;
        panelOpcionales.add(crearCampo("Color", cmbColor), gbc);

        gbc.gridy++;
        panelOpcionales.add(crearCampo("Tamaño", cmbTamano), gbc);

        gbc.gridy++;
        panelOpcionales.add(crearCampo("Rango de Edad", cmbRangoEdad), gbc);

        gbc.gridy++;
        JScrollPane spDesc = new JScrollPane(txtDescripcion);
        spDesc.setPreferredSize(new Dimension(450, 90));
        panelOpcionales.add(crearCampo("Descripción", spDesc), gbc);
    }

    private JPanel crearCampo(String label, JComponent campo) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(Color.WHITE);
        
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(lbl, BorderLayout.NORTH);
        
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapper.setBackground(Color.WHITE);
        wrapper.add(campo);
        panel.add(wrapper, BorderLayout.CENTER);
        
        return panel;
    }

    private ImageIcon crearIconoPlaceholder() {
        BufferedImage img = new BufferedImage(96, 96, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(COLOR_FONDO_DISABLED);
        g2d.fillRect(0, 0, 96, 96);
        g2d.setColor(COLOR_BORDE_NORMAL);
        g2d.drawRect(0, 0, 95, 95);
        g2d.setColor(Color.GRAY);
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 11));
        g2d.drawString("Sin imagen", 22, 50);
        g2d.dispose();
        return new ImageIcon(img);
    }

    private void setupListeners() {
        // Toggle opcionales
        btnToggleOpcionales.addActionListener(e -> toggleOpcionales());

        // Filtrado Especie → Raza
        cmbEspecie.addActionListener(e -> filtrarRazasPorEspecie());

        // Calcular edad al cambiar fecha
        txtFechaNacimiento.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { calcularYMostrarEdad(); validarFormulario(); }
            public void removeUpdate(DocumentEvent e) { calcularYMostrarEdad(); validarFormulario(); }
            public void insertUpdate(DocumentEvent e) { calcularYMostrarEdad(); validarFormulario(); }
        });

        // Condicionales Hembra
        cmbSexo.addActionListener(e -> actualizarCamposCondicionales());
        cmbEstadoReproductivo.addActionListener(e -> actualizarCamposCondicionales());

        // Validación en tiempo real
        DocumentListener validationListener = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { validarFormulario(); }
            public void removeUpdate(DocumentEvent e) { validarFormulario(); }
            public void insertUpdate(DocumentEvent e) { validarFormulario(); }
        };
        txtNombre.getDocument().addDocumentListener(validationListener);

        cmbEspecie.addActionListener(e -> validarFormulario());
        cmbRaza.addActionListener(e -> validarFormulario());
        cmbSexo.addActionListener(e -> validarFormulario());
        cmbEstadoVital.addActionListener(e -> validarFormulario());

        // Hover en botón guardar
        btnGuardarMascota.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btnGuardarMascota.isEnabled()) {
                    btnGuardarMascota.setBackground(COLOR_PRIMARY_HOVER);
                }
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnGuardarMascota.setBackground(COLOR_PRIMARY);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (btnGuardarMascota.isEnabled()) {
                    btnGuardarMascota.setBackground(COLOR_PRIMARY_PRESSED);
                }
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (btnGuardarMascota.isEnabled()) {
                    btnGuardarMascota.setBackground(COLOR_PRIMARY_HOVER);
                }
            }
        });

        // Acción cancelar
        btnCancelar.addActionListener(e -> {
            mascotaCreada = null;
            dispose();
        });

        // Acción guardar
        btnGuardarMascota.addActionListener(e -> guardarMascota());
    }

    private void toggleOpcionales() {
        opcionalesVisible = !opcionalesVisible;
        panelOpcionales.setVisible(opcionalesVisible);
        btnToggleOpcionales.setText(opcionalesVisible ? 
            "▼ Ocultar Datos Opcionales" : 
            "▶ Mostrar Datos Opcionales");
        revalidate();
        repaint();
    }

    private void filtrarRazasPorEspecie() {
        EspecieDTO especieSeleccionada = (EspecieDTO) cmbEspecie.getSelectedItem();
        cmbRaza.removeAllItems();
        
        if (especieSeleccionada != null) {
            List<RazaDTO> razasFiltradas = service.getRazasPorEspecie(especieSeleccionada.getId());
            razasFiltradas.forEach(r -> cmbRaza.addItem(r));
        }
        
        validarFormulario();
    }

    private void calcularYMostrarEdad() {
        String fechaTexto = txtFechaNacimiento.getText().trim();
        
        if (!fechaTexto.isEmpty() && !fechaTexto.equals("__/__/____") && MaskedDateField.esFechaValida(fechaTexto)) {
            try {
                LocalDate fechaNac = MaskedDateField.parsearFecha(fechaTexto);
                LocalDate hoy = LocalDate.now();
                
                if (!fechaNac.isAfter(hoy)) {
                    int años = hoy.getYear() - fechaNac.getYear();
                    int meses = hoy.getMonthValue() - fechaNac.getMonthValue();
                    
                    if (meses < 0 || (meses == 0 && hoy.getDayOfMonth() < fechaNac.getDayOfMonth())) {
                        años--;
                    }
                    
                    if (años == 0) {
                        lblEdadCalculada.setText("(menos de 1 año)");
                    } else if (años == 1) {
                        lblEdadCalculada.setText("(1 año)");
                    } else {
                        lblEdadCalculada.setText("(" + años + " años)");
                    }
                } else {
                    lblEdadCalculada.setText("");
                }
            } catch (Exception ex) {
                lblEdadCalculada.setText("");
            }
        } else {
            lblEdadCalculada.setText("");
        }
    }

    private void actualizarCamposCondicionales() {
        SexoDTO sexo = (SexoDTO) cmbSexo.getSelectedItem();
        EstadoReproductivoDTO estado = (EstadoReproductivoDTO) cmbEstadoReproductivo.getSelectedItem();

        boolean esHembra = sexo != null && "Hembra".equals(sexo.getSexo());
        boolean estadoCompatible = estado != null && 
            (estado.getEstado().contains("celo") || 
             estado.getEstado().contains("Gestante") || 
             estado.getEstado().contains("Post parto"));

        // Última Fecha Celo: visible si Hembra y estado compatible
        Component[] comps = panelOpcionales.getComponents();
        for (Component c : comps) {
            if (c instanceof JPanel) {
                JPanel p = (JPanel) c;
                Component[] subComps = p.getComponents();
                for (Component sub : subComps) {
                    if (sub == lblUltimaFechaCelo) {
                        p.setVisible(esHembra && estadoCompatible);
                        break;
                    }
                    if (sub == lblNumeroCrias) {
                        // Número Crías: visible si Hembra y (Gestante o Post parto)
                        boolean mostrarCrias = esHembra && estado != null && 
                            (estado.getEstado().contains("Gestante") || 
                             estado.getEstado().contains("Post parto"));
                        p.setVisible(mostrarCrias);
                        break;
                    }
                }
            }
        }

        revalidate();
        repaint();
    }

    private void validarFormulario() {
        String nombre = txtNombre.getText().trim();
        boolean nombreValido = nombre.length() >= 2;

        EspecieDTO especie = (EspecieDTO) cmbEspecie.getSelectedItem();
        RazaDTO raza = (RazaDTO) cmbRaza.getSelectedItem();
        SexoDTO sexo = (SexoDTO) cmbSexo.getSelectedItem();
        EstadoVitalDTO estadoVital = (EstadoVitalDTO) cmbEstadoVital.getSelectedItem();

        String fechaTexto = txtFechaNacimiento.getText().trim();
        boolean tieneFecha = !fechaTexto.isEmpty() && !fechaTexto.equals("__/__/____");
        boolean fechaValida = tieneFecha && MaskedDateField.esFechaValida(fechaTexto);

        boolean formularioValido = nombreValido && 
                                   especie != null && 
                                   raza != null && 
                                   sexo != null && 
                                   estadoVital != null && 
                                   fechaValida;

        btnGuardarMascota.setEnabled(formularioValido);
    }

    private void guardarMascota() {
        try {
            NuevaMascotaFrontendRequest request = obtenerDatosFormulario();
            
            // Validaciones adicionales
            String fechaTexto = txtFechaNacimiento.getText().trim();
            if (!fechaTexto.isEmpty() && !fechaTexto.equals("__/__/____")) {
                if (!MaskedDateField.esFechaValida(fechaTexto)) {
                    Toast.mostrar(this, "Fecha de nacimiento inválida", Toast.TipoToast.ERROR);
                    return;
                }
                LocalDate fecha = MaskedDateField.parsearFecha(fechaTexto);
                if (fecha.isAfter(LocalDate.now())) {
                    Toast.mostrar(this, "Fecha de nacimiento no puede ser futura", Toast.TipoToast.ERROR);
                    return;
                }
            }

            Double peso = (Double) spnPeso.getValue();
            if (peso != null && peso < 0) {
                Toast.mostrar(this, "Peso inválido", Toast.TipoToast.ERROR);
                return;
            }

            mascotaCreada = service.crearMascota(request);
            Toast.mostrar(this, "Mascota registrada exitosamente", Toast.TipoToast.EXITO);
            dispose();

        } catch (IllegalArgumentException ex) {
            Toast.mostrar(this, ex.getMessage(), Toast.TipoToast.ERROR);
        } catch (Exception ex) {
            Toast.mostrar(this, "Error al guardar mascota", Toast.TipoToast.ERROR);
            ex.printStackTrace();
        }
    }

    private NuevaMascotaFrontendRequest obtenerDatosFormulario() {
        NuevaMascotaFrontendRequest request = new NuevaMascotaFrontendRequest();
        
        request.setNombre(txtNombre.getText().trim());
        
        RazaDTO raza = (RazaDTO) cmbRaza.getSelectedItem();
        if (raza != null) request.setIdRaza(raza.getId());
        
        SexoDTO sexo = (SexoDTO) cmbSexo.getSelectedItem();
        if (sexo != null) request.setIdSexo(sexo.getId());
        
        EstadoVitalDTO estadoVital = (EstadoVitalDTO) cmbEstadoVital.getSelectedItem();
        if (estadoVital != null) request.setIdEstadoVital(estadoVital.getId());

        // Fecha de Nacimiento (obligatoria)
        String fechaTexto = txtFechaNacimiento.getText().trim();
        if (!fechaTexto.isEmpty() && !fechaTexto.equals("__/__/____") && MaskedDateField.esFechaValida(fechaTexto)) {
            request.setFechaNacimiento(MaskedDateField.parsearFecha(fechaTexto));
        }

        // Opcionales
        Double peso = (Double) spnPeso.getValue();
        if (peso != null && peso > 0) request.setPeso(peso);

        EstadoReproductivoDTO estadoRepr = (EstadoReproductivoDTO) cmbEstadoReproductivo.getSelectedItem();
        if (estadoRepr != null) request.setIdEstadoReproductivo(estadoRepr.getId());

        NivelActividadDTO nivel = (NivelActividadDTO) cmbNivelActividad.getSelectedItem();
        if (nivel != null) request.setIdNivelActividad(nivel.getId());

        TipoAlimentacionDTO tipoAlim = (TipoAlimentacionDTO) cmbTipoAlimentacion.getSelectedItem();
        if (tipoAlim != null) request.setIdTipoAlimentacion(tipoAlim.getId());

        String alimentoDesc = txtAlimentoDesc.getText().trim();
        if (!alimentoDesc.isEmpty()) request.setAlimentoDescripcion(alimentoDesc);

        TemperamentoDTO temp = (TemperamentoDTO) cmbTemperamento.getSelectedItem();
        if (temp != null) request.setIdTemperamento(temp.getId());

        String celoTexto = txtUltimaFechaCelo.getText().trim();
        if (!celoTexto.isEmpty() && !celoTexto.equals("__/__/____") && MaskedDateField.esFechaValida(celoTexto)) {
            request.setUltimaFechaCelo(MaskedDateField.parsearFecha(celoTexto));
        }

        Integer crias = (Integer) spnNumeroCrias.getValue();
        if (crias != null && crias > 0) request.setNumeroCrias(crias);

        ColorDTO color = (ColorDTO) cmbColor.getSelectedItem();
        if (color != null) request.setIdColor(color.getId());

        TamanoDTO tamano = (TamanoDTO) cmbTamano.getSelectedItem();
        if (tamano != null) request.setIdTamano(tamano.getId());

        RangoEdadDTO rangoEdad = (RangoEdadDTO) cmbRangoEdad.getSelectedItem();
        if (rangoEdad != null) request.setIdRangoEdad(rangoEdad.getId());

        String desc = txtDescripcion.getText().trim();
        if (!desc.isEmpty()) request.setDescripcion(desc);

        return request;
    }

    public MascotaDTO getMascotaCreada() {
        return mascotaCreada;
    }

    // Main para testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MascotasService service = new MascotasService();
            NuevaMascotaDialog dialog = new NuevaMascotaDialog(null, service);
            dialog.setVisible(true);
            
            if (dialog.getMascotaCreada() != null) {
                MascotaDTO m = dialog.getMascotaCreada();
                System.out.println("Mascota creada: " + m.getNombre());
                System.out.println("Raza: " + m.getRazaNombre());
                System.out.println("Sexo: " + m.getSexoNombre());
                System.out.println("Estado: " + m.getEstadoVitalNombre());
            }
            
            System.exit(0);
        });
    }
}

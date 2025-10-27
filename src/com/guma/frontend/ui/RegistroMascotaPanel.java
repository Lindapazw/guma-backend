package com.guma.frontend.ui;

import com.guma.frontend.dto.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Panel para registrar una nueva mascota (RF-04)
 * Incluye campos obligatorios y panel opcional colapsable
 */
public class RegistroMascotaPanel extends JPanel {
    
    // Design tokens (rosa)
    private static final Color COLOR_PRIMARIO = new Color(236, 72, 153); // #EC4899
    private static final Color COLOR_HOVER = new Color(219, 39, 119); // #DB2777
    private static final Color COLOR_PRESSED = new Color(190, 24, 93); // #BE185D
    private static final Color COLOR_FONDO_INPUT = new Color(249, 250, 251);
    private static final Color COLOR_BORDE = new Color(229, 231, 235);
    private static final Color COLOR_TEXTO_DESHABILITADO = new Color(156, 163, 175);
    private static final Color COLOR_FONDO_DESHABILITADO = new Color(243, 244, 246);
    
    // Componentes obligatorios
    private JLabel lblImagen;
    private JButton btnSeleccionarImagen;
    private JTextField txtNombre;
    private JComboBox<EspecieFrontendDTO> cmbEspecie;
    private JComboBox<RazaFrontendDTO> cmbRaza;
    private JComboBox<SexoFrontendDTO> cmbSexo;
    private JComboBox<EstadoVitalFrontendDTO> cmbEstadoVital;
    
    // Panel opcional colapsable
    private JPanel panelOpcional;
    private JButton btnToggleOpcional;
    private boolean panelOpcionalVisible = false;
    
    // Componentes opcionales
    private MaskedDateField txtFechaNacimiento;
    private JSpinner spnEdadAproximada;
    private JTextField txtPeso;
    private JComboBox<EstadoReproductivoFrondendDTO> cmbEstadoReproductivo;
    private JComboBox<NivelActividadFrontendDTO> cmbNivelActividad;
    private JComboBox<TipoAlimentacionFrontendDTO> cmbTipoAlimentacion;
    private JTextArea txtAlimentoDescripcion;
    private JComboBox<TemperamentoFrontendDTO> cmbTemperamento;
    private MaskedDateField txtUltimaFechaCelo;
    private JSpinner spnNumeroCrias;
    private JTextArea txtDescripcion;
    private JTextField txtQrId;
    private JComboBox<ColorFrontendDTO> cmbColor;
    private JComboBox<TamanoFrontendDTO> cmbTamano;
    private JComboBox<RangoEdadFrontendDTO> cmbRangoEdad;
    
    // Botón guardar
    private JButton btnGuardar;
    
    // Catálogos (cargados desde backend)
    private List<EspecieFrontendDTO> especies = new ArrayList<>();
    private List<RazaFrontendDTO> razas = new ArrayList<>();
    private List<SexoFrontendDTO> sexos = new ArrayList<>();
    private List<EstadoVitalFrontendDTO> estadosVitales = new ArrayList<>();
    private List<EstadoReproductivoFrondendDTO> estadosReproductivos = new ArrayList<>();
    private List<NivelActividadFrontendDTO> nivelesActividad = new ArrayList<>();
    private List<TipoAlimentacionFrontendDTO> tiposAlimentacion = new ArrayList<>();
    private List<TemperamentoFrontendDTO> temperamentos = new ArrayList<>();
    private List<ColorFrontendDTO> colores = new ArrayList<>();
    private List<TamanoFrontendDTO> tamanos = new ArrayList<>();
    private List<RangoEdadFrontendDTO> rangosEdad = new ArrayList<>();
    
    // Callbacks
    private Runnable onSeleccionarImagen;
    private Runnable onGuardar;
    
    // Control de imagen
    private Integer imagenSeleccionadaId;
    
    public RegistroMascotaPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        inicializarComponentes();
        construirUI();
        configurarValidaciones();
        cargarCatalogos();
    }
    
    private void inicializarComponentes() {
        // Imagen
        lblImagen = new JLabel();
        lblImagen.setPreferredSize(new Dimension(96, 96));
        lblImagen.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 2));
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setIcon(crearIconoPlaceholder());
        lblImagen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblImagen.setToolTipText("Click para seleccionar imagen");
        lblImagen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (onSeleccionarImagen != null) {
                    onSeleccionarImagen.run();
                }
            }
        });
        
        btnSeleccionarImagen = new JButton("Seleccionar Imagen");
        btnSeleccionarImagen.addActionListener(e -> {
            if (onSeleccionarImagen != null) {
                onSeleccionarImagen.run();
            }
        });
        
        // Obligatorios (inputs grandes: 45px de alto)
        txtNombre = new JTextField(20);
        txtNombre.setBackground(COLOR_FONDO_INPUT);
        txtNombre.setPreferredSize(new Dimension(450, 45));
        txtNombre.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        cmbEspecie = new JComboBox<>();
        cmbEspecie.setBackground(COLOR_FONDO_INPUT);
        cmbEspecie.setPreferredSize(new Dimension(450, 45));
        cmbEspecie.setFont(new Font("SansSerif", Font.PLAIN, 16));
        cmbEspecie.addActionListener(e -> filtrarRazasPorEspecie());
        
        cmbRaza = new JComboBox<>();
        cmbRaza.setBackground(COLOR_FONDO_INPUT);
        cmbRaza.setPreferredSize(new Dimension(450, 45));
        cmbRaza.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        cmbSexo = new JComboBox<>();
        cmbSexo.setBackground(COLOR_FONDO_INPUT);
        cmbSexo.setPreferredSize(new Dimension(450, 45));
        cmbSexo.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        cmbEstadoVital = new JComboBox<>();
        cmbEstadoVital.setBackground(COLOR_FONDO_INPUT);
        cmbEstadoVital.setPreferredSize(new Dimension(450, 45));
        cmbEstadoVital.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        // Opcionales (inputs grandes: 45px de alto)
        txtFechaNacimiento = new MaskedDateField();
        txtFechaNacimiento.setBackground(COLOR_FONDO_INPUT);
        txtFechaNacimiento.setPreferredSize(new Dimension(450, 45));
        txtFechaNacimiento.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtFechaNacimiento.setToolTipText("dd/MM/yyyy - XOR con Edad Aproximada");
        
        spnEdadAproximada = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));
        spnEdadAproximada.setPreferredSize(new Dimension(450, 45));
        ((JSpinner.DefaultEditor) spnEdadAproximada.getEditor()).getTextField().setBackground(COLOR_FONDO_INPUT);
        ((JSpinner.DefaultEditor) spnEdadAproximada.getEditor()).getTextField().setFont(new Font("SansSerif", Font.PLAIN, 16));
        spnEdadAproximada.setToolTipText("Años - XOR con Fecha de Nacimiento");
        
        txtPeso = new JTextField(10);
        txtPeso.setBackground(COLOR_FONDO_INPUT);
        txtPeso.setPreferredSize(new Dimension(450, 45));
        txtPeso.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtPeso.setToolTipText("Peso en kg (≥ 0)");
        
        cmbEstadoReproductivo = new JComboBox<>();
        cmbEstadoReproductivo.setBackground(COLOR_FONDO_INPUT);
        cmbEstadoReproductivo.setPreferredSize(new Dimension(450, 45));
        cmbEstadoReproductivo.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        cmbNivelActividad = new JComboBox<>();
        cmbNivelActividad.setBackground(COLOR_FONDO_INPUT);
        cmbNivelActividad.setPreferredSize(new Dimension(450, 45));
        cmbNivelActividad.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        cmbTipoAlimentacion = new JComboBox<>();
        cmbTipoAlimentacion.setBackground(COLOR_FONDO_INPUT);
        cmbTipoAlimentacion.setPreferredSize(new Dimension(450, 45));
        cmbTipoAlimentacion.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        txtAlimentoDescripcion = new JTextArea(2, 20);
        txtAlimentoDescripcion.setBackground(COLOR_FONDO_INPUT);
        txtAlimentoDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtAlimentoDescripcion.setLineWrap(true);
        txtAlimentoDescripcion.setWrapStyleWord(true);
        
        cmbTemperamento = new JComboBox<>();
        cmbTemperamento.setBackground(COLOR_FONDO_INPUT);
        cmbTemperamento.setPreferredSize(new Dimension(450, 45));
        cmbTemperamento.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        txtUltimaFechaCelo = new MaskedDateField();
        txtUltimaFechaCelo.setBackground(COLOR_FONDO_INPUT);
        txtUltimaFechaCelo.setPreferredSize(new Dimension(450, 45));
        txtUltimaFechaCelo.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtUltimaFechaCelo.setToolTipText("dd/MM/yyyy");
        
        spnNumeroCrias = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));
        spnNumeroCrias.setPreferredSize(new Dimension(450, 45));
        ((JSpinner.DefaultEditor) spnNumeroCrias.getEditor()).getTextField().setBackground(COLOR_FONDO_INPUT);
        ((JSpinner.DefaultEditor) spnNumeroCrias.getEditor()).getTextField().setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        txtDescripcion = new JTextArea(3, 20);
        txtDescripcion.setBackground(COLOR_FONDO_INPUT);
        txtDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        
        txtQrId = new JTextField(15);
        txtQrId.setBackground(COLOR_FONDO_INPUT);
        txtQrId.setPreferredSize(new Dimension(450, 45));
        txtQrId.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtQrId.setToolTipText("36 caracteres exactos");
        
        cmbColor = new JComboBox<>();
        cmbColor.setBackground(COLOR_FONDO_INPUT);
        cmbColor.setPreferredSize(new Dimension(450, 45));
        cmbColor.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        cmbTamano = new JComboBox<>();
        cmbTamano.setBackground(COLOR_FONDO_INPUT);
        cmbTamano.setPreferredSize(new Dimension(450, 45));
        cmbTamano.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        cmbRangoEdad = new JComboBox<>();
        cmbRangoEdad.setBackground(COLOR_FONDO_INPUT);
        cmbRangoEdad.setPreferredSize(new Dimension(450, 45));
        cmbRangoEdad.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        // Botón guardar (más grande y prominente)
        btnGuardar = new JButton("Guardar Mascota");
        btnGuardar.setBackground(COLOR_PRIMARIO);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setFont(btnGuardar.getFont().deriveFont(Font.BOLD, 16f));
        btnGuardar.setPreferredSize(new Dimension(200, 50));
        btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGuardar.setOpaque(true);
        btnGuardar.setContentAreaFilled(true);
        btnGuardar.setBorderPainted(false);
        btnGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnGuardar.setBackground(COLOR_HOVER);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnGuardar.setBackground(COLOR_PRIMARIO);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                btnGuardar.setBackground(COLOR_PRESSED);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                btnGuardar.setBackground(COLOR_HOVER);
            }
        });
        btnGuardar.addActionListener(e -> guardar());
    }
    
    private void construirUI() {
        // Panel superior: título + imagen
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Imagen a la izquierda
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        panelSuperior.add(lblImagen, gbc);
        
        // Título al lado
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblTitulo = new JLabel("Registrar Nueva Mascota");
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 18f));
        panelSuperior.add(lblTitulo, gbc);
        
        // Botón seleccionar imagen
        gbc.gridy = 1;
        panelSuperior.add(btnSeleccionarImagen, gbc);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        // Panel central: formulario
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        
        // Sección obligatoria
        panelCentral.add(crearSeccionObligatoria());
        panelCentral.add(Box.createVerticalStrut(10));
        
        // Botón toggle panel opcional
        btnToggleOpcional = new JButton("▶ Mostrar Campos Opcionales");
        btnToggleOpcional.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnToggleOpcional.addActionListener(e -> togglePanelOpcional());
        panelCentral.add(btnToggleOpcional);
        panelCentral.add(Box.createVerticalStrut(5));
        
        // Panel opcional (inicialmente oculto)
        panelOpcional = crearSeccionOpcional();
        panelOpcional.setVisible(false);
        panelCentral.add(panelOpcional);
        
        JScrollPane scrollPane = new JScrollPane(panelCentral);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior: botón guardar
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.add(btnGuardar);
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private JPanel crearSeccionObligatoria() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDE),
            "Datos Obligatorios",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 16)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Nombre
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setDisplayedMnemonic(KeyEvent.VK_N);
        lblNombre.setLabelFor(txtNombre);
        lblNombre.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(lblNombre, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(txtNombre, gbc);
        row++;
        
        // Especie
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblEspecie = new JLabel("Especie:");
        lblEspecie.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lblEspecie.setToolTipText("Filtra las razas disponibles");
        panel.add(lblEspecie, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(cmbEspecie, gbc);
        row++;
        
        // Raza
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblRaza = new JLabel("Raza:");
        lblRaza.setDisplayedMnemonic(KeyEvent.VK_R);
        lblRaza.setLabelFor(cmbRaza);
        lblRaza.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(lblRaza, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(cmbRaza, gbc);
        row++;
        
        // Sexo
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblSexo = new JLabel("Sexo:");
        lblSexo.setDisplayedMnemonic(KeyEvent.VK_S);
        lblSexo.setLabelFor(cmbSexo);
        lblSexo.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(lblSexo, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(cmbSexo, gbc);
        row++;
        
        // Estado Vital
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblEstadoVital = new JLabel("Estado Vital:");
        lblEstadoVital.setDisplayedMnemonic(KeyEvent.VK_V);
        lblEstadoVital.setLabelFor(cmbEstadoVital);
        lblEstadoVital.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(lblEstadoVital, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(cmbEstadoVital, gbc);
        
        return panel;
    }
    
    private JPanel crearSeccionOpcional() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDE),
            "Datos Opcionales",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 16)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Fecha Nacimiento
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblFechaNac = new JLabel("Fecha Nacimiento:");
        lblFechaNac.setLabelFor(txtFechaNacimiento);
        lblFechaNac.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(lblFechaNac, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(txtFechaNacimiento, gbc);
        row++;
        
        // Edad Aproximada
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblEdad = new JLabel("Edad Aproximada (años):");
        lblEdad.setLabelFor(spnEdadAproximada);
        lblEdad.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(lblEdad, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(spnEdadAproximada, gbc);
        row++;
        
        // Peso
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblPeso = new JLabel("Peso (kg):");
        lblPeso.setLabelFor(txtPeso);
        lblPeso.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(lblPeso, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(txtPeso, gbc);
        row++;
        
        // Estado Reproductivo
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblEstadoRepr = new JLabel("Estado Reproductivo:");
        lblEstadoRepr.setLabelFor(cmbEstadoReproductivo);
        lblEstadoRepr.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(lblEstadoRepr, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(cmbEstadoReproductivo, gbc);
        row++;
        
        // Nivel Actividad
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblNivelAct = new JLabel("Nivel de Actividad:");
        lblNivelAct.setLabelFor(cmbNivelActividad);
        lblNivelAct.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(lblNivelAct, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(cmbNivelActividad, gbc);
        row++;
        
        // Tipo Alimentación
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblTipoAlim = new JLabel("Tipo de Alimentación:");
        lblTipoAlim.setLabelFor(cmbTipoAlimentacion);
        lblTipoAlim.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(lblTipoAlim, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(cmbTipoAlimentacion, gbc);
        row++;
        
        // Alimento Descripción
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0; gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel lblAlimDesc = new JLabel("Descripción Alimento:");
        lblAlimDesc.setLabelFor(txtAlimentoDescripcion);
        lblAlimDesc.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(lblAlimDesc, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST;
        JScrollPane scrollAlim = new JScrollPane(txtAlimentoDescripcion);
        scrollAlim.setPreferredSize(new Dimension(450, 60));
        panel.add(scrollAlim, gbc);
        row++;
        
        // Temperamento
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblTemp = new JLabel("Temperamento:");
        lblTemp.setLabelFor(cmbTemperamento);
        lblTemp.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(lblTemp, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(cmbTemperamento, gbc);
        row++;
        
        // Última Fecha Celo
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblFechaCelo = new JLabel("Última Fecha Celo:");
        lblFechaCelo.setLabelFor(txtUltimaFechaCelo);
        lblFechaCelo.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(lblFechaCelo, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(txtUltimaFechaCelo, gbc);
        row++;
        
        // Número de Crías
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblCrias = new JLabel("Número de Crías:");
        lblCrias.setLabelFor(spnNumeroCrias);
        lblCrias.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(lblCrias, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(spnNumeroCrias, gbc);
        row++;
        
        // Descripción
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0; gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel lblDesc = new JLabel("Descripción:");
        lblDesc.setLabelFor(txtDescripcion);
        lblDesc.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(lblDesc, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST;
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        scrollDesc.setPreferredSize(new Dimension(450, 70));
        panel.add(scrollDesc, gbc);
        row++;
        
        // QR ID
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblQr = new JLabel("QR ID:");
        lblQr.setLabelFor(txtQrId);
        lblQr.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(lblQr, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(txtQrId, gbc);
        row++;
        
        // Color
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblColor = new JLabel("Color:");
        lblColor.setLabelFor(cmbColor);
        lblColor.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(lblColor, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(cmbColor, gbc);
        row++;
        
        // Tamaño
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblTamano = new JLabel("Tamaño:");
        lblTamano.setLabelFor(cmbTamano);
        lblTamano.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(lblTamano, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(cmbTamano, gbc);
        row++;
        
        // Rango Edad
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblRangoEdad = new JLabel("Rango de Edad:");
        lblRangoEdad.setLabelFor(cmbRangoEdad);
        lblRangoEdad.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(lblRangoEdad, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(cmbRangoEdad, gbc);
        
        return panel;
    }
    
    private void togglePanelOpcional() {
        panelOpcionalVisible = !panelOpcionalVisible;
        panelOpcional.setVisible(panelOpcionalVisible);
        btnToggleOpcional.setText(panelOpcionalVisible ? 
            "▼ Ocultar Campos Opcionales" : 
            "▶ Mostrar Campos Opcionales");
        revalidate();
        repaint();
    }
    
    private void configurarValidaciones() {
        // Validación XOR: fecha XOR edad
        txtFechaNacimiento.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { validarFechaEdadXOR(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { validarFechaEdadXOR(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { validarFechaEdadXOR(); }
        });
        
        spnEdadAproximada.addChangeListener(e -> validarFechaEdadXOR());
        
        // Validación peso >= 0
        txtPeso.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { validarPeso(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { validarPeso(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { validarPeso(); }
        });
        
        // Validación QR ID = 36 caracteres
        txtQrId.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { validarQrId(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { validarQrId(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { validarQrId(); }
        });
    }
    
    private void validarFechaEdadXOR() {
        String fecha = txtFechaNacimiento.getText().trim();
        Integer edad = (Integer) spnEdadAproximada.getValue();
        
        boolean fechaVacia = fecha.isEmpty() || fecha.equals("  /  /    ");
        boolean edadCero = edad == 0;
        
        if (!fechaVacia && !edadCero) {
            // Ambos llenos: deshabilitar edad
            spnEdadAproximada.setEnabled(false);
            ((JSpinner.DefaultEditor) spnEdadAproximada.getEditor()).getTextField()
                .setBackground(COLOR_FONDO_DESHABILITADO);
        } else if (fechaVacia && !edadCero) {
            // Solo edad
            spnEdadAproximada.setEnabled(true);
            ((JSpinner.DefaultEditor) spnEdadAproximada.getEditor()).getTextField()
                .setBackground(COLOR_FONDO_INPUT);
        } else {
            // Ambos vacíos o solo fecha
            spnEdadAproximada.setEnabled(true);
            ((JSpinner.DefaultEditor) spnEdadAproximada.getEditor()).getTextField()
                .setBackground(COLOR_FONDO_INPUT);
        }
    }
    
    private void validarPeso() {
        String pesoStr = txtPeso.getText().trim();
        if (!pesoStr.isEmpty()) {
            try {
                BigDecimal peso = new BigDecimal(pesoStr);
                if (peso.compareTo(BigDecimal.ZERO) < 0) {
                    txtPeso.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    txtPeso.setToolTipText("El peso debe ser mayor o igual a 0");
                } else {
                    txtPeso.setBorder(UIManager.getBorder("TextField.border"));
                    txtPeso.setToolTipText("Peso en kg (≥ 0)");
                }
            } catch (NumberFormatException ex) {
                txtPeso.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                txtPeso.setToolTipText("Ingrese un número válido");
            }
        } else {
            txtPeso.setBorder(UIManager.getBorder("TextField.border"));
            txtPeso.setToolTipText("Peso en kg (≥ 0)");
        }
    }
    
    private void validarQrId() {
        String qr = txtQrId.getText().trim();
        if (!qr.isEmpty()) {
            if (qr.length() != 36) {
                txtQrId.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                txtQrId.setToolTipText("Debe tener exactamente 36 caracteres (" + qr.length() + "/36)");
            } else {
                txtQrId.setBorder(UIManager.getBorder("TextField.border"));
                txtQrId.setToolTipText("36 caracteres exactos");
            }
        } else {
            txtQrId.setBorder(UIManager.getBorder("TextField.border"));
            txtQrId.setToolTipText("36 caracteres exactos");
        }
    }
    
    private void cargarCatalogos() {
        // TODO: Implementar carga desde backend con SwingWorker
        // Por ahora, datos mock para testing
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Simular carga
                Thread.sleep(500);
                
                // Mock data
                especies.add(new EspecieFrontendDTO(1, "Perro"));
                especies.add(new EspecieFrontendDTO(2, "Gato"));
                especies.add(new EspecieFrontendDTO(3, "Ave"));
                
                razas.add(new RazaFrontendDTO(1, "Labrador", 1));
                razas.add(new RazaFrontendDTO(2, "Golden Retriever", 1));
                razas.add(new RazaFrontendDTO(3, "Siamés", 2));
                razas.add(new RazaFrontendDTO(4, "Persa", 2));
                
                sexos.add(new SexoFrontendDTO(1, "Macho"));
                sexos.add(new SexoFrontendDTO(2, "Hembra"));
                
                estadosVitales.add(new EstadoVitalFrontendDTO(1, "Vivo"));
                estadosVitales.add(new EstadoVitalFrontendDTO(2, "Fallecido"));
                estadosVitales.add(new EstadoVitalFrontendDTO(3, "Perdido"));
                
                estadosReproductivos.add(new EstadoReproductivoFrondendDTO(1, "Entero"));
                estadosReproductivos.add(new EstadoReproductivoFrondendDTO(2, "Castrado"));
                
                nivelesActividad.add(new NivelActividadFrontendDTO(1, "Bajo"));
                nivelesActividad.add(new NivelActividadFrontendDTO(2, "Medio"));
                nivelesActividad.add(new NivelActividadFrontendDTO(3, "Alto"));
                
                tiposAlimentacion.add(new TipoAlimentacionFrontendDTO(1, "Balanceado"));
                tiposAlimentacion.add(new TipoAlimentacionFrontendDTO(2, "Casera"));
                tiposAlimentacion.add(new TipoAlimentacionFrontendDTO(3, "Mixta"));
                
                temperamentos.add(new TemperamentoFrontendDTO(1, "Tranquilo"));
                temperamentos.add(new TemperamentoFrontendDTO(2, "Activo"));
                temperamentos.add(new TemperamentoFrontendDTO(3, "Agresivo"));
                
                colores.add(new ColorFrontendDTO(1, "Negro"));
                colores.add(new ColorFrontendDTO(2, "Blanco"));
                colores.add(new ColorFrontendDTO(3, "Marrón"));
                
                tamanos.add(new TamanoFrontendDTO(1, "Pequeño"));
                tamanos.add(new TamanoFrontendDTO(2, "Mediano"));
                tamanos.add(new TamanoFrontendDTO(3, "Grande"));
                
                rangosEdad.add(new RangoEdadFrontendDTO(1, "Cachorro (0-1 año)"));
                rangosEdad.add(new RangoEdadFrontendDTO(2, "Joven (1-3 años)"));
                rangosEdad.add(new RangoEdadFrontendDTO(3, "Adulto (3-7 años)"));
                rangosEdad.add(new RangoEdadFrontendDTO(4, "Senior (7+ años)"));
                
                return null;
            }
            
            @Override
            protected void done() {
                poblarComboBoxes();
            }
        };
        worker.execute();
    }
    
    private void poblarComboBoxes() {
        // Obligatorios
        especies.forEach(e -> cmbEspecie.addItem(e));
        sexos.forEach(s -> cmbSexo.addItem(s));
        estadosVitales.forEach(ev -> cmbEstadoVital.addItem(ev));
        
        // Opcionales
        estadosReproductivos.forEach(er -> cmbEstadoReproductivo.addItem(er));
        nivelesActividad.forEach(na -> cmbNivelActividad.addItem(na));
        tiposAlimentacion.forEach(ta -> cmbTipoAlimentacion.addItem(ta));
        temperamentos.forEach(t -> cmbTemperamento.addItem(t));
        colores.forEach(c -> cmbColor.addItem(c));
        tamanos.forEach(t -> cmbTamano.addItem(t));
        rangosEdad.forEach(re -> cmbRangoEdad.addItem(re));
        
        // Insertar ítem vacío al inicio de combos opcionales
        cmbEstadoReproductivo.insertItemAt(null, 0);
        cmbEstadoReproductivo.setSelectedIndex(0);
        cmbNivelActividad.insertItemAt(null, 0);
        cmbNivelActividad.setSelectedIndex(0);
        cmbTipoAlimentacion.insertItemAt(null, 0);
        cmbTipoAlimentacion.setSelectedIndex(0);
        cmbTemperamento.insertItemAt(null, 0);
        cmbTemperamento.setSelectedIndex(0);
        cmbColor.insertItemAt(null, 0);
        cmbColor.setSelectedIndex(0);
        cmbTamano.insertItemAt(null, 0);
        cmbTamano.setSelectedIndex(0);
        cmbRangoEdad.insertItemAt(null, 0);
        cmbRangoEdad.setSelectedIndex(0);
    }
    
    private void filtrarRazasPorEspecie() {
        EspecieFrontendDTO especieSeleccionada = (EspecieFrontendDTO) cmbEspecie.getSelectedItem();
        cmbRaza.removeAllItems();
        
        if (especieSeleccionada != null) {
            List<RazaFrontendDTO> razasFiltradas = razas.stream()
                .filter(r -> r.getIdEspecie().equals(especieSeleccionada.getId()))
                .collect(Collectors.toList());
            razasFiltradas.forEach(r -> cmbRaza.addItem(r));
        }
    }
    
    private void guardar() {
        // Validar campos obligatorios
        if (!validarCamposObligatorios()) {
            return;
        }
        
        // Validar reglas de negocio
        if (!validarReglasNegocio()) {
            return;
        }
        
        // Construir DTO
        MascotaFrontendDTO mascota = construirMascotaDTO();
        
        // Ejecutar callback
        if (onGuardar != null) {
            onGuardar.run();
            // TODO: Pasar el DTO al callback cuando se implemente el backend
        }
    }
    
    private boolean validarCamposObligatorios() {
        StringBuilder errores = new StringBuilder();
        
        if (txtNombre.getText().trim().isEmpty()) {
            errores.append("- Nombre es obligatorio\n");
        }
        
        if (cmbEspecie.getSelectedItem() == null) {
            errores.append("- Especie es obligatoria\n");
        }
        
        if (cmbRaza.getSelectedItem() == null) {
            errores.append("- Raza es obligatoria\n");
        }
        
        if (cmbSexo.getSelectedItem() == null) {
            errores.append("- Sexo es obligatorio\n");
        }
        
        if (cmbEstadoVital.getSelectedItem() == null) {
            errores.append("- Estado Vital es obligatorio\n");
        }
        
        if (errores.length() > 0) {
            Toast.mostrar(
                SwingUtilities.getWindowAncestor(this),
                "Errores de validación:\n" + errores.toString(),
                Toast.TipoToast.ERROR
            );
            return false;
        }
        
        return true;
    }
    
    private boolean validarReglasNegocio() {
        // XOR fecha/edad
        String fecha = txtFechaNacimiento.getText().trim();
        Integer edad = (Integer) spnEdadAproximada.getValue();
        boolean fechaVacia = fecha.isEmpty() || fecha.equals("  /  /    ");
        boolean edadCero = edad == 0;
        
        // No es obligatorio tener uno u otro, pero si hay ambos es error
        if (!fechaVacia && !edadCero) {
            Toast.mostrar(
                SwingUtilities.getWindowAncestor(this),
                "Ingrese Fecha de Nacimiento O Edad Aproximada, no ambos",
                Toast.TipoToast.ERROR
            );
            return false;
        }
        
        // Validar fecha si está presente
        if (!fechaVacia) {
            try {
                LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException ex) {
                Toast.mostrar(
                    SwingUtilities.getWindowAncestor(this),
                    "Fecha de Nacimiento inválida",
                    Toast.TipoToast.ERROR
                );
                return false;
            }
        }
        
        // Peso >= 0
        String pesoStr = txtPeso.getText().trim();
        if (!pesoStr.isEmpty()) {
            try {
                BigDecimal peso = new BigDecimal(pesoStr);
                if (peso.compareTo(BigDecimal.ZERO) < 0) {
                    Toast.mostrar(
                        SwingUtilities.getWindowAncestor(this),
                        "El peso debe ser mayor o igual a 0",
                        Toast.TipoToast.ERROR
                    );
                    return false;
                }
            } catch (NumberFormatException ex) {
                Toast.mostrar(
                    SwingUtilities.getWindowAncestor(this),
                    "Peso inválido",
                    Toast.TipoToast.ERROR
                );
                return false;
            }
        }
        
        // QR ID = 36 caracteres
        String qr = txtQrId.getText().trim();
        if (!qr.isEmpty() && qr.length() != 36) {
            Toast.mostrar(
                SwingUtilities.getWindowAncestor(this),
                "El QR ID debe tener exactamente 36 caracteres",
                Toast.TipoToast.ERROR
            );
            return false;
        }
        
        return true;
    }
    
    private MascotaFrontendDTO construirMascotaDTO() {
        MascotaFrontendDTO dto = new MascotaFrontendDTO();
        
        // Obligatorios
        dto.setNombre(txtNombre.getText().trim());
        dto.setIdRaza(((RazaFrontendDTO) cmbRaza.getSelectedItem()).getId());
        dto.setIdSexo(((SexoFrontendDTO) cmbSexo.getSelectedItem()).getId());
        dto.setIdEstadoVital(((EstadoVitalFrontendDTO) cmbEstadoVital.getSelectedItem()).getId());
        
        // Opcionales
        String fecha = txtFechaNacimiento.getText().trim();
        if (!fecha.isEmpty() && !fecha.equals("  /  /    ")) {
            dto.setFechaNacimiento(LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        
        Integer edad = (Integer) spnEdadAproximada.getValue();
        if (edad > 0) {
            dto.setEdadAproximada(edad);
        }
        
        String pesoStr = txtPeso.getText().trim();
        if (!pesoStr.isEmpty()) {
            dto.setPeso(new BigDecimal(pesoStr));
        }
        
        if (cmbEstadoReproductivo.getSelectedItem() != null) {
            dto.setIdEstadoReproductivo(((EstadoReproductivoFrondendDTO) cmbEstadoReproductivo.getSelectedItem()).getId());
        }
        
        if (cmbNivelActividad.getSelectedItem() != null) {
            dto.setIdNivelActividad(((NivelActividadFrontendDTO) cmbNivelActividad.getSelectedItem()).getId());
        }
        
        if (cmbTipoAlimentacion.getSelectedItem() != null) {
            dto.setIdTipoAlimentacion(((TipoAlimentacionFrontendDTO) cmbTipoAlimentacion.getSelectedItem()).getId());
        }
        
        String alimDesc = txtAlimentoDescripcion.getText().trim();
        if (!alimDesc.isEmpty()) {
            dto.setAlimentoDescripcion(alimDesc);
        }
        
        if (cmbTemperamento.getSelectedItem() != null) {
            dto.setIdTemperamento(((TemperamentoFrontendDTO) cmbTemperamento.getSelectedItem()).getId());
        }
        
        String fechaCelo = txtUltimaFechaCelo.getText().trim();
        if (!fechaCelo.isEmpty() && !fechaCelo.equals("  /  /    ")) {
            dto.setUltimaFechaCelo(LocalDate.parse(fechaCelo, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        
        Integer crias = (Integer) spnNumeroCrias.getValue();
        if (crias > 0) {
            dto.setNumeroCrias(crias);
        }
        
        String desc = txtDescripcion.getText().trim();
        if (!desc.isEmpty()) {
            dto.setDescripcion(desc);
        }
        
        String qr = txtQrId.getText().trim();
        if (!qr.isEmpty()) {
            dto.setQrId(qr);
        }
        
        if (cmbColor.getSelectedItem() != null) {
            dto.setIdColor(((ColorFrontendDTO) cmbColor.getSelectedItem()).getId());
        }
        
        if (cmbTamano.getSelectedItem() != null) {
            dto.setIdTamano(((TamanoFrontendDTO) cmbTamano.getSelectedItem()).getId());
        }
        
        if (cmbRangoEdad.getSelectedItem() != null) {
            dto.setIdRangoEdad(((RangoEdadFrontendDTO) cmbRangoEdad.getSelectedItem()).getId());
        }
        
        if (imagenSeleccionadaId != null) {
            dto.setImageId(imagenSeleccionadaId);
        }
        
        dto.setActivo(true);
        
        return dto;
    }
    
    private ImageIcon crearIconoPlaceholder() {
        // Crear imagen placeholder 96x96
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(96, 96, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(COLOR_FONDO_INPUT);
        g2d.fillRect(0, 0, 96, 96);
        g2d.setColor(COLOR_BORDE);
        g2d.drawRect(0, 0, 95, 95);
        g2d.setColor(COLOR_TEXTO_DESHABILITADO);
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g2d.drawString("Sin imagen", 20, 50);
        g2d.dispose();
        return new ImageIcon(img);
    }
    
    // Métodos públicos para configuración
    public void setOnSeleccionarImagen(Runnable callback) {
        this.onSeleccionarImagen = callback;
    }
    
    public void setOnGuardar(Runnable callback) {
        this.onGuardar = callback;
    }
    
    public void actualizarImagen(ImageIcon icon, Integer imageId) {
        if (icon != null) {
            Image scaledImage = icon.getImage().getScaledInstance(96, 96, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(scaledImage));
            this.imagenSeleccionadaId = imageId;
        }
    }
    
    public void limpiarFormulario() {
        txtNombre.setText("");
        cmbEspecie.setSelectedIndex(0);
        cmbRaza.removeAllItems();
        cmbSexo.setSelectedIndex(0);
        cmbEstadoVital.setSelectedIndex(0);
        
        txtFechaNacimiento.setText("");
        spnEdadAproximada.setValue(0);
        txtPeso.setText("");
        cmbEstadoReproductivo.setSelectedIndex(0);
        cmbNivelActividad.setSelectedIndex(0);
        cmbTipoAlimentacion.setSelectedIndex(0);
        txtAlimentoDescripcion.setText("");
        cmbTemperamento.setSelectedIndex(0);
        txtUltimaFechaCelo.setText("");
        spnNumeroCrias.setValue(0);
        txtDescripcion.setText("");
        txtQrId.setText("");
        cmbColor.setSelectedIndex(0);
        cmbTamano.setSelectedIndex(0);
        cmbRangoEdad.setSelectedIndex(0);
        
        lblImagen.setIcon(crearIconoPlaceholder());
        imagenSeleccionadaId = null;
        
        panelOpcionalVisible = false;
        panelOpcional.setVisible(false);
        btnToggleOpcional.setText("▶ Mostrar Campos Opcionales");
    }
}

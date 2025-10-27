package com.guma.frontend.ui.dialogs;

import com.guma.frontend.dto.*;
import com.guma.frontend.service.DireccionesService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Diálogo para crear una nueva dirección
 */
public class NuevaDireccionDialog extends JDialog {
    
    // Design tokens
    private static final Color COLOR_PRIMARIO = new Color(236, 72, 153);
    private static final Color COLOR_HOVER = new Color(219, 39, 119);
    private static final Color COLOR_PRESSED = new Color(190, 24, 93);
    private static final Color COLOR_FONDO_INPUT = new Color(249, 250, 251);
    
    // Componentes
    private JComboBox<PaisFrontendDTO> cmbPais;
    private JComboBox<ProvinciaFrontendDTO> cmbProvincia;
    private JComboBox<LocalidadFrontendDTO> cmbLocalidad;
    private JTextField txtCalle;
    private JTextField txtNumero;
    private JCheckBox chkSinNumero;
    private JTextField txtDepto;
    private JTextField txtCodigoPostal;
    private JTextField txtAlias;  // Nombre/alias de la dirección
    private JTextField txtReferencia;
    private JTextField txtLatitud;
    private JTextField txtLongitud;
    
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    // Servicio
    private DireccionesService direccionesService;
    
    // Resultado
    private DireccionFrontendDTO direccionCreada;
    private boolean confirmado = false;
    
    public NuevaDireccionDialog(Window parent) {
        super(parent, "Nueva Dirección", ModalityType.APPLICATION_MODAL);
        this.direccionesService = new DireccionesService();
        
        initComponents();
        setupLayout();
        cargarPaises();
        
        pack();
        setSize(700, 600);  // Aumentado de 600x550 a 700x600 para evitar scroll
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        // Combos
        cmbPais = new JComboBox<>();
        cmbPais.setBackground(COLOR_FONDO_INPUT);
        cmbPais.setPreferredSize(new Dimension(500, 40));  // Aumentado de 450 a 500
        cmbPais.setFont(new Font("SansSerif", Font.PLAIN, 15));
        cmbPais.addActionListener(e -> onPaisSeleccionado());
        
        cmbProvincia = new JComboBox<>();
        cmbProvincia.setBackground(COLOR_FONDO_INPUT);
        cmbProvincia.setPreferredSize(new Dimension(500, 40));  // Aumentado de 450 a 500
        cmbProvincia.setFont(new Font("SansSerif", Font.PLAIN, 15));
        cmbProvincia.setEnabled(false);
        cmbProvincia.addActionListener(e -> onProvinciaSeleccionada());
        
        cmbLocalidad = new JComboBox<>();
        cmbLocalidad.setBackground(COLOR_FONDO_INPUT);
        cmbLocalidad.setPreferredSize(new Dimension(500, 40));  // Aumentado de 450 a 500
        cmbLocalidad.setFont(new Font("SansSerif", Font.PLAIN, 15));
        cmbLocalidad.setEnabled(false);
        
        // Campos obligatorios
        txtCalle = new JTextField();
        txtCalle.setPreferredSize(new Dimension(500, 40));  // Aumentado de 450 a 500
        txtCalle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        txtCalle.setBackground(COLOR_FONDO_INPUT);
        
        txtNumero = new JTextField();
        txtNumero.setPreferredSize(new Dimension(370, 40));  // Ajustado de 330 a 370
        txtNumero.setFont(new Font("SansSerif", Font.PLAIN, 15));
        txtNumero.setBackground(COLOR_FONDO_INPUT);
        txtNumero.setName("txtNumero");
        
        // Checkbox "Sin número"
        chkSinNumero = new JCheckBox("Sin número");
        chkSinNumero.setFont(new Font("SansSerif", Font.PLAIN, 14));
        chkSinNumero.setBackground(Color.WHITE);
        chkSinNumero.setMnemonic(KeyEvent.VK_S);
        chkSinNumero.addActionListener(e -> {
            boolean sinNumero = chkSinNumero.isSelected();
            txtNumero.setEnabled(!sinNumero);
            txtNumero.setBackground(sinNumero ? new Color(243, 244, 246) : COLOR_FONDO_INPUT);
            if (sinNumero) {
                txtNumero.setText("");
            }
        });
        
        // Campos opcionales
        txtDepto = new JTextField();
        txtDepto.setPreferredSize(new Dimension(500, 40));  // Aumentado de 450 a 500
        txtDepto.setFont(new Font("SansSerif", Font.PLAIN, 15));
        txtDepto.setBackground(COLOR_FONDO_INPUT);
        
        txtCodigoPostal = new JTextField();
        txtCodigoPostal.setPreferredSize(new Dimension(500, 40));  // Aumentado de 450 a 500
        txtCodigoPostal.setFont(new Font("SansSerif", Font.PLAIN, 15));
        txtCodigoPostal.setBackground(COLOR_FONDO_INPUT);
        txtCodigoPostal.setName("txtCodigoPostal");
        txtCodigoPostal.setToolTipText("Código postal (1000-9999 recomendado)");
        
        // Alias (nombre de la dirección)
        txtAlias = new JTextField();
        txtAlias.setPreferredSize(new Dimension(500, 40));  // Aumentado de 450 a 500
        txtAlias.setFont(new Font("SansSerif", Font.PLAIN, 15));
        txtAlias.setBackground(COLOR_FONDO_INPUT);
        txtAlias.setName("txtAlias");
        txtAlias.setToolTipText("Nombre opcional para identificar esta dirección");
        
        txtReferencia = new JTextField();
        txtReferencia.setPreferredSize(new Dimension(500, 40));  // Aumentado de 450 a 500
        txtReferencia.setFont(new Font("SansSerif", Font.PLAIN, 15));
        txtReferencia.setBackground(COLOR_FONDO_INPUT);
        
        txtLatitud = new JTextField();
        txtLatitud.setPreferredSize(new Dimension(240, 40));  // Aumentado de 215 a 240
        txtLatitud.setFont(new Font("SansSerif", Font.PLAIN, 15));
        txtLatitud.setBackground(COLOR_FONDO_INPUT);
        txtLatitud.setToolTipText("Opcional: latitud en formato decimal");
        
        txtLongitud = new JTextField();
        txtLongitud.setPreferredSize(new Dimension(240, 40));  // Aumentado de 215 a 240
        txtLongitud.setFont(new Font("SansSerif", Font.PLAIN, 15));
        txtLongitud.setBackground(COLOR_FONDO_INPUT);
        txtLongitud.setToolTipText("Opcional: longitud en formato decimal");
        
        // Botones
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(COLOR_PRIMARIO);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnGuardar.setPreferredSize(new Dimension(150, 45));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setOpaque(true);
        btnGuardar.setContentAreaFilled(true);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGuardar.setMnemonic(KeyEvent.VK_G);
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (btnGuardar.isEnabled()) btnGuardar.setBackground(COLOR_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btnGuardar.isEnabled()) btnGuardar.setBackground(COLOR_PRIMARIO);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (btnGuardar.isEnabled()) btnGuardar.setBackground(COLOR_PRESSED);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (btnGuardar.isEnabled()) btnGuardar.setBackground(COLOR_HOVER);
            }
        });
        btnGuardar.addActionListener(e -> guardar());
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("SansSerif", Font.PLAIN, 15));
        btnCancelar.setPreferredSize(new Dimension(150, 45));
        btnCancelar.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219), 2));
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(e -> cancelar());
    }
    
    private void setupLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 16));
        mainPanel.setBorder(new EmptyBorder(16, 16, 16, 16));
        mainPanel.setBackground(Color.WHITE);
        
        // Título
        JLabel lblTitulo = new JLabel("Nueva Dirección");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);
        
        // Formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        int row = 0;
        
        // País
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0; gbc.gridwidth = 1;
        JLabel lblPais = new JLabel("País *:");
        lblPais.setFont(new Font("SansSerif", Font.PLAIN, 15));
        formPanel.add(lblPais, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(cmbPais, gbc);
        row++;
        
        // Provincia
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblProvincia = new JLabel("Provincia *:");
        lblProvincia.setFont(new Font("SansSerif", Font.PLAIN, 15));
        formPanel.add(lblProvincia, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(cmbProvincia, gbc);
        row++;
        
        // Localidad
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblLocalidad = new JLabel("Localidad *:");
        lblLocalidad.setFont(new Font("SansSerif", Font.PLAIN, 15));
        formPanel.add(lblLocalidad, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(cmbLocalidad, gbc);
        row++;
        
        // Calle
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblCalle = new JLabel("Calle *:");
        lblCalle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        formPanel.add(lblCalle, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtCalle, gbc);
        row++;
        
        // Número
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblNumero = new JLabel("Número:");
        lblNumero.setFont(new Font("SansSerif", Font.PLAIN, 15));
        formPanel.add(lblNumero, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        JPanel panelNumero = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelNumero.setBackground(Color.WHITE);
        panelNumero.add(txtNumero);
        panelNumero.add(chkSinNumero);
        formPanel.add(panelNumero, gbc);
        row++;
        
        // Piso/Dto
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblDepto = new JLabel("Piso/Dto:");
        lblDepto.setFont(new Font("SansSerif", Font.PLAIN, 15));
        formPanel.add(lblDepto, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtDepto, gbc);
        row++;
        
        // Código Postal
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblCP = new JLabel("Código Postal *:");
        lblCP.setFont(new Font("SansSerif", Font.PLAIN, 15));
        formPanel.add(lblCP, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtCodigoPostal, gbc);
        row++;
        
        // Alias (nombre)
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblAlias = new JLabel("Alias:");
        lblAlias.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lblAlias.setDisplayedMnemonic(KeyEvent.VK_A);
        lblAlias.setLabelFor(txtAlias);
        formPanel.add(lblAlias, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtAlias, gbc);
        row++;
        
        // Referencia
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblRef = new JLabel("Referencia:");
        lblRef.setFont(new Font("SansSerif", Font.PLAIN, 15));
        formPanel.add(lblRef, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtReferencia, gbc);
        row++;
        
        // Latitud y Longitud
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblLatLong = new JLabel("Lat / Long:");
        lblLatLong.setFont(new Font("SansSerif", Font.PLAIN, 15));
        formPanel.add(lblLatLong, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        JPanel latLongPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        latLongPanel.setBackground(Color.WHITE);
        latLongPanel.add(txtLatitud);
        latLongPanel.add(txtLongitud);
        formPanel.add(latLongPanel, gbc);
        
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botonesPanel.setBackground(Color.WHITE);
        botonesPanel.add(btnCancelar);
        botonesPanel.add(btnGuardar);
        mainPanel.add(botonesPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private void cargarPaises() {
        SwingWorker<java.util.List<PaisFrontendDTO>, Void> worker = new SwingWorker<java.util.List<PaisFrontendDTO>, Void>() {
            @Override
            protected java.util.List<PaisFrontendDTO> doInBackground() throws Exception {
                return direccionesService.getPaises();
            }
            
            @Override
            protected void done() {
                try {
                    java.util.List<PaisFrontendDTO> paises = get();
                    cmbPais.removeAllItems();
                    paises.forEach(p -> cmbPais.addItem(p));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(NuevaDireccionDialog.this,
                        "Error al cargar países: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }
    
    private void onPaisSeleccionado() {
        PaisFrontendDTO pais = (PaisFrontendDTO) cmbPais.getSelectedItem();
        if (pais != null) {
            cargarProvincias(pais.getId());
        }
    }
    
    private void cargarProvincias(Integer idPais) {
        SwingWorker<java.util.List<ProvinciaFrontendDTO>, Void> worker = new SwingWorker<java.util.List<ProvinciaFrontendDTO>, Void>() {
            @Override
            protected java.util.List<ProvinciaFrontendDTO> doInBackground() throws Exception {
                return direccionesService.getProvincias(idPais);
            }
            
            @Override
            protected void done() {
                try {
                    java.util.List<ProvinciaFrontendDTO> provincias = get();
                    cmbProvincia.removeAllItems();
                    provincias.forEach(p -> cmbProvincia.addItem(p));
                    cmbProvincia.setEnabled(true);
                    
                    cmbLocalidad.removeAllItems();
                    cmbLocalidad.setEnabled(false);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(NuevaDireccionDialog.this,
                        "Error al cargar provincias: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }
    
    private void onProvinciaSeleccionada() {
        ProvinciaFrontendDTO provincia = (ProvinciaFrontendDTO) cmbProvincia.getSelectedItem();
        if (provincia != null) {
            cargarLocalidades(provincia.getId());
        }
    }
    
    private void cargarLocalidades(Integer idProvincia) {
        SwingWorker<java.util.List<LocalidadFrontendDTO>, Void> worker = new SwingWorker<java.util.List<LocalidadFrontendDTO>, Void>() {
            @Override
            protected java.util.List<LocalidadFrontendDTO> doInBackground() throws Exception {
                return direccionesService.getLocalidades(idProvincia);
            }
            
            @Override
            protected void done() {
                try {
                    java.util.List<LocalidadFrontendDTO> localidades = get();
                    cmbLocalidad.removeAllItems();
                    localidades.forEach(l -> cmbLocalidad.addItem(l));
                    cmbLocalidad.setEnabled(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(NuevaDireccionDialog.this,
                        "Error al cargar localidades: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }
    
    private void guardar() {
        // Validar campos obligatorios
        if (!validarCampos()) {
            return;
        }
        
        // Deshabilitar botones durante guardado
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        
        SwingWorker<DireccionFrontendDTO, Void> worker = new SwingWorker<DireccionFrontendDTO, Void>() {
            @Override
            protected DireccionFrontendDTO doInBackground() throws Exception {
                NuevaDireccionFrontendRequest request = construirRequest();
                return direccionesService.crearDireccion(request);
            }
            
            @Override
            protected void done() {
                try {
                    direccionCreada = get();
                    confirmado = true;
                    dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(NuevaDireccionDialog.this,
                        "Error al crear dirección: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    btnGuardar.setEnabled(true);
                    btnCancelar.setEnabled(true);
                }
            }
        };
        worker.execute();
    }
    
    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();
        
        if (cmbPais.getSelectedItem() == null) {
            errores.append("- País es obligatorio\n");
        }
        if (cmbProvincia.getSelectedItem() == null) {
            errores.append("- Provincia es obligatoria\n");
        }
        if (cmbLocalidad.getSelectedItem() == null) {
            errores.append("- Localidad es obligatoria\n");
        }
        if (txtCalle.getText().trim().isEmpty()) {
            errores.append("- Calle es obligatoria\n");
        }
        
        // Número: obligatorio solo si NO está marcado "Sin número"
        if (!chkSinNumero.isSelected()) {
            if (txtNumero.getText().trim().isEmpty()) {
                errores.append("- Número es obligatorio (o marque 'Sin número')\n");
            } else {
                try {
                    Integer.parseInt(txtNumero.getText().trim());
                } catch (NumberFormatException e) {
                    errores.append("- Número debe ser un valor numérico\n");
                }
            }
        }
        
        // Código Postal: obligatorio
        if (txtCodigoPostal.getText().trim().isEmpty()) {
            errores.append("- Código Postal es obligatorio\n");
        } else {
            try {
                int cp = Integer.parseInt(txtCodigoPostal.getText().trim());
                if (cp < 1000 || cp > 9999) {
                    errores.append("- Código Postal recomendado entre 1000 y 9999\n");
                }
            } catch (NumberFormatException e) {
                errores.append("- Código Postal debe ser un valor numérico\n");
            }
        }
        
        // Validar latitud/longitud si están presentes
        if (!txtLatitud.getText().trim().isEmpty()) {
            try {
                double lat = Double.parseDouble(txtLatitud.getText().trim());
                if (lat < -90 || lat > 90) {
                    errores.append("- Latitud debe estar entre -90 y 90\n");
                }
            } catch (NumberFormatException e) {
                errores.append("- Latitud debe ser un número decimal\n");
            }
        }
        if (!txtLongitud.getText().trim().isEmpty()) {
            try {
                double lng = Double.parseDouble(txtLongitud.getText().trim());
                if (lng < -180 || lng > 180) {
                    errores.append("- Longitud debe estar entre -180 y 180\n");
                }
            } catch (NumberFormatException e) {
                errores.append("- Longitud debe ser un número decimal\n");
            }
        }
        
        if (errores.length() > 0) {
            JOptionPane.showMessageDialog(this,
                "Errores de validación:\n" + errores.toString(),
                "Validación",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private NuevaDireccionFrontendRequest construirRequest() {
        NuevaDireccionFrontendRequest request = new NuevaDireccionFrontendRequest();
        
        LocalidadFrontendDTO localidad = (LocalidadFrontendDTO) cmbLocalidad.getSelectedItem();
        request.setIdLocalidad(localidad.getId());
        
        request.setCalle(txtCalle.getText().trim());
        
        // Número: null si "Sin número" está marcado
        if (chkSinNumero.isSelected()) {
            request.setNumero(null);
            // Agregar "S/N" a referencia si no está ya
            String ref = txtReferencia.getText().trim();
            if (!ref.toLowerCase().contains("s/n")) {
                ref = (ref.isEmpty() ? "" : ref + " - ") + "S/N";
                txtReferencia.setText(ref);
            }
        } else {
            request.setNumero(Integer.parseInt(txtNumero.getText().trim()));
        }
        
        String depto = txtDepto.getText().trim();
        if (!depto.isEmpty()) {
            request.setDepto(depto);
        }
        
        // Código Postal - ahora obligatorio
        request.setCodigoPostal(Integer.parseInt(txtCodigoPostal.getText().trim()));
        
        // Alias (nombre)
        String alias = txtAlias.getText().trim();
        if (!alias.isEmpty()) {
            request.setNombre(alias);
        }
        
        String ref = txtReferencia.getText().trim();
        if (!ref.isEmpty()) {
            request.setReferencia(ref);
        }
        
        String lat = txtLatitud.getText().trim();
        if (!lat.isEmpty()) {
            try {
                request.setLatitud(Double.parseDouble(lat));
            } catch (NumberFormatException e) {
                // Ignorar
            }
        }
        
        String lng = txtLongitud.getText().trim();
        if (!lng.isEmpty()) {
            try {
                request.setLongitud(Double.parseDouble(lng));
            } catch (NumberFormatException e) {
                // Ignorar
            }
        }
        
        return request;
    }
    
    private void cancelar() {
        confirmado = false;
        dispose();
    }
    
    public boolean isConfirmado() {
        return confirmado;
    }
    
    public DireccionFrontendDTO getDireccionCreada() {
        return direccionCreada;
    }
}

package com.guma.frontend.ui.dialogs;

import com.guma.frontend.dto.NuevaRedSocialFrontendRequest;
import com.guma.frontend.dto.RedSocialFrontendDTO;
import com.guma.frontend.service.RedesSocialesService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

/**
 * Diálogo para crear una nueva red social en el catálogo
 */
public class NuevaRedSocialDialog extends JDialog {
    
    // Design tokens
    private static final Color COLOR_PRIMARIO = new Color(236, 72, 153);
    private static final Color COLOR_HOVER = new Color(219, 39, 119);
    private static final Color COLOR_PRESSED = new Color(190, 24, 93);
    private static final Color COLOR_FONDO_INPUT = new Color(249, 250, 251);
    
    // Patrón para validar URL
    private static final Pattern URL_PATTERN = Pattern.compile("^(https?://)[^\\s]+$");
    
    // Componentes
    private JTextField txtNombre;
    private JTextField txtUrl;
    
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    // Servicio
    private RedesSocialesService redesSocialesService;
    
    // Resultado
    private RedSocialFrontendDTO redSocialCreada;
    private boolean confirmado = false;
    
    public NuevaRedSocialDialog(Window parent) {
        super(parent, "Nueva Red Social", ModalityType.APPLICATION_MODAL);
        this.redesSocialesService = new RedesSocialesService();
        
        initComponents();
        setupLayout();
        
        pack();
        setSize(550, 300);
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        // Campos de texto
        txtNombre = new JTextField();
        txtNombre.setPreferredSize(new Dimension(450, 45));
        txtNombre.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtNombre.setBackground(COLOR_FONDO_INPUT);
        txtNombre.setToolTipText("Ejemplo: Facebook, Instagram, Twitter");
        
        txtUrl = new JTextField();
        txtUrl.setPreferredSize(new Dimension(450, 45));
        txtUrl.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtUrl.setBackground(COLOR_FONDO_INPUT);
        txtUrl.setToolTipText("URL oficial de la red social (ej: https://www.facebook.com). No confundir con tu perfil personal.");
        txtUrl.setName("txtUrlOficial");
        
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
        JLabel lblTitulo = new JLabel("Nueva Red Social");
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
        
        // Nombre
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0; gbc.gridwidth = 1;
        JLabel lblNombre = new JLabel("Nombre *:");
        lblNombre.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lblNombre.setDisplayedMnemonic(KeyEvent.VK_N);
        lblNombre.setLabelFor(txtNombre);
        formPanel.add(lblNombre, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtNombre, gbc);
        row++;
        
        // URL Oficial
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lblUrl = new JLabel("URL Oficial *:");
        lblUrl.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lblUrl.setDisplayedMnemonic(KeyEvent.VK_U);
        lblUrl.setLabelFor(txtUrl);
        formPanel.add(lblUrl, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(txtUrl, gbc);
        row++;
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botonesPanel.setBackground(Color.WHITE);
        botonesPanel.add(btnCancelar);
        botonesPanel.add(btnGuardar);
        mainPanel.add(botonesPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private void guardar() {
        // Validar campos
        if (!validarCampos()) {
            return;
        }
        
        // Deshabilitar botones durante guardado
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        
        SwingWorker<RedSocialFrontendDTO, Void> worker = new SwingWorker<RedSocialFrontendDTO, Void>() {
            @Override
            protected RedSocialFrontendDTO doInBackground() throws Exception {
                NuevaRedSocialFrontendRequest request = new NuevaRedSocialFrontendRequest();
                request.setNombre(txtNombre.getText().trim());
                request.setLink(txtUrl.getText().trim());
                
                return redesSocialesService.crearRedSocial(request);
            }
            
            @Override
            protected void done() {
                try {
                    redSocialCreada = get();
                    confirmado = true;
                    dispose();
                } catch (java.util.concurrent.ExecutionException e) {
                    Throwable cause = e.getCause();
                    String mensaje;
                    if (cause instanceof IllegalArgumentException) {
                        mensaje = cause.getMessage();
                    } else {
                        mensaje = "No se pudo completar la acción: " + cause.getMessage();
                    }
                    JOptionPane.showMessageDialog(NuevaRedSocialDialog.this,
                        mensaje,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    btnGuardar.setEnabled(true);
                    btnCancelar.setEnabled(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(NuevaRedSocialDialog.this,
                        "Error inesperado: " + e.getMessage(),
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
        
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            errores.append("- El nombre es obligatorio\n");
        } else if (nombre.length() > 100) {
            errores.append("- El nombre no puede exceder 100 caracteres\n");
        } else if (!nombre.matches("^[a-zA-Z0-9\\s]+$")) {
            errores.append("- El nombre solo puede contener letras, números y espacios\n");
        }
        
        String url = txtUrl.getText().trim();
        if (url.isEmpty()) {
            errores.append("- La URL oficial es obligatoria\n");
        } else if (!url.startsWith("https://")) {
            errores.append("- La URL debe comenzar con https://\n");
        } else if (!URL_PATTERN.matcher(url).matches()) {
            errores.append("- La URL no tiene un formato válido\n");
        }
        
        if (errores.length() > 0) {
            JOptionPane.showMessageDialog(this,
                "Errores de validación:\n\n" + errores.toString(),
                "Validación",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void cancelar() {
        confirmado = false;
        dispose();
    }
    
    public boolean isConfirmado() {
        return confirmado;
    }
    
    public RedSocialFrontendDTO getRedSocialCreada() {
        return redSocialCreada;
    }
}

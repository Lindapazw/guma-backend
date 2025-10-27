package com.guma.frontend.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.UIManager;

import com.guma.frontend.adapter.AuthFacadeAdapter;
import com.guma.frontend.dto.ErrorFrontendDTO;
import com.guma.frontend.dto.RegistroFrontendDTO;
import com.guma.frontend.dto.ResultadoFrontendDTO;
import com.guma.frontend.dto.UsuarioFrontendDTO;
import com.guma.frontend.util.ValidationUtils;

/**
 * RF-01: Pantalla de registro de nuevos usuarios
 * Permite crear cuentas en el sistema
 */
public class RegistroDialog extends JDialog {

    // Método principal para testing
    public static void main(String[] args) {
        // Configurar Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            RegistroDialog dialog = new RegistroDialog(null);
            dialog.setVisible(true);
            System.out.println("Registro exitoso: " + dialog.isRegistroExitoso());
        });
    }

    // Componentes de UI
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtTelefono;
    private MaskedDateField txtFechaNacimiento;
    private JButton btnRegistrar;
    private JButton btnCancelar;
    private JButton btnMostrarPassword;

    private JButton btnMostrarConfirmPassword;

    // Adapter para conectar con backend real
    private AuthFacadeAdapter authFacade;
    // Estado
    private boolean passwordVisible = false;
    private boolean confirmPasswordVisible = false;
    private boolean registroExitoso = false;
    private boolean procesandoRegistro = false; // Bandera para evitar re-validación durante el registro

    private String emailRegistrado = "";

    public RegistroDialog(Frame parent) {
        super(parent, "GUMA - Registro de Usuario", true);
        this.authFacade = new AuthFacadeAdapter();
        initComponents();
        setupLayout();
        setupListeners();
    }

    // Getters para el diálogo padre
    public boolean isRegistroExitoso() {
        return registroExitoso;
    }

    public String getEmailRegistrado() {
        return emailRegistrado;
    }

    private void initComponents() {
        // Configuración del diálogo
        setSize(550, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        // Fuente y dimensiones para todos los campos
        Font campoFont = new Font("SansSerif", Font.PLAIN, 14);
        Dimension campoDimension = new Dimension(300, 35);

        // Campos de texto
        txtEmail = new JTextField(30);
        txtEmail.setFont(campoFont);
        txtEmail.setPreferredSize(campoDimension);
        txtEmail.setToolTipText("ejemplo@dominio.com");

        txtPassword = new JPasswordField(30);
        txtPassword.setFont(campoFont);
        txtPassword.setPreferredSize(campoDimension);
        txtPassword.setToolTipText("Mínimo 8 caracteres");

        txtConfirmPassword = new JPasswordField(30);
        txtConfirmPassword.setFont(campoFont);
        txtConfirmPassword.setPreferredSize(campoDimension);
        txtConfirmPassword.setToolTipText("Repita la contraseña");

        txtNombre = new JTextField(30);
        txtNombre.setFont(campoFont);
        txtNombre.setPreferredSize(campoDimension);
        txtNombre.setToolTipText("Su nombre");

        txtApellido = new JTextField(30);
        txtApellido.setFont(campoFont);
        txtApellido.setPreferredSize(campoDimension);
        txtApellido.setToolTipText("Su apellido");

        txtTelefono = new JTextField(30);
        txtTelefono.setFont(campoFont);
        txtTelefono.setPreferredSize(campoDimension);
        txtTelefono.setToolTipText("Formato: +54 9 11 1234-5678 (opcional)");

        txtFechaNacimiento = new MaskedDateField();
        txtFechaNacimiento.setToolTipText("DD/MM/AAAA - Ejemplo: 15/06/1990");

        // Botones mostrar/ocultar contraseña
        btnMostrarPassword = new JButton("👁");
        btnMostrarPassword.setToolTipText("Mostrar contraseña");
        btnMostrarPassword.setFocusPainted(false);
        btnMostrarPassword.setPreferredSize(new Dimension(45, 35));

        btnMostrarConfirmPassword = new JButton("👁");
        btnMostrarConfirmPassword.setToolTipText("Mostrar contraseña");
        btnMostrarConfirmPassword.setFocusPainted(false);
        btnMostrarConfirmPassword.setPreferredSize(new Dimension(45, 35));

        // Botones de acción
        btnRegistrar = new JButton("Crear Cuenta");
        btnRegistrar.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnRegistrar.setPreferredSize(new Dimension(150, 45));
        btnRegistrar.setBackground(new Color(16, 185, 129)); // Verde
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrar.setEnabled(false);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnCancelar.setPreferredSize(new Dimension(120, 45));
        btnCancelar.setForeground(new Color(107, 114, 128));
        btnCancelar.setBorderPainted(true);
        btnCancelar.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219), 2));
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void setupLayout() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        mainPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 6, 0);

        int fila = 0;

        // Título
        JLabel lblTitulo = new JLabel("Crear Nueva Cuenta");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = fila++;
        gbc.gridwidth = 2;
        mainPanel.add(lblTitulo, gbc);

        JLabel lblSubtitulo = new JLabel("Complete todos los campos para registrarse");
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSubtitulo.setForeground(Color.GRAY);
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = fila++;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(lblSubtitulo, gbc);

        // Email
        gbc.insets = new Insets(6, 0, 3, 0);
        gbc.gridy = fila++;
        JLabel lblEmail = new JLabel("Correo electrónico *");
        lblEmail.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEmail.setDisplayedMnemonic(KeyEvent.VK_E);
        lblEmail.setLabelFor(txtEmail);
        mainPanel.add(lblEmail, gbc);

        gbc.gridy = fila++;
        gbc.insets = new Insets(0, 0, 10, 0);
        mainPanel.add(txtEmail, gbc);

        // Contraseña
        gbc.gridy = fila++;
        gbc.insets = new Insets(6, 0, 3, 0);
        JLabel lblPassword = new JLabel("Contraseña *");
        lblPassword.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblPassword.setDisplayedMnemonic(KeyEvent.VK_C);
        lblPassword.setLabelFor(txtPassword);
        mainPanel.add(lblPassword, gbc);

        JPanel passwordPanel = new JPanel(new BorderLayout(5, 0));
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.add(txtPassword, BorderLayout.CENTER);
        passwordPanel.add(btnMostrarPassword, BorderLayout.EAST);

        gbc.gridy = fila++;
        gbc.insets = new Insets(0, 0, 10, 0);
        mainPanel.add(passwordPanel, gbc);

        // Confirmar Contraseña
        gbc.gridy = fila++;
        gbc.insets = new Insets(6, 0, 3, 0);
        JLabel lblConfirmPassword = new JLabel("Confirmar contraseña *");
        lblConfirmPassword.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblConfirmPassword.setDisplayedMnemonic(KeyEvent.VK_O);
        lblConfirmPassword.setLabelFor(txtConfirmPassword);
        mainPanel.add(lblConfirmPassword, gbc);

        JPanel confirmPasswordPanel = new JPanel(new BorderLayout(5, 0));
        confirmPasswordPanel.setBackground(Color.WHITE);
        confirmPasswordPanel.add(txtConfirmPassword, BorderLayout.CENTER);
        confirmPasswordPanel.add(btnMostrarConfirmPassword, BorderLayout.EAST);

        gbc.gridy = fila++;
        gbc.insets = new Insets(0, 0, 10, 0);
        mainPanel.add(confirmPasswordPanel, gbc);

        // Nombre
        gbc.gridy = fila++;
        gbc.insets = new Insets(6, 0, 3, 0);
        JLabel lblNombre = new JLabel("Nombre *");
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblNombre.setDisplayedMnemonic(KeyEvent.VK_N);
        lblNombre.setLabelFor(txtNombre);
        mainPanel.add(lblNombre, gbc);

        gbc.gridy = fila++;
        gbc.insets = new Insets(0, 0, 10, 0);
        mainPanel.add(txtNombre, gbc);

        // Apellido
        gbc.gridy = fila++;
        gbc.insets = new Insets(6, 0, 3, 0);
        JLabel lblApellido = new JLabel("Apellido *");
        lblApellido.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblApellido.setDisplayedMnemonic(KeyEvent.VK_A);
        lblApellido.setLabelFor(txtApellido);
        mainPanel.add(lblApellido, gbc);

        gbc.gridy = fila++;
        gbc.insets = new Insets(0, 0, 10, 0);
        mainPanel.add(txtApellido, gbc);

        // Teléfono
        gbc.gridy = fila++;
        gbc.insets = new Insets(6, 0, 3, 0);
        JLabel lblTelefono = new JLabel("Teléfono (opcional)");
        lblTelefono.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblTelefono.setDisplayedMnemonic(KeyEvent.VK_T);
        lblTelefono.setLabelFor(txtTelefono);
        mainPanel.add(lblTelefono, gbc);

        gbc.gridy = fila++;
        gbc.insets = new Insets(0, 0, 10, 0);
        mainPanel.add(txtTelefono, gbc);

        // Fecha de Nacimiento
        gbc.gridy = fila++;
        gbc.insets = new Insets(6, 0, 3, 0);
        JLabel lblFechaNacimiento = new JLabel("Fecha de Nacimiento *");
        lblFechaNacimiento.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblFechaNacimiento.setDisplayedMnemonic(KeyEvent.VK_F);
        mainPanel.add(lblFechaNacimiento, gbc);

        gbc.gridy = fila++;
        gbc.insets = new Insets(0, 0, 12, 0);
        mainPanel.add(txtFechaNacimiento, gbc);

        // Botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        botonesPanel.setBackground(Color.WHITE);
        botonesPanel.add(btnCancelar);
        botonesPanel.add(btnRegistrar);

        gbc.gridy = fila++;
        gbc.insets = new Insets(8, 0, 0, 0);
        mainPanel.add(botonesPanel, gbc);

        add(mainPanel);
    }

    private void setupListeners() {
        // Validación en tiempo real
        javax.swing.event.DocumentListener validationListener = new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                validarCampos();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                validarCampos();
            }

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                validarCampos();
            }
        };

        txtEmail.getDocument().addDocumentListener(validationListener);
        txtPassword.getDocument().addDocumentListener(validationListener);
        txtConfirmPassword.getDocument().addDocumentListener(validationListener);
        txtNombre.getDocument().addDocumentListener(validationListener);
        txtApellido.getDocument().addDocumentListener(validationListener);
        txtFechaNacimiento.getTextField().getDocument().addDocumentListener(validationListener);

        // Botones mostrar/ocultar contraseña
        btnMostrarPassword.addActionListener(e -> togglePasswordVisibility());
        btnMostrarConfirmPassword.addActionListener(e -> toggleConfirmPasswordVisibility());

        // Botón Registrar
        btnRegistrar.addActionListener(e -> registrarUsuario());

        // Botón Cancelar
        btnCancelar.addActionListener(e -> dispose());

        // ESC cierra el diálogo
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        getRootPane().registerKeyboardAction(
                e -> dispose(),
                escapeKeyStroke,
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        // Botón por defecto
        getRootPane().setDefaultButton(btnRegistrar);
    }

    private void validarCampos() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String fechaNac = txtFechaNacimiento.getText().trim();

        // Validar email
        boolean emailValido = ValidationUtils.esEmailValido(email);
        if (email.isEmpty()) {
            ValidationUtils.marcarCampoNormal(txtEmail);
        } else if (emailValido) {
            ValidationUtils.marcarCampoValido(txtEmail);
        } else {
            ValidationUtils.marcarCampoInvalido(txtEmail);
        }

        // Validar password
        boolean passwordValido = ValidationUtils.esPasswordValida(password);
        if (password.isEmpty()) {
            ValidationUtils.marcarCampoNormal(txtPassword);
        } else if (passwordValido) {
            ValidationUtils.marcarCampoValido(txtPassword);
        } else {
            ValidationUtils.marcarCampoInvalido(txtPassword);
        }

        // Validar confirm password
        boolean confirmPasswordValido = !confirmPassword.isEmpty() && password.equals(confirmPassword);
        if (confirmPassword.isEmpty()) {
            ValidationUtils.marcarCampoNormal(txtConfirmPassword);
        } else if (confirmPasswordValido) {
            ValidationUtils.marcarCampoValido(txtConfirmPassword);
        } else {
            ValidationUtils.marcarCampoInvalido(txtConfirmPassword);
        }

        // Validar nombre
        boolean nombreValido = nombre.length() >= 2;
        if (nombre.isEmpty()) {
            ValidationUtils.marcarCampoNormal(txtNombre);
        } else if (nombreValido) {
            ValidationUtils.marcarCampoValido(txtNombre);
        } else {
            ValidationUtils.marcarCampoInvalido(txtNombre);
        }

        // Validar apellido
        boolean apellidoValido = apellido.length() >= 2;
        if (apellido.isEmpty()) {
            ValidationUtils.marcarCampoNormal(txtApellido);
        } else if (apellidoValido) {
            ValidationUtils.marcarCampoValido(txtApellido);
        } else {
            ValidationUtils.marcarCampoInvalido(txtApellido);
        }

        // Validar fecha de nacimiento
        boolean fechaValida = esFechaValida(fechaNac);
        if (fechaNac.isEmpty()) {
            ValidationUtils.marcarCampoNormal(txtFechaNacimiento.getTextField());
        } else if (fechaValida) {
            ValidationUtils.marcarCampoValido(txtFechaNacimiento.getTextField());
        } else {
            ValidationUtils.marcarCampoInvalido(txtFechaNacimiento.getTextField());
        }

        // Habilitar/deshabilitar botón
        boolean todosValidos = emailValido && passwordValido && confirmPasswordValido &&
                nombreValido && apellidoValido && fechaValida;

        // Solo habilitar/deshabilitar si NO estamos procesando un registro
        if (!procesandoRegistro) {
            boolean estadoAnterior = btnRegistrar.isEnabled();
            btnRegistrar.setEnabled(todosValidos);

            // Si el botón cambió de deshabilitado a habilitado, forzar repaint
            if (!estadoAnterior && todosValidos) {
                btnRegistrar.revalidate();
                btnRegistrar.repaint();
            }
        }
    }

    private boolean esFechaValida(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            return false;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaNac = LocalDate.parse(fecha, formatter);
            LocalDate hoy = LocalDate.now();

            // Validar que sea mayor de edad (18 años)
            LocalDate hace18Anios = hoy.minusYears(18);

            // Validar que no sea una fecha futura
            if (fechaNac.isAfter(hoy)) {
                return false;
            }

            // Validar que sea mayor de edad
            if (fechaNac.isAfter(hace18Anios)) {
                return false;
            }

            // Validar que no sea muy antigua (120 años)
            LocalDate hace120Anios = hoy.minusYears(120);
            if (fechaNac.isBefore(hace120Anios)) {
                return false;
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    private void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;
        if (passwordVisible) {
            txtPassword.setEchoChar((char) 0);
            btnMostrarPassword.setText("👁‍🗨");
            btnMostrarPassword.setToolTipText("Ocultar contraseña");
        } else {
            txtPassword.setEchoChar('•');
            btnMostrarPassword.setText("👁");
            btnMostrarPassword.setToolTipText("Mostrar contraseña");
        }
    }

    private void toggleConfirmPasswordVisibility() {
        confirmPasswordVisible = !confirmPasswordVisible;
        if (confirmPasswordVisible) {
            txtConfirmPassword.setEchoChar((char) 0);
            btnMostrarConfirmPassword.setText("👁‍🗨");
            btnMostrarConfirmPassword.setToolTipText("Ocultar contraseña");
        } else {
            txtConfirmPassword.setEchoChar('•');
            btnMostrarConfirmPassword.setText("👁");
            btnMostrarConfirmPassword.setToolTipText("Mostrar contraseña");
        }
    }

    private void registrarUsuario() {
        // Validar fecha ANTES de deshabilitar el botón
        String fechaTexto = txtFechaNacimiento.getText().trim().replace("_", "");
        if (fechaTexto.length() < 10) {
            Toast.mostrar(this, "Debe ingresar una fecha de nacimiento válida (DD/MM/AAAA)", Toast.TipoToast.ERROR);
            return;
        }

        LocalDate fechaNac;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            fechaNac = LocalDate.parse(fechaTexto, formatter);

            // Validar mayor de 18 años
            LocalDate hoy = LocalDate.now();
            LocalDate hace18Anios = hoy.minusYears(18);
            if (fechaNac.isAfter(hace18Anios)) {
                Toast.mostrar(this, "Debe ser mayor de 18 años para registrarse", Toast.TipoToast.ERROR);
                return;
            }

            // Validar que no sea muy antigua (120 años)
            LocalDate hace120Anios = hoy.minusYears(120);
            if (fechaNac.isBefore(hace120Anios)) {
                Toast.mostrar(this, "La fecha de nacimiento no es válida", Toast.TipoToast.ERROR);
                return;
            }

        } catch (Exception e) {
            Toast.mostrar(this, "Formato de fecha inválido. Use DD/MM/AAAA", Toast.TipoToast.ERROR);
            return;
        }

        // AHORA sí, todas las validaciones pasaron - activar bandera y deshabilitar
        // botón
        procesandoRegistro = true;
        btnRegistrar.setEnabled(false);
        btnRegistrar.setText("Registrando...");

        // Crear DTO con los datos del formulario
        RegistroFrontendDTO registroDTO = new RegistroFrontendDTO();
        registroDTO.setEmail(txtEmail.getText().trim());
        registroDTO.setPassword(new String(txtPassword.getPassword()));
        registroDTO.setNombre(txtNombre.getText().trim());
        registroDTO.setApellido(txtApellido.getText().trim());
        registroDTO.setFechaNacimiento(fechaNac);

        String telefono = txtTelefono.getText().trim();
        if (!telefono.isEmpty()) {
            registroDTO.setTelefono(telefono);
        }

        // Crear SwingWorker para ejecutar el registro en background
        SwingWorker<ResultadoFrontendDTO<UsuarioFrontendDTO>, Void> worker = new SwingWorker<>() {
            @Override
            protected ResultadoFrontendDTO<UsuarioFrontendDTO> doInBackground() throws Exception {
                return authFacade.registrarUsuario(registroDTO);
            }

            @Override
            protected void done() {
                try {
                    ResultadoFrontendDTO<UsuarioFrontendDTO> resultado = get();

                    if (resultado.isExito()) {
                        // Registro exitoso
                        UsuarioFrontendDTO usuario = resultado.getData();
                        Toast.mostrar(RegistroDialog.this, "Cuenta creada con éxito. Email: " + usuario.getEmail(),
                                Toast.TipoToast.EXITO);

                        registroExitoso = true;
                        emailRegistrado = usuario.getEmail();

                        // Esperar un momento antes de cerrar
                        Timer timer = new Timer(1500, e -> dispose());
                        timer.setRepeats(false);
                        timer.start();

                    } else {
                        // Desactivar bandera y re-habilitar botón en caso de error
                        procesandoRegistro = false;
                        btnRegistrar.setEnabled(true);
                        btnRegistrar.setText("Crear Cuenta");

                        // Registro fallido - Mostrar TODOS los errores
                        StringBuilder mensajeErrores = new StringBuilder();
                        for (int i = 0; i < resultado.getErrores().size(); i++) {
                            ErrorFrontendDTO error = resultado.getErrores().get(i);
                            mensajeErrores.append(error.getMensaje());
                            if (i < resultado.getErrores().size() - 1) {
                                mensajeErrores.append("<br>");
                            }

                            // Marcar campo con error si corresponde
                            if (error.getCampo() != null) {
                                switch (error.getCampo()) {
                                    case "email":
                                        ValidationUtils.marcarCampoInvalido(txtEmail);
                                        break;
                                    case "password":
                                        ValidationUtils.marcarCampoInvalido(txtPassword);
                                        break;
                                    case "nombre":
                                        ValidationUtils.marcarCampoInvalido(txtNombre);
                                        break;
                                    case "apellido":
                                        ValidationUtils.marcarCampoInvalido(txtApellido);
                                        break;
                                }
                            }
                        }

                        Toast.mostrar(RegistroDialog.this, mensajeErrores.toString(), Toast.TipoToast.ERROR, 5000);
                    }
                } catch (Exception e) {
                    // Error inesperado - desactivar bandera y re-habilitar botón
                    procesandoRegistro = false;
                    btnRegistrar.setEnabled(true);
                    btnRegistrar.setText("Crear Cuenta");
                    Toast.mostrar(RegistroDialog.this, "Error inesperado: " + e.getMessage(), Toast.TipoToast.ERROR);
                    e.printStackTrace();
                }
            }
        };

        // Ejecutar el worker
        worker.execute();
    }
}

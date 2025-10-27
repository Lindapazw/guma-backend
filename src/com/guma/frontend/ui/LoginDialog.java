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
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.DocumentListener;

import com.guma.frontend.adapter.AuthFacadeAdapter;
import com.guma.frontend.dto.ErrorFrontendDTO;
import com.guma.frontend.dto.LoginFrontendDTO;
import com.guma.frontend.dto.ResultadoFrontendDTO;
import com.guma.frontend.dto.SesionFrontendDTO;
import com.guma.frontend.session.SesionActual;
import com.guma.frontend.util.ValidationUtils;

/**
 * RF-02: Pantalla de inicio de sesión
 * Permite autenticar usuarios registrados
 */
public class LoginDialog extends JDialog {

    // Método principal para testing
    public static void main(String[] args) {
        // Configurar Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            LoginDialog dialog = new LoginDialog(null);
            dialog.setVisible(true);
        });
    }

    // Componentes de UI
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegistrarse;
    private JButton btnMostrarPassword;

    private JLabel lblMensaje;

    // Adapter para conectar con backend real
    private AuthFacadeAdapter authFacade;

    // Estado
    private boolean passwordVisible = false;

    public LoginDialog(Frame parent) {
        super(parent, "GUMA - Inicio de Sesión", true);
        this.authFacade = new AuthFacadeAdapter();
        initComponents();
        setupLayout();
        setupListeners();

        // Ajustar tamaño automáticamente al contenido
        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        // Configuración del diálogo
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        // Fuente y dimensiones para campos (más grandes)
        Font campoFont = new Font("SansSerif", Font.PLAIN, 15);
        Dimension campoDimension = new Dimension(400, 45);

        // Campos de texto
        txtEmail = new JTextField(35);
        txtEmail.setFont(campoFont);
        txtEmail.setPreferredSize(campoDimension);
        txtEmail.setToolTipText("Ingrese su correo electrónico");

        txtPassword = new JPasswordField(35);
        txtPassword.setFont(campoFont);
        txtPassword.setPreferredSize(campoDimension);
        txtPassword.setToolTipText("Ingrese su contraseña");

        // Botón mostrar/ocultar contraseña
        btnMostrarPassword = new JButton("👁");
        btnMostrarPassword.setToolTipText("Mostrar contraseña");
        btnMostrarPassword.setFocusPainted(false);
        btnMostrarPassword.setPreferredSize(new Dimension(50, 45));

        // Botones de acción
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnLogin.setPreferredSize(new Dimension(250, 50));
        btnLogin.setBackground(new Color(16, 185, 129)); // Verde
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setEnabled(false);

        btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.setFont(new Font("SansSerif", Font.PLAIN, 15));
        btnRegistrarse.setPreferredSize(new Dimension(160, 50));
        btnRegistrarse.setForeground(new Color(37, 99, 235));
        btnRegistrarse.setBorderPainted(true);
        btnRegistrarse.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219), 2));
        btnRegistrarse.setContentAreaFilled(false);
        btnRegistrarse.setFocusPainted(false);
        btnRegistrarse.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Label de mensaje
        lblMensaje = new JLabel(" ");
        lblMensaje.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void setupLayout() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        mainPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Título
        JLabel lblTitulo = new JLabel("Bienvenido a GUMA");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(lblTitulo, gbc);

        JLabel lblSubtitulo = new JLabel("Sistema de Gestión de Mascotas");
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSubtitulo.setForeground(Color.GRAY);
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(lblSubtitulo, gbc);

        // Email
        gbc.gridwidth = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 6, 0);
        JLabel lblEmail = new JLabel("Correo electrónico");
        lblEmail.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblEmail.setDisplayedMnemonic(KeyEvent.VK_E);
        lblEmail.setLabelFor(txtEmail);
        mainPanel.add(lblEmail, gbc);

        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(txtEmail, gbc);

        // Contraseña
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 6, 0);
        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblPassword.setDisplayedMnemonic(KeyEvent.VK_C);
        lblPassword.setLabelFor(txtPassword);
        mainPanel.add(lblPassword, gbc);

        // Panel para contraseña + botón mostrar
        JPanel passwordPanel = new JPanel(new BorderLayout(8, 0));
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.add(txtPassword, BorderLayout.CENTER);
        passwordPanel.add(btnMostrarPassword, BorderLayout.EAST);

        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 12, 0);
        mainPanel.add(passwordPanel, gbc);

        // Mensaje de error/info
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(8, 0, 12, 0);
        mainPanel.add(lblMensaje, gbc);

        // Botón Login
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(12, 0, 15, 0);
        mainPanel.add(btnLogin, gbc);

        // Separador
        JPanel separadorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        separadorPanel.setBackground(Color.WHITE);
        JLabel lblSeparador1 = new JLabel("─────────────");
        JLabel lblSeparador2 = new JLabel("o");
        JLabel lblSeparador3 = new JLabel("─────────────");
        lblSeparador1.setForeground(Color.LIGHT_GRAY);
        lblSeparador2.setForeground(Color.GRAY);
        lblSeparador3.setForeground(Color.LIGHT_GRAY);
        separadorPanel.add(lblSeparador1);
        separadorPanel.add(lblSeparador2);
        separadorPanel.add(lblSeparador3);

        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(8, 0, 8, 0);
        mainPanel.add(separadorPanel, gbc);

        // Botón Registrarse
        JPanel registroPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        registroPanel.setBackground(Color.WHITE);
        JLabel lblNoTienesCuenta = new JLabel("¿No tienes cuenta?");
        lblNoTienesCuenta.setFont(new Font("SansSerif", Font.PLAIN, 14));
        registroPanel.add(lblNoTienesCuenta);
        registroPanel.add(btnRegistrarse);

        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(8, 0, 20, 0);
        mainPanel.add(registroPanel, gbc);

        add(mainPanel);
    }

    private void setupListeners() {
        // Validación en tiempo real
        DocumentListener validationListener = new DocumentListener() {
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

        // Botón mostrar/ocultar contraseña
        btnMostrarPassword.addActionListener(e -> togglePasswordVisibility());

        // Botón Login
        btnLogin.addActionListener(e -> iniciarSesion());

        // Enter en campos ejecuta login
        txtEmail.addActionListener(e -> {
            if (btnLogin.isEnabled())
                iniciarSesion();
        });

        txtPassword.addActionListener(e -> {
            if (btnLogin.isEnabled())
                iniciarSesion();
        });

        // Botón Registrarse
        btnRegistrarse.addActionListener(e -> abrirRegistro());

        // ESC cierra el diálogo
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        getRootPane().registerKeyboardAction(
                e -> dispose(),
                escapeKeyStroke,
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        // Botón por defecto
        getRootPane().setDefaultButton(btnLogin);
    }

    private void validarCampos() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());

        boolean emailValido = ValidationUtils.esEmailValido(email);
        boolean passwordValido = !password.isEmpty();

        // Actualizar bordes
        if (email.isEmpty()) {
            ValidationUtils.marcarCampoNormal(txtEmail);
        } else if (emailValido) {
            ValidationUtils.marcarCampoValido(txtEmail);
        } else {
            ValidationUtils.marcarCampoInvalido(txtEmail);
        }

        // Habilitar/deshabilitar botón
        btnLogin.setEnabled(emailValido && passwordValido);

        // Limpiar mensaje si el usuario está escribiendo
        if (!lblMensaje.getText().trim().isEmpty() &&
                (email.isEmpty() || password.isEmpty())) {
            lblMensaje.setText(" ");
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

    private void iniciarSesion() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());

        LoginFrontendDTO loginDTO = new LoginFrontendDTO(email, password);

        // Mostrar loading dialog mientras se ejecuta el login
        LoadingDialog.mostrarLogin(this, () -> {
            ResultadoFrontendDTO<SesionFrontendDTO> resultado = authFacade.iniciarSesion(loginDTO);

            // Actualizar UI en el hilo de Swing
            SwingUtilities.invokeLater(() -> {
                if (resultado.isExito()) {
                    // Login exitoso
                    SesionFrontendDTO sesion = resultado.getData();
                    SesionActual.getInstance().setSesion(sesion);

                    Toast.mostrar(LoginDialog.this, "Inicio de sesión exitoso", Toast.TipoToast.EXITO);

                    // Esperar un momento antes de abrir ventana principal
                    Timer timer = new Timer(800, e -> {
                        abrirVentanaPrincipal();
                        dispose();
                    });
                    timer.setRepeats(false);
                    timer.start();

                } else {
                    // Login fallido - Mostrar TODOS los errores
                    StringBuilder mensajeErrores = new StringBuilder();
                    for (int i = 0; i < resultado.getErrores().size(); i++) {
                        ErrorFrontendDTO error = resultado.getErrores().get(i);
                        mensajeErrores.append(error.getMensaje());
                        if (i < resultado.getErrores().size() - 1) {
                            mensajeErrores.append("<br>");
                        }
                    }

                    Toast.mostrar(LoginDialog.this, mensajeErrores.toString(), Toast.TipoToast.ERROR, 4000);
                }
            });
        });
    }

    private void abrirRegistro() {
        RegistroDialog registroDialog = new RegistroDialog(null);
        registroDialog.setVisible(true);

        // Si el registro fue exitoso, pre-llenar el email
        if (registroDialog.isRegistroExitoso()) {
            txtEmail.setText(registroDialog.getEmailRegistrado());
            txtPassword.setText("");
            txtPassword.requestFocus();
        }
    }

    private void abrirVentanaPrincipal() {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}

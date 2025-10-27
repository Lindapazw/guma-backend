package com.guma.frontend.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Diálogo de carga modal y reutilizable
 * Muestra un spinner animado mientras se ejecuta una operación en segundo plano
 */
public class LoadingDialog extends JDialog {
    
    private JProgressBar progressBar;
    private JLabel lblMensaje;
    private SwingWorker<Void, Void> worker;
    
    /**
     * Constructor del diálogo de carga
     * @param parent Ventana padre
     * @param mensaje Mensaje a mostrar (ej: "Iniciando sesión...")
     */
    public LoadingDialog(Window parent, String mensaje) {
        super(parent, ModalityType.APPLICATION_MODAL);
        setUndecorated(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        initComponents(mensaje);
        pack();
        setLocationRelativeTo(parent);
    }
    
    private void initComponents(String mensaje) {
        // Panel principal con fondo blanco y borde redondeado
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235), 2, true),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        
        // Ícono de carga animado (usando JProgressBar indeterminado)
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setPreferredSize(new Dimension(250, 8));
        progressBar.setMaximumSize(new Dimension(250, 8));
        progressBar.setForeground(new Color(16, 185, 129)); // Verde
        progressBar.setBorderPainted(false);
        
        // Mensaje de carga
        lblMensaje = new JLabel(mensaje);
        lblMensaje.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblMensaje.setForeground(new Color(55, 65, 81));
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Label secundario
        JLabel lblEspere = new JLabel("Por favor espere...");
        lblEspere.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblEspere.setForeground(new Color(107, 114, 128));
        lblEspere.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Agregar componentes con espaciado
        mainPanel.add(lblMensaje);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(lblEspere);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Centrar el progress bar
        JPanel progressPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        progressPanel.setBackground(Color.WHITE);
        progressPanel.add(progressBar);
        mainPanel.add(progressPanel);
        
        add(mainPanel);
    }
    
    /**
     * Muestra el diálogo de carga mientras ejecuta una tarea
     * @param task Tarea a ejecutar (Runnable)
     */
    public void showWhile(Runnable task) {
        worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                task.run();
                return null;
            }
            
            @Override
            protected void done() {
                dispose();
            }
        };
        
        worker.execute();
        setVisible(true);
    }
    
    /**
     * Muestra el diálogo de carga mientras ejecuta una tarea con retorno
     * @param <T> Tipo de retorno
     * @param task Tarea a ejecutar
     * @param callback Callback con el resultado
     */
    public <T> void showWhile(java.util.concurrent.Callable<T> task, java.util.function.Consumer<T> callback) {
        SwingWorker<T, Void> taskWorker = new SwingWorker<T, Void>() {
            @Override
            protected T doInBackground() throws Exception {
                return task.call();
            }
            
            @Override
            protected void done() {
                try {
                    T result = get();
                    dispose();
                    if (callback != null) {
                        callback.accept(result);
                    }
                } catch (Exception e) {
                    dispose();
                    e.printStackTrace();
                }
            }
        };
        
        taskWorker.execute();
        setVisible(true);
    }
    
    /**
     * Cierra el diálogo de carga
     */
    public void close() {
        if (worker != null && !worker.isDone()) {
            worker.cancel(true);
        }
        dispose();
    }
    
    /**
     * Actualiza el mensaje del diálogo
     * @param mensaje Nuevo mensaje
     */
    public void setMensaje(String mensaje) {
        SwingUtilities.invokeLater(() -> lblMensaje.setText(mensaje));
    }
    
    // ========== MÉTODOS ESTÁTICOS PARA USO RÁPIDO ==========
    
    /**
     * Muestra un diálogo de carga para inicio de sesión
     */
    public static void mostrarLogin(Window parent, Runnable task) {
        LoadingDialog dialog = new LoadingDialog(parent, "Iniciando sesión...");
        dialog.showWhile(task);
    }
    
    /**
     * Muestra un diálogo de carga para registro
     */
    public static void mostrarRegistro(Window parent, Runnable task) {
        LoadingDialog dialog = new LoadingDialog(parent, "Creando cuenta...");
        dialog.showWhile(task);
    }
    
    /**
     * Muestra un diálogo de carga genérico
     */
    public static void mostrar(Window parent, String mensaje, Runnable task) {
        LoadingDialog dialog = new LoadingDialog(parent, mensaje);
        dialog.showWhile(task);
    }
    
    // ========== MÉTODO DE PRUEBA ==========
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test LoadingDialog");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);
            
            JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            // Botón 1: Login
            JButton btnLogin = new JButton("Probar Loading - Login");
            btnLogin.addActionListener(e -> {
                LoadingDialog.mostrarLogin(frame, () -> {
                    try {
                        Thread.sleep(3000); // Simular proceso de 3 segundos
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                });
                JOptionPane.showMessageDialog(frame, "Login completado!");
            });
            
            // Botón 2: Registro
            JButton btnRegistro = new JButton("Probar Loading - Registro");
            btnRegistro.addActionListener(e -> {
                LoadingDialog.mostrarRegistro(frame, () -> {
                    try {
                        Thread.sleep(2000); // Simular proceso de 2 segundos
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                });
                JOptionPane.showMessageDialog(frame, "Registro completado!");
            });
            
            // Botón 3: Personalizado
            JButton btnCustom = new JButton("Probar Loading - Personalizado");
            btnCustom.addActionListener(e -> {
                LoadingDialog.mostrar(frame, "Guardando datos...", () -> {
                    try {
                        Thread.sleep(2500); // Simular proceso de 2.5 segundos
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                });
                JOptionPane.showMessageDialog(frame, "Datos guardados!");
            });
            
            panel.add(btnLogin);
            panel.add(btnRegistro);
            panel.add(btnCustom);
            
            frame.add(panel);
            frame.setVisible(true);
        });
    }
}

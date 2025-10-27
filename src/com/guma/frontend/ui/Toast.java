package com.guma.frontend.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Componente Toast para mostrar notificaciones temporales
 * Aparece en la parte inferior de la ventana
 */
public class Toast extends JDialog {
    
    public enum TipoToast {
        EXITO(new Color(16, 185, 129), "✅"),
        ERROR(new Color(239, 68, 68), "❌"),
        ADVERTENCIA(new Color(245, 158, 11), "⚠️"),
        INFO(new Color(59, 130, 246), "ℹ️");
        
        private final Color color;
        private final String icono;
        
        TipoToast(Color color, String icono) {
            this.color = color;
            this.icono = icono;
        }
        
        public Color getColor() {
            return color;
        }
        
        public String getIcono() {
            return icono;
        }
    }
    
    private JLabel lblMensaje;
    private Timer timer;
    
    public Toast(Window parent, String mensaje, TipoToast tipo) {
        this(parent, mensaje, tipo, 3000);
    }
    
    public Toast(Window parent, String mensaje, TipoToast tipo, int duracionMs) {
        super(parent);
        setUndecorated(true);
        setModal(false);
        setAlwaysOnTop(true);
        
        // Panel principal
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(tipo.getColor());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(tipo.getColor().darker(), 1),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        
        // Icono
        JLabel lblIcono = new JLabel(tipo.getIcono());
        lblIcono.setFont(new Font("SansSerif", Font.PLAIN, 18));
        panel.add(lblIcono, BorderLayout.WEST);
        
        // Mensaje
        lblMensaje = new JLabel("<html>" + mensaje + "</html>");
        lblMensaje.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblMensaje.setForeground(Color.WHITE);
        panel.add(lblMensaje, BorderLayout.CENTER);
        
        add(panel);
        pack();
        
        // Posicionar en la parte inferior central del parent
        if (parent != null) {
            Point parentLocation = parent.getLocationOnScreen();
            Dimension parentSize = parent.getSize();
            Dimension toastSize = getSize();
            
            int x = parentLocation.x + (parentSize.width - toastSize.width) / 2;
            int y = parentLocation.y + parentSize.height - toastSize.height - 60;
            
            setLocation(x, y);
        } else {
            setLocationRelativeTo(null);
        }
        
        // Timer para auto-cerrar
        timer = new Timer(duracionMs, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animarCierre();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void animarCierre() {
        // Animación simple de fade out
        new Thread(() -> {
            try {
                for (float opacity = 1.0f; opacity >= 0; opacity -= 0.1f) {
                    final float op = opacity;
                    SwingUtilities.invokeLater(() -> setOpacity(op));
                    Thread.sleep(30);
                }
                SwingUtilities.invokeLater(() -> dispose());
            } catch (Exception e) {
                dispose();
            }
        }).start();
    }
    
    public void mostrar() {
        setVisible(true);
    }
    
    /**
     * Método estático para mostrar toast de forma rápida
     */
    public static void mostrar(Window parent, String mensaje, TipoToast tipo) {
        SwingUtilities.invokeLater(() -> {
            Toast toast = new Toast(parent, mensaje, tipo);
            toast.mostrar();
        });
    }
    
    public static void mostrar(Window parent, String mensaje, TipoToast tipo, int duracionMs) {
        SwingUtilities.invokeLater(() -> {
            Toast toast = new Toast(parent, mensaje, tipo, duracionMs);
            toast.mostrar();
        });
    }
}

package com.guma.frontend.util;

import javax.swing.*;
import java.awt.Color;

/**
 * Utilidades para validación de formularios en UI
 */
public class ValidationUtils {
    
    // Colores para validación
    public static final Color COLOR_ERROR = new Color(220, 38, 38);
    public static final Color COLOR_EXITO = new Color(34, 197, 94);
    public static final Color COLOR_NORMAL = new Color(229, 231, 235);
    
    /**
     * Valida formato de email
     */
    public static boolean esEmailValido(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
    
    /**
     * Valida que una contraseña sea fuerte
     * Requisitos: mínimo 8 caracteres
     */
    public static boolean esPasswordValida(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        
        return true;
    }    /**
     * Obtiene mensaje de error para contraseña inválida
     */
    public static String getMensajeErrorPassword(String password) {
        if (password == null || password.isEmpty()) {
            return "La contraseña es obligatoria";
        }
        if (password.length() < 8) {
            return "Mínimo 8 caracteres";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "Debe contener al menos una mayúscula";
        }
        if (!password.matches(".*[0-9].*")) {
            return "Debe contener al menos un número";
        }
        return "";
    }
    
    /**
     * Marca un campo como válido
     */
    public static void marcarCampoValido(JTextField campo) {
        campo.setBorder(BorderFactory.createLineBorder(COLOR_EXITO, 1));
    }
    
    /**
     * Marca un campo como inválido
     */
    public static void marcarCampoInvalido(JTextField campo) {
        campo.setBorder(BorderFactory.createLineBorder(COLOR_ERROR, 2));
    }
    
    /**
     * Marca un campo como inválido (sobrecarga para JComponent)
     */
    public static void marcarCampoInvalido(JComponent campo) {
        campo.setBorder(BorderFactory.createLineBorder(COLOR_ERROR, 2));
    }
    
    /**
     * Marca un campo como normal (sin validación)
     */
    public static void marcarCampoNormal(JTextField campo) {
        campo.setBorder(BorderFactory.createLineBorder(COLOR_NORMAL, 1));
    }
    
    /**
     * Restaura el borde del campo a su estado normal
     * Alias de marcarCampoNormal para mejor semántica
     */
    public static void restaurarCampoBorde(JTextField campo) {
        marcarCampoNormal(campo);
    }
    
    /**
     * Restaura el borde del campo a su estado normal (sobrecarga para JComponent)
     */
    public static void restaurarCampoBorde(JComponent campo) {
        campo.setBorder(BorderFactory.createLineBorder(COLOR_NORMAL, 1));
    }
    
    /**
     * Valida que un campo no esté vacío
     */
    public static boolean esCampoVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}

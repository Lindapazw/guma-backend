package com.guma.frontend.ui;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Campo de texto con máscara para fechas DD/MM/AAAA
 */
public class MaskedDateField extends JPanel {
    
    private JFormattedTextField txtFecha;
    
    public MaskedDateField() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        try {
            // Crear máscara para DD/MM/AAAA
            MaskFormatter maskFormatter = new MaskFormatter("##/##/####");
            maskFormatter.setPlaceholderCharacter('_');
            maskFormatter.setValueContainsLiteralCharacters(true);
            
            txtFecha = new JFormattedTextField(maskFormatter);
            txtFecha.setFont(new Font("SansSerif", Font.PLAIN, 14));
            txtFecha.setPreferredSize(new Dimension(300, 35));
            txtFecha.setToolTipText("DD/MM/AAAA - Ejemplo: 15/06/1990");
            
            add(txtFecha, BorderLayout.CENTER);
            
        } catch (Exception e) {
            // Fallback: campo normal si falla la máscara
            txtFecha = new JFormattedTextField();
            txtFecha.setFont(new Font("SansSerif", Font.PLAIN, 14));
            txtFecha.setPreferredSize(new Dimension(300, 35));
            txtFecha.setToolTipText("DD/MM/AAAA - Ejemplo: 15/06/1990");
            add(txtFecha, BorderLayout.CENTER);
        }
    }
    
    public String getText() {
        return txtFecha.getText();
    }
    
    public void setText(String text) {
        txtFecha.setText(text);
    }
    
    public JFormattedTextField getTextField() {
        return txtFecha;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        txtFecha.setEnabled(enabled);
    }
    
    @Override
    public void setToolTipText(String text) {
        txtFecha.setToolTipText(text);
    }
    
    public void requestFocus() {
        txtFecha.requestFocus();
    }
    
    /**
     * Obtiene el Document del campo interno para agregar listeners
     */
    public Document getDocument() {
        return txtFecha.getDocument();
    }
    
    /**
     * Agrega un DocumentListener al campo interno
     */
    public void addDocumentListener(DocumentListener listener) {
        txtFecha.getDocument().addDocumentListener(listener);
    }
    
    /**
     * Valida si una fecha en formato DD/MM/AAAA es válida
     */
    public static boolean esFechaValida(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            return false;
        }
        
        // Remover caracteres de placeholder
        String fechaLimpia = fecha.replace("_", "").replace("/", "");
        if (fechaLimpia.length() != 8) {
            return false;
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(fecha, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    /**
     * Parsea una fecha en formato DD/MM/AAAA a LocalDate
     */
    public static LocalDate parsearFecha(String fecha) {
        if (!esFechaValida(fecha)) {
            return null;
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(fecha, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
    /**
     * Verifica si una fecha cumple con la edad mínima especificada
     */
    public static boolean tieneMinimoEdad(String fecha, int edadMinima) {
        LocalDate fechaNac = parsearFecha(fecha);
        if (fechaNac == null) {
            return false;
        }
        
        Period periodo = Period.between(fechaNac, LocalDate.now());
        return periodo.getYears() >= edadMinima;
    }
}

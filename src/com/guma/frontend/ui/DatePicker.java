package com.guma.frontend.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * Componente DatePicker personalizado para Swing
 * Permite seleccionar fechas mediante calendario visual
 */
public class DatePicker extends JPanel {
    
    private JTextField txtFecha;
    private JButton btnCalendario;
    private JDialog dialogoCalendario;
    private LocalDate fechaSeleccionada;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public DatePicker() {
        setLayout(new BorderLayout(5, 0));
        setBackground(Color.WHITE);
        
        // Campo de texto (read-only)
        txtFecha = new JTextField(12);
        txtFecha.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtFecha.setPreferredSize(new Dimension(200, 35));
        txtFecha.setEditable(false);
        txtFecha.setBackground(Color.WHITE);
        txtFecha.setToolTipText("Seleccione una fecha");
        
        // Botón calendario
        btnCalendario = new JButton("📅");
        btnCalendario.setFont(new Font("SansSerif", Font.PLAIN, 16));
        btnCalendario.setPreferredSize(new Dimension(45, 35));
        btnCalendario.setFocusPainted(false);
        btnCalendario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCalendario.setToolTipText("Abrir calendario");
        
        btnCalendario.addActionListener(e -> mostrarCalendario());
        
        add(txtFecha, BorderLayout.CENTER);
        add(btnCalendario, BorderLayout.EAST);
    }
    
    private void mostrarCalendario() {
        // Crear diálogo
        Window parent = SwingUtilities.getWindowAncestor(this);
        dialogoCalendario = new JDialog((Frame) parent, "Seleccionar Fecha", true);
        dialogoCalendario.setSize(350, 400);
        dialogoCalendario.setLocationRelativeTo(this);
        dialogoCalendario.setResizable(false);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);
        
        // Fecha inicial (hoy o la seleccionada)
        LocalDate fechaActual = fechaSeleccionada != null ? fechaSeleccionada : LocalDate.now().minusYears(18);
        final LocalDate[] fechaTemporal = {fechaActual};
        
        // Panel superior: selector de mes/año
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Color.WHITE);
        
        JButton btnAnterior = new JButton("◀");
        btnAnterior.setFocusPainted(false);
        
        JButton btnSiguiente = new JButton("▶");
        btnSiguiente.setFocusPainted(false);
        
        JLabel lblMesAnio = new JLabel();
        lblMesAnio.setHorizontalAlignment(SwingConstants.CENTER);
        lblMesAnio.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        panelSuperior.add(btnAnterior, BorderLayout.WEST);
        panelSuperior.add(lblMesAnio, BorderLayout.CENTER);
        panelSuperior.add(btnSiguiente, BorderLayout.EAST);
        
        // Panel de días
        JPanel panelDias = new JPanel(new GridLayout(0, 7, 5, 5));
        panelDias.setBackground(Color.WHITE);
        
        // Función para actualizar calendario
        Runnable actualizarCalendario = () -> {
            panelDias.removeAll();
            
            // Actualizar label mes/año
            String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                             "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
            lblMesAnio.setText(meses[fechaTemporal[0].getMonthValue() - 1] + " " + fechaTemporal[0].getYear());
            
            // Encabezados días de semana
            String[] diasSemana = {"Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb"};
            for (String dia : diasSemana) {
                JLabel lbl = new JLabel(dia, SwingConstants.CENTER);
                lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
                lbl.setForeground(new Color(107, 114, 128));
                panelDias.add(lbl);
            }
            
            // Obtener primer día del mes
            YearMonth yearMonth = YearMonth.from(fechaTemporal[0]);
            LocalDate primerDia = yearMonth.atDay(1);
            int diaSemana = primerDia.getDayOfWeek().getValue() % 7; // 0=Domingo
            
            // Espacios en blanco antes del primer día
            for (int i = 0; i < diaSemana; i++) {
                panelDias.add(new JLabel(""));
            }
            
            // Botones de días
            LocalDate hoy = LocalDate.now();
            LocalDate hace18Anios = hoy.minusYears(18);
            
            for (int dia = 1; dia <= yearMonth.lengthOfMonth(); dia++) {
                LocalDate fecha = yearMonth.atDay(dia);
                JButton btnDia = new JButton(String.valueOf(dia));
                btnDia.setFont(new Font("SansSerif", Font.PLAIN, 12));
                btnDia.setFocusPainted(false);
                btnDia.setBorderPainted(true);
                btnDia.setBackground(Color.WHITE);
                btnDia.setCursor(new Cursor(Cursor.HAND_CURSOR));
                
                // Deshabilitar fechas futuras o menores de 18 años
                if (fecha.isAfter(hace18Anios)) {
                    btnDia.setEnabled(false);
                    btnDia.setForeground(Color.LIGHT_GRAY);
                }
                
                // Resaltar día seleccionado
                if (fechaSeleccionada != null && fecha.equals(fechaSeleccionada)) {
                    btnDia.setBackground(new Color(37, 99, 235));
                    btnDia.setForeground(Color.WHITE);
                }
                
                // Resaltar hoy
                if (fecha.equals(hoy)) {
                    btnDia.setBorder(BorderFactory.createLineBorder(new Color(37, 99, 235), 2));
                }
                
                LocalDate fechaFinal = fecha;
                btnDia.addActionListener(e -> {
                    fechaSeleccionada = fechaFinal;
                    txtFecha.setText(fechaFinal.format(formatter));
                    dialogoCalendario.dispose();
                });
                
                panelDias.add(btnDia);
            }
            
            panelDias.revalidate();
            panelDias.repaint();
        };
        
        // Listeners para botones anterior/siguiente
        btnAnterior.addActionListener(e -> {
            fechaTemporal[0] = fechaTemporal[0].minusMonths(1);
            actualizarCalendario.run();
        });
        
        btnSiguiente.addActionListener(e -> {
            fechaTemporal[0] = fechaTemporal[0].plusMonths(1);
            actualizarCalendario.run();
        });
        
        // Panel inferior: botones
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBackground(Color.WHITE);
        
        JButton btnHoy = new JButton("Hoy");
        btnHoy.setFocusPainted(false);
        btnHoy.addActionListener(e -> {
            fechaTemporal[0] = LocalDate.now().minusYears(18);
            actualizarCalendario.run();
        });
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> dialogoCalendario.dispose());
        
        panelInferior.add(btnHoy);
        panelInferior.add(btnCancelar);
        
        // Armar diálogo
        mainPanel.add(panelSuperior, BorderLayout.NORTH);
        mainPanel.add(panelDias, BorderLayout.CENTER);
        mainPanel.add(panelInferior, BorderLayout.SOUTH);
        
        dialogoCalendario.add(mainPanel);
        
        // Mostrar calendario inicial
        actualizarCalendario.run();
        
        dialogoCalendario.setVisible(true);
    }
    
    // Getters y Setters
    public LocalDate getFechaSeleccionada() {
        return fechaSeleccionada;
    }
    
    public void setFechaSeleccionada(LocalDate fecha) {
        this.fechaSeleccionada = fecha;
        if (fecha != null) {
            txtFecha.setText(fecha.format(formatter));
        } else {
            txtFecha.setText("");
        }
    }
    
    public String getTexto() {
        return txtFecha.getText();
    }
    
    public JTextField getTextField() {
        return txtFecha;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        txtFecha.setEnabled(enabled);
        btnCalendario.setEnabled(enabled);
    }
    
    @Override
    public void setToolTipText(String text) {
        txtFecha.setToolTipText(text);
        btnCalendario.setToolTipText(text);
    }
}

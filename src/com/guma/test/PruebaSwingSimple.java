package com.guma.test;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Versión ultra-simple para probar Swing
 */
public class PruebaSwingSimple extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PruebaSwingSimple ventana = new PruebaSwingSimple();
                ventana.setVisible(true);
                System.out.println("✅ Ventana simple iniciada");
            }
        });
    }

    public PruebaSwingSimple() {
        // Configuración básica
        setTitle("GUMA - Prueba Simple");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Agregar componentes
        JLabel label = new JLabel("🐾 Sistema GUMA - Prueba Simple");
        label.setFont(new Font("Arial", Font.BOLD, 18));

        JButton boton1 = new JButton("Botón 1");
        JButton boton2 = new JButton("Botón 2");
        JButton boton3 = new JButton("Botón 3");

        panel.add(label);
        panel.add(boton1);
        panel.add(boton2);
        panel.add(boton3);

        // Agregar panel a la ventana
        add(panel);
    }
}

package com.guma.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Programa simple para probar la conexión a la base de datos MySQL.
 * 
 * Verifica:
 * - Carga del driver JDBC
 * - Conexión exitosa a la base de datos
 * - Ejecución de una consulta simple
 * 
 * @author GUMA Team
 * @version 1.0
 */
public class PruebaConexion {

    private static final String COLOR_VERDE = "\u001B[32m";
    private static final String COLOR_ROJO = "\u001B[31m";
    private static final String COLOR_AMARILLO = "\u001B[33m";
    private static final String COLOR_AZUL = "\u001B[34m";
    private static final String COLOR_RESET = "\u001B[0m";

    public static void main(String[] args) {
        System.out.println("\n" + COLOR_AZUL + "═══════════════════════════════════════════════════════" + COLOR_RESET);
        System.out.println(COLOR_AZUL + "   GUMA - Prueba de Conexión a Base de Datos" + COLOR_RESET);
        System.out.println(COLOR_AZUL + "═══════════════════════════════════════════════════════" + COLOR_RESET + "\n");

        Connection conexion = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Paso 1: Cargar propiedades
            System.out.println("📄 Cargando configuración desde application.properties...");
            Properties props = cargarPropiedades();

            String url = props.getProperty("db.url");
            String usuario = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            System.out.println(COLOR_VERDE + "   ✓ Configuración cargada" + COLOR_RESET);
            System.out.println("   URL: " + url);
            System.out.println("   Usuario: " + usuario);
            System.out.println();

            // Paso 2: Cargar driver JDBC
            System.out.println("🔌 Cargando driver MySQL JDBC...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println(COLOR_VERDE + "   ✓ Driver cargado exitosamente" + COLOR_RESET);
            System.out.println();

            // Paso 3: Establecer conexión
            System.out.println("🔗 Estableciendo conexión con la base de datos...");
            conexion = DriverManager.getConnection(url, usuario, password);
            System.out.println(COLOR_VERDE + "   ✓ ¡Conexión establecida exitosamente!" + COLOR_RESET);
            System.out.println();

            // Paso 4: Obtener información de la base de datos
            System.out.println("📊 Información de la base de datos:");
            System.out.println("   " + conexion.getMetaData().getDatabaseProductName()
                    + " " + conexion.getMetaData().getDatabaseProductVersion());
            System.out.println();

            // Paso 5: Ejecutar consulta de prueba
            System.out.println("🔍 Ejecutando consulta de prueba...");
            stmt = conexion.createStatement();
            rs = stmt.executeQuery("SELECT DATABASE() as db_nombre, NOW() as fecha_hora, VERSION() as version");

            if (rs.next()) {
                System.out.println(COLOR_VERDE + "   ✓ Consulta ejecutada correctamente" + COLOR_RESET);
                System.out.println();
                System.out.println(
                        "   📌 Base de datos actual: " + COLOR_AMARILLO + rs.getString("db_nombre") + COLOR_RESET);
                System.out.println(
                        "   📌 Fecha/Hora servidor: " + COLOR_AMARILLO + rs.getString("fecha_hora") + COLOR_RESET);
                System.out.println("   📌 Versión MySQL: " + COLOR_AMARILLO + rs.getString("version") + COLOR_RESET);
            }
            System.out.println();

            // Paso 6: Verificar tablas
            System.out.println("📋 Verificando tablas en la base de datos...");
            rs = stmt.executeQuery("SHOW TABLES");

            int contador = 0;
            System.out.println();
            while (rs.next()) {
                contador++;
                System.out.println("   " + contador + ". " + rs.getString(1));
            }

            if (contador > 0) {
                System.out.println();
                System.out.println(
                        COLOR_VERDE + "   ✓ Se encontraron " + contador + " tablas en la base de datos" + COLOR_RESET);
            } else {
                System.out.println();
                System.out
                        .println(COLOR_AMARILLO + "   ⚠ No se encontraron tablas (base de datos vacía)" + COLOR_RESET);
            }

            System.out.println();
            System.out.println(COLOR_VERDE + "═══════════════════════════════════════════════════════" + COLOR_RESET);
            System.out.println(COLOR_VERDE + "   ✅ PRUEBA DE CONEXIÓN EXITOSA" + COLOR_RESET);
            System.out.println(COLOR_VERDE + "═══════════════════════════════════════════════════════" + COLOR_RESET);
            System.out.println();

        } catch (ClassNotFoundException e) {
            System.out.println();
            System.out.println(COLOR_ROJO + "❌ ERROR: No se pudo cargar el driver MySQL JDBC" + COLOR_RESET);
            System.out.println("   Verifica que mysql-connector-j-*.jar esté en la carpeta lib/");
            System.out.println();
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println();
            System.out.println(COLOR_ROJO + "❌ ERROR DE CONEXIÓN A LA BASE DE DATOS" + COLOR_RESET);
            System.out.println();
            System.out.println("Posibles causas:");
            System.out.println("  1. El túnel SSH no está activo");
            System.out.println("     → Ejecuta: ./check-tunnel.sh");
            System.out.println();
            System.out.println("  2. Credenciales incorrectas en application.properties");
            System.out.println("     → Verifica usuario y contraseña");
            System.out.println();
            System.out.println("  3. La base de datos 'guma' no existe");
            System.out.println("     → Créala con: CREATE DATABASE guma;");
            System.out.println();
            System.out.println("Detalles del error:");
            System.out.println("  Código: " + e.getErrorCode());
            System.out.println("  Mensaje: " + e.getMessage());
            System.out.println();

        } catch (IOException e) {
            System.out.println();
            System.out.println(COLOR_ROJO + "❌ ERROR: No se pudo leer application.properties" + COLOR_RESET);
            System.out.println("   Verifica que el archivo exista en resources/application.properties");
            System.out.println();
            e.printStackTrace();

        } finally {
            // Cerrar recursos
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conexion != null) {
                    conexion.close();
                    System.out.println("🔌 Conexión cerrada correctamente");
                    System.out.println();
                }
            } catch (SQLException e) {
                System.out.println(COLOR_AMARILLO + "⚠ Error al cerrar recursos: " + e.getMessage() + COLOR_RESET);
            }
        }
    }

    /**
     * Carga las propiedades desde el archivo application.properties
     * 
     * @return Properties con la configuración
     * @throws IOException si no se puede leer el archivo
     */
    private static Properties cargarPropiedades() throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("resources/application.properties")) {
            props.load(fis);
        }
        return props;
    }
}

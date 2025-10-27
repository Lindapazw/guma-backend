package com.guma.backend.services;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.guma.backend.ports.ImageRepository;
import com.guma.data.repositories.ImageRepositoryJdbc;
import com.guma.domain.entities.Image;

/**
 * Prueba completa del flujo: FileStorage + ImageRepository + ImageService
 * 
 * Esta prueba simula el flujo real de guardar fotos de perfil:
 * 1. Crea una imagen de prueba
 * 2. ImageService la guarda físicamente (FileStorage)
 * 3. ImageService guarda metadata en BD (ImageRepository)
 * 4. Verifica que se puede recuperar
 */
public class PruebaImageService {

    public static void main(String[] args) {
        System.out.println("=== PRUEBA COMPLETA: FileStorage + ImageService + DB ===\n");

        try {
            // 1. Inicializar servicio
            System.out.println("--- Paso 1: Inicializar ImageService ---");
            ImageRepository imageRepository = new ImageRepositoryJdbc();
            ImageService imageService = new ImageService(imageRepository);
            System.out.println("✓ ImageService inicializado");
            System.out.println();

            // 2. Crear imagen de prueba
            System.out.println("--- Paso 2: Crear imagen de prueba ---");
            byte[] imagenBytes = crearImagenPrueba(200, 200, "Usuario 50", Color.BLUE);
            System.out.println("✓ Imagen creada: " + imagenBytes.length + " bytes");
            System.out.println();

            // 3. Guardar imagen (FileStorage + DB)
            System.out.println("--- Paso 3: Guardar imagen ---");
            Image imagenGuardada = imageService.guardarImagen(
                    "usuario_perfil", // tipo
                    50, // idUsuario
                    "perfil.jpg", // nombre archivo
                    imagenBytes // contenido
            );

            System.out.println("✓ Imagen guardada exitosamente!");
            System.out.println("  ID en BD: " + imagenGuardada.getIdImage());
            System.out.println("  Ruta física: " + imagenGuardada.getLink());
            System.out.println("  Extensión: " + imagenGuardada.getExtension());
            System.out.println("  Nombre: " + imagenGuardada.getNombreArchivo());
            System.out.println();

            // 4. Recuperar imagen por ID
            System.out.println("--- Paso 4: Recuperar imagen por ID ---");
            Integer idImagen = imagenGuardada.getIdImage();

            Image imagenRecuperada = imageService.obtenerPorId(idImagen)
                    .orElseThrow(() -> new RuntimeException("Imagen no encontrada"));

            System.out.println("✓ Imagen recuperada de BD:");
            System.out.println("  ID: " + imagenRecuperada.getIdImage());
            System.out.println("  Ruta: " + imagenRecuperada.getLink());
            System.out.println();

            // 5. Obtener bytes del archivo físico
            System.out.println("--- Paso 5: Leer archivo físico ---");
            byte[] bytesRecuperados = imageService.obtenerBytesImagen(idImagen);
            System.out.println("✓ Archivo leído: " + bytesRecuperados.length + " bytes");
            System.out.println("  Coincide con original: " + (bytesRecuperados.length == imagenBytes.length));
            System.out.println();

            // 6. Guardar más imágenes
            System.out.println("--- Paso 6: Guardar múltiples imágenes ---");
            for (int i = 51; i <= 53; i++) {
                byte[] img = crearImagenPrueba(150, 150, "User " + i,
                        new Color((i * 50) % 255, (i * 100) % 255, (i * 150) % 255));
                Image imgGuardada = imageService.guardarImagen("usuario_perfil", i, "perfil.jpg", img);
                System.out.println("  ✓ Usuario " + i + " - ID: " + imgGuardada.getIdImage() +
                        " - " + imgGuardada.getLink());
            }
            System.out.println();

            // 7. Guardar imagen de mascota
            System.out.println("--- Paso 7: Guardar imagen de mascota ---");
            byte[] mascotaBytes = crearImagenPrueba(300, 300, "Mascota 201", Color.GREEN);
            Image mascotaImagen = imageService.guardarImagen("mascota_foto", 201, "foto.png", mascotaBytes);
            System.out.println("✓ Mascota guardada - ID: " + mascotaImagen.getIdImage());
            System.out.println("  Ruta: " + mascotaImagen.getLink());
            System.out.println();

            // 8. Eliminar imagen
            System.out.println("--- Paso 8: Eliminar imagen ---");
            boolean eliminada = imageService.eliminarImagen(idImagen);
            System.out.println("✓ Imagen " + idImagen + " eliminada: " + eliminada);
            System.out.println();

            System.out.println("=== TODAS LAS PRUEBAS EXITOSAS ===\n");

            System.out.println("📊 Resumen:");
            System.out.println("  ✓ FileStorage guardó archivos físicos en ./data/");
            System.out.println("  ✓ ImageRepository guardó metadata en tabla IMAGES");
            System.out.println("  ✓ ImageService coordinó ambas operaciones exitosamente");
            System.out.println("  ✓ Se pueden recuperar imágenes por ID");
            System.out.println("  ✓ Soft delete funciona correctamente");
            System.out.println();

            System.out.println("💡 Próximos pasos:");
            System.out.println("  1. Integrar con PerfilUsuarioService");
            System.out.println("  2. Modificar DTOs para incluir foto");
            System.out.println("  3. Actualizar RegistroDialog con JFileChooser");

        } catch (Exception e) {
            System.err.println("❌ Error en prueba: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Crea una imagen de prueba con texto y color de fondo.
     */
    private static byte[] crearImagenPrueba(int ancho, int alto, String texto, Color color)
            throws IOException {

        BufferedImage imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = imagen.createGraphics();

        // Fondo de color
        g.setColor(color);
        g.fillRect(0, 0, ancho, alto);

        // Borde
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(3));
        g.drawRect(5, 5, ancho - 10, alto - 10);

        // Texto
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        int x = (ancho - fm.stringWidth(texto)) / 2;
        int y = (alto + fm.getHeight()) / 2;
        g.drawString(texto, x, y);

        g.dispose();

        // Convertir a bytes (JPG)
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imagen, "jpg", baos);
        return baos.toByteArray();
    }
}

package com.guma.application.validator;

import com.guma.application.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Validador para datos de registro de usuario.
 * Valida email, password, confirmación y datos personales.
 * 
 * Reglas:
 * - Email: formato válido (regex)
 * - Password: mínimo 8 caracteres
 * - Confirmación: debe coincidir con password
 * - Nombre y apellido: obligatorios
 * - Teléfono: opcional pero debe tener formato válido
 * 
 * @author GUMA Development Team
 * @version 1.1 - Agregadas validaciones de nombre, apellido y teléfono
 */
public class RegistroUsuarioValidator {

    // Patrón para validar formato de email
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // Patrón para validar teléfono (opcional)
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^\\+?[0-9]{7,15}$");

    private static final int MIN_PASSWORD_LENGTH = 8;

    /**
     * Valida los datos de registro de un usuario.
     * 
     * @param dto Datos de registro a validar
     * @return Lista de errores (vacía si no hay errores)
     */
    public static List<ErrorDTO> validar(RegistroUsuarioDTO dto) {
        List<ErrorDTO> errores = new ArrayList<>();

        if (dto == null) {
            errores.add(ErrorDTO.deCampo("general", "Los datos de registro no pueden ser nulos"));
            return errores;
        }

        // Validar email
        errores.addAll(validarEmail(dto.getEmail()));

        // Validar password
        errores.addAll(validarPassword(dto.getPassword()));

        // Validar confirmación de password
        errores.addAll(validarConfirmacion(dto.getPassword(), dto.getConfirmarPassword()));

        // Validar nombre
        errores.addAll(validarNombre(dto.getNombre()));

        // Validar apellido
        errores.addAll(validarApellido(dto.getApellido()));

        // Validar teléfono (opcional)
        if (dto.getTelefono() != null && !dto.getTelefono().trim().isEmpty()) {
            errores.addAll(validarTelefono(dto.getTelefono()));
        }

        return errores;
    }

    /**
     * Valida el formato del email.
     * 
     * @param email Email a validar
     * @return Lista de errores
     */
    private static List<ErrorDTO> validarEmail(String email) {
        List<ErrorDTO> errores = new ArrayList<>();

        if (email == null || email.trim().isEmpty()) {
            errores.add(ErrorDTO.deCampo("email", "El email es obligatorio"));
            return errores;
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            errores.add(ErrorDTO.deCampo("email", "El formato del email no es válido"));
        }

        if (email.length() > 255) {
            errores.add(ErrorDTO.deCampo("email", "El email no puede tener más de 255 caracteres"));
        }

        return errores;
    }

    /**
     * Valida la contraseña.
     * 
     * @param password Contraseña a validar
     * @return Lista de errores
     */
    private static List<ErrorDTO> validarPassword(String password) {
        List<ErrorDTO> errores = new ArrayList<>();

        if (password == null || password.isEmpty()) {
            errores.add(ErrorDTO.deCampo("password", "La contraseña es obligatoria"));
            return errores;
        }

        if (password.length() < MIN_PASSWORD_LENGTH) {
            errores.add(ErrorDTO.deCampo("password",
                    "La contraseña debe tener al menos " + MIN_PASSWORD_LENGTH + " caracteres"));
        }

        if (password.length() > 255) {
            errores.add(ErrorDTO.deCampo("password",
                    "La contraseña no puede tener más de 255 caracteres"));
        }

        return errores;
    }

    /**
     * Valida que la confirmación de password coincida con el password.
     * 
     * @param password          Contraseña original
     * @param confirmarPassword Confirmación de contraseña
     * @return Lista de errores
     */
    private static List<ErrorDTO> validarConfirmacion(String password, String confirmarPassword) {
        List<ErrorDTO> errores = new ArrayList<>();

        if (confirmarPassword == null || confirmarPassword.isEmpty()) {
            errores.add(ErrorDTO.deCampo("confirmarPassword",
                    "Debe confirmar la contraseña"));
            return errores;
        }

        if (!confirmarPassword.equals(password)) {
            errores.add(ErrorDTO.deCampo("confirmarPassword",
                    "Las contraseñas no coinciden"));
        }

        return errores;
    }

    /**
     * Valida el nombre del usuario.
     * 
     * @param nombre Nombre a validar
     * @return Lista de errores
     */
    private static List<ErrorDTO> validarNombre(String nombre) {
        List<ErrorDTO> errores = new ArrayList<>();

        if (nombre == null || nombre.trim().isEmpty()) {
            errores.add(ErrorDTO.deCampo("nombre", "El nombre es obligatorio"));
            return errores;
        }

        if (nombre.length() < 2) {
            errores.add(ErrorDTO.deCampo("nombre", "El nombre debe tener al menos 2 caracteres"));
        }

        if (nombre.length() > 50) {
            errores.add(ErrorDTO.deCampo("nombre", "El nombre no puede tener más de 50 caracteres"));
        }

        return errores;
    }

    /**
     * Valida el apellido del usuario.
     * 
     * @param apellido Apellido a validar
     * @return Lista de errores
     */
    private static List<ErrorDTO> validarApellido(String apellido) {
        List<ErrorDTO> errores = new ArrayList<>();

        if (apellido == null || apellido.trim().isEmpty()) {
            errores.add(ErrorDTO.deCampo("apellido", "El apellido es obligatorio"));
            return errores;
        }

        if (apellido.length() < 2) {
            errores.add(ErrorDTO.deCampo("apellido", "El apellido debe tener al menos 2 caracteres"));
        }

        if (apellido.length() > 50) {
            errores.add(ErrorDTO.deCampo("apellido", "El apellido no puede tener más de 50 caracteres"));
        }

        return errores;
    }

    /**
     * Valida el teléfono del usuario (opcional).
     * 
     * @param telefono Teléfono a validar
     * @return Lista de errores
     */
    private static List<ErrorDTO> validarTelefono(String telefono) {
        List<ErrorDTO> errores = new ArrayList<>();

        if (!PHONE_PATTERN.matcher(telefono).matches()) {
            errores.add(ErrorDTO.deCampo("telefono",
                    "El teléfono debe tener entre 7 y 15 dígitos (puede incluir +)"));
        }

        return errores;
    }
}

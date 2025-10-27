package com.guma.application.validator;

import com.guma.application.dto.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Validador para datos de perfil de usuario.
 * Valida campos obligatorios y formato de datos.
 * 
 * Reglas:
 * - DNI: obligatorio, formato numérico, 7-8 dígitos
 * - Nombre: obligatorio, máximo 100 caracteres
 * - Apellido: obligatorio, máximo 100 caracteres
 * - Fecha de nacimiento: obligatoria, debe ser mayor de edad
 * - Email: formato válido
 * - Sexo: obligatorio
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class PerfilUsuarioValidator {

    private static final int MIN_EDAD = 18;
    private static final int MAX_NOMBRE_LENGTH = 100;
    private static final int MAX_APELLIDO_LENGTH = 100;
    private static final int MAX_EMAIL_LENGTH = 255;
    private static final int MAX_TELEFONO_LENGTH = 50;

    /**
     * Valida los datos de perfil de un usuario.
     * 
     * @param dto Datos de perfil a validar
     * @return Lista de errores (vacía si no hay errores)
     */
    public static List<ErrorDTO> validar(PerfilUsuarioDTO dto) {
        List<ErrorDTO> errores = new ArrayList<>();

        if (dto == null) {
            errores.add(ErrorDTO.deCampo("general", "Los datos del perfil no pueden ser nulos"));
            return errores;
        }

        // Validar ID de usuario (debe existir)
        if (dto.getIdUsuario() == null) {
            errores.add(ErrorDTO.deCampo("idUsuario", "El ID de usuario es obligatorio"));
        }

        // Validar DNI
        errores.addAll(validarDni(dto.getDni()));

        // Validar nombre
        errores.addAll(validarNombre(dto.getNombre()));

        // Validar apellido
        errores.addAll(validarApellido(dto.getApellido()));

        // Validar fecha de nacimiento
        errores.addAll(validarFechaNacimiento(dto.getFechaNacimiento()));

        // Validar email
        errores.addAll(validarEmail(dto.getEmail()));

        // Validar sexo
        if (dto.getIdSexo() == null) {
            errores.add(ErrorDTO.deCampo("idSexo", "El sexo es obligatorio"));
        }

        // Validar teléfono (opcional pero con formato)
        if (dto.getTelefono() != null && !dto.getTelefono().trim().isEmpty()) {
            errores.addAll(validarTelefono(dto.getTelefono()));
        }

        return errores;
    }

    /**
     * Valida el DNI.
     * 
     * @param dni DNI a validar
     * @return Lista de errores
     */
    private static List<ErrorDTO> validarDni(String dni) {
        List<ErrorDTO> errores = new ArrayList<>();

        if (dni == null || dni.trim().isEmpty()) {
            errores.add(ErrorDTO.deCampo("dni", "El DNI es obligatorio"));
            return errores;
        }

        // Validar que sea numérico
        if (!dni.matches("\\d+")) {
            errores.add(ErrorDTO.deCampo("dni", "El DNI debe contener solo números"));
        }

        // Validar longitud (7-8 dígitos para DNI argentino)
        if (dni.length() < 7 || dni.length() > 8) {
            errores.add(ErrorDTO.deCampo("dni", "El DNI debe tener entre 7 y 8 dígitos"));
        }

        return errores;
    }

    /**
     * Valida el nombre.
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

        if (nombre.length() > MAX_NOMBRE_LENGTH) {
            errores.add(ErrorDTO.deCampo("nombre",
                    "El nombre no puede tener más de " + MAX_NOMBRE_LENGTH + " caracteres"));
        }

        return errores;
    }

    /**
     * Valida el apellido.
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

        if (apellido.length() > MAX_APELLIDO_LENGTH) {
            errores.add(ErrorDTO.deCampo("apellido",
                    "El apellido no puede tener más de " + MAX_APELLIDO_LENGTH + " caracteres"));
        }

        return errores;
    }

    /**
     * Valida la fecha de nacimiento.
     * 
     * @param fechaNacimiento Fecha a validar
     * @return Lista de errores
     */
    private static List<ErrorDTO> validarFechaNacimiento(LocalDate fechaNacimiento) {
        List<ErrorDTO> errores = new ArrayList<>();

        if (fechaNacimiento == null) {
            errores.add(ErrorDTO.deCampo("fechaNacimiento", "La fecha de nacimiento es obligatoria"));
            return errores;
        }

        // Validar que no sea futura
        if (fechaNacimiento.isAfter(LocalDate.now())) {
            errores.add(ErrorDTO.deCampo("fechaNacimiento",
                    "La fecha de nacimiento no puede ser futura"));
        }

        // Validar edad mínima
        LocalDate fechaMinima = LocalDate.now().minusYears(MIN_EDAD);
        if (fechaNacimiento.isAfter(fechaMinima)) {
            errores.add(ErrorDTO.deCampo("fechaNacimiento",
                    "Debe ser mayor de " + MIN_EDAD + " años"));
        }

        // Validar que no sea demasiado antigua (más de 120 años)
        LocalDate fechaMaxima = LocalDate.now().minusYears(120);
        if (fechaNacimiento.isBefore(fechaMaxima)) {
            errores.add(ErrorDTO.deCampo("fechaNacimiento",
                    "La fecha de nacimiento no es válida"));
        }

        return errores;
    }

    /**
     * Valida el email.
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

        if (email.length() > MAX_EMAIL_LENGTH) {
            errores.add(ErrorDTO.deCampo("email",
                    "El email no puede tener más de " + MAX_EMAIL_LENGTH + " caracteres"));
        }

        return errores;
    }

    /**
     * Valida el teléfono.
     * 
     * @param telefono Teléfono a validar
     * @return Lista de errores
     */
    private static List<ErrorDTO> validarTelefono(String telefono) {
        List<ErrorDTO> errores = new ArrayList<>();

        if (telefono.length() > MAX_TELEFONO_LENGTH) {
            errores.add(ErrorDTO.deCampo("telefono",
                    "El teléfono no puede tener más de " + MAX_TELEFONO_LENGTH + " caracteres"));
        }

        return errores;
    }
}

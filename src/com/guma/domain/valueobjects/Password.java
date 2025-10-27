package com.guma.domain.valueobjects;

import java.util.Objects;
import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Value Object que representa una contraseña segura en el sistema GUMA.
 * 
 * Este objeto garantiza que todas las contraseñas cumplan con los requisitos
 * mínimos de seguridad y se almacenen siempre hasheadas usando BCrypt.
 * 
 * Requisitos de seguridad:
 * - Mínimo 8 caracteres
 * - Al menos una letra mayúscula
 * - Al menos un número
 * 
 * Ejemplo de uso:
 * 
 * <pre>
 * Password password = Password.crear("MiPass123");
 * boolean esValida = password.verificar("MiPass123");
 * </pre>
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public final class Password {

    private static final int LONGITUD_MINIMA = 8;
    private static final int LONGITUD_MAXIMA = 100;
    private static final int BCRYPT_ROUNDS = 10;

    /**
     * Patrón para validar que contenga al menos una mayúscula.
     */
    private static final Pattern PATRON_MAYUSCULA = Pattern.compile(".*[A-Z].*");

    /**
     * Patrón para validar que contenga al menos un número.
     */
    private static final Pattern PATRON_NUMERO = Pattern.compile(".*[0-9].*");

    /**
     * Crea una nueva Password desde texto plano, aplicando validaciones y hashing.
     * 
     * @param passwordPlano la contraseña en texto plano
     * @return una instancia de Password con el hash BCrypt
     * @throws IllegalArgumentException si la contraseña no cumple los requisitos de
     *                                  seguridad
     */
    public static Password crear(String passwordPlano) {
        validarFortaleza(passwordPlano);
        String hash = BCrypt.hashpw(passwordPlano, BCrypt.gensalt(BCRYPT_ROUNDS));
        return new Password(hash);
    }

    /**
     * Crea una instancia de Password desde un hash existente (por ejemplo, al leer
     * de BD).
     * 
     * @param hash el hash BCrypt existente
     * @return una instancia de Password
     * @throws IllegalArgumentException si el hash es nulo o vacío
     */
    public static Password desdeHash(String hash) {
        if (hash == null || hash.trim().isEmpty()) {
            throw new IllegalArgumentException("El hash de la contraseña no puede ser nulo ni vacío");
        }
        return new Password(hash);
    }

    /**
     * Valida que la contraseña cumpla con los requisitos de seguridad.
     * 
     * @param passwordPlano la contraseña a validar
     * @throws IllegalArgumentException si no cumple algún requisito
     */
    private static void validarFortaleza(String passwordPlano) {
        if (passwordPlano == null || passwordPlano.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula ni vacía");
        }

        if (passwordPlano.length() < LONGITUD_MINIMA) {
            throw new IllegalArgumentException(
                    "La contraseña debe tener al menos " + LONGITUD_MINIMA + " caracteres");
        }

        if (passwordPlano.length() > LONGITUD_MAXIMA) {
            throw new IllegalArgumentException(
                    "La contraseña no puede exceder " + LONGITUD_MAXIMA + " caracteres");
        }

        if (!PATRON_MAYUSCULA.matcher(passwordPlano).matches()) {
            throw new IllegalArgumentException(
                    "La contraseña debe contener al menos una letra mayúscula");
        }

        if (!PATRON_NUMERO.matcher(passwordPlano).matches()) {
            throw new IllegalArgumentException(
                    "La contraseña debe contener al menos un número");
        }
    }

    private final String hash;

    /**
     * Constructor privado. Usar los métodos estáticos para instanciar.
     * 
     * @param hash el hash BCrypt de la contraseña
     */
    private Password(String hash) {
        this.hash = hash;
    }

    /**
     * Verifica si una contraseña en texto plano coincide con este hash.
     * 
     * @param passwordPlano la contraseña a verificar
     * @return true si la contraseña es correcta, false en caso contrario
     */
    public boolean verificar(String passwordPlano) {
        if (passwordPlano == null || passwordPlano.isEmpty()) {
            return false;
        }
        return BCrypt.checkpw(passwordPlano, hash);
    }

    /**
     * Obtiene el hash BCrypt de la contraseña.
     * 
     * @return el hash BCrypt
     */
    public String getHash() {
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Password password = (Password) o;
        return Objects.equals(hash, password.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash);
    }

    @Override
    public String toString() {
        // Por seguridad, nunca mostramos el hash completo
        return "Password{hash=*****}";
    }
}

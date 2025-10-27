package com.guma.domain.valueobjects;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value Object que representa una dirección de correo electrónico válida.
 * 
 * Este objeto es inmutable y garantiza que cualquier instancia contenga
 * una dirección de email con formato válido según RFC 5322 (versión
 * simplificada).
 * 
 * Ejemplo de uso:
 * 
 * <pre>
 * Email email = Email.crear("usuario@ejemplo.com");
 * </pre>
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public final class Email {

    /**
     * Patrón regex para validación de formato de email.
     * Permite: letras, números, puntos, guiones y guión bajo antes y después del @
     * Requiere al menos un punto después del @ y extensión de 2 o más caracteres.
     */
    private static final Pattern PATRON_EMAIL = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final int LONGITUD_MAXIMA = 255;

    /**
     * Crea una nueva instancia de Email validando el formato.
     * 
     * @param email la dirección de correo electrónico a validar
     * @return una instancia de Email si la validación es exitosa
     * @throws IllegalArgumentException si el email es nulo, vacío o tiene formato
     *                                  inválido
     */
    public static Email crear(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser nulo ni vacío");
        }

        String emailNormalizado = email.trim().toLowerCase();

        if (emailNormalizado.length() > LONGITUD_MAXIMA) {
            throw new IllegalArgumentException(
                    "El email no puede exceder " + LONGITUD_MAXIMA + " caracteres");
        }

        if (!PATRON_EMAIL.matcher(emailNormalizado).matches()) {
            throw new IllegalArgumentException(
                    "El email no tiene un formato válido: " + email);
        }

        return new Email(emailNormalizado);
    }

    private final String valor;

    /**
     * Constructor privado. Usar el método estático crear() para instanciar.
     * 
     * @param valor la dirección de email ya validada
     */
    private Email(String valor) {
        this.valor = valor;
    }

    /**
     * Obtiene el valor del email normalizado (en minúsculas).
     * 
     * @return la dirección de email
     */
    public String getValor() {
        return valor;
    }

    /**
     * Extrae el dominio del email (la parte después del @).
     * 
     * @return el dominio del email
     */
    public String obtenerDominio() {
        int posicionArroba = valor.indexOf('@');
        if (posicionArroba > 0) {
            return valor.substring(posicionArroba + 1);
        }
        return "";
    }

    /**
     * Extrae el nombre de usuario del email (la parte antes del @).
     * 
     * @return el nombre de usuario
     */
    public String obtenerNombreUsuario() {
        int posicionArroba = valor.indexOf('@');
        if (posicionArroba > 0) {
            return valor.substring(0, posicionArroba);
        }
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Email email = (Email) o;
        return Objects.equals(valor, email.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}

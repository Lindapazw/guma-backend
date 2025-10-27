package com.guma.application.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO genérico para envolver respuestas de la capa de aplicación.
 * Permite comunicar éxito/fracaso y transportar datos o errores.
 * 
 * @param <T> Tipo de dato que contiene el resultado
 * 
 *            Uso:
 *            - ResultadoDTO.exito(data) - Para operaciones exitosas
 *            - ResultadoDTO.error(mensaje) - Para errores simples
 *            - ResultadoDTO.error(errores) - Para múltiples errores
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class ResultadoDTO<T> {

    /**
     * Crea un resultado exitoso con dato.
     * 
     * @param <T>  Tipo del dato
     * @param dato Dato a transportar
     * @return ResultadoDTO exitoso
     */
    public static <T> ResultadoDTO<T> exito(T dato) {
        return new ResultadoDTO<>(true, dato, null);
    }

    /**
     * Alias de exito(T dato) para compatibilidad con frontend.
     * 
     * @param <T>  Tipo del dato
     * @param dato Dato a transportar
     * @return ResultadoDTO exitoso
     */
    public static <T> ResultadoDTO<T> exitoso(T dato) {
        return exito(dato);
    }

    /**
     * Crea un resultado exitoso sin dato.
     * 
     * @param <T> Tipo del dato
     * @return ResultadoDTO exitoso
     */
    public static <T> ResultadoDTO<T> exito() {
        return new ResultadoDTO<>(true, null, null);
    }

    /**
     * Alias de exito() para compatibilidad con frontend.
     * 
     * @param <T> Tipo del dato
     * @return ResultadoDTO exitoso
     */
    public static <T> ResultadoDTO<T> exitoso() {
        return exito();
    }

    /**
     * Crea un resultado de error con un solo mensaje.
     * 
     * @param <T>     Tipo del dato
     * @param mensaje Mensaje de error
     * @return ResultadoDTO con error
     */
    public static <T> ResultadoDTO<T> error(String mensaje) {
        List<ErrorDTO> errores = new ArrayList<>();
        errores.add(new ErrorDTO("GENERAL_ERROR", mensaje));
        return new ResultadoDTO<>(false, null, errores);
    }

    /**
     * Crea un resultado de error con un solo ErrorDTO.
     * 
     * @param <T>   Tipo del dato
     * @param error ErrorDTO con el error
     * @return ResultadoDTO con error
     */
    public static <T> ResultadoDTO<T> error(ErrorDTO error) {
        List<ErrorDTO> errores = new ArrayList<>();
        errores.add(error);
        return new ResultadoDTO<>(false, null, errores);
    }

    /**
     * Crea un resultado de error con múltiples errores.
     * 
     * @param <T>     Tipo del dato
     * @param errores Lista de errores
     * @return ResultadoDTO con errores
     */
    public static <T> ResultadoDTO<T> error(List<ErrorDTO> errores) {
        return new ResultadoDTO<>(false, null, errores);
    }

    private final boolean exito;

    private final T dato;

    private final List<ErrorDTO> errores;

    /**
     * Constructor privado. Usar métodos estáticos para crear instancias.
     */
    private ResultadoDTO(boolean exito, T dato, List<ErrorDTO> errores) {
        this.exito = exito;
        this.dato = dato;
        this.errores = errores != null ? errores : new ArrayList<>();
    }

    /**
     * Indica si la operación fue exitosa.
     * 
     * @return true si fue exitosa, false si hubo error
     */
    public boolean isExito() {
        return exito;
    }

    /**
     * Obtiene el dato del resultado.
     * 
     * @return Dato transportado (puede ser null)
     */
    public T getDato() {
        return dato;
    }

    /**
     * Obtiene la lista de errores.
     * 
     * @return Lista de errores (vacía si no hay errores)
     */
    public List<ErrorDTO> getErrores() {
        return new ArrayList<>(errores);
    }

    /**
     * Obtiene el primer error si existe.
     * 
     * @return Primer error o null si no hay errores
     */
    public ErrorDTO getPrimerError() {
        return errores.isEmpty() ? null : errores.get(0);
    }

    /**
     * Obtiene el mensaje del primer error.
     * 
     * @return Mensaje del primer error o cadena vacía
     */
    public String getMensajePrimerError() {
        ErrorDTO error = getPrimerError();
        return error != null ? error.getMensaje() : "";
    }

    @Override
    public String toString() {
        if (exito) {
            return "ResultadoDTO{exito=true, dato=" + dato + "}";
        } else {
            return "ResultadoDTO{exito=false, errores=" + errores + "}";
        }
    }
}

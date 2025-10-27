package com.guma.frontend.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DTO genérico para envolver respuestas
 * Contiene el resultado, datos y errores
 */
public class ResultadoFrontendDTO<T> {
    private boolean exito;
    private T data;
    private List<ErrorFrontendDTO> errores;

    // Constructor vacío
    public ResultadoFrontendDTO() {
        this.errores = new ArrayList<>();
    }

    // Constructor completo
    public ResultadoFrontendDTO(boolean exito, T data, List<ErrorFrontendDTO> errores) {
        this.exito = exito;
        this.data = data;
        this.errores = errores != null ? errores : new ArrayList<>();
    }

    // Métodos estáticos de conveniencia
    public static <T> ResultadoFrontendDTO<T> exitoso(T data) {
        return new ResultadoFrontendDTO<>(true, data, new ArrayList<>());
    }

    public static <T> ResultadoFrontendDTO<T> error(ErrorFrontendDTO... errores) {
        return new ResultadoFrontendDTO<>(false, null, Arrays.asList(errores));
    }

    public static <T> ResultadoFrontendDTO<T> error(List<ErrorFrontendDTO> errores) {
        return new ResultadoFrontendDTO<>(false, null, errores);
    }

    // Getters y Setters
    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<ErrorFrontendDTO> getErrores() {
        return errores;
    }

    public void setErrores(List<ErrorFrontendDTO> errores) {
        this.errores = errores;
    }
}

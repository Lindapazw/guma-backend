package com.guma.frontend.session;

import com.guma.frontend.dto.SesionFrontendDTO;
import com.guma.frontend.dto.UsuarioFrontendDTO;

/**
 * Singleton para mantener la sesión del usuario actual
 */
public class SesionActual {
    
    private static SesionActual instance;
    private SesionFrontendDTO sesion;
    
    private SesionActual() {
    }
    
    public static SesionActual getInstance() {
        if (instance == null) {
            instance = new SesionActual();
        }
        return instance;
    }
    
    public SesionFrontendDTO getSesion() {
        return sesion;
    }
    
    public void setSesion(SesionFrontendDTO sesion) {
        this.sesion = sesion;
    }
    
    public void limpiar() {
        this.sesion = null;
    }
    
    public boolean isAutenticado() {
        return sesion != null;
    }
    
    public Integer getUsuarioId() {
        return sesion != null ? sesion.getUsuarioId() : null;
    }
    
    public UsuarioFrontendDTO getUsuario() {
        return sesion != null ? sesion.getUsuario() : null;
    }
    
    public void cerrarSesion() {
        this.sesion = null;
    }
    
    public String getNombreCompleto() {
        if (sesion == null || sesion.getUsuario() == null) {
            return "";
        }
        UsuarioFrontendDTO usuario = sesion.getUsuario();
        return usuario.getNombre() + " " + usuario.getApellido();
    }
    
    public String getRolNombre() {
        if (sesion == null || sesion.getUsuario() == null) {
            return "";
        }
        return sesion.getUsuario().getRolNombre();
    }
}

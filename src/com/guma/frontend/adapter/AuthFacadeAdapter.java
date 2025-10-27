package com.guma.frontend.adapter;

import com.guma.application.facade.AuthFacade;
import com.guma.application.facade.impl.AuthFacadeImpl;
import com.guma.frontend.dto.ErrorFrontendDTO;
import com.guma.frontend.dto.LoginFrontendDTO;
import com.guma.frontend.dto.RegistroFrontendDTO;
import com.guma.frontend.dto.ResultadoFrontendDTO;
import com.guma.frontend.dto.SesionFrontendDTO;
import com.guma.frontend.dto.UsuarioFrontendDTO;

/**
 * Adaptador que conecta el frontend con el backend real.
 * Convierte entre DTOs del frontend y DTOs del backend cuando sea necesario.
 * 
 * Patron: Adapter Pattern
 * - Frontend usa: com.guma.frontend.dto.*
 * - Backend usa: com.guma.application.dto.*
 * 
 * Este adapter maneja la conversión y delega al AuthFacadeImpl real.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class AuthFacadeAdapter {

    private final AuthFacade backendFacade;

    /**
     * Constructor que crea una instancia del facade real del backend.
     */
    public AuthFacadeAdapter() {
        this.backendFacade = new AuthFacadeImpl();
    }

    /**
     * Constructor para testing (permite inyectar un facade mock).
     */
    public AuthFacadeAdapter(AuthFacade backendFacade) {
        this.backendFacade = backendFacade;
    }

    /**
     * Registra un nuevo usuario.
     * Convierte DTOs del frontend al backend y viceversa.
     */
    public ResultadoFrontendDTO<UsuarioFrontendDTO> registrarUsuario(
            RegistroFrontendDTO dtoFrontend) {

        // 1. Convertir DTO del frontend al backend
        com.guma.application.dto.RegistroUsuarioDTO dtoBackend = convertirRegistroDTO(dtoFrontend);

        // 2. Llamar al backend
        com.guma.application.dto.ResultadoDTO<com.guma.application.dto.UsuarioDTO> resultadoBackend = backendFacade
                .registrarUsuario(dtoBackend);

        // 3. Convertir resultado del backend al frontend
        return convertirResultadoUsuario(resultadoBackend);
    }

    /**
     * Inicia sesión con email y contraseña.
     */
    public ResultadoFrontendDTO<SesionFrontendDTO> iniciarSesion(
            LoginFrontendDTO dtoFrontend) {

        // 1. Convertir LoginDTO del frontend al backend
        com.guma.application.dto.LoginDTO dtoBackend = new com.guma.application.dto.LoginDTO(
                dtoFrontend.getEmail(),
                dtoFrontend.getPassword());

        // 2. Llamar al backend
        com.guma.application.dto.ResultadoDTO<com.guma.application.dto.SesionDTO> resultadoBackend = backendFacade
                .iniciarSesion(dtoBackend);

        // 3. Convertir resultado del backend al frontend
        return convertirResultadoSesion(resultadoBackend);
    }

    /**
     * Cierra la sesión del usuario (placeholder para futuras funcionalidades).
     */
    public void cerrarSesion(int usuarioId) {
        // En el futuro, esto podría actualizar última conexión
        System.out.println("Sesión cerrada para usuario: " + usuarioId);
    }

    // ==================== MÉTODOS DE CONVERSIÓN ====================

    /**
     * Convierte RegistroFrontendDTO del frontend al backend.
     */
    private com.guma.application.dto.RegistroUsuarioDTO convertirRegistroDTO(
            com.guma.frontend.dto.RegistroFrontendDTO dtoFrontend) {

        com.guma.application.dto.RegistroUsuarioDTO dtoBackend = new com.guma.application.dto.RegistroUsuarioDTO();

        dtoBackend.setEmail(dtoFrontend.getEmail());
        dtoBackend.setPassword(dtoFrontend.getPassword());
        dtoBackend.setConfirmarPassword(dtoFrontend.getPassword()); // Frontend ya validó que coincidan
        dtoBackend.setNombre(dtoFrontend.getNombre());
        dtoBackend.setApellido(dtoFrontend.getApellido());
        dtoBackend.setTelefono(dtoFrontend.getTelefono());
        dtoBackend.setFechaNacimiento(dtoFrontend.getFechaNacimiento());

        return dtoBackend;
    }

    /**
     * Convierte ResultadoFrontendDTO<UsuarioFrontendDTO> del backend al frontend.
     */
    private ResultadoFrontendDTO<UsuarioFrontendDTO> convertirResultadoUsuario(
            com.guma.application.dto.ResultadoDTO<com.guma.application.dto.UsuarioDTO> resultadoBackend) {

        if (resultadoBackend.isExito()) {
            // Éxito: convertir UsuarioFrontendDTO
            com.guma.application.dto.UsuarioDTO usuarioBackend = resultadoBackend.getDato();
            UsuarioFrontendDTO usuarioFrontend = convertirUsuarioDTO(usuarioBackend);
            return ResultadoFrontendDTO.exitoso(usuarioFrontend);
        } else {
            // Error: convertir errores
            ErrorFrontendDTO[] erroresFrontend = convertirErrores(resultadoBackend.getErrores());
            return ResultadoFrontendDTO.error(erroresFrontend);
        }
    }

    /**
     * Convierte ResultadoFrontendDTO<SesionFrontendDTO> del backend al frontend.
     */
    private ResultadoFrontendDTO<SesionFrontendDTO> convertirResultadoSesion(
            com.guma.application.dto.ResultadoDTO<com.guma.application.dto.SesionDTO> resultadoBackend) {

        if (resultadoBackend.isExito()) {
            // Éxito: convertir SesionFrontendDTO
            com.guma.application.dto.SesionDTO sesionBackend = resultadoBackend.getDato();
            SesionFrontendDTO sesionFrontend = convertirSesionDTO(sesionBackend);
            return ResultadoFrontendDTO.exitoso(sesionFrontend);
        } else {
            // Error: convertir errores
            ErrorFrontendDTO[] erroresFrontend = convertirErrores(resultadoBackend.getErrores());
            return ResultadoFrontendDTO.error(erroresFrontend);
        }
    }

    /**
     * Convierte UsuarioFrontendDTO del backend al frontend.
     */
    private UsuarioFrontendDTO convertirUsuarioDTO(
            com.guma.application.dto.UsuarioDTO usuarioBackend) {

        if (usuarioBackend == null) {
            return null;
        }

        return new UsuarioFrontendDTO(
                usuarioBackend.getIdUsuario(),
                usuarioBackend.getEmail(),
                usuarioBackend.getNombre(),
                usuarioBackend.getApellido(),
                usuarioBackend.getTelefono(),
                usuarioBackend.getNombreRol(),
                usuarioBackend.getEstado(),
                usuarioBackend.getUltimaConexion());
    }

    /**
     * Convierte SesionDTO del backend al frontend.
     */
    private SesionFrontendDTO convertirSesionDTO(
            com.guma.application.dto.SesionDTO sesionBackend) {

        if (sesionBackend == null) {
            return null;
        }

        SesionFrontendDTO sesionFrontend = new SesionFrontendDTO(
                sesionBackend.getUsuarioId(),
                sesionBackend.getEmail(),
                sesionBackend.getNombreCompleto(),
                sesionBackend.getRolNombre(),
                sesionBackend.getToken(),
                sesionBackend.getExpiracion());

        // Crear UsuarioFrontendDTO simplificado para la sesión del frontend
        UsuarioFrontendDTO usuarioFrontend = new UsuarioFrontendDTO(
                sesionBackend.getUsuarioId(),
                sesionBackend.getEmail(),
                sesionBackend.getNombre(),
                sesionBackend.getApellido(),
                sesionBackend.getTelefono(),
                sesionBackend.getRolNombre(),
                sesionBackend.getEstado(),
                sesionBackend.getUltimaConexion());

        sesionFrontend.setUsuario(usuarioFrontend);

        return sesionFrontend;
    }

    /**
     * Convierte lista de ErrorDTO del backend al frontend.
     */
    private ErrorFrontendDTO[] convertirErrores(
            java.util.List<com.guma.application.dto.ErrorDTO> erroresBackend) {

        if (erroresBackend == null || erroresBackend.isEmpty()) {
            return new ErrorFrontendDTO[0];
        }

        return erroresBackend.stream()
                .map(errorBackend -> new ErrorFrontendDTO(
                        errorBackend.getCodigo(),
                        errorBackend.getMensaje(),
                        errorBackend.getCampo()))
                .toArray(ErrorFrontendDTO[]::new);
    }
}

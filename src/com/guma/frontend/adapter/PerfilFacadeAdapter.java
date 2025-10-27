package com.guma.frontend.adapter;

import com.guma.application.dto.*;
import com.guma.application.facade.PerfilFacade;
import com.guma.application.facade.impl.PerfilFacadeImpl;
import com.guma.frontend.dto.*;

/**
 * Adaptador que conecta el frontend con el backend real para operaciones de perfil.
 * Convierte entre DTOs del frontend y DTOs del backend cuando sea necesario.
 * 
 * Patrón: Adapter Pattern
 * - Frontend usa: com.guma.frontend.dto.*
 * - Backend usa: com.guma.application.dto.*
 * 
 * Este adapter maneja la conversión y delega al PerfilFacadeImpl real.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class PerfilFacadeAdapter {
    
    private final PerfilFacade backendFacade;
    
    public PerfilFacadeAdapter() {
        this.backendFacade = new PerfilFacadeImpl();
    }
    
    /**
     * Actualiza un perfil de usuario.
     * Convierte DTOs del frontend al backend y viceversa.
     */
    public ResultadoFrontendDTO<PerfilUsuarioFrontendDTO> actualizarPerfil(
            PerfilUsuarioFrontendDTO dtoFrontend) {
        return actualizarPerfil(dtoFrontend, null, null);
    }
    
    /**
     * Actualiza un perfil de usuario con foto opcional.
     * Convierte DTOs del frontend al backend y viceversa.
     * 
     * @param dtoFrontend DTO del perfil con datos actualizados
     * @param fotoBytes   Bytes de la nueva foto (null si no se cambia)
     * @param nombreFoto  Nombre del archivo de foto
     */
    public ResultadoFrontendDTO<PerfilUsuarioFrontendDTO> actualizarPerfil(
            PerfilUsuarioFrontendDTO dtoFrontend, byte[] fotoBytes, String nombreFoto) {
        
        // 1. Convertir DTO del frontend al backend
        com.guma.application.dto.PerfilUsuarioDTO dtoBackend = convertirPerfilDTO(dtoFrontend);
        
        // 2. Llamar al backend con foto
        com.guma.application.dto.ResultadoDTO<com.guma.application.dto.PerfilUsuarioDTO> resultadoBackend = 
                backendFacade.actualizar(dtoBackend, fotoBytes, nombreFoto);
        
        // 3. Convertir resultado del backend al frontend
        return convertirResultadoPerfil(resultadoBackend);
    }
    
    /**
     * Obtiene el perfil de un usuario por su ID.
     */
    public ResultadoFrontendDTO<PerfilUsuarioFrontendDTO> obtenerPerfilPorUsuario(Integer idUsuario) {
        com.guma.application.dto.ResultadoDTO<com.guma.application.dto.PerfilUsuarioDTO> resultadoBackend = 
                backendFacade.obtenerPorUsuario(idUsuario);
        
        return convertirResultadoPerfil(resultadoBackend);
    }
    
    // === MÉTODOS PRIVADOS DE CONVERSIÓN ===
    
    private com.guma.application.dto.PerfilUsuarioDTO convertirPerfilDTO(
            PerfilUsuarioFrontendDTO dtoFrontend) {
        
        com.guma.application.dto.PerfilUsuarioDTO dtoBackend = 
                new com.guma.application.dto.PerfilUsuarioDTO();
        
        // IDs
        dtoBackend.setIdPerfilUsuario(dtoFrontend.getId() != null ? dtoFrontend.getId().intValue() : null);
        dtoBackend.setIdUsuario(dtoFrontend.getIdUsuario() != null ? dtoFrontend.getIdUsuario().intValue() : null);
        
        // Datos personales
        dtoBackend.setNombre(dtoFrontend.getNombre());
        dtoBackend.setApellido(dtoFrontend.getApellido());
        dtoBackend.setDni(dtoFrontend.getDni() != null ? String.valueOf(dtoFrontend.getDni()) : null);
        dtoBackend.setEmail(dtoFrontend.getEmail());
        dtoBackend.setFechaNacimiento(dtoFrontend.getFechaNacimiento());
        
        // IDs de relaciones
        dtoBackend.setIdSexo(dtoFrontend.getIdSexo());
        
        // Contacto y ubicación (opcionales)
        dtoBackend.setTelefono(dtoFrontend.getTelefono());
        dtoBackend.setIdDireccion(dtoFrontend.getIdDireccion());
        dtoBackend.setIdRedSocial(dtoFrontend.getIdRedSocial());
        
        // Foto de perfil (ID de la imagen) - solo si existe en frontend
        dtoBackend.setFotoPerfilId(dtoFrontend.getFotoPerfil());
        
        // Estado
        dtoBackend.setVerificado(dtoFrontend.isVerificado());
        
        return dtoBackend;
    }
    
    private ResultadoFrontendDTO<PerfilUsuarioFrontendDTO> convertirResultadoPerfil(
            com.guma.application.dto.ResultadoDTO<com.guma.application.dto.PerfilUsuarioDTO> resultadoBackend) {
        
        if (resultadoBackend.isExito()) {
            // Conversión exitosa
            com.guma.application.dto.PerfilUsuarioDTO perfilBackend = resultadoBackend.getDato();
            PerfilUsuarioFrontendDTO perfilFrontend = convertirPerfilDTOaFrontend(perfilBackend);
            
            return ResultadoFrontendDTO.exitoso(perfilFrontend);
            
        } else {
            // Conversión de errores
            ErrorFrontendDTO[] erroresFrontend = convertirErrores(
                resultadoBackend.getErrores().toArray(new com.guma.application.dto.ErrorDTO[0])
            );
            return ResultadoFrontendDTO.error(erroresFrontend);
        }
    }
    
    private PerfilUsuarioFrontendDTO convertirPerfilDTOaFrontend(
            com.guma.application.dto.PerfilUsuarioDTO dtoBackend) {
        
        PerfilUsuarioFrontendDTO dtoFrontend = new PerfilUsuarioFrontendDTO();
        
        // IDs
        dtoFrontend.setId(dtoBackend.getIdPerfilUsuario() != null ? dtoBackend.getIdPerfilUsuario().longValue() : null);
        dtoFrontend.setIdUsuario(dtoBackend.getIdUsuario() != null ? dtoBackend.getIdUsuario().longValue() : null);
        
        // Datos personales
        dtoFrontend.setNombre(dtoBackend.getNombre());
        dtoFrontend.setApellido(dtoBackend.getApellido());
        
        // DNI - convertir de String a Integer
        if (dtoBackend.getDni() != null && !dtoBackend.getDni().isEmpty()) {
            try {
                dtoFrontend.setDni(Integer.parseInt(dtoBackend.getDni()));
            } catch (NumberFormatException e) {
                // DNI inválido, dejar null
            }
        }
        
        dtoFrontend.setEmail(dtoBackend.getEmail());
        dtoFrontend.setFechaNacimiento(dtoBackend.getFechaNacimiento());
        
        // IDs de relaciones
        dtoFrontend.setIdSexo(dtoBackend.getIdSexo());
        dtoFrontend.setSexoDescripcion(dtoBackend.getNombreSexo());
        
        // Contacto y ubicación
        dtoFrontend.setTelefono(dtoBackend.getTelefono());
        dtoFrontend.setIdDireccion(dtoBackend.getIdDireccion());
        dtoFrontend.setIdRedSocial(dtoBackend.getIdRedSocial());
        
        // Foto de perfil
        dtoFrontend.setFotoPerfil(dtoBackend.getFotoPerfilId());
        dtoFrontend.setFotoPerfilUrl(dtoBackend.getFotoPerfilUrl());
        
        // Estado y última conexión
        dtoFrontend.setVerificado(dtoBackend.isVerificado());
        dtoFrontend.setUltimaConexion(dtoBackend.getUltimaConexion());
        
        return dtoFrontend;
    }
    
    private ErrorFrontendDTO[] convertirErrores(com.guma.application.dto.ErrorDTO[] erroresBackend) {
        if (erroresBackend == null || erroresBackend.length == 0) {
            return new ErrorFrontendDTO[0];
        }
        
        ErrorFrontendDTO[] erroresFrontend = new ErrorFrontendDTO[erroresBackend.length];
        
        for (int i = 0; i < erroresBackend.length; i++) {
            com.guma.application.dto.ErrorDTO errorBackend = erroresBackend[i];
            erroresFrontend[i] = new ErrorFrontendDTO(
                errorBackend.getCodigo(),
                errorBackend.getMensaje(),
                errorBackend.getCampo()
            );
        }
        
        return erroresFrontend;
    }
}

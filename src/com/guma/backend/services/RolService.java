package com.guma.backend.services;

import com.guma.domain.entities.*;
import com.guma.domain.valueobjects.*;
import com.guma.domain.exceptions.*;
import com.guma.backend.ports.*;

import java.util.Optional;

/**
 * Servicio que gestiona la lógica de negocio relacionada con roles.
 * 
 * Los roles son datos de catálogo que definen los permisos y accesos
 * de los usuarios en el sistema. Este servicio proporciona operaciones
 * de consulta sobre los roles disponibles.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class RolService {

    private final RolRepository rolRepository;

    /**
     * Constructor que inyecta la dependencia del repositorio.
     * 
     * @param rolRepository repositorio de roles
     */
    public RolService(RolRepository rolRepository) {
        if (rolRepository == null) {
            throw new IllegalArgumentException("El repositorio no puede ser nulo");
        }
        this.rolRepository = rolRepository;
    }

    /**
     * Obtiene el rol por defecto del sistema (Usuario - ID 3).
     * 
     * Este rol se asigna automáticamente a los nuevos usuarios
     * durante el proceso de registro.
     * 
     * @return el rol por defecto
     * @throws EntidadNoEncontradaException si no existe el rol por defecto
     */
    public Rol obtenerRolPorDefecto() {
        return rolRepository.getRolPorDefecto();
    }

    /**
     * Busca un rol por su nombre.
     * 
     * @param nombre el nombre del rol (Admin, Usuario, etc.)
     * @return Optional con el rol si existe
     */
    public Optional<Rol> obtenerRolPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del rol no puede ser nulo o vacío");
        }
        return rolRepository.findByNombre(nombre);
    }

    /**
     * Busca un rol por su ID.
     * 
     * @param idRol el ID del rol
     * @return Optional con el rol si existe
     */
    public Optional<Rol> obtenerRolPorId(Integer idRol) {
        if (idRol == null) {
            throw new IllegalArgumentException("El ID del rol no puede ser nulo");
        }
        return rolRepository.findById(idRol);
    }

    /**
     * Valida si un rol es de tipo administrador.
     * 
     * @param idRol el ID del rol a validar
     * @return true si es rol de administrador
     * @throws EntidadNoEncontradaException si el rol no existe
     */
    public boolean esRolAdmin(Integer idRol) {
        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new EntidadNoEncontradaException("Rol", idRol));
        return rol.esAdmin();
    }
}

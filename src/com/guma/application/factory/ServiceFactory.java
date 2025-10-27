package com.guma.application.factory;

import com.guma.backend.ports.CatalogoGeograficoRepository;
import com.guma.backend.ports.DireccionRepository;
import com.guma.backend.ports.FileStorage;
import com.guma.backend.ports.ImageRepository;
import com.guma.backend.ports.PerfilUsuarioRepository;
import com.guma.backend.ports.RedSocialRepository;
import com.guma.backend.ports.RolRepository;
import com.guma.backend.ports.UsuarioRepository;
import com.guma.backend.services.DireccionService;
import com.guma.backend.services.PerfilUsuarioService;
import com.guma.backend.services.RedSocialService;
import com.guma.backend.services.RolService;
import com.guma.backend.services.UsuarioService;
import com.guma.data.repositories.CatalogoGeograficoRepositoryJdbc;
import com.guma.data.repositories.DireccionRepositoryJdbc;
import com.guma.data.repositories.ImageRepositoryJdbc;
import com.guma.data.repositories.PerfilUsuarioRepositoryJdbc;
import com.guma.data.repositories.RedSocialRepositoryJdbc;
import com.guma.data.repositories.RolRepositoryJdbc;
import com.guma.data.repositories.UsuarioRepositoryJdbc;
import com.guma.data.storage.FileStorageConfig;

/**
 * Factory para crear instancias de servicios con sus dependencias.
 * Implementa el patrón Factory para gestionar la creación de objetos complejos.
 * 
 * Esta clase centraliza la creación de servicios con las implementaciones
 * correctas de repositorios (JDBC en este caso).
 * 
 * Uso:
 * - ServiceFactory.crearUsuarioService() - Crea servicio con repo JDBC
 * - ServiceFactory.crearRolService() - Crea servicio de roles
 * - ServiceFactory.crearPerfilUsuarioService() - Crea servicio de perfiles
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class ServiceFactory {

    // Instancias singleton de repositorios
    private static UsuarioRepository usuarioRepository;
    private static RolRepository rolRepository;
    private static PerfilUsuarioRepository perfilUsuarioRepository;
    private static ImageRepository imageRepository;
    private static FileStorage fileStorage;
    private static DireccionRepository direccionRepository;
    private static RedSocialRepository redSocialRepository;
    private static CatalogoGeograficoRepository catalogoGeograficoRepository;

    // Instancias singleton de servicios
    private static UsuarioService usuarioService;
    private static RolService rolService;
    private static PerfilUsuarioService perfilUsuarioService;
    private static DireccionService direccionService;
    private static RedSocialService redSocialService;

    /**
     * Crea o retorna la instancia singleton de UsuarioRepository.
     * 
     * @return Implementación JDBC de UsuarioRepository
     */
    public static UsuarioRepository getUsuarioRepository() {
        if (usuarioRepository == null) {
            usuarioRepository = new UsuarioRepositoryJdbc();
        }
        return usuarioRepository;
    }

    /**
     * Crea o retorna la instancia singleton de RolRepository.
     * 
     * @return Implementación JDBC de RolRepository
     */
    public static RolRepository getRolRepository() {
        if (rolRepository == null) {
            rolRepository = new RolRepositoryJdbc();
        }
        return rolRepository;
    }

    /**
     * Crea o retorna la instancia singleton de PerfilUsuarioRepository.
     * 
     * @return Implementación JDBC de PerfilUsuarioRepository
     */
    public static PerfilUsuarioRepository getPerfilUsuarioRepository() {
        if (perfilUsuarioRepository == null) {
            perfilUsuarioRepository = new PerfilUsuarioRepositoryJdbc();
        }
        return perfilUsuarioRepository;
    }

    /**
     * Crea o retorna la instancia singleton de ImageRepository.
     * 
     * @return Implementación JDBC de ImageRepository
     */
    public static ImageRepository getImageRepository() {
        if (imageRepository == null) {
            imageRepository = new ImageRepositoryJdbc();
        }
        return imageRepository;
    }

    /**
     * Crea o retorna la instancia singleton de FileStorage.
     * 
     * @return Implementación local de FileStorage
     */
    public static FileStorage getFileStorage() {
        if (fileStorage == null) {
            fileStorage = FileStorageConfig.getInstance();
        }
        return fileStorage;
    }

    /**
     * Crea o retorna la instancia singleton de UsuarioService.
     * 
     * @return Servicio de usuarios con repositorios inyectados
     */
    public static UsuarioService crearUsuarioService() {
        if (usuarioService == null) {
            usuarioService = new UsuarioService(
                    getUsuarioRepository(),
                    getRolRepository());
        }
        return usuarioService;
    }

    /**
     * Crea o retorna la instancia singleton de RolService.
     * 
     * @return Servicio de roles con repositorio inyectado
     */
    public static RolService crearRolService() {
        if (rolService == null) {
            rolService = new RolService(getRolRepository());
        }
        return rolService;
    }

    /**
     * Crea o retorna la instancia singleton de PerfilUsuarioService.
     * 
     * @return Servicio de perfiles con repositorios inyectados
     */
    public static PerfilUsuarioService crearPerfilUsuarioService() {
        if (perfilUsuarioService == null) {
            perfilUsuarioService = new PerfilUsuarioService(
                    getPerfilUsuarioRepository(),
                    getUsuarioRepository(),
                    getFileStorage(),
                    getImageRepository());
        }
        return perfilUsuarioService;
    }

    /**
     * Crea o retorna la instancia singleton de DireccionRepository.
     * 
     * @return Implementación JDBC de DireccionRepository
     */
    public static DireccionRepository getDireccionRepository() {
        if (direccionRepository == null) {
            direccionRepository = new DireccionRepositoryJdbc();
        }
        return direccionRepository;
    }

    /**
     * Crea o retorna la instancia singleton de RedSocialRepository.
     * 
     * @return Implementación JDBC de RedSocialRepository
     */
    public static RedSocialRepository getRedSocialRepository() {
        if (redSocialRepository == null) {
            redSocialRepository = new RedSocialRepositoryJdbc();
        }
        return redSocialRepository;
    }

    /**
     * Crea o retorna la instancia singleton de CatalogoGeograficoRepository.
     * 
     * @return Implementación JDBC de CatalogoGeograficoRepository
     */
    public static CatalogoGeograficoRepository getCatalogoGeograficoRepository() {
        if (catalogoGeograficoRepository == null) {
            catalogoGeograficoRepository = new CatalogoGeograficoRepositoryJdbc();
        }
        return catalogoGeograficoRepository;
    }

    /**
     * Crea o retorna la instancia singleton de DireccionService.
     * 
     * @return Servicio de direcciones con repositorios inyectados
     */
    public static DireccionService crearDireccionService() {
        if (direccionService == null) {
            direccionService = new DireccionService(
                    getDireccionRepository(),
                    getCatalogoGeograficoRepository());
        }
        return direccionService;
    }

    /**
     * Crea o retorna la instancia singleton de RedSocialService.
     * 
     * @return Servicio de redes sociales con repositorio inyectado
     */
    public static RedSocialService crearRedSocialService() {
        if (redSocialService == null) {
            redSocialService = new RedSocialService(getRedSocialRepository());
        }
        return redSocialService;
    }

    /**
     * Resetea todas las instancias singleton.
     * Útil para testing.
     */
    public static void reset() {
        usuarioRepository = null;
        rolRepository = null;
        perfilUsuarioRepository = null;
        imageRepository = null;
        fileStorage = null;
        direccionRepository = null;
        redSocialRepository = null;
        catalogoGeograficoRepository = null;
        usuarioService = null;
        rolService = null;
        perfilUsuarioService = null;
        direccionService = null;
        redSocialService = null;
    }

    /**
     * Constructor privado para evitar instanciación.
     */
    private ServiceFactory() {
        throw new IllegalStateException("Factory class");
    }
}

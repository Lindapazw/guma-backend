package com.guma.backend;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.guma.application.factory.ServiceFactory;
import com.guma.backend.ports.PerfilUsuarioRepository;
import com.guma.backend.ports.RolRepository;
import com.guma.backend.ports.UsuarioRepository;
import com.guma.backend.services.PerfilUsuarioService;
import com.guma.backend.services.RolService;
import com.guma.backend.services.UsuarioService;
import com.guma.domain.entities.PerfilUsuario;
import com.guma.domain.entities.Rol;
import com.guma.domain.entities.Usuario;
import com.guma.domain.exceptions.DniDuplicadoException;
import com.guma.domain.exceptions.EntidadNoEncontradaException;
import com.guma.domain.exceptions.PerfilDuplicadoException;
import com.guma.domain.exceptions.UsuarioDuplicadoException;
import com.guma.domain.valueobjects.Email;

/**
 * Clase de prueba para validar la capa backend.
 * 
 * Implementa mocks simples de los repositorios para probar
 * la lógica de negocio en los servicios sin necesidad de base de datos.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class PruebaBackend {

    /**
     * Mock simple de UsuarioRepository para pruebas.
     */
    static class MockUsuarioRepository implements UsuarioRepository {
        private Map<Integer, Usuario> usuarios = new HashMap<>();
        private int nextId = 1;

        @Override
        public Optional<Usuario> findByEmail(Email email) {
            return usuarios.values().stream()
                    .filter(u -> u.getEmail().equals(email))
                    .findFirst();
        }

        @Override
        public Optional<Usuario> findById(Integer idUsuario) {
            return Optional.ofNullable(usuarios.get(idUsuario));
        }

        @Override
        public Usuario save(Usuario usuario) {
            if (existsByEmail(usuario.getEmail())) {
                throw new UsuarioDuplicadoException(usuario.getEmail().getValor());
            }
            Usuario nuevoUsuario = new Usuario(
                    nextId++,
                    usuario.getEmail(),
                    usuario.getPassword(),
                    usuario.getIdRol(),
                    usuario.isVerified(),
                    usuario.getUltimaConexion());
            usuarios.put(nuevoUsuario.getIdUsuario(), nuevoUsuario);
            return nuevoUsuario;
        }

        @Override
        public boolean existsByEmail(Email email) {
            return findByEmail(email).isPresent();
        }

        @Override
        public Usuario update(Usuario usuario) {
            if (!usuarios.containsKey(usuario.getIdUsuario())) {
                throw new EntidadNoEncontradaException("Usuario", usuario.getIdUsuario());
            }
            usuarios.put(usuario.getIdUsuario(), usuario);
            return usuario;
        }

        @Override
        public Usuario save(Usuario usuario, java.sql.Connection conn) throws java.sql.SQLException {
            // Mock: simplemente delegar al método sin conexión
            return save(usuario);
        }

        @Override
        public Usuario update(Usuario usuario, java.sql.Connection conn) throws java.sql.SQLException {
            // Mock: simplemente delegar al método sin conexión
            return update(usuario);
        }
    }

    /**
     * Mock simple de RolRepository para pruebas.
     */
    static class MockRolRepository implements RolRepository {
        private Map<Integer, Rol> roles = new HashMap<>();

        public MockRolRepository() {
            roles.put(1, new Rol(1, "Admin"));
            roles.put(2, new Rol(2, "Moderador"));
            roles.put(3, new Rol(3, "Usuario"));
        }

        @Override
        public Optional<Rol> findById(Integer idRol) {
            return Optional.ofNullable(roles.get(idRol));
        }

        @Override
        public Optional<Rol> findByNombre(String nombre) {
            return roles.values().stream()
                    .filter(r -> r.getNombre().equals(nombre))
                    .findFirst();
        }

        @Override
        public Rol getRolPorDefecto() {
            return roles.get(3); // Usuario
        }
    }

    /**
     * Mock simple de PerfilUsuarioRepository para pruebas.
     */
    static class MockPerfilUsuarioRepository implements PerfilUsuarioRepository {
        private Map<Integer, PerfilUsuario> perfiles = new HashMap<>();
        private int nextId = 1;

        @Override
        public Optional<PerfilUsuario> findById(Integer idPerfilUsuario) {
            return Optional.ofNullable(perfiles.get(idPerfilUsuario));
        }

        @Override
        public Optional<PerfilUsuario> findByUsuarioId(Integer idUsuario) {
            return perfiles.values().stream()
                    .filter(p -> p.getIdUsuario().equals(idUsuario))
                    .findFirst();
        }

        @Override
        public Optional<PerfilUsuario> findByDni(String dni) {
            return perfiles.values().stream()
                    .filter(p -> p.getDni().equals(dni))
                    .findFirst();
        }

        @Override
        public PerfilUsuario save(PerfilUsuario perfil) {
            if (existsByUsuarioId(perfil.getIdUsuario())) {
                throw new PerfilDuplicadoException(perfil.getIdUsuario());
            }
            if (existsByDni(perfil.getDni())) {
                throw new DniDuplicadoException(perfil.getDni());
            }

            PerfilUsuario nuevoPerfil = new PerfilUsuario(
                    perfil.getIdUsuario(),
                    perfil.getIdSexo(),
                    perfil.getDni(),
                    perfil.getNombre(),
                    perfil.getApellido(),
                    perfil.getFechaNacimiento(),
                    perfil.getEmail());
            nuevoPerfil.setIdPerfilUsuario(nextId++);
            perfiles.put(nuevoPerfil.getIdPerfilUsuario(), nuevoPerfil);
            return nuevoPerfil;
        }

        @Override
        public PerfilUsuario update(PerfilUsuario perfil) {
            if (!perfiles.containsKey(perfil.getIdPerfilUsuario())) {
                throw new EntidadNoEncontradaException("PerfilUsuario", perfil.getIdPerfilUsuario());
            }
            perfiles.put(perfil.getIdPerfilUsuario(), perfil);
            return perfil;
        }

        @Override
        public PerfilUsuario save(PerfilUsuario perfil, java.sql.Connection conn) throws java.sql.SQLException {
            // Mock: simplemente delegar al método sin conexión
            return save(perfil);
        }

        @Override
        public PerfilUsuario update(PerfilUsuario perfil, java.sql.Connection conn) throws java.sql.SQLException {
            // Mock: simplemente delegar al método sin conexión
            return update(perfil);
        }

        @Override
        public boolean existsByUsuarioId(Integer idUsuario) {
            return findByUsuarioId(idUsuario).isPresent();
        }

        @Override
        public boolean existsByDni(String dni) {
            return findByDni(dni).isPresent();
        }
    }

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("  GUMA - Pruebas Capa Backend");
        System.out.println("==========================================\n");

        try {
            // Crear mocks de repositorios
            MockUsuarioRepository mockUsuarioRepo = new MockUsuarioRepository();
            MockRolRepository mockRolRepo = new MockRolRepository();
            MockPerfilUsuarioRepository mockPerfilRepo = new MockPerfilUsuarioRepository();

            // Crear servicios
            RolService rolService = new RolService(mockRolRepo);
            UsuarioService usuarioService = new UsuarioService(mockUsuarioRepo, mockRolRepo);

            // PerfilUsuarioService ahora requiere FileStorage e ImageRepository
            // Usamos los reales por ahora ya que no hay mocks
            PerfilUsuarioService perfilService = new PerfilUsuarioService(
                    mockPerfilRepo,
                    mockUsuarioRepo,
                    ServiceFactory.getFileStorage(),
                    ServiceFactory.getImageRepository());

            // Pruebas
            probarRolService(rolService);
            probarUsuarioService(usuarioService);
            probarPerfilService(perfilService, usuarioService);

            System.out.println("\n==========================================");
            System.out.println("  ✓ Todas las pruebas pasaron exitosamente");
            System.out.println("==========================================");

        } catch (Exception e) {
            System.err.println("\n✗ Error en las pruebas: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    // ==================== MOCKS ====================

    private static void probarRolService(RolService rolService) {
        System.out.println("▶ Probando RolService...");

        // Obtener rol por defecto
        Rol rolDefecto = rolService.obtenerRolPorDefecto();
        assert rolDefecto.getIdRol() == 3 : "El rol por defecto debe ser ID 3";
        assert rolDefecto.getNombre().equals("Usuario") : "El rol por defecto debe ser 'Usuario'";
        System.out.println("  ✓ Rol por defecto obtenido correctamente");

        // Buscar rol por nombre
        Optional<Rol> rolAdmin = rolService.obtenerRolPorNombre("Admin");
        assert rolAdmin.isPresent() : "Debe existir el rol Admin";
        assert rolAdmin.get().esAdmin() : "El rol Admin debe identificarse como admin";
        System.out.println("  ✓ Búsqueda por nombre funciona correctamente");

        // Validar rol admin
        boolean esAdmin = rolService.esRolAdmin(1);
        assert esAdmin : "El rol ID 1 debe ser admin";
        System.out.println("  ✓ Validación de rol admin correcta");

        System.out.println("✓ RolService: OK\n");
    }

    private static void probarUsuarioService(UsuarioService usuarioService) {
        System.out.println("▶ Probando UsuarioService...");

        // Validar email disponible
        boolean disponible = usuarioService.validarEmailDisponible("nuevo@test.com");
        assert disponible : "El email nuevo@test.com debe estar disponible";
        System.out.println("  ✓ Validación de email disponible funciona");

        // Validar password válida
        boolean passwordValida = usuarioService.validarPassword("Password123");
        assert passwordValida : "Password123 debe ser válida";
        System.out.println("  ✓ Validación de password funciona");

        // Registrar usuario
        Usuario nuevoUsuario = usuarioService.registrarUsuario("nuevo@test.com", "Password123");
        assert nuevoUsuario.getIdUsuario() != null : "El usuario debe tener ID asignado";
        assert nuevoUsuario.getEmail().getValor().equals("nuevo@test.com") : "El email debe coincidir";
        assert nuevoUsuario.isVerified() : "El usuario nuevo debe estar verificado por defecto";
        System.out.println("  ✓ Registro de usuario exitoso");

        // Intentar registrar email duplicado
        try {
            usuarioService.registrarUsuario("nuevo@test.com", "Password123");
            throw new AssertionError("Debería lanzar UsuarioDuplicadoException");
        } catch (UsuarioDuplicadoException e) {
            System.out.println("  ✓ Validación de email duplicado funciona");
        }

        // Buscar usuario por email
        Optional<Usuario> usuarioEncontrado = usuarioService.buscarPorEmail("nuevo@test.com");
        assert usuarioEncontrado.isPresent() : "Debe encontrar el usuario registrado";
        System.out.println("  ✓ Búsqueda por email funciona");

        // Verificar email
        usuarioService.verificarEmail(nuevoUsuario.getIdUsuario());
        Usuario usuarioVerificado = usuarioService.buscarPorId(nuevoUsuario.getIdUsuario()).get();
        assert usuarioVerificado.isVerified() : "El usuario debe estar verificado";
        System.out.println("  ✓ Verificación de email funciona");

        // Iniciar sesión
        Usuario usuarioLogueado = usuarioService.iniciarSesion("nuevo@test.com", "Password123");
        assert usuarioLogueado.getUltimaConexion() != null : "Debe actualizar última conexión";
        System.out.println("  ✓ Inicio de sesión funciona");

        // Intentar login con password incorrecta
        try {
            usuarioService.iniciarSesion("nuevo@test.com", "WrongPassword123");
            throw new AssertionError("Debería lanzar EntidadNoEncontradaException");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("  ✓ Validación de password incorrecta funciona");
        }

        System.out.println("✓ UsuarioService: OK\n");
    }

    private static void probarPerfilService(PerfilUsuarioService perfilService, UsuarioService usuarioService) {
        System.out.println("▶ Probando PerfilUsuarioService...");

        // Crear usuario para el perfil
        Usuario usuario = usuarioService.registrarUsuario("conperfil@test.com", "Password123");

        // Crear perfil
        PerfilUsuario perfil = perfilService.crearPerfil(
                usuario.getIdUsuario(),
                1, // idSexo (M)
                "12345678",
                "Juan",
                "Pérez",
                LocalDate.of(1990, 1, 1),
                "conperfil@test.com");

        assert perfil.getIdPerfilUsuario() != null : "El perfil debe tener ID";
        assert perfil.getDni().equals("12345678") : "El DNI debe coincidir";
        assert !perfil.isVerificado() : "El perfil nuevo no debe estar verificado";
        System.out.println("  ✓ Creación de perfil exitosa");

        // Buscar perfil por usuario
        Optional<PerfilUsuario> perfilEncontrado = perfilService.buscarPorUsuarioId(usuario.getIdUsuario());
        assert perfilEncontrado.isPresent() : "Debe encontrar el perfil";
        System.out.println("  ✓ Búsqueda de perfil por usuario funciona");

        // Verificar perfil
        perfilService.verificarPerfil(perfil.getIdPerfilUsuario());
        PerfilUsuario perfilVerificado = perfilService.buscarPorUsuarioId(usuario.getIdUsuario()).get();
        assert perfilVerificado.isVerificado() : "El perfil debe estar verificado";
        System.out.println("  ✓ Verificación de perfil funciona");

        // Validar perfil completo
        boolean completo = perfilService.esPerfilCompleto(perfil.getIdPerfilUsuario());
        assert completo : "El perfil debe estar completo";
        System.out.println("  ✓ Validación de perfil completo funciona");

        // Intentar crear perfil duplicado
        try {
            perfilService.crearPerfil(
                    usuario.getIdUsuario(),
                    1,
                    "87654321",
                    "Pedro",
                    "González",
                    LocalDate.of(1995, 5, 5),
                    "otro@test.com");
            throw new AssertionError("Debería lanzar PerfilDuplicadoException");
        } catch (PerfilDuplicadoException e) {
            System.out.println("  ✓ Validación de perfil duplicado funciona");
        }

        System.out.println("✓ PerfilUsuarioService: OK\n");
    }
}

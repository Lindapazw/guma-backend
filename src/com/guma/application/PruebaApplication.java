package com.guma.application;

import java.sql.Connection;
import java.time.LocalDate;

import com.guma.application.dto.LoginDTO;
import com.guma.application.dto.PerfilUsuarioDTO;
import com.guma.application.dto.RegistroUsuarioDTO;
import com.guma.application.dto.ResultadoDTO;
import com.guma.application.dto.SesionDTO;
import com.guma.application.dto.UsuarioDTO;
import com.guma.application.facade.AuthFacade;
import com.guma.application.facade.PerfilFacade;
import com.guma.application.facade.impl.AuthFacadeImpl;
import com.guma.application.facade.impl.PerfilFacadeImpl;
import com.guma.data.config.DataSourceConfig;

/**
 * Pruebas de integración para la capa Application.
 * 
 * Estas pruebas validan el flujo completo:
 * Frontend (simulado) → Application → Backend → Data → MySQL
 * 
 * Se prueban:
 * - AuthFacade: registro, login, verificación, consultas
 * - PerfilFacade: crear perfil, actualizar, consultas, verificación
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class PruebaApplication {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        System.out.println(ANSI_BLUE + "==========================================" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "  GUMA - Pruebas Capa Application" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "==========================================" + ANSI_RESET);
        System.out.println();

        // Probar conexión a base de datos
        if (!probarConexion()) {
            System.err.println(ANSI_RED + "✗ No se puede conectar a MySQL" + ANSI_RESET);
            System.err.println(ANSI_YELLOW + "  Asegúrate de que el túnel SSH esté activo:" + ANSI_RESET);
            System.err.println(ANSI_YELLOW + "  ssh -L 3307:localhost:3306 lindapazw@192.168.0.55 -N" + ANSI_RESET);
            System.exit(1);
        }

        // Crear facades
        AuthFacade authFacade = new AuthFacadeImpl();
        PerfilFacade perfilFacade = new PerfilFacadeImpl();

        try {
            // Probar AuthFacade
            probarAuthFacade(authFacade);

            // Probar PerfilFacade
            probarPerfilFacade(authFacade, perfilFacade);

            System.out.println();
            System.out.println(ANSI_BLUE + "==========================================" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "  ✓ Todas las pruebas pasaron exitosamente" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "==========================================" + ANSI_RESET);

        } catch (Exception e) {
            System.err.println(ANSI_RED + "✗ Error en las pruebas: " + e.getMessage() + ANSI_RESET);
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static boolean probarConexion() {
        System.out.println(ANSI_BLUE + "▶ Probando conexión a MySQL..." + ANSI_RESET);
        try (Connection conn = DataSourceConfig.getConnection()) {
            System.out.println("  " + ANSI_GREEN + "✓ Conexión exitosa" + ANSI_RESET);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void probarAuthFacade(AuthFacade authFacade) {
        System.out.println(ANSI_BLUE + "▶ Probando AuthFacade..." + ANSI_RESET);

        // Generar email único
        String emailPrueba = "test_app_" + System.currentTimeMillis() + "@guma.com";
        String password = "Password123"; // Con mayúscula para cumplir reglas del backend

        // 1. Probar validación de registro (datos inválidos)
        System.out.println("  Probando validación de datos inválidos...");
        RegistroUsuarioDTO registroInvalido = new RegistroUsuarioDTO();
        registroInvalido.setEmail("email-invalido");
        registroInvalido.setPassword("123"); // Muy corta
        registroInvalido.setNombre("A"); // Muy corto
        registroInvalido.setApellido("B"); // Muy corto
        registroInvalido.setTelefono("123"); // Inválido

        ResultadoDTO<UsuarioDTO> resultadoInvalido = authFacade.registrarUsuario(registroInvalido);
        if (resultadoInvalido.isExito()) {
            throw new RuntimeException("Debería haber fallado con datos inválidos");
        }
        if (resultadoInvalido.getErrores().size() < 2) {
            throw new RuntimeException("Debería tener múltiples errores");
        }
        System.out.println("  " + ANSI_GREEN + "✓ Validación de datos inválidos funciona" + ANSI_RESET);

        // 2. Probar registro exitoso
        System.out.println("  Probando registro de usuario...");
        RegistroUsuarioDTO registro = new RegistroUsuarioDTO();
        registro.setEmail(emailPrueba); // Usar el mismo email que se usará para login
        registro.setPassword(password);
        registro.setConfirmarPassword(password); // Agregar confirmación
        registro.setNombre("Juan");
        registro.setApellido("Pérez");
        registro.setTelefono("+5491123456789");

        ResultadoDTO<UsuarioDTO> resultadoRegistro = authFacade.registrarUsuario(registro);
        if (!resultadoRegistro.isExito()) {
            throw new RuntimeException("Registro falló: " + resultadoRegistro.getMensajePrimerError());
        }

        UsuarioDTO usuarioRegistrado = resultadoRegistro.getDato();
        if (usuarioRegistrado.getIdUsuario() == null) {
            throw new RuntimeException("Usuario no tiene ID");
        }
        System.out.println(
                "  " + ANSI_GREEN + "✓ Usuario registrado: ID " + usuarioRegistrado.getIdUsuario() + ANSI_RESET);

        // 3. Probar registro duplicado
        System.out.println("  Probando registro duplicado...");
        ResultadoDTO<UsuarioDTO> resultadoDuplicado = authFacade.registrarUsuario(registro);
        if (resultadoDuplicado.isExito()) {
            throw new RuntimeException("Debería rechazar email duplicado");
        }
        System.out.println("  " + ANSI_GREEN + "✓ Registro duplicado rechazado correctamente" + ANSI_RESET);

        // 4. Probar login fallido
        System.out.println("  Probando login con contraseña incorrecta...");
        LoginDTO loginFallido = new LoginDTO(emailPrueba, "wrongpassword");
        ResultadoDTO<SesionDTO> resultadoLoginFallido = authFacade.iniciarSesion(loginFallido);
        if (resultadoLoginFallido.isExito()) {
            throw new RuntimeException("Debería rechazar contraseña incorrecta");
        }
        System.out.println("  " + ANSI_GREEN + "✓ Login con contraseña incorrecta rechazado" + ANSI_RESET);

        // 5. Probar login exitoso
        System.out.println("  Probando login exitoso...");
        LoginDTO loginDTO = new LoginDTO(emailPrueba, password);
        ResultadoDTO<SesionDTO> resultadoLogin = authFacade.iniciarSesion(loginDTO);
        if (!resultadoLogin.isExito()) {
            throw new RuntimeException("Login falló: " + resultadoLogin.getMensajePrimerError());
        }

        SesionDTO sesion = resultadoLogin.getDato();
        if (sesion.getUltimaConexion() == null) {
            throw new RuntimeException("Última conexión no actualizada");
        }
        System.out.println("  " + ANSI_GREEN + "✓ Login exitoso" + ANSI_RESET);

        // 6. Verificar email
        System.out.println("  Probando verificación de email...");
        ResultadoDTO<UsuarioDTO> resultadoVerificacion = authFacade.verificarEmail(usuarioRegistrado.getIdUsuario());
        if (!resultadoVerificacion.isExito()) {
            throw new RuntimeException("Verificación falló: " + resultadoVerificacion.getMensajePrimerError());
        }
        if (!resultadoVerificacion.getDato().isVerificado()) {
            throw new RuntimeException("Usuario debería estar verificado");
        }
        System.out.println("  " + ANSI_GREEN + "✓ Email verificado" + ANSI_RESET);

        // 7. Probar obtener usuario por ID
        System.out.println("  Probando obtener usuario por ID...");
        ResultadoDTO<UsuarioDTO> resultadoPorId = authFacade.obtenerUsuario(usuarioRegistrado.getIdUsuario());
        if (!resultadoPorId.isExito()) {
            throw new RuntimeException("No se pudo obtener usuario: " + resultadoPorId.getMensajePrimerError());
        }
        System.out.println("  " + ANSI_GREEN + "✓ Usuario obtenido por ID" + ANSI_RESET);

        // 8. Probar obtener usuario por email
        System.out.println("  Probando obtener usuario por email...");
        ResultadoDTO<UsuarioDTO> resultadoPorEmail = authFacade.obtenerUsuarioPorEmail(emailPrueba);
        if (!resultadoPorEmail.isExito()) {
            throw new RuntimeException("No se pudo obtener usuario: " + resultadoPorEmail.getMensajePrimerError());
        }
        System.out.println("  " + ANSI_GREEN + "✓ Usuario obtenido por email" + ANSI_RESET);

        System.out.println(ANSI_GREEN + "✓ AuthFacade: OK" + ANSI_RESET);
        System.out.println();
    }

    private static void probarPerfilFacade(AuthFacade authFacade, PerfilFacade perfilFacade) {
        System.out.println(ANSI_BLUE + "▶ Probando PerfilFacade..." + ANSI_RESET);

        // Crear un usuario para las pruebas de perfil
        String emailPrueba = "test_perfil_" + System.currentTimeMillis() + "@guma.com";
        RegistroUsuarioDTO registro = new RegistroUsuarioDTO();
        registro.setEmail(emailPrueba);
        registro.setPassword("Password123"); // Con mayúscula
        registro.setNombre("Juan");
        registro.setApellido("Pérez");
        registro.setTelefono("3511234567");
        registro.setConfirmarPassword("Password123");

        ResultadoDTO<UsuarioDTO> resultadoUsuario = authFacade.registrarUsuario(registro);
        if (!resultadoUsuario.isExito()) {
            throw new RuntimeException("No se pudo crear usuario para pruebas de perfil: " +
                    resultadoUsuario.getMensajePrimerError());
        }
        Integer idUsuario = resultadoUsuario.getDato().getIdUsuario();
        System.out.println("  Usuario creado para perfil: ID " + idUsuario);

        // Generar DNI único
        String dniPrueba = String.valueOf(System.currentTimeMillis()).substring(5);

        // 1. Probar validación de perfil (datos inválidos)
        System.out.println("  Probando validación de datos inválidos...");
        PerfilUsuarioDTO perfilInvalido = new PerfilUsuarioDTO();
        perfilInvalido.setIdUsuario(idUsuario);
        perfilInvalido.setDni("ABC"); // DNI inválido
        perfilInvalido.setNombre(""); // Vacío
        perfilInvalido.setFechaNacimiento(LocalDate.now().plusDays(1)); // Futura

        ResultadoDTO<PerfilUsuarioDTO> resultadoInvalido = perfilFacade.crear(perfilInvalido);
        if (resultadoInvalido.isExito()) {
            throw new RuntimeException("Debería haber fallado con datos inválidos");
        }
        if (resultadoInvalido.getErrores().size() < 3) {
            throw new RuntimeException("Debería tener múltiples errores");
        }
        System.out.println("  " + ANSI_GREEN + "✓ Validación de datos inválidos funciona" + ANSI_RESET);

        // 2. Obtener perfil existente (creado automáticamente durante el registro)
        System.out.println("  Obteniendo perfil existente...");
        ResultadoDTO<PerfilUsuarioDTO> resultadoPorUsuario = perfilFacade.obtenerPorUsuario(idUsuario);
        if (!resultadoPorUsuario.isExito()) {
            throw new RuntimeException("No se pudo obtener perfil: " + resultadoPorUsuario.getMensajePrimerError());
        }
        PerfilUsuarioDTO perfilCreado = resultadoPorUsuario.getDato();
        System.out
                .println("  " + ANSI_GREEN + "✓ Perfil obtenido: ID " + perfilCreado.getIdPerfilUsuario() + ANSI_RESET);

        // 3. Actualizar DNI del perfil
        System.out.println("  Actualizando DNI del perfil...");
        perfilCreado.setDni(dniPrueba);
        ResultadoDTO<PerfilUsuarioDTO> resultadoDniActualizado = perfilFacade.actualizar(perfilCreado);
        if (!resultadoDniActualizado.isExito()) {
            throw new RuntimeException("Actualización falló: " + resultadoDniActualizado.getMensajePrimerError());
        }
        System.out.println("  " + ANSI_GREEN + "✓ DNI actualizado correctamente" + ANSI_RESET);

        // 4. Probar obtener perfil por DNI
        System.out.println("  Probando obtener perfil por DNI...");
        ResultadoDTO<PerfilUsuarioDTO> resultadoPorDni = perfilFacade.obtenerPorDni(dniPrueba);
        if (!resultadoPorDni.isExito()) {
            throw new RuntimeException("No se pudo obtener perfil: " + resultadoPorDni.getMensajePrimerError());
        }
        System.out.println("  " + ANSI_GREEN + "✓ Perfil encontrado por DNI" + ANSI_RESET);

        // 6. Probar verificación de perfil
        System.out.println("  Probando verificación de perfil...");
        ResultadoDTO<PerfilUsuarioDTO> resultadoVerificar = perfilFacade.verificar(perfilCreado.getIdPerfilUsuario());
        if (!resultadoVerificar.isExito()) {
            throw new RuntimeException("Verificación falló: " + resultadoVerificar.getMensajePrimerError());
        }
        if (!resultadoVerificar.getDato().isVerificado()) {
            throw new RuntimeException("Perfil debería estar verificado");
        }
        System.out.println("  " + ANSI_GREEN + "✓ Perfil verificado" + ANSI_RESET);

        // 7. Probar actualización de perfil
        System.out.println("  Probando actualización de perfil...");
        PerfilUsuarioDTO perfilActualizado = resultadoVerificar.getDato();
        perfilActualizado.setTelefono("0987654321");
        perfilActualizado.setNombre("Juan Carlos");

        ResultadoDTO<PerfilUsuarioDTO> resultadoActualizar = perfilFacade.actualizar(perfilActualizado);
        if (!resultadoActualizar.isExito()) {
            throw new RuntimeException("Actualización falló: " + resultadoActualizar.getMensajePrimerError());
        }
        if (!"0987654321".equals(resultadoActualizar.getDato().getTelefono())) {
            throw new RuntimeException("Teléfono no se actualizó");
        }
        System.out.println("  " + ANSI_GREEN + "✓ Perfil actualizado" + ANSI_RESET);

        // 8. Verificar que el perfil está completo
        System.out.println("  Probando validación de completitud...");
        if (!resultadoActualizar.getDato().estaCompleto()) {
            throw new RuntimeException("Perfil debería estar completo");
        }
        System.out.println("  " + ANSI_GREEN + "✓ Perfil está completo" + ANSI_RESET);

        System.out.println(ANSI_GREEN + "✓ PerfilFacade: OK" + ANSI_RESET);
        System.out.println();
    }
}

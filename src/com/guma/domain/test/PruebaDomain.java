package com.guma.domain.test;

import java.time.LocalDate;

import com.guma.domain.entities.PerfilUsuario;
import com.guma.domain.entities.Rol;
import com.guma.domain.entities.Usuario;
import com.guma.domain.enums.EstadoUsuario;
import com.guma.domain.exceptions.BusinessException;
import com.guma.domain.exceptions.DniDuplicadoException;
import com.guma.domain.exceptions.EmailInvalidoException;
import com.guma.domain.exceptions.EntidadNoEncontradaException;
import com.guma.domain.exceptions.PasswordDebilException;
import com.guma.domain.exceptions.PerfilDuplicadoException;
import com.guma.domain.exceptions.UsuarioDuplicadoException;
import com.guma.domain.valueobjects.Email;
import com.guma.domain.valueobjects.Password;

/**
 * Clase de prueba para validar el funcionamiento de la capa domain.
 * 
 * Esta clase no usa frameworks de testing; solo ejecuta pruebas básicas
 * para verificar que las entidades, value objects y excepciones funcionan
 * correctamente.
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public class PruebaDomain {

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("   PRUEBAS DE LA CAPA DOMAIN - GUMA");
        System.out.println("═══════════════════════════════════════════════════════\n");

        try {
            probarValueObjects();
            probarEntidades();
            probarExcepciones();
            probarEnums();

            System.out.println("\n═══════════════════════════════════════════════════════");
            System.out.println("   ✅ TODAS LAS PRUEBAS PASARON EXITOSAMENTE");
            System.out.println("═══════════════════════════════════════════════════════");

        } catch (Exception e) {
            System.err.println("\n❌ ERROR EN LAS PRUEBAS:");
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void probarValueObjects() {
        System.out.println("📋 Probando Value Objects...");

        // Prueba Email
        Email email = Email.crear("usuario@ejemplo.com");
        assert email.getValor().equals("usuario@ejemplo.com");
        assert email.obtenerDominio().equals("ejemplo.com");
        assert email.obtenerNombreUsuario().equals("usuario");
        System.out.println("  ✓ Email creado: " + email);

        // Prueba Password
        Password password = Password.crear("MiPassword123");
        assert password.verificar("MiPassword123");
        assert !password.verificar("OtraPassword");
        System.out.println("  ✓ Password creado y verificado");

        // Prueba validaciones Email
        try {
            Email.crear("email-invalido");
            throw new AssertionError("Debería lanzar excepción para email inválido");
        } catch (IllegalArgumentException e) {
            System.out.println("  ✓ Validación de email funciona correctamente");
        }

        // Prueba validaciones Password
        try {
            Password.crear("corta");
            throw new AssertionError("Debería lanzar excepción para password corta");
        } catch (IllegalArgumentException e) {
            System.out.println("  ✓ Validación de password funciona correctamente");
        }

        System.out.println();
    }

    private static void probarEntidades() {
        System.out.println("📋 Probando Entidades...");

        // Prueba Rol
        Rol rol = new Rol(3, "Usuario");
        assert rol.getIdRol() == 3;
        assert rol.getNombre().equals("Usuario");
        assert rol.esUsuarioEstandar();
        assert !rol.esAdmin();
        System.out.println("  ✓ Rol creado: " + rol);

        // Prueba Usuario
        Email email = Email.crear("test@guma.com");
        Password password = Password.crear("TestPass123");
        Usuario usuario = new Usuario(email, password, 3);
        usuario.verificarEmail();
        assert usuario.estaVerificado();
        System.out.println("  ✓ Usuario creado: " + usuario);

        // Prueba PerfilUsuario
        PerfilUsuario perfil = new PerfilUsuario(
                1, // idUsuario
                1, // idSexo
                "12345678", // dni
                "Juan", // nombre
                "Pérez", // apellido
                LocalDate.of(1990, 5, 15), // fechaNacimiento
                "juan.perez@guma.com" // email
        );
        assert perfil.estaCompleto();
        assert perfil.getNombreCompleto().equals("Juan Pérez");
        System.out.println("  ✓ PerfilUsuario creado: " + perfil);

        System.out.println();
    }

    private static void probarExcepciones() {
        System.out.println("📋 Probando Excepciones...");

        // BusinessException
        BusinessException be = new BusinessException("TEST_ERROR", "Mensaje de prueba");
        assert be.getCodigoError().equals("TEST_ERROR");
        assert be.tieneCodigoError();
        System.out.println("  ✓ BusinessException: " + be.getMessage());

        // UsuarioDuplicadoException
        UsuarioDuplicadoException ude = new UsuarioDuplicadoException("test@test.com");
        assert ude.getCodigoError().equals("USUARIO_DUPLICADO");
        System.out.println("  ✓ UsuarioDuplicadoException: " + ude.getMessage());

        // EmailInvalidoException
        EmailInvalidoException eie = new EmailInvalidoException("email-malo");
        assert eie.getCodigoError().equals("EMAIL_INVALIDO");
        System.out.println("  ✓ EmailInvalidoException: " + eie.getMessage());

        // PasswordDebilException
        PasswordDebilException pde = new PasswordDebilException();
        assert pde.getCodigoError().equals("PASSWORD_DEBIL");
        System.out.println("  ✓ PasswordDebilException: " + pde.getMessage());

        // EntidadNoEncontradaException
        EntidadNoEncontradaException enee = new EntidadNoEncontradaException("Usuario", 123);
        assert enee.getCodigoError().equals("ENTIDAD_NO_ENCONTRADA");
        System.out.println("  ✓ EntidadNoEncontradaException: " + enee.getMessage());

        // PerfilDuplicadoException
        PerfilDuplicadoException pdup = new PerfilDuplicadoException(1);
        assert pdup.getCodigoError().equals("PERFIL_DUPLICADO");
        System.out.println("  ✓ PerfilDuplicadoException: " + pdup.getMessage());

        // DniDuplicadoException
        DniDuplicadoException dde = new DniDuplicadoException("12345678");
        assert dde.getCodigoError().equals("DNI_DUPLICADO");
        System.out.println("  ✓ DniDuplicadoException: " + dde.getMessage());

        System.out.println();
    }

    private static void probarEnums() {
        System.out.println("📋 Probando Enums...");

        // EstadoUsuario
        EstadoUsuario activo = EstadoUsuario.ACTIVO;
        assert activo.puedeAcceder();
        assert !activo.estaBloqueado();
        assert !activo.estaEliminado();
        System.out.println("  ✓ EstadoUsuario.ACTIVO: " + activo);

        EstadoUsuario bloqueado = EstadoUsuario.BLOQUEADO;
        assert !bloqueado.puedeAcceder();
        assert bloqueado.estaBloqueado();
        System.out.println("  ✓ EstadoUsuario.BLOQUEADO: " + bloqueado);

        EstadoUsuario eliminado = EstadoUsuario.ELIMINADO;
        assert eliminado.estaEliminado();
        System.out.println("  ✓ EstadoUsuario.ELIMINADO: " + eliminado);

        System.out.println();
    }
}

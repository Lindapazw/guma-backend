package com.guma.domain.transaction;

import java.sql.Connection;

/**
 * Interface funcional que representa una acción a ejecutar dentro de una
 * transacción.
 * 
 * Permite escribir operaciones transaccionales de forma declarativa usando
 * lambdas.
 * 
 * Ejemplo:
 * 
 * <pre>
 * Usuario resultado = unitOfWork.execute(conn -> {
 *     Usuario usuario = usuarioService.crear(..., conn);
 *     Perfil perfil = perfilService.crear(..., conn);
 *     return usuario;
 * });
 * </pre>
 * 
 * @param <T> el tipo de resultado que retorna la acción
 * @author GUMA Development Team
 * @version 1.0
 */
@FunctionalInterface
public interface UnitOfWorkAction<T> {

    /**
     * Ejecuta la acción transaccional.
     * 
     * @param connection la conexión con transacción activa
     * @return el resultado de la operación
     * @throws Exception cualquier excepción que ocurra durante la ejecución
     */
    T execute(Connection connection) throws Exception;
}

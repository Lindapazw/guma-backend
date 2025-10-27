package com.guma.domain.transaction;

/**
 * Abstracción para gestión de transacciones.
 * 
 * Define el contrato para ejecutar operaciones dentro de transacciones,
 * garantizando atomicidad (commit/rollback automático).
 * 
 * Este es un puerto en la arquitectura hexagonal:
 * - La interface vive en el dominio (independiente de tecnología)
 * - La implementación vive en la capa de datos (usa JDBC)
 * 
 * Uso típico:
 * 
 * <pre>
 * public class MiFacade {
 *     private final UnitOfWork unitOfWork;
 *     
 *     public Resultado operacionCompleja() {
 *         return unitOfWork.execute(conn -> {
 *             // Todas estas operaciones son atómicas
 *             Entidad1 e1 = service1.crear(..., conn);
 *             Entidad2 e2 = service2.crear(..., conn);
 *             return convertir(e1, e2);
 *         });
 *     }
 * }
 * </pre>
 * 
 * @author GUMA Development Team
 * @version 1.0
 */
public interface UnitOfWork {

    /**
     * Ejecuta una operación dentro de una transacción.
     * 
     * Garantiza:
     * - Se inicia una transacción (autoCommit = false)
     * - Si la acción completa sin excepciones → COMMIT
     * - Si la acción lanza una excepción → ROLLBACK
     * - La conexión siempre se cierra (finally)
     * 
     * @param <T>    el tipo de resultado que retorna la operación
     * @param action la operación a ejecutar
     * @return el resultado de la operación
     * @throws RuntimeException si ocurre algún error (envuelve la excepción
     *                          original)
     */
    <T> T execute(UnitOfWorkAction<T> action);

    /**
     * Inicia una transacción manualmente (para casos avanzados).
     * 
     * Nota: Preferir usar execute() en lugar de este método.
     */
    void begin();

    /**
     * Confirma la transacción actual.
     * 
     * Nota: Preferir usar execute() en lugar de este método.
     */
    void commit();

    /**
     * Revierte la transacción actual.
     * 
     * Nota: Preferir usar execute() en lugar de este método.
     */
    void rollback();
}

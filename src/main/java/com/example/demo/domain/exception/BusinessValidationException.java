package com.example.demo.domain.exception;

/**
 * <h2>BusinessValidationException</h2>
 *
 * <p>
 * Excepción de dominio utilizada para representar errores de validación
 * asociados a reglas de negocio dentro del sistema.
 * </p>
 *
 * <p>
 * En una arquitectura basada en <b>Domain-Driven Design (DDD)</b> y
 * <b>Arquitectura Hexagonal (Ports & Adapters)</b>, este tipo de excepción
 * pertenece al <b>núcleo del dominio</b> y se utiliza cuando una operación
 * solicitada por un caso de uso viola una regla de negocio definida por el
 * modelo de dominio.
 * </p>
 *
 * <h3>Propósito dentro de la arquitectura</h3>
 *
 * <p>
 * Esta excepción permite:
 * </p>
 *
 * <ul>
 *     <li>Expresar errores de validación del negocio de forma explícita.</li>
 *     <li>Diferenciar fallos de lógica de negocio frente a errores técnicos.</li>
 *     <li>Permitir que capas externas (API REST, adaptadores, handlers globales)
 *     traduzcan estas excepciones a respuestas HTTP apropiadas
 *     (por ejemplo <b>HTTP 400 - Bad Request</b>).</li>
 * </ul>
 *
 * <h3>Ejemplos de escenarios donde se utiliza</h3>
 *
 * <ul>
 *     <li>Intentar realizar una operación no permitida por las reglas del negocio.</li>
 *     <li>Duplicidad de operaciones que deben ser únicas.</li>
 *     <li>Estados inconsistentes dentro de una entidad del dominio.</li>
 *     <li>Intentar ejecutar una transición inválida dentro de un proceso.</li>
 * </ul>
 *
 * <h3>Contexto dentro del sistema de gestión de fondos</h3>
 *
 * <p>
 * En este proyecto la excepción puede ser utilizada, por ejemplo, cuando:
 * </p>
 *
 * <ul>
 *     <li>Un cliente intenta suscribirse dos veces al mismo fondo.</li>
 *     <li>Se intenta cancelar una suscripción que no existe.</li>
 *     <li>Se viola una política de negocio definida por el dominio.</li>
 * </ul>
 *
 * <h3>Consideraciones de diseño</h3>
 *
 * <ul>
 *     <li>Extiende de {@link RuntimeException} para evitar
 *     propagación obligatoria (checked exceptions) en el dominio.</li>
 *
 *     <li>Su uso debe limitarse a reglas del negocio, evitando
 *     utilizarla para errores de infraestructura o técnicos.</li>
 *
 *     <li>Permite que los controladores o manejadores globales
 *     de excepciones generen respuestas claras para los consumidores
 *     de la API.</li>
 * </ul>
 *
 * <h3>Ejemplo de uso</h3>
 *
 * <pre>
 * if (transactionRepository.hasActiveSubscription(clientId, fundId)) {
 *     throw new BusinessValidationException(
 *         "El cliente ya posee una suscripción activa a este fondo."
 *     );
 * }
 * </pre>
 *
 * <p>
 * En este escenario la excepción comunica claramente que la operación
 * solicitada viola una regla del negocio del sistema.
 * </p>
 *
 * @author Jaime Alberto Gutierrez
 * @since 1.0
 */
public class BusinessValidationException extends RuntimeException {

    /**
     * Constructor que permite crear una excepción de validación de negocio
     * con un mensaje descriptivo del error ocurrido.
     *
     * <p>
     * El mensaje debe explicar claramente la regla de negocio violada,
     * permitiendo que capas superiores del sistema puedan comunicar
     * adecuadamente el problema al usuario o sistema consumidor.
     * </p>
     *
     * @param message descripción clara de la regla de negocio que fue violada
     */
    public BusinessValidationException(String message) {
        super(message);
    }

} // Fin de la clase

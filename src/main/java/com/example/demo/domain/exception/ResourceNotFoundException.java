package com.example.demo.domain.exception;

/**
 * <h2>ResourceNotFoundException</h2>
 *
 * <p>
 * Excepción de dominio utilizada para indicar que un recurso solicitado
 * (entidad o registro) no se encuentra en el sistema.
 * </p>
 *
 * <p>
 * Forma parte del <b>núcleo del dominio</b> dentro de una arquitectura
 * basada en <b>Domain-Driven Design (DDD)</b> y
 * <b>Arquitectura Hexagonal (Ports & Adapters)</b>.
 * </p>
 *
 * <p>
 * Esta excepción permite diferenciar:
 * </p>
 *
 * <ul>
 *     <li>Errores relacionados con la existencia de recursos en el dominio</li>
 *     <li>Errores técnicos de infraestructura (como fallos de base de datos o red)</li>
 * </ul>
 *
 * <h3>Contexto de uso</h3>
 *
 * <p>
 * Se lanza típicamente cuando un servicio de aplicación o caso de uso
 * intenta acceder a un recurso que debería existir pero no se encuentra.
 * Por ejemplo:
 * </p>
 *
 * <pre>
 * Client client = clientRepository.findById(clientId)
 *       .orElseThrow(() -> new ResourceNotFoundException(
 *           "Cliente no encontrado con id: " + clientId));
 * </pre>
 *
 * <p>
 * Esto garantiza que la lógica de negocio no continúe con datos inexistentes,
 * protegiendo la integridad del sistema.
 * </p>
 *
 * <h3>Rol dentro de la arquitectura</h3>
 *
 * <ul>
 *     <li><b>Dominio:</b> indica que un recurso del modelo de dominio no existe.</li>
 *     <li><b>Application / Servicio:</b> permite detener la ejecución del caso de uso
 *     y comunicar la ausencia de datos necesarios.</li>
 *     <li><b>Adaptadores / API:</b> puede ser capturada para generar respuestas
 *     HTTP 404 o mensajes claros a los consumidores del servicio.</li>
 * </ul>
 *
 * <h3>Consideraciones de diseño</h3>
 *
 * <ul>
 *     <li>Extiende {@link RuntimeException} para evitar la propagación obligatoria
 *     de checked exceptions en la capa de dominio.</li>
 *     <li>Debe utilizarse únicamente para indicar ausencia de recursos
 *     dentro del dominio o contexto de negocio.</li>
 *     <li>No debe mezclarse con errores técnicos o de infraestructura.</li>
 * </ul>
 *
 * <h3>Beneficios</h3>
 *
 * <ul>
 *     <li>Proporciona claridad semántica sobre la inexistencia de recursos.</li>
 *     <li>Permite que los controladores de API traduzcan el error a respuestas HTTP 404.</li>
 *     <li>Facilita pruebas unitarias y revisiones de código.</li>
 * </ul>
 *
 * <p>
 * En conjunto con {@link BusinessValidationException} e
 * {@link InsufficientBalanceException}, completa el conjunto básico
 * de excepciones de dominio utilizadas para validar reglas de negocio
 * y consistencia de datos en los casos de uso financieros.
 * </p>
 *
 * @author Jaime Alberto Gutierrez
 * @since 1.0
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Construye una excepción indicando que el recurso solicitado no existe.
     *
     * @param message mensaje descriptivo que explica cuál recurso no fue encontrado
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

} // Fin de la clase

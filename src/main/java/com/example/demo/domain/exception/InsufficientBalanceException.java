package com.example.demo.domain.exception;

/**
 * <h2>InsufficientBalanceException</h2>
 *
 * <p>
 * Excepción de dominio utilizada para indicar que un cliente intenta ejecutar
 * una operación financiera sin disponer del saldo mínimo requerido.
 * </p>
 *
 * <p>
 * Esta excepción forma parte del <b>núcleo del dominio</b> dentro de una
 * arquitectura basada en <b>Domain-Driven Design (DDD)</b> y
 * <b>Arquitectura Hexagonal (Ports & Adapters)</b>.
 * </p>
 *
 * <p>
 * Su propósito es expresar explícitamente una violación de una
 * <b>regla de negocio financiera</b>, evitando que la lógica del dominio
 * dependa de detalles de infraestructura o de presentación.
 * </p>
 *
 * <h3>Regla de negocio que representa</h3>
 *
 * <p>
 * Para que un cliente pueda suscribirse a un fondo de inversión,
 * debe poseer un saldo disponible igual o superior al monto mínimo
 * de vinculación definido por el fondo.
 * </p>
 *
 * <p>
 * Cuando esta condición no se cumple, el sistema debe impedir la operación
 * y comunicar claramente el motivo del rechazo.
 * </p>
 *
 * <h3>Contexto dentro del sistema de gestión de fondos</h3>
 *
 * <p>
 * Esta excepción se lanza típicamente durante el proceso de suscripción
 * a un fondo cuando se evalúa la siguiente condición:
 * </p>
 *
 * <pre>
 * if (client.getBalance().compareTo(fund.getMinAmount()) < 0) {
 *     throw new InsufficientBalanceException(fund.getName());
 * }
 * </pre>
 *
 * <p>
 * En este escenario se evita:
 * </p>
 *
 * <ul>
 *     <li>la generación de transacciones inválidas</li>
 *     <li>la alteración incorrecta del saldo del cliente</li>
 *     <li>inconsistencias financieras en el sistema</li>
 * </ul>
 *
 * <h3>Rol dentro de la arquitectura</h3>
 *
 * <ul>
 *     <li><b>Dominio:</b> define una condición de error asociada a reglas financieras.</li>
 *     <li><b>Aplicación:</b> es utilizada por los servicios de aplicación
 *     que implementan los casos de uso.</li>
 *     <li><b>Infraestructura / API:</b> puede ser capturada por manejadores
 *     globales de excepciones para traducirse a una respuesta HTTP apropiada
 *     (por ejemplo <b>HTTP 400</b> o <b>HTTP 422</b>).</li>
 * </ul>
 *
 * <h3>Consideraciones de diseño</h3>
 *
 * <ul>
 *     <li>Extiende {@link RuntimeException} para evitar la propagación obligatoria
 *     de excepciones checked en la capa de dominio.</li>
 *
 *     <li>Su uso debe limitarse a errores de lógica de negocio relacionados
 *     con disponibilidad de saldo.</li>
 *
 *     <li>No debe utilizarse para errores técnicos de base de datos,
 *     red o infraestructura.</li>
 * </ul>
 *
 * <h3>Beneficios de esta aproximación</h3>
 *
 * <ul>
 *     <li>Expresa claramente el lenguaje ubicuo del dominio financiero.</li>
 *     <li>Permite desacoplar reglas de negocio de la capa de presentación.</li>
 *     <li>Facilita pruebas unitarias del dominio.</li>
 * </ul>
 *
 * @author Jaime Alberto Gutierrez
 * @since 1.0
 */
public class InsufficientBalanceException extends RuntimeException {

    /**
     * Construye una excepción que indica que el cliente no dispone
     * del saldo suficiente para vincularse al fondo especificado.
     *
     * <p>
     * El nombre del fondo se incluye en el mensaje con el objetivo
     * de proporcionar un contexto claro sobre la operación que falló.
     * </p>
     *
     * @param fundName nombre del fondo al cual el cliente intentó suscribirse
     */
    public InsufficientBalanceException(String fundName) {
        super("No tiene saldo disponible para vincularse al fondo " + fundName);
    }

} // Fin de la clase

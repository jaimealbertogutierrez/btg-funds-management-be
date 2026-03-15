package com.example.demo.domain.ports.out;

import com.example.demo.domain.model.Client;
import com.example.demo.domain.model.Fund;

/**
 * <h2>Puerto de salida (Outbound Port): NotificationPort</h2>
 *
 * <p>
 * Define la interfaz para la comunicación de notificaciones relacionadas con
 * transacciones de fondos. Forma parte del núcleo de dominio en arquitectura hexagonal
 * y permite que la lógica de negocio envíe alertas sin depender de la tecnología
 * o medio de notificación específico (correo, SMS, push, etc.).
 * </p>
 *
 * <h3>Responsabilidad</h3>
 * <ul>
 *     <li>Enviar notificaciones al cliente sobre suscripciones y cancelaciones de fondos.</li>
 *     <li>Desacoplar la capa de dominio de la infraestructura de comunicación.</li>
 *     <li>Garantizar que la capa de aplicación pueda ejecutar acciones de notificación
 *         sin depender de implementaciones concretas.</li>
 * </ul>
 */
public interface NotificationPort {

    /**
     * Notifica al cliente acerca de una suscripción a un fondo específico.
     *
     * <p>
     * La implementación concreta puede ser síncrona o asíncrona, y puede utilizar
     * distintos canales de comunicación (correo electrónico, SMS, push notification, etc.).
     * Este método garantiza que la lógica de negocio pueda informar al cliente
     * sin acoplarse a la infraestructura de notificación.
     * </p>
     *
     * @param client Cliente que realiza la suscripción
     * @param fund Fondo al que se ha suscrito el cliente
     */
    void notifySubscription(Client client, Fund fund);
}

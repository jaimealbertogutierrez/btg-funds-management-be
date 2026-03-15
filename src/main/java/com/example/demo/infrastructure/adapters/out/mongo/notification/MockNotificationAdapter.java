package com.example.demo.infrastructure.adapters.out.mongo.notification;

import com.example.demo.domain.model.Client;
import com.example.demo.domain.model.Fund;
import com.example.demo.domain.ports.out.NotificationPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <h2>MockNotificationAdapter</h2>
 *
 * <p>
 * Implementación simulada del puerto de notificaciones {@link NotificationPort}.
 * Esta clase representa un adaptador de salida (outbound adapter) en la arquitectura hexagonal,
 * responsable de enviar notificaciones a los clientes cuando interactúan con los fondos.
 * </p>
 *
 * <h3>Propósito y Estrategia:</h3>
 * <ul>
 *     <li>Sirve como ejemplo de integración con sistemas de notificación externos.</li>
 *     <li>En entornos de desarrollo y prueba, evita la dependencia de servicios reales de correo o SMS.</li>
 *     <li>Permite el registro (logging) de los mensajes que se "enviarían", facilitando la verificación en pruebas unitarias y de integración.</li>
 *     <li>Cumple con el principio de la arquitectura hexagonal: la lógica de negocio nunca interactúa directamente con infraestructuras externas.</li>
 * </ul>
 *
 * <h3>Notas de diseño:</h3>
 * <ul>
 *     <li>Usa SLF4J para registro de mensajes de notificación.</li>
 *     <li>Detecta la preferencia de notificación del cliente y registra el mensaje correspondiente:
 *         <ul>
 *             <li>EMAIL → Simula envío de correo electrónico.</li>
 *             <li>SMS → Simula envío de mensaje de texto.</li>
 *             <li>Otros → Registra notificación genérica.</li>
 *         </ul>
 *     </li>
 *     <li>La clase está anotada con {@link Component} para que Spring la detecte automáticamente como bean y pueda inyectarse en el servicio de dominio.</li>
 * </ul>
 *
 * <p>
 * Esta implementación garantiza que los servicios de dominio puedan notificar eventos sin depender
 * directamente de infraestructuras externas reales, cumpliendo las buenas prácticas de aislamiento
 * y testabilidad en la arquitectura hexagonal.
 * </p>
 *
 * <p>
 * Desarrollado por: Jaime Alberto Gutiérrez
 * </p>
 */
@Component
public class MockNotificationAdapter implements NotificationPort {

    private static final Logger log = LoggerFactory.getLogger(MockNotificationAdapter.class);

    /**
     * Envía una notificación simulada al cliente cuando se suscribe a un fondo.
     *
     * @param client el cliente que realiza la suscripción
     * @param fund   el fondo al que se suscribe el cliente
     */
    @Override
    public void notifySubscription(Client client, Fund fund) {
        String message = String.format(
                "¡Hola! Te has suscrito exitosamente al fondo %s por un valor de %s.",
                fund.getName(), fund.getMinAmount()
        );

        // Envía la notificación según la preferencia del cliente
        if ("EMAIL".equalsIgnoreCase(client.getNotificationPreference())) {
            log.info("📧 Enviando EMAIL al cliente {}: {}", client.getId(), message);
        } else if ("SMS".equalsIgnoreCase(client.getNotificationPreference())) {
            log.info("📱 Enviando SMS al cliente {}: {}", client.getId(), message);
        } else {
            log.info("🔔 Notificación generada para cliente {}: {}", client.getId(), message);
        }
    }

}  // Fin de la clase del mock de notificación

package com.example.demo.domain.ports.out;

import com.example.demo.domain.model.Client;
import java.util.Optional;

/**
 * <h2>Puerto de salida (Outbound Port): ClientRepositoryPort</h2>
 *
 * <p>
 * Define la interfaz de acceso a los datos de los clientes desde la capa de dominio.
 * Forma parte del núcleo del dominio en arquitectura hexagonal y permite que
 * la lógica de negocio interactúe con la persistencia sin acoplarse a una tecnología específica.
 * </p>
 *
 * <h3>Responsabilidad</h3>
 * <ul>
 *     <li>Permitir obtener información de un cliente de forma segura.</li>
 *     <li>Persistir cambios en el cliente, como saldo actualizado o nuevas transacciones.</li>
 *     <li>Desacoplar la capa de dominio de detalles de implementación de la base de datos.</li>
 * </ul>
 */
public interface ClientRepositoryPort {

    /**
     * Busca un cliente por su identificador único.
     *
     * <p>
     * Este método devuelve un Optional para manejar de forma segura el caso
     * en que el cliente no exista. La lógica de negocio debe manejar la ausencia
     * de cliente y lanzar la excepción correspondiente si es necesario.
     * </p>
     *
     * @param id Identificador único del cliente
     * @return Optional<Client> con el cliente si existe, o vacío si no se encuentra
     */
    Optional<Client> findById(String id);

    /**
     * Persiste un cliente en el repositorio.
     *
     * <p>
     * Se utiliza para guardar un cliente nuevo o actualizar un cliente existente,
     * incluyendo cambios en el saldo o en las transacciones. Garantiza que la
     * capa de dominio pueda mantener la consistencia de los datos sin conocer
     * la tecnología de persistencia subyacente.
     * </p>
     *
     * @param client Cliente a guardar o actualizar
     * @return Cliente persistido con los cambios aplicados
     */
    Client save(Client client);
}

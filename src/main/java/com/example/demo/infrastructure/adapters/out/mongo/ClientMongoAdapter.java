package com.example.demo.infrastructure.adapters.out.mongo;

import com.example.demo.domain.model.Client;
import com.example.demo.domain.ports.out.ClientRepositoryPort;
import com.example.demo.infrastructure.adapters.out.mongo.entity.ClientEntity;
import com.example.demo.infrastructure.adapters.out.mongo.mapper.MongoMappers;
import com.example.demo.infrastructure.adapters.out.mongo.repository.SpringDataClientRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h2>ClientMongoAdapter</h2>
 *
 * <p>
 * Adaptador de salida (outbound adapter) que implementa {@link ClientRepositoryPort}
 * para persistencia de la entidad {@link Client} en MongoDB.
 * Permite que la capa de dominio permanezca desacoplada de la tecnología específica de almacenamiento.
 * </p>
 *
 * <h3>Responsabilidades principales:</h3>
 * <ul>
 *     <li>Proporcionar operaciones de lectura y escritura sobre la colección "clients" de MongoDB.</li>
 *     <li>Transformar entre modelos de dominio ({@link Client}) y entidades de persistencia ({@link ClientEntity})
 *         utilizando los {@link MongoMappers} para mantener consistencia y lógica de negocio intacta.</li>
 *     <li>Registrar eventos y diagnósticos usando {@link Logger} para auditoría y depuración sin exponer la lógica interna del dominio.</li>
 * </ul>
 *
 * <h3>Notas de diseño:</h3>
 * <ul>
 *     <li>El método {@link #findById(String)} incluye logging de diagnóstico, permitiendo observar todas las entidades
 *         presentes en MongoDB antes de filtrar por ID.</li>
 *     <li>El uso de mappers centralizados garantiza que los cambios en la estructura de la base de datos no rompan la capa de dominio.</li>
 *     <li>Se sigue el principio de inversión de dependencias: el dominio depende de {@link ClientRepositoryPort},
 *         no de MongoDB directamente, facilitando pruebas unitarias y mocks.</li>
 *     <li>La clase está anotada con {@link Component}, permitiendo inyección automática por Spring y desacoplamiento
 *         del repositorio subyacente.</li>
 * </ul>
 *
 * <p>
 * Desarrollado por: Jaime Alberto Gutiérrez
 * </p>
 */
@Component
public class ClientMongoAdapter implements ClientRepositoryPort {

    // Logger profesional para seguimiento de eventos y diagnóstico
    private static final Logger log = LoggerFactory.getLogger(ClientMongoAdapter.class);

    private final SpringDataClientRepository repository;

    /**
     * Constructor de inyección de dependencias.
     *
     * @param repository Repositorio Spring Data que maneja la colección "clients" en MongoDB.
     */
    public ClientMongoAdapter(SpringDataClientRepository repository) {
        this.repository = repository;
    }

    /**
     * Busca un cliente por su identificador.
     *
     * <p>
     * Antes de realizar la búsqueda específica, se listan todas las entidades de la colección para
     * diagnóstico (nivel INFO), útil en depuración y auditoría de datos.
     * Luego se transforma la entidad de persistencia a modelo de dominio mediante {@link MongoMappers}.
     * </p>
     *
     * @param id Identificador del cliente.
     * @return Optional con {@link Client} si se encuentra, vacío si no.
     */
    public Optional<Client> findById(String id) {
        repository.findAll().forEach(entity ->
                log.info("Entidad detectada en MongoDB -> ID: [{}] | Nombre: {}", entity.getId(), entity.getName())
        );

        return repository.findById(id)
                .map(entity -> {
                    log.info("Cliente encontrado satisfactoriamente: [{}]", entity.getId());
                    return MongoMappers.clientToDomain(entity);
                });
    }

    /**
     * Guarda o actualiza un cliente en la base de datos.
     *
     * <p>
     * El objeto de dominio {@link Client} se convierte en {@link ClientEntity} antes de la persistencia.
     * Tras guardarlo, se vuelve a mapear a dominio para mantener consistencia con la capa de negocio.
     * </p>
     *
     * @param client Objeto de dominio a guardar.
     * @return Cliente persistido, mapeado a dominio.
     */
    @Override
    public Client save(Client client) {
        ClientEntity entity = MongoMappers.toEntity(client);
        ClientEntity saved = repository.save(entity);
        return MongoMappers.clientToDomain(saved);
    }
}

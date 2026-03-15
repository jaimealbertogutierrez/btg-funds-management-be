package com.example.demo.infrastructure.adapters.out.mongo;

import com.example.demo.domain.model.Fund;
import com.example.demo.domain.ports.out.FundRepositoryPort;
import com.example.demo.infrastructure.adapters.out.mongo.entity.FundEntity;
import com.example.demo.infrastructure.adapters.out.mongo.mapper.MongoMappers;
import com.example.demo.infrastructure.adapters.out.mongo.repository.SpringDataFundRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * <h2>FundMongoAdapter</h2>
 *
 * <p>
 * Adaptador de salida (outbound adapter) que implementa {@link FundRepositoryPort}
 * para persistencia de la entidad {@link Fund} en MongoDB.
 * Permite que la capa de dominio permanezca desacoplada de la tecnología de almacenamiento específica.
 * </p>
 *
 * <h3>Responsabilidades principales:</h3>
 * <ul>
 *     <li>Proveer operaciones de lectura sobre la colección "funds" en MongoDB.</li>
 *     <li>Transformar entre modelos de dominio ({@link Fund}) y entidades de persistencia ({@link FundEntity})
 *         usando {@link MongoMappers}, asegurando que la lógica de negocio no dependa de la base de datos.</li>
 *     <li>Seguir principios de inversión de dependencias: el dominio depende de {@link FundRepositoryPort},
 *         no de MongoDB directamente.</li>
 * </ul>
 *
 * <h3>Notas de diseño:</h3>
 * <ul>
 *     <li>El método {@link #findById(String)} mapea explícitamente la entidad de MongoDB a dominio con lambda para claridad.</li>
 *     <li>Este adapter es un ejemplo de cómo desacoplar el dominio de la infraestructura usando la arquitectura hexagonal.</li>
 *     <li>Clase anotada con {@link Component} para inyección automática de Spring, manteniendo el desacoplamiento y testabilidad.</li>
 * </ul>
 *
 * <p>
 * Desarrollado por: Jaime Alberto Gutiérrez
 * </p>
 */
@Component
public class FundMongoAdapter implements FundRepositoryPort {

    private final SpringDataFundRepository repository;

    /**
     * Constructor de inyección de dependencias.
     *
     * @param repository Repositorio Spring Data que maneja la colección "funds" en MongoDB.
     */
    public FundMongoAdapter(SpringDataFundRepository repository) {
        this.repository = repository;
    }

    /**
     * Busca un fondo por su identificador.
     *
     * <p>
     * Se utiliza {@link MongoMappers} para mapear la entidad de persistencia {@link FundEntity}
     * a un modelo de dominio {@link Fund}, manteniendo la consistencia del dominio y evitando acoplamientos.
     * </p>
     *
     * @param id Identificador del fondo.
     * @return Optional con {@link Fund} si se encuentra, vacío si no existe.
     */
    @Override
    public Optional<Fund> findById(String id) {
        return repository.findById(id)
                .map(entity -> MongoMappers.toDomain(entity));
    }
}

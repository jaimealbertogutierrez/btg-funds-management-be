package com.example.demo.infrastructure.adapters.out.mongo;

import com.example.demo.domain.model.Transaction;
import com.example.demo.domain.ports.out.TransactionRepositoryPort;
import com.example.demo.infrastructure.adapters.out.mongo.entity.TransactionEntity;
import com.example.demo.infrastructure.adapters.out.mongo.mapper.MongoMappers;
import com.example.demo.infrastructure.adapters.out.mongo.repository.SpringDataTransactionRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <h2>TransactionMongoAdapter</h2>
 *
 * <p>
 * Adaptador de salida que implementa {@link TransactionRepositoryPort} para persistencia de transacciones
 * en MongoDB. Este adapter desacopla el dominio de la infraestructura y gestiona
 * operaciones críticas sobre la colección "transactions".
 * </p>
 *
 * <h3>Responsabilidades principales:</h3>
 * <ul>
 *     <li>Guardar transacciones de suscripción o cancelación en MongoDB.</li>
 *     <li>Recuperar el historial de transacciones de un cliente.</li>
 *     <li>Determinar si un cliente tiene una suscripción activa a un fondo específico.</li>
 *     <li>Transformar entre la entidad de persistencia ({@link TransactionEntity}) y el modelo de dominio
 *         ({@link Transaction}) usando {@link MongoMappers}, asegurando que la lógica de negocio permanezca
 *         independiente de MongoDB.</li>
 * </ul>
 *
 * <h3>Notas de diseño:</h3>
 * <ul>
 *     <li>El método {@link #hasActiveSubscription(String, String)} implementa una regla de negocio:
 *         la suscripción está activa si la última transacción fue de tipo "SUBSCRIPTION".</li>
 *     <li>El método {@link #findByClientId(String)} devuelve una lista inmutable de transacciones
 *         usando la API de streams de Java 16+.</li>
 *     <li>Este adaptador sigue principios de inversión de dependencias: el dominio depende de
 *         {@link TransactionRepositoryPort}, no de MongoDB.</li>
 *     <li>Clase anotada con {@link Component} para inyección automática de Spring, manteniendo testabilidad
 *         y desacoplamiento.</li>
 * </ul>
 *
 * <p>Desarrollado por: Jaime Alberto Gutiérrez</p>
 */
@Component
public class TransactionMongoAdapter implements TransactionRepositoryPort {

    private final SpringDataTransactionRepository repository;

    /**
     * Constructor de inyección de dependencias.
     *
     * @param repository Repositorio Spring Data que maneja la colección "transactions".
     */
    public TransactionMongoAdapter(SpringDataTransactionRepository repository) {
        this.repository = repository;
    }

    /**
     * Guarda una transacción en la base de datos.
     *
     * <p>
     * Se transforma el modelo de dominio {@link Transaction} a la entidad de persistencia
     * {@link TransactionEntity} antes de guardarlo, y luego se mapea de nuevo al dominio.
     * </p>
     *
     * @param transaction Transacción a guardar (suscripción o cancelación).
     * @return La transacción guardada, con su ID generado por MongoDB.
     */
    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entityToSave = MongoMappers.toEntity(transaction);
        TransactionEntity savedEntity = repository.save(entityToSave);
        return MongoMappers.toDomain(savedEntity);
    }

    /**
     * Recupera todas las transacciones de un cliente específico.
     *
     * @param clientId Identificador del cliente.
     * @return Lista inmutable de {@link Transaction} del cliente.
     */
    @Override
    public List<Transaction> findByClientId(String clientId) {
        return repository.findByClientId(clientId).stream()
                .map(MongoMappers::toDomain)
                .toList();
    }

    /**
     * Determina si un cliente tiene una suscripción activa a un fondo.
     *
     * <p>
     * Se revisa la última transacción registrada para el cliente y el fondo:
     * si la última transacción fue de tipo "SUBSCRIPTION", la suscripción se considera activa.
     * </p>
     *
     * @param clientId Identificador del cliente.
     * @param fundId   Identificador del fondo.
     * @return true si existe una suscripción activa, false si no.
     */
    @Override
    public boolean hasActiveSubscription(String clientId, String fundId) {
        Optional<TransactionEntity> lastTransaction =
                repository.findTopByClientIdAndFundIdOrderByDateDesc(clientId, fundId);

        return lastTransaction.isPresent() && "SUBSCRIPTION".equals(lastTransaction.get().getType());
    }
}

package com.example.demo.infrastructure.adapters.out.mongo.repository;

import com.example.demo.infrastructure.adapters.out.mongo.entity.TransactionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <h2>SpringDataTransactionRepository</h2>
 *
 * <p>
 * Repositorio de persistencia para la entidad {@link TransactionEntity} usando Spring Data MongoDB.
 * Actúa como adaptador de salida (outbound adapter) en la arquitectura hexagonal,
 * conectando la capa de dominio con la base de datos MongoDB para operaciones relacionadas con transacciones.
 * </p>
 *
 * <h3>Propósito:</h3>
 * <ul>
 *     <li>Proporcionar operaciones CRUD automáticas sobre la colección "transactions" en MongoDB.</li>
 *     <li>Spring Data genera automáticamente implementaciones de métodos básicos como {@code save}, {@code findById}, {@code delete}.</li>
 *     <li>Permite consultas derivadas para obtener historial de transacciones por cliente y última transacción por fondo, manteniendo desacoplamiento de la lógica de dominio.</li>
 * </ul>
 *
 * <h3>Métodos personalizados:</h3>
 * <ul>
 *     <li>{@code List<TransactionEntity> findByClientId(String clientId)}: recupera todas las transacciones asociadas a un cliente específico.</li>
 *     <li>{@code Optional<TransactionEntity> findTopByClientIdAndFundIdOrderByDateDesc(String clientId, String fundId)}: obtiene la última transacción de un cliente para un fondo dado, útil para validar suscripciones activas.</li>
 * </ul>
 *
 * <h3>Notas de diseño:</h3>
 * <ul>
 *     <li>La anotación {@code @Repository} permite que Spring la detecte como un componente de persistencia.</li>
 *     <li>El tipo genérico <code>TransactionEntity</code> indica la entidad que maneja y <code>String</code> el tipo del ID.</li>
 *     <li>Se mantiene el principio hexagonal: el dominio interactúa únicamente a través de {@code TransactionRepositoryPort}, sin depender de MongoDB directamente.</li>
 *     <li>Esta estrategia facilita pruebas unitarias, inyección de mocks y cambios futuros de la base de datos sin afectar la lógica de negocio.</li>
 * </ul>
 *
 * <p>
 * Desarrollado por: Jaime Alberto Gutiérrez
 * </p>
 */
@Repository
public interface SpringDataTransactionRepository extends MongoRepository<TransactionEntity, String> {
    List<TransactionEntity> findByClientId(String clientId);
    Optional<TransactionEntity> findTopByClientIdAndFundIdOrderByDateDesc(String clientId, String fundId);
}

package com.example.demo.infrastructure.adapters.out.mongo.repository;

import com.example.demo.infrastructure.adapters.out.mongo.entity.ClientEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * <h2>SpringDataClientRepository</h2>
 *
 * <p>
 * Repositorio de persistencia para la entidad {@link ClientEntity} usando Spring Data MongoDB.
 * Este repositorio actúa como un adaptador de salida (outbound adapter) en la arquitectura hexagonal,
 * conectando la capa de dominio con la base de datos MongoDB.
 * </p>
 *
 * <h3>Propósito:</h3>
 * <ul>
 *     <li>Proporcionar operaciones CRUD automáticas sobre la colección "clients" en MongoDB.</li>
 *     <li>Spring Data genera automáticamente las implementaciones de métodos básicos (findById, save, delete, etc.).</li>
 *     <li>Este repositorio encapsula la interacción con MongoDB, manteniendo la lógica de dominio independiente de la persistencia.</li>
 * </ul>
 *
 * <h3>Notas de diseño:</h3>
 * <ul>
 *     <li>No se definen métodos adicionales por ahora, pero se pueden agregar consultas personalizadas mediante
 *     {@code @Query} o métodos derivados siguiendo la convención de nombres de Spring Data.</li>
 *     <li>El tipo genérico <code>ClientEntity</code> indica la entidad que maneja y <code>String</code> el tipo del ID.</li>
 *     <li>Permite que los servicios de dominio interactúen con los clientes a través de un puerto de repositorio,
 *     manteniendo el desacoplamiento de la capa de persistencia.</li>
 * </ul>
 *
 * <p>
 * Esta estrategia asegura que el dominio nunca acceda directamente a MongoDB, sino que lo haga a través de un puerto
 * definido, lo que facilita pruebas unitarias, mocks y cambios futuros de la base de datos sin afectar la lógica de negocio.
 * </p>
 *
 * <p>
 * Desarrollado por: Jaime Alberto Gutiérrez
 * </p>
 */
public interface SpringDataClientRepository extends MongoRepository<ClientEntity, String> {
}

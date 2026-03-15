package com.example.demo.infrastructure.adapters.out.mongo.mapper;

import com.example.demo.domain.model.Client;
import com.example.demo.domain.model.Fund;
import com.example.demo.domain.model.Transaction;
import com.example.demo.infrastructure.adapters.out.mongo.entity.*;
import java.util.ArrayList;
import java.util.Optional;

/**
 * <h2>MongoMappers</h2>
 *
 * <p>
 * Clase de utilidad responsable de mapear datos entre las <b>entidades MongoDB</b>
 * y los <b>modelos de dominio</b> de la aplicación. Forma parte fundamental del
 * patrón hexagonal, asegurando la separación de capas y evitando que la capa
 * de persistencia contamine la lógica de negocio.
 * </p>
 *
 * <h3>Importancia en Arquitectura Hexagonal:</h3>
 * <ul>
 *     <li>La API y los servicios de dominio trabajan sobre <b>modelos de dominio puros</b>.</li>
 *     <li>MongoDB requiere <b>entidades específicas de persistencia</b> que pueden tener
 *         anotaciones y estructuras particulares.</li>
 *     <li>Los mappers garantizan que las transformaciones entre la capa de dominio y
 *         la capa de infraestructura sean seguras y consistentes.</li>
 *     <li>Permiten <b>aislar la lógica de negocio</b> de detalles de almacenamiento
 *         y tecnologías específicas (MongoDB en este caso).</li>
 *     <li>Facilitan pruebas unitarias, mantenimiento y extensibilidad futura del código.</li>
 * </ul>
 *
 * <h3>Notas de diseño:</h3>
 * <ul>
 *     <li>Clase <b>utility</b>: constructor privado para evitar instanciación.</li>
 *     <li>Uso extensivo de {@link Optional} para manejar nulls de forma segura.</li>
 *     <li>Mantenemos la lógica mínima de seguridad y valores por defecto, especialmente
 *         para campos críticos como balance y preferencias de notificación.</li>
 *     <li>Los métodos son <b>estáticos</b>, reforzando el concepto de transformación pura
 *         sin estado.</li>
 * </ul>
 *
 * <h3>Resumen de mappers:</h3>
 * <ul>
 *     <li><b>Cliente:</b> {@link #clientToDomain(ClientEntity)} y {@link #toEntity(Client)}</li>
 *     <li><b>Fondo:</b> {@link #toDomain(FundEntity)} y {@link #toEntity(Fund)}</li>
 *     <li><b>Transacción:</b> {@link #toDomain(TransactionEntity)} y {@link #toEntity(Transaction)}</li>
 * </ul>
 *
 * <p>
 * Cada mapper asegura que los datos fluyan correctamente desde la <b>entrada API</b>,
 * a la <b>capa de dominio</b>, y finalmente al <b>almacenamiento MongoDB</b>,
 * cumpliendo con las mejores prácticas de limpieza de código, seguridad de datos
 * y consistencia.
 * </p>
 *
 * <p>
 * Desarrollado por: Jaime Alberto Gutiérrez
 * </p>
 */
public class MongoMappers {

    /**
     * Constructor privado para evitar instanciación de esta clase de utilidad.
     */
    private MongoMappers() {
        throw new IllegalStateException("Utility class - No debe ser instanciada");
    }

    // --- Mappers de Cliente ---
    public static Client clientToDomain(ClientEntity entity) {
        if (entity == null) return null;

        Client client = new Client();
        client.setId(entity.getId());
        client.setName(entity.getName());

        // Seguridad: balance por defecto si es null
        client.setBalance(Optional.ofNullable(entity.getBalance()).orElse(0.0));

        client.setTransactions(entity.getTransactions() != null ? entity.getTransactions() : new ArrayList<>());
        client.setNotificationPreference(entity.getNotificationPreference() != null ? entity.getNotificationPreference() : "EMAIL");

        return client;
    }

    public static ClientEntity toEntity(Client domain) {
        if (domain == null) return null;

        ClientEntity entity = new ClientEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        Optional.ofNullable(domain.getBalance()).ifPresent(entity::setBalance);
        entity.setTransactions(domain.getTransactions());
        entity.setNotificationPreference(domain.getNotificationPreference());

        return entity;
    }

    // --- Mappers de Fondo ---
    public static Fund toDomain(FundEntity entity) {
        if (entity == null) return null;
        return new Fund(entity.getId(), entity.getName(), entity.getMinAmount(), entity.getCategory());
    }

    public static FundEntity toEntity(Fund domain) {
        if (domain == null) return null;
        return new FundEntity(domain.getId(), domain.getName(), domain.getMinAmount(), domain.getCategory());
    }

    // --- Mappers de Transacción ---
    public static Transaction toDomain(TransactionEntity entity) {
        if (entity == null) return null;
        return new Transaction(
                entity.getId(),
                entity.getClientId(),
                entity.getFundId(),
                entity.getType(),
                entity.getAmount(),
                entity.getDate()
        );
    }

    public static TransactionEntity toEntity(Transaction domain) {
        if (domain == null) return null;
        return new TransactionEntity(
                domain.getId(),
                domain.getClientId(),
                domain.getFundId(),
                domain.getType(),
                domain.getAmount(),
                domain.getDate()
        );
    }

} // Fin de los mappers

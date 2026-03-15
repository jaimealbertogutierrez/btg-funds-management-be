package com.example.demo.infrastructure.adapters.out.mongo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * <h2>Entidad MongoDB: TransactionEntity</h2>
 *
 * <p>
 * Representa la colección <b>transactions</b> en MongoDB y mapea directamente los documentos
 * de transacciones asociadas a clientes y fondos.
 * Esta clase actúa como adaptador de salida (adapter out) de la arquitectura hexagonal,
 * conectando la persistencia con la capa de dominio.
 * </p>
 *
 * <h3>Buenas prácticas y notas clave:</h3>
 * <ul>
 *     <li>Se utiliza {@link Document} para indicar la colección MongoDB asociada.</li>
 *     <li>El campo {@link #id} está anotado con {@link Id} para mapear correctamente
 *         al identificador único de MongoDB (_id).</li>
 *     <li>Todos los campos reflejan información importante de la transacción y se mantienen simples,
 *         evitando lógica de negocio en esta capa.</li>
 *     <li>Para fechas se utiliza {@link LocalDateTime}, facilitando auditoría y ordenamiento temporal
 *         de transacciones.</li>
 *     <li>Se recomienda mapear esta entidad a la clase de dominio {@link com.example.demo.domain.model.Transaction}
 *         mediante mappers, manteniendo la separación de capas.</li>
 * </ul>
 *
 * <h3>Campos:</h3>
 * <ul>
 *     <li>{@link #id} – Identificador único de la transacción en MongoDB.</li>
 *     <li>{@link #clientId} – ID del cliente asociado a la transacción.</li>
 *     <li>{@link #fundId} – ID del fondo relacionado con la transacción.</li>
 *     <li>{@link #type} – Tipo de transacción: "SUBSCRIPTION" o "CANCELLATION".</li>
 *     <li>{@link #amount} – Monto involucrado en la transacción.</li>
 *     <li>{@link #date} – Fecha y hora en que se realizó la transacción.</li>
 * </ul>
 *
 * <h3>Recomendaciones de uso:</h3>
 * <ul>
 *     <li>No incluir lógica de negocio en esta entidad; solo contiene datos de persistencia.</li>
 *     <li>Usar mappers para convertir a la clase de dominio {@link com.example.demo.domain.model.Transaction} antes de aplicar reglas de negocio.</li>
 *     <li>Asegurarse de que MongoDB tenga índices adecuados en {@link #clientId}, {@link #fundId} y {@link #date}
 *         para optimizar consultas frecuentes como historial de transacciones.</li>
 * </ul>
 */
@Document(collection = "transactions")
public class TransactionEntity {

    /** Identificador único de la transacción en MongoDB */
    @Id
    private String id;

    /** ID del cliente asociado a la transacción */
    private String clientId;

    /** ID del fondo asociado a la transacción */
    private String fundId;

    /** Tipo de transacción: "SUBSCRIPTION" o "CANCELLATION" */
    private String type;

    /** Monto involucrado en la transacción */
    private Double amount;

    /** Fecha y hora de la transacción */
    private LocalDateTime date;

    public TransactionEntity() {
    }

    public TransactionEntity(String id, String clientId, String fundId, String type, Double amount, LocalDateTime date) {
        this.id = id;
        this.clientId = clientId;
        this.fundId = fundId;
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getFundId() { return fundId; }
    public void setFundId(String fundId) { this.fundId = fundId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
}

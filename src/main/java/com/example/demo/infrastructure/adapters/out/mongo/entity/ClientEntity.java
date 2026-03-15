package com.example.demo.infrastructure.adapters.out.mongo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * <h2>Entidad MongoDB: ClientEntity</h2>
 *
 * <p>
 * Representa la colección <b>clients</b> en MongoDB y mapea directamente los documentos
 * almacenados. Esta clase actúa como un adaptador de salida (adapter out) de la arquitectura
 * hexagonal, permitiendo que la capa de infraestructura se conecte con la capa de dominio.
 * </p>
 *
 * <h3>Buenas prácticas y notas clave:</h3>
 * <ul>
 *     <li>Se utiliza {@link Document} para indicar la colección de MongoDB asociada.</li>
 *     <li>El campo {@link #id} está anotado con {@link Id} y {@link Field("_id")} para mapear correctamente
 *         al identificador de MongoDB (_id). Esto evita confusiones entre id en Java y _id en Mongo.</li>
 *     <li>Se mantiene la consistencia de nombres con la capa de dominio (Client), facilitando conversiones
 *         a objetos de dominio mediante mappers.</li>
 *     <li>Campos opcionales como {@link #notificationPreference} pueden ser null al leer documentos antiguos,
 *         lo cual es aceptable y seguro.</li>
 *     <li>Listas como {@link #transactions} representan relaciones 1:N de forma simple mediante IDs,
 *         evitando anidamientos complejos.</li>
 *     <li>Se proporcionan constructores vacíos y completos, así como getters y setters,
 *         necesarios para Spring Data MongoDB.</li>
 * </ul>
 *
 * <h3>Campos:</h3>
 * <ul>
 *     <li>{@link #id} – Identificador único del cliente en MongoDB, mapeado a <b>_id</b>.</li>
 *     <li>{@link #name} – Nombre del cliente.</li>
 *     <li>{@link #balance} – Saldo disponible del cliente para suscripciones a fondos.</li>
 *     <li>{@link #transactions} – Lista de IDs de transacciones asociadas al cliente.</li>
 *     <li>{@link #notificationPreference} – Preferencia de notificación del cliente (EMAIL, SMS, etc.), opcional.</li>
 * </ul>
 *
 * <h3>Recomendaciones de uso:</h3>
 * <ul>
 *     <li>Utilizar siempre mappers entre {@link ClientEntity} y la clase de dominio {@link com.example.demo.domain.model.Client}
 *         para separar infraestructura y dominio.</li>
 *     <li>Evitar lógica de negocio en esta entidad; debe ser un simple contenedor de datos para persistencia.</li>
 *     <li>Mantener la colección <b>clients</b> consistente con los índices de MongoDB y las reglas de unicidad según el negocio.</li>
 * </ul>
 */
@Document(collection = "clients")
public class ClientEntity {

    /**
     * Identificador único del cliente en MongoDB.
     * Mapeará automáticamente al campo '_id' de la colección.
     */
    @Id
    @Field("_id")
    private String id;

    /** Nombre completo del cliente */
    private String name;

    /** Saldo disponible del cliente para operaciones en fondos */
    private Double balance;

    /** Lista de IDs de transacciones asociadas al cliente */
    private List<String> transactions;

    /**
     * Preferencia de notificación (EMAIL, SMS, etc.)
     * Puede ser null si el documento fue creado antes de que este campo existiera.
     */
    private String notificationPreference;

    public ClientEntity() {
    }

    public ClientEntity(String id, String name, Double balance, List<String> transactions, String notificationPreference) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.transactions = transactions;
        this.notificationPreference = notificationPreference;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }
    public List<String> getTransactions() { return transactions; }
    public void setTransactions(List<String> transactions) { this.transactions = transactions; }
    public String getNotificationPreference() { return notificationPreference; }
    public void setNotificationPreference(String notificationPreference) { this.notificationPreference = notificationPreference; }
}

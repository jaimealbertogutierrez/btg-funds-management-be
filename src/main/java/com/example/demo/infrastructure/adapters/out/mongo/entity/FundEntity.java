package com.example.demo.infrastructure.adapters.out.mongo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <h2>Entidad MongoDB: FundEntity</h2>
 *
 * <p>
 * Representa la colección <b>funds</b> en MongoDB y mapea directamente los documentos de fondos.
 * Esta clase actúa como adaptador de salida (adapter out) de la arquitectura hexagonal, conectando
 * la capa de infraestructura con la capa de dominio.
 * </p>
 *
 * <h3>Buenas prácticas y notas clave:</h3>
 * <ul>
 *     <li>Se utiliza {@link Document} para indicar la colección MongoDB asociada.</li>
 *     <li>El campo {@link #id} está anotado con {@link Id} y {@link Field("_id")} para mapear correctamente
 *         al identificador de MongoDB (_id). Esto asegura compatibilidad en consultas y operaciones CRUD.</li>
 *     <li>Se mantiene la consistencia de nombres con la capa de dominio (Fund) para facilitar
 *         conversiones mediante mappers.</li>
 *     <li>Evitar lógica de negocio en esta entidad; solo debe contener datos de persistencia.</li>
 * </ul>
 *
 * <h3>Campos:</h3>
 * <ul>
 *     <li>{@link #id} – Identificador único del fondo en MongoDB, mapeado a <b>_id</b>.</li>
 *     <li>{@link #name} – Nombre descriptivo del fondo.</li>
 *     <li>{@link #minAmount} – Monto mínimo requerido para suscribirse al fondo. Utilizado en reglas de negocio.</li>
 *     <li>{@link #category} – Categoría del fondo (por ejemplo, Acciones, Renta Fija, Mixto).</li>
 * </ul>
 *
 * <h3>Recomendaciones de uso:</h3>
 * <ul>
 *     <li>Siempre utilizar mappers entre {@link FundEntity} y la clase de dominio {@link com.example.demo.domain.model.Fund}
 *         para mantener separación entre infraestructura y dominio.</li>
 *     <li>Evitar anidar objetos complejos dentro de la entidad; mantener los campos simples para facilitar consultas
 *         rápidas y escalables en MongoDB.</li>
 *     <li>Verificar que los índices de MongoDB estén correctamente definidos para campos de búsqueda frecuentes,
 *         como {@link #id} o {@link #category}.</li>
 * </ul>
 */
@Document(collection = "funds")
public class FundEntity {

    /**
     * Identificador único del fondo en MongoDB.
     * Mapeado al campo '_id' de la colección.
     */
    @Id
    @Field("_id")
    private String id;

    /** Nombre del fondo */
    private String name;

    /** Monto mínimo requerido para suscripción */
    private Double minAmount;

    /** Categoría del fondo (Acciones, Renta Fija, Mixto, etc.) */
    private String category;

    public FundEntity() {
    }

    public FundEntity(String id, String name, Double minAmount, String category) {
        this.id = id;
        this.name = name;
        this.minAmount = minAmount;
        this.category = category;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getMinAmount() { return minAmount; }
    public void setMinAmount(Double minAmount) { this.minAmount = minAmount; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}

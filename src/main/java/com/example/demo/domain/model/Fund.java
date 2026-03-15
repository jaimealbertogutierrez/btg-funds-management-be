package com.example.demo.domain.model;

/**
 * <h2>Entidad de dominio: Fund</h2>
 *
 * <p>
 * Representa un fondo de inversión dentro del sistema de gestión de fondos.
 * Esta clase pertenece al <b>núcleo del dominio</b> en arquitectura hexagonal,
 * encapsulando datos esenciales para operaciones financieras como suscripción
 * y cancelación de fondos.
 * </p>
 *
 * <h3>Campos y su significado</h3>
 *
 * <ul>
 *     <li><b>id:</b> Identificador único del fondo.
 *         Se utiliza para diferenciar fondos en repositorios, transacciones
 *         y notificaciones. Fundamental para mantener la integridad de datos.</li>
 *
 *     <li><b>name:</b> Nombre descriptivo del fondo.
 *         Se utiliza en reportes, notificaciones al cliente y logs.
 *         Permite identificar de manera amigable un fondo específico.</li>
 *
 *     <li><b>minAmount:</b> Monto mínimo requerido para suscribirse al fondo.
 *         Campo crítico para reglas de negocio: asegura que el cliente tiene saldo
 *         suficiente antes de crear transacciones. También se usa para validaciones
 *         en servicios de aplicación.</li>
 *
 *     <li><b>category:</b> Categoría o tipo de fondo (por ejemplo: Renta Fija,
 *         Renta Variable). Se usa para clasificar fondos, aplicar reglas de negocio
 *         específicas según categoría y generar reportes segmentados.</li>
 * </ul>
 *
 * <h3>Constructores</h3>
 *
 * <ul>
 *     <li>Constructor vacío: permite crear instancias inicializables con setters.</li>
 *     <li>Constructor completo: inicializa todos los campos para garantizar un
 *         estado consistente desde la creación del objeto.</li>
 * </ul>
 *
 * <h3>Rol en la arquitectura</h3>
 *
 * <ul>
 *     <li>Entidad central del dominio para operaciones financieras.</li>
 *     <li>Usada por servicios de aplicación como <code>FundManagementService</code>.</li>
 *     <li>Permite aplicar reglas de negocio de suscripción y cancelación
 *         sin depender de infraestructura o presentación.</li>
 * </ul>
 *
 * @author Jaime
 */
public class Fund {

    /**
     * Identificador único del fondo.
     * Utilizado en repositorios, transacciones y notificaciones.
     */
    private String id;

    /**
     * Nombre del fondo.
     * Descriptivo para reportes, logs y notificaciones a clientes.
     */
    private String name;

    /**
     * Monto mínimo de suscripción requerido.
     * Se valida contra el saldo del cliente antes de realizar transacciones.
     */
    private Double minAmount;

    /**
     * Categoría del fondo (ej. Renta Fija, Renta Variable).
     * Permite clasificar fondos y aplicar reglas de negocio específicas.
     */
    private String category;

    /** Constructor vacío */
    public Fund() {}

    /**
     * Constructor completo
     *
     * @param id identificador único
     * @param name nombre del fondo
     * @param minAmount monto mínimo de suscripción
     * @param category categoría del fondo
     */
    public Fund(String id, String name, Double minAmount, String category) {
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

} // Fin de la clase

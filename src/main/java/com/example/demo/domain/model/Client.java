package com.example.demo.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * <h2>Entidad de dominio: Client</h2>
 *
 * <p>
 * Representa un cliente dentro del sistema de gestión de fondos.
 * Esta clase forma parte del <b>núcleo de dominio</b> en una
 * arquitectura hexagonal (Ports & Adapters), y encapsula tanto
 * los datos relevantes del cliente como la lógica de negocio
 * relacionada con su saldo y transacciones.
 * </p>
 *
 * <h3>Datos que se procesan</h3>
 *
 * <ul>
 *     <li><b>id:</b> Identificador único del cliente.</li>
 *     <li><b>name:</b> Nombre completo del cliente.</li>
 *     <li><b>balance:</b> Saldo disponible del cliente. Se utiliza
 *         para validar y ejecutar suscripciones y cancelaciones
 *         de fondos.</li>
 *     <li><b>transactions:</b> Lista de IDs de las transacciones
 *         asociadas al cliente. Permite llevar un historial de
 *         movimientos financieros.</li>
 *     <li><b>notificationPreference:</b> Preferencia de notificación
 *         del cliente (ej. EMAIL, SMS). Se usa para definir cómo
 *         se envían alertas o confirmaciones de transacciones.</li>
 * </ul>
 *
 * <h3>Lógica de negocio incluida</h3>
 *
 * <ul>
 *     <li><b>deductBalance(Double amount):</b> Descuenta del saldo del cliente
 *         el monto especificado. Se utiliza al ejecutar una
 *         suscripción a un fondo.</li>
 *     <li><b>addBalance(Double amount):</b> Aumenta el saldo del cliente
 *         el monto especificado. Se utiliza al cancelar una
 *         suscripción o revertir transacciones.</li>
 * </ul>
 *
 * <p>
 * La clase garantiza que las transacciones y saldo del cliente se
 * mantengan consistentes, y proporciona métodos para manipular
 * el estado de forma controlada, respetando reglas de negocio.
 * </p>
 *
 * <h3>Constructores</h3>
 *
 * <ul>
 *     <li>Constructor vacío: inicializa la lista de transacciones.</li>
 *     <li>Constructor completo: permite inicializar todos los campos,
 *         asegurando valores por defecto cuando sea necesario
 *         (ej. lista vacía de transacciones, preferencia de notificación "EMAIL").</li>
 * </ul>
 *
 * <h3>Importancia dentro de la arquitectura</h3>
 *
 * <ul>
 *     <li>Representa la entidad principal sobre la que operan los
 *         casos de uso financieros.</li>
 *     <li>Se mantiene desacoplada de la infraestructura (BD, API, mensajería)
 *         mediante puertos y servicios de aplicación.</li>
 *     <li>Permite que los servicios de aplicación (como
 *         <code>FundManagementService</code>) ejecuten lógica
 *         de negocio validando saldo y registrando transacciones.</li>
 * </ul>
 *
 * <p>
 * Los getters y setters permiten acceso controlado a los datos
 * del cliente, manteniendo integridad y encapsulamiento.
 * </p>
 *
 * <author>Jaime Alberto Gutierrez</author>
 * <email>jaimealbertogutierrez@gmail.com</email>
 * <since>1.0</since>
 */
public class Client {

    /*
     * Identificador único del cliente dentro del sistema.
     * Se utiliza en repositorios, transacciones y notificaciones
     * para referenciar al cliente de manera inequívoca, garantizando
     * integridad de datos y trazabilidad de operaciones.
     */
    private String id;

    /*
     * Nombre completo del cliente.
     * Se utiliza en reportes, notificaciones y logs para identificación
     * amigable en interfaces de usuario y comunicaciones externas.
     */
    private String name;

    /*
     * Saldo disponible del cliente.
     * Campo crítico para la lógica de negocio: se valida antes de
     * suscribirse a un fondo (debe ser mayor o igual al minAmount)
     * y se actualiza al realizar transacciones de suscripción o cancelación,
     * asegurando operaciones válidas.
     */
    private Double balance;

    /*
     * Lista de IDs de transacciones vinculadas al cliente.
     * Mantiene el historial financiero, permite verificar suscripciones activas,
     * generar reportes y auditorías, y asegurar trazabilidad de cada operación.
     */
    private List<String> transactions; // IDs de las transacciones vinculadas

    /*
     * Preferencia de notificación del cliente (ej. EMAIL, SMS).
     * Define el canal de envío de alertas, confirmaciones de transacciones
     * e información de fondos, asegurando comunicación efectiva y personalizada.
     */
    private String notificationPreference;

    /**
     * Constructor vacío.
     * Inicializa la lista de transacciones vacía.
     */
    public Client() {
        this.transactions = new ArrayList<>();
    }

    /**
     * Constructor completo.
     *
     * @param id identificador único del cliente
     * @param name nombre completo del cliente
     * @param balance saldo disponible
     * @param transactions lista de IDs de transacciones asociadas
     * @param notificationPreference preferencia de notificación
     */
    public Client(String id, String name, Double balance, List<String> transactions, String notificationPreference) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.transactions = transactions != null ? transactions : new ArrayList<>();
        this.notificationPreference = notificationPreference != null ? notificationPreference : "EMAIL";
    }

    // Lógica de Negocio

    /**
     * Descuenta un monto del saldo del cliente.
     *
     * @param amount monto a deducir
     */
    public void deductBalance(Double amount) {
        if (this.balance != null && amount != null) {
            this.balance -= amount;
        }
    }

    /**
     * Añade un monto al saldo del cliente.
     *
     * @param amount monto a agregar
     */
    public void addBalance(Double amount) {
        if (this.balance != null && amount != null) {
            this.balance += amount;
        }
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }
    public List<String> getTransactions() {
        return transactions;
    }
    public void setTransactions(List<String> transactions) {
        this.transactions = transactions;
    }
    public String getNotificationPreference() {
        return notificationPreference;
    }
    public void setNotificationPreference(String notificationPreference) {
        this.notificationPreference = notificationPreference;
    }

} // Fin de la clase

package com.example.demo.domain.model;

import java.time.LocalDateTime;

/**
 * <h2>Entidad de dominio: Transaction</h2>
 *
 * <p>
 * Representa una transacción financiera asociada a un cliente y un fondo.
 * Puede ser de tipo "SUBSCRIPTION" (suscripción) o "CANCELLATION" (cancelación).
 * Esta entidad se encuentra en el núcleo de dominio de la arquitectura hexagonal.
 * </p>
 */
public class Transaction {

    /*
     * Identificador único de la transacción.
     * Se utiliza para referenciar y auditar la operación en repositorios
     * y para garantizar trazabilidad de los movimientos financieros.
     */
    private String id;

    /*
     * Identificador del cliente asociado a esta transacción.
     * Permite relacionar la transacción con el cliente correspondiente,
     * validar reglas de negocio y generar reportes personalizados.
     */
    private String clientId;

    /*
     * Identificador del fondo asociado a la transacción.
     * Vincula la transacción a un fondo específico y permite verificar
     * requisitos de suscripción y cancelación según reglas de negocio.
     */
    private String fundId;

    /*
     * Tipo de transacción: "SUBSCRIPTION" para suscripciones o "CANCELLATION"
     * para cancelaciones de fondos. Es crítico para aplicar lógica de negocio,
     * cálculo de saldo y notificaciones.
     */
    private String type; // "SUBSCRIPTION" o "CANCELLATION"

    /*
     * Monto de la transacción.
     */
    private Double amount;

    /*
     * Fecha y hora de la transacción.
     * Se registra en el historial para auditoría, seguimiento de eventos
     * y generación de reportes.
     */
    private LocalDateTime date;


    /** Constructor vacío */
    public Transaction() {
    }

    /**
     * Constructor completo
     *
     * @param id Identificador único de la transacción
     * @param clientId Identificador del cliente
     * @param fundId Identificador del fondo
     * @param type Tipo de transacción ("SUBSCRIPTION" o "CANCELLATION")
     * @param amount Monto de la transacción
     * @param date Fecha y hora de la transacción
     */
    public Transaction(String id, String clientId, String fundId, String type, Double amount, LocalDateTime date) {
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

} // Fin de la clase

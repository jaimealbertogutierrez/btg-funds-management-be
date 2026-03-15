package com.example.demo.infrastructure.adapters.in.web.dto;

/**
 * <h2>DTO de Solicitud: FundRequest</h2>
 *
 * <p>
 * Representa la estructura de datos enviada por el cliente al microservicio
 * cuando desea realizar operaciones relacionadas con fondos, como suscripción
 * o cancelación.
 * </p>
 *
 * <h3>Responsabilidad</h3>
 * <ul>
 *     <li>Transportar información esencial del cliente y del fondo hacia la capa de aplicación.</li>
 *     <li>Permitir que los adaptadores de entrada (controladores web) reciban datos
 *         de forma estructurada y segura.</li>
 *     <li>Desacoplar la lógica de negocio de la estructura de la solicitud HTTP.</li>
 * </ul>
 */
public class FundRequest {

    /**
     * Identificador único del cliente que realiza la operación.
     * Es utilizado en la capa de dominio para validar existencia y procesar transacciones.
     */
    private String clientId;

    /**
     * Identificador único del fondo sobre el que se realizará la operación
     * (suscripción o cancelación).
     */
    private String fundId;

    /**
     * Constructor por defecto.
     * Requerido por frameworks de deserialización como Jackson o Spring MVC.
     */
    public FundRequest() {
    }

    /**
     * Constructor completo.
     *
     * @param clientId Identificador del cliente
     * @param fundId Identificador del fondo
     */
    public FundRequest(String clientId, String fundId) {
        this.clientId = clientId;
        this.fundId = fundId;
    }

    // Getters y Setters

    /** @return Identificador del cliente */
    public String getClientId() { return clientId; }

    /** @param clientId Identificador del cliente */
    public void setClientId(String clientId) { this.clientId = clientId; }

    /** @return Identificador del fondo */
    public String getFundId() { return fundId; }

    /** @param fundId Identificador del fondo */
    public void setFundId(String fundId) { this.fundId = fundId; }
}

package com.example.demo.infrastructure.adapters.in.web;

import com.example.demo.domain.model.Transaction;
import com.example.demo.domain.ports.in.FundManagementUseCase;
import com.example.demo.domain.ports.out.TransactionRepositoryPort;
import com.example.demo.infrastructure.adapters.in.web.dto.FundRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de fondos (suscripción, cancelación, historial).
 */
@RestController
@RequestMapping("/api/v1/funds")
public class FundController {

    private final FundManagementUseCase fundManagementUseCase;
    private final TransactionRepositoryPort transactionRepositoryPort;

    public FundController(FundManagementUseCase fundManagementUseCase, TransactionRepositoryPort transactionRepositoryPort) {
        this.fundManagementUseCase = fundManagementUseCase;
        this.transactionRepositoryPort = transactionRepositoryPort;
    }

    /**
     * Suscribe un cliente a un fondo.
     *
     * @param request DTO con clientId y fundId
     * @return Transacción generada con estado HTTP 201
     */
    @Operation(summary = "Suscribir cliente a un fondo", description = "Permite a un cliente suscribirse a un fondo específico, generando una transacción de tipo SUBSCRIPTION")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Suscripción exitosa"),
            @ApiResponse(responseCode = "400", description = "Error de validación de negocio o saldo insuficiente"),
            @ApiResponse(responseCode = "404", description = "Cliente o fondo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error inesperado del servidor")
    })
    @PostMapping("/subscribe")
    public ResponseEntity<Transaction> subscribe(@RequestBody FundRequest request) {
        Transaction transaction = fundManagementUseCase.subscribeToFund(request.getClientId(), request.getFundId());
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    /**
     * Cancela la suscripción de un cliente a un fondo.
     *
     * @param request DTO con clientId y fundId
     * @return Transacción de cancelación con estado HTTP 201
     */
    @Operation(summary = "Cancelar suscripción a un fondo", description = "Permite cancelar la suscripción de un cliente a un fondo, generando una transacción de tipo CANCELLATION")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cancelación exitosa"),
            @ApiResponse(responseCode = "400", description = "Error de validación de negocio"),
            @ApiResponse(responseCode = "404", description = "Cliente o fondo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error inesperado del servidor")
    })
    @PostMapping("/cancel")
    public ResponseEntity<Transaction> cancel(@RequestBody FundRequest request) {
        Transaction transaction = fundManagementUseCase.cancelSubscription(request.getClientId(), request.getFundId());
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    /**
     * Obtiene el historial completo de transacciones de un cliente.
     *
     * @param clientId Identificador del cliente
     * @return Lista de transacciones con estado HTTP 200
     */
    @Operation(summary = "Obtener historial de transacciones de un cliente", description = "Devuelve todas las transacciones realizadas por un cliente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial de transacciones obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error inesperado del servidor")
    })
    @GetMapping("/transactions/{clientId}")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable String clientId) {
        List<Transaction> transactions = transactionRepositoryPort.findByClientId(clientId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}  // Fin del REST Controller

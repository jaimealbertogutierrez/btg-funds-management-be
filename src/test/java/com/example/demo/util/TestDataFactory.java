package com.example.demo.util;

import com.example.demo.domain.model.Client;
import com.example.demo.domain.model.Fund;
import com.example.demo.domain.model.Transaction;
import com.example.demo.infrastructure.adapters.out.mongo.entity.ClientEntity;
import com.example.demo.infrastructure.adapters.out.mongo.entity.FundEntity;
import com.example.demo.infrastructure.adapters.out.mongo.entity.TransactionEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Factoría centralizada de datos para pruebas unitarias e integración.
 * * Desarrollado por: Jaime Alberto Gutiérrez
 * Ingeniero de Sistemas y Computación | Analista Programador Java Backend
 * C.C. 9733675
 */
public class TestDataFactory {

    /**
     * Constructor privado para evitar instanciación (Regla SonarQube).
     */
    private TestDataFactory() {
        throw new IllegalStateException("Utility class - No debe ser instanciada");
    }

    // =========================================================================
    // MÉTODOS PARA CLIENTE (CLIENT)
    // =========================================================================

    public static Client createMockClientDomain() {
        Client client = new Client();
        client.setId("client-123");
        client.setName("Jaime Gutierrez");
        client.setBalance(500000.0);
        client.setNotificationPreference("EMAIL");
        client.setTransactions(new ArrayList<>());
        return client;
    }

    public static ClientEntity createMockClientEntity() {
        ClientEntity entity = new ClientEntity();
        entity.setId("client-123");
        entity.setName("Jaime Gutierrez");
        entity.setBalance(500000.0);
        entity.setNotificationPreference("EMAIL");
        entity.setTransactions(Collections.emptyList());
        return entity;
    }

    // =========================================================================
    // MÉTODOS PARA FONDO (FUND)
    // =========================================================================

    public static Fund createMockFundDomain() {
        return new Fund(
                "3",
                "DEUDAPRIVADA",
                50000.0,
                "FIC"
        );
    }

    public static FundEntity createMockFundEntity() {
        FundEntity entity = new FundEntity();
        entity.setId("3");
        entity.setName("DEUDAPRIVADA");
        entity.setMinAmount(50000.0);
        entity.setCategory("FIC");
        return entity;
    }

    public static List<FundEntity> createMockFundEntityList() {
        FundEntity f1 = createMockFundEntity();

        FundEntity f2 = new FundEntity();
        f2.setId("1");
        f2.setName("FPV_BTG_PACTUAL_RECAUDADORA");
        f2.setMinAmount(75000.0);
        f2.setCategory("FPV");

        return List.of(f1, f2);
    }

    // =========================================================================
    // MÉTODOS PARA TRANSACCIÓN (TRANSACTION)
    // =========================================================================

    public static Transaction createMockTransactionDomain() {
        Transaction domain = new Transaction();
        domain.setId("tx-999");
        domain.setClientId("client-123");
        domain.setFundId("3");
        domain.setType("SUBSCRIPTION");
        domain.setAmount(50000.0);
        domain.setDate(LocalDateTime.now());
        return domain;
    }

    public static TransactionEntity createMockTransactionEntity() {
        TransactionEntity entity = new TransactionEntity();
        entity.setId("tx-999");
        entity.setClientId("client-123");
        entity.setFundId("3");
        entity.setType("SUBSCRIPTION");
        entity.setAmount(50000.0);
        entity.setDate(LocalDateTime.now());
        return entity;
    }

    public static TransactionEntity createMockCancellationEntity() {
        TransactionEntity entity = createMockTransactionEntity();
        entity.setType("CANCELLATION");
        entity.setAmount(50000.0);
        return entity;
    }
}
package com.example.demo.infrastructure.adapters.in.web;

import com.example.demo.domain.model.Transaction;
import com.example.demo.domain.ports.in.FundManagementUseCase;
import com.example.demo.domain.ports.out.TransactionRepositoryPort;
import com.example.demo.infrastructure.adapters.in.web.dto.FundRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * FundControllerTest
 *
 * <p>Pruebas unitarias para {@link FundController} usando {@link WebMvcTest} y
 * {@link MockMvc} para simular peticiones HTTP a los endpoints REST.
 *
 * <p>Se valida el comportamiento de los endpoints de suscripción, cancelación
 * y consulta de historial de transacciones.
 *
 * <p>Se sigue la metodología TDD con enfoque GIVEN / WHEN / THEN:
 * <ul>
 *     <li><b>GIVEN:</b> Datos de entrada y comportamiento esperado de los mocks.</li>
 *     <li><b>WHEN:</b> Se realiza la llamada HTTP correspondiente con MockMvc.</li>
 *     <li><b>THEN:</b> Se verifica el status HTTP y el contenido JSON de la respuesta.</li>
 * </ul>
 */
@WebMvcTest(FundController.class)
class FundControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FundManagementUseCase fundManagementUseCase;

    @MockBean
    private TransactionRepositoryPort transactionRepositoryPort;

    @Autowired
    private ObjectMapper objectMapper;

    private Transaction mockTransaction;
    private FundRequest fundRequest;

    /**
     * Inicializa transacción y DTO antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        mockTransaction = new Transaction();
        mockTransaction.setId("tx-123");
        mockTransaction.setClientId("client-001");
        mockTransaction.setFundId("3");
        mockTransaction.setType("SUBSCRIPTION");
        mockTransaction.setAmount(50000.0);
        mockTransaction.setDate(LocalDateTime.now());

        fundRequest = new FundRequest();
        fundRequest.setClientId("client-001");
        fundRequest.setFundId("3");
    }

    /**
     * GIVEN: Un request de suscripción válido y un mock de FundManagementUseCase.
     * WHEN: Se realiza POST a /api/v1/funds/subscribe.
     * THEN: Se retorna HTTP 201 con la transacción creada en el cuerpo.
     */
    @Test
    @DisplayName("POST /subscribe: Debe retornar 201 y la transacción creada")
    void subscribe_ShouldReturnCreated() throws Exception {
        when(fundManagementUseCase.subscribeToFund("client-001", "3")).thenReturn(mockTransaction);

        mockMvc.perform(post("/api/v1/funds/subscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fundRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("tx-123"))
                .andExpect(jsonPath("$.type").value("SUBSCRIPTION"));
    }

    /**
     * GIVEN: Un request de cancelación válido y un mock de FundManagementUseCase.
     * WHEN: Se realiza POST a /api/v1/funds/cancel.
     * THEN: Se retorna HTTP 201 con la transacción de cancelación en el cuerpo.
     */
    @Test
    @DisplayName("POST /cancel: Debe retornar 201 al cancelar exitosamente")
    void cancel_ShouldReturnCreated() throws Exception {
        mockTransaction.setType("CANCELLATION");
        when(fundManagementUseCase.cancelSubscription("client-001", "3")).thenReturn(mockTransaction);

        mockMvc.perform(post("/api/v1/funds/cancel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fundRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("CANCELLATION"));
    }

    /**
     * GIVEN: Un cliente con historial de transacciones.
     * WHEN: Se realiza GET a /api/v1/funds/transactions/{clientId}.
     * THEN: Se retorna HTTP 200 con la lista de transacciones en formato JSON.
     */
    @Test
    @DisplayName("GET /transactions/{clientId}: Debe retornar 200 y la lista de historial")
    void getHistory_ShouldReturnOk() throws Exception {
        List<Transaction> history = List.of(mockTransaction);
        when(transactionRepositoryPort.findByClientId("client-001")).thenReturn(history);

        mockMvc.perform(get("/api/v1/funds/transactions/client-001"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("tx-123"))
                .andExpect(jsonPath("$.length()").value(1));
    }

}

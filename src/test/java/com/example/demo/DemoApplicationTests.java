package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Pruebas de integración básicas de la aplicación Spring Boot.
 *
 * Objetivos:
 * 1. Verificar que el contexto de Spring Boot se cargue correctamente.
 * 2. Asegurar que el método main de la aplicación se ejecute sin lanzar excepciones.
 *
 * Autor: Jaime Alberto Gutiérrez
 */
@SpringBootTest
class DemoApplicationTests {

	/**
	 * Test que valida la correcta carga del contexto de Spring Boot.
	 *
	 * Esta prueba garantiza que todas las configuraciones, beans e inyecciones
	 * de dependencias estén correctamente definidas.
	 */
	@Test
	@DisplayName("Carga del contexto de Spring Boot")
	void contextLoads() {
		// La prueba pasará si el contexto se carga sin errores
	}

	/**
	 * Test que valida que el método main de la aplicación se ejecute correctamente.
	 *
	 * Se asegura que el arranque inicial de la aplicación no lance ninguna excepción.
	 */
	@Test
	@DisplayName("Ejecución del método main de la aplicación")
	void mainMethodRunsWithoutExceptions() {
		assertDoesNotThrow(
				() -> DemoApplication.main(new String[] {}),
				"El método main debería ejecutar sin lanzar excepciones"
		);
	}

}

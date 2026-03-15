package com.example.demo;

import com.example.demo.infrastructure.adapters.in.config.DomainConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * <h2>Clase principal del microservicio BTG Funds Demo</h2>
 *
 * <p>
 * Esta clase es el punto de entrada de la aplicación Spring Boot y configura la ejecución
 * del microservicio. Utiliza anotaciones de Spring Boot para autoconfiguración de beans
 * y contexto de aplicación.
 * </p>
 *
 * <h3>Responsabilidades:</h3>
 * <ul>
 *     <li>Arrancar la aplicación Spring Boot con {@link SpringApplication#run(Class, String...)}.</li>
 *     <li>Importar la configuración de dominio {@link DomainConfig}, asegurando que los casos de uso
 *         del dominio estén disponibles para los adaptadores de entrada (REST controllers) y
 *         otros componentes.</li>
 *     <li>Servir como raíz del contexto de Spring, donde se registran automáticamente componentes,
 *         servicios y adaptadores.</li>
 * </ul>
 *
 * <h3>Notas de diseño:</h3>
 * <ul>
 *     <li>La anotación {@link SpringBootApplication} combina:
 *         <ul>
 *             <li>@Configuration</li>
 *             <li>@EnableAutoConfiguration</li>
 *             <li>@ComponentScan</li>
 *         </ul>
 *         permitiendo una configuración limpia y centralizada.</li>
 *     <li>Se sigue la arquitectura hexagonal, manteniendo el dominio independiente de la infraestructura,
 *         mientras los adaptadores y la configuración se inyectan a través de {@link DomainConfig}.</li>
 *     <li>La clase no contiene lógica de negocio; su único propósito es iniciar el contexto de Spring.</li>
 * </ul>
 *
 * <p>Desarrollado por: Jaime Alberto Gutiérrez</p>
 */
@SpringBootApplication
@Import(DomainConfig.class) // Importa la configuración de dominio para casos de uso y adaptadores
public class DemoApplication {

	/**
	 * Método principal que arranca la aplicación Spring Boot.
	 *
	 * @param args Argumentos de línea de comando (no utilizados en este proyecto).
	 */
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

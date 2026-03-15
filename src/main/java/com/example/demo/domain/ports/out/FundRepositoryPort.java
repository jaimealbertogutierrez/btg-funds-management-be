package com.example.demo.domain.ports.out;

import com.example.demo.domain.model.Fund;
import java.util.Optional;

/**
 * <h2>Puerto de salida (Outbound Port): FundRepositoryPort</h2>
 *
 * <p>
 * Define la interfaz de acceso a los datos de los fondos desde la capa de dominio.
 * Forma parte del núcleo de dominio en la arquitectura hexagonal y permite que
 * la lógica de negocio interactúe con la persistencia sin depender de tecnología específica.
 * </p>
 *
 * <h3>Responsabilidad</h3>
 * <ul>
 *     <li>Proporcionar acceso seguro a la información de un fondo.</li>
 *     <li>Permitir que la lógica de negocio valide existencia de fondos y obtenga
 *         atributos como nombre, categoría y monto mínimo.</li>
 *     <li>Desacoplar la capa de dominio de los detalles de implementación de la base de datos.</li>
 * </ul>
 */
public interface FundRepositoryPort {

    /**
     * Busca un fondo por su identificador único.
     *
     * <p>
     * Devuelve un Optional para manejar de manera segura el caso en que el
     * fondo no exista. La lógica de negocio debe manejar la ausencia de un
     * fondo y lanzar la excepción correspondiente si es necesario.
     * </p>
     *
     * @param id Identificador único del fondo
     * @return Optional<Fund> con el fondo si existe, o vacío si no se encuentra
     */
    Optional<Fund> findById(String id);
}

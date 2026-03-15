/* ************************************************************ */
/* ************************************************************ */
/* ************************************************************ */
/*
    Script SQL de ejemplo para validación conceptual de los registros
    insertados en las tablas del modelo relacional propuesto.

    NOTA IMPORTANTE:
    ----------------
    Esta consulta SQL es únicamente una <strong>propuesta de query</strong>
    para ilustrar cómo se podrían validar los datos si existiera un modelo
    relacional. No hay base de datos relacional implementada actualmente;
    todos los datos reales se gestionan en MongoDB y la arquitectura es
    orientada a documentos.

    La idea principal:
    ------------------
    - Mostrar los clientes (c.nombre) que cumplen la condición de existencia
      en la relación entre Inscripción y Disponibilidad.
    - La subconsulta con EXISTS simula la verificación de visitas de los clientes
      a sucursales disponibles para los productos a los que están inscritos.

    Elaboró: Jaime Alberto Gutierrez Mejia
    Cargo: Analista Programador Java
    CC: 9733675
    Móvil: 3118841634
    Correo: jaimealbertogutierrez@gmail.com
*/

/* Propuesta de consulta SQL relacional conceptual */
SELECT DISTINCT c.nombre
FROM Cliente c
         JOIN Inscripción i ON c.id = i.idCliente
         JOIN Disponibilidad d ON i.idProducto = d.idProducto
WHERE EXISTS (
    SELECT 1
    FROM Visitan v
    WHERE v.idCliente = c.id
      AND v.idSucursal = d.idSucursal
);

/* ************************************************************ */
/* ************************************************************ */
/* ************************************************************ */

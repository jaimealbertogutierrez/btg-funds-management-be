// ************************************************************************************
// ************************************************************************************
// ************************************************************************************
// ************************************************************************************
// ************************************************************************************
// ************************************************************************************
// init-mongo.js
/*
*  📄 Documentación del Script de Inicialización: init-mongo.js
*  Desarrollador: Jaime Alberto Gutiérrez Ingeniero de Sistemas y Computación
*  Analista Programador Java Backend C.C. 9733675
*  Contacto: +57 311 884 1634 / 034 649 067 928
*  Correo: jaimealbertogutierrez@gmail.com
*  LinkedIn: https://www.linkedin.com/in/jaime-alberto-guti%C3%A9rrez-mej%C3%ADa-a969bb9/
*
*  1. Propósito Técnica
*  El archivo init-mongo.js tiene como objetivo automatizar el aprovisionamiento inicial (Seeding)
*  de la base de datos del microservicio. Este script garantiza que, al levantar el entorno por
*  primera vez, el sistema cuente con todos los datos maestros y de prueba requeridos por el
*  negocio, eliminando la necesidad de cargas manuales o scripts externos de post-instalación.
*
* 2. Tecnología Empleada
* Motor de Base de Datos: MongoDB (NoSQL).
*
* Entorno de Ejecución: Docker Entrypoint (scripts ejecutados automáticamente al inicializar el contenedor oficial de MongoDB).
*
* 3. Descripción de Operaciones
* El script ejecuta de manera secuencial las siguientes tareas críticas de configuración:
*
* Autenticación y Contexto: Realiza la elevación de privilegios necesaria en la base de datos admin para asegurar la correcta ejecución de las operaciones de escritura y posteriormente se posiciona en el espacio de nombres de la aplicación: btg_funds_db.
* Carga de Fondos (Catálogo Maestro): Se insertan los 5 fondos de inversión estipulados en los requerimientos técnicos, definiendo para cada uno su identificador único, nombre comercial, monto mínimo de vinculación y categoría (FPV o FIC).
*   Fondo 1: FPV_BTG_PACTUAL_RECAUDADORA ($75.000)
*   Fondo 2: FPV_BTG_PACTUAL_ECOPETROL ($125.000)
*   Fondo 3: DEUDAPRIVADA ($50.000)
*   Fondo 4: FDO-ACCIONES ($250.000)
*   Fondo 5: FPV_BTG_PACTUAL_DINAMICA ($100.000)
*
*  Inicialización de Cliente Global: Se crea el perfil del cliente de prueba (client-001)
*  con un balance inicial de $500.000 COP, permitiendo la validación inmediata de las reglas
*  de negocio sobre suficiencia de saldo.
*
* 4. Integración con el Despliegue
* Este script se vincula al ciclo de vida del contenedor a través del archivo docker-compose.yml
* mediante un volumen de solo lectura mapeado a la ruta /docker-entrypoint-initdb.d/.
* Esta configuración es fundamental para asegurar la idempotencia del sistema y
* facilitar las pruebas unitarias y de integración en ambientes de desarrollo y QA.
*
* */

// ************************************************************************************
// ************************************************************************************
// ************************************************************************************
// Autenticación (Opcional, pero buena práctica si usas credenciales en el compose)
db = db.getSiblingDB('admin');
db.auth('admin', 'secret');

// ************************************************************************************
// ************************************************************************************
// Seleccionamos la base de datos de nuestra aplicación
db = db.getSiblingDB('btg_funds_db');

// ************************************************************************************
// ************************************************************************************
// 1. Crear la colección 'funds' y poblarla con los datos de la prueba
db.funds.insertMany([
    {
        _id: "1",
        name: "FPV_BTG_PACTUAL_RECAUDADORA",
        minAmount: 75000,
        category: "FPV"
    },
    {
        _id: "2",
        name: "FPV_BTG_PACTUAL_ECOPETROL",
        minAmount: 125000,
        category: "FPV"
    },
    {
        _id: "3",
        name: "DEUDAPRIVADA",
        minAmount: 50000,
        category: "FIC"
    },
    {
        _id: "4",
        name: "FDO-ACCIONES",
        minAmount: 250000,
        category: "FIC"
    },
    {
        _id: "5",
        name: "FPV_BTG_PACTUAL_DINAMICA",
        minAmount: 100000,
        category: "FPV"
    }
]);

// ************************************************************************************
// ************************************************************************************
// ************************************************************************************
print("✅ Colección 'funds' inicializada con éxito.");

// ************************************************************************************
// ************************************************************************************
// 2. Crear el cliente inicial con el saldo de regla de negocio
db.clients.insertOne({
    _id: "client-001",
    name: "Candidato BTG",
    balance: 500000,
    transactions: []
});

// ************************************************************************************
// ************************************************************************************
print("✅ Colección 'clients' inicializada con el saldo de 500.000.");

// ************************************************************************************
// ************************************************************************************
// ************************************************************************************
// ************************************************************************************
// ************************************************************************************
// ************************************************************************************

###############################################################################
###############################################################################
###############################################################################
###############################################################################
# INFORMACIÓN DEL DESARROLLO
###############################################################################
#
# Autor: Jaime Alberto Gutierrez
# Rol: Analista Programador Java Backend
# Profesión: Ingeniero de Sistemas y Computación
# Documento: CC 9733675
#
# Correo electrónico:
# jaimealbertogutierrez@gmail.com
#
# Teléfonos de contacto:
# +57 311 8841634
# 034 649067928
#
# LinkedIn profesional:
# https://www.linkedin.com/in/jaime-alberto-guti%C3%A9rrez-mej%C3%ADa-a969bb9/
#
# Descripción general del archivo:
#
# Este Dockerfile define el proceso mediante el cual el microservicio
# btg-funds-service es construido y preparado para ejecutarse dentro
# de un contenedor Docker.
#
# El proceso se implementa utilizando una técnica llamada
# "multi-stage build", que permite dividir la construcción en dos etapas:
#
# 1) Etapa de compilación (Build)
#    Se compila el proyecto Java utilizando Maven y se genera el archivo
#    ejecutable del microservicio (archivo .jar).
#
# 2) Etapa de ejecución (Run)
#    Se crea una imagen más liviana que únicamente contiene el entorno
#    de ejecución de Java y el microservicio ya compilado.
#
# Este enfoque permite:
#
# - Reducir el tamaño final de la imagen
# - Mejorar la seguridad del contenedor
# - Separar dependencias de desarrollo de las de ejecución
#
###############################################################################


###############################################################################
# ETAPA 1: BUILD
#
# En esta primera etapa se construye el microservicio.
#
# Se utiliza una imagen que ya contiene:
#
# - Maven (herramienta de construcción de proyectos Java)
# - OpenJDK 17 (entorno de ejecución del lenguaje Java)
#
# Esta imagen permite compilar el proyecto y generar el artefacto final.
###############################################################################
FROM public.ecr.aws/docker/library/maven:3.8.4-openjdk-17 AS build


###############################################################################
# COPIA DEL CÓDIGO FUENTE DEL PROYECTO
#
# Se copia todo el contenido del repositorio actual al directorio /app
# dentro del contenedor.
#
# Esto incluye:
#
# - Código fuente Java
# - pom.xml
# - recursos del proyecto
# - configuración de construcción
#
###############################################################################
COPY . /app


###############################################################################
# DEFINICIÓN DEL DIRECTORIO DE TRABAJO
#
# Se establece el directorio /app como el directorio desde el cual
# se ejecutarán los comandos dentro del contenedor.
#
###############################################################################
WORKDIR /app


###############################################################################
# COMPILACIÓN DEL PROYECTO
#
# Se ejecuta el comando Maven para compilar el proyecto y generar
# el archivo ejecutable del microservicio.
#
# mvn clean package
#   - clean: elimina compilaciones anteriores
#   - package: compila el proyecto y genera el archivo .jar
#
# -DskipTests
#   Indica que durante la construcción del contenedor no se ejecuten
#   las pruebas automatizadas. Esto acelera el proceso de construcción
#   cuando se generan imágenes para despliegue.
#
###############################################################################
RUN mvn clean package -DskipTests


###############################################################################
# ETAPA 2: RUN
#
# En esta segunda etapa se crea la imagen final que será utilizada
# para ejecutar el microservicio.
#
# Se utiliza Amazon Corretto 17, que es una distribución oficial
# de Java mantenida por Amazon y optimizada para entornos cloud,
# especialmente para infraestructuras desplegadas en AWS.
#
###############################################################################
FROM public.ecr.aws/amazoncorretto/amazoncorretto:17


###############################################################################
# COPIA DEL ARTEFACTO GENERADO
#
# Se copia el archivo .jar generado en la etapa de construcción
# (build) hacia la imagen final.
#
# La instrucción "--from=build" indica que el archivo proviene
# de la etapa anterior del Dockerfile.
#
# El archivo generado en la carpeta target es el microservicio
# ya compilado y listo para ejecutarse.
#
###############################################################################
COPY --from=build /app/target/*.jar app.jar


###############################################################################
# EXPOSICIÓN DEL PUERTO DEL SERVICIO
#
# Se declara que el contenedor utilizará el puerto 8080.
#
# Este es el puerto estándar utilizado por aplicaciones
# desarrolladas con Spring Boot para exponer APIs REST.
#
# Esta instrucción sirve como referencia para herramientas
# de despliegue y orquestadores de contenedores.
#
###############################################################################
EXPOSE 8080

###############################################################################
# COMANDO DE INICIO DEL CONTENEDOR
#
# ENTRYPOINT define el proceso principal que se ejecutará
# cuando el contenedor sea iniciado.
#
# El comando utilizado:
#
# java -jar app.jar
#
# inicia la aplicación Spring Boot que contiene el microservicio,
# activando el servidor web embebido (generalmente Tomcat)
# que expone los endpoints REST de la aplicación.
#
###############################################################################
ENTRYPOINT ["java", "-jar", "app.jar"]

###############################################################################
###############################################################################
###############################################################################
###############################################################################
###############################################################################
###############################################################################

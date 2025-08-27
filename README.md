# Servicio 1: Proyecto CrediYA Implementando Clean Architecture

# Configuración Inicial

Para cumplir con los requisitos del microservicio de autenticación (que utiliza WebFlux, Spring Security y Swagger), sigue estos pasos para configurar el proyecto:

1. **Creación del Proyecto**  
   Crea un directorio para el servicio de autenticación y navega dentro de él:
   ```bash
   mkdir auth-service && cd auth-service
   ```

2. **Configuración del Build Gradle**  
   Agrega el plugin de Clean Architecture al archivo `build.gradle` para establecer la estructura base:
   ```groovy
   plugins {
       id 'co.com.bancolombia.cleanArchitecture' version '3.25.0'
   }
   ```
   Alternativa rápida (Linux/macOS):
   ```bash
   echo "plugins { id 'co.com.bancolombia.cleanArchitecture' version '3.25.0' }" > build.gradle
   ```

3. **Generación de la Estructura del Proyecto**  
   Utiliza los comandos de Gradle proporcionados por el plugin para generar los módulos y configuraciones necesarias:
   - Genera el proyecto con el nombre `AuthService`, tipo reactivo, y paquete base `co.com.pragma.auth`, habilitando Lombok:
     ```bash
     gradle ca --name=AuthService --type=reactive --package=co.com.pragma.auth --lombok=true
     ```
   - Agrega soporte para WebFlux como punto de entrada:
     ```bash
     gradle gep --type webflux
     ```
   - Configura un adaptador driven para R2DBC con PostgreSQL como base de datos:
     ```bash
     gradle gda --type r2dbc --name postgresql
     ```

4. **Adición de Dependencias Requeridas**  
   Incluye las siguientes dependencias en el archivo `build.gradle` para cumplir con WebFlux, Spring Security y Swagger:
   ```groovy
   implementation "org.mapstruct:mapstruct:$mapstructVersion"
   annotationProcessor "org.mapstruct:mapstruct-processor:$mapstructVersion"
   implementation 'org.springframework.boot:spring-boot-starter-security'
   implementation "org.springdoc:springdoc-openapi-starter-webflux-ui:${springDocVersion}"
   implementation "io.swagger.core.v3:swagger-annotations:${swaggerVersion}"
   ```
   Define las versiones de las dependencias en la sección de variables:
   ```groovy
   mapstructVersion = '1.6.3'
   springDocVersion = '2.8.11'
   swaggerVersion = '2.2.35'
   ```

Esta configuración inicial establece un proyecto reactivo basado en Clean Architecture, integrando WebFlux para la lógica reactiva, Spring Security para la autenticación y autorización, y Swagger (a través de Springdoc) para la documentación de la API. Asegúrate de ajustar las versiones o dependencias según las necesidades específicas del proyecto o las actualizaciones disponibles.

## Antes de Iniciar

Empezaremos por explicar los diferentes componentes del proyecto y partiremos de los componentes externos, continuando con los componentes core de negocio (dominio) y por último el inicio y configuración de la aplicación.

Lee el artículo [Clean Architecture — Aislando los detalles](https://medium.com/bancolombia-tech/clean-architecture-aislando-los-detalles-4f9530f35d7a) para una comprensión más profunda de los principios aplicados.

# Arquitectura

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

## Domain

Es el módulo más interno de la arquitectura, pertenece a la capa del dominio y encapsula la lógica y reglas del negocio mediante modelos y entidades del dominio.

## Usecases

Este módulo gradle perteneciente a la capa del dominio, implementa los casos de uso del sistema, define lógica de aplicación y reacciona a las invocaciones desde el módulo de entry points, orquestando los flujos hacia el módulo de entities.

## Infrastructure

### Helpers

En el apartado de helpers tendremos utilidades generales para los Driven Adapters y Entry Points.

Estas utilidades no están arraigadas a objetos concretos, se realiza el uso de generics para modelar comportamientos genéricos de los diferentes objetos de persistencia que puedan existir, este tipo de implementaciones se realizan basadas en el patrón de diseño [Unit of Work y Repository](https://medium.com/@krzychukosobudzki/repository-design-pattern-bc490b256006).

Estas clases no pueden existir solas y deben heredarse su comportamiento en los **Driven Adapters**.

### Driven Adapters

Los driven adapters representan implementaciones externas a nuestro sistema, como lo son conexiones a servicios REST, SOAP, bases de datos, lectura de archivos planos, y en concreto cualquier origen y fuente de datos con la que debamos interactuar.

### Entry Points

Los entry points representan los puntos de entrada de la aplicación o el inicio de los flujos de negocio.

## Application

Este módulo es el más externo de la arquitectura, es el encargado de ensamblar los distintos módulos, resolver las dependencias y crear los beans de los casos de uso (UseCases) de forma automática, inyectando en estos instancias concretas de las dependencias declaradas. Además inicia la aplicación (es el único módulo del proyecto donde encontraremos la función “public static void main(String[] args)”.

**Los beans de los casos de uso se disponibilizan automáticamente gracias a un '@ComponentScan' ubicado en esta capa.**



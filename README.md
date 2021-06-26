# Proyecto
Aplicación de Ejemplo para el uso de un microservicio REST, está construido en Java 8 (o superior) y requiere una conexión a Base de datos (PostgreSQL)

## Entorno de desarrollo recomendado
Para facilidad de desarrollo se aconseja usar Ubuntu 20.04 LTS. Los ejemplos asumen esta distribución.

## Requerimientos
- JDK 8, se aconseja usar OpenJDK (sudo apt-get install openjdk-8-jdk)
- Maven, tener una versión de maven (sudo apt-get install maven)
- Cliente de base de datos **PostgreSQL** (sudo apt-get install postgresql-client )

## Estructura del proyecto

- **app** . Contiene el código fuente de una aplicación Spring BOOT.
- **db** . Contiene los script necesarios para trabajar con la base de datos.
- Por defecto el servicio levanta en la máquina local en el puerto 8080 y con el contexto cpyd: http://127.0.0.1:8080/cpyd
- La documentación del proyecto se encuentra en: http://127.0.0.1:8080/cpyd/swagger-ui/

### Base de datos.
Dentro de la carpeta db existen un conjunto de scripts que se deben ejecutar en orden para instanciar los datos que necesita la aplicación.

* **01-model.sql** Contiene el DDL necesario para crear las tablas y los objetos importantes del modelo de datos.

## Ejecución
Lo primero es compilar el proyecto:

**mvn -f app/ clean install**

Para ejecutar:

**java -jar app/target/cpyd.war**


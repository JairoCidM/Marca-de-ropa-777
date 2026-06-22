# Proyecto Semestral: 777

## Integrantes

* Nicolás Ortega
* Jairo Cid

---

# Descripción del Proyecto

Sistema desarrollado utilizando una arquitectura basada en microservicios con Spring Boot, Docker y MySQL.

La solución se encuentra compuesta por servicios independientes encargados de gestionar distintas áreas del negocio, permitiendo escalabilidad, mantenibilidad y desacoplamiento de responsabilidades.

---

# Arquitectura General

El sistema está compuesto por los siguientes microservicios:

| Microservicio  | Puerto API | Base de Datos     | Funcionalidad                   |
| -------------- | ---------- | ----------------- | ------------------------------- |
| Favoritos      | 8080       | db_favoritos      | Gestión de productos favoritos  |
| Catálogo       | 8081       | db_catalogo       | Gestión de productos            |
| Registro       | 8082       | db_registro       | Gestión de usuarios             |
| Carrito        | 8084       | db_carrito        | Gestión del carrito de compras  |
| Notificaciones | 8085       | db_notificaciones | Gestión de notificaciones       |
| Descuentos     | 8086       | db_descuentos     | Gestión de cupones y descuentos |
| Ventas         | 8087       | db_ventas         | Gestión de ventas               |
| Pagos          | 8088       | db_pagos          | Gestión de pagos                |
| Órdenes        | 8089       | db_ordenes        | Gestión de órdenes              |
| Envíos         | 8090       | db_envios         | Gestión de envíos               |

---

# Tecnologías Utilizadas

* Java 21
* Spring Boot 3
* Spring Data JPA
* MySQL 8
* Maven
* Docker
* Docker Compose
* AWS EC2 (Ubuntu 24.04)
* Swagger / OpenAPI
* JUnit 5
* Mockito
* MockMvc
* H2 Database

---

# Despliegue Técnico

## Infraestructura

* Instancia AWS EC2
* Sistema Operativo: Ubuntu 24.04
* Contenedores Docker para cada microservicio y base de datos

## Levantar entorno

```bash
docker compose up -d
```

## Acceder a un contenedor

```bash
docker exec -it api_pagos bash
```

## Ejecutar aplicación

```bash
./mvnw spring-boot:run
```

## Ejecutar pruebas

```bash
./mvnw test
```

---

# Arquitectura de Capas

Todos los microservicios implementan el patrón:

Controller
↓
Service
↓
Repository
↓
MySQL

Cada servicio mantiene separación de responsabilidades mediante una arquitectura CSR (Controller-Service-Repository).

---

# Documentación Swagger

Los microservicios desarrollados durante la EP3 incorporan documentación OpenAPI mediante Swagger UI.

Formato de acceso:

http://IP_PUBLICA:PUERTO/swagger-ui/index.html

Microservicios documentados:

* Registro
* Carrito
* Catálogo
* Órdenes
* Pagos

---

# Estado Actual del Proyecto

## Completado

* Arquitectura basada en microservicios.
* Persistencia con MySQL.
* Docker Compose.
* Despliegue en AWS EC2.
* CRUD de entidades principales.
* Swagger/OpenAPI.
* Tests unitarios por capas.
* Tests de Controller.
* Tests de Service.
* Tests de Repository.
* Tests de Model.

## En Desarrollo

* Eureka Server.
* API Gateway.

---

# API Gateway y Eureka

Como preparación para el Examen Transversal se han incorporado los módulos:

* ms-eureka
* api-gateway

## Rutas planificadas

| Ruta Gateway     | Destino     |
| ---------------- | ----------- |
| /api/registro/** | MS-REGISTRO |
| /api/catalogo/** | MS-CATALOGO |
| /api/carrito/**  | MS-CARRITO  |
| /api/ordenes/**  | MS-ORDENES  |
| /api/pagos/**    | MS-PAGOS    |

---

# Trabajo Pendiente para el Examen Transversal

1. Implementar Eureka Server.
2. Registrar los microservicios mediante Eureka Discovery Client.
3. Configurar Service Discovery.
4. Integrar API Gateway con Eureka.
5. Implementar enrutamiento dinámico.
6. Validar comunicación entre servicios a través del Gateway.
7. Realizar pruebas de integración.
8. Desplegar Eureka y API Gateway en AWS EC2.

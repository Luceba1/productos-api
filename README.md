# 📦 API REST de Gestión de Productos (Spring Boot)

Proyecto desarrollado como entrega del **Trabajo Práctico - APIs REST con Spring Boot** de la Tecnicatura Universitaria en Programación (UTN).  
El objetivo es construir una API REST **completa y profesional** aplicando:

- Arquitectura en capas (Controller – Service – Repository)
- DTOs y validaciones con Bean Validation
- Manejo global de errores con `@ControllerAdvice`
- Persistencia con Spring Data JPA + H2 en memoria
- Documentación automática con Swagger / OpenAPI

---

## 🧾 Descripción del proyecto

La API permite gestionar productos de un e-commerce básico.  
Se pueden realizar las operaciones típicas de un CRUD:

- Crear productos
- Listar todos los productos
- Buscar por ID
- Filtrar por categoría
- Actualizar un producto completo
- Actualizar únicamente el stock (PATCH)
- Eliminar un producto

Además:
- Valida los datos de entrada (nombre, precio, stock, categoría)
- Devuelve errores con formato unificado (timestamp, status, mensaje, path)
- Está documentada con Swagger UI

---

## 🛠 Tecnologías utilizadas

- **Java 17**  
- **Spring Boot 3.x**
  - spring-boot-starter-web
  - spring-boot-starter-data-jpa
  - spring-boot-starter-validation
- **Base de datos H2** (en memoria)
- **Spring Data JPA**
- **Swagger / OpenAPI** (springdoc-openapi-starter-webmvc-ui)
- **Lombok** (opcional)
- **Maven**

---

## 📁 Estructura de paquetes

```text
com.utn.productos
 ├── controller       # Controladores REST (@RestController)
 ├── service          # Lógica de negocio (@Service)
 ├── repository       # Acceso a datos (extends JpaRepository)
 ├── model            # Entidades JPA y enums
 ├── dto              # Clases DTO de entrada/salida
 └── exception         # Excepciones + @ControllerAdvice
```

---

## ⚙️ Configuración (application.properties)

```properties
server.port=8080

spring.datasource.url=jdbc:h2:mem:productosdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## ▶️ Cómo clonar y ejecutar

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/tu-usuario/productos-api.git
   cd productos-api
   ```

2. Compilar y ejecutar con Maven:

   ```bash
   mvn spring-boot:run
   ```

   o desde tu IDE (IntelliJ / STS) ejecutar la clase:

   ```text
   com.utn.productos.ProductosApiApplication
   ```

3. La app levanta en:

   - API: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui/index.html`
   - H2 Console: `http://localhost:8080/h2-console`

---

## 🌐 Endpoints de la API

| Método | Ruta                                | Descripción                                   | Códigos esperados              |
|--------|--------------------------------------|-----------------------------------------------|---------------------------------|
| GET    | `/api/productos`                    | Lista todos los productos                     | 200 OK                          |
| GET    | `/api/productos/{id}`               | Obtiene un producto por ID                    | 200 OK / 404 Not Found          |
| GET    | `/api/productos/categoria/{cat}`    | Lista productos por categoría                  | 200 OK                          |
| POST   | `/api/productos`                    | Crea un nuevo producto (con validación)       | 201 Created / 400 Bad Request   |
| PUT    | `/api/productos/{id}`               | Actualiza un producto completo                 | 200 OK / 404 Not Found          |
| PATCH  | `/api/productos/{id}/stock`         | Actualiza solo el stock                       | 200 OK / 404 Not Found / 400    |
| DELETE | `/api/productos/{id}`               | Elimina un producto                            | 204 No Content / 404 Not Found  |

🔎 **Categorías permitidas** (enum): `ELECTRONICA`, `ROPA`, `ALIMENTOS`, `HOGAR`, `DEPORTES`.

---

## 📘 Swagger / OpenAPI

La documentación interactiva se genera automáticamente con **springdoc**.

- **URL:** `http://localhost:8080/swagger-ui/index.html`
- Desde ahí se pueden **probar todos los endpoints** sin usar Postman.
- También se puede ver el JSON de la especificación en:  
  `http://localhost:8080/v3/api-docs`

---

## 🗄 Acceso a la consola H2

1. Ir a: `http://localhost:8080/h2-console`
2. Configurar:
   - **JDBC URL:** `jdbc:h2:mem:productosdb`
   - **User:** `sa`
   - **Password:** *(vacío)*
3. Conectar y ver la tabla `PRODUCTO`

---

## 🧱 Manejo de errores

Se implementó un manejador global con `@ControllerAdvice` y `@ExceptionHandler` para:

- `ProductoNotFoundException` → 404
- `MethodArgumentNotValidException` → 400 (muestra qué campo falló)
- `Exception` → 500

Esto asegura **respuestas consistentes** en toda la API.

---

## 🧠 Conclusiones personales

> En este trabajo práctico pude integrar todos los conceptos de la unidad en un solo proyecto: crear un modelo, exponerlo mediante un controlador REST, aplicar validaciones con Bean Validation, persistir datos con Spring Data JPA sobre H2 y finalmente documentar todo con Swagger. También aprendí la importancia de separar en capas (controller, service, repository) y de no exponer las entidades directamente, usando DTOs en su lugar. El manejo global de errores con `@ControllerAdvice` le da a la API un aspecto profesional y facilita el consumo desde el frontend.

*(acá podés agregar más texto propio)*

---

## 👤 Autor

- **Nombre:** Lucas Pujada  
- **Legajo:** *(52736)*  
- **Materia:** Programación III – UTN FRM  
- **Año:** 2025

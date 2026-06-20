# Plan de Pruebas y Cobertura de Reglas de Negocio

## Microservicio: Carrito de Compras

### Reglas de Negocio Críticas
1. **Aislamiento de Datos por Usuario:** El sistema debe devolver exclusivamente los ítems asociados al ID del usuario que consulta su carrito.
2. **Integridad del Ítem:** Todo ítem agregado debe contener obligatoriamente su identificador de producto, cantidad y precio unitario válido.
3. **Persistencia de Búsqueda:** La base de datos debe ser capaz de almacenar y recuperar el carrito completo de un usuario sin mezclar datos de otros clientes.

### Cobertura Actual
| Regla | Estado | Casos Cubiertos |
| :--- | :--- | :--- |
| 1. Aislamiento de Datos | ✅ Cubierta | Controller y Service devuelven correctamente el carrito por `usuarioId` |
| 2. Integridad del Ítem | ✅ Cubierta | Validación de creación de la entidad `ItemCarrito` en el Model Test |
| 3. Persistencia de Búsqueda| ✅ Cubierta | Operación de guardado y método `findByUsuarioId` en el Repository Test |

### Reflexión y Deuda Técnica
* **Riesgo sin probar:** Falta validar el comportamiento del carrito cuando se intenta agregar una cantidad negativa de productos o cuando el producto ya no existe en el catálogo.
* **Acción Futura:** Implementar pruebas de manejo de excepciones en el Service y validaciones de datos de entrada en el Controller.
* **Responsable:** Equipo Backend

---

## Microservicio: Catálogo

### Reglas de Negocio Críticas
1. **Búsqueda y Disponibilidad:** El sistema debe permitir consultar un producto por su ID y arrojar un error claro (404 Not Found) si el producto no existe.
2. **Integridad del Producto:** El nombre del producto es un campo obligatorio y el modelo no debe inicializarlo si se recibe como nulo.

### Cobertura Actual
| Regla | Estado | Casos Cubiertos |
| :--- | :--- | :--- |
| 1. Búsqueda y Disponibilidad | ✅ Cubierta | Controller (200 OK y 404 Not Found) y Service (`obtenerPorId_FallaCuandoNoExiste`) |
| 2. Integridad del Producto | ✅ Cubierta | Test `testCampoObligatorioNoNulo` en ProductoTest |

### Reflexión y Deuda Técnica
* **Riesgo sin probar:** El test `crearProducto_AtrapaBugDelCodigo` en el Service evidencia que el método de creación actualmente arroja un `NullPointerException` bajo ciertas condiciones no controladas.
* **Acción Futura:** Corregir el bug en la implementación de `ProductoService.crearProducto` para manejar los datos del DTO correctamente sin arrojar excepciones nulas, y luego actualizar el test.
* **Responsable:** Equipo Backend

---

## Microservicio: Registro

### Reglas de Negocio Críticas
1. **Unicidad de Correo Electrónico:** No se permite crear un nuevo registro de usuario si el email proporcionado ya existe en la base de datos.
2. **Auditoría de Fechas:** Todo nuevo registro debe autogenerar y guardar su fecha de creación al momento de persistirse en la base de datos.

### Cobertura Actual
| Regla | Estado | Casos Cubiertos |
| :--- | :--- | :--- |
| 1. Unicidad de Correo | ✅ Cubierta | Test `crearRegistro_FallaCuandoEmailYaExiste` en RegistroServiceTest |
| 2. Auditoría de Fechas | ✅ Cubierta | Test `testPrePersist` en RegistroTest validando la generación automática |

### Reflexión y Deuda Técnica
* **Riesgo sin probar:** Al igual que en Catálogo, el test `crearRegistro_AtrapaBugDelCodigo` demuestra que el código actual del servicio lanza una excepción genérica al intentar crear un registro estándar.
* **Acción Futura:** Refactorizar la lógica de `RegistroService.crearRegistro` para solucionar el bug detectado y testear casos de éxito en la creación de usuarios.
* **Responsable:** Equipo Backend

---

## Microservicio: Órdenes

### Reglas de Negocio Críticas
1. **Gestión de Órdenes por Usuario:** El sistema debe permitir consultar exclusivamente las órdenes asociadas al usuario solicitado.
2. **Control de Estado de la Orden:** Toda orden debe mantener un estado válido y permitir su actualización durante el proceso de compra.

### Cobertura Actual
| Regla | Estado | Casos Cubiertos |
| :--- | :--- | :--- |
| 1. Gestión de Órdenes por Usuario | Cubierta | Service y Repository validan la búsqueda de órdenes mediante `obtenerPorUsuario` y `findByUsuarioId` |
| 2. Control de Estado de la Orden | Cubierta | Service valida la actualización de estado y Repository verifica consultas mediante `findByEstado` |

### Reflexión y Deuda Técnica
* **Riesgo sin probar:** No se validan restricciones sobre cambios de estado inválidos ni la consistencia de datos cuando intervienen los microservicios de pagos y envíos.
* **Acción Futura:** Implementar pruebas de integración entre microservicios y validaciones de flujo para los estados permitidos de una orden.
* **Responsable:** Equipo Backend

---

## Microservicio: Pagos

### Reglas de Negocio Críticas
1. **Asociación Correcta del Pago:** Todo pago debe estar vinculado a una orden y a un usuario válido.
2. **Control del Estado del Pago:** El sistema debe permitir consultar y actualizar correctamente el estado de cada pago registrado.

### Cobertura Actual
| Regla | Estado | Casos Cubiertos |
| :--- | :--- | :--- |
| 1. Asociación Correcta del Pago | Cubierta | Service valida la creación de pagos y Repository verifica búsquedas mediante `findByUsuarioId` y `findByOrdenId` |
| 2. Control del Estado del Pago | Cubierta | Service prueba la actualización de estado y Repository valida consultas mediante `findByEstado` |

### Reflexión y Deuda Técnica
* **Riesgo sin probar:** No se validan estados inválidos ni errores provenientes de servicios externos de procesamiento de pagos.
* **Acción Futura:** Incorporar reglas de validación para estados permitidos y desarrollar pruebas de integración con plataformas externas de pago.
* **Responsable:** Equipo Backend
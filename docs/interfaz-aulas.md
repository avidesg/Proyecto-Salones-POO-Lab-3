# Interfaz de aulas — parte 3

La vista se encuentra en `src/main/resources/edu/ufg/aulas/proyectoLaboratorio3/home.fxml` y sus estilos en `home.css`, en la misma carpeta. Es una interfaz de escritorio JavaFX 25, compatible con Scene Builder. Conserva `HomeController` como controlador y el evento existente para cerrar sesión.

## Abrir y editar en Scene Builder

1. Abre `home.fxml` con Scene Builder compatible con JavaFX 25.
2. Conserva `home.css` junto al FXML: se carga mediante la ruta relativa `@home.css`.
3. Usa la vista previa para revisar el formulario. El tamaño preferido es 860 × 660; en la ventana actual de 640 × 480, el formulario permite desplazarse y los botones permanecen visibles.
4. Mantén los `fx:id` y `fx:controller` al modificar el diseño, para conservar la integración con la parte 4.

## Contrato para la persona 4

| `fx:id` | Control JavaFX | Dato o uso |
| --- | --- | --- |
| `txtNombre` | `TextField` | `Aula.nombre`, `String` |
| `txtEdificio` | `TextField` | `Aula.edificio`, `String` |
| `cmbPlanta` | `ComboBox<Planta>` | Catálogo de plantas; `Planta.numero` debe ser mayor que cero |
| `txtCapacidad` | `TextField` | Convertir a entero mayor que cero para `Aula.capacidad` |
| `cmbTipoAula` | `ComboBox<TipoAula>` | Catálogo para `Aula.tipoAula` |
| `txtResponsable` | `TextField` | `Aula.responsable`, `String` |
| `cmbEstado` | `ComboBox<EstadoAula>` | Catálogo para `Aula.estado` |
| `btnGuardar` | `Button` | Conectar con el método `guardar()` |
| `btnLimpiar` | `Button` | Conectar con el método `limpiar()` |
| `btnCerrarSesion` | `Hyperlink` | Ya enlazado con `#cerrarSesion` |
| `lblBienvenida` | `Label` | El controlador existente muestra el usuario conectado |
| `lblMensaje` | `Label` | Mensajes de validación, éxito o error |

Los tipos de catálogo corresponden a las entidades disponibles en `origin/feature/entidades-bd`. Sus métodos `toString()` muestran el número de planta y los nombres de tipo y estado. El FXML no necesita importar esas entidades.

### Eventos

Guardar y Limpiar tienen `fx:id`, pero todavía no tienen `onAction`: sus métodos corresponden a la parte 4 y aún no existen en `HomeController`. Esto permite cargar la vista actual sin errores por métodos inexistentes.

La persona 4 debe inyectar los controles anteriores con `@FXML` y, después de implementar los métodos, conectar los botones en `initialize()`:

```java
btnGuardar.setOnAction(event -> guardar());
btnLimpiar.setOnAction(event -> limpiar());
```

También puede usar `onAction="#guardar"` y `onAction="#limpiar"` en el FXML, con los respectivos métodos anotados con `@FXML`, en lugar de conectar los eventos desde Java.

El botón Guardar es el botón predeterminado del formulario. Los controles admiten navegación con Tab y muestran el foco del teclado.

### Datos y mensajes

Los tres ComboBox se dejan vacíos para que la persona 4 los cargue desde la base de datos. Los ejemplos de los campos son `promptText`, no valores registrados ni datos de prueba.

El controlador debe validar campos completos, números positivos y duplicados antes de guardar. Puede escribir en `lblMensaje` y aplicar la clase CSS `error` o `success`, retirando la clase anterior. Limpiar debe vaciar los cuatro TextField, quitar las tres selecciones, borrar el mensaje y devolver el foco a `txtNombre`.

La parte 3 no modifica el DAO, las entidades, el SQL ni el controlador.

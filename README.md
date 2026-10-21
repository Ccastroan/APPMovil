# Gestión móvil y seguimiento de solicitudes de clientes

App Android hecha para la Evaluación Parcial 2 de DSY1105 (Duoc UC). La idea es que un cliente pueda ingresar una solicitud a la empresa y después seguirla con un código único.

## Integrantes

- Cristián Castro

## Tecnologías

- Kotlin
- Jetpack Compose con Material 3
- Arquitectura MVVM
- Room (persistencia local)
- Navigation Compose
- CameraX y ML Kit (lector de QR)

## Qué hace nuestra app

- Inicio de sesión con usuario y contraseña.
- Lista con las solicitudes del cliente.
- Formulario para crear una solicitud (título, descripción, categoría y servicio); cada campo se valida y muestra su error con un ícono.
- Las validaciones están en el ViewModel, separadas de la pantalla.
- Las solicitudes se guardan en el celular con Room, así que siguen ahí después de cerrar la app.
- Cada solicitud recibe un código de seguimiento (SOL-0001, SOL-0002, etc.).
- Pantalla de resumen después de enviar la solicitud.
- Lector de QR con la cámara para buscar una solicitud por su código.
- Animaciones: el botón cambia de color según el formulario, los errores aparecen de forma suave, el resumen entra animado y el marco del lector QR parpadea.

## Recursos nativos

- Cámara: para leer los códigos QR (pide permiso al usuario).
- Notificaciones: aviso local con el código de seguimiento cuando se envía una solicitud (pide permiso en Android 13 o superior).

## Estructura del proyecto

```
app/src/main/java/com/example/dsy1105_009v_2026/
├── data/          DAO y base de datos Room
├── model/         Entidades, enumeraciones y estados de la pantalla
├── repository/    Acceso a los datos
├── viewmodel/     Lógica y estado de cada pantalla
├── view/          Pantallas en Compose
├── navigation/    Navegación entre pantallas
├── util/          Fechas, escáner QR, permiso de cámara y notificaciones
└── ui/theme/      Tema e inicio de sesión
```

## Cómo ejecutarlo

1. Clonar el repositorio: `git clone https://github.com/Ccastroan/APPMovil.git`
2. Abrirlo en Android Studio y esperar a que termine el Sync de Gradle.
3. Correrlo en un emulador con cámara (o en un celular con depuración USB).
4. Entrar con el usuario `admin` y la contraseña `123`.

Para probar el lector de QR: crear una solicitud, copiar su código (por ejemplo `SOL-0001`), generar un QR con ese texto en cualquier página de internet y escanearlo desde la pantalla del lector.

## Trello

https://trello.com/invite/b/6ad856de7d5bd19c9cd6be1b/ATTI1b38f9fc37b504619896fea0a4a047ab5B9C0F38/dsy1105-solicitudes

## Lo que quedó para después

- Perfiles de técnico y administrador.
- Adjuntar fotos a una solicitud.
- Compartir el código de seguimiento por WhatsApp.
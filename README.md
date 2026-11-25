# Hertani - Backend Spring Boot (WIM 100)

## Descripción del Proyecto
Microservicio backend desarrollado en **Spring Boot 3 (Java 17)** con **MySQL 8** para la gestión y autenticación de postulantes del sistema **WIM 100**. Conectado con la aplicación móvil Android (Hertani) mediante **Retrofit**.

## Integrantes
- Nicolás Herrera
- Renato Qevedo
- Hernaldo Silva
- **Asignatura**: Desarrollo de Aplicaciones Móviles (DSY1105)

## Endpoints Principales
- `POST /api/auth/login` — Autenticación de usuarias (roles Evaluadora y Dirección) con contraseñas cifradas en BCrypt.
- `GET /api/postulantes` — Listar todos los postulantes.
- `GET /api/postulantes/{id}` — Obtener detalle de un postulante.
- `POST /api/postulantes` — Registrar nuevo postulante (con validación de correo único).
- `PUT /api/postulantes/{id}` — Editar postulante.
- `PATCH /api/postulantes/{id}/estado` — Cambiar estado de la candidatura.
- `DELETE /api/postulantes/{id}` — Eliminar postulante.

## Requisitos y Pasos para Ejecutar
1. Instalar Java 17+ y MySQL 8.
2. Configurar la conexión en `src/main/resources/application.properties`.
3. Ejecutar la aplicación con Maven: `mvn spring-boot:run`.

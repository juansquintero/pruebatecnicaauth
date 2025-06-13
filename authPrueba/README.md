# Prueba Técnica - API de Autenticación con DummyJSON

Esta API REST en Java con Spring Boot permite a usuarios autenticarse contra la API externa DummyJSON y registra cada autenticación válida en una base de datos PostgreSQL.

## Requisitos

- Java 21+
- PostgreSQL
- Gradle

## Configuración

1. Crea una base de datos PostgreSQL llamada `auth_prueba`
2. Las credenciales de la base de datos estan definidas en `src/main/resources/application.properties` 

## Ejecución

Para ejecutar la aplicación:

```bash
./gradlew bootRun
```

La aplicación estará disponible en `http://localhost:8080`

## Endpoints

### Login

```
POST /api/auth/login
Content-Type: application/json

{
    "username": "emilys",
    "password": "emilyspass"
}
```

### Obtener usuario autenticado

```
GET /api/auth/me
Authorization: token -- El que se retorna del login
```

## Usuario de prueba

- Username: emilys
- Password: emilyspass

## Ejemplo de curl para login

```bash
curl --request POST \
  --url http://localhost:8080/api/auth/login \
  --header 'Content-Type: application/json' \
  --data '{
    "username": "emilys",
    "password": "emilyspass"
  }'
```

## Registro de login

Cada vez que un usuario se autentica exitosamente, se guarda un registro en la tabla `login_log` con la siguiente información:

- ID (UUID)
- Username
- Timestamp del login
- Token de acceso
- Token de refresh (si está disponible)

## Estructura del proyecto

- `controller`: Controladores REST
- `service`: Servicios de negocio
- `repository`: Repositorios JPA
- `model`: Entidades de base de datos
- `dto`: Objetos de transferencia de datos
- `client`: Clientes Feign para APIs externas
- `config`: Configuraciones 


## Juan Sebastian Quintero Chaves
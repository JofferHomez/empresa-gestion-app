# Empresa Gestión App

Aplicación web para la gestión de empresas, productos, inventario, clientes y órdenes.

## Stack Tecnológico

### Backend
- **Java 17**
- **Spring Boot 3.1.5**
- **Spring Data JPA + Hibernate**
- **Spring Security + JWT**
- **PostgreSQL**
- **iText PDF** (Generación de PDFs)
- **JavaMailSender** (Envío de correos)

### Frontend
- **Vue.js 3**
- **Quasar Framework**
- **Axios** (HTTP Client)
- **Node.js**

## Requisitos Previos

- **Java 17** o superior
- **Maven 3.8+**
- **PostgreSQL 12+**
- **Node.js 16+** y **npm 8+**

## Instalación y Configuración

### 1. Base de Datos

```sql
CREATE DATABASE empresa_db;
```

Luego ejecuta el script SQL en `src/main/resources/init.sql`

### 2. Backend

```bash
# Clonar el repositorio
git clone https://github.com/JofferHomez/empresa-gestion-app.git
cd empresa-gestion-app

# Configurar variables en application.yml
# - Database: usuario y contraseña
# - Email: configurar SMTP
# - JWT: cambiar secret

# Instalar dependencias y compilar
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

El backend estará disponible en: `http://localhost:8080/api`

### 3. Frontend

```bash
cd frontend

# Instalar dependencias
npm install

# Ejecutar en modo desarrollo
npm run dev

# Construir para producción
npm run build
```

El frontend estará disponible en: `http://localhost:5173`

## Estructura del Proyecto

```
empresa-gestion-app/
├── src/
│   ├── main/
│   │   ├── java/com/empresa/
│   │   │   ├── config/              # Configuraciones de Spring
│   │   │   ├── security/            # JWT, autenticación
│   │   │   ├── entity/              # Entidades JPA
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── repository/          # Repositorios JPA
│   │   │   ├── service/             # Lógica de negocio
│   │   │   ├── controller/          # Controladores REST
│   │   │   ├── exception/           # Manejo de excepciones
│   │   │   ├── util/                # Utilidades
│   │   │   └── EmpresaGestionAppApplication.java
│   │   ├── resources/
│   │   │   ├── application.yml      # Configuración de la app
│   │   │   └── init.sql             # Script de BD
│   │   └── webapp/
│   └── test/
├── frontend/
│   ├── src/
│   │   ├── components/              # Componentes Vue
│   │   ├── pages/                   # Páginas
│   │   ├── stores/                  # Pinia stores
│   │   ├── services/                # Servicios API
│   │   ├── App.vue
│   │   └── main.js
│   ├── package.json
│   └── quasar.conf.js
├── pom.xml
└── README.md
```

## Modelos de Datos

### Relaciones
- **Usuario** → Rol (ADMIN, EXTERNAL)
- **Empresa** → Productos (1:N)
- **Producto** → Categorías (M:N)
- **Cliente** → Órdenes (1:N)
- **Orden** → Productos (M:N)
- **Inventario** → Producto + Empresa (M:M)

## API Endpoints

### Autenticación
- `POST /auth/login` - Iniciar sesión
- `POST /auth/register` - Registrar usuario (Solo ADMIN)

### Empresas
- `GET /empresas` - Listar empresas
- `GET /empresas/{nit}` - Obtener empresa
- `POST /empresas` - Crear empresa (ADMIN)
- `PUT /empresas/{nit}` - Actualizar empresa (ADMIN)
- `DELETE /empresas/{nit}` - Eliminar empresa (ADMIN)

### Productos
- `GET /productos` - Listar productos
- `POST /productos` - Crear producto (ADMIN)
- `PUT /productos/{id}` - Actualizar producto (ADMIN)
- `DELETE /productos/{id}` - Eliminar producto (ADMIN)

### Inventario
- `GET /inventario` - Listar inventario
- `GET /inventario/descargar-pdf` - Descargar PDF del inventario
- `POST /inventario/enviar-correo` - Enviar inventario por correo

### Clientes
- `GET /clientes` - Listar clientes
- `POST /clientes` - Crear cliente
- `PUT /clientes/{id}` - Actualizar cliente
- `DELETE /clientes/{id}` - Eliminar cliente

### Órdenes
- `GET /ordenes` - Listar órdenes
- `POST /ordenes` - Crear orden
- `PUT /ordenes/{id}` - Actualizar orden
- `DELETE /ordenes/{id}` - Eliminar orden

## Autenticación

La aplicación utiliza **JWT (JSON Web Tokens)** para autenticación.

### Flujo de Login
1. Usuario envía email y contraseña a `POST /auth/login`
2. Backend valida credenciales y genera JWT
3. Frontend almacena token en localStorage
4. Todas las peticiones incluyen el token en header: `Authorization: Bearer {token}`

### Roles
- **ADMIN**: Acceso completo a crear, editar y eliminar empresas y productos
- **EXTERNAL**: Solo visualización de empresas

## Características Principales

✅ **Autenticación y Autorización**
- Login con email y contraseña
- Contraseñas encriptadas con BCrypt
- JWT para sesiones

✅ **Gestión de Empresas**
- Crear, editar, eliminar empresas (ADMIN)
- Visualizar empresas (todos)

✅ **Gestión de Productos**
- Crear, editar, eliminar productos (ADMIN)
- Asignar múltiples categorías
- Precios en USD, EUR, COP

✅ **Inventario**
- Descargar PDF del inventario
- Enviar inventario por correo

✅ **Gestión de Clientes y Órdenes**
- CRUD de clientes
- CRUD de órdenes
- Relación de órdenes con múltiples productos

## Contribución

Fork el repositorio y crea un branch para tu feature.

```bash
git checkout -b feature/nueva-funcionalidad
git commit -am 'Add nueva funcionalidad'
git push origin feature/nueva-funcionalidad
```

## Licencia

MIT

CREATE TABLE empresas (
  nit           VARCHAR(20) PRIMARY KEY,
  nombre        VARCHAR(100) NOT NULL,
  direccion     VARCHAR(200),
  telefono      VARCHAR(30)
);

CREATE TABLE productos (
  id            SERIAL PRIMARY KEY,
  codigo        VARCHAR(50) UNIQUE NOT NULL,
  nombre        VARCHAR(100) NOT NULL,
  caracteristicas TEXT,
  precio_usd        NUMERIC(12,2) NOT NULL,
  precio_eur        NUMERIC(12,2) NOT NULL,
  precio_cop        NUMERIC(12,2) NOT NULL,
  empresa_nit   VARCHAR(20) NOT NULL REFERENCES empresas(nit)
);

CREATE TABLE categorias (
  id          SERIAL PRIMARY KEY,
  descripcion TEXT,
  nombre      VARCHAR(100) NOT NULL
);

CREATE TABLE producto_categoria (
  producto_id   INT NOT NULL REFERENCES productos(id),
  categoria_id  INT NOT NULL REFERENCES categorias(id),
  PRIMARY KEY (producto_id, categoria_id)
);

CREATE TABLE clientes (
  id          SERIAL PRIMARY KEY,
  nombre      VARCHAR(100) NOT NULL,
  email      VARCHAR(150) UNIQUE NOT NULL,
  telefono VARCHAR(30),
  direccion VARCHAR(200),
  fecha_creacion         TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE ordenes (
  id            SERIAL PRIMARY KEY,
  cliente_id    INT NOT NULL REFERENCES clientes(id),
  fecha_orden         TIMESTAMP NOT NULL DEFAULT NOW(),
  estado varchar(255),
  total	NUMERIC(12,2) NOT NULL
);

CREATE TABLE orden_producto (
  orden_id      INT NOT NULL REFERENCES ordenes(id),
  producto_id   INT NOT NULL REFERENCES productos(id),
  cantidad      INT NOT NULL,
  PRIMARY KEY (orden_id, producto_id)
);

CREATE TABLE usuarios (
  id            SERIAL PRIMARY KEY,
  email        VARCHAR(150) UNIQUE NOT NULL,
  password     VARCHAR(200) NOT NULL,
  activo       BOOLEAN,
  rol           VARCHAR(20) NOT NULL -- 'ADMIN' o 'EXTERNAL'
);

CREATE TABLE inventario (
  id            SERIAL PRIMARY KEY,
  empresa_nit   VARCHAR(20) NOT NULL REFERENCES empresas(nit),
  producto_id   INT NOT NULL REFERENCES productos(id),
  cantidad      INT NOT NULL
);

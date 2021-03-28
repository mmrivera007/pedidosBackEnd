
CREATE SEQUENCE public.seq_articulo;

CREATE TABLE public.articulo (
                id INTEGER NOT NULL DEFAULT nextval('public.seq_articulo'),
                codigo VARCHAR(128) NOT NULL,
                nombre VARCHAR(64) NOT NULL,
                precio DOUBLE PRECISION NOT NULL,
                CONSTRAINT pk_articulo PRIMARY KEY (id)
);
COMMENT ON TABLE public.articulo IS 'Almacena informaciob de articulos';
COMMENT ON COLUMN public.articulo.id IS 'Código secuencial';
COMMENT ON COLUMN public.articulo.codigo IS 'Código del articulo';
COMMENT ON COLUMN public.articulo.nombre IS 'Nombre del artículo';
COMMENT ON COLUMN public.articulo.precio IS 'Precio unitario del artículo';


ALTER SEQUENCE public.seq_articulo OWNED BY public.articulo.id;

CREATE UNIQUE INDEX uk_articulo_codigo
 ON public.articulo
 ( codigo );

CREATE SEQUENCE public.seq_cliente;

CREATE TABLE public.cliente (
                id INTEGER NOT NULL DEFAULT nextval('public.seq_cliente'),
                nombre VARCHAR(64) NOT NULL,
                apellido VARCHAR(64) NOT NULL,
                CONSTRAINT pk_cliente PRIMARY KEY (id)
);
COMMENT ON TABLE public.cliente IS 'Almaccena la información de los clientes.';
COMMENT ON COLUMN public.cliente.id IS 'Código secuencial.';
COMMENT ON COLUMN public.cliente.nombre IS 'Nombre del cliente';
COMMENT ON COLUMN public.cliente.apellido IS 'Apellido del cliente';


ALTER SEQUENCE public.seq_cliente OWNED BY public.cliente.id;

CREATE SEQUENCE public.seq_orden;

CREATE TABLE public.orden (
                id INTEGER NOT NULL DEFAULT nextval('public.seq_orden'),
                id_cliente INTEGER NOT NULL,
                fecha DATE NOT NULL,
                CONSTRAINT pk_orden PRIMARY KEY (id)
);
COMMENT ON TABLE public.orden IS 'Almacena información de las ordenes de los clientes';
COMMENT ON COLUMN public.orden.id IS 'Código secuencial';
COMMENT ON COLUMN public.orden.id_cliente IS 'Código del cliente';


ALTER SEQUENCE public.seq_orden OWNED BY public.orden.id;

CREATE SEQUENCE public.seq_detalle;

CREATE TABLE public.detalle (
                id INTEGER NOT NULL DEFAULT nextval('public.seq_detalle'),
                id_orden INTEGER NOT NULL,
                id_articulo INTEGER NOT NULL,
                CONSTRAINT pk_detalle PRIMARY KEY (id)
);
COMMENT ON TABLE public.detalle IS 'Almacena información del detalle de la orden';
COMMENT ON COLUMN public.detalle.id IS 'Código secuencial';
COMMENT ON COLUMN public.detalle.id_orden IS 'Código de la orden';
COMMENT ON COLUMN public.detalle.id_articulo IS 'Código del artículo';


ALTER SEQUENCE public.seq_detalle OWNED BY public.detalle.id;

ALTER TABLE public.detalle ADD CONSTRAINT fk_articulo_detalle
FOREIGN KEY (id_articulo)
REFERENCES public.articulo (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.orden ADD CONSTRAINT fk_cliente_orden
FOREIGN KEY (id_cliente)
REFERENCES public.cliente (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.detalle ADD CONSTRAINT fk_orden_detalle
FOREIGN KEY (id_orden)
REFERENCES public.orden (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
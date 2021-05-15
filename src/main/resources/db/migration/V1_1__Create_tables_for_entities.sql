CREATE TABLE public.resources
(
    id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    api_id character varying(255),
    name character varying(500),
    resource_url character varying(1000),
    language integer,
    category integer,

    CONSTRAINT resourses_pkey PRIMARY KEY (id)
);

CREATE TABLE public.content
(
    id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    resource_id integer,
    author character varying(255),
    title character varying(500),
    description character varying(1000),
    news_url character varying(1000),
    image_url character varying(1000),
    published_time TIMESTAMP,
    language integer,
    content character varying(5000),
    category integer,

    CONSTRAINT content_pkey PRIMARY KEY (id),

    CONSTRAINT content_resource_fk FOREIGN KEY (resource_id)
        REFERENCES public.resources(id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
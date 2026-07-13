--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5
-- Dumped by pg_dump version 17.5

-- Started on 2026-07-13 17:33:56

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 221 (class 1259 OID 34597)
-- Name: categorias_produtos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categorias_produtos (
    id bigint NOT NULL,
    nome character varying(100) NOT NULL,
    ativa boolean DEFAULT true NOT NULL
);


ALTER TABLE public.categorias_produtos OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 34596)
-- Name: categorias_produtos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categorias_produtos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categorias_produtos_id_seq OWNER TO postgres;

--
-- TOC entry 4968 (class 0 OID 0)
-- Dependencies: 220
-- Name: categorias_produtos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categorias_produtos_id_seq OWNED BY public.categorias_produtos.id;


--
-- TOC entry 231 (class 1259 OID 34690)
-- Name: fechamento_conta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fechamento_conta (
    id bigint NOT NULL,
    pedido_id bigint NOT NULL,
    subtotal numeric(10,2) NOT NULL,
    taxa_servico numeric(10,2) DEFAULT 0 NOT NULL,
    desconto numeric(10,2) DEFAULT 0 NOT NULL,
    total numeric(10,2) DEFAULT 0 NOT NULL,
    data_fechamento timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fechamento_conta_desconto_check CHECK ((desconto >= (0)::numeric)),
    CONSTRAINT fechamento_conta_subtotal_check CHECK ((subtotal >= (0)::numeric)),
    CONSTRAINT fechamento_conta_taxa_servico_check CHECK ((taxa_servico >= (0)::numeric)),
    CONSTRAINT fechamento_conta_total_check CHECK ((total >= (0)::numeric))
);


ALTER TABLE public.fechamento_conta OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 34689)
-- Name: fechamento_conta_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.fechamento_conta_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.fechamento_conta_id_seq OWNER TO postgres;

--
-- TOC entry 4969 (class 0 OID 0)
-- Dependencies: 230
-- Name: fechamento_conta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.fechamento_conta_id_seq OWNED BY public.fechamento_conta.id;


--
-- TOC entry 217 (class 1259 OID 34575)
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 34585)
-- Name: mesas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mesas (
    id bigint NOT NULL,
    numero integer NOT NULL,
    descricao character varying(100),
    capacidade integer DEFAULT 4 NOT NULL,
    status character varying(20) DEFAULT 'LIVRE'::character varying NOT NULL,
    CONSTRAINT mesas_status_check CHECK (((status)::text = ANY ((ARRAY['LIVRE'::character varying, 'OCUPADA'::character varying, 'RESERVADA'::character varying, 'INATIVA'::character varying])::text[])))
);


ALTER TABLE public.mesas OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 34584)
-- Name: mesas_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.mesas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.mesas_id_seq OWNER TO postgres;

--
-- TOC entry 4970 (class 0 OID 0)
-- Dependencies: 218
-- Name: mesas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.mesas_id_seq OWNED BY public.mesas.id;


--
-- TOC entry 229 (class 1259 OID 34671)
-- Name: pagamentos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pagamentos (
    id bigint NOT NULL,
    pedido_id bigint NOT NULL,
    valor numeric(10,2) NOT NULL,
    forma_pagamento character varying(30) NOT NULL,
    status character varying(30) DEFAULT 'PENDENTE'::character varying NOT NULL,
    codigo_transacao_externa character varying(100),
    data_pagamento timestamp without time zone,
    criado_em timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT pagamentos_forma_pagamento_check CHECK (((forma_pagamento)::text = ANY ((ARRAY['DINHEIRO'::character varying, 'CARTAO_CREDITO'::character varying, 'CARTAO_DEBITO'::character varying, 'PIX'::character varying])::text[]))),
    CONSTRAINT pagamentos_status_check CHECK (((status)::text = ANY ((ARRAY['PENDENTE'::character varying, 'APROVADO'::character varying, 'RECUSADO'::character varying, 'CANCELADO'::character varying])::text[]))),
    CONSTRAINT pagamentos_valor_check CHECK ((valor >= (0)::numeric))
);


ALTER TABLE public.pagamentos OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 34670)
-- Name: pagamentos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pagamentos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pagamentos_id_seq OWNER TO postgres;

--
-- TOC entry 4971 (class 0 OID 0)
-- Dependencies: 228
-- Name: pagamentos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pagamentos_id_seq OWNED BY public.pagamentos.id;


--
-- TOC entry 225 (class 1259 OID 34626)
-- Name: pedidos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pedidos (
    id bigint NOT NULL,
    mesa_id bigint NOT NULL,
    data_abertura timestamp without time zone,
    data_fechamento timestamp without time zone,
    status character varying(30) DEFAULT 'ABERTO'::character varying NOT NULL,
    observacao text,
    CONSTRAINT pedidos_status_check CHECK (((status)::text = ANY (ARRAY[('ABERTO'::character varying)::text, ('EM_PREPARO'::character varying)::text, ('PRONTO'::character varying)::text, ('ENTREGUE'::character varying)::text, ('FECHADO'::character varying)::text, ('CANCELADO'::character varying)::text])))
);


ALTER TABLE public.pedidos OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 34625)
-- Name: pedidos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pedidos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pedidos_id_seq OWNER TO postgres;

--
-- TOC entry 4972 (class 0 OID 0)
-- Dependencies: 224
-- Name: pedidos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pedidos_id_seq OWNED BY public.pedidos.id;


--
-- TOC entry 227 (class 1259 OID 34645)
-- Name: pedidos_itens; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pedidos_itens (
    id bigint NOT NULL,
    pedido_id bigint NOT NULL,
    produto_id bigint NOT NULL,
    quantidade integer NOT NULL,
    preco_unitario numeric(10,2) NOT NULL,
    observacao text,
    status character varying(30) DEFAULT 'PENDENTE'::character varying NOT NULL,
    data_inicio_preparo timestamp without time zone,
    data_pronto timestamp without time zone,
    data_entrega timestamp without time zone,
    CONSTRAINT pedidos_itens_preco_unitario_check CHECK ((preco_unitario >= (0)::numeric)),
    CONSTRAINT pedidos_itens_quantidade_check CHECK ((quantidade > 0)),
    CONSTRAINT pedidos_itens_status_check CHECK (((status)::text = ANY (ARRAY[('PENDENTE'::character varying)::text, ('EM_PREPARO'::character varying)::text, ('PRONTO'::character varying)::text, ('ENTREGUE'::character varying)::text, ('CANCELADO'::character varying)::text])))
);


ALTER TABLE public.pedidos_itens OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 34644)
-- Name: pedidos_itens_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pedidos_itens_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pedidos_itens_id_seq OWNER TO postgres;

--
-- TOC entry 4973 (class 0 OID 0)
-- Dependencies: 226
-- Name: pedidos_itens_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pedidos_itens_id_seq OWNED BY public.pedidos_itens.id;


--
-- TOC entry 223 (class 1259 OID 34607)
-- Name: produtos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.produtos (
    id bigint NOT NULL,
    categoria_id bigint NOT NULL,
    nome character varying(150) NOT NULL,
    descricao text,
    preco numeric(10,2) NOT NULL,
    disponivel boolean DEFAULT true NOT NULL,
    tempo_preparo_minutos integer,
    criado_em timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    atualizado_em timestamp without time zone,
    CONSTRAINT produtos_preco_check CHECK ((preco >= (0)::numeric))
);


ALTER TABLE public.produtos OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 34606)
-- Name: produtos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.produtos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.produtos_id_seq OWNER TO postgres;

--
-- TOC entry 4974 (class 0 OID 0)
-- Dependencies: 222
-- Name: produtos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.produtos_id_seq OWNED BY public.produtos.id;


--
-- TOC entry 4734 (class 2604 OID 34600)
-- Name: categorias_produtos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categorias_produtos ALTER COLUMN id SET DEFAULT nextval('public.categorias_produtos_id_seq'::regclass);


--
-- TOC entry 4746 (class 2604 OID 34693)
-- Name: fechamento_conta id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fechamento_conta ALTER COLUMN id SET DEFAULT nextval('public.fechamento_conta_id_seq'::regclass);


--
-- TOC entry 4731 (class 2604 OID 34588)
-- Name: mesas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mesas ALTER COLUMN id SET DEFAULT nextval('public.mesas_id_seq'::regclass);


--
-- TOC entry 4743 (class 2604 OID 34674)
-- Name: pagamentos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagamentos ALTER COLUMN id SET DEFAULT nextval('public.pagamentos_id_seq'::regclass);


--
-- TOC entry 4739 (class 2604 OID 34629)
-- Name: pedidos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidos ALTER COLUMN id SET DEFAULT nextval('public.pedidos_id_seq'::regclass);


--
-- TOC entry 4741 (class 2604 OID 34648)
-- Name: pedidos_itens id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidos_itens ALTER COLUMN id SET DEFAULT nextval('public.pedidos_itens_id_seq'::regclass);


--
-- TOC entry 4736 (class 2604 OID 34610)
-- Name: produtos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produtos ALTER COLUMN id SET DEFAULT nextval('public.produtos_id_seq'::regclass);


--
-- TOC entry 4952 (class 0 OID 34597)
-- Dependencies: 221
-- Data for Name: categorias_produtos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categorias_produtos (id, nome, ativa) FROM stdin;
1	Entradas	t
2	Pratos Principais	t
3	Bebidas	t
4	Sobremesas	t
\.


--
-- TOC entry 4962 (class 0 OID 34690)
-- Dependencies: 231
-- Data for Name: fechamento_conta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.fechamento_conta (id, pedido_id, subtotal, taxa_servico, desconto, total, data_fechamento) FROM stdin;
1	13	185.40	10.00	5.00	190.40	2026-07-13 15:11:26.309605
\.


--
-- TOC entry 4948 (class 0 OID 34575)
-- Dependencies: 217
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1	create restaurante schema	SQL	V1__create_restaurante_schema.sql	63121262	postgres	2026-07-09 14:40:52.950895	115	t
2	2	insert dados iniciais	SQL	V2__insert_dados_iniciais.sql	679932942	postgres	2026-07-09 14:40:53.150288	23	t
3	3	add datas preparo pedido itens	SQL	V3__add_datas_preparo_pedido_itens.sql	-1097918343	postgres	2026-07-13 17:24:23.886844	107	t
\.


--
-- TOC entry 4950 (class 0 OID 34585)
-- Dependencies: 219
-- Data for Name: mesas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.mesas (id, numero, descricao, capacidade, status) FROM stdin;
2	2	Mesa central	4	LIVRE
3	3	Mesa próxima à janela	4	LIVRE
4	4	Mesa família	6	LIVRE
5	5	Mesa externo	4	LIVRE
1	1	Mesa próxima à entrada	4	OCUPADA
\.


--
-- TOC entry 4960 (class 0 OID 34671)
-- Dependencies: 229
-- Data for Name: pagamentos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pagamentos (id, pedido_id, valor, forma_pagamento, status, codigo_transacao_externa, data_pagamento, criado_em) FROM stdin;
\.


--
-- TOC entry 4956 (class 0 OID 34626)
-- Dependencies: 225
-- Data for Name: pedidos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pedidos (id, mesa_id, data_abertura, data_fechamento, status, observacao) FROM stdin;
13	1	2026-07-13 14:53:49.4024	2026-07-13 15:11:26.289603	FECHADO	Cliente pediu atendimento rápido
\.


--
-- TOC entry 4958 (class 0 OID 34645)
-- Dependencies: 227
-- Data for Name: pedidos_itens; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pedidos_itens (id, pedido_id, produto_id, quantidade, preco_unitario, observacao, status, data_inicio_preparo, data_pronto, data_entrega) FROM stdin;
3	13	1	2	28.90	Sem cebola	ENTREGUE	\N	\N	\N
4	13	2	1	34.90	Bem passado	ENTREGUE	\N	\N	\N
5	13	1	2	28.90	Sem cebola	ENTREGUE	\N	\N	\N
6	13	2	1	34.90	Bem passado	ENTREGUE	\N	\N	\N
\.


--
-- TOC entry 4954 (class 0 OID 34607)
-- Dependencies: 223
-- Data for Name: produtos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.produtos (id, categoria_id, nome, descricao, preco, disponivel, tempo_preparo_minutos, criado_em, atualizado_em) FROM stdin;
2	2	Cheese Burger Artesanal	Hamburger artesanal com queijo e molho especial da casa	34.90	t	15	2026-07-09 14:40:53.166358	\N
3	2	Filé com Fritas	Filé grelhado, acompanhado de batatas fritas	59.90	t	30	2026-07-09 14:40:53.166358	\N
4	3	Suco natural	Suco natural da fruta	12.00	t	5	2026-07-09 14:40:53.166358	\N
5	3	Pudim	Pudim tradicional da casa	14.90	t	5	2026-07-09 14:40:53.166358	\N
6	2	Parmegiana de Frango	Filé de frango com molho de tomate, queijo e especiarias	49.90	t	35	2026-07-09 15:16:44.034698	\N
1	1	Batatas Fritas	Porção de bata frita crocante	28.90	t	15	2026-07-09 14:40:53.166358	2026-07-09 15:25:43.577569
\.


--
-- TOC entry 4975 (class 0 OID 0)
-- Dependencies: 220
-- Name: categorias_produtos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categorias_produtos_id_seq', 4, true);


--
-- TOC entry 4976 (class 0 OID 0)
-- Dependencies: 230
-- Name: fechamento_conta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.fechamento_conta_id_seq', 1, true);


--
-- TOC entry 4977 (class 0 OID 0)
-- Dependencies: 218
-- Name: mesas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.mesas_id_seq', 5, true);


--
-- TOC entry 4978 (class 0 OID 0)
-- Dependencies: 228
-- Name: pagamentos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pagamentos_id_seq', 1, false);


--
-- TOC entry 4979 (class 0 OID 0)
-- Dependencies: 224
-- Name: pedidos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pedidos_id_seq', 13, true);


--
-- TOC entry 4980 (class 0 OID 0)
-- Dependencies: 226
-- Name: pedidos_itens_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pedidos_itens_id_seq', 6, true);


--
-- TOC entry 4981 (class 0 OID 0)
-- Dependencies: 222
-- Name: produtos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.produtos_id_seq', 7, true);


--
-- TOC entry 4772 (class 2606 OID 34605)
-- Name: categorias_produtos categorias_produtos_nome_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categorias_produtos
    ADD CONSTRAINT categorias_produtos_nome_key UNIQUE (nome);


--
-- TOC entry 4774 (class 2606 OID 34603)
-- Name: categorias_produtos categorias_produtos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categorias_produtos
    ADD CONSTRAINT categorias_produtos_pkey PRIMARY KEY (id);


--
-- TOC entry 4794 (class 2606 OID 34705)
-- Name: fechamento_conta fechamento_conta_pedido_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fechamento_conta
    ADD CONSTRAINT fechamento_conta_pedido_id_key UNIQUE (pedido_id);


--
-- TOC entry 4796 (class 2606 OID 34703)
-- Name: fechamento_conta fechamento_conta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fechamento_conta
    ADD CONSTRAINT fechamento_conta_pkey PRIMARY KEY (id);


--
-- TOC entry 4765 (class 2606 OID 34582)
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- TOC entry 4768 (class 2606 OID 34595)
-- Name: mesas mesas_numero_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mesas
    ADD CONSTRAINT mesas_numero_key UNIQUE (numero);


--
-- TOC entry 4770 (class 2606 OID 34593)
-- Name: mesas mesas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mesas
    ADD CONSTRAINT mesas_pkey PRIMARY KEY (id);


--
-- TOC entry 4792 (class 2606 OID 34681)
-- Name: pagamentos pagamentos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagamentos
    ADD CONSTRAINT pagamentos_pkey PRIMARY KEY (id);


--
-- TOC entry 4788 (class 2606 OID 34656)
-- Name: pedidos_itens pedidos_itens_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidos_itens
    ADD CONSTRAINT pedidos_itens_pkey PRIMARY KEY (id);


--
-- TOC entry 4783 (class 2606 OID 34635)
-- Name: pedidos pedidos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidos
    ADD CONSTRAINT pedidos_pkey PRIMARY KEY (id);


--
-- TOC entry 4778 (class 2606 OID 34617)
-- Name: produtos produtos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produtos
    ADD CONSTRAINT produtos_pkey PRIMARY KEY (id);


--
-- TOC entry 4766 (class 1259 OID 34583)
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- TOC entry 4789 (class 1259 OID 34687)
-- Name: idx_pagamentos_pedido; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_pagamentos_pedido ON public.pagamentos USING btree (pedido_id);


--
-- TOC entry 4790 (class 1259 OID 34688)
-- Name: idx_pagamentos_status; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_pagamentos_status ON public.pagamentos USING btree (status);


--
-- TOC entry 4779 (class 1259 OID 34643)
-- Name: idx_pedidos_data_abertura; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_pedidos_data_abertura ON public.pedidos USING btree (data_abertura);


--
-- TOC entry 4784 (class 1259 OID 34667)
-- Name: idx_pedidos_items_pedido; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_pedidos_items_pedido ON public.pedidos_itens USING btree (pedido_id);


--
-- TOC entry 4785 (class 1259 OID 34668)
-- Name: idx_pedidos_items_produto; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_pedidos_items_produto ON public.pedidos_itens USING btree (produto_id);


--
-- TOC entry 4786 (class 1259 OID 34669)
-- Name: idx_pedidos_items_status; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_pedidos_items_status ON public.pedidos_itens USING btree (status);


--
-- TOC entry 4780 (class 1259 OID 34641)
-- Name: idx_pedidos_mesa; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_pedidos_mesa ON public.pedidos USING btree (mesa_id);


--
-- TOC entry 4781 (class 1259 OID 34642)
-- Name: idx_pedidos_status; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_pedidos_status ON public.pedidos USING btree (status);


--
-- TOC entry 4775 (class 1259 OID 34624)
-- Name: idx_produtos_categoria; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_produtos_categoria ON public.produtos USING btree (categoria_id);


--
-- TOC entry 4776 (class 1259 OID 34623)
-- Name: idx_produtos_nome; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_produtos_nome ON public.produtos USING btree (nome);


--
-- TOC entry 4802 (class 2606 OID 34706)
-- Name: fechamento_conta fechamento_conta_pedido_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fechamento_conta
    ADD CONSTRAINT fechamento_conta_pedido_id_fkey FOREIGN KEY (pedido_id) REFERENCES public.pedidos(id);


--
-- TOC entry 4801 (class 2606 OID 34682)
-- Name: pagamentos pagamentos_pedido_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagamentos
    ADD CONSTRAINT pagamentos_pedido_id_fkey FOREIGN KEY (pedido_id) REFERENCES public.pedidos(id);


--
-- TOC entry 4799 (class 2606 OID 34657)
-- Name: pedidos_itens pedidos_itens_pedido_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidos_itens
    ADD CONSTRAINT pedidos_itens_pedido_id_fkey FOREIGN KEY (pedido_id) REFERENCES public.pedidos(id);


--
-- TOC entry 4800 (class 2606 OID 34662)
-- Name: pedidos_itens pedidos_itens_produto_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidos_itens
    ADD CONSTRAINT pedidos_itens_produto_id_fkey FOREIGN KEY (produto_id) REFERENCES public.produtos(id);


--
-- TOC entry 4798 (class 2606 OID 34636)
-- Name: pedidos pedidos_mesa_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidos
    ADD CONSTRAINT pedidos_mesa_id_fkey FOREIGN KEY (mesa_id) REFERENCES public.mesas(id);


--
-- TOC entry 4797 (class 2606 OID 34618)
-- Name: produtos produtos_categoria_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produtos
    ADD CONSTRAINT produtos_categoria_id_fkey FOREIGN KEY (categoria_id) REFERENCES public.categorias_produtos(id);


-- Completed on 2026-07-13 17:33:57

--
-- PostgreSQL database dump complete
--


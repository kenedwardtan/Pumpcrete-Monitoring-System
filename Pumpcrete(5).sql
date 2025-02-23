PGDMP                         y         	   Pumpcrete    13.3    13.3 /    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    17658 	   Pumpcrete    DATABASE     o   CREATE DATABASE "Pumpcrete" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'English_United States.1252';
    DROP DATABASE "Pumpcrete";
             
   admin_role    false            �            1259    17659    billings    TABLE       CREATE TABLE public.billings (
    bill_no bigint NOT NULL,
    client_name character varying NOT NULL,
    project_name character varying NOT NULL,
    project_add character varying NOT NULL,
    date_doc date NOT NULL,
    date_used date NOT NULL,
    psc_id bigint NOT NULL,
    conc_structure character varying NOT NULL,
    floor_level bigint NOT NULL,
    qty numeric NOT NULL,
    unit_price numeric NOT NULL,
    total numeric NOT NULL,
    posted boolean NOT NULL,
    posted_by character varying,
    filled_by character varying
);
    DROP TABLE public.billings;
       public         heap    postgres    false            �           0    0    TABLE billings    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.billings TO staff_role;
GRANT SELECT,UPDATE ON TABLE public.billings TO supervisor_role;
          public          postgres    false    200            �            1259    17665    billings_bill_no_seq    SEQUENCE     }   CREATE SEQUENCE public.billings_bill_no_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.billings_bill_no_seq;
       public          postgres    false    200            �           0    0    billings_bill_no_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.billings_bill_no_seq OWNED BY public.billings.bill_no;
          public          postgres    false    201            �           0    0    SEQUENCE billings_bill_no_seq    ACL     �   GRANT SELECT,USAGE ON SEQUENCE public.billings_bill_no_seq TO staff_role;
GRANT SELECT,USAGE ON SEQUENCE public.billings_bill_no_seq TO supervisor_role;
GRANT SELECT,USAGE ON SEQUENCE public.billings_bill_no_seq TO admin_role;
          public          postgres    false    201            �            1259    17667    client    TABLE     �  CREATE TABLE public.client (
    client_id bigint NOT NULL,
    client_firstname character varying NOT NULL,
    client_position character varying NOT NULL,
    client_address character varying NOT NULL,
    client_email character varying NOT NULL,
    latest_doc_date date,
    client_landline character varying NOT NULL,
    client_cellphone character varying NOT NULL,
    client_lastname character varying NOT NULL
);
    DROP TABLE public.client;
       public         heap    postgres    false            �           0    0    TABLE client    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.client TO staff_role;
GRANT SELECT,UPDATE ON TABLE public.client TO supervisor_role;
          public          postgres    false    202            �            1259    17673    client_client_id_seq    SEQUENCE     }   CREATE SEQUENCE public.client_client_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.client_client_id_seq;
       public          postgres    false    202            �           0    0    client_client_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.client_client_id_seq OWNED BY public.client.client_id;
          public          postgres    false    203            �           0    0    SEQUENCE client_client_id_seq    ACL     �   GRANT SELECT,USAGE ON SEQUENCE public.client_client_id_seq TO staff_role;
GRANT SELECT,USAGE ON SEQUENCE public.client_client_id_seq TO supervisor_role;
GRANT SELECT,USAGE ON SEQUENCE public.client_client_id_seq TO admin_role;
          public          postgres    false    203            �            1259    17675    credit_memo    TABLE     %   CREATE TABLE public.credit_memo (
);
    DROP TABLE public.credit_memo;
       public         heap    postgres    false            �           0    0    TABLE credit_memo    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.credit_memo TO staff_role;
GRANT SELECT,UPDATE ON TABLE public.credit_memo TO supervisor_role;
          public          postgres    false    204            �            1259    17678 	   pumpcrete    TABLE     d  CREATE TABLE public.pumpcrete (
    pumpcrete_id bigint NOT NULL,
    description character varying NOT NULL,
    plate_no character varying NOT NULL,
    fuel_type character varying NOT NULL,
    purchase_date date NOT NULL,
    cr_no bigint,
    or_no bigint,
    tires integer NOT NULL,
    rented boolean NOT NULL,
    client_name character varying
);
    DROP TABLE public.pumpcrete;
       public         heap    postgres    false            �            1259    17684    pumpcrete_pumpcrete_id_seq    SEQUENCE     �   CREATE SEQUENCE public.pumpcrete_pumpcrete_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public.pumpcrete_pumpcrete_id_seq;
       public          postgres    false    205            �           0    0    pumpcrete_pumpcrete_id_seq    SEQUENCE OWNED BY     Y   ALTER SEQUENCE public.pumpcrete_pumpcrete_id_seq OWNED BY public.pumpcrete.pumpcrete_id;
          public          postgres    false    206            �           0    0 #   SEQUENCE pumpcrete_pumpcrete_id_seq    ACL     �   GRANT SELECT,USAGE ON SEQUENCE public.pumpcrete_pumpcrete_id_seq TO staff_role;
GRANT SELECT,USAGE ON SEQUENCE public.pumpcrete_pumpcrete_id_seq TO supervisor_role;
GRANT SELECT,USAGE ON SEQUENCE public.pumpcrete_pumpcrete_id_seq TO admin_role;
          public          postgres    false    206            �            1259    17686 
   pumpcretes    TABLE     $   CREATE TABLE public.pumpcretes (
);
    DROP TABLE public.pumpcretes;
       public         heap    postgres    false            �           0    0    TABLE pumpcretes    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.pumpcretes TO staff_role;
GRANT SELECT,UPDATE ON TABLE public.pumpcretes TO supervisor_role;
          public          postgres    false    207            �            1259    17689    users    TABLE     D  CREATE TABLE public.users (
    username character varying(50) NOT NULL,
    email character varying(150) NOT NULL,
    first_name character varying NOT NULL,
    last_name character varying NOT NULL,
    password character varying(50) NOT NULL,
    role character varying(50) NOT NULL,
    middle_name character varying
);
    DROP TABLE public.users;
       public         heap    postgres    false            �           0    0    TABLE users    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.users TO admin_role;
GRANT SELECT,UPDATE ON TABLE public.users TO staff_role;
GRANT SELECT,UPDATE ON TABLE public.users TO supervisor_role;
          public          postgres    false    208            >           2604    17695    billings bill_no    DEFAULT     t   ALTER TABLE ONLY public.billings ALTER COLUMN bill_no SET DEFAULT nextval('public.billings_bill_no_seq'::regclass);
 ?   ALTER TABLE public.billings ALTER COLUMN bill_no DROP DEFAULT;
       public          postgres    false    201    200            ?           2604    17696    client client_id    DEFAULT     t   ALTER TABLE ONLY public.client ALTER COLUMN client_id SET DEFAULT nextval('public.client_client_id_seq'::regclass);
 ?   ALTER TABLE public.client ALTER COLUMN client_id DROP DEFAULT;
       public          postgres    false    203    202            @           2604    17697    pumpcrete pumpcrete_id    DEFAULT     �   ALTER TABLE ONLY public.pumpcrete ALTER COLUMN pumpcrete_id SET DEFAULT nextval('public.pumpcrete_pumpcrete_id_seq'::regclass);
 E   ALTER TABLE public.pumpcrete ALTER COLUMN pumpcrete_id DROP DEFAULT;
       public          postgres    false    206    205            �          0    17659    billings 
   TABLE DATA           �   COPY public.billings (bill_no, client_name, project_name, project_add, date_doc, date_used, psc_id, conc_structure, floor_level, qty, unit_price, total, posted, posted_by, filled_by) FROM stdin;
    public          postgres    false    200   9       �          0    17667    client 
   TABLE DATA           �   COPY public.client (client_id, client_firstname, client_position, client_address, client_email, latest_doc_date, client_landline, client_cellphone, client_lastname) FROM stdin;
    public          postgres    false    202   �9       �          0    17675    credit_memo 
   TABLE DATA           %   COPY public.credit_memo  FROM stdin;
    public          postgres    false    204   %:       �          0    17678 	   pumpcrete 
   TABLE DATA           �   COPY public.pumpcrete (pumpcrete_id, description, plate_no, fuel_type, purchase_date, cr_no, or_no, tires, rented, client_name) FROM stdin;
    public          postgres    false    205   B:       �          0    17686 
   pumpcretes 
   TABLE DATA           $   COPY public.pumpcretes  FROM stdin;
    public          postgres    false    207   �:       �          0    17689    users 
   TABLE DATA           d   COPY public.users (username, email, first_name, last_name, password, role, middle_name) FROM stdin;
    public          postgres    false    208   �:       �           0    0    billings_bill_no_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.billings_bill_no_seq', 11, true);
          public          postgres    false    201            �           0    0    client_client_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.client_client_id_seq', 17, true);
          public          postgres    false    203            �           0    0    pumpcrete_pumpcrete_id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.pumpcrete_pumpcrete_id_seq', 2, true);
          public          postgres    false    206            B           2606    17699    billings billings_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public.billings
    ADD CONSTRAINT billings_pkey PRIMARY KEY (bill_no);
 @   ALTER TABLE ONLY public.billings DROP CONSTRAINT billings_pkey;
       public            postgres    false    200            D           2606    17701    billings billings_psc_id_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.billings
    ADD CONSTRAINT billings_psc_id_key UNIQUE (psc_id);
 F   ALTER TABLE ONLY public.billings DROP CONSTRAINT billings_psc_id_key;
       public            postgres    false    200            F           2606    17703    client client_client_name_key 
   CONSTRAINT     d   ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_client_name_key UNIQUE (client_firstname);
 G   ALTER TABLE ONLY public.client DROP CONSTRAINT client_client_name_key;
       public            postgres    false    202            H           2606    17705    client client_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (client_id);
 <   ALTER TABLE ONLY public.client DROP CONSTRAINT client_pkey;
       public            postgres    false    202            J           2606    17707    pumpcrete pumpcrete_cr_no_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.pumpcrete
    ADD CONSTRAINT pumpcrete_cr_no_key UNIQUE (cr_no);
 G   ALTER TABLE ONLY public.pumpcrete DROP CONSTRAINT pumpcrete_cr_no_key;
       public            postgres    false    205            L           2606    17709 #   pumpcrete pumpcrete_description_key 
   CONSTRAINT     e   ALTER TABLE ONLY public.pumpcrete
    ADD CONSTRAINT pumpcrete_description_key UNIQUE (description);
 M   ALTER TABLE ONLY public.pumpcrete DROP CONSTRAINT pumpcrete_description_key;
       public            postgres    false    205            N           2606    17711    pumpcrete pumpcrete_or_no_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.pumpcrete
    ADD CONSTRAINT pumpcrete_or_no_key UNIQUE (or_no);
 G   ALTER TABLE ONLY public.pumpcrete DROP CONSTRAINT pumpcrete_or_no_key;
       public            postgres    false    205            P           2606    17713    pumpcrete pumpcrete_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.pumpcrete
    ADD CONSTRAINT pumpcrete_pkey PRIMARY KEY (pumpcrete_id);
 B   ALTER TABLE ONLY public.pumpcrete DROP CONSTRAINT pumpcrete_pkey;
       public            postgres    false    205            R           2606    17715     pumpcrete pumpcrete_plate_no_key 
   CONSTRAINT     _   ALTER TABLE ONLY public.pumpcrete
    ADD CONSTRAINT pumpcrete_plate_no_key UNIQUE (plate_no);
 J   ALTER TABLE ONLY public.pumpcrete DROP CONSTRAINT pumpcrete_plate_no_key;
       public            postgres    false    205            T           2606    17717    users users_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (username);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    208            U           2606    17723     billings billings_posted_by_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.billings
    ADD CONSTRAINT billings_posted_by_fkey FOREIGN KEY (posted_by) REFERENCES public.users(username);
 J   ALTER TABLE ONLY public.billings DROP CONSTRAINT billings_posted_by_fkey;
       public          postgres    false    208    2900    200            �   �   x��ͻ
�0���S�*I���6v�`�Y�"�A�F.}���#s��	H�l�;�W�����/�fYqU
a�@�,'9���Nc匯�7Ӆ A����0>���Q�Wc�U7�����lf��YH4�t�_?�����~��t��|K���2A�s�d8      �   L   x�34�L��L�+Q(N�-�I�,�/6DR�+�MJ-R�	;@����s9c�8��ML��-9,�LKN��=... �'p      �      x������ � �      �   d   x�3�tI-N.�,(���S0�ttr642�t�L-N���u@�-8�8c���Pty�n&�f0]FP]F@�	��%��
�9��y%
~���
�\1z\\\ ��      �      x������ � �      �   z   x�]�;
�0D�݋؉���l�
�h��1!����U����d˻��0�B�d���SRϖ̒΂J!��7O���E�Y_����D~5R�A^q�MS�&B}��%�����ta����@I���/ O�     
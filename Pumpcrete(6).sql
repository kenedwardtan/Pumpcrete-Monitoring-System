PGDMP          !                y         	   Pumpcrete    13.3    13.3 5    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16454 	   Pumpcrete    DATABASE     o   CREATE DATABASE "Pumpcrete" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'English_United States.1252';
    DROP DATABASE "Pumpcrete";
                postgres    false            �            1259    19284    billings    TABLE       CREATE TABLE public.billings (
    bill_no bigint NOT NULL,
    client_id bigint NOT NULL,
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
    filled_by character varying,
    posted_by character varying
);
    DROP TABLE public.billings;
       public         heap    postgres    false            �           0    0    TABLE billings    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.billings TO staff_role;
GRANT SELECT,UPDATE ON TABLE public.billings TO supervisor_role;
          public          postgres    false    204            �            1259    19282    billings_bill_no_seq    SEQUENCE     }   CREATE SEQUENCE public.billings_bill_no_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.billings_bill_no_seq;
       public          postgres    false    204            �           0    0    billings_bill_no_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.billings_bill_no_seq OWNED BY public.billings.bill_no;
          public          postgres    false    203            �           0    0    SEQUENCE billings_bill_no_seq    ACL     �   GRANT SELECT,USAGE ON SEQUENCE public.billings_bill_no_seq TO staff_role;
GRANT SELECT,USAGE ON SEQUENCE public.billings_bill_no_seq TO supervisor_role;
GRANT SELECT,USAGE ON SEQUENCE public.billings_bill_no_seq TO admin_role;
          public          postgres    false    203            �            1259    19272    client    TABLE     �  CREATE TABLE public.client (
    client_id bigint NOT NULL,
    client_first_name character varying NOT NULL,
    client_last_name character varying NOT NULL,
    client_position character varying NOT NULL,
    client_address character varying NOT NULL,
    client_landline integer NOT NULL,
    client_cellphone bigint NOT NULL,
    client_email character varying NOT NULL,
    latest_doc_date date
);
    DROP TABLE public.client;
       public         heap    postgres    false            �           0    0    TABLE client    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.client TO staff_role;
GRANT SELECT,UPDATE ON TABLE public.client TO supervisor_role;
          public          postgres    false    202            �            1259    19270    client_client_id_seq    SEQUENCE     }   CREATE SEQUENCE public.client_client_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.client_client_id_seq;
       public          postgres    false    202            �           0    0    client_client_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.client_client_id_seq OWNED BY public.client.client_id;
          public          postgres    false    201            �           0    0    SEQUENCE client_client_id_seq    ACL     �   GRANT SELECT,USAGE ON SEQUENCE public.client_client_id_seq TO staff_role;
GRANT SELECT,USAGE ON SEQUENCE public.client_client_id_seq TO supervisor_role;
GRANT SELECT,USAGE ON SEQUENCE public.client_client_id_seq TO admin_role;
          public          postgres    false    201            �            1259    19327    collections    TABLE     �  CREATE TABLE public.collections (
    collection_ref bigint NOT NULL,
    date date NOT NULL,
    collection_no bigint NOT NULL,
    client_id bigint NOT NULL,
    bill_no bigint NOT NULL,
    pumpcrete_id bigint NOT NULL,
    posted boolean NOT NULL,
    grand_total numeric NOT NULL,
    check_number bigint NOT NULL,
    check_date date NOT NULL,
    bank character varying NOT NULL
);
    DROP TABLE public.collections;
       public         heap    postgres    false            �           0    0    TABLE collections    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.collections TO staff_role;
GRANT SELECT,UPDATE ON TABLE public.collections TO supervisor_role;
          public          postgres    false    208            �            1259    19325    collections_collection_ref_seq    SEQUENCE     �   CREATE SEQUENCE public.collections_collection_ref_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 5   DROP SEQUENCE public.collections_collection_ref_seq;
       public          postgres    false    208            �           0    0    collections_collection_ref_seq    SEQUENCE OWNED BY     a   ALTER SEQUENCE public.collections_collection_ref_seq OWNED BY public.collections.collection_ref;
          public          postgres    false    207            �           0    0 '   SEQUENCE collections_collection_ref_seq    ACL       GRANT SELECT,USAGE ON SEQUENCE public.collections_collection_ref_seq TO staff_role;
GRANT SELECT,USAGE ON SEQUENCE public.collections_collection_ref_seq TO supervisor_role;
GRANT SELECT,USAGE ON SEQUENCE public.collections_collection_ref_seq TO admin_role;
          public          postgres    false    207            �            1259    19309 	   pumpcrete    TABLE     d  CREATE TABLE public.pumpcrete (
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
       public         heap    postgres    false            �           0    0    TABLE pumpcrete    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.pumpcrete TO staff_role;
GRANT SELECT,UPDATE ON TABLE public.pumpcrete TO supervisor_role;
          public          postgres    false    206            �            1259    19307    pumpcrete_pumpcrete_id_seq    SEQUENCE     �   CREATE SEQUENCE public.pumpcrete_pumpcrete_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public.pumpcrete_pumpcrete_id_seq;
       public          postgres    false    206            �           0    0    pumpcrete_pumpcrete_id_seq    SEQUENCE OWNED BY     Y   ALTER SEQUENCE public.pumpcrete_pumpcrete_id_seq OWNED BY public.pumpcrete.pumpcrete_id;
          public          postgres    false    205            �           0    0 #   SEQUENCE pumpcrete_pumpcrete_id_seq    ACL     �   GRANT SELECT,USAGE ON SEQUENCE public.pumpcrete_pumpcrete_id_seq TO staff_role;
GRANT SELECT,USAGE ON SEQUENCE public.pumpcrete_pumpcrete_id_seq TO supervisor_role;
GRANT SELECT,USAGE ON SEQUENCE public.pumpcrete_pumpcrete_id_seq TO admin_role;
          public          postgres    false    205            �            1259    19262    users    TABLE     <  CREATE TABLE public.users (
    username character varying NOT NULL,
    email character varying NOT NULL,
    first_name character varying NOT NULL,
    middle_name character varying NOT NULL,
    last_name character varying NOT NULL,
    role character varying NOT NULL,
    password character varying NOT NULL
);
    DROP TABLE public.users;
       public         heap    postgres    false            �           0    0    TABLE users    ACL     �   GRANT SELECT,INSERT,UPDATE ON TABLE public.users TO admin_role;
GRANT SELECT,UPDATE ON TABLE public.users TO supervisor_role;
GRANT SELECT,UPDATE ON TABLE public.users TO staff_role;
          public          postgres    false    200            >           2604    19287    billings bill_no    DEFAULT     t   ALTER TABLE ONLY public.billings ALTER COLUMN bill_no SET DEFAULT nextval('public.billings_bill_no_seq'::regclass);
 ?   ALTER TABLE public.billings ALTER COLUMN bill_no DROP DEFAULT;
       public          postgres    false    203    204    204            =           2604    19275    client client_id    DEFAULT     t   ALTER TABLE ONLY public.client ALTER COLUMN client_id SET DEFAULT nextval('public.client_client_id_seq'::regclass);
 ?   ALTER TABLE public.client ALTER COLUMN client_id DROP DEFAULT;
       public          postgres    false    202    201    202            @           2604    19330    collections collection_ref    DEFAULT     �   ALTER TABLE ONLY public.collections ALTER COLUMN collection_ref SET DEFAULT nextval('public.collections_collection_ref_seq'::regclass);
 I   ALTER TABLE public.collections ALTER COLUMN collection_ref DROP DEFAULT;
       public          postgres    false    208    207    208            ?           2604    19312    pumpcrete pumpcrete_id    DEFAULT     �   ALTER TABLE ONLY public.pumpcrete ALTER COLUMN pumpcrete_id SET DEFAULT nextval('public.pumpcrete_pumpcrete_id_seq'::regclass);
 E   ALTER TABLE public.pumpcrete ALTER COLUMN pumpcrete_id DROP DEFAULT;
       public          postgres    false    206    205    206            �          0    19284    billings 
   TABLE DATA           �   COPY public.billings (bill_no, client_id, project_name, project_add, date_doc, date_used, psc_id, conc_structure, floor_level, qty, unit_price, total, posted, filled_by, posted_by) FROM stdin;
    public          postgres    false    204   �D       �          0    19272    client 
   TABLE DATA           �   COPY public.client (client_id, client_first_name, client_last_name, client_position, client_address, client_landline, client_cellphone, client_email, latest_doc_date) FROM stdin;
    public          postgres    false    202   �E       �          0    19327    collections 
   TABLE DATA           �   COPY public.collections (collection_ref, date, collection_no, client_id, bill_no, pumpcrete_id, posted, grand_total, check_number, check_date, bank) FROM stdin;
    public          postgres    false    208   %F       �          0    19309 	   pumpcrete 
   TABLE DATA           �   COPY public.pumpcrete (pumpcrete_id, description, plate_no, fuel_type, purchase_date, cr_no, or_no, tires, rented, client_name) FROM stdin;
    public          postgres    false    206   �F       �          0    19262    users 
   TABLE DATA           d   COPY public.users (username, email, first_name, middle_name, last_name, role, password) FROM stdin;
    public          postgres    false    200   G       �           0    0    billings_bill_no_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.billings_bill_no_seq', 4, true);
          public          postgres    false    203            �           0    0    client_client_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.client_client_id_seq', 3, true);
          public          postgres    false    201            �           0    0    collections_collection_ref_seq    SEQUENCE SET     L   SELECT pg_catalog.setval('public.collections_collection_ref_seq', 3, true);
          public          postgres    false    207            �           0    0    pumpcrete_pumpcrete_id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.pumpcrete_pumpcrete_id_seq', 3, true);
          public          postgres    false    205            F           2606    19292    billings billings_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public.billings
    ADD CONSTRAINT billings_pkey PRIMARY KEY (bill_no);
 @   ALTER TABLE ONLY public.billings DROP CONSTRAINT billings_pkey;
       public            postgres    false    204            H           2606    19294    billings billings_psc_id_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.billings
    ADD CONSTRAINT billings_psc_id_key UNIQUE (psc_id);
 F   ALTER TABLE ONLY public.billings DROP CONSTRAINT billings_psc_id_key;
       public            postgres    false    204            D           2606    19280    client client_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (client_id);
 <   ALTER TABLE ONLY public.client DROP CONSTRAINT client_pkey;
       public            postgres    false    202            R           2606    19335    collections collections_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.collections
    ADD CONSTRAINT collections_pkey PRIMARY KEY (collection_ref);
 F   ALTER TABLE ONLY public.collections DROP CONSTRAINT collections_pkey;
       public            postgres    false    208            J           2606    19321    pumpcrete pumpcrete_cr_no_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.pumpcrete
    ADD CONSTRAINT pumpcrete_cr_no_key UNIQUE (cr_no);
 G   ALTER TABLE ONLY public.pumpcrete DROP CONSTRAINT pumpcrete_cr_no_key;
       public            postgres    false    206            L           2606    19323    pumpcrete pumpcrete_or_no_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.pumpcrete
    ADD CONSTRAINT pumpcrete_or_no_key UNIQUE (or_no);
 G   ALTER TABLE ONLY public.pumpcrete DROP CONSTRAINT pumpcrete_or_no_key;
       public            postgres    false    206            N           2606    19317    pumpcrete pumpcrete_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.pumpcrete
    ADD CONSTRAINT pumpcrete_pkey PRIMARY KEY (pumpcrete_id);
 B   ALTER TABLE ONLY public.pumpcrete DROP CONSTRAINT pumpcrete_pkey;
       public            postgres    false    206            P           2606    19319     pumpcrete pumpcrete_plate_no_key 
   CONSTRAINT     _   ALTER TABLE ONLY public.pumpcrete
    ADD CONSTRAINT pumpcrete_plate_no_key UNIQUE (plate_no);
 J   ALTER TABLE ONLY public.pumpcrete DROP CONSTRAINT pumpcrete_plate_no_key;
       public            postgres    false    206            B           2606    19269    users users_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (username);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    200            S           2606    19295     billings billings_filled_by_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.billings
    ADD CONSTRAINT billings_filled_by_fkey FOREIGN KEY (filled_by) REFERENCES public.users(username);
 J   ALTER TABLE ONLY public.billings DROP CONSTRAINT billings_filled_by_fkey;
       public          postgres    false    200    2882    204            T           2606    19300     billings billings_posted_by_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.billings
    ADD CONSTRAINT billings_posted_by_fkey FOREIGN KEY (posted_by) REFERENCES public.users(username);
 J   ALTER TABLE ONLY public.billings DROP CONSTRAINT billings_posted_by_fkey;
       public          postgres    false    200    204    2882            V           2606    19341 $   collections collections_bill_no_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.collections
    ADD CONSTRAINT collections_bill_no_fkey FOREIGN KEY (bill_no) REFERENCES public.billings(bill_no);
 N   ALTER TABLE ONLY public.collections DROP CONSTRAINT collections_bill_no_fkey;
       public          postgres    false    208    2886    204            U           2606    19336 &   collections collections_client_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.collections
    ADD CONSTRAINT collections_client_id_fkey FOREIGN KEY (client_id) REFERENCES public.client(client_id);
 P   ALTER TABLE ONLY public.collections DROP CONSTRAINT collections_client_id_fkey;
       public          postgres    false    202    2884    208            W           2606    19346 )   collections collections_pumpcrete_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.collections
    ADD CONSTRAINT collections_pumpcrete_id_fkey FOREIGN KEY (pumpcrete_id) REFERENCES public.pumpcrete(pumpcrete_id);
 S   ALTER TABLE ONLY public.collections DROP CONSTRAINT collections_pumpcrete_id_fkey;
       public          postgres    false    2894    208    206            �   �   x���M
�0�����%�����
RJ
�ڒ�o�j�*��=����p�i*��� C�c�l'���v�*g���᷅�gca|ˁu�<_��X��_�y��b� ��L���7K2��"�"V&���%Y��")ce��NY�e�|i!o���      �   ~   x�e�A
�0�������t_u_z�nDC	��c!"��bxBm���.�o+�O�nsaI����Ƙ�(r�|���n���I�G�+uKOK���0���7)�/N��<s7ˤ�f�=�@�      �   M   x�3�4202�5 !N4�L�450�30�4dN�y�
�\FP1# k0�4j24��ˈ����ǐ�.c�=... :��      �      x�3�tI-N.�,(���S0�ttr642�t�L-N���u@�-8�8c���Pty�n&�f0]FP]F@�	��%��
�9��y%
~���
�\�(Fs�{x�[X0F6�AF8�a����� ��/�      �   i   x�U�M
� F�z�P��1���)�u�p,���7h�!�`����X�{�df&�q���] D�wP�1�cmTӇ�kM�7�����'	�ڜyC;z_��O�     
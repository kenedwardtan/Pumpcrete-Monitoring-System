PGDMP                 
        y         	   Pumpcrete    13.3    13.3 7    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    17658 	   Pumpcrete    DATABASE     o   CREATE DATABASE "Pumpcrete" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'English_United States.1252';
    DROP DATABASE "Pumpcrete";
             
   admin_role    false            �            1259    18231    billings    TABLE     g  CREATE TABLE public.billings (
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
    in_payments boolean NOT NULL,
    is_paid boolean NOT NULL,
    edited_by character varying NOT NULL,
    posted_by character varying
);
    DROP TABLE public.billings;
       public         heap    postgres    false            �           0    0    TABLE billings    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.billings TO staff_role;
GRANT SELECT,UPDATE ON TABLE public.billings TO supervisor_role;
          public          postgres    false    205            �            1259    18229    billings_bill_no_seq    SEQUENCE     }   CREATE SEQUENCE public.billings_bill_no_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.billings_bill_no_seq;
       public          postgres    false    205            �           0    0    billings_bill_no_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.billings_bill_no_seq OWNED BY public.billings.bill_no;
          public          postgres    false    204            �           0    0    SEQUENCE billings_bill_no_seq    ACL     �   GRANT SELECT,USAGE ON SEQUENCE public.billings_bill_no_seq TO staff_role;
GRANT SELECT,USAGE ON SEQUENCE public.billings_bill_no_seq TO supervisor_role;
GRANT SELECT,USAGE ON SEQUENCE public.billings_bill_no_seq TO admin_role;
          public          postgres    false    204            �            1259    18337    client    TABLE     �  CREATE TABLE public.client (
    client_id bigint NOT NULL,
    client_first_name character varying NOT NULL,
    client_last_name character varying NOT NULL,
    client_position character varying NOT NULL,
    client_address character varying NOT NULL,
    client_landline character varying NOT NULL,
    client_cellphone character varying NOT NULL,
    client_email character varying NOT NULL,
    latest_doc_date date
);
    DROP TABLE public.client;
       public         heap    postgres    false            �           0    0    TABLE client    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.client TO staff_role;
GRANT SELECT,UPDATE ON TABLE public.client TO supervisor_role;
          public          postgres    false    209            �            1259    18335    client_client_id_seq    SEQUENCE     }   CREATE SEQUENCE public.client_client_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.client_client_id_seq;
       public          postgres    false    209            �           0    0    client_client_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.client_client_id_seq OWNED BY public.client.client_id;
          public          postgres    false    208            �           0    0    SEQUENCE client_client_id_seq    ACL     �   GRANT SELECT,USAGE ON SEQUENCE public.client_client_id_seq TO staff_role;
GRANT SELECT,USAGE ON SEQUENCE public.client_client_id_seq TO supervisor_role;
GRANT SELECT,USAGE ON SEQUENCE public.client_client_id_seq TO admin_role;
          public          postgres    false    208            �            1259    18315    collections    TABLE     �  CREATE TABLE public.collections (
    collection_no bigint NOT NULL,
    date date NOT NULL,
    client_name character varying NOT NULL,
    bill_no character varying NOT NULL,
    posted boolean NOT NULL,
    grand_total numeric NOT NULL,
    check_number bigint NOT NULL,
    check_date date NOT NULL,
    bank character varying NOT NULL,
    edited_by character varying NOT NULL,
    posted_by character varying
);
    DROP TABLE public.collections;
       public         heap    postgres    false            �           0    0    TABLE collections    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.collections TO staff_role;
GRANT SELECT,UPDATE ON TABLE public.collections TO supervisor_role;
          public          postgres    false    207            �            1259    18313    collections_collection_no_seq    SEQUENCE     �   CREATE SEQUENCE public.collections_collection_no_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 4   DROP SEQUENCE public.collections_collection_no_seq;
       public          postgres    false    207            �           0    0    collections_collection_no_seq    SEQUENCE OWNED BY     _   ALTER SEQUENCE public.collections_collection_no_seq OWNED BY public.collections.collection_no;
          public          postgres    false    206            �           0    0 &   SEQUENCE collections_collection_no_seq    ACL     �   GRANT SELECT,USAGE ON SEQUENCE public.collections_collection_no_seq TO staff_role;
GRANT SELECT,USAGE ON SEQUENCE public.collections_collection_no_seq TO supervisor_role;
GRANT SELECT,USAGE ON SEQUENCE public.collections_collection_no_seq TO admin_role;
          public          postgres    false    206            �            1259    17675    credit_memo    TABLE     %   CREATE TABLE public.credit_memo (
);
    DROP TABLE public.credit_memo;
       public         heap    postgres    false            �           0    0    TABLE credit_memo    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.credit_memo TO staff_role;
GRANT SELECT,UPDATE ON TABLE public.credit_memo TO supervisor_role;
          public          postgres    false    200            �            1259    18189 	   pumpcrete    TABLE     $  CREATE TABLE public.pumpcrete (
    pumpcrete_id bigint NOT NULL,
    description character varying NOT NULL,
    plate_no character varying NOT NULL,
    fuel_type character varying NOT NULL,
    purchase_date date NOT NULL,
    cr_no bigint,
    or_no bigint,
    tires integer NOT NULL
);
    DROP TABLE public.pumpcrete;
       public         heap    postgres    false            �           0    0    TABLE pumpcrete    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.pumpcrete TO staff_role;
GRANT SELECT,UPDATE ON TABLE public.pumpcrete TO supervisor_role;
          public          postgres    false    203            �            1259    18187    pumpcrete_pumpcrete_id_seq    SEQUENCE     �   CREATE SEQUENCE public.pumpcrete_pumpcrete_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public.pumpcrete_pumpcrete_id_seq;
       public          postgres    false    203            �           0    0    pumpcrete_pumpcrete_id_seq    SEQUENCE OWNED BY     Y   ALTER SEQUENCE public.pumpcrete_pumpcrete_id_seq OWNED BY public.pumpcrete.pumpcrete_id;
          public          postgres    false    202            �           0    0 #   SEQUENCE pumpcrete_pumpcrete_id_seq    ACL     �   GRANT SELECT,USAGE ON SEQUENCE public.pumpcrete_pumpcrete_id_seq TO staff_role;
GRANT SELECT,USAGE ON SEQUENCE public.pumpcrete_pumpcrete_id_seq TO supervisor_role;
GRANT SELECT,USAGE ON SEQUENCE public.pumpcrete_pumpcrete_id_seq TO admin_role;
          public          postgres    false    202            �            1259    17689    users    TABLE     D  CREATE TABLE public.users (
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
          public          postgres    false    201            B           2604    18234    billings bill_no    DEFAULT     t   ALTER TABLE ONLY public.billings ALTER COLUMN bill_no SET DEFAULT nextval('public.billings_bill_no_seq'::regclass);
 ?   ALTER TABLE public.billings ALTER COLUMN bill_no DROP DEFAULT;
       public          postgres    false    204    205    205            D           2604    18340    client client_id    DEFAULT     t   ALTER TABLE ONLY public.client ALTER COLUMN client_id SET DEFAULT nextval('public.client_client_id_seq'::regclass);
 ?   ALTER TABLE public.client ALTER COLUMN client_id DROP DEFAULT;
       public          postgres    false    208    209    209            C           2604    18318    collections collection_no    DEFAULT     �   ALTER TABLE ONLY public.collections ALTER COLUMN collection_no SET DEFAULT nextval('public.collections_collection_no_seq'::regclass);
 H   ALTER TABLE public.collections ALTER COLUMN collection_no DROP DEFAULT;
       public          postgres    false    207    206    207            A           2604    18192    pumpcrete pumpcrete_id    DEFAULT     �   ALTER TABLE ONLY public.pumpcrete ALTER COLUMN pumpcrete_id SET DEFAULT nextval('public.pumpcrete_pumpcrete_id_seq'::regclass);
 E   ALTER TABLE public.pumpcrete ALTER COLUMN pumpcrete_id DROP DEFAULT;
       public          postgres    false    203    202    203            �          0    18231    billings 
   TABLE DATA           �   COPY public.billings (bill_no, client_name, project_name, project_add, date_doc, date_used, psc_id, conc_structure, floor_level, qty, unit_price, total, posted, in_payments, is_paid, edited_by, posted_by) FROM stdin;
    public          postgres    false    205   F       �          0    18337    client 
   TABLE DATA           �   COPY public.client (client_id, client_first_name, client_last_name, client_position, client_address, client_landline, client_cellphone, client_email, latest_doc_date) FROM stdin;
    public          postgres    false    209   �F       �          0    18315    collections 
   TABLE DATA           �   COPY public.collections (collection_no, date, client_name, bill_no, posted, grand_total, check_number, check_date, bank, edited_by, posted_by) FROM stdin;
    public          postgres    false    207   LG       �          0    17675    credit_memo 
   TABLE DATA           %   COPY public.credit_memo  FROM stdin;
    public          postgres    false    200   �G       �          0    18189 	   pumpcrete 
   TABLE DATA           w   COPY public.pumpcrete (pumpcrete_id, description, plate_no, fuel_type, purchase_date, cr_no, or_no, tires) FROM stdin;
    public          postgres    false    203   �G       �          0    17689    users 
   TABLE DATA           d   COPY public.users (username, email, first_name, last_name, password, role, middle_name) FROM stdin;
    public          postgres    false    201   H       �           0    0    billings_bill_no_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.billings_bill_no_seq', 6, true);
          public          postgres    false    204            �           0    0    client_client_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.client_client_id_seq', 2, true);
          public          postgres    false    208            �           0    0    collections_collection_no_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public.collections_collection_no_seq', 5, true);
          public          postgres    false    206            �           0    0    pumpcrete_pumpcrete_id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.pumpcrete_pumpcrete_id_seq', 6, true);
          public          postgres    false    202            P           2606    18239    billings billings_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public.billings
    ADD CONSTRAINT billings_pkey PRIMARY KEY (bill_no);
 @   ALTER TABLE ONLY public.billings DROP CONSTRAINT billings_pkey;
       public            postgres    false    205            R           2606    18241    billings billings_psc_id_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.billings
    ADD CONSTRAINT billings_psc_id_key UNIQUE (psc_id);
 F   ALTER TABLE ONLY public.billings DROP CONSTRAINT billings_psc_id_key;
       public            postgres    false    205            V           2606    18345    client client_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (client_id);
 <   ALTER TABLE ONLY public.client DROP CONSTRAINT client_pkey;
       public            postgres    false    209            T           2606    18323    collections collections_pkey 
   CONSTRAINT     e   ALTER TABLE ONLY public.collections
    ADD CONSTRAINT collections_pkey PRIMARY KEY (collection_no);
 F   ALTER TABLE ONLY public.collections DROP CONSTRAINT collections_pkey;
       public            postgres    false    207            H           2606    18201    pumpcrete pumpcrete_cr_no_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.pumpcrete
    ADD CONSTRAINT pumpcrete_cr_no_key UNIQUE (cr_no);
 G   ALTER TABLE ONLY public.pumpcrete DROP CONSTRAINT pumpcrete_cr_no_key;
       public            postgres    false    203            J           2606    18203    pumpcrete pumpcrete_or_no_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.pumpcrete
    ADD CONSTRAINT pumpcrete_or_no_key UNIQUE (or_no);
 G   ALTER TABLE ONLY public.pumpcrete DROP CONSTRAINT pumpcrete_or_no_key;
       public            postgres    false    203            L           2606    18197    pumpcrete pumpcrete_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.pumpcrete
    ADD CONSTRAINT pumpcrete_pkey PRIMARY KEY (pumpcrete_id);
 B   ALTER TABLE ONLY public.pumpcrete DROP CONSTRAINT pumpcrete_pkey;
       public            postgres    false    203            N           2606    18199     pumpcrete pumpcrete_plate_no_key 
   CONSTRAINT     _   ALTER TABLE ONLY public.pumpcrete
    ADD CONSTRAINT pumpcrete_plate_no_key UNIQUE (plate_no);
 J   ALTER TABLE ONLY public.pumpcrete DROP CONSTRAINT pumpcrete_plate_no_key;
       public            postgres    false    203            F           2606    17717    users users_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (username);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    201            W           2606    18242     billings billings_edited_by_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.billings
    ADD CONSTRAINT billings_edited_by_fkey FOREIGN KEY (edited_by) REFERENCES public.users(username);
 J   ALTER TABLE ONLY public.billings DROP CONSTRAINT billings_edited_by_fkey;
       public          postgres    false    201    205    2886            X           2606    18247     billings billings_posted_by_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.billings
    ADD CONSTRAINT billings_posted_by_fkey FOREIGN KEY (posted_by) REFERENCES public.users(username);
 J   ALTER TABLE ONLY public.billings DROP CONSTRAINT billings_posted_by_fkey;
       public          postgres    false    2886    205    201            Y           2606    18324 &   collections collections_edited_by_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.collections
    ADD CONSTRAINT collections_edited_by_fkey FOREIGN KEY (edited_by) REFERENCES public.users(username);
 P   ALTER TABLE ONLY public.collections DROP CONSTRAINT collections_edited_by_fkey;
       public          postgres    false    207    2886    201            Z           2606    18329 &   collections collections_posted_by_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.collections
    ADD CONSTRAINT collections_posted_by_fkey FOREIGN KEY (posted_by) REFERENCES public.users(username);
 P   ALTER TABLE ONLY public.collections DROP CONSTRAINT collections_posted_by_fkey;
       public          postgres    false    201    2886    207            �   �   x���M�0���)zM�	K@7�<��i�$���qa:��b��ES�6Ho�}c%aﺓ-������*�z�1Rh(��/��烩��}��[w�}���L+�el)7�12w��EQ�9�#2�HM���/e�]�p�����L@|%AO��K=,�7�df      �   j   x�3��*M��LI�ITH.*��,�/6�442V(.)JM-1ML��-8,�����r���\�?.#$��1���canfjbld4���)wHI����� ��(      �   U   x�3�4202�5��54��*M�SpM�,IMQpI�ITp.*��4�1�L�4450�4��6��tr��,.ILK�/N�-�I������� oL�      �      x������ � �      �   8   x�3�LI-N�LLJ64�LO,�4202�5��50�446243�44261������� 	�      �   �   x�MN;� ��E�2�]��3�c%��TH! L��'���X~??�E��C��|\-����Wn�t�l$�����_k\e��z�K̫��@*o���"f�,�J��'�aoPPT�g6�C��ҧ��\#���/�{H�y{�{M�L p S�e     
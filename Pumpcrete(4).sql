PGDMP          2    
            y         	   Pumpcrete    13.3    13.3 $    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16454 	   Pumpcrete    DATABASE     o   CREATE DATABASE "Pumpcrete" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'English_United States.1252';
    DROP DATABASE "Pumpcrete";
                postgres    false            �            1259    18619    billings    TABLE     �  CREATE TABLE public.billings (
    bill_no bigint NOT NULL,
    client_name character varying NOT NULL,
    project_name character varying NOT NULL,
    project_add character varying NOT NULL,
    date_doc date NOT NULL,
    date_used date NOT NULL,
    psc_id bigint NOT NULL,
    total numeric NOT NULL,
    posted boolean NOT NULL,
    filled_by character varying,
    posted_by character varying
);
    DROP TABLE public.billings;
       public         heap    postgres    false            �           0    0    TABLE billings    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.billings TO staff_role;
GRANT SELECT,UPDATE ON TABLE public.billings TO supervisor_role;
          public          postgres    false    206            �            1259    18617    billings_bill_no_seq    SEQUENCE     }   CREATE SEQUENCE public.billings_bill_no_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.billings_bill_no_seq;
       public          postgres    false    206            �           0    0    billings_bill_no_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.billings_bill_no_seq OWNED BY public.billings.bill_no;
          public          postgres    false    205            �           0    0    SEQUENCE billings_bill_no_seq    ACL     �   GRANT SELECT,USAGE ON SEQUENCE public.billings_bill_no_seq TO staff_role;
GRANT SELECT,USAGE ON SEQUENCE public.billings_bill_no_seq TO supervisor_role;
GRANT SELECT,USAGE ON SEQUENCE public.billings_bill_no_seq TO admin_role;
          public          postgres    false    205            �            1259    18605    client    TABLE     \  CREATE TABLE public.client (
    client_id bigint NOT NULL,
    client_name character varying NOT NULL,
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
          public          postgres    false    204            �            1259    18603    client_client_id_seq    SEQUENCE     }   CREATE SEQUENCE public.client_client_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.client_client_id_seq;
       public          postgres    false    204            �           0    0    client_client_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.client_client_id_seq OWNED BY public.client.client_id;
          public          postgres    false    203            �           0    0    SEQUENCE client_client_id_seq    ACL     �   GRANT SELECT,USAGE ON SEQUENCE public.client_client_id_seq TO staff_role;
GRANT SELECT,USAGE ON SEQUENCE public.client_client_id_seq TO supervisor_role;
GRANT SELECT,USAGE ON SEQUENCE public.client_client_id_seq TO admin_role;
          public          postgres    false    203            �            1259    16546    credit_memo    TABLE     %   CREATE TABLE public.credit_memo (
);
    DROP TABLE public.credit_memo;
       public         heap    postgres    false            �           0    0    TABLE credit_memo    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.credit_memo TO staff_role;
GRANT SELECT,UPDATE ON TABLE public.credit_memo TO supervisor_role;
          public          postgres    false    201            �            1259    16549 
   pumpcretes    TABLE     $   CREATE TABLE public.pumpcretes (
);
    DROP TABLE public.pumpcretes;
       public         heap    postgres    false            �           0    0    TABLE pumpcretes    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE public.pumpcretes TO staff_role;
GRANT SELECT,UPDATE ON TABLE public.pumpcretes TO supervisor_role;
          public          postgres    false    202            �            1259    16505    users    TABLE     D  CREATE TABLE public.users (
    username character varying(50) NOT NULL,
    email character varying(150) NOT NULL,
    first_name character varying NOT NULL,
    last_name character varying NOT NULL,
    password character varying(50) NOT NULL,
    role character varying(50) NOT NULL,
    middle_name character varying
);
    DROP TABLE public.users;
       public         heap    postgres    false            �           0    0    TABLE users    ACL     �   GRANT SELECT,INSERT,UPDATE ON TABLE public.users TO admin_role;
GRANT SELECT,UPDATE ON TABLE public.users TO staff_role;
GRANT SELECT,UPDATE ON TABLE public.users TO supervisor_role;
          public          postgres    false    200            8           2604    18622    billings bill_no    DEFAULT     t   ALTER TABLE ONLY public.billings ALTER COLUMN bill_no SET DEFAULT nextval('public.billings_bill_no_seq'::regclass);
 ?   ALTER TABLE public.billings ALTER COLUMN bill_no DROP DEFAULT;
       public          postgres    false    206    205    206            7           2604    18608    client client_id    DEFAULT     t   ALTER TABLE ONLY public.client ALTER COLUMN client_id SET DEFAULT nextval('public.client_client_id_seq'::regclass);
 ?   ALTER TABLE public.client ALTER COLUMN client_id DROP DEFAULT;
       public          postgres    false    203    204    204            �          0    18619    billings 
   TABLE DATA           �   COPY public.billings (bill_no, client_name, project_name, project_add, date_doc, date_used, psc_id, total, posted, filled_by, posted_by) FROM stdin;
    public          postgres    false    206   �)       �          0    18605    client 
   TABLE DATA           �   COPY public.client (client_id, client_name, client_position, client_address, client_landline, client_cellphone, client_email, latest_doc_date) FROM stdin;
    public          postgres    false    204   q*       �          0    16546    credit_memo 
   TABLE DATA           %   COPY public.credit_memo  FROM stdin;
    public          postgres    false    201   �*       �          0    16549 
   pumpcretes 
   TABLE DATA           $   COPY public.pumpcretes  FROM stdin;
    public          postgres    false    202   +       �          0    16505    users 
   TABLE DATA           d   COPY public.users (username, email, first_name, last_name, password, role, middle_name) FROM stdin;
    public          postgres    false    200   9+       �           0    0    billings_bill_no_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.billings_bill_no_seq', 4, true);
          public          postgres    false    205            �           0    0    client_client_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.client_client_id_seq', 3, true);
          public          postgres    false    203            @           2606    18627    billings billings_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public.billings
    ADD CONSTRAINT billings_pkey PRIMARY KEY (bill_no);
 @   ALTER TABLE ONLY public.billings DROP CONSTRAINT billings_pkey;
       public            postgres    false    206            B           2606    18629    billings billings_psc_id_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.billings
    ADD CONSTRAINT billings_psc_id_key UNIQUE (psc_id);
 F   ALTER TABLE ONLY public.billings DROP CONSTRAINT billings_psc_id_key;
       public            postgres    false    206            <           2606    18615    client client_client_name_key 
   CONSTRAINT     _   ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_client_name_key UNIQUE (client_name);
 G   ALTER TABLE ONLY public.client DROP CONSTRAINT client_client_name_key;
       public            postgres    false    204            >           2606    18613    client client_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (client_id);
 <   ALTER TABLE ONLY public.client DROP CONSTRAINT client_pkey;
       public            postgres    false    204            :           2606    16728    users users_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (username);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    200            C           2606    18630     billings billings_filled_by_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.billings
    ADD CONSTRAINT billings_filled_by_fkey FOREIGN KEY (filled_by) REFERENCES public.users(username);
 J   ALTER TABLE ONLY public.billings DROP CONSTRAINT billings_filled_by_fkey;
       public          postgres    false    206    2874    200            D           2606    18635     billings billings_posted_by_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.billings
    ADD CONSTRAINT billings_posted_by_fkey FOREIGN KEY (posted_by) REFERENCES public.users(username);
 J   ALTER TABLE ONLY public.billings DROP CONSTRAINT billings_posted_by_fkey;
       public          postgres    false    2874    200    206            �   �   x�3�tTp��L�+Q�K�MU0����S
�..A����FF�� ��B=���Ĵ����܂�T��҂Ԣ����"����=F`�=F@��$�c��C)4{���iL�=&��(���c�f�	!3M��'F��� ^Yq�      �   ~   x�e�A
�0�������t_�� ݈	���4�"��bxB��f�ռ����'&����َ�ϥ��(J�r��z?�C��J���J������������79�.N��}��n���o��e=@�      �      x������ � �      �      x������ � �      �   �   x�]�;�0C��0,�.��"~�Di��>$@U���Įis��5Y�7@A)&�zN:k�"��3�DBS)�^�@�(�d~cd)	�b3�8��C\�9t`�����FF�������>K�􉼼M��:�Zby     
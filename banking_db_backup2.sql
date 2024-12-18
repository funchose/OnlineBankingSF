PGDMP  :    :                |            OnlineBanking    16.2    16.2     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    41213    OnlineBanking    DATABASE     �   CREATE DATABASE "OnlineBanking" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';
    DROP DATABASE "OnlineBanking";
                postgres    false            �            1259    41237    operations_list    TABLE     �   CREATE TABLE public.operations_list (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    type integer,
    money_amount bigint,
    date timestamp with time zone NOT NULL,
    receiver_id bigint
);
 #   DROP TABLE public.operations_list;
       public         heap    postgres    false            �            1259    41236    operations_list_seq    SEQUENCE     |   CREATE SEQUENCE public.operations_list_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.operations_list_seq;
       public          postgres    false    217            �           0    0    operations_list_seq    SEQUENCE OWNED BY     N   ALTER SEQUENCE public.operations_list_seq OWNED BY public.operations_list.id;
          public          postgres    false    216            �            1259    41214 	   user_data    TABLE     W   CREATE TABLE public.user_data (
    id bigint NOT NULL,
    balance bigint NOT NULL
);
    DROP TABLE public.user_data;
       public         heap    postgres    false            �            1259    41253    user_data_id_seq    SEQUENCE     y   CREATE SEQUENCE public.user_data_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.user_data_id_seq;
       public          postgres    false    215            �           0    0    user_data_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.user_data_id_seq OWNED BY public.user_data.id;
          public          postgres    false    218            V           2604    41240    operations_list id    DEFAULT     u   ALTER TABLE ONLY public.operations_list ALTER COLUMN id SET DEFAULT nextval('public.operations_list_seq'::regclass);
 A   ALTER TABLE public.operations_list ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    217    216    217            U           2604    41254    user_data id    DEFAULT     l   ALTER TABLE ONLY public.user_data ALTER COLUMN id SET DEFAULT nextval('public.user_data_id_seq'::regclass);
 ;   ALTER TABLE public.user_data ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    218    215            �          0    41237    operations_list 
   TABLE DATA           ]   COPY public.operations_list (id, user_id, type, money_amount, date, receiver_id) FROM stdin;
    public          postgres    false    217   f       �          0    41214 	   user_data 
   TABLE DATA           0   COPY public.user_data (id, balance) FROM stdin;
    public          postgres    false    215   �       �           0    0    operations_list_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.operations_list_seq', 32, true);
          public          postgres    false    216            �           0    0    user_data_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.user_data_id_seq', 3, true);
          public          postgres    false    218            Z           2606    41242    operations_list id_pk 
   CONSTRAINT     S   ALTER TABLE ONLY public.operations_list
    ADD CONSTRAINT id_pk PRIMARY KEY (id);
 ?   ALTER TABLE ONLY public.operations_list DROP CONSTRAINT id_pk;
       public            postgres    false    217            X           2606    41259    user_data user_data_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.user_data
    ADD CONSTRAINT user_data_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.user_data DROP CONSTRAINT user_data_pkey;
       public            postgres    false    215            [           2606    41288    operations_list receiver_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.operations_list
    ADD CONSTRAINT receiver_id_fk FOREIGN KEY (receiver_id) REFERENCES public.user_data(id) NOT VALID;
 H   ALTER TABLE ONLY public.operations_list DROP CONSTRAINT receiver_id_fk;
       public          postgres    false    217    4696    215            \           2606    41260    operations_list user_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.operations_list
    ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES public.user_data(id) NOT VALID;
 D   ALTER TABLE ONLY public.operations_list DROP CONSTRAINT user_id_fk;
       public          postgres    false    4696    217    215            �      x������ � �      �      x������ � �     
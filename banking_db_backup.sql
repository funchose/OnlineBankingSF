PGDMP      0                |            OnlineBanking    16.2    16.2     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    41213    OnlineBanking    DATABASE     �   CREATE DATABASE "OnlineBanking" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';
    DROP DATABASE "OnlineBanking";
                postgres    false            �            1259    41214 	   user_data    TABLE     N   CREATE TABLE public.user_data (
    id bigint NOT NULL,
    balance bigint
);
    DROP TABLE public.user_data;
       public         heap    postgres    false            �            1259    41220    user_data_id_seq    SEQUENCE     y   CREATE SEQUENCE public.user_data_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.user_data_id_seq;
       public          postgres    false    215            �           0    0    user_data_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.user_data_id_seq OWNED BY public.user_data.id;
          public          postgres    false    216            O           2604    41221    user_data id    DEFAULT     l   ALTER TABLE ONLY public.user_data ALTER COLUMN id SET DEFAULT nextval('public.user_data_id_seq'::regclass);
 ;   ALTER TABLE public.user_data ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    215            �          0    41214 	   user_data 
   TABLE DATA           0   COPY public.user_data (id, balance) FROM stdin;
    public          postgres    false    215   Z
       �           0    0    user_data_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.user_data_id_seq', 1, true);
          public          postgres    false    216            Q           2606    41226    user_data user_data_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.user_data
    ADD CONSTRAINT user_data_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.user_data DROP CONSTRAINT user_data_pkey;
       public            postgres    false    215            �      x������ � �     
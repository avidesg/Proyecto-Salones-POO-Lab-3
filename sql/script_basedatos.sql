/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */

/**
 * Author:  elect
 * Created: Oct 24, 2026
 */


CREATE DATABASE IF NOT EXISTS gestion_aulas
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE gestion_aulas;

-- Catalogo de plantas
CREATE TABLE IF NOT EXISTS planta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL UNIQUE,
    CONSTRAINT chk_planta_positiva CHECK (numero > 0)
);
-- Plantas iniciales
INSERT IGNORE INTO planta (numero) VALUES
    (1),
    (2),
    (3),
    (4);

-- Catalogo de tipos de aula
CREATE TABLE IF NOT EXISTS tipo_aula (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL UNIQUE
);

-- Catalogo de estados
CREATE TABLE IF NOT EXISTS estado_aula (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL UNIQUE
);

-- Registro principal de aulas
CREATE TABLE IF NOT EXISTS aula (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    edificio VARCHAR(100) NOT NULL,
    planta_id INT NOT NULL,
    capacidad INT NOT NULL,
    tipo_aula_id INT NOT NULL,
    responsable VARCHAR(120) NOT NULL,
    estado_aula_id INT NOT NULL,

    CONSTRAINT uq_aula_nombre_edificio
        UNIQUE (nombre, edificio),

    CONSTRAINT chk_aula_capacidad
        CHECK (capacidad > 0),

    CONSTRAINT fk_aula_planta
        FOREIGN KEY (planta_id)
        REFERENCES planta(id),

    CONSTRAINT fk_aula_tipo
        FOREIGN KEY (tipo_aula_id)
        REFERENCES tipo_aula(id),

    CONSTRAINT fk_aula_estado
        FOREIGN KEY (estado_aula_id)
        REFERENCES estado_aula(id)
);

-- Tipos iniciales de aula
INSERT IGNORE INTO tipo_aula (nombre) VALUES
    ('Normal'),
    ('Centro de computo'),
    ('Laboratorio'),
    ('Especial');

-- Estados iniciales
INSERT IGNORE INTO estado_aula (nombre) VALUES
    ('Asignada'),
    ('Asignada temporalmente'),
    ('En mantenimiento');

-- Tabla de usuarios del sistema
CREATE TABLE IF NOT EXISTS usuariosistema (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(100),
    Apellido VARCHAR(100),
    Usuario VARCHAR(100),
    Clave VARCHAR(32),
    Correo VARCHAR(150),
    Roll VARCHAR(50),
    Estado VARCHAR(30)
);

-- Usuario de prueba para verificar el inicio de sesión
INSERT INTO usuariosistema
    (Nombre, Apellido, Usuario, Clave, Correo, Roll, Estado)
SELECT
    'Prueba', 'Usuario', 'prueba',
    MD5('Prueba123'),
    'prueba@ufg.edu.sv', 'ADMIN', 'ACTIVO'
WHERE NOT EXISTS (
    SELECT 1
    FROM usuariosistema
    WHERE Usuario = 'prueba'
);
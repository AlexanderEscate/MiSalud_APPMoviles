
-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS db_salud;
USE db_salud;

-- Crear la tabla usuarios
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(50) NOT NULL,
    apellidos VARCHAR(50) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    contrasenia VARCHAR(100) NOT NULL
);

-- Insertar 5 usuarios en la tabla usuarios
INSERT INTO usuarios (nombres, apellidos, correo, contrasenia) VALUES
('Juan', 'Pérez', 'juan.perez@gmail.com', 'password123'),
('María', 'Gómez', 'maria.gomez@gmail.com', 'securepassword'),
('Carlos', 'López', 'carlos.lopez@gmail.com', 'qwerty123'),
('Ana', 'Martínez', 'ana.martinez@gmail.com', 'password456'),
('Luis', 'Fernández', 'luis.fernandez@gmail.com', 'mypassword789');

CREATE TABLE servicios (
    id_servicio INT AUTO_INCREMENT PRIMARY KEY, -- Identificador único del servicio
    nombre_servicio VARCHAR(100) NOT NULL,      -- Nombre del servicio (máximo 100 caracteres)
    descripcion VARCHAR(200)                   -- Descripción del servicio (máximo 200 caracteres)
);
INSERT INTO servicios (nombre_servicio, descripcion) 
VALUES 
('Consulta Pediátrica', 'Atención médica especializada para niños'),
('Examen de Laboratorio', 'Análisis clínicos de sangre, orina, y más'),
('Terapia Física', 'Rehabilitación para la movilidad y aliviar el dolor'),
('Atención de Emergencias', 'Servicios inmediatos para casos urgentes'),
('Chequeo Cardiológico', 'Revisión integral de la salud del corazón'),
('Odontoligía', 'Tratamiento para corregir la alineación de los dientes'),
('Consulta Ginecológica', 'Atención médica especializada en la salud femenina'),
('Cirugía Ambulatoria', 'Procedimientos quirúrgicos menores sin hospitalización'),
('Ultrasonido', 'Imágenes de diagnóstico por ultrasonido no invasivo'),
('Nutrición y Dietética', 'Asesoramiento en alimentación saludable y balanceada'),
('Medicina Interna', 'Diagnóstico y tratamiento de enfermedades complejas.'),
('Psicología Clínica', 'Atención profesional para la salud mental'),
('Chequeo Prenatal', 'Cuidado médico especializado durante el embarazo');


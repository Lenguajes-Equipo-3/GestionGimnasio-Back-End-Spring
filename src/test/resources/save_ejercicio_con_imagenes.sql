-- Eliminar primero las imágenes asociadas
DELETE FROM ImagenEjercicio;

-- Luego eliminar los ejercicios
DELETE FROM Ejercicio;

-- Habilitar la inserción manual de IDs en la tabla Ejercicio
SET IDENTITY_INSERT Ejercicio ON;

-- Insertar los datos de prueba en la tabla Ejercicio
INSERT INTO Ejercicio (id_ejercicio, id_categoria_ejercicio, nombre_ejercicio, descripcion_ejercicio, codigo_equipo)
VALUES
    (1, 1, 'Ejercicio 1', 'Descripción 1', 'EQ001'),
    (2, 1, 'Ejercicio 2', 'Descripción 2', 'EQ002');

-- Deshabilitar la inserción manual de IDs en la tabla Ejercicio
SET IDENTITY_INSERT Ejercicio OFF;

-- Habilitar la inserción manual de IDs en la tabla ImagenEjercicio
SET IDENTITY_INSERT ImagenEjercicio ON;

-- Insertar datos de prueba en la tabla ImagenEjercicio
INSERT INTO ImagenEjercicio (id_imagen, id_ejercicio, url_imagen, descripcion_imagen)
VALUES
    (1, 1, 'http://example.com/imagen1.jpg', 'Imagen asociada al Ejercicio 1'),
    (2, 2, 'http://example.com/imagen2.jpg', 'Imagen asociada al Ejercicio 2');

-- Deshabilitar la inserción manual de IDs en la tabla ImagenEjercicio
SET IDENTITY_INSERT ImagenEjercicio OFF;
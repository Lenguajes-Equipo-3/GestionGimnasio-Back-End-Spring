USE [IF4101_Proyecto1_Grupo3_2025]
DELETE FROM ImagenEjercicio;
DBCC CHECKIDENT ('ImagenEjercicio', RESEED, 0);
DELETE FROM [Ejercicio];
DBCC CHECKIDENT ('Ejercicio', RESEED, 0);
DELETE FROM CategoriaEjercicio;
DBCC CHECKIDENT ('CategoriaEjercicio', RESEED, 0); -- reinicia IDENTITY a 1 (solo si es SQL Server)







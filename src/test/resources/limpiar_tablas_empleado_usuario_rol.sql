DELETE FROM UsuarioRol;
DELETE FROM Usuario;
DELETE FROM Empleado;

DBCC CHECKIDENT ('dbo.Usuario', RESEED, 0);
DBCC CHECKIDENT ('dbo.Empleado', RESEED, 0);
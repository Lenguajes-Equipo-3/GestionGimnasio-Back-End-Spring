-- Inserta datos de prueba

-- === Empleado 1 (Entrenador) ===
INSERT INTO Empleado (nombre_empleado, apellidos_empleado) VALUES
('Entrenador', 'Prueba');
-- Asume que el siguiente INSERT usa el id_empleado recién generado por SCOPE_IDENTITY()
INSERT INTO Usuario (usuario, contrasena, id_empleado) VALUES
('entrenador.test', 'hashed_password_entrenador', SCOPE_IDENTITY());
-- Asume que el siguiente INSERT usa el usuario_id recién generado por SCOPE_IDENTITY()
INSERT INTO UsuarioRol (usuario_id, rol_id) VALUES
(SCOPE_IDENTITY(), 1); -- Rol ID 1 = ENTRENADOR
GO

-- === Empleado 2 (Admin) ===
INSERT INTO Empleado (nombre_empleado, apellidos_empleado) VALUES
('Admin', 'Prueba');
-- Asume que el siguiente INSERT usa el id_empleado recién generado por SCOPE_IDENTITY()
INSERT INTO Usuario (usuario, contrasena, id_empleado) VALUES
('admin.test', 'hashed_password_admin', SCOPE_IDENTITY());
-- Asume que el siguiente INSERT usa el usuario_id recién generado por SCOPE_IDENTITY()
INSERT INTO UsuarioRol (usuario_id, rol_id) VALUES
(SCOPE_IDENTITY(), 2); -- Rol ID 2 = ADMIN
GO
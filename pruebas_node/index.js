const express = require('express');
const mysql = require('mysql2');
const bodyParser = require('body-parser');

const app = express();

// Middleware CORS para permitir solicitudes desde cualquier origen
app.use(function(req, res, next) {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', '*');
    res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});

app.use(bodyParser.json());

const PUERTO = 5000;

// Conexión a la base de datos MySQL
const conexion = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'db_salud'
});

// Conexión a la base de datos
conexion.connect((error) => {
    if (error) throw error;
    console.log('Conexión exitosa a la base de datos');
});

// Escuchar en el puerto 5000
app.listen(PUERTO, () => {
    console.log(`Servidor corriendo en el puerto ${PUERTO}`);
});

// Endpoint básico
app.get('/', (req, res) => {
    res.send('API');
});

// Obtener todos los usuarios
app.get('/usuarios', async (req, res) => {
    const query = 'SELECT * FROM usuarios;';
    try {
        const [resultado] = await conexion.promise().query(query);
        if (resultado.length > 0) {
            res.json({ listaUsuarios: resultado });
        } else {
            res.status(404).send('No hay registros');
        }
    } catch (error) {
        console.error(error.message);
        res.status(500).send('Error al obtener los usuarios');
    }
});

// Obtener un usuario por ID
app.get('/usuario/:id', async (req, res) => {
    const { id } = req.params;
    const query = 'SELECT * FROM usuarios WHERE id_usuario = ?;';
    try {
        const [resultado] = await conexion.promise().query(query, [id]);
        if (resultado.length > 0) {
            // Devolver solo el primer usuario (objeto)
            res.json(resultado[0]);
        } else {
            res.status(404).send('No hay registros');
        }
    } catch (error) {
        console.error(error.message);
        res.status(500).send('Error al obtener el usuario');
    }
});

app.post('/usuario/add', async (req, res) => {
    const { nombres, apellidos, correo, contrasenia } = req.body;

    // Validar que los campos no estén vacíos
    if (!nombres || !apellidos || !correo || !contrasenia) {
        return res.status(400).send('Todos los campos son requeridos');
    }

    // Verificar si el correo ya existe en la base de datos
    const queryCheck = 'SELECT * FROM usuarios WHERE correo = ?';

    try {
        const [existingUser] = await conexion.promise().query(queryCheck, [correo]);

        // Si el correo ya existe, devolver un error
        if (existingUser.length > 0) {
            return res.status(400).send('El correo electrónico ya está registrado');
        }

        // Crear el objeto de usuario para insertar
        const usuario = { nombres, apellidos, correo, contrasenia };
        const queryInsert = 'INSERT INTO usuarios SET ?';

        // Ejecutar la consulta para insertar el usuario
        const [result] = await conexion.promise().query(queryInsert, usuario);
        res.status(201).json({ message: 'Se insertó correctamente el usuario', userId: result.insertId });
    } catch (error) {
        // Manejo de errores en la consulta
        console.error('Error al registrar el usuario:', error.message);
        res.status(500).send('Error al registrar el usuario');
    }
});

app.put('/usuario/update/:id', async (req, res) => {
    const { id } = req.params;
    const { nombres, apellidos, correo, contrasenia } = req.body;

    // Validar que los campos no estén vacíos
    if (!nombres || !apellidos || !correo || !contrasenia) {
        return res.status(400).send('Todos los campos son requeridos');
    }

    // Verificar si el correo ya está registrado con otro usuario (si se está cambiando)
    const queryCheck = 'SELECT * FROM usuarios WHERE correo = ? AND id_usuario != ?';
    
    try {
        const [existingUser] = await conexion.promise().query(queryCheck, [correo, id]);

        if (existingUser.length > 0) {
            return res.status(400).send('El correo electrónico ya está registrado por otro usuario');
        }

        // Crear el objeto con los nuevos datos
        const queryUpdate = `
            UPDATE usuarios
            SET nombres = ?, apellidos = ?, correo = ?, contrasenia = ?
            WHERE id_usuario = ?
        `;
        const [result] = await conexion.promise().query(queryUpdate, [nombres, apellidos, correo, contrasenia, id]);

        if (result.affectedRows > 0) {
            res.status(200).json({ message: 'Usuario actualizado correctamente' });
        } else {
            res.status(404).send('Usuario no encontrado');
        }
    } catch (error) {
        console.error('Error al actualizar el usuario:', error.message);
        res.status(500).send('Error al actualizar el usuario');
    }
});


app.delete('/usuario/delete/:id', async (req, res) => {
    const { id } = req.params;

    const queryDelete = 'DELETE FROM usuarios WHERE id_usuario = ?';

    try {
        const [result] = await conexion.promise().query(queryDelete, [id]);

        if (result.affectedRows > 0) {
            res.status(200).json({ message: 'Usuario eliminado correctamente' });
        } else {
            res.status(404).send('Usuario no encontrado');
        }
    } catch (error) {
        console.error('Error al eliminar el usuario:', error.message);
        res.status(500).send('Error al eliminar el usuario');
    }
});

//logueo
app.post('/usuario/login', async (req, res) => {
    const { correo, contrasenia } = req.body;  // Usamos req.body para obtener los datos del cuerpo
    const query = 'SELECT * FROM usuarios WHERE correo = ? AND contrasenia = ?';
    try {
        const [resultado] = await conexion.promise().query(query, [correo, contrasenia]);
        if (resultado.length > 0) {
            res.json(resultado[0]); // Devuelve el usuario encontrado
        } else {
            res.status(404).send('Credenciales incorrectas');
        }
    } catch (error) {
        console.error(error.message);
        res.status(500).send('Error al buscar el usuario');
    }
});

// Obtener todos los servicios
app.get('/servicios', async (req, res) => {
    const query = 'SELECT * FROM servicios;';
    try {
        const [resultado] = await conexion.promise().query(query);
        if (resultado.length > 0) {
            res.json( resultado );
        } else {
            res.status(404).send('No hay registros');
        }
    } catch (error) {
        console.error(error.message);
        res.status(500).send('Error al obtener los servicios');
    }
});
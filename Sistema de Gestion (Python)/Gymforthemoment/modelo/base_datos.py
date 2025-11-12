import sqlite3

class ConexionBD:
    def __init__(self, nombre_bd="gymforthemoment.db"):
        self.nombre_bd = nombre_bd
        self.crear_tablas()

    def conectar(self):
        return sqlite3.connect(self.nombre_bd)

    def crear_tablas(self):
        con = self.conectar()
        cur = con.cursor()
        cur.execute('''CREATE TABLE IF NOT EXISTS clientes (
                        id_cliente INTEGER PRIMARY KEY AUTOINCREMENT,
                        nombre TEXT NOT NULL,
                        apellidos TEXT,
                        dni TEXT UNIQUE,
                        telefono TEXT,
                        email TEXT,
                        ha_pagado INTEGER DEFAULT 0
                      )''')

        cur.execute('''CREATE TABLE IF NOT EXISTS aparatos (
                        id_aparato INTEGER PRIMARY KEY AUTOINCREMENT,
                        nombre TEXT,
                        tipo TEXT,
                        estado TEXT
                      )''')

        cur.execute('''CREATE TABLE IF NOT EXISTS reservas (
                        id_reserva INTEGER PRIMARY KEY AUTOINCREMENT,
                        id_cliente INTEGER,
                        id_aparato INTEGER,
                        fecha TEXT,
                        hora_inicio TEXT,
                        hora_fin TEXT,
                        FOREIGN KEY(id_cliente) REFERENCES clientes(id_cliente),
                        FOREIGN KEY(id_aparato) REFERENCES aparatos(id_aparato)
                      )''')

        cur.execute('''CREATE TABLE IF NOT EXISTS recibos (
                        id_recibo INTEGER PRIMARY KEY AUTOINCREMENT,
                        id_cliente INTEGER,
                        mes INTEGER,
                        anio INTEGER,
                        pagado INTEGER DEFAULT 0,
                        FOREIGN KEY(id_cliente) REFERENCES clientes(id_cliente)
                      )''')
        con.commit()
        con.close()

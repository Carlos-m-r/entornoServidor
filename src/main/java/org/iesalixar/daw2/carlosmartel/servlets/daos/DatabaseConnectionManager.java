package org.iesalixar.daw2.carlosmartel.servlets.daos;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    private static Connection connection =  null;

    private static Logger logger = LoggerFactory.getLogger(DatabaseConnectionManager.class);

    private static Dotenv dotenv = Dotenv.load();


    private DatabaseConnectionManager() {}


    public static Connection getConnection() {
        try {
            if(connection == null || connection.isClosed()) {
                logger.info("Iniciando conexión a BD MariaDB");

                String dbUrl = dotenv.get("DB_URL");
                String dbUser = dotenv.get("DB_USER");
                String dbPassword = dotenv.get("DB_PASSWORD");


                connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                logger.info("Conexión establecida a BD con éxito");

            }

        } catch (SQLException e) {
            logger.error("Error al conectar a BD", e.getMessage(), e);
            throw new RuntimeException("No se pudo conectar a BD",e);
        }
        return connection;
    }

    public static void closeConnection() {
        if(connection != null) {
            try {
                logger.info("Cerrando conexión a BD");
                connection.close();
                logger.info("Conexión cerrada con éxito");
            } catch (SQLException e) {
                logger.error("Error al cerrar la conexión a BD: {}", e.getMessage(), e);
            }
        }
    }

}

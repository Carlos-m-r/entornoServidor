package org.iesalixar.daw2.carlosmartel.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DataInitializer {

    private static Logger logger = LoggerFactory.getLogger(DataInitializer.class);


    public static void loadDataFromSQL(InputStream sqlFileStream) throws SQLException, IOException {
        logger.info("Entrando en el método loadDataFromSQL");

        if (sqlFileStream == null) {
            logger.error("El archivo SQL no se ha proporcionado o es nulo");
            throw new IOException("El archivo SQL es nulo o no se ha encontrado");
        }

        try(Connection connection = DatabaseConnectionManager.getConnection()) {
            logger.info("Conexón a la BD establecida");

            String sql;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(sqlFileStream))){
                sql = reader.lines().collect(Collectors.joining("\n"));
            } catch (IOException e) {
                logger.error("Error al leer el archivo SQL: {}", e.getMessage(), e);
                throw new IOException("Error al leer el archivo SQL", e);
            }


            String[] statements = sql.split(";");

            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement()) {
                for (String sqlStatement : statements) {
                    if(!sqlStatement.trim().isEmpty()) {
                        logger.info("Ejecutando sentencia SQL: {}", sqlStatement);
                        statement.execute(sqlStatement.trim());
                    }
                }

                connection.commit();
                logger.info("Datos cargados exitosamente desde el archivo SQL");
            } catch (SQLException e) {
                connection.rollback();
                logger.error("Error al ejecutar el archivo SQL, haciendo rollback: {}", e.getMessage(), e);

                throw new SQLException("Error al ejecutar el archivo SQL revirtiendo transacción",e);
            }
        } catch (SQLException e) {
            logger.error("Error durante la conexión a BD o ejecución SQL: {}", e.getMessage(), e);
            throw e;
        }

        logger.info("Saliendo del método loadDataFromSQL");
    }


}

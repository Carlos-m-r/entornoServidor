package org.iesalixar.daw2.carlosmartel.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.iesalixar.daw2.carlosmartel.daos.DataInitializer;
import org.iesalixar.daw2.carlosmartel.daos.DatabaseConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Connection;

@WebListener
public class AppContextListener implements ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(AppContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Inicializando aplicación y conectando a BD");
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            InputStream sqlFileStream = sce.getServletContext().getResourceAsStream("/WEB-INF/classes/data.sql");

            if(sqlFileStream == null) {
                logger.error("no se pudo encontrar el archivo data.sql en /WEB-INF/classes/");
            }

            DataInitializer.loadDataFromSQL(sqlFileStream);
            logger.info("Carga de datos finalizada");

        } catch (Exception e) {
            logger.error("Error al inicializar la app y cargar datos: {}", e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Cerrando conexion a DB al apagar la aplicación");
        DatabaseConnectionManager.closeConnection();
    }
}



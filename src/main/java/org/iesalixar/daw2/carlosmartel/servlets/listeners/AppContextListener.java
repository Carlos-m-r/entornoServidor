package org.iesalixar.daw2.carlosmartel.servlets.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.iesalixar.daw2.carlosmartel.servlets.daos.DatabaseConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class AppContextListener implements ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(AppContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Inicializando aplicación y conectando a BD");
        DatabaseConnectionManager.getConnection();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Cerrando conexion a DB al apagar la aplicación");
        DatabaseConnectionManager.closeConnection();
    }
}



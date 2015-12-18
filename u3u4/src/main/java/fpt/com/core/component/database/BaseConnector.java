package fpt.com.core.component.database;

import fpt.com.component.database.JPAConnector;
import fpt.com.db.AbstractDatabaseStrategy;
import fpt.com.model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * BaseConnector
 */
abstract public class BaseConnector extends AbstractDatabaseStrategy {

    /**
     * Create a new product entry in database
     *
     * @param name
     * @param price
     * @param quantity
     * @return
     */
    public abstract long insert(String name, double price, int quantity);

    /**
     * Create a new product entry in database
     *
     * @param product
     * @return
     */
    public abstract void insert(fpt.com.Product product);

    /**
     * Get product by id
     *
     * @param id
     * @return
     */
    public abstract Product read(long id);

    /**
     * Get products from database one-by-one
     *
     * @return
     */
    public abstract Product read();

    /**
     * Close all database related ressources
     */
    public abstract void close();

    /**
     * Get JDBC Connection
     * @return
     */
    public abstract Connection getJDBCConnection();

    /**
     * Print metadata
     */
    public void printMetadata() {
        try {
            System.out.println("URL: " + this.getJDBCConnection().getMetaData().getURL() +
                                       "\nUsername: " + this.getJDBCConnection().getMetaData().getUserName());

            String catalog = null;
            String schemaPattern = null;
            String tableNamePattern = "%";
            String[] types = {"TABLE"};

            try (ResultSet result = this.getJDBCConnection().getMetaData().getTables(
                    catalog, schemaPattern, tableNamePattern, types)) {
                String tableNames = "";

                while (result.next()) {
                    tableNames += result.getString(3)+", ";
                }

                System.out.println("Tables: " + tableNames);

                result.close();
                this.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Central method to get the wanted database connector
     *
     * Change this to JDBCConnector if wanted!!!!
     *
     * @return
     */
    public static BaseConnector getConnector() {
        return JPAConnector.getInstance();
    }
}

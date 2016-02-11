package fpt.com.component.strategy;

import fpt.com.Product;
import fpt.com.SerializableStrategy;
import fpt.com.core.component.database.BaseConnector;
import fpt.com.db.AbstractDatabaseStrategy;

import java.io.IOException;

/**
 * DatabaseStrategy
 */
public class DatabaseStrategy extends AbstractDatabaseStrategy
        implements SerializableStrategy {

    public DatabaseStrategy() {
        // Nothing to do
    }

    @Override
    public String toString() {
        return "DatabaseStrategy";
    }

    @Override
    public void open() throws IOException {
        /**
         * Nothing to do here. Connection will be handled by the connector.
         */
    }

    @Override
    public Product readObject() throws IOException {
        return BaseConnector.getConnector().read();
    }

    @Override
    public void close() throws IOException {
        // Nothing to do
    }

    @Override
    public void writeObject(Product obj) throws IOException {
        if (obj.getId() == 0) {
            System.out.println(obj.getName());
            BaseConnector.getConnector().insert(obj);
        }
    }
}

package fpt.com.component.strategy;

import com.thoughtworks.xstream.XStream;
import fpt.com.Product;
import fpt.com.SerializableStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by senycorp on 27.11.15.
 */
public class XStreamStrategy
        implements SerializableStrategy {

    @Override
    public Product readObject() throws IOException {
        return null;
    }

    @Override
    public void writeObject(Product obj) throws IOException {

    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void open(InputStream input, OutputStream output) throws IOException {

    }

    @Override
    public XStream createXStream(Class<? extends Product> clazz) {
        return null;
    }
}

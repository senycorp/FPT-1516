package fpt.com.component.strategy;

import fpt.com.Product;
import fpt.com.SerializableStrategy;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * XMLStrategy
 */
public class XMLStrategy
        implements SerializableStrategy {

    private String destinationFilename = "products.xml";

    @Override
    public Product readObject() throws IOException {
        Product readObject = null;
        try (FileInputStream fi = new FileInputStream("d . xml ");
             XMLDecoder decoder = new XMLDecoder(fi)) {
            readObject = (Product) decoder.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return readObject;
    }

    @Override
    public void writeObject(Product obj) throws IOException {
        try (FileOutputStream fo = new FileOutputStream(destinationFilename);
             XMLEncoder encoder = new XMLEncoder(fo)) {
            encoder.writeObject(obj);
            encoder.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void open(InputStream input, OutputStream output) throws IOException {

    }
}

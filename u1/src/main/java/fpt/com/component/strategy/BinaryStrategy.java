package fpt.com.component.strategy;

import fpt.com.Product;

import java.io.*;

/**
 * BinaryStrategy
 */
public class BinaryStrategy
        implements fpt.com.SerializableStrategy {

    private String destinationFilename = "products.ser";

    @Override
    public Product readObject() throws IOException {
        Product readObject = null;
        try (FileInputStream fi = new FileInputStream(destinationFilename);
            ObjectInputStream is = new ObjectInputStream(fi)) {
            readObject = (Product) is.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return readObject;
    }

    @Override
    public void writeObject(Product obj) throws IOException {
        try (FileOutputStream fo = new FileOutputStream(destinationFilename);
            ObjectOutputStream os = new ObjectOutputStream(fo)) {
            os.writeObject(obj);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void open(InputStream input, OutputStream output) throws IOException {

    }
}

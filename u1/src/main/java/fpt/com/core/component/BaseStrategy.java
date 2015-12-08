package fpt.com.core.component;

import fpt.com.Product;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * BaseStrategy
 *
 * This class was created to generate reusable code
 */
abstract public class BaseStrategy {

    public abstract String getDestinationFilename();

    /**
     * ObjectOutputStream
     */
    protected ObjectOutputStream objectOS = null;

    /**
     * ObjectInputStream
     */
    protected ObjectInputStream objectIS = null;

    /**
     * InputStream
     */
    protected InputStream is = null;

    /**
     * OutputStream
     */
    protected OutputStream os = null;

    /**
     * Open inputStreams
     *
     * @param pathAsString
     * @throws IOException
     */
    public void openInputStream(String... pathAsString) throws IOException {
        if (pathAsString != null && pathAsString.length > 0
                && pathAsString[0] != null) {
            Path path = Paths.get("", pathAsString);

            if (path != null) {
                if (Files.exists(path)) {
                    byte[] data = Files.readAllBytes(path);
                    InputStream in = new ByteArrayInputStream(data);
                    open(in, null);
                }
            }
        }
    }

    /**
     * Open outputStreams
     *
     * @param pathAsString
     * @throws IOException
     */
    public void openOutputStream(String... pathAsString) throws IOException{
        if (pathAsString != null && pathAsString.length > 0
                && pathAsString[0] != null) {
            Path path = Paths.get("", pathAsString);

            if (path != null) {
                OutputStream out = Files.newOutputStream(path);
                open(null, out);
            }
        }
    }

    /**
     * Create streams
     *
     * @param input  the file data if previews file exists, otherwise null
     * @param output the new output
     * @throws IOException
     */
    public void open(InputStream input, OutputStream output) throws IOException {
        if (input != null) {
            this.is = input;
            this.objectIS = new ObjectInputStream(input);
        }

        if (output != null) {
            this.os = output;
            this.objectOS = new ObjectOutputStream(output);
        }
    }

    /**
     * Read objects
     * @return
     * @throws IOException
     */
    public Product readObject() throws IOException {
        Product myProduct = null;

        // Create inputstream if needed
        if (this.objectIS == null) {
            this.openInputStream(this.getDestinationFilename());
        }

        try {
            myProduct = (Product) objectIS.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("Binary: Klasse nicht gefunden");
            e.printStackTrace();
        } catch (EOFException e){
            System.out.println("Ende der Datei erreicht");
        } catch (NullPointerException e) {
            System.out.println("Es gibt nichts zum Laden");
        }

        return myProduct;
    }

    /**
     * Close all related streams
     *
     * @throws IOException
     */
    public void close() throws IOException {
        if (objectOS != null) {
            objectOS.close();
            objectOS = null;
        }

        if (objectIS != null) {
            objectIS.close();
            objectIS = null;
        }

        if (is != null) {
            is.close();
            is = null;
        }

        if (os != null) {
            os.close();
            os = null;
        }
    }

    public void writeObject(Product obj) throws IOException {
        // Create outputstream if needed
        if (this.objectOS == null) {
            this.openOutputStream(this.getDestinationFilename());
        }

        // Write and flush object to file
        objectOS.writeObject(obj);
        objectOS.flush();
    }


    /**
     * This is needed for the combobox
     *
     * @return
     */
    public abstract String toString();
}

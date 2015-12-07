package fpt.com.component.strategy;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import fpt.com.ExternalizableReflectionConverter;
import fpt.com.Product;
import fpt.com.SerializableStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class XStreamStrategy
        implements SerializableStrategy {

    private static final String       FILENAME     = "productsXStream.xml";
    private              InputStream  inputStream  = null;
    private              OutputStream outputStream = null;

    private ObjectOutputStream objOutputStream = null;
    private ObjectInputStream  objInputStream  = null;

    private XStream xstream = null; // unser xstream object

    private void openInputStream(String... pathAsString) throws IOException {
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

    private void openOutputStream(String... pathAsString) throws IOException {
        if (pathAsString != null && pathAsString.length > 0
                && pathAsString[0] != null) {
            Path path = Paths.get("", pathAsString);

            if (path != null) {
                OutputStream out = Files.newOutputStream(path);
                open(null, out);
            }
        }
    }

    public Product readObject() throws IOException {
        Product myProduct = null;

        if (this.inputStream == null) {
            this.openInputStream(FILENAME);
        }

        try {
            myProduct = (fpt.com.model.Product) this.objInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {
            System.out.println("Ende der Datei erreicht");
        } catch (NullPointerException e) {
            System.out.println("Es gibt nichts zum Laden");
        }

        return myProduct;
    }

    public void writeObject(Product obj) throws IOException {
        if (this.outputStream == null) {
            this.openOutputStream(FILENAME);
        }

        objOutputStream.writeObject(obj);
        objOutputStream.flush();
    }

    public void close() throws IOException {
        if (objInputStream != null) {
            objInputStream.close();
            objInputStream = null;
        }

        if (objOutputStream != null) {
            objOutputStream.close();
            objOutputStream = null;
        }

        if (inputStream != null) {
            inputStream.close();
            inputStream = null;
        }

        if (outputStream != null) {
            outputStream.close();
            outputStream = null;
        }
    }

    @Override
    public void open(InputStream input, OutputStream output) throws IOException {
        if (this.xstream == null) {
        xstream = new XStream(new DomDriver("UTF-8"));
        xstream.registerConverter(
                new ExternalizableReflectionConverter(xstream),
                XStream.PRIORITY_LOW);
        xstream.processAnnotations(fpt.com.model.Product.class);
        xstream.alias("Ware", fpt.com.model.Product.class);
        xstream.autodetectAnnotations(true);
        }

        if (input != null) {
            this.inputStream = input;
            this.objInputStream = xstream.createObjectInputStream(inputStream);
        }

        if (output != null) {
            this.outputStream = output;
            this.objOutputStream = xstream.createObjectOutputStream(output, "Waren");
        }
    }


    @Override
    public String toString() {
        return "XStreamStrategy";
    }
}

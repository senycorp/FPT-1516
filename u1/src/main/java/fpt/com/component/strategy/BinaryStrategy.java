package fpt.com.component.strategy;

import fpt.com.Product;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * BinaryStrategy
 */
public class BinaryStrategy
        implements fpt.com.SerializableStrategy {

    private String destinationFilename = "products.ser";
    
    // ausserhalb streams erstellen, damit diese nicht sofort geschlossen werden
 	private ObjectOutputStream objOutputStream = null;
 	private ObjectInputStream objInputStream = null;
    
    private void openInputStream(String... pathAsString) throws IOException{
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
	
	private void openOutputStream(String... pathAsString) throws IOException{
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

		if (this.objInputStream == null) {
			this.openInputStream(destinationFilename);
		}

		try {
			myProduct = (Product) objInputStream.readObject();
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

	public void writeObject(Product obj) throws IOException {
		if (this.objOutputStream == null) {
			this.openOutputStream(destinationFilename);
		}

		objOutputStream.writeObject(obj);
		objOutputStream.flush();
	}

	public void close() throws IOException {
		if (objOutputStream != null) {
			objOutputStream.close();
			objOutputStream = null;
		}

		if (objInputStream != null) {
			objInputStream.close();
			objInputStream = null;
		}
	}

	@Override
	public void open(InputStream input, OutputStream output) throws IOException {
		if (input != null) {
			this.objInputStream = new ObjectInputStream(input);
		}
		
		if (output != null){
			this.objOutputStream = new ObjectOutputStream(output);
		}
	}
    
    //*****************************************************
	/*
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
    */
}

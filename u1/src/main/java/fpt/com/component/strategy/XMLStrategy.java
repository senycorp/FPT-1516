package fpt.com.component.strategy;

import fpt.com.Product;
import fpt.com.SerializableStrategy;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 *  c) wie unterscheiden sich die arten der serialisierung?
 * 	binaer und java beans sind analog bis auf encoder/output decoder/input (namen anders)
 * 	- statt transient(binary) - PropertyDescriptor(beans)
 * 	d) 	vorteile:
 * 		beans - ist leserlicher
 * 		beans - deserialisierung trotz fehlender methoden moeglich(NoSuchMethodException)
 * 		binary - keine setter/getter pflicht
 * 		binary schwer zu lesen(sicherer)		
 * 
 * 		nachteile:
 * 		beans ist leserlicher (sicherheits risiko)
 * 		binary schwer zu lesen
 */

public class XMLStrategy implements SerializableStrategy {
	// alle streams sozusagen aufbauen zum benutzen
	private OutputStream outputStream = null;
	private XMLEncoder xmlEncoder = null;

	private InputStream inputStream = null;
	private XMLDecoder xmlDecoder = null;

	private String FILENAME = "products.xml";
	
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

		if (this.inputStream == null || this.xmlDecoder == null) {
			this.openInputStream(FILENAME);
		}

		try {
			myProduct = (Product) xmlDecoder.readObject();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Ende der XML-Datei erreicht");
			// Unset input stream to begin from start of file again
			inputStream = null;
			xmlDecoder = null;
		}

		return myProduct;
	}

	public void writeObject(Product obj) throws IOException {
		if (this.outputStream == null || this.xmlEncoder == null) {
			this.openOutputStream(FILENAME);
		}

		xmlEncoder.writeObject(obj);
		xmlEncoder.flush();
	}

	public void close() throws IOException {
		if (xmlEncoder != null) {
			xmlEncoder.close();
			xmlEncoder = null;
		}

		if (xmlDecoder != null) {
			xmlDecoder.close();
			xmlDecoder = null;
		}

		if (outputStream != null) {
			outputStream.close();
			outputStream = null;
		}

		if (inputStream != null) {
			inputStream.close();
			inputStream = null;
		}
	}

	@Override
	public void open(InputStream input, OutputStream output) throws IOException {
		if (input != null) {
			this.inputStream = input;
			this.xmlDecoder = new XMLDecoder(this.inputStream);
		}

		if (output != null) {
			this.outputStream = output;
			this.xmlEncoder = new XMLEncoder(this.outputStream);
		}
	}

	@Override
	public String toString() {
		return "XMLStrategy";
	}
}

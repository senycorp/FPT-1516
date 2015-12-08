package fpt.com.component.strategy;

import fpt.com.Product;
import fpt.com.SerializableStrategy;
import fpt.com.core.component.BaseStrategy;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

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

public class XMLStrategy extends BaseStrategy
		implements SerializableStrategy {

	/**
	 * XML-Encoder
	 */
	private XMLEncoder xmlEncoder = null;

	/**
	 * XML-Decoder
	 */
	private XMLDecoder xmlDecoder = null;

	@Override
	public String getDestinationFilename() {
		return "products.xml";
	}

	/**
	 * Read object from xml
	 *
	 * @return
	 * @throws IOException
	 */
	public Product readObject() throws IOException {
		Product myProduct = null;

		if (this.is == null || this.xmlDecoder == null) {
			this.openInputStream(this.getDestinationFilename());
		}

		try {
			myProduct = (Product) xmlDecoder.readObject();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Ende der XML-Datei erreicht");

			// Unset input stream to begin from start of file again
			is = null;
			xmlDecoder = null;
		}

		return myProduct;
	}

	/**
	 * Write object
	 *
	 * @param obj the object for serialization
	 * @throws IOException
	 */
	public void writeObject(Product obj) throws IOException {
		if (this.os == null || this.xmlEncoder == null) {
			this.openOutputStream(this.getDestinationFilename());
		}

		xmlEncoder.writeObject(obj);
		xmlEncoder.flush();
	}

	/**
	 * Close all opened streams
	 * @throws IOException
	 */
	public void close() throws IOException {
		if (xmlEncoder != null) {
			xmlEncoder.close();
			xmlEncoder = null;
		}

		if (xmlDecoder != null) {
			xmlDecoder.close();
			xmlDecoder = null;
		}

		super.close();
	}

	@Override
	public void open(InputStream input, OutputStream output) throws IOException {
		super.open(input, output);

		if (this.is != null) {
			this.xmlDecoder = new XMLDecoder(this.is);
		}

		if (this.os != null) {
			this.os = output;
			this.xmlEncoder = new XMLEncoder(this.os);
		}
	}

	@Override
	public String toString() {
		return "XMLStrategy";
	}
}

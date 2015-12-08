package fpt.com.component.strategy;

import fpt.com.Product;
import fpt.com.SerializableStrategy;
import fpt.com.core.component.BaseStrategy;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * Aufgabe 3
 *
 * c: Binäre- und Java-Beans-Serialisierung sind größtenteils gleich. Lediglich die Ablage der serialisierten Objekte
 * ist binär oder im XML-Format. Dieser Unterschied wird durch die Verwendung eines anderen Encoders/Decoders erreicht.
 * Während die binäre Serialisierung ObjectOutputStream/ObjectInputStream verwendet, wrappt die Beans-Serialisierung diese
 * Streams nocheinmal in XMLDecoder/XMLEncoder. Des weiteren muss für die XML-Serialisierung sichergestellt werden, dass
 * einige Konventionen eingehalten werden. Dazu zählt, dass jede Property über zugehörige Getter/Setter-Methoden verfügt.
 * Weiter ist ein No-Argument-Konstruktor notwendig.
 *
 * Für die Sichtbarkeit von bestimmten Properties verwendet Beans nicht das Keyword "transient" sondern den PropertyDescriptor.
 *
 * d: Vorteile "Beans": leserlich,
 * 						Deserialisierung,
 * 						Event-Listener "PropertyChangeListener"
 * 						Portabilität
 *
 * Nachteile "Beans: 	Konventionspflicht
 * 						Lesbarkeit birgt ein Sicherheitsrisiko
 *
 * Vorteile "Binary":	Keine Konventionspflicht
 * 						Erhöhte Sicherheit durch unlesbares Format
 *
 * Nachteile "Binary":	Nicht lesbar
 *
 */

/**
 * XMLStrategy
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
			/**
			 * End of file reached
			 */
		} catch (NullPointerException e) {
			/**
			 * There is nothing to load. Maybe the file does not exist or is empty.
			 */
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

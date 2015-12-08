package fpt.com.component.strategy;

import fpt.com.core.component.BaseStrategy;

/**
 * Aufgabe 2
 *
 * a: Das Interface "Serializable" und die "serialVersionUID" muss zu Versionierungszwecken gesetzt sein.
 *
 * d: Da diese Klassen nicht das "Serializable"-Interface implementieren können Sie nicht serialisiert werden,
 * auch wenn sie mit der Product-Klasse zusammenhängen. Das gleiche gilt beispielsweise für Threads und Sockets.
 *
 * f: Der Versuch einer Deserialisierung eines Objektes, bei der sich die Klasse verändert hat birgt verschiedene Probleme.
 *
 * Fall 1: Keine SUID
 *
 * Falls die Klasse keine SUID hat kommt es bei der kleinsten Änderung der Klasse und der anschließenden Deserialisierung
 * zu einer Exception: local class incompatible: stream classdesc serialVersionUID = Umbruch
 * 44259824709362049, local class serialVersionUID = 8962277452270582278
 *
 * Dies hängt damit zusammen, dass JAVA trotzdem eine SUID generiert und bei der Deserialisierung abgleicht.
 *
 * Fall 2: Eigene SUID
 *
 * Falls Attribute hinzugekommen sind, werden diese lediglich mit einem Default-Wert belegt (0 oder null).
 * Falls Attribute entfernt werden, werden diese einfach ignoriert . Um zu verhindern, dass alte Objekte deserialisiert
 * werden, kann der Abgleich mit der "serialVersionUID" verwendet werden. Dabei kommt es zu einer InvalidClassException,
 * falls sich die UIDs nicht gleichen und die Deserialisierung wird somit verhindert.
 *
 * g: Die "serialVersionUID" kann als Identifikationsnummer der Version einer Klasse verstanden werden. Bei jeder Deserialisierung
 * und dem Versuch des Castings in die Zielklasse geschieht ein Abgleich dieser UID um zu verhindern, dass es zu Kompatibilitätsproblemen kommt.
 * Die Änderung der UID liegt im Ermessen des Entwicklers. Es is sicherlich nicht immer notwendig bei der kleinsten Veränderung der Klasse die UID
 * hochzusetzen.
 *
 * h: Durch die Serialisierung und Deserialiserung eines Objektes wird keine komplett eigenständige Kopie im Speicher abgelegt.
 * Somit wird verhindert, dass zwei Variablen auf die gleiche Referenz zu greifen:
 *
 * QUELLE: http://openbook.rheinwerk-verlag.de/javainsel9/javainsel_17_010.htm#mjb643b06ff5b465827ea26c24669f7289
 *
 * public static <T> T deepCopy( T o ) throws Exception
 * {
 *		ByteArrayOutputStream baos = new ByteArrayOutputStream();
 *		new ObjectOutputStream( baos ).writeObject( o );
 *
 *		ByteArrayInputStream bais = new ByteArrayInputStream( baos.toByteArray() );
 *		Object p = new ObjectInputStream( bais ).readObject();
 *
 *
 *		return (T) p;
 *	}
 */

/**
 * BinaryStrategy
 */
public class BinaryStrategy extends BaseStrategy
        implements fpt.com.SerializableStrategy {

	@Override
	public String getDestinationFilename() {
		return "products.ser";
	}

	/**
	 * This is needed for the combobox
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "BinaryStrategy";
	}
}

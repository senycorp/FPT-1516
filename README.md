## FPT-1516

Repository for FPT-Excercises

## Verwendung und Konfiguration

Aktuell ist vorgesehen, dass dieses Repository als Eclipse-Workspace eingebunden wird. Dazu klickt man auf "File > Switch workspace" 
und wählt das geklonte Directory aus. Alle Metadaten  wurden ebenfalls gepusht, so dass ein Großteil der Konfiguration enthalten ist.

## Issues

Bitte linkt eure Commits an vorher erstellte Issue-Tickets. Somit können wir nachverfolgen, zu welchem Thema bereits Arbeitsschritte vollzogen wurden.
Dazu reicht es beim Commit-Message die ID des Tickets mit einem führend # anzu geben: "#[TICKET-ID] Did some special stuff".

## Architektur

Coming soon...

#   Übungsblatt 1
#   Aufgabe 1a
Zu erstellende Klassen befinden sich im Paket {fpt.com.model}. Hierzu zählen Product, ProductList, Order. Alle erben von den geforderten Interfaces. Die Product-Klasse verwendet zusätzlich bereits Property-Objekte, welche das automatische aktualisieren in den TableViews erlauben.

Die beiden Klassen ProductList und Order erben von ArrayList, da sie lediglich als Container-Objekte für Products dienen und weiter nichts implementieren müssen.

# Aufgabe 1b

Die ModelShop-Klasse befindet sich ebenfalls im Package {fpt.com.model}. Sie erbt von ModifiableObserableListBase, um als Collection ohne Aufwand in der View verwendet werden zu können. Somit müssen keine lästigen EventListener per Observable registriert werden. JavaFX macht alles voll automatisch. Sobald sich unser Model verändert werden die Views aktualisiert.

# Aufgabe 1c

Die ViewShop enthält alle notwendigen View-Komponenten (TableView, Buttons, Textfields, etc.) und stellt eine Methode bereit, die es erlaubt einen Listener für die Actions (Button-Klicks) zu registrieren. Da lediglich ein Listener gebunden werden kann erfolgt die Unterscheidung welche Schaltfläche gedrückt wurde über die ID des Buttons.

# Aufgabe 1d

Die Controller befinden sich im Package {fpt.com.controller}. Die rudimentäre "link()"-Methode wurde durch eine Entkernung verlagert. Die Bindung erfolgt nun über die Implementierung der Interfaces "ModelableController" und "ViewableController". Die Interfaces setzen voraus, dass gewisse Methoden implementiert werden müssen, die einen Verweis auf die benötigte View/Model-Klasse liefern. Der Core-Controller "BaseController" kümmert sich dann um das Instanziieren und das Linken an den Controller.

# Aufgabe 1e

Die notwendigen View-Elemente werden von ViewShop erstellt und zu Verfügung gestellt. Das Erstellen und Löschen wird per EventListener an den Controller weitergeleitet. Die Registrierung des Listeners erfolgt im ControllerShop::initializeController():
```
// Set up eventhandler for add and delete Button
view.addEventHandler(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent event) {
        // Switch between add and delete button
        String buttonID = ((Button) event.getSource()).getId();

        switch(buttonID) {
            case "addButton":
                addProduct();
                break;
            case "deleteButton":
                deleteProduct();
                break;
            case "loadButton":
                loadProducts();
                break;
            case "saveButton":
                saveProducts();
                break;
        }
    }
});
```
Die Prüfung der Eingabe verläuft auf zwei Ebenen. In der ersten Ebene prüft die View, ob alle notwendigen Felder gefüllt sind und aktiviert erst dann den "Add"-Button. Auf der zweiten Ebene erfolgt dann die Validierung der eingegebenen Werte im Controller, also ob "Price" und "Quantity" numerische Werte sind. Der "Delete"-Button wird nur aktiviert, falls eine Zeile in der TableView ausgewählt ist. Ansonsten ist steht die Schaltfläche auf "disabled" und ist nicht klickbar.

# Am Rande

Die Tabellen sind in eigenständige Klassen als "TableView" ausgelagert und werden von den Views entsprechend instruiert.

# Aufgabe 2a und 2b

Die GUI wurde erstellt soll laut Blatt aber keinerlei Funktionalitäten enthalten. Die zugehörigen Klassen befinden sich im {fpt.com.controller} und {fpt.com.view}.

# Übungsblatt 2

# Aufgabe 1a
Die Klasse zur Generierung von IDs befindet sich im Package {fpt.com.component}. Diese wurde als Singleton implementiert um zu verhindern, dass zugleich mehrere Instanzen existieren. Singleton bedeutet, dass der Constructor auf private gesetzt wird und somit bei einer Instanziierung per "new" eine Exception geworfen wird, da diese als private nicht zugänglich ist. Die Instanz kann nur über die statische Methode "getInstance()" geholt werden. Diese erstellt eine Instanz nur wenn keine existiert:

```
/**
 * Get instance of class
 *
 * @return
 */
public static IDGenerator getInstance() {
    // Check for already existing instance
    if (instance == null) {
        instance = new IDGenerator();
    }
    return instance;
}
```

# Aufgabe 1b

Beim Speichern eines "Products" wird vom "IDGenerator" eine ID beantragt, welches dem "Product"-Objekt übergeben wird. Dies geschieht in "ControllerShop::addProduct()":

```
// Set product properties
p.setId(idGen.getId());     // Get an id from IDGenerator and set it as ID of product
p.setName(view.getName());
p.setPrice(price);
p.setQuantity(quantity);
```

# Aufgabe 1c

Dieses Handling geschieht im "IDGenerator::getID()", welcher bei jedem ID prüft, ob die maximale Anzahl überschritten wurde oder nicht. Die dazugehörige Exception ist ebenfalls dort als innere Klasse definiert.

```
/**
 * Generate an id
 *
 * @return
 * @throws IDOverflow
 */
public long getId() throws IDOverflow {
    if (this.id == IDmax) {
        // Throw exception if maximum amount of id is reached
        throw new IDOverflow();
    }
    return id++;
}
```

# Aufgabe 1d

Die Auswahl der Serialisierungsstrategie erfolgt über eine ComboBox, welche in der ViewShop initialisiert und gefüllt wird:

```
comboBox.getItems().addAll(new BinaryStrategy(), new XMLStrategy(), new XStreamStrategy());
```
Die Strategien implementieren alle die "toString()"-Methode um entsprechend dargestellt zu werden:

```
@Override
public String toString() {
    return "XStreamStrategy";
}
```

Das Laden/Speichern der Produkte über die jeweilige Strategie erfolgt im ControllerShop, welcher sich über die Listener bindet:

```
private void loadProducts() {
    SerializableStrategy strategy = (SerializableStrategy)this.view.comboBox.getValue();

    if (strategy == null) {
        Alert.warning("Strategy not selected!", "Please make sure that you select a strategy first.",
                      "You should choose a strategy first to deserialize your product list.").show();
        return;
    }

    try {
        // Clear the products list
        model.getProducts().clear();
        Product product;
        int i = 0;
        long lastId = 0;
        while ((product = (Product)strategy.readObject()) != null) {
            model.doAdd(i++, product);
            lastId = product.getId();
        }

        // Make sure to generate unique IDs
        idGen.setId(lastId+1);

        Tooltip.disappearingTooltip("Products loaded successfully!");

        // Activate save button again
        if (i > 0) {
            view.saveButton.setDisable(false);
        } else {
            view.saveButton.setDisable(true);
        }
    } catch (IOException openError) {
        Alert.error("Error",
                    "Unable to open output stream",
                    "Please make sure that the resource file is readable and try again.").show();
        openError.printStackTrace();
    } finally {
        try {
            strategy.close();
        } catch (IOException closeError) {
            Alert.error("Error",
                        "Unable to close output stream",
                        "Please make sure that the resource file is readable and allready existing on your harddisk.");
            closeError.printStackTrace();
        }
    }
}

private void saveProducts() {
    SerializableStrategy strategy = (SerializableStrategy)this.view.comboBox.getValue();

	if (strategy == null) {
        Alert.warning("Strategy not selected!", "Please make sure that you select a strategy first.",
                      "You should choose a strategy first to serialize your product list.").show();
		return;
	}

    // Get products
    ObservableList<Product> pl = this.getModel().getProducts();

    // Serialize each product with the choosen strategy
    for (fpt.com.Product p : pl) {
        try {
            strategy.writeObject(p);
        } catch (IOException e) {
            Alert.warning("Error: Serializing object", "There was an error while serializing a product with the given strategy.",
                          "Please make sure that the there are no rights conflicts " +
                          "and the path is writable."
                          ).show();
            e.printStackTrace();

            return;
        }
    }

    try {
        strategy.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

    Tooltip.disappearingTooltip("Products saved successfully!");
}
```
## Aufgabe 2a
Das Interface "Serializable" und die "serialVersionUID" kann zu Versionierungszwecken gesetzt sein.
 
## Aufgabe 2b
Das Interface wurde implementiert. Ganz klar ist mir aktuell nicht was es genau macht. Anscheinend ist es notwendig um bei der XStream-Serialisierung Mappings vorzunehmen.

## Aufgabe 2c
Die BinaryStrategy befindet sicht im Package "fpt.com.component.strategy" und implementiert indirekt das geforderte Interface. Da alle Strategien die gleichen Strukturen verwendet, wurden diese in einer Core-Klasse "BaseStrategy" zusammengefasst. Diese kümmert sich um die Streams und alles weitere.

## Aufgabe 2d
Da diese Klassen nicht das "Serializable"-Interface implementieren können Sie nicht serialisiert werden,
auch wenn sie mit der Product-Klasse zusammenhängen. Das gleiche gilt beispielsweise für Threads und Sockets.
 
## Aufgabe 2e
Da gibt es nichts zu sagen. KLAPPT!

## Aufgabe 2f
Der Versuch einer Deserialisierung eines Objektes, bei der sich die Klasse verändert hat birgt verschiedene Probleme.
 
* Fall 1: Keine SUID

    Falls die Klasse keine SUID hat kommt es bei der kleinsten Änderung der Klasse und der anschließenden Deserialisierung
zu einer 

```
Exception: local class incompatible: stream classdesc serialVersionUID = 44259824709362049, local class serialVersionUID = 8962277452270582278
```
    
    Dies hängt damit zusammen, dass JAVA trotzdem eine SUID generiert und bei der Deserialisierung abgleicht.
 
* Fall 2: Eigene SUID
 
  Falls Attribute hinzugekommen sind, werden diese lediglich mit einem Default-Wert belegt (0 oder null).
  Falls Attribute entfernt werden, werden diese einfach ignoriert . Um zu verhindern, dass alte Objekte deserialisiert
  werden, kann der Abgleich mit der "serialVersionUID" verwendet werden. Dabei kommt es zu einer InvalidClassException,
  falls sich die UIDs nicht gleichen und die Deserialisierung wird somit verhindert.

## Aufgabe 2g 
Die "serialVersionUID" kann als Identifikationsnummer der Version einer Klasse verstanden werden. Bei jeder Deserialisierung
und dem Versuch des Castings in die Zielklasse geschieht ein Abgleich dieser UID um zu verhindern, dass es zu Kompatibilitätsproblemen kommt.Die Änderung der UID liegt im Ermessen des Entwicklers. Es is sicherlich nicht immer notwendig bei der kleinsten Veränderung der Klasse die UID hochzusetzen.

## Aufgabe 2h
Durch die Serialisierung und Deserialiserung eines Objektes wird keine komplett eigenständige Kopie im Speicher abgelegt.
Somit wird verhindert, dass zwei Variablen auf die gleiche Referenz zu greifen:
 
  QUELLE: http://openbook.rheinwerk-verlag.de/javainsel9/javainsel_17_010.htm#mjb643b06ff5b465827ea26c24669f7289

```
public static <T> T deepCopy( T o ) throws Exception
{
 	ByteArrayOutputStream baos = new ByteArrayOutputStream();
 	new ObjectOutputStream( baos ).writeObject( o );

 	ByteArrayInputStream bais = new ByteArrayInputStream( baos.toByteArray() );
 	Object p = new ObjectInputStream( bais ).readObject();

 	return (T) p;
 }
 ```

## Aufgabe 3

## Aufgabe 3a
Die Strategie wurde erstellt und befindet sich im  gleichen Package wie die BinaryStrategy. Durch die Verwendung des XML-Decoders/Encoders mussten einige Methoden der BaseStrategy überschrieben werden. Alle zur Serialiserung mit Beans notwendigen Konvention wurden implementiert und betreffen größtenteils das "Product"-Model.

## Aufgabe 3b
Klappt!

## Aufgabe 3c
Binäre- und Java-Beans-Serialisierung sind größtenteils gleich. Lediglich die Ablage der serialisierten Objekte ist binär oder im XML-Format. Dieser Unterschied wird durch die Verwendung eines anderen Encoders/Decoders erreicht. Während die binäre Serialisierung ObjectOutputStream/ObjectInputStream verwendet, wrappt die Beans-Serialisierung diese Streams nocheinmal in XMLDecoder/XMLEncoder. Des weiteren muss für die XML-Serialisierung sichergestellt werden, dass einige Konventionen eingehalten werden. Dazu zählt, dass jede Property über zugehörige Getter/Setter-Methoden verfügt. Weiter ist ein No-Argument-Konstruktor notwendig. Für die Sichtbarkeit von bestimmten Properties verwendet Beans nicht das Keyword "transient" sondern den PropertyDescriptor.

## Aufgabe 3d

#### Vorteile "Beans": 
* leserlich,
* 	Deserialisierung,
* 	Event-Listener "PropertyChangeListener"
* 	Portabilität

#### Nachteile "Beans": 	
* Konventionspflicht
* Lesbarkeit birgt ein Sicherheitsrisiko

#### Vorteile "Binary":	
* Keine Konventionspflicht
* Erhöhte Sicherheit durch unlesbares Format

#### Nachteile "Binary":	
* Nicht lesbar

## Aufgabe 4

## Aufgabe 4a
Attribute können per Annotations oder manuell im XStream-Objekt umbenannt werden:

```
// Anderer Tag-Name für Property
@XStreamAlias("MySpecialAlias")
private SimpleStringProperty name = new SimpleStringProperty();
```

## Aufgabe 4b
Die Konvertierung erfolgt über die "Converter", welche sich im Package "fpt.com.component.converter" befinden. Diese werden dazu verwendet, "SimpleXProperty"-Attribute des Product-Models in lesbare Werte zu übersetzen. Aus einem Objekt wird also ein einfacher primitiver Wert und bei der Deserialisierung umgekehrt. Die Dekoration erfolgt ebenfalls über die Converter. Dies geschieht zum Beispiel für die id, welche mit Nullen aufgefüllt wird.

## Aufgabe 4c
Diese Strategie ist ebenfalls im Package "fpt.com.component.strategy". Lediglich die"open()"-Methode musste für die Nutzung und Einstellung des XStream-Objektes angepasst werden:

```
@Override
public void open(InputStream input, OutputStream output) throws IOException {
    // Setting the instance with all necessary properties
    if (this.xstream == null) {
        xstream = new XStream(new DomDriver("UTF-8"));
        xstream.registerConverter(
                new ExternalizableReflectionConverter(xstream),
                XStream.PRIORITY_LOW);
        xstream.processAnnotations(fpt.com.model.Product.class);
        xstream.alias("Ware", fpt.com.model.Product.class);
        xstream.autodetectAnnotations(true);
    }

    // Setting up object streams for input and output
    if (input != null) {
        this.is = input;
        this.objectIS = xstream.createObjectInputStream(is);
    }

    if (output != null) {
        this.os = output;
        this.objectOS = xstream.createObjectOutputStream(output, "Waren");
    }
}
```

## Aufgabe 4d
Die ID wurde per Annotation (@XStreamAsAttribute) zu einem XML-Attribut transformiert. Das Auffüllen übernimmt der "LongConverter":

```
/**
 * ID
 */
@XStreamAlias("id")
@XStreamConverter(LongConverter.class)
@XStreamAsAttribute
private SimpleLongProperty id = new SimpleLongProperty();
```

## Aufgabe 4e
Diese Aufgabe wird vom DoubleConverter übernommen:

```
/**
 * Property to string
 *
 * @param arg0
 * @return
 */
public String toString(Object arg0) {
	return String.format(Locale.US,"%3.2f", ((SimpleDoubleProperty)arg0).getValue());
}
```

## Aufgabe 4f
Die Converter wurden implementiert und befindet sich im Package "fpt.com.components.converter".

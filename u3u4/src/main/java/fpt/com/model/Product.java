package fpt.com.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import fpt.com.component.converter.DoubleConverter;
import fpt.com.component.converter.IntegerConverter;
import fpt.com.component.converter.LongConverter;
import fpt.com.component.converter.StringConverter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import org.apache.openjpa.persistence.Persistent;
import org.apache.openjpa.persistence.jdbc.Strategy;

import javax.persistence.*;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Model: Product
 */
@Entity()
@Table(name="products")
@XStreamAlias("Ware")
public class Product
        implements fpt.com.Product, java.io.Externalizable {


    /**
     * SerialID
     */
    private static final long serialVersionUID = 79055685589078331L;

    /**
     * Name
     */
    @Persistent
    @Strategy("fpt.com.db.StringPropertyValueHandler")
    @XStreamAlias("name")
    @XStreamConverter(StringConverter.class)
    private SimpleStringProperty name = new SimpleStringProperty();



    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY, generator="products_SEQ " )
    @Column(name="id")
    private Long idDB;

    public Long getIdDB() {
        return this.idDB;
    }

    public void setIdDB(Long id) {
        this.idDB = id;
        this.id.set(this.idDB);
    }

    /**
     * ID
     */
    @Persistent
    @Strategy("fpt.com.db.LongPropertyValueHandler")
    @XStreamAlias("id")
    @XStreamConverter(LongConverter.class)
    @XStreamAsAttribute
    private SimpleLongProperty id = new SimpleLongProperty();

    /**
     * Price
     */
    @Persistent
    @Strategy("fpt.com.db.DoublePropertyValueHandler")
    @XStreamAlias("preis")
    @XStreamConverter(DoubleConverter.class)
    private SimpleDoubleProperty price = new SimpleDoubleProperty();

    /**
     * Quantity
     */
    @Persistent
    @Strategy("fpt.com.db.IntegerPropertyValueHandler")
    @XStreamAlias("anzahl")
    @XStreamConverter(IntegerConverter.class)
    private SimpleIntegerProperty quantity = new SimpleIntegerProperty();

    /**
     * Constructor
     */
    public Product() {
        // No implementation needed
    }

    /**
     * Constructor
     *
     * @param id
     * @param name
     * @param price
     * @param quantity
     */
    public Product(long id, String name, double price, int quantity) {
        System.out.println(id);
        this.id.set(this.getIdDB());
        this.name.set(name);
        this.price.set(price);
        this.quantity.set(quantity);
    }

    @Override
    public long getId() {
        return this.id.get();
    }

    @Override
    public void setId(long id) {
        this.id.set(id);
    }

    @Override
    public double getPrice() {
        return this.price.get();
    }

    @Override
    public void setPrice(double price) {
        this.price.set(price);
    }

    @Override
    public int getQuantity() {
        return this.quantity.get();
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    @Override
    public String getName() {
        return this.name.get();
    }

    @Override
    public void setName(String name) {
        this.name.set(name);
    }

    @Override
    public ObservableValue<String> nameProperty() {
        return this.name;
    }

    @Override
    public ObservableValue<Number> priceProperty() {
        //s.o.
        return this.price;
    }

    @Override
    public ObservableValue<Number> quantityProperty() {
        return this.quantity;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setId(in.readLong());
        setName((String) in.readObject());
        setPrice(in.readDouble());
        setQuantity(in.readInt());
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(getId());
        out.writeObject(getName());
        out.writeDouble(getPrice());
        out.writeInt(getQuantity());
    }

}
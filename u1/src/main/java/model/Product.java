package model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Model: Product
 */
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
    private SimpleStringProperty name = new SimpleStringProperty();

    /**
     * ID
     */
    private SimpleLongProperty id = new SimpleLongProperty();

    /**
     * Price
     */
    private SimpleDoubleProperty price = new SimpleDoubleProperty();

    /**
     * Quantity
     */
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
        this.id.set(id);
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
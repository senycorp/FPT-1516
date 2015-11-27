package fpt.com.core.model;

import javafx.collections.FXCollections;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;

/**
 * BaseModel
 *
 * @author senycorp
 */
abstract public class BaseModel<E, T extends BaseModelList>
        extends ModifiableObservableListBase<E> {

    /**
     * ObservableList
     */
    protected ObservableList<E> list;

    protected BaseModelList sourceList;

    /**
     * Constructor
     *
     * @param
     */
    public BaseModel() {
        // Set ObservableList for the view
        this.sourceList = getList();
        this.list = FXCollections.observableArrayList(this.sourceList);
    }

    /**
     * Get Sourcelist
     * @return
     */
    public BaseModelList getSourceList() {
        return this.sourceList;
    }

    /**
     * Get the instantiated list object
     *
     * @return
     */
    public abstract T getList();

    /**
     * Returns observable list for the product list
     *
     * @return
     */
    public ObservableList<E> getProducts() {
        return this.list;
    }

    @Override
    public E get(int index) {
        return (E) this.list.get(index);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public void doAdd(int index, E product) {
        this.list.add(index, product);
    }

    @Override
    public E doSet(int index, E product) {
        return (E) this.list.set(index, product);
    }

    @Override
    public E doRemove(int index) {
        return (E) this.list.remove(index);
    }
}

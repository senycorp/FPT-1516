package fpt.com.db;

import javafx.beans.property.SimpleLongProperty;
import org.apache.openjpa.meta.JavaTypes;

public class LongPropertyValueHandler
        extends AbstractPropertyValueHandler {

    private static final long serialVersionUID = 6977270441354674752L;

    @Override
    protected Object mapToObject(Object value) {
        return new SimpleLongProperty(new Long(value.toString()));
    }

    @Override
    int getDatabaseColumnType() {
        return JavaTypes.NUMBER;
    }
}

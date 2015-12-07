package fpt.com.component.converter;

import javafx.beans.property.SimpleIntegerProperty;

import com.thoughtworks.xstream.converters.SingleValueConverter;


public class IntegerConverter implements SingleValueConverter {

	public boolean canConvert(Class arg0) {
		return SimpleIntegerProperty.class.equals(arg0);
	}

	public Object fromString(String arg0) {
		return new SimpleIntegerProperty(Integer.parseInt(arg0));
	}

	public String toString(Object arg0) {
		return ((SimpleIntegerProperty)arg0).getValue().toString();
	}

}

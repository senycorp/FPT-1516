package fpt.com.component.converter;

import javafx.beans.property.SimpleStringProperty;

import com.thoughtworks.xstream.converters.SingleValueConverter;


public class StringConverter implements SingleValueConverter {

	public boolean canConvert(Class arg0) {
		return SimpleStringProperty.class.equals(arg0);
	}

	public Object fromString(String arg0) {
		return new SimpleStringProperty(arg0);
	}

	public String toString(Object arg0) {
		return ((SimpleStringProperty)arg0).getValue();
	}

}
package fpt.com.component.converter;

import javafx.beans.property.SimpleLongProperty;

import com.thoughtworks.xstream.converters.SingleValueConverter;


public class LongConverter implements SingleValueConverter {

	public boolean canConvert(Class arg0) {
		return SimpleLongProperty.class.equals(arg0);
	}

	public Object fromString(String arg0) {
		return new SimpleLongProperty(Long.parseLong(arg0));
	}

	public String toString(Object arg0) {
		return String.format("%06d", ((SimpleLongProperty)arg0).getValue());
	}

}

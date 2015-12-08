package fpt.com.component.converter;

import javafx.beans.property.SimpleIntegerProperty;

import com.thoughtworks.xstream.converters.SingleValueConverter;

/**
 * XStream: IntegerConverter
 */
public class IntegerConverter implements SingleValueConverter {

	/**
	 * Converter
	 *
	 * @param arg0
	 * @return
	 */
	public boolean canConvert(Class arg0) {
		return SimpleIntegerProperty.class.equals(arg0);
	}

	/**
	 * String to property
	 *
	 * @param arg0
	 * @return
	 */
	public Object fromString(String arg0) {
		return new SimpleIntegerProperty(Integer.parseInt(arg0));
	}

	/**
	 * Property to string
	 *
	 * @param arg0
	 * @return
	 */
	public String toString(Object arg0) {
		return ((SimpleIntegerProperty)arg0).getValue().toString();
	}

}

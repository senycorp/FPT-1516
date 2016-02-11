package fpt.com.component.converter;

import javafx.beans.property.SimpleStringProperty;

import com.thoughtworks.xstream.converters.SingleValueConverter;

/**
 * XStream: StringConverter
 */
public class StringConverter implements SingleValueConverter {

	/**
	 * Converter
	 *
	 * @param arg0
	 * @return
	 */
	public boolean canConvert(Class arg0) {
		return SimpleStringProperty.class.equals(arg0);
	}

	/**
	 * String to property
	 *
	 * @param arg0
	 * @return
	 */
	public Object fromString(String arg0) {
		return new SimpleStringProperty(arg0);
	}

	/**
	 * Property to string
	 *
	 * @param arg0
	 * @return
	 */
	public String toString(Object arg0) {
		return ((SimpleStringProperty)arg0).getValue();
	}

}
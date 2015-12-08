package fpt.com.component.converter;

import javafx.beans.property.SimpleLongProperty;

import com.thoughtworks.xstream.converters.SingleValueConverter;

/**
 * XStream: LongConverter
 */
public class LongConverter implements SingleValueConverter {

	/**
	 * Converter
	 *
	 * @param arg0
	 * @return
	 */
	public boolean canConvert(Class arg0) {
		return SimpleLongProperty.class.equals(arg0);
	}

	/**
	 * String to property
	 *
	 * @param arg0
	 * @return
	 */
	public Object fromString(String arg0) {
		return new SimpleLongProperty(Long.parseLong(arg0));
	}

	/**
	 * Property to string
	 *
	 * @param arg0
	 * @return
	 */
	public String toString(Object arg0) {
		return String.format("%06d", ((SimpleLongProperty)arg0).getValue());
	}

}

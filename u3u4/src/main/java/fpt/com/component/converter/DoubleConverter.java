package fpt.com.component.converter;

import com.thoughtworks.xstream.converters.SingleValueConverter;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Locale;

/**
 * XStream: DoubleConverter
 */
public class DoubleConverter implements SingleValueConverter{

	/**
	 * Converter
	 *
	 * @param arg0
	 * @return
	 */
	public boolean canConvert(Class arg0) {
		return SimpleDoubleProperty.class.equals(arg0);
	}


	/**
	 * String to property
	 *
	 * @param arg0
	 * @return
	 */
	public Object fromString(String arg0) {
		return new SimpleDoubleProperty(Double.parseDouble(arg0));
	}


	/**
	 * Property to string
	 *
	 * @param arg0
	 * @return
	 */
	public String toString(Object arg0) {
		return String.format(Locale.US,"%3.2f", ((SimpleDoubleProperty)arg0).getValue());
	}

}
